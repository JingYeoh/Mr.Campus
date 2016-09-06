package com.jkb.mrcampus.adapter.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jkb.core.contract.menu.data.FriendsData;
import com.jkb.mrcampus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 右滑菜单的好友列表的适配器
 * Created by JustKiddingBaby on 2016/8/10.
 */

public class RightMenuFriendsAdapter extends BaseAdapter {

    private Context context;
    private List<FriendsData> friendsData;

    public RightMenuFriendsAdapter(Context context, List<FriendsData> friendsData) {
        this.context = context;
        if (friendsData == null) {
            friendsData = new ArrayList<>();
        }
        this.friendsData = friendsData;
    }

    @Override
    public int getCount() {
        return friendsData.size();
    }

    @Override
    public Object getItem(int i) {
        return friendsData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
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
