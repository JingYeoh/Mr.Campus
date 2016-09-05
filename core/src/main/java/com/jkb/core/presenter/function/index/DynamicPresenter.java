package com.jkb.core.presenter.function.index;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.core.contract.function.index.DynamicContract;
import com.jkb.core.contract.function.index.data.DynamicData;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.function.index.dynamic.DynamicDataResponsitory;
import com.jkb.model.info.UserInfoSingleton;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import retrofit2.Response;

/**
 * 动态列表的P层
 * Created by JustKiddingBaby on 2016/8/23.
 */

public class DynamicPresenter implements DynamicContract.Presenter {

    private DynamicContract.View view;
    private DynamicDataResponsitory responsitory;

    //data
    private List<DynamicData> dynamicDatas;
    private boolean isCached = false;
    //分页
    private PageControlEntity pageControl;
    private int action = ACTION_REFRESH;
    private static final int ACTION_REFRESH = 0;
    private static final int ACTION_LOADMORE = 1;
    private boolean isLoading = false;//正在加载

    public DynamicPresenter(
            @NonNull DynamicContract.View view, @NonNull DynamicDataResponsitory responsitory) {
        this.view = view;
        this.responsitory = responsitory;

        this.view.setPresenter(this);

        pageControl = new PageControlEntity();
        dynamicDatas = new ArrayList<>();
    }

    @Override
    public void start() {
        initDynamicData();
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        view.showRefreshingView();//设置刷新动画
        action = ACTION_REFRESH;
        pageControl.setCurrent_page(1);
        reqDynamicListData();
    }

    @Override
    public void onLoadMore() {
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
        reqDynamicListData();
    }

    @Override
    public void initDynamicData() {
        //判断是否登录
        if (!LoginContext.getInstance().isLogined()) {
            view.showUnLoginView();
            return;
        }
        //判断是否有缓存数据
        if (isCached) {
            bindDataToView();
            return;
        }
        onRefresh();
    }

    @Override
    public void likeDynamic(int position) {

    }

    @Override
    public void bindDataToView() {

    }

    /**
     * 得到动态数据
     */
    public void reqDynamicListData() {
        isLoading = true;
        if (!LoginContext.getInstance().isLogined()) {
            return;
        }
        UserAuths auths = getUserAuths();
        if (auths == null) {
            return;
        }
        String Authorization = Config.HEADER_BEARER + auths.getToken();
        responsitory.getAllDynamic(Authorization, pageControl.getCurrent_page(), dynamicApiCallback);
    }

    /**
     * 动态的数据回调接口
     */
    private ApiCallback<ApiResponse<DynamicListEntity>> dynamicApiCallback =
            new ApiCallback<ApiResponse<DynamicListEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<DynamicListEntity>> response) {
                    if (view.isActive()) {
                        view.hideRefreshingView();
                        handleRespData(response.body());
                    }
                }

                /**
                 * 解析动态数据
                 */
                private void handleRespData(ApiResponse<DynamicListEntity> body) {
                    if (body == null) {
                        bindDataToView();
                        return;
                    }
                    DynamicListEntity entity = body.getMsg();
                    if (entity == null) {
                        bindDataToView();
                        return;
                    }
                    //判断是否添加数据或者清空数据
                    isCached = true;//设置为已缓存

                    //设置页码控制器
                    pageControl.setTotal(entity.getTotal());
                    pageControl.setPer_page(entity.getPer_page());
                    pageControl.setCurrent_page(entity.getCurrent_page());
                    pageControl.setLast_page(entity.getLast_page());
                    pageControl.setNext_page_url(entity.getNext_page_url());
                    pageControl.setPrev_page_url(entity.getPrev_page_url());
                    pageControl.setFrom(entity.getFrom());
                    pageControl.setTo(entity.getTo());

                    //处理数据
                    handleDynamicData(entity);

                    bindDataToView();
                }

                /**
                 * 处理动态数据
                 */
                private void handleDynamicData(DynamicListEntity entity) {
                    //判断操作动作
                    switch (action) {
                        case ACTION_REFRESH://刷新
                            dynamicDatas.clear();
                            break;
                        case ACTION_LOADMORE://加载
                            break;
                    }
                    //更新数据进去
                    List<DynamicListEntity.DataBean> dataBeen = entity.getData();
                    if (dataBeen == null) {
                        return;
                    }
                    for (int i = 0; i < dataBeen.size(); i++) {
                        //添加数据
                    }
                }

                @Override
                public void onError(Response<ApiResponse<DynamicListEntity>> response,
                                    String error, ApiResponse<DynamicListEntity> apiResponse) {
                    if (view.isActive()) {
                        view.hideRefreshingView();
                        bindDataToView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.hideRefreshingView();
                        bindDataToView();
                    }
                }
            };

    /**
     * 得到用户数据
     */
    private UserAuths getUserAuths() {
        UserInfoSingleton userInfo = UserInfoSingleton.getInstance();
        UserAuths users = userInfo.getUserAuths();
        if (users == null) {
            LoginContext.getInstance().setUserState(new LogoutState());
            view.showReqResult("登录过期，请重新登录~");
        }
        return users;
    }
}
