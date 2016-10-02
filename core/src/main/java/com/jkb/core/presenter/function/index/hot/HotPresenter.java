package com.jkb.core.presenter.function.index.hot;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicPopularListEntity;
import com.jkb.core.contract.function.data.hot.HotDynamic;
import com.jkb.core.contract.function.index.HotContract;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.function.index.hot.DynamicHotDataRepository;
import com.jkb.model.info.SchoolInfoSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * 热门动态的P层
 * Created by JustKiddingBaby on 2016/9/30.
 */

public class HotPresenter implements HotContract.Presenter {

    //data
    private HotContract.View view;
    private DynamicHotDataRepository repository;

    //数据
    private int school_id = -1;
    private boolean isCached;
    private boolean isRefreshing;
    private List<HotDynamic> hotDynamics;
    //分页数据
    private PageControlEntity pageControlEntity;

    public HotPresenter(HotContract.View view, DynamicHotDataRepository repository) {
        this.view = view;
        this.repository = repository;

        hotDynamics = new ArrayList<>();
        pageControlEntity = new PageControlEntity();

        this.view.setPresenter(this);
    }

    @Override
    public void initSchoolId() {
        SchoolInfoSingleton schoolInfo = SchoolInfoSingleton.getInstance();
        if (!schoolInfo.isSelectedSchool()) {
            view.hideHotView();
        } else {
            school_id = schoolInfo.getSchool().getSchool_id();
            view.showHotView();
        }
    }

    @Override
    public void initHotDynamic() {
        initSchoolId();
        if (school_id <= 0) {
            return;
        }
        //请求学校数据
        onRefresh();
    }

    @Override
    public void bindDataToView() {
        isCached = true;
        isRefreshing = false;
        if (!view.isActive()) {
            return;
        }
        view.hideRefreshingView();
        view.setHotDynamicData(hotDynamics);
    }

    @Override
    public void onRefresh() {
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
//        view.showRefreshingView();
        hotDynamics.clear();
        pageControlEntity.setCurrent_page(1);
        reqHotDynamicData();
    }


    @Override
    public void onLoadMore() {
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        pageControlEntity.setCurrent_page(pageControlEntity.getCurrent_page() + 1);
        reqHotDynamicData();
    }

    @Override
    public void start() {
        initHotDynamic();
    }

    /**
     * 请求热门动态的数据
     */
    private void reqHotDynamicData() {
        String Authorization = null;
        repository.getAllHotDynamic(Authorization, school_id, pageControlEntity.getCurrent_page(),
                hotDynamicListApiCallback);
    }

    /**
     * 获取热门动态列表的回调
     */
    private ApiCallback<ApiResponse<DynamicPopularListEntity>> hotDynamicListApiCallback = new
            ApiCallback<ApiResponse<DynamicPopularListEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<DynamicPopularListEntity>> response) {
                    if (view.isActive()) {
                        handleData(response.body());
                    }
                }

                /**
                 * 解析数据
                 */
                private void handleData(ApiResponse<DynamicPopularListEntity> body) {
                    if (body == null) {
                        hotDynamics.clear();
                        bindDataToView();
                        return;
                    }
                    handleHotData(body.getMsg());
                }

                /**
                 * 解析热门数据列表
                 */
                private void handleHotData(DynamicPopularListEntity msg) {
                    if (msg == null) {
                        hotDynamics.clear();
                        bindDataToView();
                        return;
                    }
                    //在此处理数据
                }

                @Override
                public void onError(Response<ApiResponse<DynamicPopularListEntity>> response,
                                    String error, ApiResponse<DynamicPopularListEntity> apiResponse) {
                    if (view.isActive()) {

                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {

                    }
                }
            };
}
