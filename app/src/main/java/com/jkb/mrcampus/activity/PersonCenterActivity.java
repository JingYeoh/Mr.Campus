package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.personCenter.PersonCenterPresenter;
import com.jkb.core.presenter.personCenter.PersonSettingPresenter;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.personCenter.PersonCenterFragment;
import com.jkb.mrcampus.fragment.personCenter.PersonSettingFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.helper.FragmentStack;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 个人中心的控制器
 * Created by JustKiddingBaby on 2016/8/8.
 */

public class PersonCenterActivity extends BaseActivity {

    //个人中心
    private PersonCenterFragment personCenterFragment;
    private PersonCenterPresenter personCenterPresenter;

    //个人设置
    private PersonSettingFragment personSettingFragment;
    private PersonSettingPresenter personSettingPresenter;

    //保存入栈的Fragment顺序
    private FragmentStack fragmentStack;

    //数据类
    private int user_id = -1;
    private String showCurrentView;
    private static final String SAVED_USER_ID = "saved_userId";
    private static final String SAVED_SHOW_VIEW = "saved_show_view";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_personcenter);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        fragmentStack = new FragmentStack();
        //第一次进入时调用显示首页视图
        if (!savedInstanceStateValued) {
            Intent intent = getIntent();
            user_id = intent.getIntExtra(Config.INTENT_KEY_USER_ID, -1);
            showCurrentView = intent.getStringExtra(Config.INTENT_KEY_SHOW_PERSONALCENTER);
            showDefaultView();
        } else {
            user_id = savedInstanceState.getInt(SAVED_USER_ID);
            showCurrentView = savedInstanceState.getString(SAVED_SHOW_VIEW);
            restoreFragments();
            //恢复保存的栈数据
            fragmentStack.setFragmetStackNames(
                    savedInstanceState.getStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK));
        }
    }

    /**
     * 显示默认的页面
     */
    private void showDefaultView() {
        //默认为个人中心页面
        if (StringUtils.isEmpty(showCurrentView)) {
            showCurrentView = ClassUtils.getClassName(PersonCenterFragment.class);
        }
        Log.d(TAG, "user_id=" + user_id);
        if (user_id == -1) {
            showShortToast("该用户不存在！");
            onBackPressed();
        } else {
            showFragment(showCurrentView);//展示页面
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
            //添加到回退栈中
            fragmentStack.addFragmentToStack(fragmentName);

            if (ClassUtils.isNameEquals(fragmentName, PersonCenterFragment.class)) {
                showPersonCenter();
            } else if (ClassUtils.isNameEquals(fragmentName, PersonSettingFragment.class)) {
                showPersonSetting();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void restoreFragments(String fragmentTAG) {
        Log.d(TAG, "restoreFragments----------->>" + fragmentTAG);
        if (ClassUtils.isNameEquals(fragmentTAG, PersonCenterFragment.class)) {
            personCenterFragment = (PersonCenterFragment) fm.findFragmentByTag(fragmentTAG);
            personCenterPresenter = new PersonCenterPresenter(personCenterFragment,
                    Injection.providePersonCenterDataResponsitory(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, PersonSettingFragment.class)) {
            personSettingFragment = (PersonSettingFragment) fm.findFragmentByTag(fragmentTAG);
            personSettingPresenter = new PersonSettingPresenter(personSettingFragment
                    , Injection.providePersonSettingDataResponsitory(getApplicationContext()));
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();

        if (ClassUtils.isNameEquals(fragmentTAG, PersonCenterFragment.class)) {
            initPersonCenter();
        } else if (ClassUtils.isNameEquals(fragmentTAG, PersonSettingFragment.class)) {
            initPersonSetting();
        }
    }

    /**
     * 初始化个人设置
     */
    private void initPersonSetting() {
        if (personSettingFragment == null) {
            personSettingFragment = PersonSettingFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, personSettingFragment, R.id.personFrame);
        }
        if (personSettingPresenter == null) {
            personSettingPresenter = new PersonSettingPresenter(personSettingFragment
                    , Injection.providePersonSettingDataResponsitory(getApplicationContext()));
        }
    }

    /**
     * 初始化个人中心
     */
    private void initPersonCenter() {
        Log.d(TAG, "initPersonCenter");
        Log.d(TAG, "我要发送过去的用户id是：" + user_id);
        if (personCenterFragment == null) {
            personCenterFragment = PersonCenterFragment.newInstance(user_id);
            ActivityUtils.addFragmentToActivity(fm, personCenterFragment, R.id.personFrame);
        }
        if (personCenterPresenter == null) {
            personCenterPresenter = new PersonCenterPresenter(personCenterFragment
                    , Injection.providePersonCenterDataResponsitory(getApplicationContext()));
        }
    }

    /**
     * 显示个人中心的数据
     */
    private void showPersonCenter() {
        Log.d(TAG, "showPersonCenter");
        ActivityUtils.showFragment(fm, personCenterFragment);
    }

    /**
     * 显示个人设置页面
     */
    private void showPersonSetting() {
        Log.d(TAG, "showPersonSetting");
        ActivityUtils.showFragment(fm, personSettingFragment);
    }

    /**
     * 通知数据改变
     */
    public void notifyDataChanged() {
        Log.d(TAG, "notifyDataChanged");
        if (personCenterPresenter != null) {
            //设置数据过期
            personCenterPresenter.notifyDataChanged();
        }
    }

    /**
     * 返回到上级页面
     */
    public void backToLastView() {
        int sum = fragmentStack.getFragmetStackNames().size();
        Log.d(TAG, "当前保存的栈内数量：" + sum);
        //如果当前页面就是登录页面的时候
        if (ClassUtils.isNameEquals(fragmentStack.getCurrentFragmentName(), PersonCenterFragment.class)) {
            super.onBackPressed();
            return;
        }
        //先销毁使用过后的Fragment
        removeCurrentFragment();

        String fragmentName = fragmentStack.getCurrentFragmentName();
        //如果到最顶层栈或者到了登录页面的时候，再次回退就直接销毁当前页面
        if (fragmentName == null) {
            //返回上级页面
            super.onBackPressed();
        } else {
            //先回退一级栈保存的类信息，避免重复添加
            fragmentStack.removePopFragmentStack();
            //根据类名显示Fragment视图
            showFragment(fragmentName);
        }
    }

    /**
     * 显示用户列表页面
     * 包括：访客、粉丝、关注页面
     *
     * @param user_id 用户id
     * @param action  要打开的页面
     */
    public void startUserListView(@NonNull int user_id, @NonNull String action) {
        Intent intent = new Intent(this, UsersListActivity.class);
        intent.putExtra(Config.INTENT_KEY_USER_ID, user_id);
        intent.putExtra(Config.INTENT_KEY_SHOW_USERSLIST, action);
        startActivityWithPushLeftAnim(intent);
    }

    /**
     * 显示圈子页面
     */
    public void startCircleView(@NonNull int circle_id) {
        Intent intent = new Intent(this, CircleActivity.class);
        intent.putExtra(Config.INTENT_KEY_CIRCLE_ID, circle_id);
        startActivityWithPushLeftAnim(intent);
    }

    /**
     * 销毁使用过后的Fragment
     */
    private void removeCurrentFragment() {
        //销毁的Fragment出栈
        fragmentStack.removePopFragmentStack();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK,
                fragmentStack.getFragmetStackNames());
        outState.putString(SAVED_SHOW_VIEW, showCurrentView);//存储当前页面
        outState.putInt(SAVED_USER_ID, user_id);//存储用户id
    }

    @Override
    public void onBackPressed() {
        backToLastView();
    }

}
