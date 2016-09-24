package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.jkb.core.Injection;
import com.jkb.core.presenter.dynamicDetail.normal.DynamicDetailNormalPresenter;
import com.jkb.core.presenter.dynamicDetail.topic.DynamicDetailTopicPresenter;
import com.jkb.core.presenter.entering.LoginPresenter;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.dynamicDetail.ArticleDynamicFragment;
import com.jkb.mrcampus.fragment.dynamicDetail.NormalDynamicFragment;
import com.jkb.mrcampus.fragment.dynamicDetail.TopicDynamicFragment;
import com.jkb.mrcampus.fragment.entering.EnteringPersonMessageFragment;
import com.jkb.mrcampus.fragment.entering.IdentifyFragment;
import com.jkb.mrcampus.fragment.entering.LoginFragment;
import com.jkb.mrcampus.fragment.entering.MrCampusAgreementFragment;
import com.jkb.mrcampus.fragment.entering.ResetPasswordFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.helper.FragmentStack;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 动态详情页面的页面控制器
 * 包含不同的动态类型
 * 文章、话题、普通
 * Created by JustKiddingBaby on 2016/9/14.
 */

public class DynamicDetailActivity extends BaseActivity {

    //data
    private int dynamic_id = -1;
    private int content_id = R.id.fm_content;//载体的视图id
    private String show_dynamic_type;
    //常量
    private static final String SAVED_DYNAMIC_ID = "saved_dynamic_id";
    private static final String SAVED_DYNAMIC_TYPE = "saved_dynamic_type";

    public static final String SHOW_DYNAMIC_TYPE_TOPIC = "show_dynamic_type_topic";
    public static final String SHOW_DYNAMIC_TYPE_NORMAL = "show_dynamic_type_normal";
    public static final String SHOW_DYNAMIC_TYPE_ARTICLE = "show_dynamic_type_article";

    //保存入栈的Fragment顺序
    private FragmentStack fragmentStack;

    //普通
    private NormalDynamicFragment normalDynamicFragment;
    private DynamicDetailNormalPresenter dynamicDetailNormalPresenter;

    //话题
    private TopicDynamicFragment topicDynamicFragment;
    private DynamicDetailTopicPresenter dynamicDetailTopicPresenter;

