package com.jkb.mrcampus.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * 聊天的页面控制器
 * Created by JustKiddingBaby on 2016/10/19.
 */

public class ConversationActivity extends BaseActivity
//        implements
//        RongIM.UserInfoProvider
{

    /**
     * 目标 Id
     */
    private String mTargetId;

    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String mTargetIds;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_conversation);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        RongIM.setUserInfoProvider(this, true);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void showFragment(String fragmentName) {

    }

    @Override
    protected void restoreFragments(String fragmentTAG) {

    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {

    }

   /* @Override
    public UserInfo getUserInfo(String s) {
        UserInfo userInfo = new UserInfo(s, "对面的昵称",
                Uri.parse("http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png"));
        return userInfo;
    }*/
}
