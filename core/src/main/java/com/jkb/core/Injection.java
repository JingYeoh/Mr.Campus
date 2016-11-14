package com.jkb.core;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.model.dataSource.baidu.map.webService.BaiduMapWebServiceResponsitory;
import com.jkb.model.dataSource.baidu.map.webService.local.BaiduMapWebServiceLocalDataSource;
import com.jkb.model.dataSource.baidu.map.webService.remote.BaiduMapWebServiceRemoteDataSource;
import com.jkb.model.dataSource.circle.attentionUserList.CircleAttentionUserListRepertory;
import com.jkb.model.dataSource.circle.attentionUserList.local.CircleAttentionUserListLocalDataSource;
import com.jkb.model.dataSource.circle.attentionUserList.remote.CircleAttentionUserListRemoteDataSource;
import com.jkb.model.dataSource.circle.circleIndex.CircleIndexDataRepertory;
import com.jkb.model.dataSource.circle.circleIndex.local.CircleIndexLocalDataSource;
import com.jkb.model.dataSource.circle.circleIndex.remote.CircleIndexRemoteDataSource;
import com.jkb.model.dataSource.circle.circleSetting.user.CircleSettingUserDataRepertory;
import com.jkb.model.dataSource.circle.circleSetting.user.local.CircleSettingUserLocalDataSource;
import com.jkb.model.dataSource.circle.circleSetting.user.remote.CircleSettingUserRemoteDataSource;
import com.jkb.model.dataSource.circle.dynamiInBlackList.CircleDynamicInCircleBlackListRepertory;
import com.jkb.model.dataSource.circle.dynamiInBlackList.local.CircleDynamicInCircleBlackListLocalDataSource;
import com.jkb.model.dataSource.circle.dynamiInBlackList.remote.CircleDynamicInCircleBlackListRemoteDataSource;
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
import com.jkb.model.dataSource.dynamicCreate.article.DynamicCreateArticleDataRepertory;
import com.jkb.model.dataSource.dynamicCreate.article.local.DynamicCreateArticleLocalDataSource;
import com.jkb.model.dataSource.dynamicCreate.article.remote.DynamicCreateArticleRemoteDataSource;
import com.jkb.model.dataSource.dynamicCreate.normal.DynamicCreateNormalDataRepertory;
import com.jkb.model.dataSource.dynamicCreate.normal.local.DynamicCreateNormalLocalDataSource;
import com.jkb.model.dataSource.dynamicCreate.normal.remote.DynamicCreateNormalRemoteDataSource;
import com.jkb.model.dataSource.dynamicCreate.topic.DynamicCreateTopicDataRepertory;
import com.jkb.model.dataSource.dynamicCreate.topic.local.DynamicCreateTopicLocalDataSource;
import com.jkb.model.dataSource.dynamicCreate.topic.remote.DynamicCreateTopicRemoteDataSource;
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
import com.jkb.model.dataSource.function.index.hot.DynamicHotDataRepository;
import com.jkb.model.dataSource.function.index.hot.local.DynamicHotLocalDataSource;
import com.jkb.model.dataSource.function.index.hot.remote.DynamicHotRemoteDataSource;
import com.jkb.model.dataSource.function.setting.FunctionSettingDataRepertory;
import com.jkb.model.dataSource.function.setting.local.FunctionSettingLocalDataSource;
import com.jkb.model.dataSource.function.setting.remote.FunctionSettingRemoteDataSource;
import com.jkb.model.dataSource.im.conversation.ConversationRepertory;
import com.jkb.model.dataSource.im.conversation.local.ConversationLocalDataSource;
import com.jkb.model.dataSource.im.conversation.remote.ConversationRemoteDataSource;
import com.jkb.model.dataSource.map.mapList.MapListRepertory;
import com.jkb.model.dataSource.map.mapList.local.MapListLocalDataSource;
import com.jkb.model.dataSource.map.mapList.remote.MapListRemoteDataSource;
import com.jkb.model.dataSource.menuRight.friends.FriendsRepertory;
import com.jkb.model.dataSource.menuRight.friends.local.FriendsLocalDataSource;
import com.jkb.model.dataSource.menuRight.friends.remote.FriendsRemoteDataSource;
import com.jkb.model.dataSource.menuRight.rightMenu.RightMenuDataRepertory;
import com.jkb.model.dataSource.menuRight.rightMenu.local.RightMenuLocalDataSource;
import com.jkb.model.dataSource.menuRight.rightMenu.remote.RightMenuRemoteDataSource;
import com.jkb.model.dataSource.myDynamic.article.MyDynamicArticleRepertory;
import com.jkb.model.dataSource.myDynamic.article.local.MyDynamicArticleLocalDataSource;
import com.jkb.model.dataSource.myDynamic.article.remote.MyDynamicArticleRemoteDataSource;
import com.jkb.model.dataSource.myDynamic.circle.MyDynamicCircleRepertory;
import com.jkb.model.dataSource.myDynamic.circle.local.MyDynamicCircleLocalDataSource;
import com.jkb.model.dataSource.myDynamic.circle.remote.MyDynamicCircleRemoteDataSource;
import com.jkb.model.dataSource.myDynamic.circleSelector.CircleSelectorRepertory;
import com.jkb.model.dataSource.myDynamic.circleSelector.local.CircleSelectorLocalDataSource;
import com.jkb.model.dataSource.myDynamic.circleSelector.remote.CircleSelectorRemoteDataSource;
import com.jkb.model.dataSource.myDynamic.normal.MyDynamicNormalRepertory;
import com.jkb.model.dataSource.myDynamic.normal.local.MyDynamicNormalLocalDataSource;
import com.jkb.model.dataSource.myDynamic.normal.remote.MyDynamicNormalRemoteDataSource;
import com.jkb.model.dataSource.myDynamic.topic.MyDynamicTopicRepertory;
import com.jkb.model.dataSource.myDynamic.topic.local.MyDynamicTopicLocalDataSource;
import com.jkb.model.dataSource.myDynamic.topic.remote.MyDynamicTopicRemoteDataSource;
import com.jkb.model.dataSource.myFavorite.MyFavoriteDynamicRepertory;
import com.jkb.model.dataSource.myFavorite.local.MyFavoriteLocalDataSource;
import com.jkb.model.dataSource.myFavorite.remote.MyFavoriteRemoteDataSource;
import com.jkb.model.dataSource.personCenter.PersonCenterDataResponsitory;
import com.jkb.model.dataSource.personCenter.local.PersonCenterLocalDataSource;
import com.jkb.model.dataSource.personCenter.remote.PersonCenterRemoteDataSource;
import com.jkb.model.dataSource.personSetting.PersonSettingDataResponsitory;
import com.jkb.model.dataSource.personSetting.local.PersonSettingLocalDataSource;
import com.jkb.model.dataSource.personSetting.remote.PersonSettingRemoteDataSource;
import com.jkb.model.dataSource.school.SelectSchoolDataRepertory;
import com.jkb.model.dataSource.school.local.SelectSchoolLocalDataSource;
import com.jkb.model.dataSource.school.remote.SelectSchoolRemoteDataSource;
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
     */
    public static FirstDataResponsitory provideFirstResponsitory(Context context) {
        FirstLocalDataSource firstLocalDataSource = FirstLocalDataSource.getInstance(context);
        FirstDataResponsitory responsitory = FirstDataResponsitory.getInstance(firstLocalDataSource
                , FirstRemoteDataSource.getInstance(context));
        return responsitory;
    }

    /**
     * 得到WelcomeDataResponsitory的实例
     */
    public static WelcomeDataResponsitory provideWelcomeResponsitory(Context context) {
        WelcomeDataResponsitory welcomeDataResponsitory =
                WelcomeDataResponsitory.getInstance(WelcomeLocalDataSource.getInstance(context),
                        WelcomeRemoteDataSource.getInstance());
        return welcomeDataResponsitory;
    }

    /**
     * 得到IdentifyCodeResponsitory类的实例
     */
    public static IdentifyCodeResponsitory provideIdentifyCodeResponsitory() {
        IdentifyCodeResponsitory responsitory = IdentifyCodeResponsitory.getInstance(
                IdentifyCodeLocalDataSource.getInstance(),
                IdentifyCodeRemoteDataSource.getInstance()
        );
        return responsitory;
    }

    /**
     * 得到PersonMessageResponsitory的实例
     */
    public static PersonMessageResponsitory providePersonMessageResponsotory(Context context) {
        PersonMessageResponsitory responsitory = PersonMessageResponsitory.getInstance(
                PersonMessageLocalDataSource.getInstance(context),
                PersonMessageRemoteDataSource.getInstance()
        );
        return responsitory;
    }

    /**
     * 得到LoginResponsitory的对象
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
     * @return CircleIndexDataRepertory
     */
    public static CircleIndexDataRepertory provideCircleIndexDataResponsitiry(
            @NonNull Context applicationContext) {
        CircleIndexDataRepertory responsitiry = CircleIndexDataRepertory.getInstance(
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

    /**
     * 得到DynamicCreateTopicDataRepertory对象
     */
    public static DynamicCreateTopicDataRepertory provideDynamicCreateTopicDataRepertory(
            @NonNull Context applicationContext) {
        DynamicCreateTopicDataRepertory repertory = DynamicCreateTopicDataRepertory.getInstance(
                DynamicCreateTopicLocalDataSource.getInstance(applicationContext),
                DynamicCreateTopicRemoteDataSource.getInstance());
        return repertory;
    }

    /**
     * 得到DynamicCreateNormalDataRepertory对象
     */
    public static DynamicCreateNormalDataRepertory provideDynamicCreateNormalDataRepertory(
            @NonNull Context applicationContext) {
        DynamicCreateNormalDataRepertory repertory = DynamicCreateNormalDataRepertory.getInstance(
                DynamicCreateNormalLocalDataSource.getInstance(applicationContext),
                DynamicCreateNormalRemoteDataSource.getInstance());
        return repertory;
    }

    /**
     * 得到DynamicCreateArticleDataRepertory对象
     */
    public static DynamicCreateArticleDataRepertory provideDynamicCreateArticleDataRepertory(
            @NonNull Context applicationContext) {
        DynamicCreateArticleDataRepertory repertory = DynamicCreateArticleDataRepertory.getInstance(
                DynamicCreateArticleLocalDataSource.getInstance(applicationContext),
                DynamicCreateArticleRemoteDataSource.getInstance());
        return repertory;
    }

    /**
     * 得到SelectSchoolDataRepertory对象
     */
    public static SelectSchoolDataRepertory provideSelectSchoolDataRepertory(
            @NonNull Context applicationContext) {
        SelectSchoolDataRepertory repertory = SelectSchoolDataRepertory.getInstance(
                SelectSchoolLocalDataSource.getInstance(applicationContext),
                SelectSchoolRemoteDataSource.getInstance());
        return repertory;
    }

    /**
     * 得到DynamicHotDataRepository对象
     */
    public static DynamicHotDataRepository provideDynamicHotDataRepository(
            @NonNull Context applicationContext) {
        DynamicHotDataRepository repertory = DynamicHotDataRepository.getInstance(
                DynamicHotLocalDataSource.getInstance(applicationContext),
                DynamicHotRemoteDataSource.getInstance());
        return repertory;
    }

    /**
     * 得到FunctionSettingDataRepertory对象
     */
    public static FunctionSettingDataRepertory provideFunctionSettingDataRepertory(
            @NonNull Context applicationContext) {
        FunctionSettingDataRepertory repertory = FunctionSettingDataRepertory.getInstance(
                FunctionSettingLocalDataSource.getInstance(applicationContext),
                FunctionSettingRemoteDataSource.getInstance());
        return repertory;
    }

    /**
     * 得到MyDynamicNormalRepertory對象
     */
    public static MyDynamicNormalRepertory provideMyDynamicNormalRepertory(
            @NonNull Context applicationContext) {
        MyDynamicNormalRepertory repertory = MyDynamicNormalRepertory.getInstance(
                MyDynamicNormalLocalDataSource.getInstance(applicationContext),
                MyDynamicNormalRemoteDataSource.getInstance()
        );
        return repertory;
    }

    /**
     * 得到MyDynamicArticleRepertory對象
     */
    public static MyDynamicArticleRepertory provideMyDynamicArticleRepertory(
            @NonNull Context applicationContext) {
        MyDynamicArticleRepertory repertory = MyDynamicArticleRepertory.getInstance(
                MyDynamicArticleLocalDataSource.getInstance(applicationContext),
                MyDynamicArticleRemoteDataSource.getInstance()
        );
        return repertory;
    }

    /**
     * 得到MyDynamicTopicRepertory對象
     */
    public static MyDynamicTopicRepertory provideMyDynamicTopicRepertory(
            @NonNull Context applicationContext) {
        MyDynamicTopicRepertory repertory = MyDynamicTopicRepertory.getInstance(
                MyDynamicTopicLocalDataSource.getInstance(applicationContext),
                MyDynamicTopicRemoteDataSource.getInstance()
        );
        return repertory;
    }

    /**
     * 得到MyFavoriteDynamicRepertory對象
     */
    public static MyFavoriteDynamicRepertory provideMyFavoriteDynamicRepertory(
            @NonNull Context applicationContext) {
        MyFavoriteDynamicRepertory repertory = MyFavoriteDynamicRepertory.getInstance(
                MyFavoriteLocalDataSource.getInstance(applicationContext),
                MyFavoriteRemoteDataSource.getInstance()
        );
        return repertory;
    }

    /**
     * 得到MyDynamicCircleRepertory對象
     */
    public static MyDynamicCircleRepertory provideMyDynamicCircleRepertory(
            @NonNull Context applicationContext) {
        MyDynamicCircleRepertory repertory = MyDynamicCircleRepertory.getInstance(
                MyDynamicCircleLocalDataSource.getInstance(applicationContext),
                MyDynamicCircleRemoteDataSource.getInstance()
        );
        return repertory;
    }

    /**
     * 得到CircleSelectorRepertory對象
     */
    public static CircleSelectorRepertory provideCircleSelectorRepertory(
            @NonNull Context applicationContext) {
        CircleSelectorRepertory repertory = CircleSelectorRepertory.newInstance(
                CircleSelectorLocalDataSource.newInstance(applicationContext),
                CircleSelectorRemoteDataSource.newInstance()
        );
        return repertory;
    }

    /**
     * 得到ConversationRepertory對象
     */
    public static ConversationRepertory provideConversationRepertory(
            @NonNull Context applicationContext) {
        ConversationRepertory repertory = ConversationRepertory.newInstance(
                ConversationLocalDataSource.newInstance(applicationContext),
                ConversationRemoteDataSource.newInstance()
        );
        return repertory;
    }

    /**
     * 得到CircleSettingUserDataRepertory對象
     */
    public static CircleSettingUserDataRepertory provideCircleSettingUserDataRepertory(
            @NonNull Context applicationContext) {
        CircleSettingUserDataRepertory repertory = CircleSettingUserDataRepertory.newInstance(
                CircleSettingUserLocalDataSource.newInstance(applicationContext),
                CircleSettingUserRemoteDataSource.newInstance()
        );
        return repertory;
    }

    /**
     * 得到CircleAttentionUserListRepertory對象
     */
    public static CircleAttentionUserListRepertory provideCircleAttentionUserListRepertory(
            @NonNull Context applicationContext) {
        CircleAttentionUserListRepertory repertory = CircleAttentionUserListRepertory.newInstance(
                CircleAttentionUserListLocalDataSource.newInstance(applicationContext),
                CircleAttentionUserListRemoteDataSource.newInstance()
        );
        return repertory;
    }

    /**
     * 得到CircleDynamicInCircleBlackListRepertory對象
     */
    public static CircleDynamicInCircleBlackListRepertory
    provideCircleDynamicInCircleBlackListRepertory(@NonNull Context applicationContext) {
        CircleDynamicInCircleBlackListRepertory repertory =
                CircleDynamicInCircleBlackListRepertory.newInstance(
                        CircleDynamicInCircleBlackListLocalDataSource.newInstance(applicationContext),
                        CircleDynamicInCircleBlackListRemoteDataSource.newInstance()
                );
        return repertory;
    }

    /**
     * 得到MapListRepertory對象
     */
    public static MapListRepertory provideMapListRepertory(@NonNull Context applicationContext) {
        MapListRepertory repertory =
                MapListRepertory.newInstance(
                        MapListLocalDataSource.newInstance(applicationContext),
                        MapListRemoteDataSource.newInstance()
                );
        return repertory;
    }
}
