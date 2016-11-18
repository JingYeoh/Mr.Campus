package com.jkb.mrcampus.fragment.function.tools.function;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.ToolsFunctionActivity;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 快递查询的V层
 * Created by JustKiddingBaby on 2016/11/15.
 */

public class ToolsCourierFunctionFragment extends BaseFragment {

    public static ToolsCourierFunctionFragment newInstance() {
        Bundle args = new Bundle();
        ToolsCourierFunctionFragment fragment = new ToolsCourierFunctionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private ToolsFunctionActivity toolsFunctionActivity;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        toolsFunctionActivity = (ToolsFunctionActivity) mActivity;
        setRootView(R.layout.frg_tools_function_courier);
        super.onCreateView(inflater, container, savedInstanceState);
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
        toolsFunctionActivity = null;
    }
}
