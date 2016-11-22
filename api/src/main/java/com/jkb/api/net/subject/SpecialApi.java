package com.jkb.api.net.subject;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.subject.SpecialListEntity;
import com.jkb.api.entity.subject.SubjectActionEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 专题的请求接口Api类
 * Created by JustKiddingBaby on 2016/11/19.
 */

public interface SpecialApi {


    /**
     * 得到专题列表
     *
     * @param authorization 头,可选
     * @param subject       专题类型
     * @param schoolId      学校id
     * @param page          分页
     * @return Call
     */
    @GET(Config.URL_SUBJECT_LIST)
    Call<ApiResponse<SpecialListEntity>> getSpecialList(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String authorization,
            @Path(Config.KEY_SUBJECY) String subject,
            @Path(Config.KEY_SCHOOLID) int schoolId,
            @Query(Config.KEY_PAGE) int page
    );

    /**
     * 改变专题的状态
     */
    @Multipart
    @POST(Config.URL_SUBJECT_CHANGE_MARK)
    Call<ApiResponse<SubjectActionEntity>> changeSpecialMarkStatus(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String authorization,
            @Path(Config.KEY_ID) int id
    );
}
