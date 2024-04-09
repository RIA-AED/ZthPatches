package ink.magma.zthpatches.utils.AdventurePlatformSender;

import ink.magma.zthpatches.bukkit.ZthPatchesBukkit;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.command.CommandActor;

public class BukkitAdventureSender {
    public static Audience getAudience(CommandActor actor) {
        CommandSender sender = actor.as(BukkitCommandActor.class).getSender();
        return ZthPatchesBukkit.getInstance().adventure().sender(sender);
    }
}
