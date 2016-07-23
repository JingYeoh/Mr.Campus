package com.jkb.mrcampus.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.jkb.core.contract.first.FirstContract;
import com.jkb.core.presenter.first.FirstPresenter;
import com.jkb.core.presenter.first.WelcomePresenter;
import com.jkb.model.first.firstlogic.FirstDataResponsitory;
import com.jkb.model.first.firstlogic.local.FirstLocalDataSource;
import com.jkb.model.first.welcome.WelcomeDataResponsitory;
import com.jkb.model.first.welcome.local.WelcomeLocalDataSource;
import com.jkb.model.first.welcome.remote.WelcomeRemoteDataSource;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.first.WelcomeFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 进入APP的第一个页面控制器
 * 包含三个页面：GuideFragment、WelcomeFragment、AdventFragment
 * GuideFragment：引导页（第一次进入APP或者版本更新的引导页面）
 * WelcomeFragment：欢迎页面，初始化APP页面数据时用到
 * AdventFragment：广告页面，用于显示推送的广告
 * Created by JustKiddingBaby on 2016/7/21.
 */
public class FirstActivity extends BaseActivity implements FirstContract.View {

    //显示的View
    private FrameLayout containView;
    /**
     * savedInstanceState是否有数据
     * 判断是否发生了内存重启后还是有保存数据
     */
    private boolean savedInstanceStateValued = false;

    //有关WelcomeFragment页面的数据
    private WelcomePresenter welcomePresenter;
    private WelcomeDataResponsitory welcomeDataResponsitory;
    private WelcomeFragment welcomeFragment;

    //有关First本页面的逻辑
    private FirstContract.Presenter mPresenter;
    private FirstPresenter firstPresenter;
    private FirstDataResponsitory firstDataResponsitory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_first);
        init(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            savedInstanceStateValued = false;
        } else {
            savedInstanceStateValued = true;
        }

        fm = getSupportFragmentManager();
        initFirstData(savedInstanceState);//初始化本页面的逻辑层数据
    }

    /**
     * 初始化页面本身的数据
     *
     * @param savedInstanceState
     */
    private void initFirstData(Bundle savedInstanceState) {
        FirstLocalDataSource firstLocalDataSource = FirstLocalDataSource.getInstance(context);
        firstDataResponsitory = FirstDataResponsitory.getInstance(firstLocalDataSource);

        firstPresenter = new FirstPresenter(firstDataResponsitory, this);
    }

    @Override
    protected void initView() {
        containView = (FrameLayout) findViewById(R.id.firstFrame);
    }

    @Override
    public void showFragment(int position) {
        switch (position) {
            case 0:
                showWelcomeFragment();
                break;
            case 1:
                showGuideFragment();
                break;
            case 2:
                showAdventFragment();
                break;
        }
    }

    /**
     * 显示AdventFragment
     */
    private void showAdventFragment() {
        Log.d(TAG, "showAdventFragment");
    }

    /**
     * 显示GuideFragment
     */
    private void showGuideFragment() {
        Log.d(TAG, "showGuideFragment");
    }

    /**
     * 显示WelcomeFragment
     */
    private void showWelcomeFragment() {
        Log.d(TAG, "showWelcomeFragment");

        //设置Fragment的值
        if (savedInstanceStateValued) {
            //此时发生内存重启后此处有数据
            welcomeFragment = (WelcomeFragment) fm.findFragmentByTag(WelcomeFragment.class.getSimpleName());
        } else {
            //第一次进入
            welcomeFragment = new WelcomeFragment();
            ft = fm.beginTransaction();
            ft.add(R.id.firstFrame, welcomeFragment, WelcomeFragment.class.getSimpleName());
            ft.commit();
        }

        //初始化要用到的Presenter层数据
        welcomeDataResponsitory = WelcomeDataResponsitory.getInstance(WelcomeLocalDataSource.getInstance(context),
                WelcomeRemoteDataSource.getInstance());
        welcomePresenter = new WelcomePresenter(welcomeDataResponsitory, welcomeFragment);
    }

    @Override
    public void setPresenter(FirstContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
