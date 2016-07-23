package com.jkb.model.first.firstlogic.local;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.model.first.firstlogic.FirstData;
import com.jkb.model.first.firstlogic.FirstDataSource;
import com.jkb.model.utils.Config;
import com.jkb.model.utils.SharePreferenceUtils;
import com.jkb.model.utils.SystemUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * FirstActivity控制层要用到的数据的本地来源类
 * Created by JustKiddingBaby on 2016/7/22.
 */
public class FirstLocalDataSource implements FirstDataSource {

    private static FirstLocalDataSource INSTANCE;
    private String TAG = getClass().getSimpleName();

    private Context context;


    /**
     * 获取WelcomeRemoteDataSource的单例类对象
     *
     * @return
     */
    public static FirstLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FirstLocalDataSource(context);
        }
        return INSTANCE;
    }

    private FirstLocalDataSource(Context context) {
        this.context = checkNotNull(context);
    }

    @Override
    public void getFirstData(@NonNull FirstDataCallBack callBack) {

        FirstData firstData = new FirstData();

        String currentVersion = getCurrentVersion();
        String cachedVersion = getCachedVersion();
        Log.d(TAG, "cachedVersion:" + cachedVersion);
        Log.d(TAG, "currentVersion:" + currentVersion);
        //判断是否有版本更新
        if (isUpdated(cachedVersion, currentVersion)) {
            //没有版本更新
            firstData.setIsUpdated(false);
            firstData.setShowPosition(FirstData.ShowPosition.WELCOME);
        } else {
            //缓存当前版本
            cacheCurrentVersion(currentVersion);
            firstData.setIsUpdated(true);
            firstData.setShowPosition(FirstData.ShowPosition.GUIDE);
        }
        //判断是否有广告缓存

        //回调
        if (firstData != null) {
            callBack.onFirstDataLoaded(firstData);
        } else {
            callBack.onDataNotAvailable();
        }
    }

    /**
     * 缓存当前版本号
     *
     * @param currentVersion
     */
    private void cacheCurrentVersion(String currentVersion) {
        SharePreferenceUtils.cacheStringData(context, Config.KEY_VERSION, currentVersion);
    }

    /**
     * 是否版本升级
     *
     * @param cachedVersion
     * @param currentVersion
     * @return
     */
    private boolean isUpdated(String cachedVersion, String currentVersion) {
        return (cachedVersion.equals(currentVersion));
    }

    /**
     * 得到缓存的版本号
     *
     * @return
     */
    private String getCachedVersion() {
        return SharePreferenceUtils.getCachedStringData(context, Config.KEY_VERSION);
    }

    /**
     * 得到当前的版本
     *
     * @return
     */
    private String getCurrentVersion() {
        try {
            String currentVersion = SystemUtils.getCurrentVersion(context);
            return currentVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}