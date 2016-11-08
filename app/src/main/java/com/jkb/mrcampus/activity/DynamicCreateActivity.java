package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.dynamicCreate.article.DynamicCreateArticlePresenter;
import com.jkb.core.presenter.dynamicCreate.normal.DynamicCreateNormalPresenter;
import com.jkb.core.presenter.dynamicCreate.topic.DynamicCreateTopicPresenter;
import com.jkb.model.dataSource.dynamicCreate.normal.DynamicCreateNormalDataRepertory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.dynamicCreate.DynamicCreateArticleFragment;
import com.jkb.mrcampus.fragment.dynamicCreate.DynamicCreateNormalFragment;
import com.jkb.mrcampus.fragment.dynamicCreate.DynamicCreateTopicFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 发表动态的页面控制器
 * Created by JustKiddingBaby on 2016/9/26.
 */

public class DynamicCreateActivity extends BaseActivity {

    //data
    private int content_id = R.id.dynamicCreate_content;
    private String dynamicCreateType;//创建动态的类型
    private int circle_id = 0;

    //常量
    public static final String DYNAMIC_CREATE_TYPE_NORMAL = "dynamicCreate.type.normal";
    public static final String DYNAMIC_CREATE_TYPE_TOPIC = "dynamicCreate.type.topic";
    public static final String DYNAMIC_CREATE_TYPE_ARTICLE = "dynamicCreate.type.article";

    //话题
    private DynamicCreateTopicFragment dynamicCreateTopicFragment;
    private DynamicCreateTopicPresenter dynamicCreateTopicPresenter;

    //普通
    private DynamicCreateNormalFragment dynamicCreateNormalFragment;
    private DynamicCreateNormalPresenter dynamicCreateNormalPresenter;

