package com.jkb.mrcampus.adapter.conversation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.model.Conversation;

/**
 * 我的会话列表的适配器
 * Created by JustKiddingBaby on 2016/10/21.
 */

public class MyConversationListAdapter extends ConversationListAdapter {

    public MyConversationListAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, int position, ViewGroup group) {
        return super.newView(context, position, group);
    }

    @Override
    protected void bindView(View v, int position, UIConversation data) {
        if (data.getConversationType().equals(Conversation.ConversationType.DISCUSSION))
            data.setUnreadType(UIConversation.UnreadRemindType.REMIND_ONLY);
        super.bindView(v, position, data);
    }
}
