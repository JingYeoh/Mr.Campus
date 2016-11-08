package com.jkb.core.presenter.circle;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleAttentionUserListEntity;
import com.jkb.core.contract.circle.CircleUserInCircleBlackListContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.data.circle.userInCircle.UserInCircle;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.circle.attentionUserList.CircleAttentionUserListRepertory;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * 圈子成员的P层
 * Created by JustKiddingBaby on 2016/11/3.
 */

public class CircleUserInBlackListPresenter implements CircleUserInCircleBlackListContract.Presenter {

    private CircleUserInCircleBlackListContract.View view;
    private CircleAttentionUserListRepertory repertory;

    //data
    private int circle_id;
    private List<UserInCircle> usersInCircle;
    private boolean isCached;
    private boolean isRefreshing;

    //分页
    private PageControlEntity pageControl;


    public CircleUserInBlackListPresenter(
            CircleUserInCircleBlackListContract.View view,
            CircleAttentionUserListRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        usersInCircle = new ArrayList<>();
        pageControl = new PageControlEntity();

        this.view.setPresenter(this);
    }

    @Override
    public void initCircleId() {
        circle_id = view.getCircleId();
    }

    @Override
    public void initUserList() {
        if (isCached) {
            bindDataToView();
        } else {
            onRefresh();
        }
    }

    @Override
    public void bindDataToView() {
        if (usersInCircle.size() > 0) {
            isCached = true;
        } else {
            isCached = false;
        }
        isRefreshing = false;
        if (!view.isActive()) {
            return;
        }
        view.dismissLoading();
        view.hideRefreshingView();
        view.setUserData(usersInCircle, true);
    }

    @Override
    public void onRefresh() {
        if (isRefreshing) {
            return;
        }
        String Authorization = getAuthorization();
        view.showRefreshingView();
        isRefreshing = true;
        usersInCircle.clear();
        pageControl.setCurrent_page(1);
        reqCircleBlackList(Authorization);
    }

    @Override
    public void onLoadMore() {
        if (isRefreshing) {
            return;
        }
        if (pageControl.getCurrent_page() >= pageControl.getLast_page()) {
            return;
        }
        String Authorization = getAuthorization();
        isRefreshing = true;
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        reqCircleBlackList(Authorization);
    }

    /**
     * 请求圈子黑名单用户接口
     */
    private void reqCircleBlackList(String Authorization) {
        repertory.getUsersInCircleInBlackList(Authorization, circle_id,
                pageControl.getCurrent_page(), usersInCircleApiCallback);
    }

    @Override
    public void onUserItemClick(int position) {
        UserInCircle userInCircle = usersInCircle.get(position);
        int id = userInCircle.getUserInfo().getId();
        view.startPersonCenter(id);
    }

    @Override
    public void onPull$PutBlackListItemClick(int position) {
        String Authorization = getAuthorization();
        if (StringUtils.isEmpty(Authorization)) {
            view.showReqResult("请您先去登录再进行操作");
            return;
        }
        UserInCircle userInCircle = usersInCircle.get(position);
        int id = userInCircle.getUserInfo().getId();
        boolean hasInBlackList = userInCircle.isHasInBlackList();
        view.showLoading("");
        if (hasInBlackList) {
            pullUserOutBlackList(position, Authorization, circle_id, id, getUser_id());
        } else {
            putUserInBlackList(position, Authorization, circle_id, id, getUser_id());
        }
    }

    /**
     * 拉黑用戶
     */
    private void putUserInBlackList(
            final int position, String authorization, int circle_id, int id, int user_id) {
        repertory.putUserInBlackList(authorization, circle_id, id, user_id,
                new ApiCallback<ApiResponse<CircleActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<CircleActionEntity>> response) {
                        if (view.isActive()) {
                            view.showReqResult("操作成功");
                            UserInCircle userInCircle = usersInCircle.get(position);
                            boolean hasInBlackList = userInCircle.isHasInBlackList();
                            hasInBlackList = !hasInBlackList;
                            userInCircle.setHasInBlackList(hasInBlackList);
                            usersInCircle.set(position, userInCircle);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(
                            Response<ApiResponse<CircleActionEntity>> response,
                            String error, ApiResponse<CircleActionEntity> apiResponse) {
                        if (view.isActive()) {
                            view.dismissLoading();
                            view.showReqResult(error);
                        }
                    }

                    @Override
                    public void onFail() {
                        if (view.isActive()) {
                            view.dismissLoading();
                            view.showReqResult("请求失败");
                        }
                    }
                });
    }

