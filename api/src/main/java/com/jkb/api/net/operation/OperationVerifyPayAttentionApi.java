package com.jkb.api.net.operation;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationVerifyPayAttentionEntity;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 访客是否关注了用户API接口
 * Created by JustKiddingBaby on 2016/8/22.
 */

public interface OperationVerifyPayAttentionApi {

    @Multipart
    @POST(Config.URL_OPERATION_VERIFYIFPAYATTENTION)
    Call<ApiResponse<OperationVerifyPayAttentionEntity>> verifyIfPayAttention(
            @Part(Config.KEY_USER_ID) int user_id,
            @Part(Config.KEY_VISITOR_ID) int visitor_id
    );
}
