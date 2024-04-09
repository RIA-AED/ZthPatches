package ink.magma.zthpatches.command.Main;

import ink.magma.zthpatches.states.settings.GlobalSettingInitializer;
import ink.magma.zthpatches.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import revxrsal.commands.command.CommandActor;

public class Main {
    public void reloadConfig(CommandActor sender) {
        GlobalSettingInitializer.createConfig();
        Component msg = Component.text("已重载配置文件").color(NamedTextColor.GRAY);
        MessageUtils.sendMessage(sender, msg);
    }
}


