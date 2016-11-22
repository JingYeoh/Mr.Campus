package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.function.special.create.SpecialCreatePresenter;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.function.special.create.SpecialCreateFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 创建专题的页面控制器
 * Created by JustKiddingBaby on 2016/11/18.
 */

public class SpecialCreateActivity extends BaseActivity {

    //data
    private int contentView = R.id.specialCreateContent;

    //常量
    public static final int SUBJECT_CREATE_TYPE_CONFESSION = 1001;
    public static final int SUBJECT_CREATE_TYPE_TAUNTED = 1002;
    public static final int SUBJECT_CREATE_TYPE_LOSTANDFOUND = 1003;
    public static final int SUBJECT_CREATE_TYPE_FLEAMARKET = 1004;
    public static final int SUBJECT_CREATE_TYPE_WANTED_PARTNER = 1005;
    public static final int SUBJECT_CREATE_TYPE_WANTED_SAVANT = 1006;

    //发表专题
    private int subjectCreateType = -1;
    private SpecialCreateFragment specialCreateFragment;
    private SpecialCreatePresenter specialCreatePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_special_create);
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
            subjectCreateType = intent.getIntExtra(Config.INTENT_KEY_SUBJECT_CREATE_TYPE, -1);
            filterSubjectCreateType();
        } else {
            subjectCreateType = savedInstanceState.getInt(Config.BUNDLE_KEY_SUBJECT_CREATE_TYPE);
            restoreFragments();
        }
    }

    /**
     * 筛选专题创建类型
     */
    private void filterSubjectCreateType() {
        switch (subjectCreateType) {
            case SUBJECT_CREATE_TYPE_CONFESSION:
            case SUBJECT_CREATE_TYPE_TAUNTED:
            case SUBJECT_CREATE_TYPE_LOSTANDFOUND:
            case SUBJECT_CREATE_TYPE_FLEAMARKET:
            case SUBJECT_CREATE_TYPE_WANTED_PARTNER:
            case SUBJECT_CREATE_TYPE_WANTED_SAVANT:
                showFragment(ClassUtils.getClassName(SpecialCreateFragment.class));
                break;
            default:
                showShortToast("无该专题类型");
                super.onBackPressed();
                break;
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

            if (ClassUtils.isNameEquals(fragmentName, SpecialCreateFragment.class)) {
                showSubjectCreate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, SpecialCreateFragment.class)) {
            specialCreateFragment = (SpecialCreateFragment) fm.findFragmentByTag(fragmentTAG);
            specialCreatePresenter = new SpecialCreatePresenter(specialCreateFragment,
                    Injection.provideSpecialCreateRepertory(getApplicationContext()));
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();

        if (ClassUtils.isNameEquals(fragmentTAG, SpecialCreateFragment.class)) {
            initSpecialCreate();
        }
    }

    /**
     * 初始化专题创建页面
     */
    private void initSpecialCreate() {
        if (specialCreateFragment == null) {
            specialCreateFragment = SpecialCreateFragment.newInstance(subjectCreateType);
            ActivityUtils.addFragmentToActivity(fm, specialCreateFragment, contentView);
        }
        specialCreatePresenter = new SpecialCreatePresenter(specialCreateFragment,
                Injection.provideSpecialCreateRepertory(getApplicationContext()));
    }

    /**
     * 显示创建专题页面
     */
    private void showSubjectCreate() {
        ActivityUtils.showFragment(fm, specialCreateFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.BUNDLE_KEY_SUBJECT_CREATE_TYPE, subjectCreateType);
    }
}
