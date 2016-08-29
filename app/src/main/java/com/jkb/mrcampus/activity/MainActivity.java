package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jkb.core.Injection;
import com.jkb.core.contract.menu.MenuContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.core.control.userstate.UserState;
import com.jkb.core.presenter.function.homepage.HomePagePresenter;
import com.jkb.core.presenter.menu.MenuPresenter;
import com.jkb.core.presenter.menu.RightMenuPresenter;
import com.jkb.core.presenter.menu.SwitchFunctionPresenter;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseSlideMenuActivity;
import com.jkb.mrcampus.fragment.function.HomePageFragment;
import com.jkb.mrcampus.fragment.function.SettingFragment;
import com.jkb.mrcampus.fragment.menu.RightMenuFragment;
import com.jkb.mrcampus.fragment.menu.SwitchFunctionFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.service.LocationService;

/**
 * 核心的Activity类，负责显示主要功能模块
 * Created by JustKiddingBaby on 2016/7/23.
 */
public class MainActivity extends BaseSlideMenuActivity implements MenuContract.View {

    private FrameLayout fmContain;
    /**
     * 展示的当前页面
     */
    private MenuPresenter.SHOW_VIEW showView = MenuPresenter.SHOW_VIEW.HOMEPAGE;
    private static final String SAVE_SHOW_VIEW_POSITION = "save_show_view_position";
    //本页的Presenter层
    private MenuPresenter mPresenter;
    //菜单
    private SlidingMenu slidingMenu;

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

    //服务
//    private LocationService locationService;

