package com.jkb.model.dataSource.personCenter.original.mySubject;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.subject.SpecialListEntity;
import com.jkb.api.entity.subject.SubjectActionEntity;

/**
 * 我的专题数据来源接口
 * Created by JustKiddingBaby on 2016/11/22.
 */

public interface MyOriginalSubjectDataSource {
    /**
     * 得到我的专题动态数据
     *
     * @param Authorization 头，可选
     * @param page          分页
     * @param type          类型
     * @param apiCallback   回调
     */
    void getMySubject(String Authorization, int page, @NonNull String type,
                      @NonNull ApiCallback<ApiResponse<SpecialListEntity>> apiCallback);

    /**
     * 删除动态
     *
     * @param Authorization 头，必选
     * @param dynamic_id    动态id
     * @param apiCallback   回调
     */
    void deleteDynamic(
            @NonNull String Authorization, int dynamic_id, int operator_id,
            @NonNull ApiCallback<ApiResponse<DynamicActionEntity>> apiCallback);

    /**
     * 喜欢/点赞动态
     *
     * @param Authorization token
     * @param user_id       用户id
     * @param target_id     目标id
     * @param apiCallback   回调
     */
    void favorite(
            @NonNull String Authorization, int user_id, int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback);

    /**
     * 改变专题标记的状态
     *
     * @param Authorization 头
     * @param dynamicId     动态id
     * @param apiCallback   回调
     */
    void changeSubjectMarkStatus(
            @NonNull String Authorization, int dynamicId,
            @NonNull ApiCallback<ApiResponse<SubjectActionEntity>> apiCallback
    );
}
