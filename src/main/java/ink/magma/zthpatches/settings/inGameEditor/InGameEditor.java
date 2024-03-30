package ink.magma.zthpatches.settings.inGameEditor;

import ink.magma.zthpatches.ZthPatches;
import ink.magma.zthpatches.settings.GlobalSettings;
import ink.magma.zthpatches.settings.GlobalSettingsController;
import org.bukkit.command.CommandSender;
import revxrsal.commands.annotation.AutoComplete;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;


/**
 * 本模块实现了一个游戏内 GlobalSettings 编辑器
 * 方便在游戏内直接开关各种补丁
 * <p>
 * 指令实现了完善的自动补全。
 */
public class InGameEditor {

    public static void registerCommand() {
        BukkitCommandHandler handler = ZthPatches.getInstance().handler;

        // 自动补全相关
        // 设置键的补全
        handler.getAutoCompleter().registerSuggestion("inGameGlobalSettingKey", (args, sender, command) -> GlobalSettingsController.getModifiableFieldNames());
        // 设置值的补全
        handler.getAutoCompleter().registerSuggestion("inGameGlobalSettingValue", (args, sender, command) -> {
            try {
                // 获取用户正在输入的设置的键名
                String settingKey = args.get(1);
                Field field = GlobalSettingsController.getModifiableField(settingKey);
                // 根据此配置项的类，获取可能的补全
                Set<String> fieldSuggestions = new java.util.HashSet<>(GlobalSettingsController.getFieldSuggestions(field.getType()));

                // 同时也将配置项现在已经设置的值加到自动补全中，方便修改
                try {
                    fieldSuggestions.add(GlobalSettingsController.getFieldValueString(settingKey));
                } catch (Throwable ignore) {
                    // 如果无法获取当前值就算了
                }

                return fieldSuggestions;
            } catch (Throwable ignore) {
                // 避免不存在字段读取
            }
            return List.of();
        });
        // 注册指令
        handler.register(new InGameEditor());
        handler.registerBrigadier();
    }

    @Command("zth-patches-setting")
    @AutoComplete("@inGameGlobalSettingKey @inGameGlobalSettingValue")
    public void getSettingValue(CommandSender sender,
                                @Named("配置项") String settingKey,
                                @Optional @Named("配置项值") String settingValue) {
        // Get
        if (settingValue == null) {
            try {
                String fieldValueString = GlobalSettingsController.getFieldValueString(settingKey);
                sender.sendMessage(MessageFormat.format(
                        "配置项 {0} 的值为 {1}",
                        settingKey, fieldValueString
                ));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                sender.sendMessage("配置项不存在");
            } catch (NullPointerException e) {
                sender.sendMessage(MessageFormat.format(
                        "配置项 {0} 的值为 {1}",
                        settingKey, "未配置"
                ));
            }
        }
        // Set
        else {
            try {
                GlobalSettingsController.setModifiableFieldsByString(ZthPatches.getGlobalSettings(), settingKey, settingValue);
                ZthPatches.getInstance().saveConfig();
                sender.sendMessage(MessageFormat.format(
                        "配置项 {0} 已修改为 {1}",
                        settingKey, settingValue
                ));
            } catch (GlobalSettingsController.NoSupportTypeException e) {
                sender.sendMessage("很抱歉，此类型的配置暂无法在游戏内更改，请前往服务端配置文件修改。");
                return;
            } catch (NoSuchFieldException e) {
                sender.sendMessage(MessageFormat.format("找不到 {0} 配置项.", settingKey));
                return;
            } catch (IllegalAccessException e) {
                sender.sendMessage("很抱歉，由于可能的程序 BUG，修改此配置项被拒绝。 (IllegalAccessException)");
                return;
            } catch (IllegalArgumentException e) {
                String typeName;
                try {
                    typeName = GlobalSettings.class.getField(settingKey).getType().getSimpleName();
                } catch (NoSuchFieldException ignore) {
                    typeName = "未知类型";
                }
                sender.sendMessage(MessageFormat.format(
                        "无法解析输入 \"{0}\", 需要的类型: {1}",
                        settingValue, typeName
                ));
            } catch (IllegalStateException e) {
                sender.sendMessage("已更改此配置，但无法保存新的配置文件到硬盘。本次的配置在重启后可能失效！");
            }
        }
    }
}