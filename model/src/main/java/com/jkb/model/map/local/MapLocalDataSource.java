package com.jkb.model.map.local;

import com.jkb.model.map.MapDataSource;

/**
 * 地图页面用到的本地数据来源
 * Created by JustKiddingBaby on 2016/7/28.
 */
public class MapLocalDataSource implements MapDataSource {
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
