package com.jkb.model.net.glide;

import android.content.Context;

import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

/**
 * 当我们的新Glide module完成，并准备自定义大小请求。我们已经完成了Glide module边的所有事情，
 * 但是我们实际上还没有创建CustomImageSizeModel接口的实现。为了用CustomImageSizeModel传递请求到Glide，
 * 我们需要一个类，创建定制图片大小的URL:
 * Created by JustKiddingBaby on 2016/9/7.
 */

public class CustomImageSizeUrlLoader extends BaseGlideUrlLoader<CustomImageSizeModel> {

    public CustomImageSizeUrlLoader(Context context) {
        super(context);
    }

    @Override
    protected String getUrl(CustomImageSizeModel model, int width, int height) {
        return model.requestCustomSizeUrl(width, height);
    }
}
