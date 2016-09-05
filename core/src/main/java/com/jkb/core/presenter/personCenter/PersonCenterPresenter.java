package com.jkb.core.presenter.personCenter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.operation.OperationVerifyPayAttentionEntity;
import com.jkb.api.entity.user.UserActionCircleEntity;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.core.contract.personCenter.PersonCenterContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.core.presenter.personCenter.data.CircleData;
import com.jkb.core.presenter.personCenter.data.UserData;
import com.jkb.model.dataSource.personCenter.PersonCenterDataResponsitory;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.intfc.BitmapLoadedCallback;
import com.jkb.model.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import retrofit2.Response;

/**
 * 个人中心的P层
 * Created by JustKiddingBaby on 2016/8/15.
 */

public class PersonCenterPresenter implements PersonCenterContract.Presenter {

    private PersonCenterContract.View view;
    private PersonCenterDataResponsitory responsitory;
    //data
    private int user_id = -1;
    private boolean isReqVisited = false;//是否调用访问接口
    private List<CircleData> circleDatas;//View层可用的圈子数据
    private UserData userData;//要缓存的用户数据
    private boolean isOverdue = false;//数据是否过期
    private static final String TAG = "PersonCenterPresenter";


    public PersonCenterPresenter(@NonNull PersonCenterContract.View view,
                                 @NonNull PersonCenterDataResponsitory responsitory) {
        this.view = view;
        this.responsitory = responsitory;

        circleDatas = new ArrayList<>();

        view.setPresenter(this);
    }

    @Override
    public void getUserData() {
        Log.d(TAG, "getUserData----overDue=" + isOverdue);
        //设置缓存的数据
        if (userData != null && !isOverdue) {
            bindDataToView();
            return;
        }
        view.showRefreshingView();//设置加载的动画
        responsitory.getUserInfo(user_id, userInfoApiCallback);
    }


