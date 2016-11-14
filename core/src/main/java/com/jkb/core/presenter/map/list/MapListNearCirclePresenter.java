package com.jkb.core.presenter.map.list;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.circle.CircleListInSchoolEntity;
import com.jkb.core.contract.map.list.MapListNearCircleContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.map.mapList.MapListDataSource;
import com.jkb.model.info.SchoolInfoSingleton;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * 附近的圈子的P层
 * Created by JustKiddingBaby on 2016/11/14.
 */

public class MapListNearCirclePresenter implements MapListNearCircleContract.Presenter {

    private MapListNearCircleContract.View view;
    private MapListDataSource repertory;

    //data
    private boolean isCached=false;
    private List<CircleInfo> mCircleInfo;
    private boolean isRefreshing=false;
    private PageControlEntity pageControl;

    public MapListNearCirclePresenter(
            MapListNearCircleContract.View view, MapListDataSource repertory) {
        this.view = view;
        this.repertory = repertory;

        pageControl = new PageControlEntity();
        mCircleInfo = new ArrayList<>();

        this.view.setPresenter(this);
    }

    @Override
    public void onRefresh() {
        LogUtils.d(MapListNearCirclePresenter.class,"onRefresh");
        isCached = false;
        if (isRefreshing) {
            return;
        }
        mCircleInfo.clear();
        isRefreshing = true;
        pageControl.setCurrent_page(1);
        view.showRefreshing();
        reqSchoolCircleList();
    }

    @Override
    public void onLoadMore() {
        LogUtils.d(MapListNearCirclePresenter.class,"onLoadMore");
        if (pageControl.getCurrent_page() > pageControl.getLast_page()) {
            return;
        }
        isCached = false;
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        reqSchoolCircleList();
    }


    @Override
    public void bindDataToView() {
        LogUtils.d(MapListNearCirclePresenter.class,"bindDataToView");
        if (mCircleInfo.size() == 0) {
            isCached = false;
        } else {
            isCached = true;
        }
        isRefreshing = false;
        if (!view.isActive()) {
            return;
        }
        view.dismissLoading();
        view.hideRefreshing();
        //绑定数据
        view.setCircleInfo(mCircleInfo);
    }

    @Override
    public void initNearCircleData() {
        if (isCached) {
            bindDataToView();
        } else {
            onRefresh();
        }
    }

    @Override
    public void onCircleItemClick(int position) {
        CircleInfo circleInfo = mCircleInfo.get(position);
        view.startCircleIndex(circleInfo.getCircleId());
    }

    @Override
    public void start() {
        initNearCircleData();
    }

    /**
     * 请求学校内的圈子列表
     */
    private void reqSchoolCircleList() {
        int schoolId = getCurrentSelectedSchoolId();
        LogUtils.d(MapListNearCirclePresenter.class,"reqSchoolCircleList schoolId="+schoolId);
        if (schoolId <= 0) {
            bindDataToView();
            return;
        }
        String authorization = getAuthorization();
        repertory.getCircleListInSchool(
                authorization, schoolId, pageControl.getCurrent_page(), circleListApiCallback);
    }

    /**
     * 得到當前選擇的學校id
     */
    private int getCurrentSelectedSchoolId() {
        int schoolId = 0;
        if (SchoolInfoSingleton.getInstance().isSelectedSchool()) {
            schoolId = SchoolInfoSingleton.getInstance().getSchool().getSchool_id();
        }
        return schoolId;
    }

    /**
     * 得到Authorization
     */
    private String getAuthorization() {
        String authorization = null;
        if (LoginContext.getInstance().isLogined()) {
            authorization = Config.HEADER_BEARER +
                    UserInfoSingleton.getInstance().getUserAuths().getToken();
        }
        return authorization;
    }

    /**
     * 学校内圈子的回调
     */
    private ApiCallback<ApiResponse<CircleListInSchoolEntity>> circleListApiCallback =
            new ApiCallback<ApiResponse<CircleListInSchoolEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<CircleListInSchoolEntity>> response) {
                    if (view.isActive()) {
                        ApiResponse<CircleListInSchoolEntity> body = response.body();
                        CircleListInSchoolEntity msg = body.getMsg();
                        if (msg == null) {
                            bindDataToView();
                        } else {
                            handleCircleListData(msg);
                        }
                    }
                }

                /**
                 * 处理圈子数据
                 */
                private void handleCircleListData(CircleListInSchoolEntity msg) {
                    //设置页码控制器
                    pageControl.setTotal(msg.getTotal());
                    pageControl.setPer_page(msg.getPer_page());
                    pageControl.setCurrent_page(msg.getCurrent_page());
                    pageControl.setLast_page(msg.getLast_page());
                    pageControl.setNext_page_url(msg.getNext_page_url());
                    pageControl.setPrev_page_url(msg.getPrev_page_url());
                    pageControl.setFrom(msg.getFrom());
                    pageControl.setTo(msg.getTo());

                    List<CircleListInSchoolEntity.DataBean> data = msg.getData();
                    if (data == null || data.size() == 0) {
                        bindDataToView();
                        return;
                    }
                    for (CircleListInSchoolEntity.DataBean dataBean : data) {
                        addCircleToData(dataBean);
                    }
                    bindDataToView();
                }

                /**
                 * 添加圈子数据
                 */
                private void addCircleToData(CircleListInSchoolEntity.DataBean dataBean) {
                    if (dataBean == null) {
                        return;
                    }
                    CircleInfo circleInfo = new CircleInfo();
                    circleInfo.setCircleId(dataBean.getCircle_id());
                    circleInfo.setCircleName(dataBean.getCircle_name());
                    circleInfo.setCircleType(dataBean.getType());
                    circleInfo.setPictureUrl(dataBean.getPicture());
                    circleInfo.setIntroduction(dataBean.getIntroduction());
                    circleInfo.setOperatorCount(dataBean.getCount_of_subscription());
                    circleInfo.setDynamicsCount(dataBean.getCount_of_dynamic());
                    circleInfo.setLongitude(dataBean.getCircle_longitude());
                    circleInfo.setLatitude(dataBean.getCircle_latitude());
                    if (dataBean.getHas_subscribe() == 0) {
                        circleInfo.setSubscribe(false);
                    } else {
                        circleInfo.setSubscribe(true);
                    }
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(dataBean.getCreator_id());
                    userInfo.setAvatar(dataBean.getCreator_avatar());
                    userInfo.setNickName(dataBean.getCreator_nickname());
                    circleInfo.setUser(userInfo);

                    mCircleInfo.add(circleInfo);
                }

                @Override
                public void onError(
                        Response<ApiResponse<CircleListInSchoolEntity>> response,
                        String error, ApiResponse<CircleListInSchoolEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showLoading(error);
                        bindDataToView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showLoading("请求失败");
                        bindDataToView();
                    }
                }
            };
}
