package ink.magma.zthpatches.states;

import ink.magma.zthpatches.states.settings.GlobalSettingInitializer;

import java.util.Map;

public class RedisPool {
    public RedisPool() {
        Map<String, String> redisConfig = GlobalSettingInitializer.getGlobalSettings().redisConfig;
        String host = redisConfig.get("host");
        String port = redisConfig.get("port");
    }
}
