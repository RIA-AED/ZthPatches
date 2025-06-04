package ink.magma.zthpatches.bukkit.patches.MyMenu.commands;

import ink.magma.zthpatches.bukkit.patches.MyMenu.menus.main.MainMenu;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;

public class MainMenuCommand {
    @Command("cd-test")
    public void open(Player player) {
        new MainMenu(player);
    }
}
