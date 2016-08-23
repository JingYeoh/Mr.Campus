package com.jkb.api.net.operation;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationVisitorEntity;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * 获取访问我的人的Api接口
 * Created by JustKiddingBaby on 2016/8/23.
 */

public interface OperationVisitorApi {

    /**
     * 获取访问我的人的接口
     *
     * @param user_id    用户id
     * @param visitor_id 访客id ，可空
     * @param page       页码
     * @return Call
     */
    @Multipart
    @POST(Config.URL_OPERATION_VISITME)
    Call<ApiResponse<OperationVisitorEntity>> visitorMe(
            @Part(Config.KEY_USER_ID) int user_id,
            @Part(Config.KEY_VISITOR_ID) int visitor_id,
            @Query(Config.KEY_PAGE) int page
    );
}
