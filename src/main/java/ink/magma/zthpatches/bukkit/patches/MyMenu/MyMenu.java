package ink.magma.zthpatches.bukkit.patches.MyMenu;

import ink.magma.zthpatches.PatchAddon;
import ink.magma.zthpatches.bukkit.ZthPatchesBukkit;
import ink.magma.zthpatches.bukkit.patches.MyMenu.commands.MainMenuCommand;

public class MyMenu extends PatchAddon {
    @Override
    public void onEnable() {
        ZthPatchesBukkit.getInstance().getLogger().info("菜单 patch 启动");
        ZthPatchesBukkit.getInstance().handler.register(new MainMenuCommand());
    }

    @Override
    public void onDisable() {

    }
}