    //文章
    private DynamicCreateArticleFragment dynamicCreateArticleFragment;
    private DynamicCreateArticlePresenter dynamicCreateArticlePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_dynamic_create);
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
            dynamicCreateType = intent.getStringExtra(Config.INTENT_KEY_DYNAMIC_CREATE_TYPE);
            circle_id = intent.getIntExtra(Config.INTENT_KEY_CIRCLE_ID, 0);
            filterDynamicCreateType();//筛选动态创建的类型
        } else {
            dynamicCreateType = savedInstanceState.getString(Config.INTENT_KEY_DYNAMIC_CREATE_TYPE);
            restoreFragments();
        }
        if (StringUtils.isEmpty(dynamicCreateType)) {
            dynamicCreateType = DYNAMIC_CREATE_TYPE_NORMAL;
        }
        showFragment(dynamicCreateType);
    }

    /**
     * 筛选动态创建的类型
     */
    private void filterDynamicCreateType() {
        switch (dynamicCreateType) {
            case DYNAMIC_CREATE_TYPE_NORMAL:
                dynamicCreateType = ClassUtils.getClassName(DynamicCreateNormalFragment.class);
                break;
            case DYNAMIC_CREATE_TYPE_TOPIC:
                dynamicCreateType = ClassUtils.getClassName(DynamicCreateTopicFragment.class);
                break;
            case DYNAMIC_CREATE_TYPE_ARTICLE:
                dynamicCreateType = ClassUtils.getClassName(DynamicCreateArticleFragment.class);
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
            //添加到回退栈中
            if (ClassUtils.isNameEquals(fragmentName, DynamicCreateNormalFragment.class)) {
                showDynamicCreateNormal();
            } else if (ClassUtils.isNameEquals(fragmentName, DynamicCreateTopicFragment.class)) {
                showDynamicCreateTopic();
            } else if (ClassUtils.isNameEquals(fragmentName, DynamicCreateArticleFragment.class)) {
                showDynamicCreateArticle();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, DynamicCreateNormalFragment.class)) {
            dynamicCreateNormalFragment = (DynamicCreateNormalFragment)
                    fm.findFragmentByTag(fragmentTAG);
            dynamicCreateNormalPresenter = new DynamicCreateNormalPresenter(dynamicCreateNormalFragment,
                    Injection.provideDynamicCreateNormalDataRepertory(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, DynamicCreateTopicFragment.class)) {
            dynamicCreateTopicFragment = (DynamicCreateTopicFragment)
                    fm.findFragmentByTag(fragmentTAG);
            dynamicCreateTopicPresenter = new DynamicCreateTopicPresenter(dynamicCreateTopicFragment,
                    Injection.provideDynamicCreateTopicDataRepertory(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, DynamicCreateArticleFragment.class)) {
            dynamicCreateArticleFragment = (DynamicCreateArticleFragment)
                    fm.findFragmentByTag(fragmentTAG);
            dynamicCreateArticlePresenter = new DynamicCreateArticlePresenter(dynamicCreateArticleFragment,
                    Injection.provideDynamicCreateArticleDataRepertory(getApplicationContext()));
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, DynamicCreateNormalFragment.class)) {
            initDynamicCreateNormal();
        } else if (ClassUtils.isNameEquals(fragmentTAG, DynamicCreateTopicFragment.class)) {
            initDynamicCreateTopic();
        } else if (ClassUtils.isNameEquals(fragmentTAG, DynamicCreateArticleFragment.class)) {
            initDynamicCreateArticle();
        }
    }

    /**
     * 初始化动态创建：文章
     */
    private void initDynamicCreateArticle() {
        if (dynamicCreateArticleFragment == null) {
            dynamicCreateArticleFragment = DynamicCreateArticleFragment.newInstance(circle_id);
            ActivityUtils.addFragmentToActivity(fm, dynamicCreateArticleFragment, content_id);
        }
        dynamicCreateArticlePresenter = new DynamicCreateArticlePresenter(dynamicCreateArticleFragment,
                Injection.provideDynamicCreateArticleDataRepertory(getApplicationContext()));
    }

    /**
     * 初始化动态创建：话题
     */
    private void initDynamicCreateTopic() {
        if (dynamicCreateTopicFragment == null) {
            dynamicCreateTopicFragment = DynamicCreateTopicFragment.newInstance(circle_id);
            ActivityUtils.addFragmentToActivity(fm, dynamicCreateTopicFragment, content_id);
        }
        dynamicCreateTopicPresenter = new DynamicCreateTopicPresenter(dynamicCreateTopicFragment,
                Injection.provideDynamicCreateTopicDataRepertory(getApplicationContext()));
    }

    /**
     * 初始化动态创建：普通
     */
    private void initDynamicCreateNormal() {
        if (dynamicCreateNormalFragment == null) {
            dynamicCreateNormalFragment = DynamicCreateNormalFragment.newInstance(circle_id);
            ActivityUtils.addFragmentToActivity(fm, dynamicCreateNormalFragment, content_id);
        }
        dynamicCreateNormalPresenter =
                new DynamicCreateNormalPresenter(dynamicCreateNormalFragment,
                        Injection.provideDynamicCreateNormalDataRepertory(getApplicationContext()));
    }

    /**
     * 展示创建动态：文章类型
     */
    private void showDynamicCreateArticle() {
        ActivityUtils.showFragment(fm, dynamicCreateArticleFragment);
    }

    /**
     * 展示创建动态：话题类型
     */
    private void showDynamicCreateTopic() {
        ActivityUtils.showFragment(fm, dynamicCreateTopicFragment);
    }

    /**
     * 展示创建动态：普通类型
     */
    private void showDynamicCreateNormal() {
        ActivityUtils.showFragment(fm, dynamicCreateNormalFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dynamicCreateArticlePresenter = null;
        dynamicCreateNormalPresenter = null;
        dynamicCreateTopicPresenter = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Config.INTENT_KEY_DYNAMIC_CREATE_TYPE, dynamicCreateType);
        outState.putInt(Config.INTENT_KEY_CIRCLE_ID, circle_id);
    }
}
