package com.jkb.model.dataSource.map.remote;

import com.jkb.model.dataSource.map.MapDataSource;

/**
 * 地图页面用到的远程数据来源
 * Created by JustKiddingBaby on 2016/7/28.
 */
public class MapRemoteDataSource implements MapDataSource {
    @Override
    public void getCurrentSchool(GetCurrentSchoolCallBack callBack) {

    }

    @Override
    public void getUser(String userId, GetUserCallBack callBack) {

    }

    @Override
    public void getUsers(String lng, String lat, LoadUsersCallBack callBack) {

    }

    @Override
    public void getCoterie(String coterieId, GetCoterieCallBack callBack) {

    }

    @Override
    public void getCoteries(LoadCoteriesCallBack callBack) {

    }
}
