package com.jkb.core.presenter.circle;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.entity.circle.DynamicInCircleListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.core.contract.circle.CircleIndexContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.core.data.dynamic.circle.DynamicInCircle;
import com.jkb.core.data.dynamic.circle.original.DynamicInCircleOriginalArticle;
import com.jkb.core.data.dynamic.circle.original.DynamicInCircleOriginalNormal;
import com.jkb.core.data.dynamic.circle.original.DynamicInCircleOriginalTopic;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.circle.circleIndex.CircleIndexDataRepertory;
import com.jkb.model.dataSource.circle.data.CircleIndexData;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import retrofit2.Response;

/**
 * 圈子首页的P层
 * Created by JustKiddingBaby on 2016/8/29.
 */

public class CircleIndexPresenter implements CircleIndexContract.Presenter {

    private static final String TAG = "CircleIndexPresenter";
    private CircleIndexContract.View view;
    private CircleIndexDataRepertory repertory;

    //data
    private int circle_id = 0;
    private boolean isCached = false;//是否有缓存的数据
    private CircleIndexData circleIndexData = null;
    private List<DynamicInCircle> dynamicInCircles;

    //分页控制器
    private boolean isRefreshing = false;
    private PageControlEntity pageControl;

    public CircleIndexPresenter(
            @NonNull CircleIndexContract.View view,
            @NonNull CircleIndexDataRepertory responsitiry) {
        this.view = view;
        this.repertory = responsitiry;

        dynamicInCircles = new ArrayList<>();
        pageControl = new PageControlEntity();
        circleIndexData = new CircleIndexData();

        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        //初始化数据
        initCircleData();
    }

    @Override
    public void onRefresh() {
        if (isRefreshing) {
            return;
        }
        view.showRefreshingView();
        //刷新数据
        isCached = false;
        isRefreshing = true;
        dynamicInCircles.clear();
        pageControl.setCurrent_page(1);
        getCircleData();//请求圈子信息
    }

    @Override
    public void onLoadMore() {
        if (isRefreshing) {
            return;
        }
        //刷新数据
        isCached = false;
        isRefreshing = true;
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        initDynamicInCircle();//初始化圈子中动态数据
    }

    @Override
    public void initCircleId() {
        circle_id = view.provideCircleId();
    }

    @Override
    public void initCircleData() {
        initCircleId();
        //初始化数据
        if (isCached) {
            bindData();
        } else {
            view.hideContentView();
            //请求网络数据
            onRefresh();
        }
    }

    @Override
    public void initDynamicInCircle() {
        //请求圈子内动态数据
        String Authorization = null;
        if (LoginContext.getInstance().isLogined()) {
            Authorization = Config.HEADER_BEARER + getUserAuths().getToken();
        }
        repertory.getAllDynamicInCircle(Authorization, circle_id, pageControl.getCurrent_page(),
                dynamicInCircleApiCallback);
    }

    @Override
    public void bindData() {
        isCached = true;
        isRefreshing = false;
        if (!view.isActive()) {
            return;
        }
        view.showContentView();
        view.dismissLoading();
        view.hideRefreshingView();

        view.setCircleName(circleIndexData.getCircleName());
        view.setCircleType(circleIndexData.getCircleType());
        view.setCircleIntroduction(circleIndexData.getCircleIntroducton());
        view.setCircleOperation_count(circleIndexData.getDynamicsCount());
        view.setCircleSubscribe_count(circleIndexData.getSubsribeCount());
        if (circleIndexData.getPicture() != null) {
            view.setCirclePicture(circleIndexData.getPicture());
        }
        view.setSubscribeStatus(circleIndexData.isHasSubscribe());//设置是否有状态

        //设置圈子中动态
        view.setDynamicInCircle(dynamicInCircles);
    }

