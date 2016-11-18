package com.jkb.model.dataSource.function.tools.cet;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.tools.ToolsCETEntity;

/**
 * 四六级查询的数据来源接口
 * Created by JustKiddingBaby on 2016/11/16.
 */

public interface ToolsCETDataSource {

    /**
     * 得到四六级成绩接口
     *
     * @param zkzh        准考证号
     * @param xm          姓名
     * @param apiCallback 回调
     */
    void getCetReportCard(String zkzh, String xm,
                          ApiCallback<ApiResponse<ToolsCETEntity>> apiCallback);
}
