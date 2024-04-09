package ink.magma.zthpatches.states.settings;

import ink.magma.zthpatches.PlatformHandler;
import ink.magma.zthpatches.bukkit.ZthPatchesBukkit;
import ink.magma.zthpatches.bungee.ZthPatchesBungee;
import net.william278.annotaml.Annotaml;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GlobalSettingInitializer {
    private static GlobalSettings globalSettings;

    public static void createConfig() throws IllegalStateException {
        try {
            final Annotaml<GlobalSettings> annotaml = Annotaml.create(getConfigFile(), GlobalSettings.class);
            globalSettings = annotaml.get();
        } catch (IOException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("创建配置文件失败", e);
        }
    }

    public static void saveConfig() throws IllegalStateException {
        try {
            // Create a *new* Annotaml wrapper over the newly modified object...
            final Annotaml<GlobalSettings> annotaml = Annotaml.create(globalSettings);
            annotaml.save(getConfigFile()); // This will overwrite the file if it already exists.
        } catch (Throwable e) {
            throw new IllegalStateException("保存配置文件时失败", e);
        }
    }

    public static GlobalSettings getGlobalSettings() {
        return globalSettings;
    }

    @NotNull
    private static File getConfigFile() {
        if (PlatformHandler.getPlatformType().equals(PlatformHandler.PlatformType.Bukkit)) {
            return new File(ZthPatchesBukkit.getInstance().getDataFolder(), "config.yml");
        }
        if (PlatformHandler.getPlatformType().equals(PlatformHandler.PlatformType.Bungee)) {
            return new File(ZthPatchesBungee.getInstance().getDataFolder(), "config.yml");
        }
        return null;
    }
}
