package ink.magma.zthpatches.command.InGameEditor;

import ink.magma.zthpatches.states.settings.GlobalSettingInitializer;
import ink.magma.zthpatches.states.settings.GlobalSettings;
import ink.magma.zthpatches.states.settings.GlobalSettingsController;
import ink.magma.zthpatches.utils.MessageUtils;
import revxrsal.commands.CommandHandler;
import revxrsal.commands.command.CommandActor;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 本模块实现了一个游戏内 GlobalSettings 编辑器
 * 方便在游戏内直接开关各种补丁
 * <p>
 * 指令实现了完善的自动补全。
 */
public class InGameEditor {

    public void command(CommandActor sender, String settingKey, String settingValue) {
        // Get
        if (settingValue == null) {
            try {
                String fieldValueString = GlobalSettingsController.getFieldValueString(getSettings().getClass(), settingKey);
                MessageUtils.sendMiniMessage(sender, MessageFormat.format(
                        "<gray>配置项 <white>{0} <gray>的值为 <white>{1} <gray>.",
                        settingKey, fieldValueString
                ));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                MessageUtils.sendMiniMessage(sender, "<gray>配置项不存在.");
            } catch (NullPointerException e) {
                MessageUtils.sendMiniMessage(sender, MessageFormat.format(
                        "<gray>配置项 <white>{0} <gray>的值为 <white>{1} <gary>.",
                        settingKey, "<i>未配置</i>"
                ));
            }
        }
        // Set
        else {
            try {
                GlobalSettingsController.setModifiableFieldsByString(getSettings(), settingKey, settingValue);
                saveConfig();
                MessageUtils.sendMiniMessage(sender, MessageFormat.format(
                        "<gray>配置项 <white>{0} <gray>已修改为 <white>{1} <gray>.",
                        settingKey, settingValue
                ));
            } catch (GlobalSettingsController.NoSupportTypeException e) {
                MessageUtils.sendMiniMessage(
                        sender, "<gray>很抱歉，此类型的配置暂无法在游戏内更改，请前往服务端配置文件修改."
                );
            } catch (NoSuchFieldException e) {
                MessageUtils.sendMiniMessage(
                        sender,
                        MessageFormat.format("<gray>找不到 <white>{0} <gray>配置项.", settingKey)
                );
            } catch (IllegalAccessException e) {
                MessageUtils.sendMiniMessage(
                        sender,
                        "<red>很抱歉，由于可能的程序 BUG，修改此配置项被拒绝. (IllegalAccessException)"
                );
            } catch (IllegalArgumentException e) {
                String typeName;
                try {
                    typeName = GlobalSettings.class.getField(settingKey).getType().getSimpleName();
                } catch (NoSuchFieldException ignore) {
                    typeName = "未知类型";
                }
                MessageUtils.sendMiniMessage(
                        sender,
                        MessageFormat.format(
                                "<gray>无法解析输入 <white>\"{0}\"<gray>, 需要的类型: <white>{1} <gray>.",
                                settingValue, typeName
                        )
                );
            } catch (IllegalStateException e) {
                MessageUtils.sendMiniMessage(
                        sender,
                        "<red>已更改此配置，但无法保存新的配置文件到硬盘. 本次的配置在重启后可能失效！"
                );
            }
        }
    }

    public void registerAutoCompleter(CommandHandler handler) {
        // 自动补全相关
        // 设置键的补全
        handler.getAutoCompleter().registerSuggestion(
                "inGameGlobalSettingKey",
                (args, sender, command) -> GlobalSettingsController.getModifiableFieldNames(getSettings().getClass())
        );
        // 设置值的补全
        handler.getAutoCompleter().registerSuggestion("inGameGlobalSettingValue", (args, sender, command) -> {
            try {
                // 获取用户正在输入的设置的键名
                String settingKey = args.get(1);
                Field field = getSettings().getClass().getField(settingKey);
                // 根据此配置项的类，获取可能的补全
                Set<String> fieldSuggestions = new HashSet<>(GlobalSettingsController.getFieldSuggestions(field.getType()));

                // 同时也将配置项现在已经设置的值加到自动补全中，方便修改
                try {
                    fieldSuggestions.add(GlobalSettingsController.getFieldValueString(getSettings().getClass(), settingKey));
                } catch (Throwable ignore) {
                    // 如果无法获取当前值就算了
                }

                return fieldSuggestions;
            } catch (Throwable ignore) {
                // 避免不存在字段读取
            }
            return List.of();
        });
    }

    private GlobalSettings getSettings() {
        return GlobalSettingInitializer.getGlobalSettings();
    }

    private void saveConfig() {
        GlobalSettingInitializer.saveConfig();
    }
}