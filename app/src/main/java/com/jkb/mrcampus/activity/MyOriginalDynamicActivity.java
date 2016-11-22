package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.personCenter.original.myDynamic.MyDynamicArticlePresenter;
import com.jkb.core.presenter.personCenter.original.myDynamic.circle.MyDynamicCirclePresenter;
import com.jkb.core.presenter.personCenter.original.myDynamic.MyDynamicNormalPresenter;
import com.jkb.core.presenter.personCenter.original.myDynamic.MyDynamicTopicPresenter;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.personCenter.myOriginalDynamic.MyDynamicArticleFragment;
import com.jkb.mrcampus.fragment.personCenter.myOriginalDynamic.MyDynamicNormalFragment;
import com.jkb.mrcampus.fragment.personCenter.myOriginalDynamic.MyDynamicTopicFragment;
import com.jkb.mrcampus.fragment.personCenter.myOriginalDynamic.circle.MyDynamicCircleFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 我的圈子页面控制器
 * 传递参数：用户id、页面类型
 * Created by JustKiddingBaby on 2016/10/13.
 */

public class MyOriginalDynamicActivity extends BaseActivity {

    private int contentView = R.id.myDynamic_content;
    private String showView = null;
    private int user_id = -1;//用户id

    private static final String SAVED_SHOW_VIEW = "saved_show_view";

    //常量
    public static final int MY_DYNAMIC_TYPE_ARTICLE = 1001;
    public static final int MY_DYNAMIC_TYPE_NORMAL = 1002;
    public static final int MY_DYNAMIC_TYPE_TOPIC = 1003;
    public static final int MY_DYNAMIC_TYPE_CIRCLE = 1010;

    //文章
    private MyDynamicArticleFragment myDynamicArticleFragment;
    private MyDynamicArticlePresenter myDynamicArticlePresenter;

    //话题
    private MyDynamicTopicFragment myDynamicTopicFragment;
    private MyDynamicTopicPresenter myDynamicTopicPresenter;

    //普通
    private MyDynamicNormalFragment myDynamicNormalFragment;
    private MyDynamicNormalPresenter myDynamicNormalPresenter;

