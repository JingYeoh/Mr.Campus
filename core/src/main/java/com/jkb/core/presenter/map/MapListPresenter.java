package com.jkb.core.presenter.map;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.user.UserListInfoEntity;
import com.jkb.core.contract.map.MapListContract;
import com.jkb.core.data.info.school.SchoolInfo;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.map.mapList.MapListRepertory;
import com.jkb.model.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * 地图列表的P层
 * Created by JustKiddingBaby on 2016/11/12.
 */

public class MapListPresenter implements MapListContract.Presenter {

    private MapListContract.View view;
    private MapListRepertory repertory;

    //data
    private boolean isCached = false;
    private boolean isRefreshing = false;
    private String currentFilterType;

    //用户
    private List<UserInfo> nearUserInfo;
    private PageControlEntity userPageControl;

    //常量
    private static final String CIRCLE_FILTER_CIRCLE = "circle.filter.circle";
    private static final String CIRCLE_FILTER_USER_ALL = "circle.filter.user.all";
    private static final String CIRCLE_FILTER_USER_MALE = "circle.filter.user.male";
    private static final String CIRCLE_FILTER_USER_FEMALE = "circle.filter.user.female";

    public MapListPresenter(MapListContract.View view, MapListRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        currentFilterType = CIRCLE_FILTER_USER_ALL;//默认选择的是所有的用户
        nearUserInfo = new ArrayList<>();
        userPageControl = new PageControlEntity();

        this.view.setPresenter(this);
    }

    @Override
    public void bindDataToView() {
        isRefreshing = false;
        if (!view.isActive()) {
            return;
        }
        view.hideRefreshing();
        view.dismissLoading();
        switch (currentFilterType) {
            case CIRCLE_FILTER_CIRCLE:
                break;
            case CIRCLE_FILTER_USER_ALL:
                filterUserInfo(0);
                break;
            case CIRCLE_FILTER_USER_MALE:
                filterUserInfo(1);
                break;
            case CIRCLE_FILTER_USER_FEMALE://请求用户数据
                filterUserInfo(2);
                break;
        }
    }

    /**
     * 筛选用户数据
     */
    private void filterUserInfo(int sexType) {
        String sexFilter;
        if (sexType == 0) {
            return;
        } else if (sexType == 1) {
            sexFilter = Config.SEX_MALE;
        } else if (sexType == 2) {
            sexFilter = Config.SEX_FEMALE;
        } else {
            return;
        }
        if (nearUserInfo.size() != 0) {
            isCached = true;
        } else {
            isCached = false;
        }
        for (UserInfo info :
                nearUserInfo) {
            if (sexFilter.equals(info.getSex())) {
                nearUserInfo.remove(info);
            }
        }
        if (view.isActive()) {
            view.setUserInfo(nearUserInfo);
            view.setTitleText("附近的人");
        }
    }

    @Override
    public void initCircleListData() {
        if (isCached) {
            bindDataToView();
        } else {
            onRefresh();
        }
    }

    @Override
    public void onNoFilterSelected() {

    }

    @Override
    public void onCircleSelected() {
        currentFilterType = CIRCLE_FILTER_CIRCLE;
        view.setTitleText("附近的圈子");
        onRefresh();
    }

    @Override
    public void onUserSelected(int sex) {
        if (sex == 0) {
            currentFilterType = CIRCLE_FILTER_USER_ALL;
        } else if (sex == 1) {
            currentFilterType = CIRCLE_FILTER_USER_MALE;
        } else if (sex == 2) {
            currentFilterType = CIRCLE_FILTER_USER_FEMALE;
        } else {
            return;
        }
        view.setTitleText("附近的人");
        onRefresh();
    }

    @Override
    public void onRefresh() {
        LogUtils.d(MapListPresenter.class, "onRefresh type=" + currentFilterType);
        isCached = false;
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        view.showRefreshing();
        //根据类型判断需要什么数据
        switch (currentFilterType) {
            case CIRCLE_FILTER_CIRCLE://请求圈子数据
                
                break;
            case CIRCLE_FILTER_USER_ALL:
            case CIRCLE_FILTER_USER_MALE:
            case CIRCLE_FILTER_USER_FEMALE://请求用户数据
                nearUserInfo.clear();
                userPageControl.setCurrent_page(0);
                view.reqNearUserInfo(userPageControl.getCurrent_page());
                break;
        }
    }

