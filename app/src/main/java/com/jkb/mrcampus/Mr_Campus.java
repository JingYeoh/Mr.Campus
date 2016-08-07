package com.jkb.mrcampus;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.mrcampus.singleton.ActivityManager;

import cn.sharesdk.framework.ShareSDK;
import jkb.mrcampus.db.MrCampusDB;

/**
 * 该APP的Application类
 * Created by JustKiddingBaby on 2016/7/20.
 */
public class Mr_Campus extends Application {

    private ImageLoaderFactory imageLoaderFactory = null;
    private ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
    private MrCampusDB mrCampusDB = null;
    //shareSDK的appkey
    private static final String SHARE_SDK_APP_KEY = "15b05f956cc6c";


    public static Context applicationContext = null;

    @Override
    public void onCreate() {
        super.onCreate();// 多进程导致多次初始化Application,这里只初始化App主进程的Application
        applicationContext = getApplicationContext();
        initInNewThread();
        initDb();
    }

    /**
     * 在新线程中初始化
     */
    private void initInNewThread() {
        initSDK();
        initSingleton();
    }

    /**
     * 初始化数据库
     */
    private void initDb() {
        //初始化数据库的单例类
        mrCampusDB = MrCampusDB.getInstance();
        mrCampusDB.initDb(getApplicationContext());
    }

    /**
     * 初始化单例的实例类
     */
    private void initSingleton() {
        //初始化Activity管理者
        ActivityManager.getInstance();
        //初始化图片加载器配置
        imageLoaderFactory = ImageLoaderFactory.getInstance();
        imageLoaderFactory.setApplicationContext(getApplicationContext());
        imageLoaderFactory.create();
        //初始化API层的工厂类
        apiFactory = ApiFactoryImpl.newInstance();
        //初始化个人数据的单例类
        UserInfoSingleton.getInstance();
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
     * 返回applicationContext实例
     *
     * @return
     */
    public static Context getContext() {
        return applicationContext;
    }
}
