package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.function.special.detail.SpecialDetailConfessionFragment;
import com.jkb.mrcampus.fragment.function.special.detail.SpecialDetailFleaMarketFragment;
import com.jkb.mrcampus.fragment.function.special.detail.SpecialDetailLost$FoundFragment;
import com.jkb.mrcampus.fragment.function.special.detail.SpecialDetailTauntedFragment;
import com.jkb.mrcampus.fragment.function.special.detail.SpecialDetailWantedPartnerFragment;
import com.jkb.mrcampus.fragment.function.special.detail.SpecialDetailWantedSavantFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 专题详情页面
 * Created by JustKiddingBaby on 2016/11/20.
 */

public class SpecialDetailActivity extends BaseActivity {

    //data
    private int contentId = R.id.specialDetail_content;
    private int dynamicId = -1;//动态id

    //常量
    public static final int SUBJECT_TYPE_CONFESSION = 1001;
    public static final int SUBJECT_TYPE_TAUNTED = 1002;
    public static final int SUBJECT_TYPE_FLEAMARKET = 1003;
    public static final int SUBJECT_TYPE_LOSTANDFOUND = 1004;
    public static final int SUBJECT_TYPE_WANTED_PARTNER = 1005;
    public static final int SUBJECT_TYPE_WANTED_SAVANT = 1006;

    //表白墙
    private SpecialDetailConfessionFragment specialConfessionFragment;

    //吐槽墙
    private SpecialDetailTauntedFragment specialTauntedFragment;

    //失物招领
    private SpecialDetailLost$FoundFragment specialLost$FoundFragment;

    //跳蚤市场
    private SpecialDetailFleaMarketFragment specialFleaMarketFragment;

    //求学霸
    private SpecialDetailWantedSavantFragment specialWantedSavantFragment;

