package com.jkb.mrcampus.net;

import android.content.Context;
import android.widget.ImageView;

import com.jkb.model.net.ImageLoaderFactory;
import com.youth.banner.loader.ImageLoader;

/**
 * 自己加载图片的方式
 * Created by JustKiddingBaby on 2016/11/23.
 */

public class MyBannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImageLoaderFactory.getInstance().displayImage(imageView,(String)path);
    }
}
