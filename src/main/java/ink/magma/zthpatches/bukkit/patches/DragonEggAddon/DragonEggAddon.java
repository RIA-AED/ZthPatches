package ink.magma.zthpatches.bukkit.patches.DragonEggAddon;

import ink.magma.zthpatches.bukkit.ZthPatches;
import ink.magma.zthpatches.bukkit.patches.PatchAddon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

public class DragonEggAddon extends PatchAddon implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, ZthPatches.getInstance());
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onPlayerEggPickUp(EntityPickupItemEvent event) {
        if (!ZthPatches.getGlobalSettings().dragonEggEndWorldPickUpMessage) return;
        if (event.getEntity().getWorld().getEnvironment() != World.Environment.THE_END) return;

        ItemStack pickupItem = event.getItem().getItemStack();
        LivingEntity picker = event.getEntity();
        if (pickupItem.getType() == Material.DRAGON_EGG
            && picker.getType() == EntityType.PLAYER
            && picker.getWorld().getEnvironment() == World.Environment.THE_END) {
            Player player = (Player) picker;
            Bukkit.getServer().broadcastMessage(player.getName() + " 拾起了龙蛋!");
        }
    }

    @EventHandler
    public void onEggFallVoid(EntitySpawnEvent event) {
        if (!ZthPatches.getGlobalSettings().dragonEggFallVoidAlert) return;
        if (event.getLocation().getWorld().getEnvironment() != World.Environment.THE_END) return;
        if (event.getEntityType() != EntityType.FALLING_BLOCK) return;

        FallingBlock fallingBlock = (FallingBlock) event.getEntity();
        if (fallingBlock.getBlockData().getMaterial() == Material.DRAGON_EGG) {
            Location blockLocation = fallingBlock.getLocation();
            int x = blockLocation.getBlockX();
            int y = blockLocation.getBlockY();
            int z = blockLocation.getBlockZ();
            for (int i = y; i >= 0; i--) {
                Block thisBlock = new Location(event.getEntity().getWorld(), x, i, z).getBlock();
                if (!thisBlock.isEmpty()) {
                    return;
                }
            }
            Bukkit.broadcastMessage("§4[悲报] §f一颗龙蛋落入了虚空");
        }
    }
}