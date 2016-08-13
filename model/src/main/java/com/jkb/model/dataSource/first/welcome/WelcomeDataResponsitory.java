package com.jkb.model.dataSource.first.welcome;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Welcome页面用到的数据仓库类
 * Created by JustKiddingBaby on 2016/7/21.
 */
public class WelcomeDataResponsitory implements WelcomeDataSource {

    private static WelcomeDataResponsitory INSTANCE = null;

    private final WelcomeDataSource welcomeLocalDataSource;
    private final WelcomeDataSource welcomeRemoteDataSource;

    //缓存
    WelcomeData cacheImage;

    private WelcomeDataResponsitory(@NonNull WelcomeDataSource welcomeLocalDataSource,
                                    @NonNull WelcomeDataSource welcomeRemoteDataSource) {
        this.welcomeLocalDataSource = checkNotNull(welcomeLocalDataSource);
        this.welcomeRemoteDataSource = checkNotNull(welcomeRemoteDataSource);
    }

    /**
     * 获取单利实例对象
     *
     * @param welcomeLocalDataSource
     * @param welcomeRemoteDataSource
     * @return
     */
    public static WelcomeDataResponsitory getInstance(@NonNull WelcomeDataSource welcomeLocalDataSource,
                                               @NonNull WelcomeDataSource welcomeRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new WelcomeDataResponsitory(welcomeLocalDataSource, welcomeRemoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getWelcomeBackground(@NonNull GetBitmapCallBack callBack) {
        //判断使用哪种类型得到实例对象
        checkNotNull(callBack);
        if (cacheImage != null) {
            callBack.onBitmapLoaded(cacheImage);
            return;
        }
        //判断是否有缓存本地的图片
        welcomeLocalDataSource.getWelcomeBackground(callBack);
    }
}
