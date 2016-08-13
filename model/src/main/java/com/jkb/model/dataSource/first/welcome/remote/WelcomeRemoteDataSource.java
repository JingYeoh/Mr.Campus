package com.jkb.model.dataSource.first.welcome.remote;

import android.support.annotation.NonNull;

import com.jkb.model.dataSource.first.welcome.WelcomeData;
import com.jkb.model.dataSource.first.welcome.WelcomeDataSource;

/**
 * Welcome页面中用到的数据远程来源
 * Created by JustKiddingBaby on 2016/7/21.
 */
public class WelcomeRemoteDataSource implements WelcomeDataSource {

    private static WelcomeRemoteDataSource INSTANCE;

    private final static WelcomeData FIRST_BACKGROUND_IMAGE;

    static {
        FIRST_BACKGROUND_IMAGE = new WelcomeData();
    }

    /**
     * 获取WelcomeRemoteDataSource的单例类对象
     *
     * @return
     */
    public static WelcomeRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WelcomeRemoteDataSource();
        }
        return INSTANCE;
    }

    private WelcomeRemoteDataSource() {
    }


    @Override
    public void getWelcomeBackground(@NonNull GetBitmapCallBack callBack) {
        //加载缓存到本地的文件数据
    }
}
