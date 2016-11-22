package com.jkb.model.dataSource.function.special.list;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.subject.SpecialListEntity;
import com.jkb.api.entity.subject.SubjectActionEntity;

/**
 * 专题的数据来源类
 * Created by JustKiddingBaby on 2016/11/19.
 */

public interface SpecialDataSource {

    /**
     * 得到所有的专题
     */
    void getAllSpecial(
            String authorization, @NonNull String subject, int schoolId, int page,
            @NonNull ApiCallback<ApiResponse<SpecialListEntity>> apiCallback
    );

    /**
     * 改变专题状态
     */
    void changeSpecialMarkStatus(
            @NonNull String authorization, int subjectId,
            @NonNull ApiCallback<ApiResponse<SubjectActionEntity>> apiCallback);

    /**
     * 喜欢/点赞动态
     *
     * @param Authorization token
     * @param user_id       用户id
     * @param target_id     目标id
     * @param apiCallback   回调
     */
    void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback);
}
