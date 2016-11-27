package com.jkb.mrcampus.helper;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.jkb.model.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Activity相关的工具类
 * Created by JustKiddingBaby on 2016/7/25.
 */
public class ActivityUtils {

    private static final String TAG = "ActivityUtils";

    /**
     * 添加Fragment至Activity中
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fm,
                                             @NonNull Fragment fragment, @NonNull int containId) {
        checkNotNull(fm);
        checkNotNull(fragment);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(containId, fragment, fragment.getClass().getName());
        ft.commit();
    }

    /**
     * 添加Fragment至Activity中
     */
    public static void addFragmentToActivity(@NonNull android.app.FragmentManager fm,
                                             @NonNull android.app.Fragment fragment) {
        checkNotNull(fm);
        checkNotNull(fragment);
        android.app.FragmentTransaction ft = fm.beginTransaction();
        ft.add(fragment, fragment.getClass().getName());
        ft.commit();
    }

    /**
     * remove所有的Fragment
     */
    public static void removeAllFragment(@NonNull FragmentManager fm) {
        checkNotNull(fm);
        List<Fragment> fragments = fm.getFragments();
        if (fragments == null || fragments.size() == 0) {
            return;
        }
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                removeFragment(fm, fragment);
                fragment = null;
            }
        }
    }

    /**
     * 销毁只使用add()方法添加的Fragment
     */
    public static void removeFragment(@NonNull FragmentManager fm,
                                      @NonNull String fragmentName) {
        checkNotNull(fm);
        checkNotNull(fragmentName);

        Log.w(TAG, "要移除的fragment是：" + fragmentName);

        Fragment fragment = fm.findFragmentByTag(fragmentName);
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fragment);
            ft.commit();
        }
    }

    /**
     * 销毁只使用add()方法添加的Fragment
     */
    public static void removeFragment(@NonNull FragmentManager fm,
                                      @NonNull Fragment fragment) {
        checkNotNull(fm);
        checkNotNull(fragment);
        String fragmentTag = fragment.getClass().getName();
        removeFragment(fm, fragmentTag);
    }

    /**
     * 出栈頂部的Fragment
     */
    public static void popBackFragmentStack(@NonNull FragmentManager fm) {
        checkNotNull(fm);
        fm.popBackStackImmediate();
    }

    /**
     * 添加Fragment到回退栈
     */
    public static void addFragmentToBackStack(@NonNull FragmentManager fm,
                                              @NonNull Fragment fragment, @NonNull int containId) {
        checkNotNull(fm);
        checkNotNull(fragment);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(containId, fragment, fragment.getClass().getName());
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * 显示Fragment页面
     */
    public static void showFragment(@NonNull FragmentManager fm, @NonNull Fragment fragment) {
        checkNotNull(fm);
        checkNotNull(fragment);
        showFragment(fm, fragment.getClass().getName());
    }

    /**
     * 显示Fragment
     */
    private static void showFragment(@NonNull FragmentManager fm, String fragmentTag) {
        checkNotNull(fm);
        checkNotNull(fragmentTag);
        if (fragmentTag == null || fragmentTag.isEmpty()) {
            return;
        }
        Fragment fragment = fm.findFragmentByTag(fragmentTag);
        if (fragment != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.show(fragment);
            ft.commit();
        }
    }

    /**
     * 判断Fragment是否被添加过
     */
    public static boolean isFragmentAdded(@NonNull FragmentManager fm, @NonNull String fragmentTAG) {
        checkNotNull(fm);
        checkNotNull(fragmentTAG);
//        Fragment fragmentByTag = fm.findFragmentByTag(fragmentTAG);
//        LogUtils.w(TAG, "fragmentByTag=" + fragmentByTag);
        List<Fragment> fragments = fm.getFragments();
        if (fragments == null || fragments.size() == 0) {
            return false;
        }
        Log.w(TAG, "Fragment栈内条数：" + fragments.size());
        for (Fragment fragment : fragments) {
            if (fragment == null) {
                continue;
            }
            if (fragmentTAG.equals(fragment.getClass().getName())) {
                Log.w(TAG, fragmentTAG + "被添加过");
                return true;
            }
        }
        Log.w(TAG, fragmentTAG + "没有被添加过");
        return false;
    }

    /**
     * 判断Fragment是否被添加过
     */
    public static boolean isFragmentAdded(@NonNull android.app.FragmentManager fm,
                                          @NonNull String fragmentTAG) {
        checkNotNull(fm);
        checkNotNull(fragmentTAG);
        android.app.Fragment fragment = fm.findFragmentByTag(fragmentTAG);
        LogUtils.d(TAG, "isFragmentAdded--->fragment=" + fragment);
        return fragment != null;
    }

    /**
     * 隐藏掉所有的Fragment
     */
    public static void hideAllFragments(@NonNull FragmentManager fm) {
        List<Fragment> fragments = fm.getFragments();
        if (fragments == null || fragments.size() == 0) {
            return;
        }
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment == null) {
                continue;
            }
            //判断是否被添加过
            if (isFragmentAdded(fm, fragment.getClass().getName())) {
                Log.w(TAG, fragment.getClass().getName() + "被hide()了");
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    /**
     * * 隐藏Fragment视图
     * 已过期，方法错误
     */
    @Deprecated
    public static void hideFragments(@NonNull FragmentManager fm, Fragment... mfragments) {
        Fragment[] fgs = mfragments;
        FragmentTransaction ft = fm.beginTransaction();
        List<Fragment> fragments = fm.getFragments();
        for (Fragment fragment : fragments) {
            lableA:
            for (Fragment mfg : fgs) {
                if (mfg.equals(fragment)) {
                    continue lableA;
                }
            }
            ft.hide(fragment);
        }
        ft.commit();
    }

    /**
     * 隐藏所有的视图
     */
    public static void hideFragments(@NonNull FragmentManager fm, String... fgmTAG) {
        String[] tags = fgmTAG;
        List<Fragment> fragments = fm.getFragments();
        if (fragments == null || fragments.size() == 0) {
            return;
        }
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment == null) {
                continue;
            }
            ft.hide(fragment);
        }
        for (int i = 0; i < tags.length; i++) {
            Fragment fragment = fm.findFragmentByTag(tags[i]);
            if (fragment != null) {
                ft.show(fragment);
            }
        }
        ft.commit();
    }

    /**
     * 判断服务是否开启
     *
     * @param className 类名
     * @param context   上下文
     * @return 返回值
     */
    public static boolean isServiceWorked(String className, Context context) {
        ActivityManager myManager = (ActivityManager) context
                .getApplicationContext().getSystemService(
                        Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(className)) {
                return true;
            }
        }
        return false;
    }
}
