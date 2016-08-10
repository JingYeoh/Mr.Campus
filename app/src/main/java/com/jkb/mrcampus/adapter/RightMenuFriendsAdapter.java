package com.jkb.mrcampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jkb.mrcampus.R;

/**
 * 右滑菜单的好友列表的适配器
 * Created by JustKiddingBaby on 2016/8/10.
 */

public class RightMenuFriendsAdapter extends BaseAdapter {

    private Context context;

    public RightMenuFriendsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FriendsViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_friend, viewGroup, false);
            holder = new FriendsViewHolder();
            view.setTag(holder);
        } else {
            holder = (FriendsViewHolder) view.getTag();
        }
        initView(holder, i, view);
        return view;
    }

    /**
     * 初始化View视图
     */
    private void initView(FriendsViewHolder holder, int i, View view) {

    }

    /**
     * 聊天的ViewHolder内部类
     */
    public class FriendsViewHolder {
    }
}
