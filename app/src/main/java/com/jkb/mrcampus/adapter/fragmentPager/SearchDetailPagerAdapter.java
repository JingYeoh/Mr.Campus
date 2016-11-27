package com.jkb.mrcampus.adapter.fragmentPager;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.fragment.search.detail.SearchDetailAllFragment;
import com.jkb.mrcampus.fragment.search.detail.SearchDetailCircleFragment;
import com.jkb.mrcampus.fragment.search.detail.SearchDetailDynamicFragment;
import com.jkb.mrcampus.fragment.search.detail.SearchDetailSubjectFragment;
import com.jkb.mrcampus.fragment.search.detail.SearchDetailUserFragment;

/**
 * 搜索详情的页面数据适配器
 * Created by JustKiddingBaby on 2016/11/25.
 */

public class SearchDetailPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String[] searchType = new String[]{
            "全部", "用户", "圈子", "动态", "专题"
    };

    public int[] searchCount = new int[searchType.length];

    //颜色
    private int textColorSelector;
    private int textColorNormal;

    public SearchDetailPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        textColorSelector = context.getResources().getColor(R.color.black);
        textColorNormal = context.getResources().getColor(R.color.line);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return SearchDetailAllFragment.newInstance();
        } else if (position == 1) {
            return SearchDetailUserFragment.newInstance();
        } else if (position == 2) {
            return SearchDetailCircleFragment.newInstance();
        } else if (position == 3) {
            return SearchDetailDynamicFragment.newInstance();
        } else {
            return SearchDetailSubjectFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return searchType.length;
    }

    /**
     * 得到Tab的视图
     */
    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_guide, null);
        TextView tvType = (TextView) view.findViewById(R.id.isg_tv_name);
        TextView tvCount = (TextView) view.findViewById(R.id.isg_tv_count);
        tvType.setText(searchType[position]);
        tvCount.setText(searchCount[position] + "");
        return view;
    }

    /**
     * 更新视图
     */
    public void updateView(TabLayout tabLayout) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tabAt = tabLayout.getTabAt(i);
            if (tabAt == null) {
                return;
            }
            View view = tabAt.getCustomView();
            if (view == null) {
                return;
            }
            TextView tvType = (TextView) view.findViewById(R.id.isg_tv_name);
            TextView tvCount = (TextView) view.findViewById(R.id.isg_tv_count);
            tvType.setText(searchType[i]);
            tvCount.setText(searchCount[i] + "");
        }
    }

    /**
     * 设置视图状态为选择状态
     */
    public void setViewSelected(TabLayout tabLayout, int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tabAt = tabLayout.getTabAt(i);
            if (tabAt == null) {
                return;
            }
            View view = tabAt.getCustomView();
            if (view == null) {
                return;
            }
            TextView tvType = (TextView) view.findViewById(R.id.isg_tv_name);
            TextView tvCount = (TextView) view.findViewById(R.id.isg_tv_count);
            if (i == position) {
                tvType.setTextColor(textColorSelector);
                tvCount.setTextColor(textColorSelector);
            } else {
                tvType.setTextColor(textColorNormal);
                tvCount.setTextColor(textColorNormal);
            }
        }
    }
}
