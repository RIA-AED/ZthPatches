package ink.magma.zthpatches.commands.Main;

import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bungee.BungeeCommandActor;
import revxrsal.commands.bungee.annotation.CommandPermission;

@Command({"zth-patches-bungee", "zpb"})
public class MainBungee {
    private final Main real;

    public MainBungee() {
        real = new Main();
    }

    @Subcommand("reload-config")
    @CommandPermission("zth-patches.reload")
    public void reloadConfig(BungeeCommandActor actor) {
        real.reloadConfig(actor);
    }
}
