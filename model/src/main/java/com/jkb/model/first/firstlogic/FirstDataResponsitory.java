package com.jkb.model.first.firstlogic;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * FirstActivity的数据仓库类
 * Created by JustKiddingBaby on 2016/7/22.
 */
public class FirstDataResponsitory implements FirstDataSource {


    private static FirstDataResponsitory INSTANCE = null;

    private FirstDataSource firstLocalDataSource;

    //缓存
    private FirstData cacheData;
    //是否过期
    private boolean overdue = false;

    private FirstDataResponsitory(@NonNull FirstDataSource firstLocalDataSource) {
        this.firstLocalDataSource = checkNotNull(firstLocalDataSource);
    }

    /**
     * 获取单利实例对象
     *
     * @param firstLocalDataSource
     * @return
     */
    public static FirstDataResponsitory getInstance(@NonNull FirstDataSource firstLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FirstDataResponsitory(firstLocalDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getFirstData(@NonNull final FirstDataCallBack callBack) {
//        planOne(callBack);
        planTwo(callBack);
    }

    /**
     * 方案一：使用了缓存机制
     *
     * @param callBack
     */
    private void planOne(@NonNull final FirstDataCallBack callBack) {
        //如果有缓存就使用缓存的数据
        if (cacheData != null && !overdue) {
            callBack.onFirstDataLoaded(cacheData);
            //如果返回的结果为Guide的时候标注为已过期
            if (cacheData.isUpdated()) {
                overdue = true;
            } else if (cacheData.isCachedAdvent()) {//有广告的时候，标注为已过期
                overdue = true;
            }
        } else {//否则使用本地数据
            this.firstLocalDataSource.getFirstData(new FirstDataCallBack() {
                @Override
                public void onFirstDataLoaded(FirstData firstData) {
                    //缓存到本地数据
                    cacheData = firstData;
                    callBack.onFirstDataLoaded(cacheData);
                }

                @Override
                public void onDataNotAvailable() {
                    callBack.onDataNotAvailable();
                }
            });
        }
    }

    /**
     * 方案二：不使用缓存机制，每次都更新数据
     *
     * @param callBack
     */
    private void planTwo(@NonNull final FirstDataCallBack callBack) {
        firstLocalDataSource.getFirstData(callBack);
    }
}
