package com.jkb.api.net.tools;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.tools.ToolsCETEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 小工具的Api接口
 * Created by JustKiddingBaby on 2016/11/16.
 */

public interface ToolsApi {

    /**
     * 查询四六级成绩单
     *
     * @param zkzh 准考证号
     * @param xm   姓名
     * @return Call
     */
    @GET(Config.URL_TOOLS_CET)
    Call<ApiResponse<ToolsCETEntity>> getCetReportCard(
            @Query(Config.KEY_ZKZH) String zkzh,
            @Query(Config.KEY_XM) String xm
    );
}
