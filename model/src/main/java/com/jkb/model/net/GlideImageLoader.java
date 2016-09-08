package com.jkb.model.net;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.jkb.model.R;

/**
 * Glide加载图片的类
 * Created by JustKiddingBaby on 2016/9/7.
 */

public class GlideImageLoader {

    private static final String TAG = "GlideImageLoader";
    private static String URL = "http://e.hiphotos.baidu.com/image/h%3D200/sign=4cb5691061380cd7f91ea5ed9145ad14/48540923dd54564e6e937299b7de9c82d0584f98.jpg";

    /**
     * 加载普通的图片
     *
     * @param context   上下文
     * @param imageView ImageView对象
     * @param imageUrl  图片地址
     */
    public static void loadNormalImage(Context context, ImageView imageView, String imageUrl) {
        Log.d(TAG, "imageUrl="+imageUrl);
        Uri uri = Uri.parse(imageUrl);
        Glide.with(context)
                .load(uri)
                .placeholder(R.color.default_picture)
                .error(R.color.default_picture)
                .priority(Priority.NORMAL)
                .crossFade()//设置平滑动画
                .into(imageView);
    }

    /**
     * 加载高级别的图片
     *
     * @param context   上下文
     * @param imageView ImageView对象
     * @param imageUrl  图片地址
     */
    public static void loadHighPriorityImage(Context context, ImageView imageView, String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.color.default_picture)
                .error(R.color.default_picture)
                .priority(Priority.HIGH)
                .crossFade()//设置平滑动画
                .into(imageView);
    }

    /**
     * 加载低级别的图片
     *
     * @param context   上下文
     * @param imageView ImageView对象
     * @param imageUrl  图片地址
     */
    public static void loadLowPriorityImage(Context context, ImageView imageView, String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.color.default_picture)
                .error(R.color.default_picture)
                .priority(Priority.LOW)
                .crossFade()//设置平滑动画
                .into(imageView);
    }
}
