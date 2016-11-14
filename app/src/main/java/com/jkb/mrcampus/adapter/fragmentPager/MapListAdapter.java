package com.jkb.mrcampus.adapter.fragmentPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jkb.mrcampus.fragment.function.index.detail.DynamicFragment2;
import com.jkb.mrcampus.fragment.function.index.detail.HotFragment;
import com.jkb.mrcampus.fragment.function.map.list.MapListNearCircleFragment;
import com.jkb.mrcampus.fragment.function.map.list.MapListNearUserFragment;

/**
 * 圈子列表的适配器
 * Created by JustKiddingBaby on 2016/7/26.
 */
public class MapListAdapter extends FragmentPagerAdapter {

    private String[] mTab = new String[]{"附近的人", "圈子"};

    public MapListAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return MapListNearUserFragment.newInstance();
        } else if (position == 1) {
            return MapListNearCircleFragment.newInstance();
        } else {
            return MapListNearUserFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return mTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTab[position];
    }
}
