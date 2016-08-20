package com.jkb.mrcampus.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.jkb.mrcampus.Config;

/**
 * 关于类之间的工具类
 * Created by JustKiddingBaby on 2016/8/1.
 */
public class ClassUtils {

    private static final String TAG = "ClassUtils";

    /**
     * 根据类得到类名
     *
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> String getClassName(@NonNull Class<T> clz) {
        return clz.getName();
    }

    /**
     * 判断两个类名是否相等
     *
     * @param name
     * @param clz
     * @return
     */
    public static boolean isNameEquals(String name, Class<?> clz) {
        if (clz == null || name == null) {
            return false;
        } else {
            if (getClassName(clz).equals(name)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 得到View的TAG
     *
     * @param view View控件
     * @return TAG bundle对象
     */
    public static Bundle getViewTag(View view) {
        Bundle bundle = new Bundle();
        int tagId = view.getId();
        bundle.putInt(Config.BUNDLE_KEY_VIEW_ID, tagId);
        return bundle;
    }

    /**
     * 给view绑定TAG
     *
     * @param views View控件
     */
    public static void bindViewsTag(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setTag(getViewTag(views[i]));
        }
    }

    /**
     * 给view绑定TAG
     *
     * @param views    View控件
     * @param position 所在的条目item数
     */
    public static void bindViewsTag(int position, View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setTag(getViewTag(views[i], position));
        }
    }

    /**
     * 得到View的TAG
     *
     * @param view     View控件
     * @param position 所在的条目item数
     * @return TAG bundle对象
     */
    public static Bundle getViewTag(View view, int position) {
        Bundle bundle = new Bundle();
        int tagId = view.getId();
        bundle.putInt(Config.BUNDLE_KEY_VIEW_ID, tagId);
        bundle.putInt(Config.BUNDLE_KEY_VIEW_POSITION, position);
        return bundle;
    }
}
