package com.jkb.mrcampus.adapter.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.presenter.usersList.data.UserData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 访客用户列表的适配器
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class VisitorListAdapter extends RecyclerView.Adapter<VisitorListAdapter.ViewHolder>
        implements View.OnClickListener {

    private Context context;
    private int colorWhite;
    private int colorGravy;
    public List<UserData> userDatas;
    private OnUserListItemsClickListener onUserListItemsClickListener;

    /**
     * 设置条目的子控件的点击监听
     */
    public void setOnUserListItemsClickListener(
            OnUserListItemsClickListener onUserListItemsClickListener) {
        this.onUserListItemsClickListener = onUserListItemsClickListener;
    }

    @Override
    public void onClick(View view) {
        if (onUserListItemsClickListener != null) {
            //判断是哪个控件
            Bundle bundle = (Bundle) view.getTag();
            if (bundle == null) {
                return;
            }
            int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
            int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
            switch (viewId) {
                case R.id.iul_iv_headImg:
                    onUserListItemsClickListener.onHeadImgClick(position);
                    break;
                case R.id.iul_tv_attentionStatus:
                    onUserListItemsClickListener.onAttentionClick(position);
                    break;
            }
        }
    }

    /**
     * 用户列表子控件的点击监听接口
     */
    public interface OnUserListItemsClickListener {
        /**
         * 点击头像回调
         */
        void onHeadImgClick(int position);

        /**
         * 点击关注回调
         */
        void onAttentionClick(int position);
    }

    public VisitorListAdapter(Context context, List<UserData> userDatas) {
        this.context = context;
        colorWhite = context.getResources().getColor(R.color.white);
        colorGravy = context.getResources().getColor(R.color.background_general);

        if (userDatas == null) {
            userDatas = new ArrayList<>();
        }
        this.userDatas = userDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_userslist, parent, false);
        VisitorListAdapter.ViewHolder holder = new VisitorListAdapter.ViewHolder(view);
        //初始化控件
        holder.ivHeadImg = (CircleImageView) view.findViewById(R.id.iul_iv_headImg);
        holder.tvAttention = (TextView) view.findViewById(R.id.iul_tv_attentionStatus);
        holder.tvNickName = (TextView) view.findViewById(R.id.iul_tv_nickName);
        holder.tvSign = (TextView) view.findViewById(R.id.iul_tv_sign);
        holder.tvTime = (TextView) view.findViewById(R.id.iul_tv_time);
        holder.contentView = view.findViewById(R.id.iul_ll_content);

        //初始化监听器
        holder.ivHeadImg.setOnClickListener(this);
        holder.tvAttention.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //绑定控件的TAG
        ClassUtils.bindViewsTag(position, holder.ivHeadImg, holder.tvAttention);

        //绑定数据
        if (position % 2 == 0) {//偶数
            holder.contentView.setBackgroundColor(colorGravy);
        } else {//奇数
            holder.contentView.setBackgroundColor(colorWhite);
        }
        holder.tvTime.setVisibility(View.VISIBLE);
        UserData userData = userDatas.get(position);
        holder.tvNickName.setText(userData.getNickname());
        holder.tvSign.setText(userData.getBref_introduction());
        holder.tvTime.setText(userData.getTime());
        String headImgUrl = userData.getAvatar();
        if (!StringUtils.isEmpty(headImgUrl)) {
            setImageLoad(holder.ivHeadImg, headImgUrl);
        } else {
            holder.ivHeadImg.setImageResource(R.drawable.ic_user_head);
        }
        //设置头像
        if (!userData.isAttentioned()) {
            holder.tvAttention.setBackgroundResource(
                    R.drawable.bg_edittext_maintheme_white_round_content);
            holder.tvAttention.setText("关注");
        } else {
            holder.tvAttention.setBackgroundResource(
                    R.drawable.bg_edittext_mainthemegravy_white_round_content);
            holder.tvAttention.setText("已关注");
        }
    }

    /**
     * 加载头像
     */
    private void setImageLoad(final ImageView tvHeadImg, String headImgUrl) {
        ImageLoaderFactory.getInstance().loadImage(headImgUrl, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                tvHeadImg.setImageResource(R.drawable.ic_user_head);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                tvHeadImg.setImageResource(R.drawable.ic_user_head);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                tvHeadImg.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                tvHeadImg.setImageResource(R.drawable.ic_user_head);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userDatas.size();
    }


    /**
     * ViewHolder类
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }

        private TextView tvNickName;//昵称
        private TextView tvTime;//时间
        private TextView tvSign;//签名
        private TextView tvAttention;//是否关注
        private CircleImageView ivHeadImg;//头像
        private View contentView;//包裹的背景
    }
}
