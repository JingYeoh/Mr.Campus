package com.jkb.core;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.model.entering.login.LoginResponsitory;
import com.jkb.model.entering.login.local.LoginLocalDataSource;
import com.jkb.model.entering.login.remote.LoginRemoteDataSource;
import com.jkb.model.entering.personmessage.PersonMessageResponsitory;
import com.jkb.model.entering.personmessage.local.PersonMessageLocalDataSource;
import com.jkb.model.entering.personmessage.remote.PersonMessageRemoteDataSource;
import com.jkb.model.entering.identify.IdentifyCodeResponsitory;
import com.jkb.model.entering.identify.local.IdentifyCodeLocalDataSource;
import com.jkb.model.entering.identify.remote.IdentifyCodeRemoteDataSource;
import com.jkb.model.entering.resetpassword.ResetPasswordResponsitory;
import com.jkb.model.entering.resetpassword.local.ResetpasswordLocalDataSource;
import com.jkb.model.entering.resetpassword.remote.ResetpasswordRemoteDataSource;
import com.jkb.model.first.firstlogic.FirstDataResponsitory;
import com.jkb.model.first.firstlogic.local.FirstLocalDataSource;
import com.jkb.model.first.firstlogic.remote.FirstRemoteDataSource;
import com.jkb.model.first.welcome.WelcomeDataResponsitory;
import com.jkb.model.first.welcome.local.WelcomeLocalDataSource;
import com.jkb.model.first.welcome.remote.WelcomeRemoteDataSource;

/**
 * 用于创建Presenter的集合工具类
 * Created by JustKiddingBaby on 2016/7/24.
 */
public class Injection {

    /**
     * 得到FirstDataResponsitory实例
     *
     * @param context
     * @return
     */
    public static FirstDataResponsitory provideFirstResponsitory(Context context) {
        FirstLocalDataSource firstLocalDataSource = FirstLocalDataSource.getInstance(context);
        FirstDataResponsitory responsitory = FirstDataResponsitory.getInstance(firstLocalDataSource
                , FirstRemoteDataSource.getInstance(context));
        return responsitory;
    }

    /**
     * 得到WelcomeDataResponsitory的实例
     *
     * @param context
     * @return
     */
    public static WelcomeDataResponsitory provideWelcomeResponsitory(Context context) {
        WelcomeDataResponsitory welcomeDataResponsitory = WelcomeDataResponsitory.getInstance(WelcomeLocalDataSource.getInstance(context),
                WelcomeRemoteDataSource.getInstance());
        return welcomeDataResponsitory;
    }

    /**
     * 得到IdentifyCodeResponsitory类的实例
     *
     * @return
     */
    public static IdentifyCodeResponsitory provideIdentifyCodeResponsitory() {
        IdentifyCodeResponsitory responsitory = IdentifyCodeResponsitory.getInstance(
                IdentifyCodeLocalDataSource.getInstance(), IdentifyCodeRemoteDataSource.getInstance()
        );
        return responsitory;
    }

    /**
     * 得到PersonMessageResponsitory的实例
     *
     * @return
     */
    public static PersonMessageResponsitory providePersonMessageResponsotory(Context context) {
        PersonMessageResponsitory responsitory = PersonMessageResponsitory.getInstance(
                PersonMessageLocalDataSource.getInstance(context), PersonMessageRemoteDataSource.getInstance()
        );
        return responsitory;
    }

    /**
     * 得到LoginResponsitory的对象
     *
     * @param applicationContext
     * @return
     */
    public static LoginResponsitory provideLoginResponsitory(@NonNull Context applicationContext) {
        LoginResponsitory responsitory = LoginResponsitory.getInstance(
                LoginLocalDataSource.getInstance(applicationContext),
                LoginRemoteDataSource.getInstance(applicationContext)
        );
        return responsitory;
    }

    /**
     * 得到ResetPasswordResponsitory对象
     *
     * @param applicationContext
     * @return
     */
    public static ResetPasswordResponsitory provideResetPasswordResponsitory(
            @NonNull Context applicationContext) {
        ResetPasswordResponsitory responsitory = ResetPasswordResponsitory.getInstance(
                ResetpasswordLocalDataSource.getInstance(applicationContext),
                ResetpasswordRemoteDataSource.getInstance()
        );
        return responsitory;
    }

}
