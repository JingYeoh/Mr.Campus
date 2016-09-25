package com.jkb.api.net.dynamic;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicArticleEntity;
import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.api.entity.dynamic.DynamicNormalEntity;
import com.jkb.api.entity.dynamic.DynamicTopicEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.Path;
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

    /**
     * 获取特定动态信息：普通
     *
     * @param userId 用户id
     * @param id     动态id
     * @return Call
     */
    @GET(Config.URL_DYNAMIC_GET_SINGLE)
    Call<ApiResponse<DynamicNormalEntity>> getNormalDynamic(
            @Path(Config.KEY_USERID) int userId,
            @Path(Config.KEY_ID) int id);

    /**
     * 获取特定动态信息：话题
     *
     * @param userId 用户id
     * @param id     动态id
     * @return Call
     */
    @GET(Config.URL_DYNAMIC_GET_SINGLE)
    Call<ApiResponse<DynamicTopicEntity>> getTopicDynamic(
            @Path(Config.KEY_USERID) int userId,
            @Path(Config.KEY_ID) int id);

    /**
     * 获取特定动态信息：文章
     *
     * @param userId 用户id
     * @param id     动态id
     * @return Call
     */
    @GET(Config.URL_DYNAMIC_GET_SINGLE)
    Call<ApiResponse<DynamicArticleEntity>> getArticleDynamic(
            @Path(Config.KEY_USERID) int userId,
            @Path(Config.KEY_ID) int id);
}
