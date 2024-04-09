package ink.magma.zthpatches.states.settings;

import net.william278.annotaml.YamlComment;
import net.william278.annotaml.YamlFile;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * <h2>向开发者的备注</h2>
 * <p>
 * 为本配置文件新增字段时，请尽量让你修改的主体在字段名最开始。
 * <p>
 * 例：我为龙蛋落入虚空增加了警报，那么我的键名开头则是 dragonEgg。这样在游戏内想要开关某个功能时，自动补全将方便更快找到对应配置项。
 * <p>
 * 请留意，游戏内编辑器目前不支持 Map、Array、List 等类型。
 */

@YamlFile(header = """
        ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
        ┃          ZthPatches          ┃
        ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
        ┣╸这是 Zth 全新的补丁系统配置文件，迁移服务器前，请留意调整此配置文件。
        ┗╸本配置的许多选项也可以在游戏内直接更改。
        """)
public class GlobalSettings {
    @YamlComment("""
            
            ━━━━━━━━━━━━━ 全局配置项 ━━━━━━━━━━━━
            全局配置项是插件运行所必须的配置项。
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            
            serverId 应当与群组中此服务器的 Id 相同，且大小写敏感。必须配置此项!
            """)
    @Nullable
    public String serverId = null;
    @YamlComment("redis 配置。用于存储全局的一些变量状态。")
    public Map<String, String> redisConfig = Map.of(
            "host", "direct.kemijoki.sakura-realm.studio",
            "port", "26720"
    );

    @YamlComment("""
            
            ━━━━━━━━━━━━━━━ Bukkit 侧配置项 ━━━━━━━━━━━━━━━
            仅在 Bukkit 服务端上生效。非 Bukkit 本节内容无用。
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            
            """)
    public Boolean dragonEggFallVoidAlert = true;
    public Boolean dragonEggEndWorldPickUpMessage = true;
    public Boolean composterMoreRecipe = true;
    public String lobbyNewPlayerSpawnWarpName = "lobby-new-player";


    @YamlComment("""
            
            ━━━━━━━━━━━━━━━ Bungee 侧配置项 ━━━━━━━━━━━━━━━
            仅在 Bungee 服务端上生效。非 Bungee 本节内容无用。
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            
            """)
    public String testBungeeConfig = "";


    public GlobalSettings() {
    }
}
