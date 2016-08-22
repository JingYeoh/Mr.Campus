package com.jkb.mrcampus.fragment.function.homePage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 首页——动态的View层
 * Created by JustKiddingBaby on 2016/8/22.
 */

public class DynamicFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public DynamicFragment() {
    }

    private static DynamicFragment INSTANCE = null;

    public static DynamicFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DynamicFragment();
        }
        return INSTANCE;
    }

    //View
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_homepage_dynamic);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        //刷新控件
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fhd_srl);
        //初始化列表栏
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fhd_rv);
    }

    @Override
    public void onRefresh() {

    }
}
