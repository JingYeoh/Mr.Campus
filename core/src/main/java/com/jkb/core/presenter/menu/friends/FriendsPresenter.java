package com.jkb.core.presenter.menu.friends;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.contacts.FriendListEntity;
import com.jkb.core.contract.menu.data.FriendsData;
import com.jkb.core.contract.menu.friends.FriendsContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.menuRight.friends.FriendsRepertory;
import com.jkb.model.info.UserInfoSingleton;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import retrofit2.Response;

/**
 * 好友列表的P层
 * Created by JustKiddingBaby on 2016/9/6.
 */

public class FriendsPresenter implements FriendsContract.Presenter {

    private FriendsContract.View view;
    private FriendsRepertory repertory;

    //Data
    private PageControlEntity pageControl;
    private int action = ACTION_REFRESH;
    private static final int ACTION_REFRESH = 0;
    private static final int ACTION_LOADMORE = 1;
    private boolean isLoading = false;//正在加载

    private boolean isCached = false;//是否缓存
    private List<FriendsData> friendsDatas;

    public FriendsPresenter(@NonNull FriendsContract.View view, @NonNull FriendsRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        pageControl = new PageControlEntity();//初始化页码控制器
        friendsDatas = new ArrayList<>();

        this.view.setPresenter(this);
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        view.showRefreshingView();//设置刷新动画
        action = ACTION_REFRESH;
        pageControl.setCurrent_page(1);
        getFriendsData();
    }

    @Override
    public void onLoadMore() {
        if (isLoading) {
            return;
        }
        action = ACTION_LOADMORE;
        if (pageControl.getCurrent_page() >= pageControl.getLast_page()) {
            return;
        }
        //设置当前页数+1
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        getFriendsData();
    }

    @Override
    public void bindData() {
        isCached = true;
        //绑定缓存的数据
        if (!view.isActive()) {
            return;
        }
        //绑定数据到视图中
        if (friendsDatas == null || friendsDatas.size() == 0) {
            view.showNonFriendsDataView();
        } else {
            view.setFriendsData(friendsDatas);
        }
    }

    @Override
    public void initFriendsData() {
        if (!LoginContext.getInstance().isLogined()) {
            return;
        }
        if (isCached) {
            bindData();
        } else {
            //请求数据
            getFriendsData();
        }
    }

    @Override
    public int getUserId(int position) {
        return friendsDatas.get(position).getId();
    }

    @Override
    public void start() {
        initFriendsData();//初始化数据
    }

    /**
     * 请求好友数据
     */
    public void getFriendsData() {
        UserAuths userAuths = getUserAuths();
        String Authorization = Config.HEADER_BEARER + userAuths.getToken();
        repertory.getFriendsList(Authorization, pageControl.getCurrent_page(), friendsApiCallback);
    }

    /**
     * 获取好友列表的数据回调类
     */
    private ApiCallback<ApiResponse<FriendListEntity>> friendsApiCallback =
            new ApiCallback<ApiResponse<FriendListEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<FriendListEntity>> response) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.hideRefreshingView();
                        handleData(response.body());
                        bindData();//绑定数据
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(ApiResponse<FriendListEntity> body) {
                    if (body == null) {
                        return;
                    }
                    //处理数据
                    FriendListEntity entity = body.getMsg();
                    handleFriendsData(entity);
                }

                /**
                 * 处理好友数据
                 */
                private void handleFriendsData(FriendListEntity entity) {
                    if (entity == null) {
                        return;
                    }

                    //设置页码控制器
                    pageControl.setTotal(entity.getTotal());
                    pageControl.setPer_page(entity.getPer_page());
                    pageControl.setCurrent_page(entity.getCurrent_page());
                    pageControl.setLast_page(entity.getLast_page());
                    pageControl.setNext_page_url(entity.getNext_page_url());
                    pageControl.setPrev_page_url(entity.getPrev_page_url());
                    pageControl.setFrom(entity.getFrom());
                    pageControl.setTo(entity.getTo());

                    //绑定数据到缓存类中
                    List<FriendListEntity.DataBean> dataBean = entity.getData();
                    if (dataBean == null || dataBean.size() == 0) {
                        return;
                    }
                    //判断操作动作
                    switch (action) {
                        case ACTION_REFRESH://刷新
                            friendsDatas.clear();
                            break;
                        case ACTION_LOADMORE://加载
                            break;
                    }
                    for (FriendListEntity.DataBean bean : dataBean) {
                        friendsDatas.add(changeData(bean));
                    }
                }

                /**
                 * 转换数据
                 */
                private FriendsData changeData(FriendListEntity.DataBean bean) {
                    if (bean == null) {
                        return null;
                    }
                    FriendsData friendsData = new FriendsData();
                    friendsData.setId(bean.getId());
                    friendsData.setSex(bean.getSex());
                    friendsData.setBref(bean.getBref_introduction());
                    friendsData.setHeadImgUrl(bean.getAvatar());
                    friendsData.setNickName(bean.getNickname());
                    return friendsData;
                }

                @Override
                public void onError(Response<ApiResponse<FriendListEntity>> response,
                                    String error, ApiResponse<FriendListEntity> apiResponse) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.hideRefreshingView();
                        view.showReqResult("数据获取错误，请重试");
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.hideRefreshingView();
                        view.showReqResult("获取数据失败，请检测您的网络连接");
                    }
                }
            };

    /**
     * 得到用户数据
     */
    private UserAuths getUserAuths() {
        UserInfoSingleton userInfo = UserInfoSingleton.getInstance();
        UserAuths userAuths = userInfo.getUserAuths();
        if (userAuths == null) {
            LoginContext.getInstance().setUserState(new LogoutState());
            view.showReqResult("登录过期，请重新登录~");
        }
        return userAuths;
    }
}
