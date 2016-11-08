package com.jkb.mrcampus.adapter.recycler.circle.userInCircle;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.core.data.circle.userInCircle.UserInCircle;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 圈子成员的适配器
 * Created by JustKiddingBaby on 2016/11/3.
 */

public class UsersInCircleAdapter extends RecyclerView.Adapter<UsersInCircleAdapter.ViewHolder>
        implements View.OnClickListener {

    private Context context;
    public List<UserInCircle> usersInCircles;
    public boolean isCircleCreator;

    private int colorWhite;
    private int colorGravy;

    //接口
    private OnUserInCircleItemClickListener onUserInCircleItemClickListener;

    public UsersInCircleAdapter(Context context, List<UserInCircle> usersInCircles) {
        this.context = context;
        if (usersInCircles == null) {
            usersInCircles = new ArrayList<>();
        }
        this.usersInCircles = usersInCircles;

        colorWhite = context.getResources().getColor(R.color.white);
        colorGravy = context.getResources().getColor(R.color.background_general);
    }

    @Override
    public UsersInCircleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        holder = initUserInCircle(parent, inflater);
        return holder;
    }

    private ViewHolder initUserInCircle(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_userslist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件
        holder.ivHeadImg = (CircleImageView) view.findViewById(R.id.iul_iv_headImg);
        holder.tvAttention = (TextView) view.findViewById(R.id.iul_tv_attentionStatus);
        holder.tvNickName = (TextView) view.findViewById(R.id.iul_tv_nickName);
        holder.tvSign = (TextView) view.findViewById(R.id.iul_tv_sign);
        holder.tvTime = (TextView) view.findViewById(R.id.iul_tv_time);
        holder.contentView = view.findViewById(R.id.iul_ll_content);
        holder.contentHeadImg = view.findViewById(R.id.iul_contentHeadImg);

        //初始化监听器
        holder.contentHeadImg.setOnClickListener(this);
        holder.tvAttention.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(UsersInCircleAdapter.ViewHolder holder, int position) {
        //绑定控件的TAG
        ClassUtils.bindViewsTag(position, holder.contentHeadImg, holder.tvAttention);

        //绑定数据
        if (position % 2 == 0) {//偶数
            holder.contentView.setBackgroundColor(colorGravy);
        } else {//奇数
            holder.contentView.setBackgroundColor(colorWhite);
        }
        holder.tvTime.setVisibility(View.VISIBLE);
        UserInCircle userInCircle = usersInCircles.get(position);
        UserInfo userData = userInCircle.getUserInfo();
        holder.tvNickName.setText(userData.getNickName());
        holder.tvSign.setText(userData.getBref_introduction());
        holder.tvTime.setVisibility(View.GONE);
        String headImgUrl = userData.getAvatar();
        if (!StringUtils.isEmpty(headImgUrl)) {
            loadImage(holder.ivHeadImg, headImgUrl);
        } else {
            holder.ivHeadImg.setImageResource(R.drawable.ic_user_head);
        }
        if (!userInCircle.isHasInBlackList()) {
            holder.tvAttention.setBackgroundResource(
                    R.drawable.bg_edittext_maintheme_white_round_content);
            holder.tvAttention.setText("拉黑");
        } else {
            holder.tvAttention.setBackgroundResource(
                    R.drawable.bg_edittext_mainthemegravy_white_round_content);
            holder.tvAttention.setText("已拉黑");
        }
        if (isCircleCreator) {
            holder.tvAttention.setVisibility(View.VISIBLE);
        } else {
            holder.tvAttention.setVisibility(View.GONE);
        }
    }

    /**
     * 加载网络图片
     */
    private void loadImage(CircleImageView ivHeadImg, String headImgUrl) {
        ImageLoaderFactory.getInstance().displayImage(ivHeadImg, headImgUrl);
    }

    @Override
    public int getItemCount() {
        return usersInCircles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        private TextView tvNickName;//昵称
        private TextView tvTime;//时间
        private TextView tvSign;//签名
        private TextView tvAttention;//是否关注
        private CircleImageView ivHeadImg;//头像
        private View contentHeadImg;
        private View contentView;//包裹的背景
    }

    @Override
    public void onClick(View v) {
        if (onUserInCircleItemClickListener == null) {
            return;
        }
        Bundle bundle = (Bundle) v.getTag();
        if (bundle == null) {
            return;
        }
        int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
        int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
        switch (viewId) {
            case R.id.iul_contentHeadImg:
                onUserInCircleItemClickListener.onUserItemClick(position);
                break;
            case R.id.iul_tv_attentionStatus:
                onUserInCircleItemClickListener.onPull$PutBlackList(position);
                break;
        }
    }

    /**
     * 圈子内用户条目点击接口回调
     */
    public interface OnUserInCircleItemClickListener {

        /**
         * 用户条目被点击的时候
         *
         * @param position 条目数
         */
        void onUserItemClick(int position);


        /**
         * 拉黑/取消拉黑的条目被点击的时候
         *
         * @param position 条目数
         */
        void onPull$PutBlackList(int position);
    }

    /**
     * 设置圈子条目的点击监听接口
     */
    public void setOnUserInCircleItemClickListener(
            OnUserInCircleItemClickListener onUserInCircleItemClickListener) {
        this.onUserInCircleItemClickListener = onUserInCircleItemClickListener;
    }
}
