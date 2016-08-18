package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.usersList.AttentionPresenter;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.usersList.AttentionFragment;
import com.jkb.mrcampus.fragment.usersList.FansFragment;
import com.jkb.mrcampus.fragment.usersList.VisitorFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 用户列表的控制器
 * 包含三个内容：关注列表、粉丝列表、访客列表
 * 必须传递用户id字段和要打开的页面的className字段进来
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class UsersListActivity extends BaseActivity {

    //关注
    private AttentionFragment attentionFragment;
    private AttentionPresenter attentionPresenter;

    //粉丝
    private FansFragment fansFragment;

    //访客
    private VisitorFragment visitorFragment;

    //数据类
    private String currentShowView = "";//当前显示的View视图
    private int user_id;//用户的id
    private static final String SAVED_CURRENT_VIEW = "saved_current_view";
    private static final String SAVED_USER_ID = "saved_user_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_userslist);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        if (!savedInstanceStateValued) {
            //初始化传递过来的数据
            Intent intent = getIntent();
            currentShowView = intent.getStringExtra(Config.INTENT_KEY_SHOW_USERSLIST);
            user_id = intent.getIntExtra(Config.INTENT_KEY_USER_ID, -1);
            currentShowView = (StringUtils.isEmpty(currentShowView) ?
                    ClassUtils.getClassName(VisitorFragment.class) : currentShowView);
        } else {
            currentShowView = savedInstanceState.getString(SAVED_CURRENT_VIEW);//得到缓存的数据
            user_id = savedInstanceState.getInt(SAVED_USER_ID);//得到缓存的用户id
            restoreFragments();
        }
        if (user_id == -1) {
            showShortToast("没有该用户~");
            onBackPressed();
        } else {
            showFragment(currentShowView);
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    public void showFragment(String fragmentName) {
        Log.d(TAG, "showFragment------->" + fragmentName);
        try {
            Class<?> clzFragment = Class.forName(fragmentName);
            //初始化Fragment
            initFragmentStep1(clzFragment);
            //隐藏掉所有的视图
            ActivityUtils.hideAllFragments(fm);

            if (ClassUtils.isNameEquals(fragmentName, AttentionFragment.class)) {
                showAttention();
            } else if (ClassUtils.isNameEquals(fragmentName, FansFragment.class)) {
                showFans();
            } else if (ClassUtils.isNameEquals(fragmentName, VisitorFragment.class)) {
                showVisitor();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, AttentionFragment.class)) {
            attentionFragment = (AttentionFragment) fm.findFragmentByTag(fragmentTAG);
            attentionPresenter = new AttentionPresenter(attentionFragment,
                    Injection.provideAttentionDataResponsitory(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, FansFragment.class)) {
            fansFragment = (FansFragment) fm.findFragmentByTag(fragmentTAG);
        } else if (ClassUtils.isNameEquals(fragmentTAG, VisitorFragment.class)) {
            visitorFragment = (VisitorFragment) fm.findFragmentByTag(fragmentTAG);
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, AttentionFragment.class)) {
            initAttention();
        } else if (ClassUtils.isNameEquals(fragmentTAG, FansFragment.class)) {
            initFans();
        } else if (ClassUtils.isNameEquals(fragmentTAG, VisitorFragment.class)) {
            initVisitor();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_CURRENT_VIEW, currentShowView);
        outState.putInt(SAVED_USER_ID, user_id);
    }

    /**
     * 初始化Attention
     */
    private void initAttention() {
        if (attentionFragment == null) {
            attentionFragment = AttentionFragment.newInstance(this.user_id);
        }
        if (attentionPresenter == null) {
            attentionPresenter = new AttentionPresenter(attentionFragment,
                    Injection.provideAttentionDataResponsitory(getApplicationContext()));
        }
        ActivityUtils.addFragmentToActivity(fm, attentionFragment, R.id.userListFrame);
    }

    /**
     * 初始化Visitor
     */
    private void initVisitor() {
        if (visitorFragment == null) {
            visitorFragment = VisitorFragment.newInstance();
        }
        ActivityUtils.addFragmentToActivity(fm, visitorFragment, R.id.userListFrame);
    }

    /**
     * 初始化Fans
     */
    private void initFans() {
        if (fansFragment == null) {
            fansFragment = FansFragment.newInstance();
        }
        ActivityUtils.addFragmentToActivity(fm, fansFragment, R.id.userListFrame);
    }

    /**
     * 显示关注的视图
     */
    private void showAttention() {
        Log.d(TAG, "showAttention");
        ActivityUtils.showFragment(fm, attentionFragment);
    }

    /**
     * 显示访客视图
     */
    private void showVisitor() {
        Log.d(TAG, "showVisitor");
        ActivityUtils.showFragment(fm, visitorFragment);
    }

    /**
     * 显示粉丝视图
     */
    private void showFans() {
        Log.d(TAG, "showFans");
        ActivityUtils.showFragment(fm, fansFragment);
    }

}