    //data
    private DynamicLoginStatusChangedListener dynamicLoginStatusChangedListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_main);
        init(savedInstanceState);
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mPresenter.start();
    }

    @Override
    protected void initListener() {
        //设置右滑菜单的监听器
        LoginContext.getInstance().setLoginStatusChangedShowViewListener(slideMenuRightListener);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        //恢复数据
        if (savedInstanceState != null) {
            showView = (MenuPresenter.SHOW_VIEW) savedInstanceState.
                    getSerializable(SAVE_SHOW_VIEW_POSITION);
        }
        fm = getSupportFragmentManager();
        initPresenter();
        initSlideMenu(savedInstanceState);

        startLocatonService();//开启服务

        //第一次进入时调用显示首页视图
        if (!savedInstanceStateValued) {
            showIndex();
        } else {
            restorePresenters();
        }
    }

    /**
     * 开启Service
     */
    private void startLocatonService() {
        System.out.println("提示信息:我在绑定service");
        if (!ActivityUtils.isServiceWorked(LocationService.class.getName(), getApplicationContext())) {
            Intent intent = new Intent(context, LocationService.class);
            startService(intent);
        }
    }


    /**
     * 恢复添加过的Presenter
     *
     * @param fragmentTAG
     */
    @Override
    public void restorePresenter(String fragmentTAG) {
        if (HomePageFragment.class.getName().equals(fragmentTAG)) {
            homePageFragment = (HomePageFragment) fm.findFragmentByTag(fragmentTAG);
            homePagePresenter = new HomePagePresenter(homePageFragment, this);
        } else if (SettingFragment.class.getName().equals(fragmentTAG)) {
            settingFragment = (SettingFragment) fm.findFragmentByTag(fragmentTAG);
        }
    }

    /**
     * 初始化Presenter层数据
     */
    private void initPresenter() {
        mPresenter = new MenuPresenter(this, Injection.provideLoginResponsitory(getApplicationContext()));
        mPresenter.setCurrentView(showView);
    }


    @Override
    protected void initView() {
        fmContain = (FrameLayout) findViewById(R.id.fm_content);

        slideMenuSetting();//设置SlideMenu
    }

    /**
     * 初始化侧滑菜单
     */
    private void initSlideMenu(Bundle savedInstanceState) {
        //设置Fragments
        if (savedInstanceState == null) {
            initMenuFragments();
        } else {
            getMenuFragmentsFromFm();
        }
        //初始化侧滑菜单的Presenter层
        if (switchFunctionPresenter == null) {
            switchFunctionPresenter = new SwitchFunctionPresenter(this, functionFragment);
        }
        if (rightMenuPresenter == null) {
            rightMenuPresenter = new RightMenuPresenter(rightMenuFragment);
        }
    }

    /**
     * 右滑菜单的状态改变的监听器
     */
    UserState.LoginStatusChangedShowViewListener slideMenuRightListener =
            new UserState.LoginStatusChangedShowViewListener() {
                @Override
                public void showLoginView() {
                    slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);//设置为两侧滑动
                    //设置首页的右侧视图
                    if (homePageFragment != null && homePageFragment.isActive()) {
                        homePageFragment.setLoginRightMenuView();
                        if (dynamicLoginStatusChangedListener != null) {
                            dynamicLoginStatusChangedListener.showLoginDynamicView();
                        }
                    }
                }

                @Override
                public void showLogoutView() {
                    slidingMenu.setMode(SlidingMenu.LEFT);//设置为只有左侧滑动
                    if (homePageFragment != null && homePageFragment.isActive()) {
                        homePageFragment.setLogoutRightMenuView();
                        if (dynamicLoginStatusChangedListener != null) {
                            dynamicLoginStatusChangedListener.showLogoutDynamicView();
                        }
                    }
                }
            };

    /**
     * 设置SlideMenu
     */
    private void slideMenuSetting() {
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
     * 从FragmentManager中得到Fragment
     */
    private void getMenuFragmentsFromFm() {
        functionFragment = (SwitchFunctionFragment) fm.findFragmentByTag(SwitchFunctionFragment.class.getName());
        rightMenuFragment = (RightMenuFragment) fm.findFragmentByTag(RightMenuFragment.class.getName());
    }

    /**
     * 初始化菜单Fragment
     */
    private void initMenuFragments() {
        //初始化左滑菜单
        if (functionFragment == null) {
            functionFragment = SwitchFunctionFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, functionFragment, R.id.id_menu_left);
        }
        if (rightMenuFragment == null) {
            //初始化右滑菜单
            rightMenuFragment = RightMenuFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, rightMenuFragment, R.id.id_menu_right);
        }
    }

    @Override
    public void showLeftMenu() {
        Log.d(TAG, "showLeftMenu");
        slidingMenu.showMenu();
    }

    @Override
    public void showRightMenu() {
        Log.d(TAG, "showRightMenu");
        if (LoginContext.getInstance().isLogined()) {
            slidingMenu.showSecondaryMenu();
        } else {
            startLoginActivity();
        }
    }

    @Override
    public void hideMenu() {
        Log.d(TAG, "hideMenu");
        toggle();
    }

    @Override
    public void setMenuOnCloseListener(SlidingMenu.OnCloseListener onClosedListener) {
        slidingMenu.setOnCloseListener(onClosedListener);
    }

    @Override
    public void setMenuOnClosedListener(SlidingMenu.OnClosedListener onClosedListener) {
        slidingMenu.setOnClosedListener(onClosedListener);
    }

    @Override
    public void setMenuOpenListener(SlidingMenu.OnOpenListener openListener) {
        slidingMenu.setOnOpenListener(openListener);
    }

    @Override
    public void setMenuOpenedListener(SlidingMenu.OnOpenedListener openedListener) {
        slidingMenu.setOnOpenedListener(openedListener);
    }

    @Override
    public void showIndex() {
        Log.d(TAG, "showIndex");
        initFragmentStep1(HomePageFragment.class);
        showView = MenuPresenter.SHOW_VIEW.HOMEPAGE;
        ActivityUtils.showFragment(fm, homePageFragment);
    }

    @Override
    public void startMap() {
        Log.d(TAG, "startMap");
        Intent intent = new Intent(this, MapActivity.class);
        startActivityWithPushLeftAnim(intent);
    }

    @Override
    public void showSpecialModel() {
        Log.d(TAG, "showSpecialModel");
    }

    @Override
    public void showTools() {
        Log.d(TAG, "showTools");
    }

    @Override
    public void startPersonalCenter(int user_id) {
        Log.d(TAG, "startPersonalCenter");
        Intent intent = new Intent(this, PersonCenterActivity.class);
        intent.putExtra(Config.INTENT_KEY_USER_ID, user_id);

        startActivityWithPushLeftAnim(intent);
    }

    @Override
    public void startMessage() {
        Log.d(TAG, "startMessage");
        //显示创建圈子视图
        Intent intent = new Intent(this, CreateCircleActivity.class);
        startActivityWithPushLeftAnim(intent);
    }

    @Override
    public void showSetting() {
        Log.d(TAG, "showSetting");
        initFragmentStep1(SettingFragment.class);
        showView = MenuPresenter.SHOW_VIEW.SETTING;
        ActivityUtils.showFragment(fm, settingFragment);
    }


    @Override
    public void startChooseSchools() {
        Log.d(TAG, "startChooseSchools");
        LoginContext.getInstance().setUserState(new LogoutState());
    }

    @Override
    public void startSearchView() {

    }

    @Override
    public void startMyLoveView() {

    }

    @Override
    public void startMyCircleWord() {

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
                SwitchFunctionFragment.class.getName(), RightMenuFragment.class.getName());
    }

    @Override
    public void startUsersListActivity(int user_id, String action) {
        Log.d(TAG, "startUsersListActivity");
        Intent intent = new Intent(this, UsersListActivity.class);
        intent.putExtra(Config.INTENT_KEY_USER_ID, user_id);
        intent.putExtra(Config.INTENT_KEY_SHOW_USERSLIST, action);
        startActivityWithPushLeftAnim(intent);
    }

    @Override
    public void setDynamicLoginStatusChangedListener(DynamicLoginStatusChangedListener listener) {
        this.dynamicLoginStatusChangedListener = listener;
    }

    /**
     * 初始化Fragment步骤2
     */
    @Override
    public void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (HomePageFragment.class.getName().equals(fragmentTAG)) {
            initIndexFragment();
        } else if (SettingFragment.class.getName().equals(fragmentTAG)) {
            initSettingFragment();
        }
    }


    /**
     * 初始化首页的页面数据
     */
    private void initIndexFragment() {
        if (homePageFragment == null) {
            homePageFragment = HomePageFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, homePageFragment, R.id.fm_content);
        }
        if (homePagePresenter == null) {
            homePagePresenter = new HomePagePresenter(homePageFragment, this);
        }
    }

    /**
     * 初始化设置页面的数据
     */
    private void initSettingFragment() {
        if (settingFragment == null) {
            settingFragment = SettingFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, settingFragment, R.id.fm_content);
        }
    }

    @Override
    public void setPresenter(MenuContract.Presenter presenter) {
        mPresenter = (MenuPresenter) presenter;
    }

    @Override
    public void showLoading(String value) {
        super.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVE_SHOW_VIEW_POSITION, showView);
    }

    @Override
    public void onBackPressed() {
        if (slidingMenu.isMenuShowing() || slidingMenu.isSecondaryMenuShowing()) {
            onBackPressed();
        } else {
            exitBy2Click();
        }
    }
}
