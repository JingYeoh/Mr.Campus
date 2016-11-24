package com.jkb.mrcampus.adapter.fragmentPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jkb.mrcampus.fragment.function.index.detail.DynamicFragment2;
import com.jkb.mrcampus.fragment.function.index.detail.HotFragment;
import com.jkb.mrcampus.fragment.function.special.function.SpecialConfessionFragment;
import com.jkb.mrcampus.fragment.function.special.function.SpecialFleaMarketFragment;
import com.jkb.mrcampus.fragment.function.special.function.SpecialLost$FoundFragment;
import com.jkb.mrcampus.fragment.function.special.function.SpecialTauntedFragment;
import com.jkb.mrcampus.fragment.function.special.function.SpecialWantedPartnerFragment;
import com.jkb.mrcampus.fragment.function.special.function.SpecialWantedSavantFragment;

/**
 * 专题的适配器
 * Created by JustKiddingBaby on 2016/7/26.
 */
public class SpecialAdapter extends FragmentPagerAdapter {

    private String[] mTab = new String[]{"表白墙", "吐槽墙", "失物招领", "跳蚤市场", "求学霸", "寻水友"};

    public SpecialAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return SpecialConfessionFragment.newInstance();
        } else if (position == 1) {
            return SpecialTauntedFragment.newInstance();
        } else if (position == 2) {
            return SpecialLost$FoundFragment.newInstance();
        } else if (position == 3) {
            return SpecialFleaMarketFragment.newInstance();
        } else if (position == 4) {
            return SpecialWantedSavantFragment.newInstance();
        } else if (position == 5) {
            return SpecialWantedPartnerFragment.newInstance();
        } else {
            return SpecialConfessionFragment.newInstance();
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
