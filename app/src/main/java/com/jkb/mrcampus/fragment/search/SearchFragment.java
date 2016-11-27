package com.jkb.mrcampus.fragment.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.SearchActivity;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.search.function.SearchDetailFragment;
import com.jkb.mrcampus.fragment.search.function.SearchRecommendFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索的V层
 * Created by JustKiddingBaby on 2016/11/25.
 */

public class SearchFragment extends BaseFragment implements View.OnClickListener {

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private SearchActivity searchActivity;
    private int contentView = R.id.searchDetailContent;

    //view
    private EditText etInput;

    //搜索推荐
    private SearchRecommendFragment searchRecommendFragment;

    //搜索详情
    private SearchDetailFragment searchDetailFragment;
    private List<OnKeyWordsChangedListener> onKeyWordsChangedListeners;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchActivity = (SearchActivity) mActivity;
        setRootView(R.layout.frg_search);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    protected void initListener() {
        etInput.setOnEditorActionListener(onEditorActionListener);
        rootView.findViewById(R.id.fs_iv_clear).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getChildFragmentManager();
        onKeyWordsChangedListeners = new ArrayList<>();
        if (savedInstanceState == null) {
            showFragment(ClassUtils.getClassName(SearchRecommendFragment.class));
        } else {
            restoreFragments();
        }
    }

    @Override
    protected void initView() {
        etInput = (EditText) rootView.findViewById(R.id.fs_et_input);
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
            if (ClassUtils.isNameEquals(fragmentName, SearchRecommendFragment.class)) {
                showSearchRecommend();
            } else if (ClassUtils.isNameEquals(fragmentName, SearchDetailFragment.class)) {
                showSearchDetail();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, SearchRecommendFragment.class)) {
            searchRecommendFragment = (SearchRecommendFragment) fm.findFragmentByTag(fragmentTAG);
        } else if (ClassUtils.isNameEquals(fragmentTAG, SearchDetailFragment.class)) {
            searchDetailFragment = (SearchDetailFragment) fm.findFragmentByTag(fragmentTAG);
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, SearchRecommendFragment.class)) {
            initSearchCommend();
        } else if (ClassUtils.isNameEquals(fragmentTAG, SearchDetailFragment.class)) {
            initSearchDetail();
        }
    }

    /**
     * 初始化搜索详情
     */
    private void initSearchDetail() {
        if (searchDetailFragment == null) {
            searchDetailFragment = SearchDetailFragment.newInstance(getSearchKeyWords());
            ActivityUtils.addFragmentToActivity(fm, searchDetailFragment, contentView);
        }
    }

    /**
     * 初始化搜索推荐
     */
    private void initSearchCommend() {
        if (searchRecommendFragment == null) {
            searchRecommendFragment = SearchRecommendFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, searchRecommendFragment, contentView);
        }
    }

    /**
     * 显示搜索详情
     */
    private void showSearchDetail() {
        searchDetailFragment.updateKeyWords(getSearchKeyWords());
        ActivityUtils.showFragment(fm, searchDetailFragment);
    }

    /**
     * 显示搜索推荐
     */
    private void showSearchRecommend() {
        ActivityUtils.showFragment(fm, searchRecommendFragment);
    }

    /**
     * 开始搜索
     */
    private void search() {
        if (StringUtils.isEmpty(getSearchKeyWords())) {
            showShortToash("请输入搜索内容");
            return;
        }
        showFragment(ClassUtils.getClassName(SearchDetailFragment.class));
        //通知
        notifyKeyWordsChanged();
    }

    /**
     * 通知关键字变化
     */
    private void notifyKeyWordsChanged() {
        if (onKeyWordsChangedListeners == null || onKeyWordsChangedListeners.size() == 0) {
            return;
        }
        for (OnKeyWordsChangedListener listener : onKeyWordsChangedListeners) {
            listener.onKeyWordsChanged(getSearchKeyWords());
        }
    }

    /**
     * 得到搜索的内容
     */
    private String getSearchKeyWords() {
        return etInput.getText().toString();
    }

    /**
     * 关闭或者清楚内容
     */
    private void clearOrClose() {
        String searValue = getSearchKeyWords();
        if (!StringUtils.isEmpty(searValue)) {
            etInput.setText("");
        } else {
            searchActivity.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        searchActivity = null;
        etInput = null;
        onEditorActionListener = null;
        onKeyWordsChangedListeners.clear();
        onKeyWordsChangedListeners = null;
    }

    /**
     * 软键盘监听
     */
    private TextView.OnEditorActionListener onEditorActionListener =
            new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEND ||
                            actionId == EditorInfo.IME_ACTION_SEARCH) {
                        search();
                        return true;
                    }
                    return false;
                }
            };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fs_iv_clear:
                clearOrClose();
                break;
        }
    }

    /**
     * 添加为关键字变化的监听者
     */
    public void addKeyWordsChangedListener(OnKeyWordsChangedListener listener) {
        if (!onKeyWordsChangedListeners.contains(listener)) {
            onKeyWordsChangedListeners.add(listener);
        }
    }

    /**
     * 添加为关键字变化的监听者
     */
    public void removeKeyWordsChangedListener(OnKeyWordsChangedListener listener) {
        if (onKeyWordsChangedListeners.contains(listener)) {
            onKeyWordsChangedListeners.remove(listener);
        }
    }

    //关键字变化的监听器
    public interface OnKeyWordsChangedListener {
        /**
         * 关键字变化时触发
         */
        void onKeyWordsChanged(String keyWords);
    }
}
