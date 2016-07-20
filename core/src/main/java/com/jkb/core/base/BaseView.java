package com.jkb.core.base;

/**
 * 所有的View类的基类
 * View类：实现V层UI的操作
 * Created by JustKiddingBaby on 2016/7/20.
 */
public interface BaseView<T> {

    /**
     * 给View注入Presenter对象
     * 该操作主要在Presenter具体实现类内部完成
     *
     * @param presenter
     */
    void setPresenter(T presenter);
}
