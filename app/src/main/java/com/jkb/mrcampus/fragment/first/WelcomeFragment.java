package com.jkb.mrcampus.fragment.first;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jkb.core.contract.first.WelcomeContract;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 欢迎页面的Fragment，用于数据的初始化时候的缓冲
 * Created by JustKiddingBaby on 2016/7/21.
 */
public class WelcomeFragment extends BaseFragment implements WelcomeContract.View {


    private ImageView imageView;

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

    }

    @Override
    protected void initView() {
        imageView = (ImageView) rootView.findViewById(R.id.ffw_iv);
    }

    @Override
    public void startCount() {

    }

    @Override
    public void stopCount() {

    }

    @Override
    public void completedCount() {

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
}