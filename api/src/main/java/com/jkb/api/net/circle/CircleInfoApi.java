package com.jkb.api.net.circle;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.entity.circle.DynamicInCircleListEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    /**
     * 得到所有圈子中动态
     *
     * @param Authorization 头可选
     * @param circleId      圈子id
     * @param page          分页
     * @return Call
     */
    @GET(Config.URL_CIRCLE_ALLDYNAMIC)
    Call<ApiResponse<DynamicInCircleListEntity>> getAllDynamicInCircle(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_CIRCLEID) int circleId,
            @Query(Config.KEY_PAGE) int page
    );
}
