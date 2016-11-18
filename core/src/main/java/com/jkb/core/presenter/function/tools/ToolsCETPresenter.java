package com.jkb.core.presenter.function.tools;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.tools.ToolsCETEntity;
import com.jkb.core.contract.function.tools.ToolsCETContract;
import com.jkb.model.dataSource.function.tools.cet.ToolsCETDataSource;
import com.jkb.model.utils.StringUtils;

import retrofit2.Response;

/**
 * 查询四六级成绩的P层
 * Created by JustKiddingBaby on 2016/11/16.
 */

public class ToolsCETPresenter implements ToolsCETContract.Presenter {

    private ToolsCETContract.View view;
    private ToolsCETDataSource repertory;

    public ToolsCETPresenter(ToolsCETContract.View view, ToolsCETDataSource repertory) {
        this.view = view;
        this.repertory = repertory;

        this.view.setPresenter(this);
    }

    @Override
    public void getCetReportCard(String zkzh, String xm) {
        if (StringUtils.isEmpty(zkzh)) {
            view.showReqResult("准考帐号不能为空");
            return;
        }
        if (StringUtils.isEmpty(xm)) {
            view.showReqResult("姓名不能为空");
            return;
        }
        //请求接口
        repertory.getCetReportCard(zkzh, xm, cetReportCardApiCallback);
    }

    @Override
    public void start() {

    }

    /**
     * 四六级成绩单的请求回调
     */
    private ApiCallback<ApiResponse<ToolsCETEntity>> cetReportCardApiCallback =
            new ApiCallback<ApiResponse<ToolsCETEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<ToolsCETEntity>> response) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        ToolsCETEntity msg = response.body().getMsg();
                        ToolsCETEntity.CetBean cet = msg.getCet();
                        if (cet == null) {
                            return;
                        }
                        view.setCetReportCardValue(
                                cet.getName(), cet.getSchool(), cet.getType(), cet.getNum(),
                                cet.getTotal(), cet.getListen(), cet.getRead(), cet.getWrite());
                    }
                }

                @Override
                public void onError(Response<ApiResponse<ToolsCETEntity>> response, String error,
                                    ApiResponse<ToolsCETEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult(error);
                        view.dismissLoading();
                        view.showErrorView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("请求失败");
                        view.dismissLoading();
                        view.showErrorView();
                    }
                }
            };
}
