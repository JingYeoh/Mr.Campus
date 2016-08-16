package com.jkb.mrcampus.fragment.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.adapter.baseAdapter.RightMenuChatAdapter;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 右滑菜单——聊天的页面
 * Created by JustKiddingBaby on 2016/8/9.
 */

public class ChatFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListView lvChat;
    private SwipeRefreshLayout refreshLayout;

    private RightMenuChatAdapter rightMenuChatAdapter;

    private static ChatFragment INSTANCE = null;

    public ChatFragment() {
    }

    public static ChatFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_chat_chat);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        rightMenuChatAdapter = new RightMenuChatAdapter(mActivity);
        lvChat.setAdapter(rightMenuChatAdapter);
    }

    @Override
    protected void initView() {
        lvChat = (ListView) rootView.findViewById(R.id.fcc_lv);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fcc_srl);
        refreshLayout.setColorSchemeResources(
                R.color.main_theme_gravy, R.color.line, R.color.line_deep, R.color.main_theme);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
        Log.d(TAG, "onRefresh");
    }
}
