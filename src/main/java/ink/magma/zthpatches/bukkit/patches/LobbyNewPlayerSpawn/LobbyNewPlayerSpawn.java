package ink.magma.zthpatches.bukkit.patches.LobbyNewPlayerSpawn;

import ink.magma.zthpatches.PatchAddon;
import ink.magma.zthpatches.bukkit.ZthPatchesBukkit;
import ink.magma.zthpatches.utils.GlobalUtils;
import net.william278.huskhomes.api.HuskHomesAPI;
import net.william278.huskhomes.position.Warp;
import net.william278.huskhomes.user.OnlineUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class LobbyNewPlayerSpawn extends PatchAddon implements Listener {
    private HuskHomesAPI huskHomesAPI;
    public static LobbyNewPlayerSpawn instance;

    @Override
    public void onEnable() {
        if (!GlobalUtils.checkInServer("lobby")) return;
        instance = this;

        Bukkit.getPluginManager().registerEvents(this, ZthPatchesBukkit.getInstance());

        huskHomesAPI = HuskHomesAPI.getInstance();
    }

    public boolean newPlayerHandle(Player player) {
        CompletableFuture<Optional<Warp>> getWarp;
        if (!player.hasPermission("zth.rules.agreed")) {
            getWarp = huskHomesAPI.getWarp(GlobalUtils.getGlobalSettings().lobbyNewPlayerSpawnWarpName);

            // 进行相应目标的传送
            getWarp.thenAccept((result) -> result.ifPresent(warp -> {
                OnlineUser onlineUser = huskHomesAPI.adaptUser(player);
                huskHomesAPI.teleportBuilder(onlineUser)
                        .target(result.get())
                        .toTimedTeleport()
                        .execute();
            }));

            player.setFlying(false);
            return true;
        }
        return false;
    }


    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent event) {
        newPlayerHandle(event.getPlayer());
    }

    @EventHandler
    public void onPlayerSpawn(PlayerSpawnLocationEvent event) {
        newPlayerHandle(event.getPlayer());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        newPlayerHandle(event.getPlayer());
    }

    @EventHandler
    public void onPlayerSpawnCommand(PlayerCommandPreprocessEvent event) {
        // 忽略非 /spawn
        if (!List.of("/spawn", "/cmi spawn").contains(event.getMessage())) return;
        boolean handed = newPlayerHandle(event.getPlayer());
        if (handed) event.setCancelled(true);
    }

    @Override
    public void onDisable() {

    }
}
