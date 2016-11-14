package com.jkb.mrcampus.fragment.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.jkb.mrcampus.R;

/**
 * 圈子的筛选条件的Fragment
 * Created by JustKiddingBaby on 2016/11/12.
 */

public class CircleFilterFloatFragment extends DialogFragment implements View.OnClickListener {

    //data
    private View rootView;
    private Activity mActivity;
    private Dialog mDialog;

    //监听
    private OnCircleFilterItemClickListener onCircleFilterItemClickListener;

    public static CircleFilterFloatFragment newInstance(
            OnCircleFilterItemClickListener onCircleFilterItemClickListener) {
        Bundle args = new Bundle();
        CircleFilterFloatFragment fragment = new CircleFilterFloatFragment();
        fragment.setOnCircleFilterItemClickListener(onCircleFilterItemClickListener);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mActivity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.frg_dialog_float_filter_map, null);
        builder.setView(rootView);
        mDialog = builder.create();
        initConfig();//初始化配置
        //初始化操作
        init(savedInstanceState);
        return mDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置为全屏
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDialog.getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
    }

    /**
     * 初始化
     */
    private void init(Bundle savedInstanceState) {
        initListener();
    }

    /**
     * 设置点击事件
     */
    private void initListener() {
        rootView.findViewById(R.id.fdffm_all).setOnClickListener(this);
        rootView.findViewById(R.id.fdffm_circle).setOnClickListener(this);
        rootView.findViewById(R.id.fdffm_close).setOnClickListener(this);
        rootView.findViewById(R.id.fdffm_female).setOnClickListener(this);
        rootView.findViewById(R.id.fdffm_user).setOnClickListener(this);
        rootView.findViewById(R.id.fdffm_male).setOnClickListener(this);
        rootView.findViewById(R.id.fdffm_content).setOnClickListener(this);
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        mDialog.setCanceledOnTouchOutside(true);
        //设置动画
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.TOP);
        window.setWindowAnimations(R.style.animate_dialog_topIn);
        //设置宽度和高度为全屏
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fdffm_content:
                dismiss();
                break;
            case R.id.fdffm_close:
                dismiss();
                break;
            case R.id.fdffm_all:
                onClickFilter(0);
                break;
            case R.id.fdffm_circle:
                onClickFilter(1);
                break;
            case R.id.fdffm_user:
                onClickFilter(2);
                break;
            case R.id.fdffm_male:
                onClickFilter(3);
                break;
            case R.id.fdffm_female:
                onClickFilter(4);
                break;
        }
    }

    /**
     * 被点击
     */
    private void onClickFilter(int position) {
        dismiss();
        if (onCircleFilterItemClickListener == null) {
            return;
        }
        switch (position) {
            case 0:
                onCircleFilterItemClickListener.onNoFilterSelected();
                break;
            case 1:
                onCircleFilterItemClickListener.onCircleSelected();
                break;
            case 2:
                onCircleFilterItemClickListener.onUserSelected();
                break;
            case 3:
                onCircleFilterItemClickListener.onUserMaleSelected();
                break;
            case 4:
                onCircleFilterItemClickListener.onUserFemaleSelected();
                break;
        }
    }

    /**
     * 圈子筛选的条目点击监听事件
     */
    public interface OnCircleFilterItemClickListener {
        /**
         * 查看全部
         */
        void onNoFilterSelected();

        /**
         * 只有圈子被选择的时候
         */
        void onCircleSelected();

        /**
         * 当用户被选择的时候
         */
        void onUserSelected();

        /**
         * 当男性用户被选择的时候
         */
        void onUserMaleSelected();

        /**
         * 当女性用户被选择的时候
         */
        void onUserFemaleSelected();
    }

    public void setOnCircleFilterItemClickListener(
            OnCircleFilterItemClickListener onCircleFilterItemClickListener) {
        this.onCircleFilterItemClickListener = onCircleFilterItemClickListener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
        mDialog = null;
        rootView = null;
        onCircleFilterItemClickListener = null;
    }
}
