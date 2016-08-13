package com.jkb.model.dataSource.first.firstlogic.remote;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import com.jkb.model.dataSource.first.firstlogic.FirstDataSource;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.Date;

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
    public void getStatusData(StatusDataCallback callback) {

    }

    @Override
    public void cacheStatus(String version, boolean isLogined, int userId, Date date) {

    }

    @Override
    public void getUsersData(@NonNull int userId, UsersDataCallback callback) {

    }

    @Override
    public void getUserAuthData(@NonNull int userId, UserAuthsDataCallback callback) {

    }

    @Override
    public String getCurrentVersion() {
        return null;
    }

    @Override
    public void loadHeadImgByUrl(final String url, @NonNull final BitmapDataCallback callback) {
        if (StringUtils.isEmpty(url)) {
            callback.onDataNotAvailable(url);
            return;
        }
        ImageLoaderFactory.getInstance().loadImage(url, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                callback.onDataNotAvailable(url);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                callback.onDataNotAvailable(url);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                callback.onBitmapDataLoaded(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                callback.onDataNotAvailable(url);
            }
        });
    }

    @Override
    public void loadHeadImgByLocalPath(String path, BitmapDataCallback callback) {

    }
}
