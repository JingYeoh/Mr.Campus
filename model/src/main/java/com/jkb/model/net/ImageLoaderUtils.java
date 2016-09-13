package com.jkb.model.net;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jkb.model.utils.Config;

/**
 * Created by JustKiddingBaby on 2016/9/13.
 */

public class ImageLoaderUtils {

    /**
     * 得到ImageView的TAG
     *
     * @param urls View控件
     * @return TAG bundle对象
     */
    public static void bindImage$Url(String urls, ImageView imageViews) {
        imageViews.setTag(getImageViewTag(imageViews, urls));
    }

    /**
     * 得到View的Url的TAG
     *
     * @param view     ImageView控件
     * @param imageUrl Url
     * @return TAG bundle对象
     */
    public static Bundle getImageViewTag(View view, String imageUrl) {
        Bundle bundle = new Bundle();
        int tagId = view.getId();
        bundle.putInt(Config.BUNDLE_KEY_VIEW_ID, tagId);
        bundle.putString(Config.BUNDLE_KEY_IMAGE_URL, imageUrl);
        return bundle;
    }
}
