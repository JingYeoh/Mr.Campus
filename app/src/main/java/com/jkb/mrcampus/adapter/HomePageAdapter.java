package com.jkb.mrcampus.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jkb.mrcampus.fragment.function.SettingFragment;

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
//        if (position == 1) {
//            return MapFragment.newInstance();
//        } else {
//        }
        return SettingFragment.newInstance();
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
