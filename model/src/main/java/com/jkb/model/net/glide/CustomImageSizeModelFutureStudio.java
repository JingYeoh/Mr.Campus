package com.jkb.model.net.glide;

/**
 * 我们已经实现了根据添加的宽高参数创建图片URL的逻辑
 * Created by JustKiddingBaby on 2016/9/7.
 */

public class CustomImageSizeModelFutureStudio implements CustomImageSizeModel {

    private String baseImageUrl;

    public CustomImageSizeModelFutureStudio(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    @Override
    public String requestCustomSizeUrl(int width, int height) {
        return baseImageUrl + "?w=" + width + "&h=" + height;
    }
}
