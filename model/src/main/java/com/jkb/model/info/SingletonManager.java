package com.jkb.model.info;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理单例模式的事物类
 */
public class SingletonManager {

    private static Map<String, Object> objMap = new HashMap<String, Object>();

    private SingletonManager() {
    }

    private static SingletonManager INSTANCE = null;

    public static SingletonManager newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonManager();
        }
        return INSTANCE;
    }


    /**
     * 添加单例对象
     *
     * @param key      键
     * @param instence 对象
     */
    public synchronized static void registerService(String key, Object instence) {
        if (!objMap.containsKey(key)) {
            objMap.put(key, instence);
        }
    }

    /**
     * 得到单例对象
     *
     * @param key 键
     * @return 单例对象
     */
    public static Object getService(String key) {
        return objMap.get(key);
    }

    /**
     * 移除单例对象
     *
     * @param key 键
     */
    public static void removeService(String key) {
        if (objMap.containsKey(key)) {
            objMap.remove(key);
        }
    }
}
