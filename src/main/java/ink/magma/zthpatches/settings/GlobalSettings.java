package ink.magma.zthpatches.settings;

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
        这是 Zth 全新的补丁系统配置文件，迁移服务器前，请留意调整此配置文件。
        本配置的许多选项也可以在游戏内直接更改。""")
public class GlobalSettings {
    @Nullable
    @YamlComment("必须配置此项! serverId 应当与群组中此服务器的 Id 相同，且大小写敏感。")
    public String serverId = null;

    public Map<String, String> redisConfig = Map.of(
            "host", "direct.kemijoki.sakura-realm.studio",
            "port", "26720"
    );

    @YamlComment("龙蛋落入虚空警报")
    public Boolean dragonEggFallVoidAlert = true;
    @YamlComment("末地龙蛋拾取提示")
    public Boolean dragonEggEndWorldPickUpMessage = true;

    public Boolean composterMoreRecipe = true;

    public GlobalSettings() {
    }
}
