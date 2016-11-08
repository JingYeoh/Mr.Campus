package com.jkb.core.presenter.circle;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleDynamicInBlackListEntity;
import com.jkb.core.contract.circle.CircleDynamicInCircleBlackListContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.data.circle.dynamicInBlackList.ArticleDynamicInBlackList;
import com.jkb.core.data.circle.dynamicInBlackList.DynamicInBlackList;
import com.jkb.core.data.circle.dynamicInBlackList.NormalDynamicInBlackList;
import com.jkb.core.data.circle.dynamicInBlackList.TopicDynamicInBlackList;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.circle.dynamiInBlackList.CircleDynamicInCircleBlackListDataSource;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * 圈子内动态黑名单的P层
 * Created by JustKiddingBaby on 2016/11/5.
 */

public class CircleDynamicInCircleBlackListPresenter implements
        CircleDynamicInCircleBlackListContract.Presenter {

    private CircleDynamicInCircleBlackListContract.View view;
    private CircleDynamicInCircleBlackListDataSource repertory;

    //data
    private int circleId;
    private boolean isCached;
    private boolean isRefreshing;
    private List<DynamicInBlackList> dynamicInBlackLists;
    private PageControlEntity pageControl;

    public CircleDynamicInCircleBlackListPresenter(
            CircleDynamicInCircleBlackListContract.View view,
            CircleDynamicInCircleBlackListDataSource repertory) {
        this.view = view;
        this.repertory = repertory;

        dynamicInBlackLists = new ArrayList<>();
        pageControl = new PageControlEntity();

        this.view.setPresenter(this);
    }

    @Override
    public void initCircleId() {
        circleId = view.getCircleId();
    }

    @Override
    public void initDynamicInCircleBlackList() {
        if (isCached) {
            bindDataToView();
        } else {
            onRefresh();
        }
    }

    @Override
    public void bindDataToView() {
        if (dynamicInBlackLists.size() == 0) {
            isCached = false;
        } else {
            isCached = true;
        }
        isRefreshing = false;
        if (!view.isActive()) {
            return;
        }
        view.hideRefreshingView();
        view.dismissLoading();
        view.setDynamicInBlackListData(dynamicInBlackLists);
    }

    @Override
    public void onRefresh() {
        if (isRefreshing) {
            return;
        }
        isCached = false;
        isRefreshing = true;
        view.showRefreshingView();
        pageControl.setCurrent_page(1);
        dynamicInBlackLists.clear();
        reqDynamicsInCircleBlackList();
    }

    @Override
    public void onLoadMore() {
        if (isRefreshing) {
            return;
        }
        isCached = false;
        isRefreshing = true;
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        reqDynamicsInCircleBlackList();
    }

    @Override
    public void onDynamicItemClick(int position) {
        if (!view.isActive()) {
            return;
        }
        DynamicInBlackList dynamic = dynamicInBlackLists.get(position);
        int dynamic_id = dynamic.getDynamic_id();
        if (dynamic instanceof ArticleDynamicInBlackList) {
            view.startDynamicDetailArticle(dynamic_id);
        } else if (dynamic instanceof NormalDynamicInBlackList) {
            view.startDynamicDetailNormal(dynamic_id);
        } else if (dynamic instanceof TopicDynamicInBlackList) {
            view.startDynamicDetailTopic(dynamic_id);
        }
    }

    @Override
    public void onPullDynamicOutBlackItemClick(final int position) {
        if (!view.isActive()) {
            return;
        }
        String authorization = getAuthorization();
        if (StringUtils.isEmpty(authorization)) {
            view.showReqResult("请您登录后再进行操作");
            return;
        }
        DynamicInBlackList dynamic = dynamicInBlackLists.get(position);
        int dynamic_id = dynamic.getDynamic_id();
        repertory.pullDynamicOutBlackList(
                authorization, dynamic_id, getUserId(),
                new ApiCallback<ApiResponse<CircleActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<CircleActionEntity>> response) {
                        if (view.isActive()) {
                            dynamicInBlackLists.remove(position);
                            view.showReqResult("操作成功");
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResponse<CircleActionEntity>> response,
                                        String error, ApiResponse<CircleActionEntity> apiResponse) {
                        if (view.isActive()) {
                            view.showReqResult(error);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (view.isActive()) {
                            view.showReqResult("请求失败");
                            bindDataToView();
                        }
                    }
                });
    }

    @Override
    public void onUserItemClick(int position) {
        if (!view.isActive()) {
            return;
        }
        DynamicInBlackList dynamic = dynamicInBlackLists.get(position);
        UserInfo user = dynamic.getUser();
        view.startPersonCenter(user.getId());
    }

    @Override
    public void start() {
        initCircleId();
        initDynamicInCircleBlackList();
    }

    /**
     * 得到头
     */
    public String getAuthorization() {
        String Authorization = null;
        if (LoginContext.getInstance().isLogined()) {
            Authorization = Config.HEADER_BEARER +
                    UserInfoSingleton.getInstance().getUserAuths().getToken();
        }
        return Authorization;
    }

    /**
     * 得到用户id
     */
    public int getUserId() {
        int user_id = 0;
        if (LoginContext.getInstance().isLogined()) {
            user_id = UserInfoSingleton.getInstance().getUserAuths().getUser_id();
        }
        return user_id;
    }

    /**
     * 请求圈子黑名单动态
     */
    private void reqDynamicsInCircleBlackList() {
        String authorization = getAuthorization();
        if (StringUtils.isEmpty(authorization)) {
            view.showReqResult("请您先去登录再进行操作");
            return;
        }
        repertory.getAllDynamicsInBlackList(authorization, circleId, pageControl.getCurrent_page(),
                dynamicInBlackApiCallback);
    }

    /**
     * 请求圈子拉黑动态的数据回调类
     */
    private ApiCallback<ApiResponse<CircleDynamicInBlackListEntity>> dynamicInBlackApiCallback =
            new ApiCallback<ApiResponse<CircleDynamicInBlackListEntity>>() {
                @Override
                public void onSuccess(
                        Response<ApiResponse<CircleDynamicInBlackListEntity>> response) {
                    if (view.isActive()) {
                        handleData(response.body());
                    }
                }

                /**
                 *处理数据
                 */
                private void handleData(ApiResponse<CircleDynamicInBlackListEntity> body) {
                    CircleDynamicInBlackListEntity msg = body.getMsg();
                    if (msg == null) {
                    } else {
                        //设置分页数据
                        pageControl.setTotal(msg.getTotal());
                        pageControl.setPer_page(msg.getPer_page());
                        pageControl.setCurrent_page(msg.getCurrent_page());
                        pageControl.setLast_page(msg.getLast_page());
                        pageControl.setNext_page_url(msg.getNext_page_url());
                        pageControl.setPrev_page_url(msg.getPrev_page_url());
                        pageControl.setFrom(msg.getFrom());
                        pageControl.setTo(msg.getTo());

                        handleDynamicData(msg.getData());
                    }
                    bindDataToView();
                }

                /**
                 * 处理动态数据
                 */
                private void handleDynamicData(
                        List<CircleDynamicInBlackListEntity.DataBean> data) {
                    if (data == null || data.size() == 0) {
                        return;
                    }
                    for (CircleDynamicInBlackListEntity.DataBean dataBean :
                            data) {
                        DynamicInBlackList dynamic =
                                new DynamicInBlackList(dataBean).getDynamicInBlackList();
                        if (dynamic != null) {
                            dynamicInBlackLists.add(dynamic);
                        }
                    }
                }

                @Override
                public void onError(
                        Response<ApiResponse<CircleDynamicInBlackListEntity>> response,
                        String error, ApiResponse<CircleDynamicInBlackListEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult(error);
                        bindDataToView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("请求失败");
                        bindDataToView();
                    }
                }
            };
}
