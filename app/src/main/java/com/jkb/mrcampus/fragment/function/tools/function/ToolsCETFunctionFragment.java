package com.jkb.mrcampus.fragment.function.tools.function;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.core.contract.function.tools.ToolsCETContract;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.ToolsFunctionActivity;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 四六级查询的V层
 * Created by JustKiddingBaby on 2016/11/15.
 */

public class ToolsCETFunctionFragment extends BaseFragment implements
        ToolsCETContract.View, View.OnClickListener {

    public static ToolsCETFunctionFragment newInstance() {
        Bundle args = new Bundle();
        ToolsCETFunctionFragment fragment = new ToolsCETFunctionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private ToolsFunctionActivity toolsFunctionActivity;
    private ToolsCETContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        toolsFunctionActivity = (ToolsFunctionActivity) mActivity;
        setRootView(R.layout.frg_tools_function_cet);
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
        if (!isHidden()) {
            mPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.ftsc_bt_search).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("CET成绩查询");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toolsFunctionActivity = null;
    }

    @Override
    public void sendCetValue() {
        String zkzh = ((TextView) rootView.findViewById(R.id.ftsc_et_zkzh)).getText().toString();
        String xm = ((TextView) rootView.findViewById(R.id.ftsc_et_xm)).getText().toString();
        mPresenter.getCetReportCard(zkzh, xm);
    }

    @Override
    public void showErrorView() {
        rootView.findViewById(R.id.ftsc_contentReportCard).setVisibility(View.GONE);
    }

    @Override
    public void setCetReportCardValue(
            String name, String school, String type, String num,
            int total, int listen, int read, int write) {
        rootView.findViewById(R.id.ftsc_contentReportCard).setVisibility(View.VISIBLE);
        ((TextView) rootView.findViewById(R.id.ftsc_tv_name)).setText(name);
        ((TextView) rootView.findViewById(R.id.ftsc_tv_school)).setText(school);
        ((TextView) rootView.findViewById(R.id.ftsc_tv_type)).setText(type);
        ((TextView) rootView.findViewById(R.id.ftsc_tv_zkzh)).setText(num);
        ((TextView) rootView.findViewById(R.id.ftsc_tv_total)).setText(total + "");
        ((TextView) rootView.findViewById(R.id.ftsc_tv_listen)).setText(listen + "");
        ((TextView) rootView.findViewById(R.id.ftsc_tv_read)).setText(read + "");
        ((TextView) rootView.findViewById(R.id.ftsc_tv_write)).setText(write + "");
    }

    @Override
    public void setPresenter(ToolsCETContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        toolsFunctionActivity.showLoading(value, this);
    }

    @Override
    public void dismissLoading() {
        toolsFunctionActivity.dismissLoading();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts4_ib_left:
                toolsFunctionActivity.onBackPressed();
                break;
            case R.id.ftsc_bt_search:
                sendCetValue();
                break;
        }
    }
}
