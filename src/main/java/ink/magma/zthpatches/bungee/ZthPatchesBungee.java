package ink.magma.zthpatches.bungee;

import ink.magma.zthpatches.PlatformHandler;
import ink.magma.zthpatches.bukkit.settings.GlobalSettingInitializer;
import ink.magma.zthpatches.command.InGameEditor.InGameEditorBungee;
import ink.magma.zthpatches.command.Main.MainCommandBungee;
import net.md_5.bungee.api.plugin.Plugin;
import revxrsal.commands.bungee.BungeeCommandHandler;

public class ZthPatchesBungee extends Plugin {
    private static ZthPatchesBungee instance;
    public BungeeCommandHandler handler;

    @Override
    public void onEnable() {
        instance = this;
        PlatformHandler.setPlatformType(PlatformHandler.PlatformType.Bungee);

        GlobalSettingInitializer.createConfig();

        handler = BungeeCommandHandler.create(this);
        // 主指令
        handler.register(new MainCommandBungee());
        // 游戏内编辑器指令
        InGameEditorBungee inGameEditor = new InGameEditorBungee();
        inGameEditor.registerAutoCompleter(handler);
        handler.register(inGameEditor);

    }


    public static ZthPatchesBungee getInstance() {
        return instance;
    }
}
