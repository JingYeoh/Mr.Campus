package com.jkb.mrcampus.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.mrcampus.R;

/**
 * 个人中心——我的圈子的适配器
 * Created by JustKiddingBaby on 2016/8/16.
 */

public class PersonCenterCircleAdapter extends RecyclerView.Adapter<PersonCenterCircleAdapter.ViewHolder> {

    private Context context;

    public PersonCenterCircleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person_center_circle, parent, false);
        PersonCenterCircleAdapter.ViewHolder holder = new ViewHolder(view);
        //初始化控件
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //绑定数据
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    /**
     * ViewHolder类
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
