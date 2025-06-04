package ink.magma.zthpatches.bukkit;

import ink.magma.zthpatches.PlatformHandler;
import ink.magma.zthpatches.bukkit.patches.ComposterMoreRecipe.ComposterMoreRecipe;
import ink.magma.zthpatches.bukkit.patches.LobbyNewPlayerSpawn.LobbyNewPlayerSpawn;
import ink.magma.zthpatches.bukkit.patches.RiaSetFreezeCommand.RiaSetFreezeCommand;
import ink.magma.zthpatches.command.InGameEditor.InGameEditorBukkit;
import ink.magma.zthpatches.command.Main.MainBukkit;
import ink.magma.zthpatches.states.settings.GlobalSettingInitializer;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public final class ZthPatchesBukkit extends JavaPlugin {
    private static ZthPatchesBukkit instance;
    public BukkitCommandHandler handler;
    private BukkitAudiences adventure;

    @Override
    public void onEnable() {
        instance = this;
        // 设定平台类型
        PlatformHandler.setPlatformType(PlatformHandler.PlatformType.Bukkit);
        // 创建 adventure
        this.adventure = BukkitAudiences.create(this);
        // 初始化配置文件
        GlobalSettingInitializer.createConfig();
        // lamp 指令处理器
        handler = BukkitCommandHandler.create(this);
        // 主指令
        handler.register(new MainBukkit());
        // 游戏内编辑器指令
        InGameEditorBukkit inGameEditor = new InGameEditorBukkit();
        inGameEditor.registerAutoCompleter(handler);
        handler.register(inGameEditor);
        // bukkit 平台注册 brigadier 以便支持更好的指令补全
        handler.registerBrigadier();
        // 提供 BukkitCommandActor.audience() 支持
        handler.enableAdventure(adventure);

        // patches
//        new DragonEggAddon().onEnable();
        new ComposterMoreRecipe().onEnable();
        new RiaSetFreezeCommand().onEnable();
        new LobbyNewPlayerSpawn().onEnable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }

    public @NotNull BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    public static ZthPatchesBukkit getInstance() {
        return instance;
    }
}
