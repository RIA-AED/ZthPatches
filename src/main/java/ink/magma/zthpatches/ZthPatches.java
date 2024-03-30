package ink.magma.zthpatches;

import ink.magma.zthpatches.command.MainCommand;
import ink.magma.zthpatches.patches.ComposterMoreRecipe.ComposterMoreRecipe;
import ink.magma.zthpatches.patches.DragonEggAddon.DragonEggAddon;
import ink.magma.zthpatches.patches.Events20240401Zeroth1132.Events20240401Zeroth1132;
import ink.magma.zthpatches.patches.RiaSetFreezeCommand.RiaSetFreezeCommand;
import ink.magma.zthpatches.settings.GlobalSettings;
import ink.magma.zthpatches.settings.inGameEditor.InGameEditor;
import net.william278.annotaml.Annotaml;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public final class ZthPatches extends JavaPlugin {
    public GlobalSettings globalSettings;
    private static ZthPatches instance;
    public BukkitCommandHandler handler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        createConfig();
        instance = this;

        handler = BukkitCommandHandler.create(this);
        handler.register(new MainCommand());
        handler.registerBrigadier();
        InGameEditor.registerCommand();

        new DragonEggAddon().onEnable();
        new ComposterMoreRecipe().onEnable();
        new RiaSetFreezeCommand().onEnable();
        new Events20240401Zeroth1132().onEnable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ZthPatches getInstance() {
        return instance;
    }

    public static GlobalSettings getGlobalSettings() {
        return getInstance().globalSettings;
    }

    public void createConfig() throws IllegalStateException {
        try {
            final Annotaml<GlobalSettings> annotaml = Annotaml.create(getConfigFile(), GlobalSettings.class);
            globalSettings = annotaml.get();
        } catch (IOException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("创建配置文件失败", e);
        }
    }

    public void saveConfig() throws IllegalStateException {
        try {
            // Create a *new* Annotaml wrapper over the newly modified object...
            final Annotaml<GlobalSettings> annotaml = Annotaml.create(globalSettings);
            annotaml.save(getConfigFile()); // This will overwrite the file if it already exists.
        } catch (Throwable e) {
            throw new IllegalStateException("保存配置文件时失败", e);
        }
    }

    @NotNull
    private File getConfigFile() {
        return new File(getDataFolder(), "config.yml");
    }
}
