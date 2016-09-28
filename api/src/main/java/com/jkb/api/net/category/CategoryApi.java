package com.jkb.api.net.category;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.category.CategoryTypeEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 标签的请求接口Api
 * Created by JustKiddingBaby on 2016/9/26.
 */

public interface CategoryApi {

    /**
     * 请求标签类型
     *
     * @param type 标签类型
     *             允许值: "topic", "article"
     * @return Call
     */
    @GET(Config.URL_CATEGORY_TYPE)
    Call<ApiResponse<CategoryTypeEntity>> getCategoryType(
            @Path(Config.KEY_TYPE) String type);
}
