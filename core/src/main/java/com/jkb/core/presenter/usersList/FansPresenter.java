package com.jkb.core.presenter.usersList;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.operation.OperationUserEntity;
import com.jkb.core.contract.usersList.FansContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.core.presenter.usersList.data.UserData;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.usersList.fans.FansDataResponsitory;
import com.jkb.model.info.UserInfoSingleton;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import retrofit2.Response;

/**
 * 粉丝列表的P层
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class FansPresenter implements FansContract.Presenter {

    private static final String TAG = "AttentionPresenter";
    private FansContract.View view;
    private FansDataResponsitory responsitory;

    //Data
    private int user_id = -1;
    private PageControlEntity pageControl;
    private int action = ACTION_REFRESH;
    private static final int ACTION_REFRESH = 0;
    private static final int ACTION_LOADMORE = 1;
    private boolean isLoading = false;//正在加载

    private List<OperationUserEntity.DataBean> users;//用户数据
    private List<UserData> userDatas;//转换后要传递的用户数据
    private boolean isCached = false;//是否缓存

    public FansPresenter(@NonNull FansContract.View view,
                         @NonNull FansDataResponsitory responsitory) {
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
        readAllUnReadFansMessage();
        //得到用户数据
        getData();
    }

    /**
     * 得到数据
     */
    private void getData() {
        if (isCached) {//又缓存的数据
            //设置数据
            bindDataToView();
        } else {//否则缓存过期
            onRefresh();//刷新数据
        }
    }

    @Override
    public void readAllUnReadFansMessage() {
        if (!LoginContext.getInstance().isLogined()) {
            return;
        }
        Integer userId = UserInfoSingleton.getInstance().getUserAuths().getUser_id();
        if (userId == user_id) {
            view.readAllUnReadFansMessage();
        }
    }

    @Override
    public void onPayAttentionCLicked(int position) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("登录后才能操作哦~");
            return;
        }
        //请求关注接口
        OperationUserEntity.DataBean user = users.get(position);
        payAttentionOrCancle(user.getId());
    }

    @Override
    public void onHeadImgClicked(int position) {
        //得到用户id
        OperationUserEntity.DataBean user = users.get(position);
        int user_id = user.getId();
        Log.d(TAG, "user_id=" + user_id);
        view.showPersonCenter(user_id);
    }

    @Override
    public void getUser_id() {
        user_id = view.getUser_id();
    }

    @Override
    public void getFansUsersListData() {
        isLoading = true;
        int visitor_id = 0;
        if (LoginContext.getInstance().isLogined()) {
            UserAuths auths = getUserAuths();
            if (auths == null) {
                return;
            }
            visitor_id = auths.getUser_id();
        }
        responsitory.fans(visitor_id,
                pageControl.getCurrent_page(),
                user_id, apiCallback);
    }

    @Override
    public void bindDataToView() {
        //更新数据
        if (view.isActive()) {
            view.updataViewData(getUsersData());
        }
    }

    @Override
    public void payAttentionOrCancle(int target_id) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("您未登录，请先去登录才能操作");
            return;
        }
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
        getFansUsersListData();
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
        getFansUsersListData();
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
                        view.showReqResult(error);
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
    private ApiCallback<ApiResponse<OperationUserEntity>> apiCallback = new
            ApiCallback<ApiResponse<OperationUserEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<OperationUserEntity>> response) {
                    isLoading = false;
                    if (view.isActive()) {
                        view.dismissRefresh$Loaded();
                        handleUserActionEntity(response.body());
                    }
                }

                /**
                 * 解析数据
                 */
                private void handleUserActionEntity(ApiResponse<OperationUserEntity> body) {
                    OperationUserEntity entity = body.getMsg();
                    if (entity == null) {
                        return;
                    }

                    //设置为已缓存
                    isCached = true;

                    pageControl.setTotal(entity.getTotal());
                    pageControl.setPer_page(entity.getPer_page());
                    pageControl.setCurrent_page(entity.getCurrent_page());
                    pageControl.setLast_page(entity.getLast_page());
                    pageControl.setNext_page_url(entity.getNext_page_url());
                    pageControl.setPrev_page_url(entity.getPrev_page_url());
                    pageControl.setFrom(entity.getFrom());
                    pageControl.setTo(entity.getTo());

                    Log.d(TAG, pageControl.toString());
                    //处理数据
                    handleUserData(entity);

                    bindDataToView();
                }

                /**
                 * 处理用户数据
                 */
                private void handleUserData(OperationUserEntity userBean) {
                    //判断操作动作
                    switch (action) {
                        case ACTION_REFRESH://刷新
                            users.clear();
                            break;
                        case ACTION_LOADMORE://加载
                            break;
                    }
                    //更新数据进去
                    List<OperationUserEntity.DataBean> dataBeen = userBean.getData();
                    if (dataBeen == null) {
                        return;
                    }
                    for (int i = 0; i < dataBeen.size(); i++) {
                        users.add(dataBeen.get(i));
                    }
                }

                @Override
                public void onError(Response<ApiResponse<OperationUserEntity>> response,
                                    String error, ApiResponse<OperationUserEntity> apiResponse) {
                    isLoading = false;
                    if (view.isActive()) {
                        view.dismissRefresh$Loaded();
                        view.showReqResult(error);
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
     * 转换当前数据到userData数据
     */
    public List<UserData> getUsersData() {
        userDatas.clear();
        for (int i = 0; i < users.size(); i++) {
            UserData data = new UserData();
            OperationUserEntity.DataBean bean = users.get(i);

            //是否被关注
            if (bean.getHasPayAttention() == 1) {
                data.setAttentioned(true);
            } else {
                data.setAttentioned(false);
            }
            data.setAvatar(bean.getAvatar());
            data.setBref_introduction(bean.getBref_introduction());
            data.setNickname(bean.getNickname());
            data.setSex(bean.getSex());
            data.setTime(bean.getCreated_at());
            userDatas.add(data);
        }
        return userDatas;
    }

    /**
     * 得到用户数据
     */
    private Users getUsers() {
        UserInfoSingleton userInfo = UserInfoSingleton.getInstance();
        Users users = userInfo.getUsers();
        if (users == null) {
            LoginContext.getInstance().setUserState(new LogoutState());
            view.showReqResult("登录过期，请重新登录~");
        }
        return users;
    }

    /**
     * 得到用户Auth数据
     */
    private UserAuths getUserAuths() {
        UserInfoSingleton userInfo = UserInfoSingleton.getInstance();
        UserAuths auths = userInfo.getUserAuths();
        if (auths == null) {
            LoginContext.getInstance().setUserState(new LogoutState());
            view.showReqResult("登录过期，请重新登录~");
        }
        return auths;
    }
}
