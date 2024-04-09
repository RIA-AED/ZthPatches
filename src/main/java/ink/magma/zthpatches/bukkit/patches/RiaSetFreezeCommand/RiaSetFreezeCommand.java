package ink.magma.zthpatches.bukkit.patches.RiaSetFreezeCommand;

import ink.magma.zthpatches.bukkit.ZthPatchesBukkit;
import ink.magma.zthpatches.PatchAddon;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.text.MessageFormat;

/**
 * 这是一个历史遗留命令，因为此命令可能已经写入 TtemTag 或 sbp.
 * 来自 RiaTools
 */
public class RiaSetFreezeCommand extends PatchAddon {

    @Command("ria setfreeze")
    @CommandPermission("riatools.admin")
    public void setFreezeCmd(CommandSender sender, @Named("目标玩家") Player targetPlayer, @Named("冷冻 Tick(s)") int ticks) {
        targetPlayer.setFreezeTicks(ticks);
        sender.sendMessage(MessageFormat.format(
                "已设置 {0} setFreezeTicks({1})", targetPlayer.getName(), ticks
        ));
    }

    @Override
    public void onEnable() {
        ZthPatchesBukkit.getInstance().handler.register(new RiaSetFreezeCommand());
        ZthPatchesBukkit.getInstance().handler.registerBrigadier();
    }

    @Override
    public void onDisable() {
        ZthPatchesBukkit.getInstance().handler.unregister("ria setfreeze");
    }
}
