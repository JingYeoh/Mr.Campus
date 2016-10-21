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
import android.widget.TextView;

import com.jkb.mrcampus.R;

/**
 * 提示框：有两个按钮可供选择
 * Created by JustKiddingBaby on 2016/10/15.
 */
@SuppressLint("ValidFragment")
public class HintDetermineFloatFragment extends DialogFragment implements View.OnClickListener {

    //data
    private View rootView;
    private Activity mActivity;
    private Dialog mDialog;

    //要用到的数据
    private String title = "";
    private String content = "";
    private String bt1Content = "确定";
    private String bt2Content = "取消";

    //用到的常亮
    private static final String SAVED_STR_TITLE = "saved_str_title";
    private static final String SAVED_STR_CONTENT = "saved_str_content";
    private static final String SAVED_STR_BT1CONTENT = "saved_str_bt1Content";
    private static final String SAVED_STR_BT2CONTENT = "saved_str_bt2Content";

    //用到的组件
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvFirstItem;
    private TextView tvSecondItem;

    private OnDetermineItemClickListener onDetermineItemClickListener;

    /**
     * 提示框的构造方法
     *
     * @param title                        标题栏，不可空
     * @param content                      内容，可空
     * @param bt1Content                   第一个条目的内容，可空
     * @param bt2Content                   第二个可点击条目的内容，可空
     * @param onDetermineItemClickListener 点击的监听回调事件
     */
    public HintDetermineFloatFragment(
            String title, String content, String bt1Content, String bt2Content,
            OnDetermineItemClickListener onDetermineItemClickListener) {
        this.title = title;
        this.content = content;
        this.bt1Content = bt1Content;
        this.bt2Content = bt2Content;
        this.onDetermineItemClickListener = onDetermineItemClickListener;
    }


    public HintDetermineFloatFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mActivity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.frg_dialog_hint_determine, null);
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
        initView();
        initData(savedInstanceState);
        initListener();
    }

    private void initListener() {
        tvFirstItem.setOnClickListener(this);
        tvSecondItem.setOnClickListener(this);
    }

    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            title = savedInstanceState.getString(SAVED_STR_TITLE);
            content = savedInstanceState.getString(SAVED_STR_CONTENT);
            bt1Content = savedInstanceState.getString(SAVED_STR_BT1CONTENT);
            bt2Content = savedInstanceState.getString(SAVED_STR_BT2CONTENT);
        }
        tvContent.setText(content);
        tvTitle.setText(title);
        tvFirstItem.setText(bt1Content);
        tvSecondItem.setText(bt2Content);
    }

    private void initView() {
        tvTitle = (TextView) rootView.findViewById(R.id.fdhd_tv_titleMain);
        tvContent = (TextView) rootView.findViewById(R.id.fdhd_tv_content);
        tvFirstItem = (TextView) rootView.findViewById(R.id.fdhd_tv_sure);
        tvSecondItem = (TextView) rootView.findViewById(R.id.fdhd_tv_cancel);
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        mDialog.setCanceledOnTouchOutside(false);
        //设置动画
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.CENTER);
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
        if (onDetermineItemClickListener == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.fdhd_tv_sure:
                onDetermineItemClickListener.onFirstItemClick();
                break;
            case R.id.fdhd_tv_cancel:
                onDetermineItemClickListener.onSecondItemClick();
                break;
        }
    }

    /**
     * 决定条目的点击监听事件
     */
    public interface OnDetermineItemClickListener {

        /**
         * 第一个按钮点击的时候
         */
        void onFirstItemClick();

        /**
         * 第二个按钮点击的时候
         */
        void onSecondItemClick();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_STR_TITLE, title);
        outState.putString(SAVED_STR_CONTENT, content);
        outState.putString(SAVED_STR_BT1CONTENT, bt1Content);
        outState.putString(SAVED_STR_BT2CONTENT, bt2Content);
    }
}
