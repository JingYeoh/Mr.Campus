package com.jkb.core.presenter.usersList;

import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.user.UserActionUserEntity;
import com.jkb.core.contract.usersList.AttentionContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.core.presenter.usersList.data.UserData;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.usersList.attention.AttentionDataResponsitory;
import com.jkb.model.info.UserInfoSingleton;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
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
    private List<UserData> userDatas;//转换后要传递的用户数据


    public AttentionPresenter(AttentionContract.View view, AttentionDataResponsitory responsitory) {
        this.view = view;
        this.responsitory = responsitory;

        //初始化页数控制器
        pageControl = new PageControlEntity();
        users = new ArrayList<>();//初始化用户的数据容器
        userDatas = new ArrayList<>();//初始化要传递的用户数据容器

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
    public void onPayAttentionCLicked(int position) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("登录后才能操作哦~");
            return;
        }
        //请求关注接口
        UserActionUserEntity.UserBean.DataBean user = users.get(position);
        payAttentionOrCancle(user.getId());
        //刷新数据
    }

    @Override
    public void onHeadImgClicked(int position) {
        //得到用户id
        UserActionUserEntity.UserBean.DataBean user = users.get(position);
        int user_id = user.getId();
        Log.d(TAG, "user_id=" + user_id);
        view.showPersonCenter(user_id);
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
    public void payAttentionOrCancle(int target_id) {
        //得到操作者id
        UserInfoSingleton userInfo = UserInfoSingleton.getInstance();
        UserAuths auths = userInfo.getUserAuths();
        if (auths == null) {
            view.showReqResult("登录过期请重新登录");
            LoginContext.getInstance().setUserState(new LogoutState());
            return;
        }
        String Authorization = Config.HEADER_BEARER + auths.getToken();
        view.showLoading("");
        //请求关注接口
        responsitory.payAttentionOrCancle(Authorization, auths.getUser_id(), target_id,
                operationSubscribeApiCallback);
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        view.showRefreshing();//设置刷新动画
        action = ACTION_REFRESH;
        pageControl.setCurrent_page(1);
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
     * 请求关注接口的回调
     */
    private ApiCallback<ApiResponse<OperationActionEntity>> operationSubscribeApiCallback = new
            ApiCallback<ApiResponse<OperationActionEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        //请求成功更新数据
                        onRefresh();
                    }
                }

                @Override
                public void onError(Response<ApiResponse<OperationActionEntity>> response,
                                    String error, ApiResponse<OperationActionEntity> apiResponse) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("请求错误，请重试");
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("请求失败，请检查您的网络连接");
                    }
                }
            };

    /**
     * 下拉刷新回调方法
     */
    private ApiCallback<ApiResponse<UserActionUserEntity>> apiCallback = new
            ApiCallback<ApiResponse<UserActionUserEntity>>() {
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
        userDatas.clear();
        for (int i = 0; i < users.size(); i++) {
            UserData data = new UserData();
            UserActionUserEntity.UserBean.DataBean bean = users.get(i);
            //是否被关注
            data.setAttentioned(true);
            data.setAvatar(bean.getAvatar());
            data.setBref_introduction(bean.getBref_introduction());
            data.setNickname(bean.getNickname());
            data.setSex(bean.getSex());
            userDatas.add(data);
        }
        return userDatas;
    }
}
