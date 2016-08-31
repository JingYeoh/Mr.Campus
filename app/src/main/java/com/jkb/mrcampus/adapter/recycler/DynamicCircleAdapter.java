package com.jkb.mrcampus.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.mrcampus.R;

import java.util.Random;

/**
 * 圈子动态的适配器
 * Created by JustKiddingBaby on 2016/8/31.
 */

public class DynamicCircleAdapter extends RecyclerView.Adapter<DynamicCircleAdapter.ViewHolder> {

    private Context context;
    private Object object;
    private Random random;

    //常量
    private static final int DYNAMIC_TYPE_LEFT = 1000;
    private static final int DYNAMIC_TYPE_RIGHT = 10002;

    private static final String TAG = "DynamicCircleAdapter";

    public DynamicCircleAdapter(Context context, Object object) {
        this.context = context;
        this.object = object;

        random = new Random();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(context);
        Log.d(TAG, "viewType=" + viewType);
        switch (viewType) {
            case DYNAMIC_TYPE_LEFT:
                view = inflater.inflate(R.layout.item_dynamic_circle_left, parent, false);
                break;
            case DYNAMIC_TYPE_RIGHT:
                view = inflater.inflate(R.layout.item_dynamic_circle_right, parent, false);
                break;
        }
        ViewHolder viewHolder = new ViewHolder(view);
        //初始化id
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        //随机左右设置布局
        int type = random.nextInt();
        if (type % 2 == 0) {
            return DYNAMIC_TYPE_LEFT;
        } else {
            return DYNAMIC_TYPE_RIGHT;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置内容
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }
}
