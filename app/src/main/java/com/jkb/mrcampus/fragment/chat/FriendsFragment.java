package com.jkb.mrcampus.fragment.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.adapter.baseAdapter.RightMenuFriendsAdapter;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 右滑菜单——好友页面
 * Created by JustKiddingBaby on 2016/8/9.
 */

public class FriendsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RightMenuFriendsAdapter friendsAdapter;
    private ListView lvFriends;
    private SwipeRefreshLayout refreshLayout;


    private static FriendsFragment INSTANCE = null;

    public FriendsFragment() {
    }

    public static FriendsFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FriendsFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_chat_friends);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        friendsAdapter = new RightMenuFriendsAdapter(mActivity);
        lvFriends.setAdapter(friendsAdapter);
    }

    @Override
    protected void initView() {
        lvFriends = (ListView) rootView.findViewById(R.id.fcf_lv);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fcf_srl);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }
}
