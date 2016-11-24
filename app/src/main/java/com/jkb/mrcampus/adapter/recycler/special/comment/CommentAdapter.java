package com.jkb.mrcampus.adapter.recycler.special.comment;

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
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentReplyData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论的数据适配器
 * Created by JustKiddingBaby on 2016/11/24.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>
        implements View.OnClickListener {

    private Context context;
    public List<DynamicDetailCommentData> commentDataList;

    private OnCommentItemClickListener onCommentItemClickListener;

    public CommentAdapter(Context context, List<DynamicDetailCommentData> commentDataList) {
        this.context = context;
        if (commentDataList == null) {
            commentDataList = new ArrayList<>();
        }
        this.commentDataList = commentDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
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
        holder.contentHeadImg = view.findViewById(R.id.icoml_contentHeadImg);


        if (holder.commentReplyAdapter == null) {
            holder.commentReplyAdapter = new CommentReplyAdapter(context, null);
            holder.recyclerView.setAdapter(holder.commentReplyAdapter);
        }

        //设置监听事件
        holder.ivFavorite.setOnClickListener(this);
        holder.contentHeadImg.setOnClickListener(this);
        holder.viewAllComment.setOnClickListener(this);
        holder.tvContent.setOnClickListener(this);
        //设置回复的监听事件
        holder.commentReplyAdapter.setOnCommentReplyItemClickListener(
                onCommentReplyItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DynamicDetailCommentData commentData = commentDataList.get(position);
        if (commentData == null) {
            commentDataList.remove(position);
            return;
        }
        //设置Tag
        ClassUtils.bindViewsTag(position,
                holder.ivFavorite,
                holder.contentHeadImg,
                holder.viewAllComment,
                holder.tvContent);

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


    class ViewHolder extends RecyclerView.ViewHolder {

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
        View contentHeadImg;
    }

    /**
     * 评论的条目点击事件
     */
    public interface OnCommentItemClickListener {
        /**
         * 点赞
         */
        void onLikeItemClick(int commentPosition);

        /**
         * 评论的用户
         */
        void onCommentUserClick(int commentPosition);

        /**
         * 回复的内容
         */
        void onCommentContentItemClick(int commentPosition);

        /**
         * 查看全部评论
         */
        void onCommentViewAllItemClick(int commentPosition);

        /**
         * 回复的目标用户条目被点击
         */
        void onReplyTargetUserItemClick(int commentItem, int replyPosition);

        /**
         * 评论条目被点击
         */
        void onReplyCommentItemClick(int commentItem, int replyPosition);

        /**
         * 发送的回复的用户条目被点击
         */
        void onReplySenderUserItemClick(int commentItem, int replyPosition);
    }

    public void setOnCommentItemClickListener(
            OnCommentItemClickListener onCommentItemClickListener) {
        this.onCommentItemClickListener = onCommentItemClickListener;
    }

    /**
     * 回复的点击事件监听
     */
    private CommentReplyAdapter.OnCommentReplyItemClickListener onCommentReplyItemClickListener =
            new CommentReplyAdapter.OnCommentReplyItemClickListener() {
                @Override
                public void onTargetUserItemClick(int commentItem, int replyPosition) {
                    if (onCommentItemClickListener != null) {
                        onCommentItemClickListener.onReplyTargetUserItemClick(commentItem, replyPosition);
                    }
                }

                @Override
                public void onCommentItemClick(int commentItem, int replyPosition) {
                    if (onCommentItemClickListener != null) {
                        onCommentItemClickListener.onReplyCommentItemClick(commentItem, replyPosition);
                    }
                }

                @Override
                public void onSenderUserItemClick(int commentItem, int replyPosition) {
                    if (onCommentItemClickListener != null) {
                        onCommentItemClickListener.onReplySenderUserItemClick(commentItem, replyPosition);
                    }
                }
            };

    @Override
    public void onClick(View v) {
        if (onCommentItemClickListener == null) {
            return;
        }
        Bundle bundle = (Bundle) v.getTag();
        if (bundle == null) {
            return;
        }
        int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
        int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
        switch (viewId) {
            case R.id.icoml_contentHeadImg:
                onCommentItemClickListener.onCommentUserClick(position);
                break;
            case R.id.icoml_iv_praise://喜欢
                onCommentItemClickListener.onLikeItemClick(position);
                break;
            case R.id.icoml_ll_showAllComment:
                onCommentItemClickListener.onCommentViewAllItemClick(position);
                break;
            case R.id.icoml_tv_commentContent:
                onCommentItemClickListener.onCommentContentItemClick(position);
                break;
        }
    }
}
