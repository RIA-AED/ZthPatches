package ink.magma.zthpatches.utils;

import ink.magma.zthpatches.PlatformHandler;
import ink.magma.zthpatches.utils.AdventurePlatformSender.BukkitAdventureSender;
import ink.magma.zthpatches.utils.AdventurePlatformSender.BungeeAdventureSender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.command.CommandActor;

public class MessageUtils {
    public static void sendMessage(CommandActor actor, ComponentLike message) {
        sendMessage(getAudience(actor), message);
    }

    public static void sendMessage(Audience audience, ComponentLike message) {
        audience.sendMessage(message);
    }

    public static void sendPlainMessage(CommandActor actor, String message) {
        sendPlainMessage(getAudience(actor), message);
    }

    public static void sendPlainMessage(Audience audience, String message) {
        audience.sendMessage(Component.text(message));
    }

    public static void sendMiniMessage(CommandActor actor, String message) {
        sendMiniMessage(getAudience(actor), message);
    }

    public static void sendMiniMessage(Audience audience, String message) {
        Component deserialize = MiniMessage.miniMessage().deserialize(message);
        audience.sendMessage(deserialize);
    }

    public static void sendLegacyMessage(CommandActor actor, String message) {
        sendLegacyMessage(getAudience(actor), message);
    }

    public static void sendLegacyMessage(Audience audience, String message) {
        TextComponent deserialized = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
        audience.sendMessage(deserialized);
    }

    /**
     * 将 Lamp 的 CommandActor 或其子类转换为 Audience
     */
    public static Audience getAudience(CommandActor actor) {
        PlatformHandler.PlatformType platformType = PlatformHandler.getPlatformType();

        if (platformType.equals(PlatformHandler.PlatformType.Bukkit)) {
            if (actor instanceof BukkitCommandActor bukkitActor) {
                return bukkitActor.audience();
            }
            return BukkitAdventureSender.getAudience(actor);
        } else {
            return BungeeAdventureSender.getAudience(actor);
        }
    }
}