    /**
     * 解除拉黑用户
     */
    private void pullUserOutBlackList(
            final int position, String authorization, int circle_id, int id, int user_id) {
        repertory.pullUserOutBlackList(authorization, circle_id, id, user_id,
                new ApiCallback<ApiResponse<CircleActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<CircleActionEntity>> response) {
                        if (view.isActive()) {
                            view.showReqResult("操作成功");
                            UserInCircle userInCircle = usersInCircle.get(position);
                            boolean hasInBlackList = userInCircle.isHasInBlackList();
                            hasInBlackList = !hasInBlackList;
                            userInCircle.setHasInBlackList(hasInBlackList);
                            usersInCircle.set(position, userInCircle);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(
                            Response<ApiResponse<CircleActionEntity>> response,
                            String error, ApiResponse<CircleActionEntity> apiResponse) {
                        if (view.isActive()) {
                            view.dismissLoading();
                            view.showReqResult(error);
                        }
                    }

                    @Override
                    public void onFail() {
                        if (view.isActive()) {
                            view.dismissLoading();
                            view.showReqResult("请求失败");
                        }
                    }
                });
    }

    @Override
    public void start() {
        initCircleId();
        initUserList();
    }

    /**
     * 返回头部
     */
    private String getAuthorization() {
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
    private int getUser_id() {
        int user_id = 0;
        if (LoginContext.getInstance().isLogined()) {
            user_id = UserInfoSingleton.getInstance().getUserAuths().getUser_id();
        }
        return user_id;
    }

    /**
     * 得到圈子成员的回调
     */
    private ApiCallback<ApiResponse<CircleAttentionUserListEntity>> usersInCircleApiCallback =
            new ApiCallback<ApiResponse<CircleAttentionUserListEntity>>() {
                @Override
                public void onSuccess(
                        Response<ApiResponse<CircleAttentionUserListEntity>> response) {
                    if (view.isActive()) {
                        handleData(response.body());
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(ApiResponse<CircleAttentionUserListEntity> body) {
                    if (body == null) {
                        bindDataToView();
                        return;
                    }
                    handleUserData(body.getMsg());
                }

                /**
                 * 处理圈子内成员
                 */
                private void handleUserData(CircleAttentionUserListEntity msg) {
                    if (msg == null) {
                        bindDataToView();
                        return;
                    }

                    //设置分页数据
                    pageControl.setTotal(msg.getTotal());
                    pageControl.setPer_page(msg.getPer_page());
                    pageControl.setCurrent_page(msg.getCurrent_page());
                    pageControl.setLast_page(msg.getLast_page());
                    pageControl.setNext_page_url(msg.getNext_page_url());
                    pageControl.setPrev_page_url(msg.getPrev_page_url());
                    pageControl.setFrom(msg.getFrom());
                    pageControl.setTo(msg.getTo());

                    List<CircleAttentionUserListEntity.DataBean> data = msg.getData();
                    if (data == null || data.size() == 0) {
                        bindDataToView();
                        return;
                    }
                    for (CircleAttentionUserListEntity.DataBean dataBean :
                            data) {
                        changeReqDataToModel(dataBean);
                    }
                    bindDataToView();
                }

                /**
                 * 转换数据
                 */
                private void changeReqDataToModel(CircleAttentionUserListEntity.DataBean dataBean) {
                    if (dataBean == null) {
                        return;
                    }
                    UserInCircle userInCircle = new UserInCircle();
                    userInCircle.setCircle_id(circle_id);
                    if (dataBean.getIsBlack() == 0) {
                        userInCircle.setHasInBlackList(false);
                    } else {
                        userInCircle.setHasInBlackList(true);
                    }
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(dataBean.getId());
                    userInfo.setUID(dataBean.getUID());
                    userInfo.setNickName(dataBean.getNickname());
                    userInfo.setAvatar(dataBean.getAvatar());
                    userInfo.setSex(dataBean.getSex());
                    userInfo.setName(dataBean.getName());
                    userInfo.setBref_introduction(dataBean.getBref_introduction());
                    userInfo.setBackground(dataBean.getBackground());
                    userInfo.setCount_of_fan(dataBean.getCount_of_fan());
                    userInfo.setCount_of_visitor(dataBean.getCount_of_visitor());
                    if (dataBean.getHasAttention() == 0) {
                        userInfo.setHas_attention(false);
                    } else {
                        userInfo.setHas_attention(true);
                    }

                    userInCircle.setUserInfo(userInfo);

                    usersInCircle.add(userInCircle);
                }

                @Override
                public void onError(
                        Response<ApiResponse<CircleAttentionUserListEntity>> response,
                        String error, ApiResponse<CircleAttentionUserListEntity> apiResponse) {
                    if (view.isActive()) {
                        isCached = false;
                        usersInCircle.clear();
                        bindDataToView();
                        view.showReqResult(error);
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        isCached = false;
                        usersInCircle.clear();
                        bindDataToView();
                        view.showReqResult("请求错误");
                    }
                }
            };
}
