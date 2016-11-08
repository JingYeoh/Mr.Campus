package com.jkb.mrcampus.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jkb.core.Injection;
import com.jkb.core.contract.menu.MenuContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.presenter.function.index.HomePagePresenter;
import com.jkb.core.presenter.function.setting.FunctionSettingPresenter;
import com.jkb.core.presenter.menu.MenuPresenter;
import com.jkb.core.presenter.menu.RightMenuPresenter;
import com.jkb.core.presenter.menu.SwitchFunctionPresenter;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.Mr_Campus;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.callback.RongIMConnectCallBack;
import com.jkb.mrcampus.base.BaseSlideMenuActivity;
import com.jkb.mrcampus.fragment.function.index.HomePageFragment;
import com.jkb.mrcampus.fragment.function.setting.SettingFragment;
import com.jkb.mrcampus.fragment.function.special.SubjectFragment;
import com.jkb.mrcampus.fragment.function.tools.ToolsFragment;
import com.jkb.mrcampus.fragment.menu.RightMenuFragment;
import com.jkb.mrcampus.fragment.menu.SwitchFunctionFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.service.LocationService;
import com.jkb.mrcampus.utils.ClassUtils;
import com.jkb.mrcampus.utils.SystemUtils;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;

/**
 * 核心的Activity类，负责显示主要功能模块
 * Created by JustKiddingBaby on 2016/7/23.
 */
