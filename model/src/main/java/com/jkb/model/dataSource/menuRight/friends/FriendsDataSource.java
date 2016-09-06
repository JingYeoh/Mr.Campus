package com.jkb.model.dataSource.menuRight.friends;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.contacts.FriendListEntity;

/**
 * 好友列表的数据来源类
 * Created by JustKiddingBaby on 2016/9/6.
 */

public interface FriendsDataSource {
    /**
     * 得到好友列表数据
     *
     * @param Authorization 头
     * @param page          分页
     * @param apiCallback   回调
     */
    void getFriendsList(
            @NonNull String Authorization, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<FriendListEntity>> apiCallback);
}
