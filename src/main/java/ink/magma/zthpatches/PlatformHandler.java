package ink.magma.zthpatches;

import ink.magma.zthpatches.bukkit.ZthPatches;
import ink.magma.zthpatches.bungee.ZthPatchesBungee;
import net.md_5.bungee.api.plugin.Plugin;
import revxrsal.commands.CommandHandler;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import revxrsal.commands.bungee.BungeeCommandHandler;

public class PlatformHandler {
    public static CommandHandler getPlatformCommandHandler() {
        if (getPlatformType().equals(PlatformType.Bukkit)) {
            return BukkitCommandHandler.create(ZthPatches.getInstance());
        }
        if (getPlatformType().equals(PlatformType.Bungee)) {
            BungeeCommandHandler.create((Plugin) ZthPatchesBungee.getInstance());
        }
        return null;
    }

    private static PlatformType platformType;

    public static PlatformType getPlatformType() {
        return platformType;
    }

    public static void setPlatformType(PlatformType platformType) {
        PlatformHandler.platformType = platformType;
    }

    public enum PlatformType {
        Bukkit,
        Bungee
    }
}
