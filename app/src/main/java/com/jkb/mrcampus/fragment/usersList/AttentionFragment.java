package com.jkb.mrcampus.fragment.usersList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.adapter.recycler.AttentionListAdapter;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 关注的页面
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class AttentionFragment extends BaseFragment {

    public AttentionFragment() {
    }

    public static AttentionFragment INSTANCE = null;

    public static AttentionFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AttentionFragment();
        }
        return INSTANCE;
    }

    private RecyclerView rv;
    private AttentionListAdapter attentionListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_userslist_attention);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        attentionListAdapter = new AttentionListAdapter(mActivity);
        rv.setAdapter(attentionListAdapter);
    }

    @Override
    protected void initView() {
        //初始化标题栏
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText(R.string.Attention);
        //设置布局方向等
        rv = (RecyclerView) rootView.findViewById(R.id.fua_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
    }
}
