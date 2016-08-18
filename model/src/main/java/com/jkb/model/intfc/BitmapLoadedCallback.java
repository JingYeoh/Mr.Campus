package com.jkb.model.intfc;

import android.graphics.Bitmap;

/**
 * 网络图片加载为bitmap的回调
 * Created by JustKiddingBaby on 2016/8/18.
 */
public interface BitmapLoadedCallback {

    /**
     * 数据加载成功
     */
    void onBitmapDataLoaded(Bitmap bitmap);

    /**
     * 数据加载失败
     */
    void onDataNotAvailable(String url);
}