public class MainActivity extends BaseSlideMenuActivity implements
        MenuContract.View {

    //data
    private MenuContract.Presenter mPresenter;
    private Bundle jumpBundle;//要跳转的数据
    private SlidingMenu slidingMenu;//侧滑菜单
    private int contentView = R.id.fm_content;
    private String showView;

    //聊天的回调
    private RongIMConnectCallBack rongIMConnectCallBack;

    //左滑菜单
    private SwitchFunctionFragment functionFragment;//左滑菜单
    private SwitchFunctionPresenter switchFunctionPresenter;

    //右滑菜单
    private RightMenuFragment rightMenuFragment;//右滑菜单View视图层
    private RightMenuPresenter rightMenuPresenter;//右滑菜单的P层

    //首页
    private HomePageFragment homePageFragment;
    private HomePagePresenter homePagePresenter;

    //设置
    private SettingFragment settingFragment;
    private FunctionSettingPresenter functionSettingPresenter;

    //小工具
    private ToolsFragment toolsFragment;

    //专题
    private SubjectFragment subjectFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_main);
        init(savedInstanceState);
        mPresenter.start();
    }

    @Override
    protected void initListener() {
        LoginContext.getInstance().addObserver(loginObserver);
        //设置融云聊天的点击事件
        RongIM.setConversationBehaviorListener(behaviorListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        initPresenter();
        if (savedInstanceState == null) {
            jumpBundle = getIntent().getExtras();//得到跳转的数据
            showView = ClassUtils.getClassName(HomePageFragment.class);
        } else {
            showView = savedInstanceState.getString(Config.SAVED_BUNDLE_KEY_SHOW_VIEW);
            restoreFragments();
        }
        initSlideMenu(savedInstanceState);
        startLocationService();//开启定位服务
        //处理跳转页面
        handleJumpView();
        showFragment(showView);
    }

    /**
     * 处理跳转页面
     */
    private void handleJumpView() {
        if (jumpBundle == null) {
            return;
        }
        String jumpAction = jumpBundle.getString(Config.BUNDLE_KEY_JUMP_ACTION);
        if (StringUtils.isEmpty(jumpAction)) {
            return;
        }
        switch (jumpAction) {
            case Config.BUNDLE_JUMP_ACTION_MESSAGE_DYNAMIC:
                startMessageActivity(MessageActivity.MESSAGE_TYPE_DYNAMIC);
                break;
            case Config.BUNDLE_JUMP_ACTION_MESSAGE_FANS:
                startMessageActivity(MessageActivity.MESSAGE_TYPE_FANS);
                break;
            case Config.BUNDLE_JUMP_ACTION_MESSAGE_SUBSCRIBE:
                startMessageActivity(MessageActivity.MESSAGE_TYPE_SUBSCRIBE);
                break;
            case Config.BUNDLE_JUMP_ACTION_MESSAGE_CIRCLE:
                startMessageActivity(MessageActivity.MESSAGE_TYPE_CIRCLE);
                break;
            case Config.BUNDLE_JUMP_ACTION_MESSAGE_SYSTEM:
                startMessageActivity(MessageActivity.MESSAGE_TYPE_SYSTEM);
                break;
            case Config.BUNDLE_JUMP_ACTION_CONVERSATION_PRIVETE://私聊
                jumpToPrivateConversation();
                break;
            default:
                break;
        }
    }

    /**
     * 跳转私聊页面
     */
    private void jumpToPrivateConversation() {
        RongPushClient.ConversationType type = (RongPushClient.ConversationType)
                jumpBundle.getSerializable(Config.INTENT_KEY_CONVERSATION_TYPE);
        String targetId = jumpBundle.getString(Config.INTENT_KEY_TARGETID);
        String targetName = jumpBundle.getString(Config.INTENT_KEY_TARGETNAME);
        SystemUtils.startConversationPrivateActivity(this, type, targetId, targetName);
    }

    @Override
    protected void initView() {
    }

    /**
     * 初始化自身的P层
     */
    private void initPresenter() {
        if (mPresenter == null) {
            mPresenter = new MenuPresenter(this,
                    Injection.provideLoginResponsitory(getApplicationContext()));
        }
    }

    /**
     * 初始化侧滑菜单
     */
    private void initSlideMenu(Bundle savedInstanceState) {
        initSlideMenuSetting();
        if (savedInstanceState == null) {
            initSlideMenu();
        } else {
            restoreSlideMenu();
        }
    }

    /**
     * 初始化侧滑菜单的配置
     */
    private void initSlideMenuSetting() {
        slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        //设置触摸屏幕的模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        // 设置滑动菜单视图的宽度
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.35f);
        // menu.setBehindScrollScale(1.0f);
        slidingMenu.setSecondaryShadowDrawable(R.drawable.shadow);
        //设置左右布局
        setBehindContentView(R.layout.menu_frame_left);
        slidingMenu.setSecondaryMenu(R.layout.menu_frame_right);
    }

    /**
     * 恢复侧滑菜单
     */
    private void restoreSlideMenu() {
        if (functionFragment == null) {
            functionFragment = (SwitchFunctionFragment)
                    fm.findFragmentByTag(ClassUtils.getClassName(SwitchFunctionFragment.class));
            switchFunctionPresenter = new SwitchFunctionPresenter(functionFragment);
        }
        if (rightMenuFragment == null) {
            rightMenuFragment = (RightMenuFragment)
                    fm.findFragmentByTag(ClassUtils.getClassName(RightMenuFragment.class));
            rightMenuPresenter = new RightMenuPresenter(rightMenuFragment,
                    Injection.provideRightMenuDataRepertory(getApplicationContext()));
        }
    }

    /**
     * 初始化侧滑菜单
     */
    private void initSlideMenu() {
        //初始化左滑菜单
        if (functionFragment == null) {
            functionFragment = SwitchFunctionFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, functionFragment, R.id.id_menu_left);
            switchFunctionPresenter = new SwitchFunctionPresenter(functionFragment);
        }
        if (rightMenuFragment == null) {
            //初始化右滑菜单
            rightMenuFragment = RightMenuFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, rightMenuFragment, R.id.id_menu_right);
            rightMenuPresenter = new RightMenuPresenter(rightMenuFragment,
                    Injection.provideRightMenuDataRepertory(getApplicationContext()));
        }
    }

    /**
     * 开启Service
     */
    private void startLocationService() {
        System.out.println("提示信息:我在绑定service");
        if (!ActivityUtils.isServiceWorked(LocationService.class.getName(),
                getApplicationContext())) {
            Intent intent = new Intent(context, LocationService.class);
            startService(intent);
        }
    }

    @Override
    public void showFragment(String fragmentName) {
        Log.d(TAG, "showFragment------->" + fragmentName);
        try {
            Class<?> clzFragment = Class.forName(fragmentName);
            //初始化Fragment
            initFragmentStep1(clzFragment);
            //隐藏掉所有的视图
            hideAllView();
            showView = fragmentName;
            if (ClassUtils.isNameEquals(fragmentName, HomePageFragment.class)) {
                showHomePage();
            } else if (ClassUtils.isNameEquals(fragmentName, SubjectFragment.class)) {
                showSubject();
            } else if (ClassUtils.isNameEquals(fragmentName, ToolsFragment.class)) {
                showTools();
            } else if (ClassUtils.isNameEquals(fragmentName, SettingFragment.class)) {
                showSetting();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, HomePageFragment.class)) {
            homePageFragment = (HomePageFragment) fm.findFragmentByTag(fragmentTAG);
            homePagePresenter = new HomePagePresenter(homePageFragment);
        } else if (ClassUtils.isNameEquals(fragmentTAG, SubjectFragment.class)) {
            subjectFragment = (SubjectFragment) fm.findFragmentByTag(fragmentTAG);
        } else if (ClassUtils.isNameEquals(fragmentTAG, ToolsFragment.class)) {
            toolsFragment = (ToolsFragment) fm.findFragmentByTag(fragmentTAG);
        } else if (ClassUtils.isNameEquals(fragmentTAG, SettingFragment.class)) {
            settingFragment = (SettingFragment) fm.findFragmentByTag(fragmentTAG);
            functionSettingPresenter = new FunctionSettingPresenter(settingFragment,
                    Injection.provideFunctionSettingDataRepertory(getApplicationContext()));
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, HomePageFragment.class)) {
            initHomepage();
        } else if (ClassUtils.isNameEquals(fragmentTAG, SubjectFragment.class)) {
            initSubject();
        } else if (ClassUtils.isNameEquals(fragmentTAG, ToolsFragment.class)) {
            initTools();
        } else if (ClassUtils.isNameEquals(fragmentTAG, SettingFragment.class)) {
            initSetting();
        }
    }

    /**
     * 初始化设置
     */
    private void initSetting() {
        if (settingFragment == null) {
            settingFragment = SettingFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, settingFragment, contentView);
        }
        functionSettingPresenter = new FunctionSettingPresenter(settingFragment,
                Injection.provideFunctionSettingDataRepertory(getApplicationContext()));
    }

    /**
     * 初始化小工具
     */
    private void initTools() {
        if (toolsFragment == null) {
            toolsFragment = ToolsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, toolsFragment, contentView);
        }
    }

    /**
     * 初始化专题
     */
    private void initSubject() {
        if (subjectFragment == null) {
            subjectFragment = SubjectFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, subjectFragment, contentView);
        }
    }

    /**
     * 初始化首页
     */
    private void initHomepage() {
        if (homePageFragment == null) {
            homePageFragment = HomePageFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, homePageFragment, contentView);
        }
        homePagePresenter = new HomePagePresenter(homePageFragment);
    }

    /**
     * 显示设置视图
     */
    private void showSetting() {
        Log.d(TAG, "showSubject");
        ActivityUtils.showFragment(fm, settingFragment);
    }

    /**
     * 显示小工具视图
     */
    private void showTools() {
        Log.d(TAG, "showToolsView");
        ActivityUtils.showFragment(fm, toolsFragment);
    }

    /**
     * 显示专题视图
     */
    private void showSubject() {
        Log.d(TAG, "showSubject");
        ActivityUtils.showFragment(fm, subjectFragment);
    }

    /**
     * 显示首页视图
     */
    private void showHomePage() {
        Log.d(TAG, "showHomePage");
        ActivityUtils.showFragment(fm, homePageFragment);
    }

    @Override
    public void showLeftMenuView() {
        Log.d(TAG, "showLeftMenuView");
        slidingMenu.showMenu();
    }

    @Override
    public void showRightMenuView() {
        Log.d(TAG, "showRightMenuView");
        if (LoginContext.getInstance().isLogined()) {
            slidingMenu.showSecondaryMenu();
        } else {
            startLoginActivity();
        }
    }

    @Override
    public void hideMenuView() {
        Log.d(TAG, "hideMenuView");
        toggle();
    }

    @Override
    public void showIndexView() {
        showFragment(ClassUtils.getClassName(HomePageFragment.class));
    }

    @Override
    public void startMapView() {
        Log.d(TAG, "startMapView");
        Intent intent = new Intent(this, MapActivity.class);
        startActivityWithPushLeftAnim(intent);
    }

    @Override
    public void showSpecialModelView() {
        showFragment(ClassUtils.getClassName(SubjectFragment.class));
    }

    @Override
    public void showToolsView() {
        showFragment(ClassUtils.getClassName(ToolsFragment.class));
    }

    @Override
    public void startMessageView() {
        Log.d(TAG, "startMessageView");
        startMessageCenterActivity();
    }

    @Override
    public void showSettingView() {
        showFragment(ClassUtils.getClassName(SettingFragment.class));
    }

    @Override
    public void startChooseSchoolsView() {
        Log.d(TAG, "startChooseSchoolsView");
        showSelectSchoolView();
    }

    @Override
    public void startLoginActivity() {
        Log.d(TAG, "startLoginActivity");
        Intent intent = new Intent(this, EnteringActivity.class);
        startActivityWithPushLeftAnim(intent);
    }

    @Override
    public void hideAllView() {
        ActivityUtils.hideFragments(fm,
                ClassUtils.getClassName(SwitchFunctionFragment.class),
                ClassUtils.getClassName(RightMenuFragment.class));
    }

    @Override
    public void connectRongIM(String token) {
        if (getApplicationInfo().packageName.
                equals(Mr_Campus.getCurProcessName(getApplicationContext()))) {
            LogUtils.d(TAG, "我要开始连接融云服务了");
            RongIM.connect(token, rongImConnectCallback);
        } else {
            showReqResult("应用包名不同，不能启动聊天服务");
        }
    }

    /**
     * 融云连接的回调
     */
    private RongIMClient.ConnectCallback rongImConnectCallback =
            new RongIMClient.ConnectCallback() {
                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，
                 * 您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    LogUtils.d(TAG, "--onTokenIncorrect");
                    //设置登录失败状态
                    mPresenter.rongIMTokenIncorrect();
                    if (rongIMConnectCallBack != null) {
                        rongIMConnectCallBack.onTokenIncorrect();
                    }
                }

                @Override
                public void onSuccess(String user_id) {
                    LogUtils.d(TAG, "--onSuccess" + user_id);
                    if (rongIMConnectCallBack != null) {
                        rongIMConnectCallBack.onSuccess(Integer.parseInt(user_id));
                    }
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    LogUtils.d(TAG, "--onError" + errorCode.getValue());
                    if (rongIMConnectCallBack != null) {
                        rongIMConnectCallBack.onError(errorCode);
                    }
                }
            };


    @Override
    public void breakConnectRongIM() {
        LogUtils.d(TAG, "我要开始断开聊天了");
        RongIM.getInstance().logout();
    }

    @Override
    public void setJPushAlias(int user_id) {
        LogUtils.d(TAG, "我收到的user_id=" + user_id);
        JPushInterface.setAlias(getApplicationContext(), user_id + "", tagAliasCallback);
    }

    @Override
    public void quitJPush() {
        JPushInterface.setAlias(getApplicationContext(), "", tagAliasCallback);
    }

    /**
     * 设置别名的回调方法
     */
    private TagAliasCallback tagAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int i, String s, Set<String> set) {
            switch (i) {
                case 0:
                    LogUtils.d(TAG, "-----设置的别名成功");
                    break;
                default:
                    LogUtils.w(TAG, "-----设置别名失败，错误Code是" + i);
                    break;
            }
        }
    };

    @Override
    public void setPresenter(MenuContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showReqResult(String value) {
        showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rongImConnectCallback = null;
        LoginContext.getInstance().deleteObserver(loginObserver);
        behaviorListener = null;
        mPresenter = null;
        slidingMenu = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Config.SAVED_BUNDLE_KEY_SHOW_VIEW, showView);
    }

    @Override
    public void onBackPressed() {
        if (slidingMenu.isMenuShowing() || slidingMenu.isSecondaryMenuShowing()) {
            onBackPressed();
        } else {
            exitBy2Click();
        }
    }

    /**
     * 设置聊天连接状态的监听器
     */
    public void setRongIMConnectCallBack(RongIMConnectCallBack rongIMConnectCallBack) {
        this.rongIMConnectCallBack = rongIMConnectCallBack;
    }

    /**
     * 聊天的Observer监听
     */
    private Observer loginObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            boolean logined = LoginContext.getInstance().isLogined();
            if (logined) {
                slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);//设置为两侧滑动
                mPresenter.connectRongIM();
                mPresenter.initJPushAlias();
            } else {
                slidingMenu.setMode(SlidingMenu.LEFT);//设置为只有左侧滑动
                mPresenter.logoutRongIM();
                mPresenter.quitJPush();
            }
        }
    };
    /**
     * 设置用户聊天IM的各个点击事件
     */
    private RongIM.ConversationBehaviorListener behaviorListener =
            new RongIM.ConversationBehaviorListener() {
                @Override
                public boolean onUserPortraitClick(
                        Context context, Conversation.ConversationType conversationType,
                        UserInfo userInfo) {
                    String userId = userInfo.getUserId();
                    int user_id = Integer.parseInt(userId);
                    startPersonalCenterActivity(user_id);
                    return true;
                }

                @Override
                public boolean onUserPortraitLongClick(
                        Context context, Conversation.ConversationType conversationType,
                        UserInfo userInfo) {
                    return false;
                }

                @Override
                public boolean onMessageClick(Context context, View view, Message message) {
                    return false;
                }

                @Override
                public boolean onMessageLinkClick(Context context, String s) {
                    return false;
                }

                @Override
                public boolean onMessageLongClick(Context context, View view, Message message) {
                    return false;
                }
            };
}
