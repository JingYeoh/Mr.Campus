package com.jkb.model.dataSource.function.special.list.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.subject.SpecialListEntity;
import com.jkb.api.entity.subject.SubjectActionEntity;
import com.jkb.model.dataSource.function.special.list.SpecialDataSource;

/**
 * 专题的远程数据来源类
 * Created by JustKiddingBaby on 2016/11/19.
 */

public class SpecialLocalDataSource implements SpecialDataSource {

    private Context context;

    private SpecialLocalDataSource(Context context) {
    }

    private static SpecialLocalDataSource INSTANCE = null;

    public static SpecialLocalDataSource newInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SpecialLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getAllSpecial(String authorization, @NonNull String subject, int schoolId, int page,
                              @NonNull ApiCallback<ApiResponse<SpecialListEntity>> apiCallback) {
    }

    @Override
    public void changeSpecialMarkStatus(
            @NonNull String authorization, int subjectId,
            @NonNull ApiCallback<ApiResponse<SubjectActionEntity>> apiCallback) {
    }

    @Override
    public void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
    }
}
