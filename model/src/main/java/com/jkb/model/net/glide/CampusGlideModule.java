package com.jkb.model.net.glide;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;
import com.jkb.api.config.Config;

/**
 * 关于Glide的Module配置类
 * Glide modules是一个全局改变Glide行为的抽象的方式。你需要创建Glide的实例，
 * 来访问GlideBuilder。可以通过创建一个公共的类，实现GlideModule的接口来定制Glide：
 * Created by JustKiddingBaby on 2016/9/7.
 */

public class CampusGlideModule implements GlideModule {

    private static final String TAG = "CampusGlideModule";

    private static final String DISC_CACHE_PATH = Config.PATH_ROOT_IMAGE + "glideCaches";
    private static final int DISK_CACHE_SIZE = 2014 * 1024;//设置的磁盘缓存大小

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        Log.d(TAG, "applyOptions");

        //设置解析图片方式为ARGB8888，提升图片质量
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

        //设置缓存大小
        //MemorySizeCalculator会根据给定屏幕大小可用内存算出合适的缓存大小，这也是推荐的缓存大小，
        // 我们可以根据这个推荐大小做出调整：
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        //设置磁盘缓存
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,
                DISC_CACHE_PATH, DISK_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        Log.d(TAG, "registerComponents");
    }
}
