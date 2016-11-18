package com.jkb.mrcampus.fragment.function.tools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 小工具的页面
 * Created by JustKiddingBaby on 2016/10/11.
 */

public class ToolsFragment extends BaseFragment implements View.OnClickListener {

    public static ToolsFragment newInstance() {
        return new ToolsFragment();
    }

    //data
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) mActivity;
        setRootView(R.layout.frg_function_tools);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.fft_ll_cet).setOnClickListener(this);
        rootView.findViewById(R.id.fft_ll_courier).setOnClickListener(this);
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        //初始化标题栏
        ((TextView) (rootView.findViewById(R.id.ts4_tv_name))).setText("小工具");
        ((ImageView) rootView.findViewById(R.id.ts4_ib_left)).setImageResource(R.drawable.ic_drawer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivity = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts4_ib_left://返回
                mainActivity.showLeftMenuView();
                break;
            case R.id.fft_ll_cet://四六级
                mainActivity.startToolsFunctionCET();
                break;
            case R.id.fft_ll_courier://快递查询
                mainActivity.startToolsFunctionCourier();
                break;
        }
    }
}
