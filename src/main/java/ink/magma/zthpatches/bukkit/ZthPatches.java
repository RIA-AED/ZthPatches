package ink.magma.zthpatches.bukkit;

import ink.magma.zthpatches.PlatformHandler;
import ink.magma.zthpatches.bukkit.patches.ComposterMoreRecipe.ComposterMoreRecipe;
import ink.magma.zthpatches.bukkit.patches.DragonEggAddon.DragonEggAddon;
import ink.magma.zthpatches.bukkit.patches.LobbyNewPlayerSpawn.LobbyNewPlayerSpawn;
import ink.magma.zthpatches.bukkit.patches.RiaSetFreezeCommand.RiaSetFreezeCommand;
import ink.magma.zthpatches.bukkit.settings.GlobalSettingInitializer;
import ink.magma.zthpatches.bukkit.settings.GlobalSettings;
import ink.magma.zthpatches.command.InGameEditor.InGameEditor;
import ink.magma.zthpatches.command.Main.MainCommand;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public final class ZthPatches extends JavaPlugin {
    private static ZthPatches instance;
    public BukkitCommandHandler handler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        PlatformHandler.setPlatformType(PlatformHandler.PlatformType.Bukkit);

        GlobalSettingInitializer.createConfig();

        handler = BukkitCommandHandler.create(this);
        // 主指令
        handler.register(new MainCommand());
        // 游戏内编辑器指令
        InGameEditor inGameEditor = new InGameEditor();
        inGameEditor.registerAutoCompleter(handler);
        handler.register(inGameEditor);

        handler.registerBrigadier();

        // patches
        new DragonEggAddon().onEnable();
        new ComposterMoreRecipe().onEnable();
        new RiaSetFreezeCommand().onEnable();
        new LobbyNewPlayerSpawn().onEnable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ZthPatches getInstance() {
        return instance;
    }

    public static GlobalSettings getGlobalSettings() {
        return GlobalSettingInitializer.getGlobalSettings();
    }
}
