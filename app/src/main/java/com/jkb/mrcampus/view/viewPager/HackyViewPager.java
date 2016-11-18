package com.jkb.mrcampus.view.viewPager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 主要为了解决和PhotoView一起使用时滑动冲突问题
 * Created by JustKiddingBaby on 2016/11/17.
 */

public class HackyViewPager extends android.support.v4.view.ViewPager {

    public HackyViewPager(Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
