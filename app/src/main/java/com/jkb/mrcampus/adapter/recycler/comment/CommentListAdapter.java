package com.jkb.mrcampus.adapter.recycler.comment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.dynamicDetail.data.DynamicDetailUserData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentReplyData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论列表的数据适配器
 * Created by JustKiddingBaby on 2016/9/20.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    private Context context;
    public List<DynamicDetailCommentData> commentDataList;

    private OnLikeClickListener onLikeClickListener;
    private OnHeadImgClickListener onHeadImgClickListener;
    private CommentReplyAdapter.OnReplyUserClickListener onReplyUserClickListener;
    private CommentReplyAdapter.OnTargetReplyUserClickListener onTargetReplyUserClickListener;
    private OnViewAllCommentClickListener onViewAllCommentClickListener;

    public CommentListAdapter(Context context, List<DynamicDetailCommentData> commentDataList) {
        this.context = context;
        if (commentDataList == null) {
            commentDataList = new ArrayList<>();
        }
        this.commentDataList = commentDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment_list, parent, false);
        CommentListAdapter.ViewHolder holder = new CommentListAdapter.ViewHolder(view);
        initView(view, holder);
        return holder;
    }

    /**
     * 初始化数据视图
     */
    private void initView(View view, ViewHolder holder) {
        holder.replyContent = view.findViewById(R.id.icoml_applyContent);
        holder.recyclerView = (RecyclerView) view.findViewById(R.id.icoml_rv_comment);
        holder.linearLayoutManager = new LinearLayoutManager(context);
        holder.linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(holder.linearLayoutManager);

        holder.viewAllComment = view.findViewById(R.id.icoml_ll_showAllComment);
        holder.tvreplyCount = (TextView) view.findViewById(R.id.icoml_tv_applyCount);
        //其他
        holder.tvLikeCount = (TextView) view.findViewById(R.id.icoml_tv_praiseCount);
        holder.tvNickName = (TextView) view.findViewById(R.id.icoml_tv_name);
        holder.tvTime = (TextView) view.findViewById(R.id.icoml_tv_time);
        holder.tvContent = (TextView) view.findViewById(R.id.icoml_tv_commentContent);
        holder.ivHeadImg = (ImageView) view.findViewById(R.id.icoml_iv_headImg);
        holder.ivFavorite = (ImageView) view.findViewById(R.id.icoml_iv_praise);


        if (holder.commentReplyAdapter == null) {
            holder.commentReplyAdapter = new CommentReplyAdapter(context, null);
            holder.recyclerView.setAdapter(holder.commentReplyAdapter);
        }

        //设置监听事件
        holder.ivFavorite.setOnClickListener(clickLikeListener);
        holder.ivHeadImg.setOnClickListener(clickHeadImgListener);
        holder.viewAllComment.setOnClickListener(clickViewAllCommentListener);
        //设置回复的监听事件
        holder.commentReplyAdapter.setOnReplyUserClickListener(onReplyUserClickListener);
        holder.commentReplyAdapter.setOnTargetReplyUserClickListener(onTargetReplyUserClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DynamicDetailCommentData commentData = commentDataList.get(position);
        if (commentData == null) {
            commentDataList.remove(position);
            return;
        }
        //设置Tag
        ClassUtils.bindViewsTag(position, holder.ivFavorite, holder.ivHeadImg,
                holder.viewAllComment);

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

        //设置回复内容
        List<DynamicDetailCommentReplyData> replyDatas = commentData.getReplyDatas();
        if (replyDatas == null || replyDatas.size() <= 0) {
            holder.replyContent.setVisibility(View.GONE);
        } else {
            holder.replyContent.setVisibility(View.VISIBLE);
            if (commentData.getReplyCount() > 3) {
                holder.viewAllComment.setVisibility(View.VISIBLE);
                holder.tvreplyCount.setText(commentData.getReplyCount() + "");
            } else {
                holder.viewAllComment.setVisibility(View.GONE);
            }
            //设置回复数据
            holder.commentReplyAdapter.replyDataList = replyDatas;
            holder.commentReplyAdapter.parentPosition = position;
            holder.commentReplyAdapter.notifyDataSetChanged();
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
        return commentDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        View replyContent;
        RecyclerView recyclerView;
        CommentReplyAdapter commentReplyAdapter;
        LinearLayoutManager linearLayoutManager;
        View viewAllComment;
        TextView tvreplyCount;
        //其他数据
        TextView tvNickName;
        TextView tvLikeCount;
        TextView tvTime;
        TextView tvContent;
        ImageView ivHeadImg;
        ImageView ivFavorite;
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
     * 查看所有评论的点击回调方法
     */
    public interface OnViewAllCommentClickListener {

        /**
         * 点击查看所有评论的调用方法
         *
         * @param position 条目
         */
        void OnViewAllCommentClick(int position);
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
     * 设置回复的用户点击接口
     */
    public void setOnReplyUserClickListener(
            CommentReplyAdapter.OnReplyUserClickListener onReplyUserClickListener) {
        this.onReplyUserClickListener = onReplyUserClickListener;
    }

    /**
     * 设置回复目标的用户点击接口
     */
    public void setOnTargetReplyUserClickListener(
            CommentReplyAdapter.OnTargetReplyUserClickListener onTargetReplyUserClickListener) {
        this.onTargetReplyUserClickListener = onTargetReplyUserClickListener;
    }

    /**
     * 设置查看所有的评论的点击回调接口
     */
    public void setOnViewAllCommentClickListener(
            OnViewAllCommentClickListener onViewAllCommentClickListener) {
        this.onViewAllCommentClickListener = onViewAllCommentClickListener;
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
                    case R.id.icoml_iv_praise:
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
                    case R.id.icoml_iv_headImg:
                        onHeadImgClickListener.onHeadImgClick(position);
                        break;
                }
            }
        }
    };

    /**
     * 喜欢的点击事件监听
     */
    private View.OnClickListener clickViewAllCommentListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onViewAllCommentClickListener != null) {
                //判断是哪个控件
                Bundle bundle = (Bundle) view.getTag();
                if (bundle == null) {
                    return;
                }
                int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
                int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
                switch (viewId) {
                    case R.id.icoml_ll_showAllComment:
                        onViewAllCommentClickListener.OnViewAllCommentClick(position);
                        break;
                }
            }
        }
    };

}
