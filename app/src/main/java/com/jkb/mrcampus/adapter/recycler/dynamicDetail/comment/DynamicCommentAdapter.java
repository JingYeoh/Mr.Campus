package com.jkb.mrcampus.adapter.recycler.dynamicDetail.comment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.dynamicDetail.data.DynamicDetailUserData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态评论列表的适配器
 * Created by JustKiddingBaby on 2016/9/18.
 */

public class DynamicCommentAdapter extends RecyclerView.Adapter<DynamicCommentAdapter.ViewHolder> {

    private Context context;
    public List<DynamicDetailCommentData> commentsData;
    private OnLikeClickListener onLikeClickListener;
    private OnHeadImgClickListener onHeadImgClickListener;

    public DynamicCommentAdapter(Context context, List<DynamicDetailCommentData> commentsData) {
        this.context = context;
        if (commentsData == null) {
            commentsData = new ArrayList<>();
        }
        this.commentsData = commentsData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.item_dynamic_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        //初始化控件
        initCommentId(viewHolder, view);
        return viewHolder;
    }

    /**
     * 初始化控件id
     */
    private void initCommentId(ViewHolder holder, View view) {
        holder.tvLikeCount = (TextView) view.findViewById(R.id.idc_tv_praiseCount);
        holder.tvNickName = (TextView) view.findViewById(R.id.idc_tv_name);
        holder.tvTime = (TextView) view.findViewById(R.id.idc_tv_time);
        holder.tvContent = (TextView) view.findViewById(R.id.idc_tv_commentContent);
        holder.ivHeadImg = (ImageView) view.findViewById(R.id.idc_iv_headImg);
        holder.ivFavorite = (ImageView) view.findViewById(R.id.idc_iv_praise);
        holder.contentHeadImg = view.findViewById(R.id.idc_contentHeadImg);

        //设置监听事件
        holder.ivFavorite.setOnClickListener(clickLikeListener);
        holder.contentHeadImg.setOnClickListener(clickHeadImgListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DynamicDetailCommentData commentData = commentsData.get(position);
        if (commentData == null) {
            commentsData.remove(position);
            return;
        }
        //设置Tag
        ClassUtils.bindViewsTag(position, holder.ivFavorite, holder.contentHeadImg);

        holder.tvContent.setText(commentData.getComment());
        holder.tvTime.setText(commentData.getComment_time());
        //设置评论数
        holder.tvLikeCount.setText(commentData.getLikeCount() + "");
        if (commentData.isHas_like()) {
            holder.ivFavorite.setImageResource(R.drawable.ic_praise_yellow);
        } else {
            holder.ivFavorite.setImageResource(R.drawable.ic_praise_gray);
        }
        //设置用户
        DynamicDetailUserData comment_user = commentData.getComment_user();
        if (comment_user != null) {
            holder.tvNickName.setText(comment_user.getNickName());
            String avatar = comment_user.getAvatar();
            loadImage(holder.ivHeadImg, avatar);
        }
    }

    /**
     * 加載圖片
     */
    private void loadImage(ImageView imageView, String imageUrl) {
        ImageLoaderFactory.getInstance().displayImage(imageView, imageUrl);
    }

    @Override
    public int getItemCount() {
        if (commentsData.size() <= 3) {
            return commentsData.size();
        } else {
            return 3;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        TextView tvNickName;
        TextView tvLikeCount;
        TextView tvTime;
        TextView tvContent;
        ImageView ivHeadImg;
        ImageView ivFavorite;
        View contentHeadImg;
    }

    /**
     * 喜欢的回调接口
     */
    public interface OnLikeClickListener {
        /**
         * 点击喜欢的回调方法
         *
         * @param position 条目
         */
        void onLikeClick(int position);
    }

    /**
     * 点击头像的回调接口
     */
    public interface OnHeadImgClickListener {
        /**
         * 点击头像的回调方法
         *
         * @param position 条目
         */
        void onHeadImgClick(int position);
    }

    /**
     * 设置喜欢的点击事件接口
     */
    public void setOnLikeClickListener(OnLikeClickListener onLikeClickListener) {
        this.onLikeClickListener = onLikeClickListener;
    }

    /**
     * 设置头像的点击事件接口
     */
    public void setOnHeadImgClickListener(OnHeadImgClickListener onHeadImgClickListener) {
        this.onHeadImgClickListener = onHeadImgClickListener;
    }

    /**
     * 喜欢的点击事件监听
     */
    private View.OnClickListener clickLikeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onLikeClickListener != null) {
                //判断是哪个控件
                Bundle bundle = (Bundle) view.getTag();
                if (bundle == null) {
                    return;
                }
                int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
                int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
                switch (viewId) {
                    case R.id.idc_iv_praise:
                        onLikeClickListener.onLikeClick(position);
                        break;
                }
            }
        }
    };
    /**
     * 头像的点击事件监听
     */
    private View.OnClickListener clickHeadImgListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onHeadImgClickListener != null) {
                //判断是哪个控件
                Bundle bundle = (Bundle) view.getTag();
                if (bundle == null) {
                    return;
                }
                int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
                int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
                switch (viewId) {
                    case R.id.idc_contentHeadImg:
                        onHeadImgClickListener.onHeadImgClick(position);
                        break;
                }
            }
        }
    };
}
