package com.jkb.mrcampus.utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import com.commit451.nativestackblur.NativeStackBlur;

/**
 * Bitmap相关的工具类
 * Created by Roger on 2016/4/28.
 */
public class BitmapUtil {
    /**
     * 得到主要的颜色
     *
     * @param src Bitmap对象
     * @return 得到色值
     */
    public static int getPixColor(Bitmap src) {
        int pixelColor;
        pixelColor = src.getPixel(5, 5);
        return pixelColor;
    }

    /**
     * 快速高斯模糊图片
     *
     * @param bitmap       传入的bitmap图片对象
     * @param radius       高斯模糊的半径, 每一个像素都取周边(多少个)像素的平均值,推荐10以上
     * @param downSampling 采样率 原本是设置到BlurPostprocessor上的,因为高斯模糊本身对图片清晰度要求就不高,
     *                     所以此处直接设置到ResizeOptions上,直接让解码生成的bitmap就缩小,而BlurPostprocessor
     *                     内部sampling设置为1,无需再缩
     * @return 返回高斯模糊后的图片Bitmap对象
     */
    public static Bitmap fastBlur(@NonNull Bitmap bitmap, int radius, int downSampling) {
        if (downSampling < 2) {
            downSampling = 2;
        }
        Bitmap smallBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / downSampling,
                bitmap.getHeight() / downSampling, true);

        return NativeStackBlur.process(smallBitmap, radius);
    }

    /**
     * 转换View为Bitmap
     */
    public static Bitmap getViewBitmap(View addViewContent) {
        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());
        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        return bitmap;
    }
}