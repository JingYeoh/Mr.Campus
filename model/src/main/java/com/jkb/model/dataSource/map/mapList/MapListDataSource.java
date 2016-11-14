package com.jkb.model.dataSource.map.mapList;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleListInSchoolEntity;
import com.jkb.api.entity.user.UserListInfoEntity;

/**
 * 地图列表的附近的人的数据来源类接口
 * Created by JustKiddingBaby on 2016/11/13.
 */

public interface MapListDataSource {

    /**
     * 得到用户列表数据
     *
     * @param authorization 头，可选
     * @param idArr         id数组
     * @param callback      回调
     */
    void getUserListInfo(String authorization, @NonNull String idArr,
                         @NonNull ApiCallback<ApiResponse<UserListInfoEntity>> callback);

    /**
     * 得到学校中的圈子列表
     *
     * @param authorization 头，可选
     * @param schoolId      学校id
     * @param page          分页
     * @param callback      回调
     */
    void getCircleListInSchool(
            String authorization, @NonNull int schoolId, int page,
            @NonNull ApiCallback<ApiResponse<CircleListInSchoolEntity>> callback);
}
