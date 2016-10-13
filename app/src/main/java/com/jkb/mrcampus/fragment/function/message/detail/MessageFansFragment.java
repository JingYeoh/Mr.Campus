package com.jkb.mrcampus.fragment.function.message.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MessageActivity;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 消息详情：粉丝的View层
 * Created by JustKiddingBaby on 2016/10/10.
 */

public class MessageFansFragment extends BaseFragment implements View.OnClickListener {


    public MessageFansFragment() {
    }

    private static MessageFansFragment INSTANCE = null;

    public static MessageFansFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MessageFansFragment();
        }
        return INSTANCE;
    }

    //data
    private MessageActivity messageActivity;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        messageActivity = (MessageActivity) mActivity;
        setRootView(R.layout.frg_message_fans);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("粉丝");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts4_ib_left:
                messageActivity.onBackPressed();
                break;
        }
    }
}
