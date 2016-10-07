package com.jkb.model.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.jkb.api.config.Config;
import com.jkb.model.R;
import com.jkb.model.utils.BitmapUtils;
import com.jkb.model.utils.StringUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;

/**
 * 网络图片加载的工具类
 * Created by JustKiddingBaby on 2016/7/29.
 */
public class ImageLoaderFactory {

    private static final String DISC_CACHE_PATH = Config.PATH_ROOT_IMAGE + "caches";
    private Context context;
    private static ImageLoaderFactory INSTANCE = new ImageLoaderFactory();
    private DisplayImageOptions options;
    private ImageLoaderConfiguration config;

    private ImageLoaderFactory() {
    }

    /**
     * 得到单利实例对象
     */
    public static ImageLoaderFactory getInstance() {
        return ImageLoaderHolder.sInstance;
    }

    /**
     * 实例化类对象
     */
    private static class ImageLoaderHolder {
        private static final ImageLoaderFactory sInstance = new ImageLoaderFactory();
    }

    /**
     *
     */
    public void create() {
        ImageLoader.getInstance().init(getLoaderConfig());
    }

    /**
     * 设置上下文对象
     */
    public void setApplicationContext(@NonNull Context applicationContext) {
        this.context = applicationContext;
    }

    public void setOptions(DisplayImageOptions options) {
        this.options = options;
    }

    /**
     * 加载网络图片
     */
    public void loadImage(@NonNull String imageUrl, ImageSize imageSize,
                          @NonNull ImageLoadingListener listener) {
        if (imageSize == null) {
            ImageLoader.getInstance().loadImage(imageUrl, listener);
        } else {
            ImageLoader.getInstance().loadImage(imageUrl, imageSize, listener);
        }
    }

    /**
     * 加载图片
     */
    public void displayImage(ImageView imageView, String imageUrl) {
        Bundle bundle = (Bundle) imageView.getTag();
        if (bundle != null) {
            String url = bundle.getString(com.jkb.model.utils.Config.BUNDLE_KEY_IMAGE_URL);
            if (!StringUtils.isEmpty(url, imageUrl) && imageUrl.equals(url)) {
                return;
            }
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView);
        ImageLoaderUtils.bindImage$Url(imageUrl, imageView);
    }

    /**
     * 加载高斯模糊的图片
     */
    public void displayBlurImage(final ImageView imageView,
                                 final String imageUrl, final int radius, final int downSampling) {
        Bundle bundle = (Bundle) imageView.getTag();
        if (bundle != null) {
            String url = bundle.getString(com.jkb.model.utils.Config.BUNDLE_KEY_IMAGE_URL);
            if (!StringUtils.isEmpty(url, imageUrl) && imageUrl.equals(url)) {
                return;
            }
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (view.getId() == imageView.getId()) {
                    ((ImageView) view).
                            setImageBitmap(BitmapUtils.fastBlur(bitmap, radius, downSampling));
                    ImageLoaderUtils.bindImage$Url(imageUrl, imageView);
                }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    /**
     * 从内存卡中异步加载本地图片
     */
    public void displayFromSDCard(String uri, ImageView imageView) {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        ImageLoader.getInstance().displayImage("file://" + uri, imageView);
    }

    /**
     * 从assets文件夹中异步加载图片
     */
    public void dispalyFromAssets(String imageName, ImageView imageView) {
        // String imageUri = "assets://image.png"; // from assets
        ImageLoader.getInstance().displayImage("assets://" + imageName,
                imageView);
    }

    /**
     * 从drawable中异步加载本地图片
     */
    public void displayFromDrawable(int imageId, ImageView imageView) {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
        ImageLoader.getInstance().displayImage("drawable://" + imageId,
                imageView);
    }

    /**
     * 从内容提提供者中抓取图片
     */
    public void displayFromContent(String uri, ImageView imageView) {
        // String imageUri = "content://media/external/audio/albumart/13"; //
        // from content provider
        ImageLoader.getInstance().displayImage("content://" + uri, imageView);
    }

    public DisplayImageOptions getDisplayOptions() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.color.default_picture) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.color.default_picture)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.color.default_picture) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的下载前的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
//                .displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(250))// 是否图片加载好后渐入的动画时间
                .displayer(new SimpleBitmapDisplayer())//解决闪烁的问题
                .build();
        return options;
    }

    public ImageLoaderConfiguration getLoaderConfig() {
        // 我们可以根据这个推荐大小做出调整：
        config = new ImageLoaderConfiguration.Builder(
                context)
                .memoryCacheExtraOptions(480, 800)
                // max width, max height，即保存的每个缓存文件的最大长宽
                .discCacheExtraOptions(480, 800, null)
                // Can slow ImageLoader, use it carefully (Better don't use
                // it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
//                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                // You can pass your own memory cache
                // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                // 缓存的文件数量
                .discCache(
                        new UnlimitedDiskCache(new File(DISC_CACHE_PATH)))
                // 自定义缓存路径
                .defaultDisplayImageOptions(getDisplayOptions())
                .imageDownloader(
                        new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
//                .writeDebugLogs() // Remove for release app
                .build();// 开始构建
        return config;
    }
}
