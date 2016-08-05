package com.jkb.core.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.io.InputStream;

/**
 * Created by JustKiddingBaby on 2016/7/26.
 */
public class BitmapUils {

    /**
     * 从Drawable文件中得到Bitmap对象
     *
     * @param context
     * @param ResourceId
     * @return
     */
    public static Bitmap getBitmapFromDrawable(Context context, int ResourceId) {
        //得到Resources对象
        Resources r = context.getResources();
        //以数据流的方式读取资源
        InputStream is = r.openRawResource(ResourceId);
        BitmapDrawable bmpDraw = new BitmapDrawable(is);
        Bitmap bmp = bmpDraw.getBitmap();
        return bmp;
    }
}
