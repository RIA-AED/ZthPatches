package ink.magma.zthpatches;

// 请注意，此类中不能含有来自各个平台的类的 import，否则会导致类不存在错误而无法启动
public class PlatformHandler {
    private static PlatformType platformType;

    public static PlatformType getPlatformType() {
        return platformType;
    }

    public static void setPlatformType(PlatformType platformType) {
        PlatformHandler.platformType = platformType;
    }

    public enum PlatformType {
        Bukkit,
        Bungee
    }
}