    //文章
    private ArticleDynamicFragment articleDynamicFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setRootView(R.layout.aty_dynamic_detail);
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
            dynamic_id = intent.getIntExtra(Config.INTENT_KEY_DYNAMIC_ID, -1);
            show_dynamic_type = intent.getStringExtra(Config.INTENT_KEY_DYNAMIC_TYPE);
        } else {
            restoreFragments();
            dynamic_id = savedInstanceState.getInt(SAVED_DYNAMIC_ID, -1);
            show_dynamic_type = savedInstanceState.getString(SAVED_DYNAMIC_TYPE);
            //恢复保存的栈数据
            fragmentStack.setFragmetStackNames(
                    savedInstanceState.getStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK));
        }

        if (dynamic_id == -1 || StringUtils.isEmpty(show_dynamic_type)) {
            showShortToast("动态不存在");
            super.onBackPressed();
            return;
        }

        switch (show_dynamic_type) {
            case SHOW_DYNAMIC_TYPE_ARTICLE:
                showFragment(ClassUtils.getClassName(ArticleDynamicFragment.class));
                break;
            case SHOW_DYNAMIC_TYPE_NORMAL:
                showFragment(ClassUtils.getClassName(NormalDynamicFragment.class));
                break;
            case SHOW_DYNAMIC_TYPE_TOPIC:
                showFragment(ClassUtils.getClassName(TopicDynamicFragment.class));
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
            fragmentStack.addFragmentToStack(fragmentName);

            if (ClassUtils.isNameEquals(fragmentName, NormalDynamicFragment.class)) {
                showNormalDynamic();
            } else if (ClassUtils.isNameEquals(fragmentName, TopicDynamicFragment.class)) {
                showTopicDynamic();
            } else if (ClassUtils.isNameEquals(fragmentName, ArticleDynamicFragment.class)) {
                showArticleDynamic();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, NormalDynamicFragment.class)) {
            normalDynamicFragment = (NormalDynamicFragment) fm.findFragmentByTag(
                    ClassUtils.getClassName(NormalDynamicFragment.class));
            dynamicDetailNormalPresenter = new DynamicDetailNormalPresenter(normalDynamicFragment,
                    Injection.provideDynamicDetailNormalRepository(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, TopicDynamicFragment.class)) {
            topicDynamicFragment = (TopicDynamicFragment) fm.findFragmentByTag(
                    ClassUtils.getClassName(TopicDynamicFragment.class));
            dynamicDetailTopicPresenter = new DynamicDetailTopicPresenter(topicDynamicFragment,
                    Injection.provideDynamicDetailTopicRepository(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, ArticleDynamicFragment.class)) {
            articleDynamicFragment = (ArticleDynamicFragment) fm.findFragmentByTag(
                    ClassUtils.getClassName(ArticleDynamicFragment.class));
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, NormalDynamicFragment.class)) {
            initNormalDynamic();
        } else if (ClassUtils.isNameEquals(fragmentTAG, TopicDynamicFragment.class)) {
            initTopicDynamic();
        } else if (ClassUtils.isNameEquals(fragmentTAG, ArticleDynamicFragment.class)) {
            initArticleDynamic();
        }
    }

    /**
     * 初始化文章动态
     */
    private void initArticleDynamic() {
        if (articleDynamicFragment == null) {
            articleDynamicFragment = ArticleDynamicFragment.newInstance(dynamic_id);
            ActivityUtils.addFragmentToActivity(fm, articleDynamicFragment, content_id);
        }
    }

    /**
     * 初始化话题动态
     */
    private void initTopicDynamic() {
        if (topicDynamicFragment == null) {
            topicDynamicFragment = TopicDynamicFragment.newInstance(dynamic_id);
            ActivityUtils.addFragmentToActivity(fm, topicDynamicFragment, content_id);
        }
        if (dynamicDetailTopicPresenter == null) {
            dynamicDetailTopicPresenter = new DynamicDetailTopicPresenter(topicDynamicFragment,
                    Injection.provideDynamicDetailTopicRepository(getApplicationContext()));
        }
    }

    /**
     * 初始化普通动态
     */
    private void initNormalDynamic() {
        if (normalDynamicFragment == null) {
            normalDynamicFragment = NormalDynamicFragment.newInstance(dynamic_id);
            ActivityUtils.addFragmentToActivity(fm, normalDynamicFragment, content_id);
        }
        if (dynamicDetailNormalPresenter == null) {
            dynamicDetailNormalPresenter = new DynamicDetailNormalPresenter(normalDynamicFragment,
                    Injection.provideDynamicDetailNormalRepository(getApplicationContext()));
        }
    }

    /**
     * 显示文章动态视图
     */
    private void showArticleDynamic() {
        ActivityUtils.showFragment(fm, articleDynamicFragment);
    }

    /**
     * 显示话题动态视图
     */
    private void showTopicDynamic() {
        ActivityUtils.showFragment(fm, topicDynamicFragment);
    }

    /**
     * 显示普通动态视图
     */
    private void showNormalDynamic() {
        ActivityUtils.showFragment(fm, normalDynamicFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK,
                fragmentStack.getFragmetStackNames());
        outState.putInt(SAVED_DYNAMIC_ID, dynamic_id);
        outState.putString(SAVED_DYNAMIC_TYPE, show_dynamic_type);
    }

    /**
     * 返回到上级页面
     */
    public void backToLastView() {
        if (ClassUtils.isNameEquals(fragmentStack.getCurrentFragmentName(),
                NormalDynamicFragment.class)
                || ClassUtils.isNameEquals(fragmentStack.getCurrentFragmentName(),
                ArticleDynamicFragment.class)
                || ClassUtils.isNameEquals(fragmentStack.getCurrentFragmentName(),
                TopicDynamicFragment.class)) {
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
    public void onBackPressed() {
        backToLastView();
    }
}