    @Override
    public void onLoadMore() {
        isCached = false;
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        switch (currentFilterType) {
            case CIRCLE_FILTER_CIRCLE:
                break;
            case CIRCLE_FILTER_USER_ALL:
            case CIRCLE_FILTER_USER_MALE:
            case CIRCLE_FILTER_USER_FEMALE://请求用户数据
                if (userPageControl.getLast_page() >= userPageControl.getCurrent_page()) {
                    userPageControl.setCurrent_page(userPageControl.getCurrent_page() + 1);
                    view.reqNearUserInfo(userPageControl.getCurrent_page());
                }
                break;
        }
    }

    @Override
    public void onNearUserNotAble() {
        nearUserInfo.clear();
        userPageControl.setCurrent_page(0);
        currentFilterType = CIRCLE_FILTER_CIRCLE;
        onRefresh();
    }

    @Override
    public void setNearUserInfo(List<UserInfo> nearUserInfo) {
        this.nearUserInfo = nearUserInfo;
        int[] ids = new int[nearUserInfo.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = nearUserInfo.get(i).getId();
        }
        //从服务器请求用户信息
        reqUserList(ids);
    }

    @Override
    public void getNearUserInfoFailed() {
        isCached = false;
        bindDataToView();
    }

    @Override
    public void start() {
        initCircleListData();
    }

    /**
     * 请求用户数据
     */
    private void reqUserList(int[] ids) {
        if (ids == null || ids.length == 0) {
            bindDataToView();
            return;
        }
        String idArr = "";
        for (int i = 0; i < ids.length; i++) {
            idArr += ids[i];
            if (i != ids.length - 1) {
                idArr += ",";
            }
        }
        //请求接口
        if (isRefreshing) {
            reqUserList(ids);
            return;
        }
        isRefreshing = true;
        if (view.isActive()) {
            view.showReqResult("");
            repertory.getUserListInfo(null, idArr, userListApiCallback);
        }
    }

    /**
     * 用户列表的回调
     */
    private ApiCallback<ApiResponse<UserListInfoEntity>> userListApiCallback =
            new ApiCallback<ApiResponse<UserListInfoEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<UserListInfoEntity>> response) {
                    if (view.isActive()) {
                        ApiResponse<UserListInfoEntity> body = response.body();
                        UserListInfoEntity msg = body.getMsg();
                        if (msg == null) {
                            bindDataToView();
                        } else {
                            handleUsrListData(msg.getData());
                        }
                    }
                }

                /**
                 * 处理用户列表数据
                 */
                private void handleUsrListData(List<UserListInfoEntity.DataBean> data) {
                    if (data == null || data.size() == 0) {
                        bindDataToView();
                        return;
                    }
                    for (UserListInfoEntity.DataBean entity : data) {
                        //添加数据
                        UserInfo userInfo = new UserInfo();
                        userInfo.setId(entity.getId());
                        userInfo.setAvatar(entity.getAvatar());
                        userInfo.setNickName(entity.getNickname());
                        userInfo.setSex(entity.getSex());
                        UserListInfoEntity.DataBean.SchoolBean school = entity.getSchool();
                        if (school != null) {
                            SchoolInfo schoolInfo = new SchoolInfo();
                            schoolInfo.setId(school.getId());
                            schoolInfo.setSname(school.getSname());
                            schoolInfo.setBadge(school.getBadge());
                            schoolInfo.setSummary(school.getSummary());
                            schoolInfo.setLatitude(school.getLatitude());
                            schoolInfo.setLongitude(school.getLongitude());
                            userInfo.setSchool(schoolInfo);
                        }
                        //添加到总体的数据中
                        nearUserInfo.add(userInfo);
                    }
                    bindDataToView();
                }

                @Override
                public void onError(Response<ApiResponse<UserListInfoEntity>> response,
                                    String error, ApiResponse<UserListInfoEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult(error);
                        bindDataToView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("请求失败");
                        bindDataToView();
                    }
                }
            };
}
