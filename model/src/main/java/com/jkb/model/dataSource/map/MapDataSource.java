package com.jkb.model.dataSource.map;

import com.jkb.model.base.BaseDataSource;
import com.jkb.model.dataSource.map.data.CoterieData;
import com.jkb.model.dataSource.map.data.SchoolData;
import com.jkb.model.dataSource.map.data.UserData;

import java.util.List;

/**
 * Map页面的数据处理接口
 * Created by JustKiddingBaby on 2016/7/28.
 */
public interface MapDataSource extends BaseDataSource {

    /**
     * 获取当前学校数据的回调接口
     */
    interface GetCurrentSchoolCallBack {
        /**
         * 数据获取成功
         */
        void onSchoolLoaded(SchoolData schoolData);

        /**
         * 数据获得失败
         */
        void onDataNotAvailable();
    }

    /**
     * 获取用户数据的接口
     */
    interface GetUserCallBack {
        /**
         * 数据获取成功
         */
        void onUserLoaded(UserData userData);

        /**
         * 数据获得失败
         */
        void onDataNotAvailable();
    }

    /**
     * 获取用户数据的回调接口
     */
    interface LoadUsersCallBack {

        /**
         * 获取或调数据成功
         *
         * @param userDatas
         */
        void onUsersLoaded(List<UserData> userDatas);

        /**
         * 数据获得失败
         */
        void onDataNotAvailable();
    }

    /**
     * 得到一条圈子信息的回调接口
     */
    interface GetCoterieCallBack {

        /**
         * 获取成功
         *
         * @param coterieData
         */
        void onCoterieLoaded(CoterieData coterieData);

        /**
         * 数据获得失败
         */
        void onDataNotAvailable();
    }

    /**
     * 得到多条圈子信息的回调接口
     */
    interface LoadCoteriesCallBack {

        /**
         * 获取多条数据成功
         *
         * @param coterieDatas
         */
        void onCoterieLoaded(List<CoterieData> coterieDatas);

        /**
         * 数据获得失败
         */
        void onDataNotAvailable();
    }

    /**
     * 得到当前学校的信息
     *
     * @param callBack
     */
    void getCurrentSchool(GetCurrentSchoolCallBack callBack);

    /**
     * 获取单个用户的数据
     *
     * @param userId
     * @param callBack
     */
    void getUser(String userId, GetUserCallBack callBack);

    /**
     * 获取多个用户接口
     *
     * @param lng
     * @param lat
     * @param callBack
     */
    void getUsers(String lng, String lat, LoadUsersCallBack callBack);

    /**
     * 获取单条圈子信息
     *
     * @param coterieId
     * @param callBack
     */
    void getCoterie(String coterieId, GetCoterieCallBack callBack);

    /**
     * 获取多条圈子信息
     *
     * @param callBack
     */
    void getCoteries(LoadCoteriesCallBack callBack);
}
