package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.myFavorite.MyFavoriteDynamicPresenter;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.personCenter.unOriginalDynamic.MyFavoriteDynamicFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 我的非原创的页面控制器
 * Created by JustKiddingBaby on 2016/10/16.
 */

public class MyUnOriginalDynamicActivity extends BaseActivity {


    //data
    private int user_id = -1;
    private int contentView = R.id.myUnOriginalDynamic_content;
    private String showView;

    //常量
    private static final String SAVED_SHOW_VIEW = "saved_show_view";
    public static final int TYPE_MY_FAVORITE = 1001;

    //我的喜欢
    private MyFavoriteDynamicFragment myFavoriteDynamicFragment;
    private MyFavoriteDynamicPresenter myFavoriteDynamicPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_my_unoriginal_dynamic);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            int dynamicType = intent.getIntExtra(Config.INTENT_KEY_DYNAMIC_TYPE, -1);
            user_id = intent.getIntExtra(Config.INTENT_KEY_USER_ID, -1);
            filterDynamicType(dynamicType);
        } else {
            user_id = savedInstanceState.getInt(Config.INTENT_KEY_USER_ID);
            showView = savedInstanceState.getString(SAVED_SHOW_VIEW);
            restoreFragments();
        }
        if (user_id <= 0) {
            showShortToast("用户不存在");
            super.onBackPressed();
            return;
        }
        showFragment(showView);
    }

    /**
     * 筛选动态类型
     *
     * @param dynamicType 动态类型
     */
    private void filterDynamicType(int dynamicType) {
        switch (dynamicType) {
            case TYPE_MY_FAVORITE:
                showView = ClassUtils.getClassName(MyFavoriteDynamicFragment.class);
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

            if (ClassUtils.isNameEquals(fragmentName, MyFavoriteDynamicFragment.class)) {
                showMyFavorite();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void restoreFragments(String fragmentTAG) {
        Log.d(TAG, "restoreFragments----------->>" + fragmentTAG);
        if (ClassUtils.isNameEquals(fragmentTAG, MyFavoriteDynamicFragment.class)) {
            myFavoriteDynamicFragment = (MyFavoriteDynamicFragment) fm.findFragmentByTag(fragmentTAG);
            myFavoriteDynamicPresenter = new MyFavoriteDynamicPresenter(myFavoriteDynamicFragment,
                    Injection.provideMyFavoriteDynamicRepertory(getApplication()));
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = ClassUtils.getClassName(fragmentClass);
        if (ClassUtils.isNameEquals(fragmentTAG, MyFavoriteDynamicFragment.class)) {
            initMyFavorite();
        }
    }

    /**
     * 初始化我的喜欢
     */
    private void initMyFavorite() {
        if (myFavoriteDynamicFragment == null) {
            myFavoriteDynamicFragment = MyFavoriteDynamicFragment.newInstance(user_id);
            ActivityUtils.addFragmentToActivity(fm, myFavoriteDynamicFragment, contentView);
        }
        if (myFavoriteDynamicPresenter == null) {
            myFavoriteDynamicPresenter = new MyFavoriteDynamicPresenter(myFavoriteDynamicFragment,
                    Injection.provideMyFavoriteDynamicRepertory(getApplication()));
        }
    }

    /**
     * 显示我喜欢的页面
     */
    private void showMyFavorite() {
        ActivityUtils.showFragment(fm, myFavoriteDynamicFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.INTENT_KEY_USER_ID, user_id);
        outState.putString(SAVED_SHOW_VIEW, showView);
    }
}
