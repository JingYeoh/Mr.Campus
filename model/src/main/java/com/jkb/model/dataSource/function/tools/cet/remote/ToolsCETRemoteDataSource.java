package com.jkb.model.dataSource.function.tools.cet.remote;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicPopularListEntity;
import com.jkb.api.entity.tools.ToolsCETEntity;
import com.jkb.api.net.tools.ToolsApi;
import com.jkb.model.dataSource.function.tools.cet.ToolsCETDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 请求四六成绩查询的远程数据来源类
 * Created by JustKiddingBaby on 2016/11/16.
 */

public class ToolsCETRemoteDataSource implements ToolsCETDataSource {

    private ToolsCETRemoteDataSource() {
    }

    private static ToolsCETRemoteDataSource INSTANCE;

    public static ToolsCETRemoteDataSource newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ToolsCETRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getCetReportCard(String zkzh, String xm,
                                 ApiCallback<ApiResponse<ToolsCETEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        ToolsApi toolsApi = factory.createApi(ToolsApi.class);
        Call<ApiResponse<ToolsCETEntity>> call = toolsApi.getCetReportCard(zkzh, xm);
        Type type = new TypeToken<ApiResponse<ToolsCETEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<ToolsCETEntity>>(apiCallback, call, type);
    }
}
