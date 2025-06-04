package ink.magma.zthpatches.commands.Main;

import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command({"zth-patches", "zp"})
public class MainBukkit {
    private final Main real;

    public MainBukkit() {
        real = new Main();
    }

    @Subcommand("reload-config")
    @CommandPermission("zth-patches.reload")
    public void reloadConfig(BukkitCommandActor sender) {
        real.reloadConfig(sender);
    }
}