    /**
     * 获取用户数据的回调接口
     */
    private ApiCallback<ApiResponse<UserInfoEntity>> userInfoApiCallback =
            new ApiCallback<ApiResponse<UserInfoEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<UserInfoEntity>> response) {
                    if (view.isActive()) {
                        view.hideRefreshingView();
                        //处理数据并显示
                        handleUserData(response.body());
                    }
                }

                /**
                 * 处理用户数据
                 */
                private void handleUserData(ApiResponse<UserInfoEntity> body) {
                    UserInfoEntity entity = body.getMsg();
                    UserInfoEntity.UserInfoBean infoBean = entity.getUserInfo();
                    if (infoBean == null) {
                        return;
                    }
                    //设置数据未过期
                    isOverdue = false;
                    //初始化缓存数据对象
                    userData = new UserData();
                    //获取圈子数据
                    getCircleData();

                    //判断是否设置头像
                    if (!StringUtils.isEmpty(infoBean.getAvatar())) {
                        //加载头像
                        responsitory.loadHeadImgByUrl(infoBean.getAvatar(), bitmapLoadedCallback);
                    }
                    //加載背景圖片
                    if (!StringUtils.isEmpty(infoBean.getBackground())) {
                        responsitory.loadHeadImgByUrl(infoBean.getBackground(), backGroundBitmapCallback);
                    }

                    String name = (StringUtils.isEmpty(infoBean.getName()) ?
                            infoBean.getNickname() + "菌" : infoBean.getName());
                    //设置缓存的数据
                    userData.setName(name);
                    userData.setNickName(infoBean.getNickname());
                    userData.setSign(infoBean.getBref_introduction());
                    userData.setFansCount(infoBean.getFansCount());
                    userData.setAttentionUserCount(infoBean.getAttentionUserCount());
                    userData.setVisitorCount(infoBean.getVisitorCount());
                    //绑定数据
                    bindDataToView();
                }

                @Override
                public void onError(
                        Response<ApiResponse<UserInfoEntity>> response, String error,
                        ApiResponse<UserInfoEntity> apiResponse) {
                    if (view.isActive()) {
                        view.hideRefreshingView();
                        view.showReqResult("请求失败");
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.hideRefreshingView();
                        view.showReqResult("请求失败，请检查您的网络连接是否正常");
                    }
                }
            };
    /**
     * 头像加载的回调
     */
    private BitmapLoadedCallback bitmapLoadedCallback = new BitmapLoadedCallback() {
        @Override
        public void onBitmapDataLoaded(Bitmap bitmap) {
            if (view.isActive()) {
                userData.setHeadImg(bitmap);
                bindDataToView();
            }
        }

        @Override
        public void onDataNotAvailable(String url) {
        }
    };
    /**
     * 背景图片的回调
     */
    private BitmapLoadedCallback backGroundBitmapCallback = new BitmapLoadedCallback() {
        @Override
        public void onBitmapDataLoaded(Bitmap bitmap) {
            if (view.isActive()) {
                userData.setBackGround(bitmap);
                bindDataToView();
            }
        }

        @Override
        public void onDataNotAvailable(String url) {
        }
    };
    /**
     * 访客的回调接口
     */
    private ApiCallback<ApiResponse<OperationActionEntity>> visitorApiCallback = new
            ApiCallback<ApiResponse<OperationActionEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                    isReqVisited = true;
                    getUserData();
                }

                @Override
                public void onError(Response<ApiResponse<OperationActionEntity>> response,
                                    String error, ApiResponse<OperationActionEntity> apiResponse) {
                    getUserData();
                }

                @Override
                public void onFail() {
                    getUserData();
                }
            };
    /**
     * 获取订阅的圈子数据
     */
    private ApiCallback<ApiResponse<UserActionCircleEntity>> subscribeCircleApiCallback =
            new ApiCallback<ApiResponse<UserActionCircleEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<UserActionCircleEntity>> response) {
                    //设置数据
                    if (view.isActive()) {
                        handleSubscribeCircleData(response.body());
                    }
                }

                /**
                 * 处理返回的结果
                 */
                private void handleSubscribeCircleData(ApiResponse<UserActionCircleEntity> body) {
                    if (body == null) {
                        view.showCircleNonDataView();
                        return;
                    }
                    UserActionCircleEntity entity = body.getMsg();
                    if (entity == null) {
                        userData.setCircleDatas(null);
                        view.showCircleNonDataView();
                        return;
                    }
                    List<UserActionCircleEntity.DataBean> circles = entity.getData();
                    if (circles == null || circles.size() == 0) {
                        userData.setCircleDatas(null);
                    } else {
                        //设置数据进去
                        userData.setCircleDatas(changeToCircleData(circles));
                    }
                    bindDataToView();
                }

                /**
                 * 转换为可用的数据
                 */
                private List<CircleData> changeToCircleData(
                        List<UserActionCircleEntity.DataBean> circles) {
                    circleDatas.clear();
                    for (int i = 0; i < circles.size(); i++) {
                        CircleData data = new CircleData();
                        UserActionCircleEntity.DataBean bean = circles.get(i);
                        data.setCircleName(bean.getName());
                        data.setCircleType(bean.getType());
                        data.setPictureUrl(bean.getPicture());
                        data.setDynamics_count(bean.getDynamics_count());
                        data.setOperation_count(bean.getOperation_count());
                        data.setCircleId(bean.getId());
                        circleDatas.add(data);
                    }
                    return circleDatas;
                }

                @Override
                public void onError(
                        Response<ApiResponse<UserActionCircleEntity>> response,
                        String error, ApiResponse<UserActionCircleEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showCircleNonDataView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showCircleNonDataView();
                    }
                }
            };
    /**
     * 是否被关注的回调
     */
    private ApiCallback<ApiResponse<OperationVerifyPayAttentionEntity>> ifPayAttentionApiCallback =
            new ApiCallback<ApiResponse<OperationVerifyPayAttentionEntity>>() {
                @Override
                public void onSuccess(
                        Response<ApiResponse<OperationVerifyPayAttentionEntity>> response) {
                    if (view.isActive()) {
                        //判断是否被关注
                        handleVerifyIfPayAttention(response.body());
                    }
                }

                /**
                 * 解析是否被关注的返回数据
                 */
                private void handleVerifyIfPayAttention(
                        ApiResponse<OperationVerifyPayAttentionEntity> body) {
                    if (body == null) {
                        return;
                    }
                    OperationVerifyPayAttentionEntity entity = body.getMsg();
                    if (entity == null) {
                        return;
                    }
                    int hasPayAttention = entity.getHasPayAttention();
                    if (hasPayAttention > 0) {//被关注
                        view.showPayAttentionView();
                    } else {//没有被关注
                        view.showUnPayAttentionView();
                    }
                }

                @Override
                public void onError(
                        Response<ApiResponse<OperationVerifyPayAttentionEntity>> response,
                        String error, ApiResponse<OperationVerifyPayAttentionEntity> apiResponse) {
                    view.showUnPayAttentionView();
                }

                @Override
                public void onFail() {
                    view.showUnPayAttentionView();
                }
            };
    /**
     * 关注/取消关注接口
     */
    private ApiCallback<ApiResponse<OperationActionEntity>> payAttentionOrCancleApiCallback =
            new ApiCallback<ApiResponse<OperationActionEntity>>() {
                @Override
                public void onSuccess(
                        Response<ApiResponse<OperationActionEntity>> response) {
                    if (view.isActive()) {
                        verifyIfPayAttention();
                    }
                }

                @Override
                public void onError(
                        Response<ApiResponse<OperationActionEntity>> response,
                        String error, ApiResponse<OperationActionEntity> apiResponse) {
                    if (view.isActive()) {
                        verifyIfPayAttention();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        verifyIfPayAttention();
                    }
                }
            };

    /**
     * 绑定数据到view中
     */
    private void bindDataToView() {
        if (!view.isActive()) {
            return;
        }
        view.showContentView();//显示视图
        view.setUserName(userData.getNickName());
        view.setUserSign(userData.getSign());
        view.setName(userData.getName());
        //设置关注栏的数据
        view.setFansNum(userData.getFansCount());
        view.setVistiorsNum(userData.getVisitorCount());
        view.setWatchedNum(userData.getAttentionUserCount());
        if (userData.getHeadImg() != null) {
            view.setHeadImg(userData.getHeadImg());//设置头像
        }
        if (userData.getBackGround() != null) {
            view.setBackGround(userData.getBackGround());//设置背景图片
        }
        if (userData.getCircleDatas() == null || userData.getCircleDatas().size() == 0) {
            view.showCircleNonDataView();
        } else {
            //设置圈子
            view.setCircleViewData(userData.getCircleDatas());
        }
    }

    @Override
    public void judgeUserToSetTitleStyle() {
        if (LoginContext.getInstance().isLogined()) {
            Users users = getUsers();
            if (user_id == users.getUser_id()) {
                //设置自己的样式
                view.setSelfConfig();
            } else {
                //设置非自己的样式
                view.setNonSelfConfig();
                verifyIfPayAttention();//请求是否被关注
                return;
            }
        } else {
            //设置非自己的样式
            view.setNonSelfConfig();
        }
    }

    @Override
    public void getCircleData() {
        //从网上获取圈子数据
        //只获取第一页的数据
        Users users = getUsers();
        int visitor_id = users.getUser_id();
        responsitory.subscribeCircle(user_id, visitor_id, 1, subscribeCircleApiCallback);
    }

    @Override
    public int getCircleId(int position) {
        CircleData data = userData.getCircleDatas().get(position);
        if (data == null) {
            return 0;
        }
        return data.getCircleId();
    }

    @Override
    public void visit() {
        if (!LoginContext.getInstance().isLogined()) {//判断是否登录
            getUserData();//获取用户数据
            return;
        }
        //判断是否调用过访客的接口,避免重复调用
        if (isReqVisited) {
            getUserData();
            return;
        }
        UserAuths auths = getUserAuths();
        String Authorization = Config.HEADER_BEARER + auths.getToken();
        responsitory.visit(Authorization, auths.getUser_id(), user_id, visitorApiCallback);
    }

    @Override
    public void verifyIfPayAttention() {
        //得到访客id，即自己的id
        Users users = getUsers();
        if (users == null) {
            return;
        }
        int visitor_id = users.getUser_id();
        //判断是否被关注
        responsitory.verifyIfPayAttention(user_id, visitor_id, ifPayAttentionApiCallback);
    }

    @Override
    public void payAttentionOrCancle() {
        if (!LoginContext.getInstance().isLogined()) {
            //显示未登录视图
            view.showReqResult("未登录，请您先去登录~");
        }
        UserAuths auths = getUserAuths();
        if (auths == null) {
            return;
        }
        String Authorization = Config.HEADER_BEARER + auths.getToken();
        //关注或者取消关注
        responsitory.payAttentionOrCancle(Authorization, auths.getUser_id(), user_id,
                payAttentionOrCancleApiCallback);
    }

    @Override
    public void onRefresh() {
        //刷新数据的时候
        isOverdue = true;//设置数据过期
        getUserData();
    }

    @Override
    public void notifyDataChanged() {
        Log.d(TAG, "notifyDataChanged");
        isOverdue = true;//设置数据过期
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

    @Override
    public void start() {
        user_id = view.getUser_id();//得到用户的id
        view.hideContentView();//隐藏显示
        judgeUserToSetTitleStyle();//设置标题栏样式
        visit();//请求访客接口
    }
}
