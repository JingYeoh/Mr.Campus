package com.jkb.mrcampus.fragment.function.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarNearbyInfo;
import com.baidu.mapapi.radar.RadarNearbyResult;
import com.baidu.mapapi.radar.RadarNearbySearchOption;
import com.baidu.mapapi.radar.RadarSearchError;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.baidu.mapapi.radar.RadarSearchManager;
import com.jkb.core.contract.map.MapListContract;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.info.LocationInfoSingleton;
import com.jkb.model.utils.LogUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MapActivity;
import com.jkb.mrcampus.adapter.recycler.map.MapListNearUserAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.CircleFilterFloatFragment;
import com.jkb.mrcampus.fragment.dialog.HintDetermineFloatFragment;
import com.jkb.mrcampus.manager.MapManagerSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 地图列表显示视图的V层
 * Created by JustKiddingBaby on 2016/11/11.
 */

public class MapListFragment extends BaseFragment implements
        MapListContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, Observer {

    public static MapListFragment newInstance() {
        Bundle args = new Bundle();
        MapListFragment fragment = new MapListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private MapActivity mapActivity;
    private MapListContract.Presenter mPresenter;

    //周边雷达
    private RadarSearchManager radarSearchManager;

    //view
    private SwipeRefreshLayout refreshLayout;

    //列表
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MapListNearUserAdapter mapListNearUserAdapter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mapActivity = (MapActivity) mActivity;
        setRootView(R.layout.frg_map_list);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        refreshLayout.setVisibility(View.VISIBLE);
        refreshLayout.setOnRefreshListener(this);
        rootView.findViewById(R.id.ts9_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.ts9_ib_right).setOnClickListener(this);
        //设置为地图管理类的观察者
        MapManagerSingleton.getInstance().addObserver(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mapListNearUserAdapter = new MapListNearUserAdapter(context, null);
        recyclerView.setAdapter(mapListNearUserAdapter);
    }

    @Override
    protected void initView() {
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fml_srl);
        //列表
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fml_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void setTitleText(String titleText) {
        ((TextView) rootView.findViewById(R.id.ts9_tv_name)).setText(titleText);
    }

    @Override
    public void showRefreshing() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshing() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showFloatMapFilter() {
        mapActivity.showCircleFilterFloatView(onCircleFilterItemClickListener);
    }

    @Override
    public void reqNearUserInfo(int currentPage) {
        LogUtils.d(TAG, "reqNearUserInfo page:" + currentPage);
        //开始搜索附近的用户
        if (radarSearchManager == null) {
            radarSearchManager = RadarSearchManager.getInstance();
            radarSearchManager.addNearbyInfoListener(radarSearchListener);
        }
        LatLng latLng = new LatLng(LocationInfoSingleton.getInstence().latitude,
                LocationInfoSingleton.getInstence().longitude);
        //开始搜索
        RadarNearbySearchOption option = new RadarNearbySearchOption()
                .pageCapacity(MapManagerSingleton.RADAR_SEARCH_PAGECAPACITY)
                .radius(MapManagerSingleton.RADAR_SEARCH_RADIUS)
                .centerPt(latLng)
                .pageNum(currentPage);
        radarSearchManager.nearbyInfoRequest(option);
    }

    @Override
    public void clearNearUserInfo() {
        if (radarSearchManager != null) {
            radarSearchManager.clearUserInfo();//清除用户信息
        }
    }

    @Override
    public void setUserInfo(List<UserInfo> userInfo) {
        LogUtils.d(TAG, "我得到的用户数据为：" + userInfo.size());
        mapListNearUserAdapter.userInfo = userInfo;
        mapListNearUserAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mapListNearUserAdapter);
    }

    @Override
    public void showHintForOpenNearUser() {
        mapActivity.showHintDetermineFloatView("是否打开附近的人？",
                "您尚未打开附近的人，是否打开？打开后您的位置也会被暴漏", "确定", "取消",
                new HintDetermineFloatFragment.OnDetermineItemClickListener() {
                    @Override
                    public void onFirstItemClick() {
                        MapManagerSingleton.getInstance().switchNearSearchAbleStatus();
                        mapActivity.dismiss();
                    }

                    @Override
                    public void onSecondItemClick() {
                        mapActivity.dismiss();
                    }
                });
    }

    @Override
    public void setPresenter(MapListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        mapActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        mapActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        showShortToash(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapActivity = null;
        onCircleFilterItemClickListener = null;
        MapManagerSingleton.getInstance().deleteObserver(this);
        //释放周边雷达
        radarSearchManager.removeNearbyInfoListener(radarSearchListener);        //移除监听
        radarSearchManager.clearUserInfo();//清除用户信息
        radarSearchManager.destroy();//释放资源
        radarSearchManager = null;
        radarSearchListener = null;
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts9_ib_left:
                mapActivity.onBackPressed();
                break;
            case R.id.ts9_ib_right:
                showFloatMapFilter();
                break;
        }
    }

    /**
     * 圈子筛选的条件
     */
    private CircleFilterFloatFragment.OnCircleFilterItemClickListener onCircleFilterItemClickListener =
            new CircleFilterFloatFragment.OnCircleFilterItemClickListener() {
                @Override
                public void onNoFilterSelected() {
                    mPresenter.onNoFilterSelected();
                }

                @Override
                public void onCircleSelected() {
                    mPresenter.onCircleSelected();
                }

                @Override
                public void onUserSelected() {
                    if (!MapManagerSingleton.getInstance().isAbleRadarSearch()) {
                        showHintForOpenNearUser();
                        return;
                    }
                    mPresenter.onUserSelected(0);
                }

                @Override
                public void onUserMaleSelected() {
                    if (!MapManagerSingleton.getInstance().isAbleRadarSearch()) {
                        showHintForOpenNearUser();
                        return;
                    }
                    mPresenter.onUserSelected(1);
                }

                @Override
                public void onUserFemaleSelected() {
                    if (!MapManagerSingleton.getInstance().isAbleRadarSearch()) {
                        showHintForOpenNearUser();
                        return;
                    }
                    mPresenter.onUserSelected(2);
                }
            };

    /**
     * 周边雷达的监听器
     */
    private RadarSearchListener radarSearchListener = new RadarSearchListener() {
        @Override
        public void onGetNearbyInfoList(RadarNearbyResult radarNearbyResult,
                                        RadarSearchError radarSearchError) {
            if (radarSearchError == RadarSearchError.RADAR_NO_ERROR) {
                //获取成功，处理数据
                List<RadarNearbyInfo> radarNearbyInfos = radarNearbyResult.infoList;
                if (radarNearbyInfos == null || radarNearbyInfos.size() == 0) {
                    return;
                }
                List<UserInfo> userInfos = new ArrayList<>();
                for (RadarNearbyInfo info : radarNearbyInfos) {
                    String comments = info.comments;
                    LogUtils.i(TAG, "comments=" + comments);
                    UserInfo userInfo = new UserInfo();
                    userInfo.setLatitude(info.pt.latitude);
                    userInfo.setLongitude(info.pt.longitude);
                    userInfo.setId(Integer.valueOf(info.comments));
                    userInfos.add(userInfo);
                }
                mPresenter.setNearUserInfo(userInfos);
            } else {
                LogUtils.w(TAG, "getNearList failed：" + radarSearchError);
                mPresenter.getNearUserInfoFailed();
            }
        }

        @Override
        public void onGetUploadState(RadarSearchError radarSearchError) {
        }

        @Override
        public void onGetClearInfoState(RadarSearchError radarSearchError) {
        }
    };

    @Override
    public void update(Observable o, Object arg) {
        if (MapManagerSingleton.getInstance().isAbleRadarSearch()) {
            mPresenter.onRefresh();
        } else {
            mPresenter.onNearUserNotAble();
            clearNearUserInfo();
        }
    }
}
