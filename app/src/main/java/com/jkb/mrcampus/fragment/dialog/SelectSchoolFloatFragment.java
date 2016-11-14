package com.jkb.mrcampus.fragment.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.jkb.core.Injection;
import com.jkb.core.contract.school.SelectSchoolContract;
import com.jkb.core.contract.school.data.SchoolData;
import com.jkb.core.presenter.school.SelectSchoolPresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.adapter.baseAdapter.SelectSchoolAdapter;

import java.util.List;

/**
 * 选择学校的视图
 * Created by JustKiddingBaby on 2016/9/29.
 */

public class SelectSchoolFloatFragment extends DialogFragment implements
        SelectSchoolContract.View, View.OnClickListener {

    //data
    private View rootView;
    private Activity mActivity;
    private Context context;
    private Dialog mDialog;
    private SelectSchoolContract.Presenter mPresenter;

    //view
    private ListView listView;
    private SelectSchoolAdapter selectSchoolAdapter;

    public SelectSchoolFloatFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mActivity = getActivity();
        context = mActivity.getApplication();
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.frg_float_select_school, null);
        builder.setView(rootView);
        mDialog = builder.create();
        initConfig();//初始化配置
        //初始化操作
        init(savedInstanceState);
        return mDialog;
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

    /**
     * 初始化
     */
    private void init(Bundle savedInstanceState) {
        initView();
        initData(savedInstanceState);
        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        //初始化监听器
        rootView.findViewById(R.id.ffss_iv_close).setOnClickListener(this);
        rootView.findViewById(R.id.ffss_content).setOnClickListener(this);
        selectSchoolAdapter.setOnItemClickListener(onItemClickListener);
    }

    /**
     * 初始化数据
     */
    private void initData(Bundle savedInstanceState) {
        initPresenter();
        //初始化数据
        selectSchoolAdapter = new SelectSchoolAdapter(context, null);
        listView.setAdapter(selectSchoolAdapter);
    }

    /**
     * 初始化P
     */
    private void initPresenter() {
        if (mPresenter == null) {
            mPresenter = new SelectSchoolPresenter(this,
                    Injection.provideSelectSchoolDataRepertory(context));
        }
    }

    /**
     * 初始化View
     */
    private void initView() {
        listView = (ListView) rootView.findViewById(R.id.ffss_lv);
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        mDialog.setCanceledOnTouchOutside(true);
        //设置动画
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.animate_dialog_bottomIn);
        //设置宽度和高度为全屏
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置为全屏
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDialog.getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ffss_content:
            case R.id.ffss_iv_close:
                dismiss();
                break;
        }
    }

    @Override
    public void setSchoolData(List<SchoolData> schoolData) {
        selectSchoolAdapter.schoolDatas = schoolData;
        selectSchoolAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void setPresenter(SelectSchoolContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showReqResult(String value) {
        Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
        context = null;
        mDialog = null;
        listView = null;
        selectSchoolAdapter = null;
        rootView = null;
    }

    /**
     * 条目的点击事件的监听
     */
    private SelectSchoolAdapter.OnItemClickListener onItemClickListener =
            new SelectSchoolAdapter.OnItemClickListener() {
                @Override
                public void onItemCLick(int position) {
                    mPresenter.onSchoolSelected(position);
                    dismiss();
                }
            };
}
