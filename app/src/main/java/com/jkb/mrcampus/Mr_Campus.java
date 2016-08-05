package com.jkb.mrcampus;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.mrcampus.singleton.ActivityManager;

import cn.sharesdk.framework.ShareSDK;

/**
 * 该APP的Application类
 * Created by JustKiddingBaby on 2016/7/20.
 */
public class Mr_Campus extends Application {

    private ActivityManager activityManager;
    private static ImageLoaderFactory imageLoaderFactory = ImageLoaderFactory.getInstance();
    private static ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
    //shareSDK的appkey
    private static final String SHARE_SDK_APP_KEY = "15b05f956cc6c";

    @Override
    public void onCreate() {
        super.onCreate();

        initSDK();
        initSingleton();
    }

    /**
     * 初始化单例的实例类
     */
    private void initSingleton() {
        //初始化Activity管理者
        activityManager = ActivityManager.getInstance();
        //初始化图片加载器配置
        imageLoaderFactory.setApplicationContext(getApplicationContext());
        imageLoaderFactory.create();
        //初始化API层的工厂类
        apiFactory = ApiFactoryImpl.newInstance();
    }

    /**
     * 初始化SDK
     */
    private void initSDK() {
        //初始化百度地图
        SDKInitializer.initialize(getApplicationContext());
        //初始化ShareSDK
        ShareSDK.initSDK(getApplicationContext(), SHARE_SDK_APP_KEY);
    }

    /**
     * 返回图片加载的工厂类
     *
     * @return
     */
    public static ImageLoaderFactory getImageLoaderFactory() {
        return imageLoaderFactory;
    }

    /**
     * 返回API层的工厂类
     *
     * @return
     */
    public static ApiFactoryImpl getApiFactory() {
        return apiFactory;
    }
}
