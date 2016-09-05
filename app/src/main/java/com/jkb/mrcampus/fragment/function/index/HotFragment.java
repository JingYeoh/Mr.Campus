package com.jkb.mrcampus.fragment.function.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 首页——热门的View层
 * Created by JustKiddingBaby on 2016/8/22.
 */

public class HotFragment extends BaseFragment {

    public HotFragment() {
    }

    private static HotFragment INSTANCE = null;

    public static HotFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HotFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_homepage_hot);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

    }
}
