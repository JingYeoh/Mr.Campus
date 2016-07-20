package com.jkb.core.base;

/**
 * 所有Presenter类的基类接口
 * Presenter类：实现V层逻辑的封装以及M层数据的调度
 * Created by JustKiddingBaby on 2016/7/20.
 */
public interface BasePresenter {

    /**
     * 开始
     * 可以在此进行数据的初始化等操作
     * 调用时机：在View层的onResume()的方法中调用
     */
    void start();
}
