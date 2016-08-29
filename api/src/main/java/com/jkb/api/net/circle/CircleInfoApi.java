package com.jkb.api.net.circle;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.circle.CircleInfoEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.Path;

/**
 * 圈子信息的Api接口
 * Created by JustKiddingBaby on 2016/8/29.
 */

public interface CircleInfoApi {

    @GET(Config.URL_CIRCLE_INFO)
    Call<ApiResponse<CircleInfoEntity>> circleInfo(
            @Path(Config.KEY_USERID) int userId,
            @Path(Config.KEY_ID) int id
    );
}
