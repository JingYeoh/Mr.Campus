package com.jkb.mrcampus.adapter.recycler.special.comment;

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
 * Created by JustKiddingBaby on 2016/11/24.
 */

public class CommentReplyAdapter extends RecyclerView.Adapter<CommentReplyAdapter.ViewHolder>
        implements View.OnClickListener {

    private Context context;
    public List<DynamicDetailCommentReplyData> replyDataList;
    public int parentPosition;//父布局的条目数

    //监听器
    private OnCommentReplyItemClickListener onCommentReplyItemClickListener;

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
        ViewHolder holder = new ViewHolder(view);
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
        holder.tvApplyName.setOnClickListener(this);
        holder.tvContent.setOnClickListener(this);
        holder.tvTargetApplyName.setOnClickListener(this);
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

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        TextView tvContent;
        TextView tvApplyName;
        TextView tvTargetApplyName;
        View tvReply;
    }

    /**
     * 回复评论条目点击事件
     */
    public interface OnCommentReplyItemClickListener {

        /**
         * 回复的目标用户条目被点击
         */
        void onTargetUserItemClick(int commentItem, int replyPosition);

        /**
         * 评论条目被点击
         */
        void onCommentItemClick(int commentItem, int replyPosition);

        /**
         * 发送的回复的用户条目被点击
         */
        void onSenderUserItemClick(int commentItem, int replyPosition);
    }

    public void setOnCommentReplyItemClickListener(
            OnCommentReplyItemClickListener onCommentReplyItemClickListener) {
        this.onCommentReplyItemClickListener = onCommentReplyItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onCommentReplyItemClickListener == null) {
            return;
        }
        //判断是哪个控件
        Bundle bundle = (Bundle) v.getTag();
        if (bundle == null) {
            return;
        }
        int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
        int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
        switch (viewId) {
            case R.id.ica_tv_applyName:
                onCommentReplyItemClickListener.onSenderUserItemClick(parentPosition, position);
                break;
            case R.id.ica_tv_targetApplyName:
                onCommentReplyItemClickListener.onTargetUserItemClick(parentPosition, position);
                break;
            case R.id.ica_tv_content:
                onCommentReplyItemClickListener.onCommentItemClick(parentPosition, position);
                break;
        }
    }
}
