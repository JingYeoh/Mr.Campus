package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.personCenter.original.mySubject.MyOriginalSubjectPresenter;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.personCenter.myOriginalSubject.MyOriginalSubjectFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 我的原创的页面控制器
 * Created by JustKiddingBaby on 2016/11/22.
 */

public class MyOriginalSubjectActivity extends BaseActivity {

    //data
    private int contentView = R.id.mySubject_content;
    private int subjectType = -1;

    //常量
    public static final int SUBJECT_TYPE_CONFESSION = 1001;
    public static final int SUBJECT_TYPE_TAUNTED = 1002;
    public static final int SUBJECT_TYPE_LOSTANDFOUND = 1003;
    public static final int SUBJECT_TYPE_FLEAMARKET = 1004;
    public static final int SUBJECT_TYPE_WANTED_SAVANT = 1005;
    public static final int SUBJECT_TYPE_WANTED_PARTNER = 1006;

    //我的原创专题页面
    private MyOriginalSubjectFragment myOriginalSubjectFragment;
    private MyOriginalSubjectPresenter myOriginalSubjectPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_my_original_subject);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            subjectType = intent.getIntExtra(Config.INTENT_KEY_SUBJECT_TYPE, -1);
            showFragment(ClassUtils.getClassName(MyOriginalSubjectFragment.class));
        } else {
            subjectType = savedInstanceState.getInt(Config.BUNDLE_KEY_SUBJECT_TYPE);
            restoreFragments();
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

            if (ClassUtils.isNameEquals(fragmentName, MyOriginalSubjectFragment.class)) {
                showMyOriginalSubject();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, MyOriginalSubjectFragment.class)) {
            myOriginalSubjectFragment = (MyOriginalSubjectFragment)
                    fm.findFragmentByTag(fragmentTAG);
            myOriginalSubjectPresenter = new MyOriginalSubjectPresenter(myOriginalSubjectFragment,
                    Injection.provideMyOriginalSubjectRepertory(getApplicationContext()));
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, MyOriginalSubjectFragment.class)) {
            initMyOriginalSubject();
        }
    }

    /**
     * 初始化我的原创专题
     */
    private void initMyOriginalSubject() {
        if (myOriginalSubjectFragment == null) {
            myOriginalSubjectFragment = MyOriginalSubjectFragment.newInstance(subjectType);
            ActivityUtils.addFragmentToActivity(fm, myOriginalSubjectFragment, contentView);
        }
        myOriginalSubjectPresenter = new MyOriginalSubjectPresenter(myOriginalSubjectFragment,
                Injection.provideMyOriginalSubjectRepertory(getApplicationContext()));
    }

    /**
     * 显示我的原创专题页面
     */
    private void showMyOriginalSubject() {
        ActivityUtils.showFragment(fm, myOriginalSubjectFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.BUNDLE_KEY_SUBJECT_TYPE, subjectType);
    }
}
