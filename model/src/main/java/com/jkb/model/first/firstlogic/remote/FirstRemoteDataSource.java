package com.jkb.model.first.firstlogic.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.model.first.firstlogic.FirstDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 远程加载的第一次数据来源类
 * Created by JustKiddingBaby on 2016/7/22.
 */
public class FirstRemoteDataSource implements FirstDataSource {

    private static FirstRemoteDataSource INSTANCE;

    private Context context;


    /**
     * 获取WelcomeRemoteDataSource的单例类对象
     *
     * @return
     */
    public static FirstRemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FirstRemoteDataSource(context);
        }
        return INSTANCE;
    }

    private FirstRemoteDataSource(Context context) {
        this.context = checkNotNull(context);
    }

    @Override
    public void getFirstData(@NonNull FirstDataCallBack callBack) {
        //得到本地数据
        //得到版本号码
        getCachedVersion();
    }

    /**
     * 得到缓存的版本号
     *
     * @return
     */
    private String getCachedVersion() {

        return null;
    }
}