    @Override
    public void subscribeOrCancleCircle() {
        Log.d(TAG, "subscribeOrCancleCircle");
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("您未登录，请先去登录");
            return;
        }
        UserAuths auths = getUserAuths();
        if (auths == null) {
            return;
        }
        int userId = auths.getUser_id();
        String Authorization = Config.HEADER_BEARER + auths.getToken();
        view.showLoading("");
        //订阅或者取消订阅的操作
        repertory.circleSubscribeOrNot(userId, circle_id, Authorization, subscribeApiCallBack);
    }

    @Override
    public void onDynamicInCircleItemClick(int position) {
        DynamicInCircle dynamicInCircle = dynamicInCircles.get(position);
        int dynamic_id = dynamicInCircle.getDynamic_id();
        if (dynamicInCircle instanceof DynamicInCircleOriginalArticle) {
            view.startDynamicActivity(dynamic_id, Config.DYNAMIC_TYPE_ARTICLE);
        } else if (dynamicInCircle instanceof DynamicInCircleOriginalNormal) {
            view.startDynamicActivity(dynamic_id, Config.DYNAMIC_TYPE_NORMAL);
        } else if (dynamicInCircle instanceof DynamicInCircleOriginalTopic) {
            view.startDynamicActivity(dynamic_id, Config.DYNAMIC_TYPE_TOPIC);
        }
    }

    @Override
    public void onHeadImgItemClick(int position) {
        DynamicInCircle dynamicInCircle = dynamicInCircles.get(position);
        UserInfo user = dynamicInCircle.getUser();
        int id = user.getId();
        view.startPersonCenter(id);
    }

    @Override
    public void onCommentItemClick(int position) {
        DynamicInCircle dynamicInCircle = dynamicInCircles.get(position);
        int dynamic_id = dynamicInCircle.getDynamic_id();
        view.startCommentActivity(dynamic_id);
    }

    @Override
    public void onLikeItemClick(final int position) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请先去登录再进行操作");
            return;
        }
        DynamicInCircle dynamicInCircle = dynamicInCircles.get(position);
        int dynamic_id = dynamicInCircle.getDynamic_id();
        //请求喜欢操作
        UserAuths userAuths = getUserAuths();
        int userId = userAuths.getUser_id();
        String Authorization = Config.HEADER_BEARER + userAuths.getToken();
        repertory.favorite(Authorization, userId, dynamic_id,
                new ApiCallback<ApiResponse<OperationActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                        if (view.isActive()) {
                            view.showReqResult("操作成功");
                            DynamicInCircle dynamicInCircle = dynamicInCircles.get(position);
                            boolean has_favorite = dynamicInCircle.isHas_favorite();
                            has_favorite = !has_favorite;
                            dynamicInCircle.setHas_favorite(has_favorite);
                            int count_of_favorite = dynamicInCircle.getCount_of_favorite();
                            if (has_favorite) {
                                count_of_favorite += 1;
                            } else {
                                count_of_favorite -= 1;
                            }
                            dynamicInCircle.setCount_of_favorite(count_of_favorite);
                            bindData();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResponse<OperationActionEntity>> response,
                                        String error, ApiResponse<OperationActionEntity> apiResponse) {
                        if (view.isActive()) {
                            view.showReqResult("操作失败");
                        }
                    }

                    @Override
                    public void onFail() {
                        if (view.isActive()) {
                            view.showReqResult("操作失败");
                        }
                    }
                });
    }

    @Override
    public void onJoinChatRoomClick() {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请您先去登录再进行操作");
            return;
        }
        if (circleIndexData == null) {
            view.showReqResult("获取圈子数据失败");
            return;
        }
        if (!view.isActive()) {
            return;
        }
        //判断是否关注该圈子
        boolean hasSubscribe = circleIndexData.isHasSubscribe();
        LogUtils.d(CircleIndexPresenter.class, "是否关注状态是" + hasSubscribe);
        if (hasSubscribe) {
            view.showHintForJoinChatRoot(circleIndexData.getCircleName());
        } else {
            view.showHintForPayAttentionCircle();
        }
    }

    @Override
    public void onCircleSettingClick() {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请您先去登录再进行操作");
            return;
        }
        if (circleIndexData == null) {
            view.showReqResult("获取圈子数据失败");
            return;
        }
        if (!view.isActive()) {
            return;
        }
        int user_id = circleIndexData.getUser_id();
        UserAuths userAuths = getUserAuths();
        Integer mUser_id = userAuths.getUser_id();
        if (user_id == mUser_id) {
            view.showUserCircleSetting();
        } else {
            view.showVisitorCircleSetting();
        }
    }

    /**
     * 得到圈子数据
     */
    public void getCircleData() {
        int userId = 0;
        if (LoginContext.getInstance().isLogined()) {
            Users users = getUsers();
            userId = users.getUser_id();
        }
        view.showLoading("");
        repertory.getCircleInfo(userId, circle_id, circleIndexApiCallback);
    }

    /**
     * 圈子数据的回调
     */
    private ApiCallback<ApiResponse<CircleInfoEntity>> circleIndexApiCallback =
            new ApiCallback<ApiResponse<CircleInfoEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<CircleInfoEntity>> response) {
                    if (view.isActive()) {
                        handleCircleInfoData(response.body().getMsg());
                        initDynamicInCircle();//请求圈子中动态数据
                    }
                }

                /**
                 * 处理圈子数据
                 */
                private void handleCircleInfoData(CircleInfoEntity entity) {
                    if (entity == null) {
                        return;
                    }
                    CircleInfoEntity.CircleBean bean = entity.getCircle();
                    if (bean == null) {
                        return;
                    }
                    changeCircleData(bean);
                    bindData();
                }

                /**
                 * 转换圈子数据为可用的数据
                 */
                private void changeCircleData(CircleInfoEntity.CircleBean bean) {
                    circleIndexData.setCircleName(bean.getName());
                    circleIndexData.setCircleType(bean.getType());
                    circleIndexData.setCircleIntroducton(bean.getIntroduction());
                    circleIndexData.setDynamicsCount(bean.getDynamics_count());
                    circleIndexData.setSubsribeCount(bean.getSubscribe_count());
                    if (bean.getHasSubscribe() == 0) {
                        circleIndexData.setHasSubscribe(false);
                    } else {
                        circleIndexData.setHasSubscribe(true);
                    }
                    circleIndexData.setPicture(bean.getPicture());
                    CircleInfoEntity.CircleBean.UserBean user = bean.getUser();
                    if (user != null) {
                        circleIndexData.setUser_id(user.getId());
                    }
                }


                @Override
                public void onError(Response<ApiResponse<CircleInfoEntity>> response, String error,
                                    ApiResponse<CircleInfoEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult("获取失败");
                        bindData();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("获取失败，请检查您的网络");
                        bindData();
                    }
                }
            };

    /**
     * 关注/取消关注圈子的回调
     */
    private ApiCallback<ApiResponse<OperationActionEntity>> subscribeApiCallBack =
            new ApiCallback<ApiResponse<OperationActionEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("操作成功");
                        circleIndexData.setHasSubscribe(!circleIndexData.isHasSubscribe());
                        bindData();
                    }
                }

                @Override
                public void onError(Response<ApiResponse<OperationActionEntity>> response,
                                    String error, ApiResponse<OperationActionEntity> apiResponse) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("操作失败");
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
     * 获取圈子内动态回调接口
     */
    private ApiCallback<ApiResponse<DynamicInCircleListEntity>> dynamicInCircleApiCallback =
            new ApiCallback<ApiResponse<DynamicInCircleListEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<DynamicInCircleListEntity>> response) {
                    if (view.isActive()) {
                        handleData(response.body());
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(ApiResponse<DynamicInCircleListEntity> body) {
                    if (body == null) {
                        dynamicInCircles.clear();
                        bindData();
                        return;
                    }
                    handleDynamicData(body.getMsg());
                }

                /**
                 * 解析动态数据
                 */
                private void handleDynamicData(DynamicInCircleListEntity msg) {
                    if (msg == null) {
                        dynamicInCircles.clear();
                        bindData();
                        return;
                    }

                    //设置页码控制器
                    pageControl.setTotal(msg.getTotal());
                    pageControl.setPer_page(msg.getPer_page());
                    pageControl.setCurrent_page(msg.getCurrent_page());
                    pageControl.setLast_page(msg.getLast_page());
                    pageControl.setNext_page_url(msg.getNext_page_url());
                    pageControl.setPrev_page_url(msg.getPrev_page_url());
                    pageControl.setFrom(msg.getFrom());
                    pageControl.setTo(msg.getTo());

                    List<DynamicInCircleListEntity.DataBean> data = msg.getData();
                    if (data == null || data.size() == 0) {
                        return;
                    }
                    for (DynamicInCircleListEntity.DataBean dataBean : data) {
                        DynamicInCircle dynamicInCircle =
                                new DynamicInCircle(dataBean).getDynamic();
                        if (dynamicInCircle != null) {
                            dynamicInCircles.add(dynamicInCircle);
                        }
                    }
                    bindData();
                }

                @Override
                public void onError(Response<ApiResponse<DynamicInCircleListEntity>> response,
                                    String error, ApiResponse<DynamicInCircleListEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult("得到圈子内动态失败");
                        bindData();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("得到圈子内动态失败");
                        bindData();
                    }
                }
            };

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
