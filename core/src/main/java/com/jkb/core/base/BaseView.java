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

    /**
     * 显示加载的loading效果
     *
     * @param value
     */
    void showLoading(String value);

    /**
     * 取消loading加载显示效果
     */
    void dismissLoading();

    /**
     * 显示返回的结果
     *
     * @param value
     */
    void showReqResult(String value);

    /**
     * 是否被销毁
     *
     * @return
     */
    boolean isActive();
}
