package ink.magma.zthpatches.utils;

import ink.magma.zthpatches.ZthPatches;
import ink.magma.zthpatches.settings.GlobalSettings;

import java.util.Date;

public class GlobalUtils {
    /**
     * 判断当前插件运行的服务器 ID 是否是传入此方法的服务器 ID
     * 通常用于限制部分功能仅在部分服务器生效
     *
     * @param serverIds 服务器 ID（可为多个）
     * @return 是否位于参数中的服务器
     */
    public static boolean checkInServer(String... serverIds) {
        String serverId = getGlobalSettings().serverId;
        if (serverId == null) return false;
        for (String s : serverIds) {
            if (s.equals(serverId)) return true;
        }
        return false;
    }

    /**
     * 此方法检查传入的时间是否还未达到。
     * 通常用于限时活动，此方法可以放在执行前。如果结果为真，则活动仍未结束
     *
     * @param date 用于比较的时间（如：配置为活动结束的时间）
     * @return 是否还未达到
     */
    public static boolean checkBeforeTime(Date date) {
        Date current = new Date();
        if (current.before(date)) {
            return true;
        } else {
            return false;
        }
    }

    public static GlobalSettings getGlobalSettings() {
        return ZthPatches.getGlobalSettings();
    }
}