    //寻伙伴
    private SpecialDetailWantedPartnerFragment specialWantedPartnerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_special_detail);
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
            dynamicId = intent.getIntExtra(Config.INTENT_KEY_DYNAMIC_ID, -1);
            int subjectType = intent.getIntExtra(Config.INTENT_KEY_SUBJECT_TYPE, -1);
            String showTag = filterSpecialDetailType(subjectType);
            if (StringUtils.isEmpty(showTag)) {
                showShortToast("无该类型专题");
                super.onBackPressed();
                return;
            }
            showFragment(showTag);
        } else {
            restoreFragments();
            dynamicId = savedInstanceState.getInt(Config.INTENT_KEY_DYNAMIC_ID, -1);
        }
    }

    @Override
    protected void initView() {

    }

    /**
     * 筛选类型
     */
    private String filterSpecialDetailType(int subjectType) {
        String subjectTag;
        switch (subjectType) {
            case SUBJECT_TYPE_CONFESSION:
                subjectTag = ClassUtils.getClassName(SpecialDetailConfessionFragment.class);
                break;
            case SUBJECT_TYPE_TAUNTED:
                subjectTag = ClassUtils.getClassName(SpecialDetailTauntedFragment.class);
                break;
            case SUBJECT_TYPE_FLEAMARKET:
                subjectTag = ClassUtils.getClassName(SpecialDetailFleaMarketFragment.class);
                break;
            case SUBJECT_TYPE_LOSTANDFOUND:
                subjectTag = ClassUtils.getClassName(SpecialDetailLost$FoundFragment.class);
                break;
            case SUBJECT_TYPE_WANTED_PARTNER:
                subjectTag = ClassUtils.getClassName(SpecialDetailWantedPartnerFragment.class);
                break;
            case SUBJECT_TYPE_WANTED_SAVANT:
                subjectTag = ClassUtils.getClassName(SpecialDetailWantedSavantFragment.class);
                break;
            default:
                subjectTag = null;
                break;
        }
        return subjectTag;
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

            if (ClassUtils.isNameEquals(fragmentName, SpecialDetailConfessionFragment.class)) {
                showConfession();
            } else if (ClassUtils.isNameEquals(fragmentName, SpecialDetailTauntedFragment.class)) {
                showTaunted();
            } else if (ClassUtils.isNameEquals(fragmentName, SpecialDetailLost$FoundFragment.class)) {
                showLostAndFound();
            } else if (ClassUtils.isNameEquals(fragmentName, SpecialDetailFleaMarketFragment.class)) {
                showFleaMarket();
            } else if (ClassUtils.isNameEquals(fragmentName, SpecialDetailWantedPartnerFragment.class)) {
                showWantedPartner();
            } else if (ClassUtils.isNameEquals(fragmentName, SpecialDetailWantedSavantFragment.class)) {
                showWantedSavant();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, SpecialDetailConfessionFragment.class)) {
            specialConfessionFragment = (SpecialDetailConfessionFragment)
                    fm.findFragmentByTag(fragmentTAG);
        } else if (ClassUtils.isNameEquals(fragmentTAG, SpecialDetailTauntedFragment.class)) {
            specialTauntedFragment = (SpecialDetailTauntedFragment)
                    fm.findFragmentByTag(fragmentTAG);
        } else if (ClassUtils.isNameEquals(fragmentTAG, SpecialDetailLost$FoundFragment.class)) {
            specialLost$FoundFragment = (SpecialDetailLost$FoundFragment)
                    fm.findFragmentByTag(fragmentTAG);
        } else if (ClassUtils.isNameEquals(fragmentTAG, SpecialDetailFleaMarketFragment.class)) {
            specialFleaMarketFragment = (SpecialDetailFleaMarketFragment)
                    fm.findFragmentByTag(fragmentTAG);
        } else if (ClassUtils.isNameEquals(fragmentTAG, SpecialDetailWantedPartnerFragment.class)) {
            specialWantedPartnerFragment = (SpecialDetailWantedPartnerFragment)
                    fm.findFragmentByTag(fragmentTAG);
        } else if (ClassUtils.isNameEquals(fragmentTAG, SpecialDetailWantedSavantFragment.class)) {
            specialWantedSavantFragment = (SpecialDetailWantedSavantFragment)
                    fm.findFragmentByTag(fragmentTAG);
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, SpecialDetailConfessionFragment.class)) {
            initConfession();
        } else if (ClassUtils.isNameEquals(fragmentTAG, SpecialDetailTauntedFragment.class)) {
            initTaunted();
        } else if (ClassUtils.isNameEquals(fragmentTAG, SpecialDetailLost$FoundFragment.class)) {
            initLostAndFound();
        } else if (ClassUtils.isNameEquals(fragmentTAG, SpecialDetailFleaMarketFragment.class)) {
            initFleaMarket();
        } else if (ClassUtils.isNameEquals(fragmentTAG, SpecialDetailWantedPartnerFragment.class)) {
            initWantedPartner();
        } else if (ClassUtils.isNameEquals(fragmentTAG, SpecialDetailWantedSavantFragment.class)) {
            initWantedSavant();
        }
    }

    /**
     * 初始化表白墙页面
     */
    private void initConfession() {
        if (specialConfessionFragment == null) {
            specialConfessionFragment = SpecialDetailConfessionFragment.newInstance(dynamicId);
            ActivityUtils.addFragmentToActivity(fm, specialConfessionFragment, contentId);
        }
    }

    /**
     * 初始化吐槽墙页面
     */
    private void initTaunted() {
        if (specialTauntedFragment == null) {
            specialTauntedFragment = SpecialDetailTauntedFragment.newInstance(dynamicId);
            ActivityUtils.addFragmentToActivity(fm, specialTauntedFragment, contentId);
        }
    }

    /**
     * 初始化失物招领页面
     */
    private void initLostAndFound() {
        if (specialLost$FoundFragment == null) {
            specialLost$FoundFragment = SpecialDetailLost$FoundFragment.newInstance(dynamicId);
            ActivityUtils.addFragmentToActivity(fm, specialLost$FoundFragment, contentId);
        }
    }

    /**
     * 初始化跳蚤市场页面
     */
    private void initFleaMarket() {
        if (specialFleaMarketFragment == null) {
            specialFleaMarketFragment = SpecialDetailFleaMarketFragment.newInstance(dynamicId);
            ActivityUtils.addFragmentToActivity(fm, specialFleaMarketFragment, contentId);
        }
    }

    /**
     * 初始化寻伙伴页面
     */
    private void initWantedPartner() {
        if (specialWantedPartnerFragment == null) {
            specialWantedPartnerFragment = SpecialDetailWantedPartnerFragment.newInstance(dynamicId);
            ActivityUtils.addFragmentToActivity(fm, specialWantedPartnerFragment, contentId);
        }
    }

    /**
     * 初始化求学霸页面
     */
    private void initWantedSavant() {
        if (specialWantedSavantFragment == null) {
            specialWantedSavantFragment = SpecialDetailWantedSavantFragment.newInstance(dynamicId);
            ActivityUtils.addFragmentToActivity(fm, specialWantedSavantFragment, contentId);
        }
    }


    /**
     * 显示表白墙页面
     */
    private void showConfession() {
        LogUtils.d(TAG, "showConfession");
        ActivityUtils.showFragment(fm, specialConfessionFragment);
    }

    /**
     * 显示吐槽墙页面
     */
    private void showTaunted() {
        LogUtils.d(TAG, "showTaunted");
        ActivityUtils.showFragment(fm, specialTauntedFragment);
    }

    /**
     * 显示失物招领页面
     */
    private void showLostAndFound() {
        LogUtils.d(TAG, "showLostAndFound");
        ActivityUtils.showFragment(fm, specialLost$FoundFragment);
    }

    /**
     * 显示跳蚤市场页面
     */
    private void showFleaMarket() {
        LogUtils.d(TAG, "showFleaMarket");
        ActivityUtils.showFragment(fm, specialFleaMarketFragment);
    }

    /**
     * 显示寻伙伴页面
     */
    private void showWantedPartner() {
        LogUtils.d(TAG, "showWantedPartner");
        ActivityUtils.showFragment(fm, specialWantedPartnerFragment);
    }

    /**
     * 显示求学霸页面
     */
    private void showWantedSavant() {
        LogUtils.d(TAG, "showWantedSavant");
        ActivityUtils.showFragment(fm, specialWantedSavantFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.INTENT_KEY_DYNAMIC_ID, dynamicId);
    }
}
