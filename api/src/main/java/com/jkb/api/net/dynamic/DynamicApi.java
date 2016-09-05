package com.jkb.api.net.dynamic;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicListEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * 动态的Api接口
 * Created by JustKiddingBaby on 2016/9/3.
 */

public interface DynamicApi {

    /**
     * 获取全部动态信息
     *
     * @param Authorization 头
     * @param page          页码
     * @return Call
     */
    @GET(Config.URL_DYNAMIC_GET_ALL)
    Call<ApiResponse<DynamicListEntity>> getAllDynamic(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Query(Config.KEY_PAGE) int page
    );
}
