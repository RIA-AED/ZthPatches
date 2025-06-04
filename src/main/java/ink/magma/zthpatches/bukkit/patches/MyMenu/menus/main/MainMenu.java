package ink.magma.zthpatches.bukkit.patches.MyMenu.menus.main;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import ink.magma.zthpatches.bukkit.patches.MyMenu.menus.main.navigator.Navigator;
import org.bukkit.entity.Player;

import static ink.magma.zthpatches.utils.MessageUtils.mini;

public class MainMenu {
    private final Navigator navigator;

    public MainMenu(Player player) {
        ChestGui menu = new ChestGui(6, "个人菜单");
        menu.setOnGlobalClick(e -> e.setCancelled(true));
        navigator = new Navigator(player);
        menu.addPane(navigator.getPane());
        navigator.onChange(newPage -> {
            player.sendMessage(mini.deserialize(newPage.name()));
        });

        player.openInventory(menu.getInventory());
    }
}
