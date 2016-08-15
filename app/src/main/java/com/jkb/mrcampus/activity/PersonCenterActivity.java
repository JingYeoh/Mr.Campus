package com.jkb.mrcampus.activity;

import android.os.Bundle;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.entering.LoginPresenter;
import com.jkb.core.presenter.personCenter.PersonCenterPresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.entering.EnteringPersonMessageFragment;
import com.jkb.mrcampus.fragment.entering.IdentifyFragment;
import com.jkb.mrcampus.fragment.entering.LoginFragment;
import com.jkb.mrcampus.fragment.entering.MrCampusAgreementFragment;
import com.jkb.mrcampus.fragment.entering.ResetPasswordFragment;
import com.jkb.mrcampus.fragment.personCenter.PersonCenterFragment;
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

    //保存入栈的Fragment顺序
    private FragmentStack fragmentStack;

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
            showFragment(ClassUtils.getClassName(PersonCenterFragment.class));
        } else {
            restoreFragments();
            //恢复保存的栈数据
            fragmentStack.setFragmetStackNames(
                    savedInstanceState.getStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK));
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
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, PersonCenterFragment.class)) {
            personCenterFragment = (PersonCenterFragment) fm.findFragmentByTag(fragmentTAG);
            personCenterPresenter = new PersonCenterPresenter(personCenterFragment);
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();

        if (ClassUtils.isNameEquals(fragmentTAG, PersonCenterFragment.class)) {
            initPersonCenter();
        }
    }

    /**
     * 初始化个人中心
     */
    private void initPersonCenter() {
        if (personCenterFragment == null) {
            personCenterFragment = PersonCenterFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, personCenterFragment, R.id.personFrame);
        }
        if (personCenterPresenter == null) {
            personCenterPresenter = new PersonCenterPresenter(personCenterFragment);
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
     * 返回到上级页面
     */
    public void backToLastView() {
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
     * 销毁使用过后的Fragment
     */
    private void removeCurrentFragment() {
        //销毁的Fragment出栈
        fragmentStack.removePopFragmentStack();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK, fragmentStack.getFragmetStackNames());
    }

    @Override
    public void onBackPressed() {
        backToLastView();
    }
}
