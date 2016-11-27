package com.jkb.model.dataSource.search.detail.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.search.SearchEntity;
import com.jkb.model.dataSource.search.detail.SearchDetailDataSource;

/**
 * 搜索详情的数据来源类
 * Created by JustKiddingBaby on 2016/11/26.
 */

public class SearchDetailLocalDataSource implements SearchDetailDataSource {

    private Context applicationContext;

    private SearchDetailLocalDataSource(Context applicationContext) {
    }

    private static SearchDetailLocalDataSource INSTANCE = null;

    public static SearchDetailLocalDataSource newInstance(Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new SearchDetailLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void searchByKeyWords(
            String Authorization, @NonNull String type, @NonNull String keyWords, int page,
            @NonNull ApiCallback<ApiResponse<SearchEntity>> apiCallback) {

    }

    @Override
    public void payAttentionOrCancel(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
    }
}
