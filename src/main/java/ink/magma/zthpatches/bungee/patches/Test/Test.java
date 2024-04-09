package ink.magma.zthpatches.bungee.patches.Test;

import ink.magma.zthpatches.PatchAddon;
import ink.magma.zthpatches.bungee.ZthPatchesBungee;
import net.md_5.bungee.api.plugin.Listener;

public class Test extends PatchAddon implements Listener {

    @Override
    public void onEnable() {
        ZthPatchesBungee.getInstance().getLogger().info("HelloWorld!");
    }

    @Override
    public void onDisable() {
    }
}
