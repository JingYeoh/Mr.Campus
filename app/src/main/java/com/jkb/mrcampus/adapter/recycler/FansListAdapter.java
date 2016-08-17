package com.jkb.mrcampus.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.mrcampus.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 粉丝用户列表的适配器
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class FansListAdapter extends RecyclerView.Adapter<FansListAdapter.ViewHolder> {

    private Context context;
    private int colorWhite;
    private int colorGravy;


    public FansListAdapter(Context context) {
        this.context = context;
        colorWhite = context.getResources().getColor(R.color.white);
        colorGravy = context.getResources().getColor(R.color.background_general);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_userslist, parent, false);
        FansListAdapter.ViewHolder holder = new FansListAdapter.ViewHolder(view);
        //初始化控件
        holder.ivHeadImg = (CircleImageView) view.findViewById(R.id.iul_iv_headImg);
        holder.tvAttention = (TextView) view.findViewById(R.id.iul_tv_attentionStatus);
        holder.tvNickName = (TextView) view.findViewById(R.id.iul_tv_nickName);
        holder.tvSign = (TextView) view.findViewById(R.id.iul_tv_sign);
        holder.tvTime = (TextView) view.findViewById(R.id.iul_tv_time);
        holder.contentView = view.findViewById(R.id.iul_ll_content);
        //初始化监听器
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTime.setVisibility(View.GONE);
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
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public TextView tvNickName;//昵称
        public TextView tvTime;//时间
        public TextView tvSign;//签名
        public TextView tvAttention;//是否关注
        public CircleImageView ivHeadImg;//头像
        public View contentView;//包裹的背景
    }
}
