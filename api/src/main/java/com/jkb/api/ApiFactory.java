package com.jkb.api;

/**
 * 工厂方法模式：Api层的公用接口
 * Created by JustKiddingBaby on 2016/7/31.
 */
public interface ApiFactory {
    /**
     * 得到Api对象
     *
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T createApi(Class<T> clz);
}
