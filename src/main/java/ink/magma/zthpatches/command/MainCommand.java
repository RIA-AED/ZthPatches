package ink.magma.zthpatches.command;

import ink.magma.zthpatches.ZthPatches;
import org.bukkit.command.CommandSender;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;

@Command({"zth-patches", "zp"})
public class MainCommand {
    @Subcommand("reload-config")
    public void reloadConfig(CommandSender sender) {
        ZthPatches instance = ZthPatches.getInstance();
        instance.createConfig();
        sender.sendMessage("已重载配置文件。");
    }


}
