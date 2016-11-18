package com.jkb.model.dataSource.function.tools.cet.local;

import android.content.Context;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.tools.ToolsCETEntity;
import com.jkb.model.dataSource.function.tools.cet.ToolsCETDataSource;

/**
 * 请求四六成绩查询的远程数据来源类
 * Created by JustKiddingBaby on 2016/11/16.
 */

public class ToolsCETLocalDataSource implements ToolsCETDataSource {

    private Context applicationContext;

    private ToolsCETLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static ToolsCETLocalDataSource INSTANCE;

    public static ToolsCETLocalDataSource newInstance(Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new ToolsCETLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getCetReportCard(String zkzh, String xm,
                                 ApiCallback<ApiResponse<ToolsCETEntity>> apiCallback) {
    }
}
