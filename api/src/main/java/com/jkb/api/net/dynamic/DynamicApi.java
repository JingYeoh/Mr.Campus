package com.jkb.api.net.dynamic;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicArticleEntity;
import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.api.entity.dynamic.DynamicNormalEntity;
import com.jkb.api.entity.dynamic.DynamicPopularListEntity;
import com.jkb.api.entity.dynamic.DynamicPostEntity;
import com.jkb.api.entity.dynamic.DynamicTopicEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    /**
     * 创建动态
     *
     * @param user_id      发布动态的用户id.
     * @param dynamic_type 动态标签.
     *                     允许值: "topic", "normal", "article"
     * @param title        动态标题.
     * @param dcontent     动态内容.
     * @param tag          主题标签（仅发表话题动态时需要此项）.
     * @param circle_id    动态所属圈子id.
     * @return Call
     */
    @Multipart
    @POST(Config.URL_DYNAMIC_POST)
    Call<ApiResponse<DynamicPostEntity>> postDynamic(
            @Part(Config.KEY_USER_ID) int user_id,
            @Part(Config.KEY_DYNAMIC_TYPE) String dynamic_type,
            @Part(Config.KEY_TITLE) String title,
            @Part(Config.KEY_DCONTENT) String dcontent,
            @Part(Config.KEY_TAG) String tag,
            @Part(Config.KEY_CIRCLE_ID) int circle_id);

    /**
     * 创建动态
     *
     * @param Authorization 头
     * @param user_id       发布动态的用户id.
     * @param dynamic_type  动态标签.
     *                      允许值: "topic", "normal", "article"
     * @param title         动态标题.
     * @param dcontent      动态内容.
     * @param tag           主题标签（仅发表话题动态时需要此项）.
     * @return Call
     */
    @Multipart
    @POST(Config.URL_DYNAMIC_POST)
    Call<ApiResponse<DynamicPostEntity>> postDynamic(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Part(Config.KEY_USER_ID) int user_id,
            @Part(Config.KEY_DYNAMIC_TYPE) String dynamic_type,
            @Part(Config.KEY_TITLE) String title,
            @Part(Config.KEY_DCONTENT) String dcontent,
            @Part(Config.KEY_TAG) String tag);

    /**
     * 得到所有的热门动态
     *
     * @param Authorization 头，可选
     * @param schoolId      学校id，必选
     * @param page          分页
     * @return Call
     */
    @GET(Config.URL_DYNAMIC_POPULAY_ALL)
    Call<ApiResponse<DynamicPopularListEntity>> getAllPopularDynamic(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_SCHOOLID) int schoolId,
            @Query(Config.KEY_PAGE) int page);
}
