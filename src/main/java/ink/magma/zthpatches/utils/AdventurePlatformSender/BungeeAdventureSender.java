package ink.magma.zthpatches.utils.AdventurePlatformSender;

import ink.magma.zthpatches.bungee.ZthPatchesBungee;
import net.kyori.adventure.audience.Audience;
import net.md_5.bungee.api.CommandSender;
import revxrsal.commands.bungee.BungeeCommandActor;
import revxrsal.commands.command.CommandActor;

public class BungeeAdventureSender {
    public static Audience getAudience(CommandActor actor) {
        CommandSender sender = actor.as(BungeeCommandActor.class).getSender();
        return ZthPatchesBungee.getInstance().adventure().sender(sender);
    }
}
