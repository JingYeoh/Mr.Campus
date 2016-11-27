package com.jkb.mrcampus.fragment.search.function;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.model.utils.LogUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.adapter.fragmentPager.SearchDetailPagerAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.search.SearchFragment;

/**
 * 搜索详情的V层
 * Created by JustKiddingBaby on 2016/11/25.
 */

public class SearchDetailFragment extends BaseFragment {

    public static SearchDetailFragment newInstance(String searchKeyWord) {
        Bundle args = new Bundle();
        args.putString(Config.BUNDLE_KEY_SEARCH_KEY_WORDS, searchKeyWord);
        SearchDetailFragment fragment = new SearchDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private String searchKeyWord;
    private SearchFragment searchFragment;

    //view
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SearchDetailPagerAdapter searchDetailAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchFragment = (SearchFragment) getParentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_search_detail);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    protected void initListener() {
        viewPager.addOnPageChangeListener(onPageChangeListener);
        searchFragment.addKeyWordsChangedListener(onKeyWordsChangedListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getChildFragmentManager();
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            searchKeyWord = args.getString(Config.BUNDLE_KEY_SEARCH_KEY_WORDS);
        } else {
            searchKeyWord = savedInstanceState.getString(Config.BUNDLE_KEY_SEARCH_KEY_WORDS);
        }
        initGuide();
    }

    /**
     * 初始化导航栏视图
     */
    private void initGuide() {
        searchDetailAdapter = new SearchDetailPagerAdapter(fm, context);
        /**让标题栏和viewpager联动起来*/
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setAdapter(searchDetailAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //设置自定义视图
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //得到对应位置的tab
            TabLayout.Tab tabAt = tabLayout.getTabAt(i);
            if (tabAt != null) {
                tabAt.setCustomView(searchDetailAdapter.getTabView(i));
            }
        }
        viewPager.setCurrentItem(0);//设置当前只有第一个显示
        searchDetailAdapter.setViewSelected(tabLayout, 0);
    }

    @Override
    protected void initView() {
        //初始化导航栏
        viewPager = (ViewPager) rootView.findViewById(R.id.fsd_vp);
        tabLayout = (TabLayout) rootView.findViewById(R.id.fsd_tab);
    }

    /**
     * 设置搜索的结果数目
     *
     * @param allCount     总数
     * @param userCount    用户数
     * @param circleCount  圈子数
     * @param dynamicCount 动态数
     * @param subjectCount 专题数
     */
    public void setSearchResultCount(
            int allCount, int userCount, int circleCount, int dynamicCount, int subjectCount) {
        LogUtils.d(TAG, "setSearchResultCount---->>" +
                "allCount=" + allCount + "\n"
                + "userCount=" + userCount + "\n"
                + "circleCount=" + circleCount + "\n"
                + "dynamicCount=" + dynamicCount + "\n"
                + "subjectCount=" + subjectCount + "\n");
        searchDetailAdapter.searchCount =
                new int[]{allCount, userCount, circleCount, dynamicCount, subjectCount};
        //重新初始化视图
        searchDetailAdapter.updateView(tabLayout);
    }

    /**
     * 添加为搜索的关键字变化的监听者
     */
    public void addSearchKeyWordsChangedListener(SearchFragment.OnKeyWordsChangedListener listener) {
        searchFragment.addKeyWordsChangedListener(listener);
    }

    /**
     * 移除搜索的关键字变化的监听者
     */
    public void removeSearchKeyWordsChangedListener(
            SearchFragment.OnKeyWordsChangedListener listener) {
        searchFragment.removeKeyWordsChangedListener(listener);
    }

    /**
     * 更新数据
     */
    public void updateKeyWords(String keyWords) {
        if (searchDetailAdapter == null) {
            return;
        }
        this.searchKeyWord = keyWords;
        viewPager.setCurrentItem(0);
    }

    /**
     * 得到搜素的关键字
     */
    public String getSearchKeyWords() {
        return searchKeyWord;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Config.BUNDLE_KEY_SEARCH_KEY_WORDS, searchKeyWord);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //data
        searchDetailAdapter = null;
        //view
        tabLayout = null;
        viewPager = null;
        //监听器
        onPageChangeListener = null;
        searchFragment.removeKeyWordsChangedListener(onKeyWordsChangedListener);
        onKeyWordsChangedListener = null;
    }

    /**
     * 选择视图的监听器
     */
    private ViewPager.OnPageChangeListener onPageChangeListener =
            new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(
                        int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    searchDetailAdapter.setViewSelected(tabLayout, position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            };
    /**
     * 添加搜索的关键字改变时候的监听器
     */
    private SearchFragment.OnKeyWordsChangedListener onKeyWordsChangedListener =
            new SearchFragment.OnKeyWordsChangedListener() {
                @Override
                public void onKeyWordsChanged(String keyWords) {
                    searchKeyWord = keyWords;
                }
            };
}
