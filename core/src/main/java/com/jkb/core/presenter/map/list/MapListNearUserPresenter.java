package com.jkb.core.presenter.map.list;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.user.UserListInfoEntity;
import com.jkb.core.contract.map.list.MapListNearUserContract;
import com.jkb.core.data.info.school.SchoolInfo;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.map.mapList.MapListDataSource;
import com.jkb.model.utils.LogUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Response;

/**
 * 地图列表附近的人的P层
 * Created by JustKiddingBaby on 2016/11/14.
 */

public class MapListNearUserPresenter implements MapListNearUserContract.Presenter {

    private MapListNearUserContract.View view;
    private MapListDataSource repertory;

    //data
    private List<UserInfo> mUserInfo;
    private boolean isCached;
    private boolean isRefreshing;
    private PageControlEntity pageControl;
    private String currentUserType = CIRCLE_FILTER_USER_ALL;

    //常量
    private static final String CIRCLE_FILTER_USER_ALL = "circle.filter.user.all";
    private static final String CIRCLE_FILTER_USER_MALE = "circle.filter.user.male";
    private static final String CIRCLE_FILTER_USER_FEMALE = "circle.filter.user.female";

    public MapListNearUserPresenter(
            MapListNearUserContract.View view, MapListDataSource repertory) {
        this.view = view;
        this.repertory = repertory;

        mUserInfo = new ArrayList<>();
        pageControl = new PageControlEntity();

        this.view.setPresenter(this);
    }

    @Override
    public void bindDataToView() {
        if (mUserInfo.size() == 0) {
            isCached = false;
        } else {
            isCached = true;
        }
        isRefreshing = false;
        if (!view.isActive()) {
            return;
        }
        view.hideRefreshing();
        view.dismissLoading();
        //筛选用户
        filterUserBySex();
        view.setUserInfo(mUserInfo);//设置用户信息
    }

    /**
     * 筛选用户性别
     */
    private List<UserInfo> filterUserBySex() {
        String sexFilter;
        switch (currentUserType) {
            case CIRCLE_FILTER_USER_ALL:
                view.setSexNoneFilterView();
                return mUserInfo;
            case CIRCLE_FILTER_USER_MALE:
                view.setSexFilterMaleView();
                sexFilter = Config.SEX_MALE;
                break;
            case CIRCLE_FILTER_USER_FEMALE:
                view.setSexFilterFemaleView();
                sexFilter = Config.SEX_FEMALE;
                break;
            default:
                return mUserInfo;
        }
        LogUtils.d(MapListNearUserPresenter.class, "filterSex-->sex=" + sexFilter);
        if (mUserInfo.size() == 0) {
            return mUserInfo;
        }
        List<UserInfo> copyUserInfo = new ArrayList<>();
        for (Iterator<UserInfo> it = mUserInfo.iterator(); it.hasNext(); ) {
            UserInfo userInfo = it.next();
            if (!sexFilter.equals(userInfo.getSex())) {
                copyUserInfo.add(userInfo);
            }
        }
        mUserInfo.removeAll(copyUserInfo);//要删除的数据
        return copyUserInfo;
    }

    @Override
    public void initNearUserData() {
        if (isCached) {
            bindDataToView();
        } else {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        if (!view.isAbleSearchNearUser()) {
            return;
        }
        isCached = false;
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        mUserInfo.clear();
        pageControl.setCurrent_page(0);
        //请求用户信息
        reqUserInfoList();
    }

    @Override
    public void onLoadMore() {
        if (!view.isAbleSearchNearUser()) {
            return;
        }
        if (pageControl.getCurrent_page() < pageControl.getLast_page()) {
            return;
        }
        isCached = false;
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        mUserInfo.clear();
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        //请求用户信息
        reqUserInfoList();
    }

    @Override
    public void onNoFilterSelected() {
        currentUserType = CIRCLE_FILTER_USER_ALL;
        onRefresh();
    }

    @Override
    public void onMaleSelected() {
        currentUserType = CIRCLE_FILTER_USER_MALE;
        onRefresh();
    }

    @Override
    public void onFeMaleSelected() {
        currentUserType = CIRCLE_FILTER_USER_FEMALE;
        onRefresh();
    }

    @Override
    public void setNearUserInfo(final List<UserInfo> nearUserInfo, int currentPage, int lastPage) {
        LogUtils.d(MapListNearUserPresenter.class, "setNearUserInfo");
        if (!view.isActive()) {
            return;
        }
        view.hideRefreshing();
        pageControl.setCurrent_page(currentPage);
        pageControl.setLast_page(lastPage);
        if (nearUserInfo == null || nearUserInfo.size() == 0) {
            bindDataToView();
            return;
        }
        String idArr = "";
        for (int i = 0; i < nearUserInfo.size(); i++) {
            idArr += nearUserInfo.get(i).getId();
            if (i < nearUserInfo.size() - 1) {
                idArr += ",";
            }
        }
        view.showLoading("");
        repertory.getUserListInfo(null, idArr, new ApiCallback<ApiResponse<UserListInfoEntity>>() {
            @Override
            public void onSuccess(Response<ApiResponse<UserListInfoEntity>> response) {
                if (view.isActive()) {
                    ApiResponse<UserListInfoEntity> body = response.body();
                    UserListInfoEntity msg = body.getMsg();
                    if (msg != null) {
                        handleUsrListData(msg.getData());
                    }
                    bindDataToView();
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
                    addCoordinate(userInfo);
                    //添加到总体的数据中
                    mUserInfo.add(userInfo);
                }
            }

            /**
             * 添加坐标信息
             */
            private void addCoordinate(UserInfo userInfo) {
                for (UserInfo info : nearUserInfo) {
                    if (info.getId() == userInfo.getId()) {
                        userInfo.setLatitude(info.getLatitude());
                        userInfo.setLongitude(info.getLongitude());
                        return;
                    }
                }
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
                    view.showReqResult("");
                    bindDataToView();
                }
            }
        });
    }

    @Override
    public void onUserItemClick(int position) {
        UserInfo userInfo = mUserInfo.get(position);
        view.startUserCenter(userInfo.getId());
    }

    @Override
    public void start() {
        initNearUserData();
    }

    /**
     * 请求用户列表信息
     */
    private void reqUserInfoList() {
        if (view.isActive()) {
            view.showRefreshing();
            view.reqNearUserInfo(pageControl.getCurrent_page());
        }
    }
}
