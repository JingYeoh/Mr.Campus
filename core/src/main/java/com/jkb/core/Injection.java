package com.jkb.core;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.model.dataSource.baidu.map.webService.BaiduMapWebServiceResponsitory;
import com.jkb.model.dataSource.baidu.map.webService.local.BaiduMapWebServiceLocalDataSource;
import com.jkb.model.dataSource.baidu.map.webService.remote.BaiduMapWebServiceRemoteDataSource;
import com.jkb.model.dataSource.circle.circleIndex.CircleIndexDataResponsitiry;
import com.jkb.model.dataSource.circle.circleIndex.local.CircleIndexLocalDataSource;
import com.jkb.model.dataSource.circle.circleIndex.remote.CircleIndexRemoteDataSource;
import com.jkb.model.dataSource.circleList.CircleListDataResponsitory;
import com.jkb.model.dataSource.circleList.local.CircleListLocalDatasource;
import com.jkb.model.dataSource.circleList.remote.CircleListRemoteDatasource;
import com.jkb.model.dataSource.comment.list.CommentListRepository;
import com.jkb.model.dataSource.comment.list.local.CommentListLocalDataSource;
import com.jkb.model.dataSource.comment.list.remote.CommentListRemoteDataSource;
import com.jkb.model.dataSource.comment.singleAll.CommentSingleAllRepository;
import com.jkb.model.dataSource.comment.singleAll.local.CommentSingleAllLocalDataSource;
import com.jkb.model.dataSource.comment.singleAll.remote.CommentSingleAllRemoteDataSource;
import com.jkb.model.dataSource.create$circle.CircleCreateDataResponsitory;
import com.jkb.model.dataSource.create$circle.local.CircleCreateLocalDataSource;
import com.jkb.model.dataSource.create$circle.remote.CircleCreateRemoteDataSource;
import com.jkb.model.dataSource.dynamicDetail.article.DynamicDetailArticleRepository;
import com.jkb.model.dataSource.dynamicDetail.article.local.DynamicDetailArticleLocalDataSource;
import com.jkb.model.dataSource.dynamicDetail.article.remote.DynamicDetailArticleRemoteDataSource;
import com.jkb.model.dataSource.dynamicDetail.normal.DynamicDetailNormalRepository;
import com.jkb.model.dataSource.dynamicDetail.normal.local.DynamicDetailNormalLocalDataSource;
import com.jkb.model.dataSource.dynamicDetail.normal.remote.DynamicDetailNormalRemoteDataSource;
import com.jkb.model.dataSource.dynamicDetail.topic.DynamicDetailTopicRepository;
import com.jkb.model.dataSource.dynamicDetail.topic.local.DynamicDetailTopicLocalDataSource;
import com.jkb.model.dataSource.dynamicDetail.topic.remote.DynamicDetailTopicRemoteDataSource;
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
import com.jkb.model.dataSource.function.index.dynamic.DynamicDataRepository;
import com.jkb.model.dataSource.function.index.dynamic.local.DynamicLocalDataSource;
import com.jkb.model.dataSource.function.index.dynamic.remote.DynamicRemoteDataSource;
import com.jkb.model.dataSource.menuRight.friends.FriendsRepertory;
import com.jkb.model.dataSource.menuRight.friends.local.FriendsLocalDataSource;
import com.jkb.model.dataSource.menuRight.friends.remote.FriendsRemoteDataSource;
import com.jkb.model.dataSource.menuRight.rightMenu.RightMenuDataRepertory;
import com.jkb.model.dataSource.menuRight.rightMenu.local.RightMenuLocalDataSource;
import com.jkb.model.dataSource.menuRight.rightMenu.remote.RightMenuRemoteDataSource;
import com.jkb.model.dataSource.personCenter.PersonCenterDataResponsitory;
import com.jkb.model.dataSource.personCenter.local.PersonCenterLocalDataSource;
import com.jkb.model.dataSource.personCenter.remote.PersonCenterRemoteDataSource;
import com.jkb.model.dataSource.personSetting.PersonSettingDataResponsitory;
import com.jkb.model.dataSource.personSetting.local.PersonSettingLocalDataSource;
import com.jkb.model.dataSource.personSetting.remote.PersonSettingRemoteDataSource;
import com.jkb.model.dataSource.usersList.attention.AttentionDataResponsitory;
import com.jkb.model.dataSource.usersList.attention.local.AttentionLocalDataSource;
import com.jkb.model.dataSource.usersList.attention.remote.AttentionRemoteDataSource;
import com.jkb.model.dataSource.usersList.fans.FansDataResponsitory;
import com.jkb.model.dataSource.usersList.fans.local.FansDataLocalSource;
import com.jkb.model.dataSource.usersList.fans.remote.FansDataRemoteSource;
import com.jkb.model.dataSource.usersList.visitor.VisitorDataResponsitory;
import com.jkb.model.dataSource.usersList.visitor.local.VisitorLocalDataSource;
import com.jkb.model.dataSource.usersList.visitor.remote.VisitorRemoteDataSource;

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
     * 得到VisitorDataResponsitory对象
     *
     * @param applicationContext 上下文
     * @return VisitorDataResponsitory
     */
    public static VisitorDataResponsitory provideVisitorDataResponsitory(
            @NonNull Context applicationContext) {
        VisitorDataResponsitory responsitory = VisitorDataResponsitory.getInstance(
                VisitorLocalDataSource.getInstance(applicationContext),
                VisitorRemoteDataSource.getInstance()
        );
        return responsitory;
    }

    /**
     * FansDataResponsitory
     *
     * @param applicationContext 上下文
     * @return FansDataResponsitory
     */
    public static FansDataResponsitory provideFansDataResponsitory(
            @NonNull Context applicationContext) {
        FansDataResponsitory responsitory = FansDataResponsitory.getInstance(
                FansDataLocalSource.getInstance(applicationContext),
                FansDataRemoteSource.getInstance()
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

    /**
     * 返回PersonSettingDataResponsitory实例
     *
     * @param applicationContext 上下文
     * @return PersonSettingDataResponsitory
     */
    public static PersonSettingDataResponsitory providePersonSettingDataResponsitory(
            @NonNull Context applicationContext) {
        PersonSettingDataResponsitory responsitory = PersonSettingDataResponsitory.getInstance(
                PersonSettingLocalDataSource.getInstance(applicationContext),
                PersonSettingRemoteDataSource.getInstance()
        );
        return responsitory;
    }

    /**
     * 返回CircleIndexDataResponsitiry实例
     *
     * @param applicationContext 上下文
     * @return CircleIndexDataResponsitiry
     */
    public static CircleIndexDataResponsitiry provideCircleIndexDataResponsitiry(
            @NonNull Context applicationContext) {
        CircleIndexDataResponsitiry responsitiry = CircleIndexDataResponsitiry.getInstance(
                CircleIndexLocalDataSource.getInstance(applicationContext),
                CircleIndexRemoteDataSource.getInstance()
        );
        return responsitiry;
    }

    /**
     * 得到CircleListDataResponsitory对象
     */
    public static CircleListDataResponsitory provideCircleListDataResponsitory(
            @NonNull Context applicationContext) {
        CircleListDataResponsitory responsitory = CircleListDataResponsitory.getInstance(
                CircleListLocalDatasource.getInstance(applicationContext),
                CircleListRemoteDatasource.getInstance());
        return responsitory;
    }

    /**
     * 得到DynamicDataResponsitory对象
     */
    public static DynamicDataRepository provideDynamicDataResponsitory(
            @NonNull Context applicationContext) {
        DynamicDataRepository responsitory = DynamicDataRepository.getInstance(
                DynamicLocalDataSource.getInstance(applicationContext),
                DynamicRemoteDataSource.getInstance());
        return responsitory;
    }

    /**
     * 得到RightMenuDataRepertory对象
     */
    public static RightMenuDataRepertory provideRightMenuDataRepertory(
            @NonNull Context applicationContext) {
        RightMenuDataRepertory repertory = RightMenuDataRepertory.getInstance(
                RightMenuLocalDataSource.getInstance(applicationContext),
                RightMenuRemoteDataSource.getInstance());
        return repertory;
    }

    /**
     * 得到FriendsRepertory对象
     */
    public static FriendsRepertory provideFriendsRepertory(@NonNull Context applicationContext) {
        FriendsRepertory repertory = FriendsRepertory.getInstance(
                FriendsLocalDataSource.getInstance(applicationContext),
                FriendsRemoteDataSource.getInstance()
        );
        return repertory;
    }

    /**
     * 得到DynamicDetailNormalRepository对象
     */
    public static DynamicDetailNormalRepository provideDynamicDetailNormalRepository(
            @NonNull Context applicationContext) {
        DynamicDetailNormalRepository repository = DynamicDetailNormalRepository.getInstance(
                DynamicDetailNormalLocalDataSource.getInstance(applicationContext),
                DynamicDetailNormalRemoteDataSource.getInstance());
        return repository;
    }

    /**
     * 得到DynamicDetailTopicRepository对象
     */
    public static DynamicDetailTopicRepository provideDynamicDetailTopicRepository(
            @NonNull Context applicationContext) {
        DynamicDetailTopicRepository repository = DynamicDetailTopicRepository.getInstance(
                DynamicDetailTopicLocalDataSource.getInstance(applicationContext),
                DynamicDetailTopicRemoteDataSource.getInstance());
        return repository;
    }

    /**
     * 得到DynamicDetailArticleRepository对象
     */
    public static DynamicDetailArticleRepository provideDynamicDetailArticleRepository(
            @NonNull Context applicationContext) {
        DynamicDetailArticleRepository repository = DynamicDetailArticleRepository.getInstance(
                DynamicDetailArticleLocalDataSource.getInstance(applicationContext),
                DynamicDetailArticleRemoteDataSource.getInstance());
        return repository;
    }

    /**
     * 得到CommentListRepository对象
     */
    public static CommentListRepository provideCommentListRepository(
            @NonNull Context applicationContext) {
        CommentListRepository repository = CommentListRepository.getInstance(
                CommentListLocalDataSource.genInstance(applicationContext),
                CommentListRemoteDataSource.genInstance());
        return repository;
    }

    /**
     * 得到CommentSingleAllRepository对象
     */
    public static CommentSingleAllRepository provideCommentSingleAllRepository(
            @NonNull Context applicationContext) {
        CommentSingleAllRepository repository = CommentSingleAllRepository.getInstance(
                CommentSingleAllLocalDataSource.genInstance(applicationContext),
                CommentSingleAllRemoteDataSource.genInstance());
        return repository;
    }
}
