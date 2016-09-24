package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.comment.list.CommentListPresenter;
import com.jkb.core.presenter.comment.singleAll.CommentSingleAllPresenter;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.comment.CommentListFragment;
import com.jkb.mrcampus.fragment.comment.CommentSingleAllFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.helper.FragmentStack;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 评论的控制器
 * Created by JustKiddingBaby on 2016/9/20.
 */

public class CommentActivity extends BaseActivity {

    //data
    private int target_id = -1;
    private int contentViewId = R.id.commentFrame;
    private String comment_showType;
    //常量
    public static final String ACTION_SHOW_VIEW_COMMENT_LIST = "action.show.view.comment.list";
    public static final String ACTION_SHOW_VIEW_COMMENT_SINGLE_ALL = "action.show.view.comment.single.all";
    public static final String SAVED_DYNAMIC_ID = "saved_dynamic_id";
    public static final String SAVED_COMMENT_ID = "saved_comment_id";
    public static final String SAVED_TARGET_ID = "saved_comment_id";
    public static final String SAVED_COMMENT_SHOW_TYPE = "saved_comment_showType";

    //data
    //保存入栈的Fragment顺序
    private FragmentStack fragmentStack;

    //评论列表
    private int dynamic_id = -1;
    private CommentListFragment commentListFragment;
    private CommentListPresenter commentListPresenter;

    //单挑全部评论
    private int comment_id = -1;//评论的id
    private CommentSingleAllFragment commentSingleAllFragment;
    private CommentSingleAllPresenter commentSingleAllPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_comment);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fragmentStack = new FragmentStack();
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            target_id = intent.getIntExtra(Config.INTENT_KEY_TARGET_ID, -1);
            dynamic_id = intent.getIntExtra(Config.INTENT_KEY_DYNAMIC_ID, -1);
            String showType = intent.getStringExtra(Config.INTENT_KEY_SHOW_COMMENT);
            comment_showType=showType;
        } else {
            restoreFragments();
            target_id=savedInstanceState.getInt(SAVED_TARGET_ID,-1);
            dynamic_id = savedInstanceState.getInt(SAVED_DYNAMIC_ID, -1);
            comment_id = savedInstanceState.getInt(SAVED_COMMENT_ID, -1);
            comment_showType = savedInstanceState.getString(SAVED_COMMENT_SHOW_TYPE);
            //恢复保存的栈数据
            fragmentStack.setFragmetStackNames(
                    savedInstanceState.getStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK));
        }
        //设置判断页面数据
        switch (comment_showType) {
            case ACTION_SHOW_VIEW_COMMENT_LIST:
                dynamic_id = target_id;
                comment_showType = ClassUtils.getClassName(CommentListFragment.class);
                break;
            case ACTION_SHOW_VIEW_COMMENT_SINGLE_ALL:
                comment_id = target_id;
                comment_showType = ClassUtils.getClassName(CommentSingleAllFragment.class);
                break;
        }
        if (dynamic_id <= 0) {
            showShortToast("该动态不存在");
            super.onBackPressed();
            return;
        }
        //显示视图
        showFragment(comment_showType);
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

            if (ClassUtils.isNameEquals(fragmentName, CommentListFragment.class)) {
                showCommentList();
            } else if (ClassUtils.isNameEquals(fragmentName, CommentSingleAllFragment.class)) {
                showCommentSingleAll();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, CommentListFragment.class)) {
            commentListFragment = (CommentListFragment) fm.findFragmentByTag(
                    ClassUtils.getClassName(CommentListFragment.class));
            commentListPresenter = new CommentListPresenter(commentListFragment,
                    Injection.provideCommentListRepository(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, CommentSingleAllFragment.class)) {
            commentSingleAllFragment = (CommentSingleAllFragment) fm.findFragmentByTag(
                    ClassUtils.getClassName(CommentSingleAllFragment.class));
            commentSingleAllPresenter = new CommentSingleAllPresenter(commentSingleAllFragment,
                    Injection.provideCommentSingleAllRepository(getApplicationContext()));
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, CommentListFragment.class)) {
            initCommentList();
        } else if (ClassUtils.isNameEquals(fragmentTAG, CommentSingleAllFragment.class)) {
            initCommentSingleAll();
        }
    }

    /**
     * 初始化单条全部评论
     */
    private void initCommentSingleAll() {
        if (commentSingleAllFragment == null) {
            commentSingleAllFragment = CommentSingleAllFragment.newInstance(dynamic_id, comment_id);
            ActivityUtils.addFragmentToActivity(fm, commentSingleAllFragment, contentViewId);
        }
        if (commentSingleAllPresenter == null) {
            commentSingleAllPresenter = new CommentSingleAllPresenter(commentSingleAllFragment,
                    Injection.provideCommentSingleAllRepository(getApplicationContext()));
        }
    }

    /**
     * 初始化评论列表
     */
    private void initCommentList() {
        if (commentListFragment == null) {
            commentListFragment = CommentListFragment.newInstance(dynamic_id);
            ActivityUtils.addFragmentToActivity(fm, commentListFragment, contentViewId);
        }
        if (commentListPresenter == null) {
            commentListPresenter = new CommentListPresenter(commentListFragment,
                    Injection.provideCommentListRepository(getApplicationContext()));
        }
    }

    /**
     * 显示单条全部评论
     */
    private void showCommentSingleAll() {
        comment_showType = ClassUtils.getClassName(CommentSingleAllFragment.class);
        ActivityUtils.showFragment(fm, commentSingleAllFragment);
    }

    /**
     * 显示评论列表
     */
    private void showCommentList() {
        comment_showType = ClassUtils.getClassName(CommentListFragment.class);
        ActivityUtils.showFragment(fm, commentListFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK,
                fragmentStack.getFragmetStackNames());
        outState.putInt(SAVED_DYNAMIC_ID, dynamic_id);
        outState.putInt(SAVED_TARGET_ID,target_id);
        outState.putInt(SAVED_COMMENT_ID, comment_id);
        outState.putString(SAVED_COMMENT_SHOW_TYPE, comment_showType);
    }
}
