package com.jkb.mrcampus.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jkb.mrcampus.fragment.chat.ChatFragment;
import com.jkb.mrcampus.fragment.chat.FriendsFragment;

/**
 * 聊天页面的适配器
 * Created by JustKiddingBaby on 2016/8/9.
 */

public class ChatAdapter extends FragmentPagerAdapter {

    private String[] mTab = new String[]{"聊天", "好友"};
    private Context context;

    public ChatAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ChatFragment.newInstance();
        } else if (position == 1) {
            return FriendsFragment.newInstance();
        } else {
            return null;
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
