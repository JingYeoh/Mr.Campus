package com.jkb.mrcampus.adapter.fragmentPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jkb.mrcampus.fragment.function.SettingFragment;
import com.jkb.mrcampus.fragment.function.homePage.DynamicFragment;
import com.jkb.mrcampus.fragment.function.homePage.HotFragment;

/**
 * 首页HomePageFragment的适配器
 * Created by JustKiddingBaby on 2016/7/26.
 */
public class HomePageAdapter extends FragmentPagerAdapter {

    private String[] mTab = new String[]{"热门", "动态"};

    public HomePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return HotFragment.newInstance();
        } else if (position == 1) {
            return DynamicFragment.newInstance();
        } else {
            return HotFragment.newInstance();
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
