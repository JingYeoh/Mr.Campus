package com.jkb.core.presenter.usersList;

import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserActionUserEntity;
import com.jkb.core.contract.usersList.AttentionContract;
import com.jkb.core.presenter.usersList.data.UserData;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.usersList.attention.AttentionDataResponsitory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * 关注用户列表的P层
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class AttentionPresenter implements AttentionContract.Presenter {

    private static final String TAG = "AttentionPresenter";
    private AttentionContract.View view;
    private AttentionDataResponsitory responsitory;

    //Data
    private int user_id = -1;
    private PageControlEntity pageControl;
    private int action = ACTION_REFRESH;
    private static final int ACTION_REFRESH = 0;
    private static final int ACTION_LOADMORE = 1;
    private boolean isLoading = false;//正在加载

    private List<UserActionUserEntity.UserBean.DataBean> users;//用户数据


    public AttentionPresenter(AttentionContract.View view, AttentionDataResponsitory responsitory) {
        this.view = view;
        this.responsitory = responsitory;

        //初始化页数控制器
        pageControl = new PageControlEntity();
        users = new ArrayList<>();//初始化用户的数据容器

        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        //得到用户id
        getUser_id();
        //请求数据
        onRefresh();//刷新数据
    }

    @Override
    public void onUserItemClicked(int position) {

    }

    @Override
    public void getUser_id() {
        user_id = view.getUser_id();
    }

    @Override
    public void getAttentionUsersListData() {
        isLoading = true;
        responsitory.payAttention(
                pageControl.getCurrent_page(),
                user_id, apiCallback);
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        view.showRefreshing();//设置刷新动画
        action = ACTION_REFRESH;
        pageControl.setCurrent_page(0);
        getAttentionUsersListData();
    }

    @Override
    public void onLoaded() {
        if (isLoading) {
            return;
        }
        action = ACTION_LOADMORE;
        if (pageControl.getCurrent_page() == pageControl.getLast_page()) {
//            view.showReqResult("无更多数据");
            return;
        }
        //设置当前页数+1
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        getAttentionUsersListData();
    }

    /**
     * 下拉刷新回调方法
     */
    private ApiCallback<ApiResponse<UserActionUserEntity>> apiCallback = new ApiCallback<ApiResponse<UserActionUserEntity>>() {
        @Override
        public void onSuccess(Response<ApiResponse<UserActionUserEntity>> response) {
            isLoading = false;
            if (view.isActive()) {
                view.dismissRefresh$Loaded();
                handleUserActionEntity(response.body());
            }
        }

        @Override
        public void onError(Response<ApiResponse<UserActionUserEntity>> response,
                            String error, ApiResponse<UserActionUserEntity> apiResponse) {
            isLoading = false;
            if (view.isActive()) {
                view.dismissRefresh$Loaded();
                view.showReqResult("请求错误，请重试");
                pageControl.setCurrent_page(pageControl.getCurrent_page() - 1);
            }
        }

        @Override
        public void onFail() {
            isLoading = false;
            if (view.isActive()) {
                view.dismissRefresh$Loaded();
                pageControl.setCurrent_page(pageControl.getCurrent_page() - 1);
                view.showReqResult("请求失败，请检查您的网络");
            }
        }
    };

    /**
     * 解析数据
     */
    private void handleUserActionEntity(ApiResponse<UserActionUserEntity> body) {
        UserActionUserEntity entity = body.getMsg();
        if (entity == null) {
            return;
        }
        UserActionUserEntity.UserBean userBean = entity.getUser();
        if (userBean == null) {
            return;
        }
        pageControl.setTotal(userBean.getTotal());
        pageControl.setPer_page(userBean.getPer_page());
        pageControl.setCurrent_page(userBean.getCurrent_page());
        pageControl.setLast_page(userBean.getLast_page());
        pageControl.setNext_page_url(userBean.getNext_page_url());
        pageControl.setPrev_page_url(userBean.getPrev_page_url());
        pageControl.setFrom(userBean.getFrom());
        pageControl.setTo(userBean.getTo());

        Log.d(TAG, pageControl.toString());
        //处理数据
        handleUserData(userBean);
        //更新数据
        view.updataViewData(getUsersData());
    }

    /**
     * 处理用户数据
     */
    private void handleUserData(UserActionUserEntity.UserBean userBean) {
        //判断操作动作
        switch (action) {
            case ACTION_REFRESH://刷新
                users.clear();
                break;
            case ACTION_LOADMORE://加载
                break;
        }
        //更新数据进去
        List<UserActionUserEntity.UserBean.DataBean> dataBeen = userBean.getData();
        if (dataBeen == null) {
            return;
        }
        for (int i = 0; i < dataBeen.size(); i++) {
            users.add(dataBeen.get(i));
        }
    }

    /**
     * 转换当前数据到userData数据
     */
    public List<UserData> getUsersData() {
        List<UserData> userDatas = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            UserData data = new UserData();
            UserActionUserEntity.UserBean.DataBean bean = users.get(i);
            data.setAttentioned(false);
            data.setAvatar(bean.getAvatar());
            data.setBref_introduction(bean.getBref_introduction());
            data.setNickname(bean.getNickname());
            data.setSex(bean.getSex());
            userDatas.add(data);
        }
        return userDatas;
    }
}
