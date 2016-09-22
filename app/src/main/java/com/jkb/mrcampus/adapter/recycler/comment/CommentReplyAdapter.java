package com.jkb.mrcampus.adapter.recycler.comment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.core.contract.dynamicDetail.data.DynamicDetailUserData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentReplyData;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 回复的数据适配器
 * Created by JustKiddingBaby on 2016/9/20.
 */

public class CommentReplyAdapter extends RecyclerView.Adapter<CommentReplyAdapter.ViewHolder> {

    private Context context;
    public List<DynamicDetailCommentReplyData> replyDataList;
    public int parentPosition;//父布局的条目数

    //回调
    private OnReplyUserClickListener onReplyUserClickListener;
    private OnTargetReplyUserClickListener onTargetReplyUserClickListener;
    private OnReplyReplyCommentClickListener onReplyReplyCommentClickListener;

    public CommentReplyAdapter(Context context, List<DynamicDetailCommentReplyData> replyDataList) {
        this.context = context;
        if (replyDataList == null) {
            replyDataList = new ArrayList<>();
        }
        this.replyDataList = replyDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment_apply, parent, false);
        CommentReplyAdapter.ViewHolder holder = new ViewHolder(view);
        initView(holder, view);
        return holder;
    }

    /**
     * 初始化View视图
     */
    private void initView(ViewHolder holder, View view) {
        holder.tvApplyName = (TextView) view.findViewById(R.id.ica_tv_applyName);
        holder.tvContent = (TextView) view.findViewById(R.id.ica_tv_content);
        holder.tvTargetApplyName = (TextView) view.findViewById(R.id.ica_tv_targetApplyName);
        holder.tvReply = view.findViewById(R.id.ica_tv_apply);

        //设置点击事件
        holder.tvApplyName.setOnClickListener(clickReplyUserListener);
        holder.tvContent.setOnClickListener(clickReplyReplyCommentListener);
        holder.tvTargetApplyName.setOnClickListener(clickTargetReplyUserListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DynamicDetailCommentReplyData reply = replyDataList.get(position);

        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.tvApplyName,
                holder.tvTargetApplyName,
                holder.tvContent);

        //内容
        holder.tvContent.setText(reply.getComment());
        //回复者
        DynamicDetailUserData reply_user = reply.getReply_user();
        if (reply_user == null) {
            holder.tvApplyName.setVisibility(View.GONE);
            holder.tvReply.setVisibility(View.GONE);
            holder.tvTargetApplyName.setVisibility(View.GONE);
        } else {
            holder.tvApplyName.setVisibility(View.VISIBLE);
            holder.tvApplyName.setText(reply_user.getNickName());
        }
        //目标
        DynamicDetailUserData reply_target_user = reply.getReply_target_user();
        if (reply_target_user == null) {
            holder.tvReply.setVisibility(View.GONE);
            holder.tvTargetApplyName.setVisibility(View.GONE);
        } else {
            holder.tvTargetApplyName.setVisibility(View.VISIBLE);
            holder.tvTargetApplyName.setText(reply_target_user.getNickName());
        }
    }

    @Override
    public int getItemCount() {
        return replyDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        TextView tvContent;
        TextView tvApplyName;
        TextView tvTargetApplyName;
        View tvReply;
    }

    /**
     * 回复用户的点击接口
     */
    public interface OnReplyUserClickListener {
        /**
         * 回复的用户的点击回调方法
         *
         * @param parentPosition 父布局的条目F
         * @param position       条目
         */
        void onReplyUserClick(int parentPosition, int position);
    }

    /**
     * 目标回复用户的点击接口
     */
    public interface OnTargetReplyUserClickListener {
        /**
         * 回复的用户的点击回调方法
         *
         * @param parentPosition 父布局的条目F
         * @param position       条目
         */
        void onTargetReplyUserClick(int parentPosition, int position);
    }

    /**
     * 回复复内容的点击接口
     */
    public interface OnReplyReplyCommentClickListener {
        /**
         * 回复回复内容的点击回调方法
         *
         * @param parentPosition 父布局的条目F
         * @param position       条目
         */
        void onReplyReplyCommentClick(int parentPosition, int position);
    }

    /**
     * 设置回复的用户点击接口
     */
    public void setOnReplyUserClickListener(OnReplyUserClickListener onReplyUserClickListener) {
        this.onReplyUserClickListener = onReplyUserClickListener;
    }

    /**
     * 设置回复的回复评论点击接口
     */
    public void setOnReplyReplyCommentClickListener(
            OnReplyReplyCommentClickListener onReplyReplyCommentClickListener) {
        this.onReplyReplyCommentClickListener = onReplyReplyCommentClickListener;
    }

    /**
     * 设置回复目标的用户点击接口
     */
    public void setOnTargetReplyUserClickListener(
            OnTargetReplyUserClickListener onTargetReplyUserClickListener) {
        this.onTargetReplyUserClickListener = onTargetReplyUserClickListener;
    }

    private View.OnClickListener clickReplyUserListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onReplyUserClickListener != null) {
                //判断是哪个控件
                Bundle bundle = (Bundle) view.getTag();
                if (bundle == null) {
                    return;
                }
                int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
                int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
                switch (viewId) {
                    case R.id.ica_tv_applyName:
                        onReplyUserClickListener.onReplyUserClick(parentPosition, position);
                        break;
                }
            }
        }
    };

    private View.OnClickListener clickTargetReplyUserListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onTargetReplyUserClickListener != null) {
                //判断是哪个控件
                Bundle bundle = (Bundle) view.getTag();
                if (bundle == null) {
                    return;
                }
                int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
                int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
                switch (viewId) {
                    case R.id.ica_tv_targetApplyName:
                        onTargetReplyUserClickListener.onTargetReplyUserClick(parentPosition, position);
                        break;
                }
            }
        }
    };

    private View.OnClickListener clickReplyReplyCommentListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onReplyReplyCommentClickListener != null) {
                //判断是哪个控件
                Bundle bundle = (Bundle) view.getTag();
                if (bundle == null) {
                    return;
                }
                int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
                int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
                switch (viewId) {
                    case R.id.ica_tv_content:
                        onReplyReplyCommentClickListener.onReplyReplyCommentClick(
                                parentPosition, position);
                        break;
                }
            }
        }
    };
}
