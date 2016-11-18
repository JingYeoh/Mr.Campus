package com.jkb.mrcampus.fragment.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.jkb.mrcampus.R;


/**
 * 发布专题动态的V
 * Created by JustKiddingBaby on 2016/11/17.
 */

public class WriteSpecialDialogFragment extends DialogFragment implements View.OnClickListener {

    //data
    private View rootView;
    private Activity mActivity;
    private Dialog mDialog;

    //条目点击事件
    private OnWriteSpecialItemClickListener onWriteSpecialItemClickListener;

    public static WriteSpecialDialogFragment newInstance() {
        Bundle args = new Bundle();
        WriteSpecialDialogFragment fragment = new WriteSpecialDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mActivity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.frg_dialog_write_special, null);
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
        rootView.findViewById(R.id.fdws_ll_specialContent).setOnClickListener(this);
        rootView.findViewById(R.id.fdws_ll_confrssion).setOnClickListener(this);
        rootView.findViewById(R.id.fdws_ll_fleaMarket).setOnClickListener(this);
        rootView.findViewById(R.id.fdws_ll_foundPartner).setOnClickListener(this);
        rootView.findViewById(R.id.fdws_ll_lost$found).setOnClickListener(this);
        rootView.findViewById(R.id.fdws_ll_question).setOnClickListener(this);
        rootView.findViewById(R.id.fdws_ll_taunted).setOnClickListener(this);
        rootView.findViewById(R.id.fdws_iv_close).setOnClickListener(this);
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
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
        mDialog = null;
        rootView = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fdws_ll_specialContent:
            case R.id.fdws_iv_close:
                dismiss();
                break;
            case R.id.fdws_ll_confrssion://表白墙
                onItemClick(0);
                break;
            case R.id.fdws_ll_fleaMarket://跳蚤市场
                onItemClick(1);
                break;
            case R.id.fdws_ll_foundPartner://寻伙伴
                onItemClick(2);
                break;
            case R.id.fdws_ll_lost$found://失物招领
                onItemClick(3);
                break;
            case R.id.fdws_ll_question://求学吧
                onItemClick(4);
                break;
            case R.id.fdws_ll_taunted://吐槽墙
                onItemClick(5);
                break;
        }
    }

    /**
     * 条目类型
     */
    private void onItemClick(int itemType) {
        if (onWriteSpecialItemClickListener == null) {
            return;
        }
        switch (itemType) {
            case 0:
                onWriteSpecialItemClickListener.onConfessionClick();
                break;
            case 1:
                onWriteSpecialItemClickListener.onFleaMarketClick();
                break;
            case 2:
                onWriteSpecialItemClickListener.onFoundPartnerClick();
                break;
            case 3:
                onWriteSpecialItemClickListener.onLost$FoundClick();
                break;
            case 4:
                onWriteSpecialItemClickListener.onQuestionClick();
                break;
            case 5:
                onWriteSpecialItemClickListener.onTauntedClick();
                break;
        }
        dismiss();
    }

    public interface OnWriteSpecialItemClickListener {
        /**
         * 关闭
         */
        void onClosed();

        /**
         * 表白墙被点击
         */
        void onConfessionClick();

        /**
         * 跳蚤市场被点击
         */
        void onFleaMarketClick();

        /**
         * 寻伙伴被点击
         */
        void onFoundPartnerClick();

        /**
         * 失物招领被点击
         */
        void onLost$FoundClick();

        /**
         * 求学霸被点击
         */
        void onQuestionClick();

        /**
         * 吐槽被点击
         */
        void onTauntedClick();
    }

    public void setOnWriteSpecialItemClickListener(
            OnWriteSpecialItemClickListener onWriteSpecialItemClickListener) {
        this.onWriteSpecialItemClickListener = onWriteSpecialItemClickListener;
    }
}
