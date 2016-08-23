package com.jkb.mrcampus.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.mrcampus.R;

/**
 * 动态的适配器
 * Created by JustKiddingBaby on 2016/8/23.
 */

public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.ViewHolder> {

    private Context context;
    private int colorWhite;
    private int colorGravy;

    public DynamicAdapter(Context context) {
        this.context = context;

        colorWhite = context.getResources().getColor(R.color.white);
        colorGravy = context.getResources().getColor(R.color.background_general);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic_orginal_normal,
                parent, false);
        DynamicAdapter.ViewHolder holder = new DynamicAdapter.ViewHolder(view);

        //初始化id
        holder.contentView = view.findViewById(R.id.idon_content);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //绑定数据
        if (position % 2 == 0) {//偶数
            holder.contentView.setBackgroundColor(colorGravy);
        } else {//奇数
            holder.contentView.setBackgroundColor(colorWhite);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    /**
     * ViewHolder类
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }

        View contentView;
    }
}
