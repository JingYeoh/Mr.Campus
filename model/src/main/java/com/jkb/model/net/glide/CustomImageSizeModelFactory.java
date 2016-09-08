package com.jkb.model.net.glide;

import android.content.Context;

import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import java.io.InputStream;

/**
 * 它创建了我们CustomImageSizeUrlLoader的一个新实例，一旦Glide请求被创建，将会去处理图片加载
 * Created by JustKiddingBaby on 2016/9/7.
 */

public class CustomImageSizeModelFactory implements ModelLoaderFactory<CustomImageSizeModel, InputStream> {


    @Override
    public ModelLoader<CustomImageSizeModel, InputStream> build(
            Context context, GenericLoaderFactory factories) {
        return new CustomImageSizeUrlLoader(context);
    }

    @Override
    public void teardown() {

    }
}
