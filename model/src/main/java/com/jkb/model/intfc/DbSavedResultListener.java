package com.jkb.model.intfc;

/**
 * 存储数据库操作的返回结果类
 * Created by JustKiddingBaby on 2016/8/7.
 */

public interface DbSavedResultListener<T> {

    /**
     * 数据存储成功调用的方法
     */
    void onSuccess(T info);

    /**
     * 数据存储失败调用的回调方法
     */
    void onFailed(T info);
}
