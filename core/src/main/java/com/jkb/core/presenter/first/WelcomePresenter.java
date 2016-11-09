package com.jkb.core.presenter.first;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.jkb.core.contract.first.WelcomeContract;
import com.jkb.model.dataSource.first.welcome.WelcomeData;
import com.jkb.model.dataSource.first.welcome.WelcomeDataResponsitory;
import com.jkb.model.dataSource.first.welcome.WelcomeDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Welcome页面的Presenter类
 * Created by JustKiddingBaby on 2016/7/21.
 */
public class WelcomePresenter implements WelcomeContract.Presenter {


    private WelcomeContract.View welcomeView;
    private WelcomeDataResponsitory welcomeDataResponsitory;

    /**
     * 初始化Presenter，并且绑定View
     */
    public WelcomePresenter(
            @NonNull WelcomeDataResponsitory welcomeDataResponsitory,
            @NonNull WelcomeContract.View welcomeView) {
        this.welcomeView = welcomeView;
        this.welcomeDataResponsitory = welcomeDataResponsitory;

        this.welcomeView.setPresenter(this);
    }

    @Override
    public void start() {
        chooseBitmapResource();
    }

    @Override
    public void setBackgroundBitmap(Bitmap bitmap) {
        //设置Bitmap对象进去
        welcomeView.showBackGround(bitmap);
    }

    @Override
    public void chooseBitmapResource() {
        //得到图片
        welcomeDataResponsitory.getWelcomeBackground(new WelcomeDataSource.GetBitmapCallBack() {
            @Override
            public void onBitmapLoaded(WelcomeData welcomeData) {
                setBackgroundBitmap(welcomeData.getBitmap());
                //设置倒计时开始
//                welcomeView.startCount();
            }

            @Override
            public void onDataNotAvailable() {
                setBackgroundBitmap(null);
                //设置倒计时开始
//                welcomeView.startCount();
            }
        });
    }
}
