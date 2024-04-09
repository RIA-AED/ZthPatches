package ink.magma.zthpatches.bukkit.patches.ComposterMoreRecipe;

import ink.magma.zthpatches.PatchAddon;
import ink.magma.zthpatches.bukkit.ZthPatchesBukkit;
import ink.magma.zthpatches.states.settings.GlobalSettingInitializer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ComposterMoreRecipe extends PatchAddon implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, ZthPatchesBukkit.getInstance());
    }

    @Override
    public void onDisable() {
    }

    public static final List<Material> materialList = List.of(Material.ROTTEN_FLESH, Material.SPIDER_EYE);


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!GlobalSettingInitializer.getGlobalSettings().composterMoreRecipe ||
            event.getClickedBlock() == null ||
            event.getItem() == null ||
            event.getHand() != EquipmentSlot.HAND ||
            event.getClickedBlock().getType() != Material.COMPOSTER ||
            event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack handItem = event.getItem();
        Block clicked = event.getClickedBlock();

        // 如果玩家主手对着堆肥桶使用腐肉或蜘蛛眼
        if (materialList.contains(handItem.getType())) {
            DoComposterCompostResult compostResult = doComposterCompost(clicked);
            if (player.getGameMode() != GameMode.CREATIVE) { // 如果玩家不是创造模式
                // 如果堆肥结果需要消耗物品
                if (List.of(DoComposterCompostResult.SUCCESS, DoComposterCompostResult.FALL).contains(compostResult)) {
                    handItem.setAmount(handItem.getAmount() - 1); // 拿走玩家手里的东西
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onHopperPutItem(InventoryMoveItemEvent event) {
        if (!GlobalSettingInitializer.getGlobalSettings().composterMoreRecipe) return;
        // 判断目标是否是堆肥桶
        if (!event.getDestination().getType().equals(InventoryType.COMPOSTER)) return;
        ItemStack item = event.getItem();
        // 判断材料是否正确
        if (!materialList.contains(item.getType())) return;
        Location location = event.getDestination().getLocation();
        if (location == null) return;
        Block block = location.getBlock();
        if (!block.getType().equals(Material.COMPOSTER)) return;
        DoComposterCompostResult compostResult = doComposterCompost(block);
        if (List.of(DoComposterCompostResult.SUCCESS, DoComposterCompostResult.FALL).contains(compostResult)) {
            item.setAmount(item.getAmount() - 1);
        }
    }

    private static DoComposterCompostResult doComposterCompost(Block composterBlock) {
        Levelled clickedBlockLevel = (Levelled) composterBlock.getBlockData();
        World world = composterBlock.getWorld();

        if (clickedBlockLevel.getLevel() < clickedBlockLevel.getMaximumLevel()) { // 如果桶没满
            if (Math.random() <= 0.65) { // 65% 概率
                clickedBlockLevel.setLevel(clickedBlockLevel.getLevel() + 1);
                composterBlock.setBlockData(clickedBlockLevel);
                world.playSound(composterBlock.getLocation(), Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1, 1);
                world.spawnParticle(Particle.COMPOSTER, composterBlock.getLocation().add(0.5, 1, 0.5), 10, 0.2, 0.2, 0.2);
                return DoComposterCompostResult.SUCCESS;
            }
            world.spawnParticle(Particle.COMPOSTER, composterBlock.getLocation().add(0.5, 1, 0.5), 10, 0.2, 0.2, 0.2);
            return DoComposterCompostResult.FALL;
        } else { // 桶满
            world.playSound(composterBlock.getLocation(), Sound.BLOCK_COMPOSTER_FILL, 1, 1);
            return DoComposterCompostResult.FULL;
        }
    }

    private enum DoComposterCompostResult {
        SUCCESS,
        FALL,
        FULL
    }
}