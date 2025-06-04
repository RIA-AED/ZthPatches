package ink.magma.zthpatches.utils;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuUtils {
    public static void setShine(GuiItem mainButton) {
        ItemStack itemStack = mainButton.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
    }
}
