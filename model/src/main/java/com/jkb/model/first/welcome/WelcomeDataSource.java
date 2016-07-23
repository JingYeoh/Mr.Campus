package com.jkb.model.first.welcome;

import android.support.annotation.NonNull;

import com.jkb.model.base.BaseDataSource;

/**
 * Welcome页面数据处理接口
 * Created by JustKiddingBaby on 2016/7/21.
 */
public interface WelcomeDataSource extends BaseDataSource {

    /**
     * 得到图像Bitmap位图的回调接口
     */
    interface GetBitmapCallBack {
        /**
         * 数据加载成功
         *
         * @param welcomeData
         */
        void onBitmapLoaded(WelcomeData welcomeData);

        /**
         * 数据获得失败
         */
        void onDataNotAvailable();
    }

    /**
     * 获取Bitmap位图对象
     *
     * @param callBack
     */
    void getWelcomeBackground(@NonNull GetBitmapCallBack callBack);
}
