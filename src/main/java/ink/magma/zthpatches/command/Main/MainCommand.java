package ink.magma.zthpatches.command.Main;

import ink.magma.zthpatches.bukkit.settings.GlobalSettingInitializer;
import org.bukkit.command.CommandSender;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;

@Command({"zth-patches", "zp"})
public class MainCommand {
    @Subcommand("reload-config")
    public void reloadConfig(CommandSender sender) {
        GlobalSettingInitializer.createConfig();
        sender.sendMessage("已重载配置文件。");
    }
}
