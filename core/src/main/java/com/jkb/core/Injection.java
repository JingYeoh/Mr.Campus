package com.jkb.core;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.model.dataSource.baidu.map.webService.BaiduMapWebServiceResponsitory;
import com.jkb.model.dataSource.baidu.map.webService.local.BaiduMapWebServiceLocalDataSource;
import com.jkb.model.dataSource.baidu.map.webService.remote.BaiduMapWebServiceRemoteDataSource;
import com.jkb.model.dataSource.create$circle.CircleCreateDataResponsitory;
import com.jkb.model.dataSource.create$circle.local.CircleCreateLocalDataSource;
import com.jkb.model.dataSource.create$circle.remote.CircleCreateRemoteDataSource;
import com.jkb.model.dataSource.entering.login.LoginResponsitory;
import com.jkb.model.dataSource.entering.login.local.LoginLocalDataSource;
import com.jkb.model.dataSource.entering.login.remote.LoginRemoteDataSource;
import com.jkb.model.dataSource.entering.personmessage.PersonMessageResponsitory;
import com.jkb.model.dataSource.entering.personmessage.local.PersonMessageLocalDataSource;
import com.jkb.model.dataSource.entering.personmessage.remote.PersonMessageRemoteDataSource;
import com.jkb.model.dataSource.entering.identify.IdentifyCodeResponsitory;
import com.jkb.model.dataSource.entering.identify.local.IdentifyCodeLocalDataSource;
import com.jkb.model.dataSource.entering.identify.remote.IdentifyCodeRemoteDataSource;
import com.jkb.model.dataSource.entering.resetpassword.ResetPasswordResponsitory;
import com.jkb.model.dataSource.entering.resetpassword.local.ResetpasswordLocalDataSource;
import com.jkb.model.dataSource.entering.resetpassword.remote.ResetpasswordRemoteDataSource;
import com.jkb.model.dataSource.first.firstlogic.FirstDataResponsitory;
import com.jkb.model.dataSource.first.firstlogic.local.FirstLocalDataSource;
import com.jkb.model.dataSource.first.firstlogic.remote.FirstRemoteDataSource;
import com.jkb.model.dataSource.first.welcome.WelcomeDataResponsitory;
import com.jkb.model.dataSource.first.welcome.local.WelcomeLocalDataSource;
import com.jkb.model.dataSource.first.welcome.remote.WelcomeRemoteDataSource;
import com.jkb.model.dataSource.personCenter.PersonCenterDataResponsitory;
import com.jkb.model.dataSource.personCenter.local.PersonCenterLocalDataSource;
import com.jkb.model.dataSource.personCenter.remote.PersonCenterRemoteDataSource;
import com.jkb.model.dataSource.usersList.attention.AttentionDataResponsitory;
import com.jkb.model.dataSource.usersList.attention.local.AttentionLocalDataSource;
import com.jkb.model.dataSource.usersList.attention.remote.AttentionRemoteDataSource;

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

    /**
     * 得到BaiduMapWebServiceResponsitory对象
     *
     * @param applicationContext
     * @return
     */
    public static BaiduMapWebServiceResponsitory provideBaiduMapWebServiceResponsitory(
            @NonNull Context applicationContext) {
        BaiduMapWebServiceResponsitory responsitory = BaiduMapWebServiceResponsitory.getInstance(
                BaiduMapWebServiceLocalDataSource.getInstance(applicationContext),
                BaiduMapWebServiceRemoteDataSource.getInstance()
        );
        return responsitory;
    }

    /**
     * 得到CircleCreateDataResponsitory对象
     *
     * @param applicationContext
     * @return
     */
    public static CircleCreateDataResponsitory provideCircleCreateDataResponsitory(
            @NonNull Context applicationContext) {
        CircleCreateDataResponsitory responsitory = CircleCreateDataResponsitory.getInstance(
                CircleCreateLocalDataSource.getInstance(applicationContext),
                CircleCreateRemoteDataSource.getInstance()
        );
        return responsitory;
    }

    /**
     * 得到AttentionDataResponsitory对象
     *
     * @param applicationContext 上下文
     * @return AttentionDataResponsitory
     */
    public static AttentionDataResponsitory provideAttentionDataResponsitory(
            @NonNull Context applicationContext) {
        AttentionDataResponsitory responsitory = AttentionDataResponsitory.getInstance(
                AttentionLocalDataSource.getInstance(applicationContext),
                AttentionRemoteDataSource.getInstance()
        );
        return responsitory;
    }

    /**
     * 返回PersonCenterDataResponsitory实例
     *
     * @param applicationContext 上下文
     * @return PersonCenterDataResponsitory
     */
    public static PersonCenterDataResponsitory providePersonCenterDataResponsitory(
            @NonNull Context applicationContext) {
        PersonCenterDataResponsitory responsitory = PersonCenterDataResponsitory.getInstance(
                PersonCenterLocalDataSource.getInstance(applicationContext),
                PersonCenterRemoteDataSource.getInstance()
        );
        return responsitory;
    }
}