    //圈子
    private MyDynamicCircleFragment myDynamicCircleFragment;
    private MyDynamicCirclePresenter myDynamicCirclePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_my_dynamic);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        int dynamicType;
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            dynamicType = intent.getIntExtra(Config.INTENT_KEY_DYNAMIC_TYPE, -1);
            user_id = intent.getIntExtra(Config.INTENT_KEY_USER_ID, -1);
            filterMyDynamic(dynamicType);
        } else {
            showView = savedInstanceState.getString(SAVED_SHOW_VIEW);
            user_id = savedInstanceState.getInt(Config.INTENT_KEY_USER_ID);
            restoreFragments();
        }
        if (StringUtils.isEmpty(showView)) {
            showShortToast("页面不存在");
            super.onBackPressed();
            return;
        }
        showFragment(showView);
    }

    /**
     * 筛选我的动态类型
     *
     * @param dynamicType 动态类型
     */
    private void filterMyDynamic(int dynamicType) {
        switch (dynamicType) {
            case MY_DYNAMIC_TYPE_ARTICLE:
                showView = ClassUtils.getClassName(MyDynamicArticleFragment.class);
                break;
            case MY_DYNAMIC_TYPE_NORMAL:
                showView = ClassUtils.getClassName(MyDynamicNormalFragment.class);
                break;
            case MY_DYNAMIC_TYPE_TOPIC:
                showView = ClassUtils.getClassName(MyDynamicTopicFragment.class);
                break;
            case MY_DYNAMIC_TYPE_CIRCLE:
                showView = ClassUtils.getClassName(MyDynamicCircleFragment.class);
                break;
            default:
                showView = null;
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

            if (ClassUtils.isNameEquals(fragmentName, MyDynamicArticleFragment.class)) {
                showMyDynamicArticle();
            } else if (ClassUtils.isNameEquals(fragmentName, MyDynamicNormalFragment.class)) {
                showMyDynamicNormal();
            } else if (ClassUtils.isNameEquals(fragmentName, MyDynamicTopicFragment.class)) {
                showMyDynamicTopic();
            } else if (ClassUtils.isNameEquals(fragmentName, MyDynamicCircleFragment.class)) {
                showMyDynamicCircle();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, MyDynamicArticleFragment.class)) {
            myDynamicArticleFragment = (MyDynamicArticleFragment) fm.findFragmentByTag(fragmentTAG);
            myDynamicArticlePresenter = new MyDynamicArticlePresenter(myDynamicArticleFragment,
                    Injection.provideMyDynamicArticleRepertory(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, MyDynamicNormalFragment.class)) {
            myDynamicNormalFragment = (MyDynamicNormalFragment) fm.findFragmentByTag(fragmentTAG);
            myDynamicNormalPresenter = new MyDynamicNormalPresenter(myDynamicNormalFragment,
                    Injection.provideMyDynamicNormalRepertory(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, MyDynamicTopicFragment.class)) {
            myDynamicTopicFragment = (MyDynamicTopicFragment) fm.findFragmentByTag(fragmentTAG);
            myDynamicTopicPresenter = new MyDynamicTopicPresenter(myDynamicTopicFragment,
                    Injection.provideMyDynamicTopicRepertory(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, MyDynamicCircleFragment.class)) {
            myDynamicCircleFragment = (MyDynamicCircleFragment) fm.findFragmentByTag(fragmentTAG);
            myDynamicCirclePresenter = new MyDynamicCirclePresenter(myDynamicCircleFragment,
                    Injection.provideMyDynamicCircleRepertory(getApplicationContext()));
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();

        if (ClassUtils.isNameEquals(fragmentTAG, MyDynamicArticleFragment.class)) {
            initMyDynamicArticle();
        } else if (ClassUtils.isNameEquals(fragmentTAG, MyDynamicTopicFragment.class)) {
            initMyDynamicTopic();
        } else if (ClassUtils.isNameEquals(fragmentTAG, MyDynamicNormalFragment.class)) {
            initMyDynamicNormal();
        } else if (ClassUtils.isNameEquals(fragmentTAG, MyDynamicCircleFragment.class)) {
            initMyDynamicCircle();
        }
    }

    /**
     * 初始化我的动态：圈子
     */
    private void initMyDynamicCircle() {
        if (myDynamicCircleFragment == null) {
            myDynamicCircleFragment = MyDynamicCircleFragment.newInstance(user_id);
            ActivityUtils.addFragmentToActivity(fm, myDynamicCircleFragment, contentView);
        }
        if (myDynamicCirclePresenter == null) {
            myDynamicCirclePresenter = new MyDynamicCirclePresenter(myDynamicCircleFragment,
                    Injection.provideMyDynamicCircleRepertory(getApplicationContext()));
        }
    }

    /**
     * 初始化我的动态：普通
     */
    private void initMyDynamicNormal() {
        if (myDynamicNormalFragment == null) {
            myDynamicNormalFragment = MyDynamicNormalFragment.newInstance(user_id);
            ActivityUtils.addFragmentToActivity(fm, myDynamicNormalFragment, contentView);
        }
        if (myDynamicNormalPresenter == null) {
            myDynamicNormalPresenter = new MyDynamicNormalPresenter(myDynamicNormalFragment,
                    Injection.provideMyDynamicNormalRepertory(getApplicationContext()));
        }
    }

    /**
     * 初始化我的动态：话题
     */
    private void initMyDynamicTopic() {
        if (myDynamicTopicFragment == null) {
            myDynamicTopicFragment = MyDynamicTopicFragment.newInstance(user_id);
            ActivityUtils.addFragmentToActivity(fm, myDynamicTopicFragment, contentView);
        }
        if (myDynamicTopicPresenter == null) {
            myDynamicTopicPresenter = new MyDynamicTopicPresenter(myDynamicTopicFragment,
                    Injection.provideMyDynamicTopicRepertory(getApplicationContext()));
        }
    }

    /**
     * 初始化我的动态：文章
     */
    private void initMyDynamicArticle() {
        if (myDynamicArticleFragment == null) {
            myDynamicArticleFragment = MyDynamicArticleFragment.newInstance(user_id);
            ActivityUtils.addFragmentToActivity(fm, myDynamicArticleFragment, contentView);
        }
        if (myDynamicArticlePresenter == null) {
            myDynamicArticlePresenter = new MyDynamicArticlePresenter(myDynamicArticleFragment,
                    Injection.provideMyDynamicArticleRepertory(getApplicationContext()));
        }
    }

    /**
     * 显示圈子
     */
    private void showMyDynamicCircle() {
        ActivityUtils.showFragment(fm, myDynamicCircleFragment);
    }

    /**
     * 显示话题
     */
    private void showMyDynamicTopic() {
        ActivityUtils.showFragment(fm, myDynamicTopicFragment);
    }

    /**
     * 显示普通
     */
    private void showMyDynamicNormal() {
        ActivityUtils.showFragment(fm, myDynamicNormalFragment);
    }

    /**
     * 显示文章
     */
    private void showMyDynamicArticle() {
        ActivityUtils.showFragment(fm, myDynamicArticleFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_SHOW_VIEW, showView);
        outState.putInt(Config.INTENT_KEY_USER_ID, user_id);
    }
}
