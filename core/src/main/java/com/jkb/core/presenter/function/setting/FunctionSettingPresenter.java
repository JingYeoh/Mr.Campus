package com.jkb.core.presenter.function.setting;

import android.support.annotation.NonNull;

import com.jkb.core.contract.function.setting.FunctionSettingContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.dataSource.function.setting.FunctionSettingDataRepertory;
import com.jkb.model.info.SchoolInfoSingleton;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;

import jkb.mrcampus.db.entity.Status;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 功能設置的P层
 * Created by JustKiddingBaby on 2016/10/8.
 */
public class FunctionSettingPresenter implements FunctionSettingContract.Presenter {

    private FunctionSettingContract.View view;
    private FunctionSettingDataRepertory repertory;

    public FunctionSettingPresenter(
            @NonNull FunctionSettingContract.View view,
            @NonNull FunctionSettingDataRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        this.view.setPresenter(this);
    }

    @Override
    public void initLoginStatus() {
        if (!view.isActive()) {
            return;
        }
        if (LoginContext.getInstance().isLogined()) {
            view.showLoginView();
        } else {
            view.showLogoutView();
        }
    }

    @Override
    public void initCacheStatus() {
        //使用线程
        Observable.create(new Observable.OnSubscribe<Double>() {
            @Override
            public void call(Subscriber<? super Double> subscriber) {
                double deskCacheSize = ImageLoaderFactory.getInstance().getDeskCacheSize();
                subscriber.onNext(deskCacheSize);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Double>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view.isActive()) {
                            view.setCacheSize(0);
                        }
                    }

                    @Override
                    public void onNext(Double aDouble) {
                        if (view.isActive()) {
                            view.setCacheSize(aDouble);
                        }
                    }
                });
    }

    @Override
    public void onLogin$LogoutClick() {
        boolean isLogin = LoginContext.getInstance().isLogined();
        if (isLogin) {
            //设置登出
            setLoginStatusToLogout();
            if (view.isActive()) {
                view.showLogoutView();
            }
        } else {
            //设置登录
            setLoginStatusToLogin();
        }
    }

    /**
     * 设置登录状态为登录
     */
    private void setLoginStatusToLogin() {
        if (view.isActive()) {
            view.startLoginActivity();
        }
    }

    /**
     * 设置登录状态为登出
     */
    private void setLoginStatusToLogout() {
        //設置系統数据库为登出
        Status status = new Status();
        status.setFlag_login(false);
        status.setUser_id(0);
        status.setVersion(repertory.getSystemCurrentVision());
        status.setFlag_select_school(false);
        status.setSchool_id(0);
        status.setCreated_at(StringUtils.getSystemCurrentTime());
        repertory.saveStatusToDb(status);

        SchoolInfoSingleton.getInstance().setSelectedSchool(false);
        LoginContext.getInstance().setUserState(new LogoutState());
    }

    @Override
    public void onClearCacheClick() {
        ImageLoaderFactory.getInstance().clearDeskCache();
        if (view.isActive()) {
            view.showReqResult("清除成功");
        }
        initCacheStatus();
    }

    @Override
    public void start() {
        //设置是否登录的页面
        initLoginStatus();
        //初始化缓存内容的状态
        initCacheStatus();
    }
}
