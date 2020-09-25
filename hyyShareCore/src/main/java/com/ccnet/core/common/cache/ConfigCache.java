package com.ccnet.core.common.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016-04-11.
 */
public final class ConfigCache {

    /**
     * 系统配置缓存（加载config.properties和t_setting中的配置，其中t_setting 中的配置会覆盖config.properties）
     */
    private static Map<String, Object> configCacheMap = new ConcurrentHashMap<>();

    /**
     * 系统配置缓存（config.properties中不能被替换的配置，以前缀FIXED#开头的标识）
     */
    private static Map<String, Object> fixedConfigCacheMap = new ConcurrentHashMap<>();

    /**
     * 配置项缓存(t_item中的配置)
     */
    private static Map<String, List<String>> itemCacheMap = new ConcurrentHashMap<>();

    private static final String FIXED_PERFIX = "FIXED#";

    /**
     * 刷新所有缓存
     *
     * @param configMap
     */
    public static void refresAllConfig(Map<String, Object> configMap) {
        configCacheMap.putAll(configMap);
    }

    /**
     * 刷新缓存
     */
    public static void refresConfig(String key, Object value) {
        if (key != null && value != null) {

            if (key.startsWith(FIXED_PERFIX)) {
                key = key.replace(FIXED_PERFIX, "");
                fixedConfigCacheMap.put(key, value);
            }

            configCacheMap.put(key, value);
            System.out.println("key:[" + key + "] value:[" + value + "]");
        }
    }

    public static void putFixedConfig(String key, Object value) {
        if (key != null && value != null) {
            fixedConfigCacheMap.put(key, value);
        }
    }

    public static String getConfig(String key) {
        return getConfig(key, null);
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public static String getConfig(String key, String defValue) {
        Object fixedObj = fixedConfigCacheMap.get(key);
        Object configValue = configCacheMap.get(key);
        return fixedObj != null ? fixedObj.toString() : (configValue != null ? configValue.toString() : defValue);
    }

    /**
     * 存入缓存
     *
     * @param name
     * @param values
     */
    public static void putItem(String name, List<String> values) {
        if (name != null && values != null) {
            List<String> strings = itemCacheMap.get(name);
            if (strings == null) {
                itemCacheMap.put(name, values);
            } else {
                strings.addAll(values);
                itemCacheMap.put(name, strings);
            }
        }
    }

    /**
     * 获取配置项
     *
     * @param name
     * @return
     */
    public static String[] getItem(String name) {
        List<String> strings = itemCacheMap.get(name);
        return strings != null ? strings.toArray(new String[strings.size()]) : new String[0];
    }

    /**
     * 移除系统设置项
     *
     * @param key
     */
    public static void removeConfig(String key) {
        if (key != null) {
            configCacheMap.remove(key);
        }
    }

    /**
     * 清空所有缓存
     */
    public static void clearConfig() {
        configCacheMap.clear();
    }

    /**
     * 移除配置项
     *
     * @param key
     */
    public static void removeItem(String key) {
        if (key != null) {
            itemCacheMap.remove(key);
        }
    }

    /**
     * 清空所有缓存
     */
    public static void clearItem() {
        itemCacheMap.clear();
    }

}
