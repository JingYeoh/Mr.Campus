package com.jkb.mrcampus.adapter.pager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 引导页面的适配器
 * Created by JustKiddingBaby on 2016/10/27.
 */

public class GuidePagerAdapter extends PagerAdapter {
    private List<View> data;

    public GuidePagerAdapter(List<View> data) {
        super();
        this.data = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        container.addView(data.get(position));
        return data.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(data.get(position));
    }
}
