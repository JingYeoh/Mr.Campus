package com.jkb.mrcampus.fragment.function.map.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarNearbyInfo;
import com.baidu.mapapi.radar.RadarNearbyResult;
import com.baidu.mapapi.radar.RadarNearbySearchOption;
import com.baidu.mapapi.radar.RadarSearchError;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.baidu.mapapi.radar.RadarSearchManager;
import com.jkb.core.Injection;
import com.jkb.core.contract.map.list.MapListNearUserContract;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.core.presenter.map.list.MapListNearUserPresenter;
import com.jkb.model.info.LocationInfoSingleton;
import com.jkb.model.utils.LogUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MapActivity;
import com.jkb.mrcampus.adapter.recycler.map.MapListNearUserAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.HintDetermineFloatFragment;
import com.jkb.mrcampus.fragment.dialog.MapFilterFloatFragment;
import com.jkb.mrcampus.manager.MapManagerSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 地图列表的附近的人的V层
 * Created by JustKiddingBaby on 2016/11/14.
 */

public class MapListNearUserFragment extends BaseFragment implements
        MapListNearUserContract.View, SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener, Observer {

    public static MapListNearUserFragment newInstance() {
        Bundle args = new Bundle();
        MapListNearUserFragment fragment = new MapListNearUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private MapActivity mapActivity;
    private MapListNearUserContract.Presenter mPresenter;

    //view
    private SwipeRefreshLayout refreshLayout;
    private int[] sexFilterResource = new int[]{
            R.drawable.ic_user_head,
            R.drawable.ic_write,
            R.drawable.ic_top
    };

    //列表
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MapListNearUserAdapter mapListNearUserAdapter;

    //周边雷达
    private RadarSearchManager radarSearchManager;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mapActivity = (MapActivity) mActivity;
        setRootView(R.layout.frg_map_list_near_user);
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
        refreshLayout.setOnRefreshListener(this);
        rootView.findViewById(R.id.fmlnu_bt_openNearUser).setOnClickListener(this);
        rootView.findViewById(R.id.fmlnu_iv_secFilter).setOnClickListener(this);

        recyclerView.addOnScrollListener(onScrollListener);
        mapListNearUserAdapter.setOnNearUserItemClickListener(onNearUserItemClickListener);

        MapManagerSingleton.getInstance().addObserver(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (mPresenter == null) {//初始化P层
            mPresenter = new MapListNearUserPresenter(this,
                    Injection.provideMapListRepertory(mapActivity.getApplicationContext()));
        }
        mapListNearUserAdapter = new MapListNearUserAdapter(context, null);
        recyclerView.setAdapter(mapListNearUserAdapter);
        //初始化周边雷达
        radarSearchManager = RadarSearchManager.getInstance();
        radarSearchManager.addNearbyInfoListener(radarSearchListener);
    }

    @Override
    protected void initView() {
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fmlnu_srl);
        //列表
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fmlnu_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean isAbleSearchNearUser() {
        return MapManagerSingleton.getInstance().isAbleRadarSearch();
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
    public void showSexFilterView() {
        //显示性别筛选视图
        mapActivity.showMapFilterFloatView(MapFilterFloatFragment.FILTER_SEX,
                onCircleFilterItemClickListener);
    }

    @Override
    public void setSexNoneFilterView() {
        ((ImageView) rootView.findViewById(R.id.fmlnu_iv_secFilter)).
                setImageResource(sexFilterResource[0]);
    }

    @Override
    public void setSexFilterMaleView() {
        ((ImageView) rootView.findViewById(R.id.fmlnu_iv_secFilter)).
                setImageResource(sexFilterResource[1]);
    }

    @Override
    public void setSexFilterFemaleView() {
        ((ImageView) rootView.findViewById(R.id.fmlnu_iv_secFilter)).
                setImageResource(sexFilterResource[2]);
    }

    @Override
    public void reqNearUserInfo(int currentPage) {
        //向百度地图请求用户信息
        LogUtils.d(TAG, "reqNearUserInfo page:" + currentPage);
        //开始搜索附近的用户
        LatLng latLng = new LatLng(LocationInfoSingleton.getInstence().latitude,
                LocationInfoSingleton.getInstence().longitude);
        LogUtils.d(TAG, "latitude=" + LocationInfoSingleton.getInstence().latitude);
        LogUtils.d(TAG, "longitude=" + LocationInfoSingleton.getInstence().longitude);
        //开始搜索
        RadarNearbySearchOption option = new RadarNearbySearchOption()
                .pageCapacity(MapManagerSingleton.RADAR_SEARCH_PAGECAPACITY)
                .radius(MapManagerSingleton.RADAR_SEARCH_RADIUS)
                .centerPt(latLng)
                .pageNum(currentPage);
        radarSearchManager.nearbyInfoRequest(option);
    }

    @Override
    public void closeNearUserSearch() {
        if (radarSearchManager != null) {
            radarSearchManager.clearUserInfo();//清除用户信息
        }
        rootView.findViewById(R.id.fmlnu_content_open).setVisibility(View.GONE);
        rootView.findViewById(R.id.fmlnu_content_closeHint).setVisibility(View.VISIBLE);
    }

    @Override
    public void openNearUserSearch() {
        rootView.findViewById(R.id.fmlnu_content_closeHint).setVisibility(View.GONE);
        rootView.findViewById(R.id.fmlnu_content_open).setVisibility(View.VISIBLE);
        mPresenter.onRefresh();
    }

    @Override
    public void setUserInfo(List<UserInfo> userInfo) {
        mapListNearUserAdapter.userInfo = userInfo;
        mapListNearUserAdapter.notifyDataSetChanged();
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
    public void startUserCenter(int userId) {
        mapActivity.startPersonalCenterActivity(userId);
    }

    @Override
    public void setPresenter(MapListNearUserContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        mapActivity.showLoading(value, this);
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
        //view
        mapListNearUserAdapter = null;
        recyclerView = null;
        refreshLayout = null;
        linearLayoutManager = null;
        //监听器
        onScrollListener = null;
        onNearUserItemClickListener = null;
        onCircleFilterItemClickListener = null;
        //释放周边雷达
        MapManagerSingleton.getInstance().deleteObserver(this);
        radarSearchManager.removeNearbyInfoListener(radarSearchListener);        //移除监听
        radarSearchManager.clearUserInfo();//清除用户信息
        radarSearchManager.destroy();//释放资源
        radarSearchManager = null;
        radarSearchListener = null;
    }

    /**
     * 附近的人的列表显示
     */
    private MapListNearUserAdapter.OnNearUserItemClickListener onNearUserItemClickListener =
            new MapListNearUserAdapter.OnNearUserItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mPresenter.onUserItemClick(position);
                }
            };

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    /**
     * 周边雷达的监听器
     */
    private RadarSearchListener radarSearchListener = new RadarSearchListener() {
        @Override
        public void onGetNearbyInfoList(RadarNearbyResult radarNearbyResult,
                                        RadarSearchError radarSearchError) {
            LogUtils.d(TAG, "onGetNearbyInfoList result=" + radarSearchError);
            if (radarSearchError == RadarSearchError.RADAR_NO_ERROR) {
                //获取成功，处理数据
                List<RadarNearbyInfo> radarNearbyInfos = radarNearbyResult.infoList;
                if (radarNearbyInfos == null || radarNearbyInfos.size() == 0) {
                    mPresenter.setNearUserInfo(new ArrayList<UserInfo>(), 0, 0);
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
                mPresenter.setNearUserInfo(userInfos, radarNearbyResult.pageIndex,
                        radarNearbyResult.pageNum);
            } else {
                mPresenter.setNearUserInfo(new ArrayList<UserInfo>(), 0, 0);
                LogUtils.w(TAG, "getNearList failed：" + radarSearchError);
            }
        }

        @Override
        public void onGetUploadState(RadarSearchError radarSearchError) {
        }

        @Override
        public void onGetClearInfoState(RadarSearchError radarSearchError) {
        }
    };
    /**
     * 滑动的监听器
     */
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
           /* int topRowVerticalPosition =
                    (recyclerView == null || recyclerView.getChildCount() == 0) ? 0
                            : recyclerView.getChildAt(0).getTop();
            refreshLayout.setEnabled(topRowVerticalPosition >= 0);*/

            int lastVisibleItem = (linearLayoutManager).findLastVisibleItemPosition();
            int totalItemCount = linearLayoutManager.getItemCount();
            if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                mPresenter.onLoadMore();//设置下拉加载
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fmlnu_bt_openNearUser:
                showHintForOpenNearUser();
                break;
            case R.id.fmlnu_iv_secFilter:
                showSexFilterView();
                break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (MapManagerSingleton.getInstance().isAbleRadarSearch()) {
            openNearUserSearch();
        } else {
            closeNearUserSearch();
        }
    }

    /**
     * 地图的筛选的条目监听器
     */
    private MapFilterFloatFragment.OnCircleFilterItemClickListener onCircleFilterItemClickListener =
            new MapFilterFloatFragment.OnCircleFilterItemClickListener() {
                @Override
                public void onNoFilterSelected() {
                }

                @Override
                public void onCircleSelected() {

                }

                @Override
                public void onUserSelected() {
                    mPresenter.onNoFilterSelected();
                }

                @Override
                public void onUserMaleSelected() {
                    mPresenter.onMaleSelected();
                }

                @Override
                public void onUserFemaleSelected() {
                    mPresenter.onFeMaleSelected();
                }
            };
}
