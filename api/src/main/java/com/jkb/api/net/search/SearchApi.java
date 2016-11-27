package com.jkb.api.net.search;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.search.SearchEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 搜索的Api接口类
 * Created by JustKiddingBaby on 2016/11/26.
 */

public interface SearchApi {

    /**
     * 根据关键字搜索
     *
     * @param Authorization 头
     * @param type          类型
     * @param keyWords      关键字
     * @param page          分页
     * @return Call
     */
    @GET(Config.URL_SEARCH_KEYWORDS)
    Call<ApiResponse<SearchEntity>> searchByKeyWords(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_TYPE) String type, @Path(Config.KEY_KEYWORDS) String keyWords,
            @Query(Config.KEY_PAGE) int page
    );
}
