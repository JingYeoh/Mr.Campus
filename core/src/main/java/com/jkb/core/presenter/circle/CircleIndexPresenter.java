package com.jkb.core.presenter.circle;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.core.contract.circle.CircleIndexContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.dataSource.circle.circleIndex.CircleIndexDataResponsitiry;
import com.jkb.model.dataSource.circle.data.CircleIndexData;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.intfc.BitmapLoadedCallback;
import com.jkb.model.utils.StringUtils;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import retrofit2.Response;

/**
 * 圈子首页的P层
 * Created by JustKiddingBaby on 2016/8/29.
 */

public class CircleIndexPresenter implements CircleIndexContract.Presenter {

    private CircleIndexContract.View view;
    private CircleIndexDataResponsitiry responsitiry;

    //data
    private static final String TAG = "CircleIndexPresenter";
    private int circle_id = 0;
    private boolean isCached = false;//是否有缓存的数据
    private CircleIndexData circleIndexData = null;

    public CircleIndexPresenter(
            @NonNull CircleIndexContract.View view,
            @NonNull CircleIndexDataResponsitiry responsitiry) {
        this.view = view;
        this.responsitiry = responsitiry;

        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        //初始化数据
        initCircleData();
    }

    @Override
    public void onRefresh() {
        //刷新数据
        isCached = false;
        initCircleData();
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
            getCircleData();
        }
    }

    @Override
    public void bindData() {
        if (!view.isActive()) {
            return;
        }
        view.setCircleName(circleIndexData.getCircleName());
        view.setCircleType(circleIndexData.getCircleType());
        view.setCircleIntroduction(circleIndexData.getCircleIntroducton());
        view.setCircleOperation_count(circleIndexData.getDynamicsCount());
        view.setCircleSubscribe_count(circleIndexData.getSubsribeCount());
        if (circleIndexData.getPicture() != null) {
            view.setCirclePicture(circleIndexData.getPicture());
        }
        view.setSubscribeStatus(circleIndexData.isHasSubscribe());//设置是否有状态
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
        responsitiry.circleSubscribeOrNot(userId, circle_id, Authorization, subscribeApiCallBack);
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
        responsitiry.getCircleInfo(userId, circle_id, circleIndexApiCallback);
    }

    /**
     * 圈子数据的回调
     */
    private ApiCallback<ApiResponse<CircleInfoEntity>> circleIndexApiCallback =
            new ApiCallback<ApiResponse<CircleInfoEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<CircleInfoEntity>> response) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showContentView();
                        handleCircleInfoData(response.body().getMsg());
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
                    //设置数据有缓存
                    isCached = true;
                    //绑定数据到用到的数据类中
                    if (circleIndexData == null) {
                        circleIndexData = new CircleIndexData();
                    }
                    changeCircleData(bean);
                    //请求加载图片数据
//                    String pictureUrl = bean.getPicture();
//                    loadPicure(pictureUrl);
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
                }

                /**
                 * 加载图片
                 */
                @Deprecated
                private void loadPicure(String pictureUrl) {
                    if (StringUtils.isEmpty(pictureUrl)) {
                        return;
                    }
//                    responsitiry.loadBitmapByUrl(pictureUrl, bitmapLoadedCallback);

                }

                /**
                 * 加载图片的回调方法
                 */
                @Deprecated
                private BitmapLoadedCallback bitmapLoadedCallback = new BitmapLoadedCallback() {
                    @Override
                    public void onBitmapDataLoaded(Bitmap bitmap) {
//                        circleIndexData.setPicture(bitmap);
                        bindData();
                    }

                    @Override
                    public void onDataNotAvailable(String url) {
                        circleIndexData.setPicture(null);
                        bindData();
                    }
                };

                @Override
                public void onError(Response<ApiResponse<CircleInfoEntity>> response, String error,
                                    ApiResponse<CircleInfoEntity> apiResponse) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("获取失败");
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("获取失败，请检查您的网络");
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
