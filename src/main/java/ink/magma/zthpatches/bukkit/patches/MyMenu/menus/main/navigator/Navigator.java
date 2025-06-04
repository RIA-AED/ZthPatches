package ink.magma.zthpatches.bukkit.patches.MyMenu.menus.main.navigator;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import ink.magma.zthpatches.utils.MenuUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

import static ink.magma.zthpatches.utils.MessageUtils.mini;

public class Navigator {
    private MainMenuPage activePage = MainMenuPage.Main;
    private final OutlinePane pane;
    private Consumer<MainMenuPage> onChangeCallback;
    private final Player player;

    public Navigator(Player player) {
        this.player = player;
        pane = new OutlinePane(1, 0, 5, 1);
        pane.setOnClick(e -> e.setCancelled(true));
        render();
    }

    private void render() {
        var mainButton = new GuiItem(getMainButton(), e -> {
            setActivePage(MainMenuPage.Main);
        });
        var personalButton = new GuiItem(getPersonalButton(player), e -> {
            setActivePage(MainMenuPage.Personal);
        });
        var functionsButton = new GuiItem(getFunctionsButton(), e -> {
            setActivePage(MainMenuPage.Functions);
        });

        if (activePage == MainMenuPage.Main) {
            MenuUtils.setShine(mainButton);
        } else if (activePage == MainMenuPage.Personal) {
            MenuUtils.setShine(personalButton);
        } else if (activePage == MainMenuPage.Functions) {
            MenuUtils.setShine(functionsButton);
        }

        pane.clear();
        pane.addItem(mainButton);
        pane.addItem(personalButton);
        pane.addItem(functionsButton);
    }


    public void setActivePage(MainMenuPage activePage) {
        this.activePage = activePage;
        render();
    }

    private ItemStack getMainButton() {
        ItemStack itemStack = new ItemStack(Material.CHEST);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(mini.deserialize("<white>个人菜单"));
        itemMeta.lore(List.of(
                mini.deserialize("<gray>个人菜单的主页,"),
                mini.deserialize("<gray>常用的功能放在这里."),
                mini.deserialize(""),
                mini.deserialize("<white>点击打开"),
                mini.deserialize("<dark_gray>/cd 或 /gui 或 /help 或 /菜单\n"),
                mini.deserialize("<dark_gray>使用 潜行 + 副手 也可以打开.")
        ));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private ItemStack getPersonalButton(@Nullable Player player) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        itemMeta.displayName(mini.deserialize("<white>个人设置"));
        itemMeta.lore(List.of(
                mini.deserialize("<gray>个性你的莉亚."),
                mini.deserialize(""),
                mini.deserialize("<white>点击打开")
        ));
        if (player != null) itemMeta.setOwningPlayer(player);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private ItemStack getFunctionsButton() {
        ItemStack itemStack = new ItemStack(Material.FIREWORK_ROCKET);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(mini.deserialize("<white>功能和娱乐"));
        itemMeta.lore(List.of(
                mini.deserialize("<gray>一些好玩的和有用的 ♪"),
                mini.deserialize(""),
                mini.deserialize("<white>点击打开")
        ));

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public MainMenuPage getActivePage() {
        return activePage;
    }

    public OutlinePane getPane() {
        return pane;
    }

    public void onChange(Consumer<MainMenuPage> onChangeCallback) {
        this.onChangeCallback = onChangeCallback;
    }


    public enum MainMenuPage {
        Main,
        Personal,
        Functions,
        NoSelected
    }
}
