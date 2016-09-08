package com.jkb.model.net.glide;

/**
 * 为了添加宽度和高度作为参数，可以从媒体服务器请求精确像素的图片
 * Created by JustKiddingBaby on 2016/9/7.
 */

public interface CustomImageSizeModel {
    /**
     * 设置请求图片的宽度和高度
     *
     * @param width  宽度
     * @param height 高度
     * @return URL
     */
    String requestCustomSizeUrl(int width, int height);
}
