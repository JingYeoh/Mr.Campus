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
 * 写动态的DialogFragment
 * Created by JustKiddingBaby on 2016/9/6.
 */
@SuppressLint("ValidFragment")
public class WriteDynamicDialogFragment extends DialogFragment implements View.OnClickListener {

    //data
    private View rootView;
    private Activity mActivity;
    private Dialog mDialog;
    private OnWriteDynamicClickListener onWriteDynamicClickListener;

    public WriteDynamicDialogFragment(
            @NonNull OnWriteDynamicClickListener onWriteDynamicClickListener) {
        this.onWriteDynamicClickListener = onWriteDynamicClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mActivity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.frg_dialog_write_dynamic, null);
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
        rootView.findViewById(R.id.fdwd_content).setOnClickListener(this);
        rootView.findViewById(R.id.fdwd_ll_normal).setOnClickListener(this);
        rootView.findViewById(R.id.fdwd_ll_article).setOnClickListener(this);
        rootView.findViewById(R.id.fdwd_ll_topic).setOnClickListener(this);
        rootView.findViewById(R.id.fdwd_iv_close).setOnClickListener(this);
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        mDialog.setCanceledOnTouchOutside(false);
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
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
        mDialog = null;
        rootView = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fdwd_content:
//                dismiss();
                break;
            case R.id.fdwd_ll_normal:
                onNormalClick();
                break;
            case R.id.fdwd_ll_article:
                onArticleClick();
                break;
            case R.id.fdwd_ll_topic:
                onTopicClick();
                break;
            case R.id.fdwd_iv_close:
                dismiss();
                break;
        }
    }

    /**
     * 点击话题的时候
     */
    private void onTopicClick() {
        if (onWriteDynamicClickListener != null) {
            onWriteDynamicClickListener.onTopicClick();
        }
        dismiss();
    }

    /**
     * 点击文章的时候
     */
    private void onArticleClick() {
        if (onWriteDynamicClickListener != null) {
            onWriteDynamicClickListener.onArticleClick();
        }
        dismiss();
    }

    /**
     * 点击普通的时候
     */
    private void onNormalClick() {
        if (onWriteDynamicClickListener != null) {
            onWriteDynamicClickListener.onNormalClick();
        }
        dismiss();
    }

    /**
     * 发表动态点击的监听器
     */
    public interface OnWriteDynamicClickListener {
        /**
         * 点击话题回调
         */
        void onTopicClick();

        /**
         * 点击文章的时候
         */
        void onArticleClick();

        /**
         * 点击普通的时候
         */
        void onNormalClick();
    }
}
