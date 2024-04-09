package ink.magma.zthpatches.command.InGameEditor;

import revxrsal.commands.CommandHandler;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.command.CommandActor;

public class InGameEditorBukkit {
    private final InGameEditor real;

    public InGameEditorBukkit() {
        real = new InGameEditor();
    }

    public void registerAutoCompleter(CommandHandler handler) {
        real.registerAutoCompleter(handler);
    }

    @Command("zth-patches-setting")
    @CommandPermission("zth-patches.setting")
    @Description("可以实现游戏内对 ZthPatches 设置的更改。")
    @AutoComplete("@inGameGlobalSettingKey @inGameGlobalSettingValue")
    public void command(BukkitCommandActor sender,
                        @Named("配置项") String settingKey,
                        @Optional @Named("配置项值") String settingValue) {
        real.command(sender, settingKey, settingValue);
    }
}
