package ink.magma.zthpatches.states.settings;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class GlobalSettingsController {
    /**
     * 获取所有可写字段
     */
    public static Set<Field> getModifiableFields(Class<?> type) {
        HashSet<Field> writableFieldNames = new HashSet<>();

        // 所有字段
        Field[] fields = type.getFields();
        for (Field field : fields) {
            // 如果此字段是公开可写
            if (Modifier.isPublic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                writableFieldNames.add(field);
            }
        }
        // 返回所有公开可写字段
        return writableFieldNames;
    }

    /**
     * 获取所有可写字段的名称 Set，用于自动补全
     */
    public static Set<String> getModifiableFieldNames(Class<?> type) {
        var result = new HashSet<String>();

        for (Field field : getModifiableFields(type)) {
            result.add(field.getName());
        }

        return result;
    }

    /**
     * 读取字段值。并返回 String，此方法主要用于展示使用。
     *
     * @return Object.toString() 后的结果
     */
    public static @NotNull String getFieldValueString(Class<?> type, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        try {
            Field field = type.getField(fieldName);
            Object object = field.get(GlobalSettingInitializer.getGlobalSettings());
            return object.toString();
        } catch (NullPointerException e) {
            return "null";
        }
    }

    /**
     * 根据类型，获取可能的补全
     */
    public static Set<String> getFieldSuggestions(Class<?> fieldType) {
        if (fieldType == String.class) {
            return Set.of();
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return Set.of("true", "false");
        } else if (fieldType == Integer.class || fieldType == int.class) {
            return Set.of();
        } else if (fieldType == Float.class || fieldType == float.class) {
            return Set.of();
        } else if (fieldType == Double.class || fieldType == double.class) {
            return Set.of();
        } else {
            return Set.of();
        }
    }

    /**
     * 写入可写字段
     *
     * @param instance  全局配置实例
     * @param fieldName 字段名称
     * @param value     要写入的对象的 String 值, 目前这个方法支持 String、Boolean、Integer、Float、Double 及其基类，使用如 Integer.parseInt() 等方法进行解析。若要写入的字段类型不是以上支持的类型，会掷出 NoSupportTypeException
     */
    public static void setModifiableFieldsByString(GlobalSettings instance, String fieldName, String value) throws NoSuchFieldException, IllegalAccessException, NoSupportTypeException, IllegalArgumentException {
        Field field = GlobalSettings.class.getField(fieldName);
        Class<?> fieldType = field.getType();

        if (fieldType == String.class) {
            field.set(instance, value);
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            field.set(instance, Boolean.parseBoolean(value));
        } else if (fieldType == Integer.class || fieldType == int.class) {
            field.set(instance, Integer.parseInt(value));
        } else if (fieldType == Float.class || fieldType == float.class) {
            field.set(instance, Float.parseFloat(value));
        } else if (fieldType == Double.class || fieldType == double.class) {
            field.set(instance, Double.parseDouble(value));
        } else {
            throw new NoSupportTypeException();
        }

        // TODO: Array, Lists & Maps
    }

    public static class NoSupportTypeException extends Exception {
    }
}
