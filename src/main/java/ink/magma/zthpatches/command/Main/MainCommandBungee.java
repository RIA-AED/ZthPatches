package ink.magma.zthpatches.command.Main;

import org.bukkit.command.CommandSender;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;

@Command({"zth-patches-bungee", "zpb"})
public class MainCommandBungee {
    private final MainCommand real;

    public MainCommandBungee() {
        real = new MainCommand();
    }

    @Subcommand("reload-config")
    public void reloadConfig(CommandSender sender) {
        real.reloadConfig(sender);
    }
}
