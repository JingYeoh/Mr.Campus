package com.jkb.model.first.firstlogic;

import android.support.annotation.NonNull;

import com.jkb.model.base.BaseDataSource;

/**
 * 第一次进入APP的数据处理接口
 * Created by JustKiddingBaby on 2016/7/22.
 */
public interface FirstDataSource extends BaseDataSource {


    /**
     * 得到firstData数据的回调接口
     */
    interface FirstDataCallBack {
        /**
         * 数据加载成功
         *
         * @param firstData
         */
        void onFirstDataLoaded(FirstData firstData);

        /**
         * 数据获得失败
         */
        void onDataNotAvailable();
    }

    /**
     * 得到第一次加载的数据
     *
     * @param callBack
     */
    void getFirstData(@NonNull FirstDataCallBack callBack);
}
