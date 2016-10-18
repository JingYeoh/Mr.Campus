package com.jkb.api.net.dynamic;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicCircleListEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 我的动态的Api接口
 * Created by JustKiddingBaby on 2016/10/17.
 */

public interface MyDynamicApi {
    /**
     * 得到我的圈子内动态数据
     *
     * @param Authorization 头，可选
     * @param type          类型
     * @param userId        用户id
     * @param page          分页
     * @return Call
     */
    @GET(Config.URL_DYNAMIC_MY)
    Call<ApiResponse<DynamicCircleListEntity>> getCircleDynamic(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_TYPE) String type,
            @Query(Config.KEY_USERID) int userId,
            @Query(Config.KEY_PAGE) int page);

    /**
     * 得到我的圈子内动态数据
     *
     * @param Authorization 头，可选
     * @param type          类型
     * @param userId        用户id
     * @param page          分页
     * @param partial       筛选条件
     * @return Call
     */
    @GET(Config.URL_DYNAMIC_MY)
    Call<ApiResponse<DynamicCircleListEntity>> getCircleDynamic(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_TYPE) String type,
            @Query(Config.KEY_USERID) int userId,
            @Query(Config.KEY_PAGE) int page,
            @Query(Config.KEY_PARTIAL) String partial);

    /**
     * 得到我的圈子内动态数据
     *
     * @param type    类型
     * @param userId  用户id
     * @param page    分页
     * @param partial 筛选条件
     * @return Call
     */
    @GET(Config.URL_DYNAMIC_MY)
    Call<ApiResponse<DynamicCircleListEntity>> getCircleDynamic(
            @Path(Config.KEY_TYPE) String type,
            @Query(Config.KEY_USERID) int userId,
            @Query(Config.KEY_PAGE) int page,
            @Query(Config.KEY_PARTIAL) String partial);

    /**
     * 得到我的圈子内动态数据
     *
     * @param type   类型
     * @param userId 用户id
     * @param page   分页
     * @return Call
     */
    @GET(Config.URL_DYNAMIC_MY)
    Call<ApiResponse<DynamicCircleListEntity>> getCircleDynamic(
            @Path(Config.KEY_TYPE) String type,
            @Query(Config.KEY_USERID) int userId,
            @Query(Config.KEY_PAGE) int page);
}
