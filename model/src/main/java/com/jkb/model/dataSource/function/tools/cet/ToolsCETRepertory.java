package com.jkb.model.dataSource.function.tools.cet;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.tools.ToolsCETEntity;

/**
 * 得到四六级成绩的数据仓库类
 * Created by JustKiddingBaby on 2016/11/16.
 */

public class ToolsCETRepertory implements ToolsCETDataSource {

    private ToolsCETDataSource localDataSource;
    private ToolsCETDataSource remoteDataSource;

    private ToolsCETRepertory(ToolsCETDataSource localDataSource,
                              ToolsCETDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static ToolsCETRepertory INSTANCE = null;

    public static ToolsCETRepertory newInstance(ToolsCETDataSource localDataSource,
                                                ToolsCETDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ToolsCETRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getCetReportCard(String zkzh, String xm,
                                 ApiCallback<ApiResponse<ToolsCETEntity>> apiCallback) {
        remoteDataSource.getCetReportCard(zkzh, xm, apiCallback);
    }
}
