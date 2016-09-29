package com.jkb.api.net.school;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.school.SchoolListEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 学校的Api接口
 * Created by JustKiddingBaby on 2016/9/29.
 */

public interface SchoolApi {

    /**
     * 得到所有学校
     *
     * @param page 分页
     * @return Call
     */
    @GET(Config.URL_SCHOOL_ALL)
    Call<ApiResponse<SchoolListEntity>> getAllSchool(
            @Query(Config.KEY_PAGE) int page);
}
