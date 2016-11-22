package com.jkb.core.presenter.function.special.function;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.subject.SpecialListEntity;
import com.jkb.core.contract.function.special.function.SpecialLostAndFoundContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.data.special.SpecialData;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.function.special.list.SpecialRepertory;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * 失物招领的P层
 * Created by JustKiddingBaby on 2016/11/19.
 */

public class SpecialLost$FoundPresenter implements SpecialLostAndFoundContract.Presenter {

    private SpecialLostAndFoundContract.View view;
    private SpecialRepertory repertory;

    //data
    private int schoolId = -1;
    private boolean isCached;
    private boolean isRefreshing;
    private PageControlEntity pageControl;
    private List<SpecialData> mSpecialData;

    public SpecialLost$FoundPresenter(
            SpecialLostAndFoundContract.View view, SpecialRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        pageControl = new PageControlEntity();
        mSpecialData = new ArrayList<>();

        this.view.setPresenter(this);
    }

    @Override
    public void bindDataToView() {
        isCached = mSpecialData.size() > 0;
        isRefreshing = false;
        if (!view.isActive()) {
            return;
        }
        view.dismissLoading();
        view.hideRefreshingView();
        view.setSpecialData(mSpecialData);
    }

    @Override
    public void initSpecialData() {
        if (isCached) {
            bindDataToView();
        } else {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        isCached = false;
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        pageControl.setCurrent_page(1);
        mSpecialData.clear();
        view.showRefreshingView();
        reqSpecialLostAndFoundData();
    }


    @Override
    public void onLoadMore() {
        if (pageControl.getCurrent_page() > pageControl.getLast_page()) {
            return;
        }
        if (isRefreshing) {
            return;
        }
        isCached = false;
        isRefreshing = true;
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        reqSpecialLostAndFoundData();
    }

    @Override
    public void onItemClick(int position) {
        int specialItemId = getSpecialItemId(position);
        view.startSpecialLostAndFoundDetail(specialItemId);
    }

    @Override
    public void onShareItemClick(int position) {

    }

    @Override
    public void onCommentItemClick(int position) {
        int specialItemId = getSpecialItemId(position);//跳转到评论
        view.startCommentList(specialItemId);
    }

    @Override
    public void onHeartItemClick(final int position) {
        String authorization = getAuthorization();
        if (StringUtils.isEmpty(authorization)) {
            view.showReqResult("请先登录再进行操作");
            return;
        }
        int specialItemId = getSpecialItemId(position);//喜欢
        int userId = getUserId();
        view.showLoading("");
        repertory.favorite(authorization, userId, specialItemId,
                new ApiCallback<ApiResponse<OperationActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                        if (view.isActive()) {
                            SpecialData specialData = mSpecialData.get(position);
                            boolean hasFavorite = specialData.getHasFavorite();
                            hasFavorite = !hasFavorite;
                            specialData.setHasFavorite(hasFavorite);
                            int count_of_favorite = specialData.getCount_of_favorite();
                            if (hasFavorite) {
                                count_of_favorite += 1;
                            } else {
                                count_of_favorite -= 1;
                            }
                            specialData.setCount_of_favorite(count_of_favorite);
                            mSpecialData.set(position, specialData);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(
                            Response<ApiResponse<OperationActionEntity>> response,
                            String error, ApiResponse<OperationActionEntity> apiResponse) {
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
    public void start() {
        schoolId = view.getSchoolId();
        initSpecialData();
    }

    /**
     * 请求表白墙专题数据
     */
    private void reqSpecialLostAndFoundData() {
        if (schoolId <= 0) {
            return;
        }
        LogUtils.d(SpecialLost$FoundPresenter.class,"reqSpecialLostAndFoundData");
        String authorization = getAuthorization();
        repertory.getAllSpecialLost$Found(authorization, schoolId, pageControl.getCurrent_page(),
                specialCallback);
    }

    /**
     * 得到详情条目的id
     */
    private int getSpecialItemId(int position) {
        int dynamicId;
        dynamicId = mSpecialData.get(position).getId();
        return dynamicId;
    }

    /**
     * 得到头
     */
    private String getAuthorization() {
        String authorization = null;
        if (LoginContext.getInstance().isLogined()) {
            authorization = Config.HEADER_BEARER +
                    UserInfoSingleton.getInstance().getUserAuths().getToken();
        }
        return authorization;
    }

    /**
     * 得到用户id
     */
    private int getUserId() {
        int userId = -1;
        if (LoginContext.getInstance().isLogined()) {
            userId = UserInfoSingleton.getInstance().getUserAuths().getUser_id();
        }
        return userId;
    }

    private ApiCallback<ApiResponse<SpecialListEntity>> specialCallback =
            new ApiCallback<ApiResponse<SpecialListEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<SpecialListEntity>> response) {
                    if (view.isActive()) {
                        handleData(response.body().getMsg());
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(SpecialListEntity msg) {
                    if (msg == null) {
                        bindDataToView();
                        return;
                    }
                    //处理分页数据
                    pageControl.setTotal(msg.getTotal());
                    pageControl.setPer_page(msg.getPer_page());
                    pageControl.setCurrent_page(msg.getCurrent_page());
                    pageControl.setLast_page(msg.getLast_page());
                    pageControl.setNext_page_url(msg.getNext_page_url());
                    pageControl.setPrev_page_url(msg.getPrev_page_url());
                    pageControl.setFrom(msg.getFrom());
                    pageControl.setTo(msg.getTo());

                    //处理数据
                    List<SpecialListEntity.DataBean> data = msg.getData();
                    if (data == null || data.size() == 0) {
                        bindDataToView();
                        return;
                    }
                    for (SpecialListEntity.DataBean dataBean : data) {
                        changeReqDataToModel(dataBean);
                    }
                    bindDataToView();
                }

                /**
                 * 转换请求数据为model
                 */
                private void changeReqDataToModel(SpecialListEntity.DataBean dataBean) {
                    if (dataBean == null) {
                        return;
                    }
                    SpecialData special = new SpecialData(dataBean).getSpecial();
                    if (special != null) {
                        mSpecialData.add(special);
                    }
                }

                @Override
                public void onError(Response<ApiResponse<SpecialListEntity>> response, String error,
                                    ApiResponse<SpecialListEntity> apiResponse) {
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
