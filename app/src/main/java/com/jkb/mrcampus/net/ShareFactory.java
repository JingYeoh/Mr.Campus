package com.jkb.mrcampus.net;

import android.content.Context;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 分享的工具类
 * Created by JustKiddingBaby on 2016/10/8.
 */

public class ShareFactory {

    private Context applicationContext;
    private OnekeyShare oks;

    public ShareFactory(Context applicationContext) {
        this.applicationContext = applicationContext;

        ShareSDK.initSDK(applicationContext);
        oks = new OnekeyShare();
    }

    /**
     * 分享
     *
     * @param title    title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
     * @param titleUrl titleUrl是标题的网络链接，仅在人人网和QQ空间使用
     * @param text     text是分享文本，所有平台都需要这个字段
     * @param imageUrl 分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
     * @param url      url仅在微信（包括好友和朋友圈）中使用
     * @param site     site是分享此内容的网站名称，仅在QQ空间使用
     * @param siteUrl  siteUrl是分享此内容的网站地址，仅在QQ空间使用
     */
    public void share(
            String title, String titleUrl, String text, String imageUrl, String url,
            String site, String siteUrl) {
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(titleUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(imageUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(site);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(siteUrl);
        // 启动分享GUI
        oks.show(applicationContext);
    }
}
