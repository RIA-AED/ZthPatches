package ink.magma.zthpatches.states;

import ink.magma.zthpatches.ZthPatches;

import java.util.Map;

public class RedisPool {
    public RedisPool() {
        Map<String, String> redisConfig = ZthPatches.getGlobalSettings().redisConfig;
        String host = redisConfig.get("host");
        String port = redisConfig.get("port");
    }
}
