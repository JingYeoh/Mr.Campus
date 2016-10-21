package com.jkb.mrcampus.fragment.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.jkb.mrcampus.R;

/**
 * 分享动态的浮动框
 * Created by JustKiddingBaby on 2016/9/14.
 */
@SuppressLint("ValidFragment")
public class ShareDynamicDialogFragment extends DialogFragment implements View.OnClickListener {

    //data
    private View rootView;
    private Activity mActivity;
    private Dialog mDialog;
    private OnShareItemClickListener onShareItemClickListener;

    public ShareDynamicDialogFragment() {
    }

    public ShareDynamicDialogFragment(OnShareItemClickListener onShareItemClickListener) {
        this.onShareItemClickListener = onShareItemClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mActivity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.frg_dialog_share_dynamic, null);
        builder.setView(rootView);
        mDialog = builder.create();
        initConfig();//初始化配置
        //初始化操作
        init(savedInstanceState);
        return mDialog;
    }

    /**
     * 初始化
     */
    private void init(Bundle savedInstanceState) {
        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        rootView.findViewById(R.id.fdsd_iv_wechatCircle).setOnClickListener(this);
        rootView.findViewById(R.id.fdsd_iv_wechat).setOnClickListener(this);
        rootView.findViewById(R.id.fdsd_iv_qq).setOnClickListener(this);
        rootView.findViewById(R.id.fdsd_iv_qqzone).setOnClickListener(this);
        rootView.findViewById(R.id.fdsd_iv_sina).setOnClickListener(this);
        rootView.findViewById(R.id.fdsd_iv_circle).setOnClickListener(this);
        rootView.findViewById(R.id.fdsd_iv_close).setOnClickListener(this);
        rootView.findViewById(R.id.fdsd_blank).setOnClickListener(this);
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        mDialog.setCanceledOnTouchOutside(true);
        //设置动画
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.animate_dialog);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fdsd_iv_wechatCircle:
                onShareItemClick(0);
                break;
            case R.id.fdsd_iv_wechat:
                onShareItemClick(1);
                break;
            case R.id.fdsd_iv_sina:
                onShareItemClick(2);
                break;
            case R.id.fdsd_iv_qqzone:
                onShareItemClick(4);
                break;
            case R.id.fdsd_iv_qq:
                onShareItemClick(3);
                break;
            case R.id.fdsd_iv_circle:
                onShareItemClick(5);
                break;
            case R.id.fdsd_iv_close:
                dismiss();
                break;
            case R.id.fdsd_blank:
                dismiss();
                break;
        }
    }

    private void onShareItemClick(int shareType) {
        if (onShareItemClickListener == null) {
            return;
        }
        switch (shareType) {
            case 0:
                onShareItemClickListener.onWechatCircleClick();
                break;
            case 1:
                onShareItemClickListener.onWechatClick();
                break;
            case 2:
                onShareItemClickListener.onSinaClick();
                break;
            case 3:
                onShareItemClickListener.onQQClick();
                break;
            case 4:
                onShareItemClickListener.onQQZoneClick();
                break;
            case 5:
                onShareItemClickListener.onCircleClick();
                break;
        }
        dismiss();
    }

    public void setOnShareItemClickListener(OnShareItemClickListener onShareItemClickListener) {
        this.onShareItemClickListener = onShareItemClickListener;
    }

    /**
     * 分享条目的点击事件监听
     */
    public interface OnShareItemClickListener {
        void onWechatClick();

        void onWechatCircleClick();

        void onQQClick();

        void onQQZoneClick();

        void onSinaClick();

        void onCircleClick();
    }
}
