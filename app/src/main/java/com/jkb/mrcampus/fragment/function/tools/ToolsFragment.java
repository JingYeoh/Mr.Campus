package com.jkb.mrcampus.fragment.function.tools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 小工具的页面
 * Created by JustKiddingBaby on 2016/10/11.
 */

public class ToolsFragment extends BaseFragment {

    public ToolsFragment() {
    }

    private static ToolsFragment INSTANCE = null;

    public static ToolsFragment newInstance() {
        INSTANCE = new ToolsFragment();
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_function_tools);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
