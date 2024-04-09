package ink.magma.zthpatches.bungee;

import ink.magma.zthpatches.PlatformHandler;
import ink.magma.zthpatches.bungee.patches.Test.Test;
import ink.magma.zthpatches.command.InGameEditor.InGameEditorBungee;
import ink.magma.zthpatches.command.Main.MainBungee;
import ink.magma.zthpatches.states.settings.GlobalSettingInitializer;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import revxrsal.commands.bungee.BungeeCommandHandler;

public class ZthPatchesBungee extends Plugin {
    private static ZthPatchesBungee instance;
    public BungeeCommandHandler handler;
    private BungeeAudiences adventure;

    @Override
    public void onEnable() {
        instance = this;
        // 设定平台类型
        PlatformHandler.setPlatformType(PlatformHandler.PlatformType.Bungee);
        // 创建 adventure
        this.adventure = BungeeAudiences.create(this);
        // 初始化配置文件
        GlobalSettingInitializer.createConfig();
        // lamp 指令处理器
        handler = BungeeCommandHandler.create(this);
        // 主指令
        handler.register(new MainBungee());
        // 游戏内编辑器指令
        InGameEditorBungee inGameEditor = new InGameEditorBungee();
        inGameEditor.registerAutoCompleter(handler);
        handler.register(inGameEditor);

        // patches
        new Test().onEnable();
    }

    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }

    public @NonNull BungeeAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Cannot retrieve audience provider while plugin is not enabled");
        }
        return this.adventure;
    }

    public static ZthPatchesBungee getInstance() {
        return instance;
    }
}
