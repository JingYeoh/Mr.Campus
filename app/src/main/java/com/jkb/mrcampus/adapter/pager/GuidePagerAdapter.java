package com.jkb.mrcampus.adapter.pager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jkb.mrcampus.R;

import java.util.List;

/**
 * 引导页面的适配器
 * Created by JustKiddingBaby on 2016/10/27.
 */

public class GuidePagerAdapter extends PagerAdapter {
    private List<View> data;
    private int[] mResIds;

    public GuidePagerAdapter(List<View> data, int[] mResIds) {
        this.data = data;
        this.mResIds = mResIds;
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
        View view = data.get(position);
        int count = getCount();
//        ImageView imageView = (ImageView) view.findViewById(R.id.iguide_img);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
//        imageView.setImageResource(mResIds[position % count]);
        view.setBackgroundResource(mResIds[position % count]);
        view.setLayoutParams(params);
        container.addView(data.get(position));
        return data.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(data.get(position));
    }
}
