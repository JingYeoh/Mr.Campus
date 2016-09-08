package com.jkb.core.presenter.circleList;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserActionCircleEntity;
import com.jkb.core.contract.circleList.CircleListContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.core.presenter.personCenter.data.CircleData;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.circleList.CircleListDataResponsitory;
import com.jkb.model.info.UserInfoSingleton;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.Users;
import retrofit2.Response;

/**
 * 圈子列表的P层
 * Created by JustKiddingBaby on 2016/9/2.
 */

public class CircleListPresenter implements CircleListContract.Presenter {

    private CircleListContract.View view;
    private CircleListDataResponsitory responsitory;

    public CircleListPresenter(@NonNull CircleListContract.View view,
                               @NonNull CircleListDataResponsitory responsitory) {
        this.view = view;
        this.responsitory = responsitory;

        pageControl = new PageControlEntity();
        circleDatas = new ArrayList<>();

        this.view.setPresenter(this);
    }

    //data
    private int user_id = 0;
    private List<CircleData> circleDatas;//转换后要传递的用户数据
    private boolean isCached = false;//是否缓存
    //分页
    private PageControlEntity pageControl;
    private int action = ACTION_REFRESH;
    private static final int ACTION_REFRESH = 0;
    private static final int ACTION_LOADMORE = 1;
    private boolean isLoading = false;//正在加载


    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        isCached = false;
        view.showRefreshingView();//设置刷新动画
        action = ACTION_REFRESH;
        pageControl.setCurrent_page(1);
        initCircleListData();
    }

    @Override
    public void onLoadMore() {
        if (isLoading) {
            return;
        }
        action = ACTION_LOADMORE;
        if (pageControl.getCurrent_page() >= pageControl.getLast_page()) {
//            view.showReqResult("无更多数据");
            return;
        }
        //设置当前页数+1
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        initCircleListData();
    }

    @Override
    public void initCircleListData() {
        bindUser_id();//绑定用户id
        if (isCached) {
            bindData();
        } else {
            //请求网络数据
            reqSubscribeCircles();
        }
    }


    @Override
    public void bindUser_id() {
        user_id = view.bindUser_id();
    }

    @Override
    public void bindData() {
        if (!view.isActive()) {
            return;
        }
        view.hideRefreshingView();
        view.dismissLoading();
        if (circleDatas == null || circleDatas.size() == 0) {
            view.showCircleNonDataView();
            return;
        }
        view.setCircleData(circleDatas);
    }

    @Override
    public int getCircleId(int position) {
        return circleDatas.get(position).getCircleId();
    }

    @Override
    public void start() {
        //得到用户id
        initCircleListData();
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
     * 请求订阅的圈子
     */
    private void reqSubscribeCircles() {
        Users users = getUsers();
        int visitor_id = users.getUser_id();
        responsitory.subscribeCircle(user_id, visitor_id, pageControl.getCurrent_page(),
                subscribeCircleApiCallback);
    }

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
                        isCached = false;
                        view.showCircleNonDataView();
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

                    List<UserActionCircleEntity.DataBean> circles = entity.getData();
                    if (circles == null || circles.size() == 0) {
                        isCached = false;
                    } else {
                        //设置数据进去
                        circleDatas = (changeToCircleData(circles));
                    }
                    bindData();
                }

                /**
                 * 转换为可用的数据
                 */
                private List<CircleData> changeToCircleData(
                        List<UserActionCircleEntity.DataBean> circles) {

                    switch (action) {
                        case ACTION_REFRESH://刷新
                            circleDatas.clear();
                            break;
                        case ACTION_LOADMORE://加载
                            break;
                    }
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
                    bindData();
                }

                @Override
                public void onFail() {
                    bindData();
                }
            };
}
