package com.jkb.mrcampus.fragment.first;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.first.WelcomeContract;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.FirstActivity;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.base.BaseFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 欢迎页面的Fragment，用于数据的初始化时候的缓冲
 * Created by JustKiddingBaby on 2016/7/21.
 */
public class WelcomeFragment extends BaseFragment implements WelcomeContract.View {


    private FirstActivity firstActivity;
    //用到的View层控件
    private ImageView imageView;
    private TextView tvCount;

    //用到的逻辑数据
    private int count = 3;
    private static final String SAVED_COUNT = "savedCount";
    private static final int WHAT_COUNT = 1;//what参数
    private static final int TIME_SLOT = 1000;//倒计时的时间间隙

    //用到的Presenter层数据
    private WelcomeContract.Presenter mPresenter;

    public WelcomeFragment() {
    }

    /**
     * 获得一个实例化的WelcomeFragment对象
     *
     * @return
     */
    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_first_welcome);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            count = savedInstanceState.getInt(SAVED_COUNT);
        }

        if (mActivity instanceof FirstActivity) {
            firstActivity = (FirstActivity) mActivity;
        }
    }

    @Override
    protected void initView() {
        imageView = (ImageView) rootView.findViewById(R.id.ffw_iv);
        tvCount = (TextView) rootView.findViewById(R.id.tv_count);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_COUNT:
                    handleCount();
                    break;
            }
        }
    };

    /**
     * 处理倒计时
     */
    private void handleCount() {
        if (count > 0) {
            count--;
            tvCount.setText(count + "");
            handler.sendEmptyMessageDelayed(WHAT_COUNT, TIME_SLOT);
        } else {
            completedCount();
        }
    }

    @Override
    public void startCount() {
        handler.sendEmptyMessageDelayed(WHAT_COUNT, TIME_SLOT);
    }

    @Override
    public void stopCount() {
        completedCount();
    }

    @Override
    public void completedCount() {
        startMainActivity();
        handler = null;
        mActivity.finish();
    }

    /**
     * 打开MainActivity
     */
    private void startMainActivity() {
        firstActivity.startMenuActivity();
    }

    @Override
    public void showBackGround(Bitmap bitmap) {
//        imageView = (ImageView) rootView.findViewById(R.id.ffw_iv);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(WelcomeContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showLoading(String value) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showReqResult(String value) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_COUNT, count);
    }
}