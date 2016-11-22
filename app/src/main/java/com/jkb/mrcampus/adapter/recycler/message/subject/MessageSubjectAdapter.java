package com.jkb.mrcampus.adapter.recycler.message.subject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.api.config.Config;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.Messages;

/**
 * 专题消息的数据适配器
 * Created by JustKiddingBaby on 2016/11/21.
 */

public class MessageSubjectAdapter extends RecyclerView.Adapter<MessageSubjectAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    public List<Messages> subjectMessages;

    //常量
    private static final int SUBJECT_TYPE_LIKE = 1001;
    private static final int SUBJECT_TYPE_FAVORITE = 1002;
    private static final int SUBJECT_TYPE_COMMENT = 1003;

    //回调
    private OnSubjectItemClickListener onSubjectItemClickListener;

    public MessageSubjectAdapter(Context context, List<Messages> subjectMessages) {
        this.context = context;
        if (subjectMessages == null) {
            subjectMessages = new ArrayList<>();
        }
        this.subjectMessages = subjectMessages;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        Messages messages = subjectMessages.get(position);
        String action = messages.getAction();
        switch (action) {
            case Config.MESSAGE_ACTION_SUBJECT_FAVORITE:
                viewType = SUBJECT_TYPE_FAVORITE;
                break;
            case Config.MESSAGE_ACTION_SUBJECT_LIKE:
                viewType = SUBJECT_TYPE_LIKE;
                break;
            case Config.MESSAGE_ACTION_SUBJECT_MAKECOMMENT:
            case Config.MESSAGE_ACTION_SUBJECT_MAKEREPLY:
                viewType = SUBJECT_TYPE_COMMENT;
                break;
            default:
                viewType = -1;
                break;
        }
        return viewType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder = null;
        switch (viewType) {
            case SUBJECT_TYPE_COMMENT:
                holder = initCommentSubjectView(inflater, parent);
                break;
            case SUBJECT_TYPE_FAVORITE:
                holder = initFavoriteSubjectView(inflater, parent);
                break;
            case SUBJECT_TYPE_LIKE:
                holder = initLikeSubjectView(inflater, parent);
                break;
            default:
                holder = initSubjectBlankView(inflater, parent);
                break;
        }
        holder.viewType = viewType;
        return holder;
    }

    /**
     * 初始化空白的页面视图
     */
    private ViewHolder initSubjectBlankView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_message_blank, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.viewType = -1;
        holder.contentView = view.findViewById(R.id.messageContent);
        return holder;
    }

    /**
     * 初始化点赞的视图
     */
    private ViewHolder initLikeSubjectView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_message_subject_like,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.messageContent);
        holder.statusIsRead = view.findViewById(R.id.message_status_read);
        if (holder.subjectLike == null) {
            holder.subjectLike = new SubjectLike();
        }
        //初始化id
        holder.subjectLike.tvSenderName = (TextView)
                view.findViewById(R.id.imsl_tv_senderNickName);
        holder.subjectLike.contentHeadImg = view.findViewById(R.id.imsl_contentHeadImg);
        holder.subjectLike.ivSenderHeadImg = (ImageView)
                view.findViewById(R.id.imsl_iv_sendUserHeadImg);
        holder.subjectLike.tvComment = (TextView)
                view.findViewById(R.id.imsl_tv_comment);
        holder.subjectLike.tvTime = (TextView)
                view.findViewById(R.id.imsl_tv_time);
        holder.subjectLike.ivTargetPicture = (ImageView)
                view.findViewById(R.id.imsl_iv_targetPicture);
        holder.subjectLike.tvTargetText = (TextView)
                view.findViewById(R.id.imsl_tv_targetText);
        //初始化监听事件
        holder.contentView.setOnClickListener(this);
        holder.subjectLike.tvSenderName.setOnClickListener(this);
        holder.subjectLike.contentHeadImg.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化评论及回复的视图
     */
    private ViewHolder initCommentSubjectView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_message_subject_comment,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.messageContent);
        holder.statusIsRead = view.findViewById(R.id.message_status_read);
        if (holder.subjectComment == null) {
            holder.subjectComment = new SubjectComment();
        }
        //初始化id
        holder.subjectComment.tvSenderName = (TextView)
                view.findViewById(R.id.imsc_tv_senderNickName);
        holder.subjectComment.contentHeadImg = view.findViewById(R.id.imsc_contentHeadImg);
        holder.subjectComment.ivSenderHeadImg = (ImageView)
                view.findViewById(R.id.imsc_iv_sendUserHeadImg);
        holder.subjectComment.tvComment = (TextView)
                view.findViewById(R.id.imsc_tv_comment);
        holder.subjectComment.tvTime = (TextView)
                view.findViewById(R.id.imsc_tv_time);
        holder.subjectComment.ivTargetPicture = (ImageView)
                view.findViewById(R.id.imsc_iv_targetPicture);
        holder.subjectComment.tvTargetText = (TextView)
                view.findViewById(R.id.imsc_tv_targetText);
        //初始化监听事件
        holder.contentView.setOnClickListener(this);
        holder.subjectComment.tvSenderName.setOnClickListener(this);
        holder.subjectComment.contentHeadImg.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化喜欢的视图
     */
    private ViewHolder initFavoriteSubjectView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_message_subject_favorite,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.messageContent);
        holder.statusIsRead = view.findViewById(R.id.message_status_read);
        if (holder.subjectFavorite == null) {
            holder.subjectFavorite = new SubjectFavorite();
        }
        //初始化id
        holder.subjectFavorite.tvSenderName = (TextView)
                view.findViewById(R.id.imsf_tv_senderNickName);
        holder.subjectFavorite.contentHeadImg = view.findViewById(R.id.imsf_contentHeadImg);
        holder.subjectFavorite.ivSenderHeadImg = (ImageView)
                view.findViewById(R.id.imsf_iv_sendUserHeadImg);
        holder.subjectFavorite.tvTime = (TextView)
                view.findViewById(R.id.imsf_tv_time);
        holder.subjectFavorite.ivTargetPicture = (ImageView)
                view.findViewById(R.id.imsf_iv_targetPicture);
        holder.subjectFavorite.tvTargetText = (TextView)
                view.findViewById(R.id.imsf_tv_targetText);
        //初始化监听事件
        holder.contentView.setOnClickListener(this);
        holder.subjectFavorite.tvSenderName.setOnClickListener(this);
        holder.subjectFavorite.contentHeadImg.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = holder.viewType;
        switch (viewType) {
            case SUBJECT_TYPE_COMMENT:
                bindSubjectCommentData(holder, position);
                break;
            case SUBJECT_TYPE_FAVORITE:
                bindSubjectFavoriteData(holder, position);
                break;
            case SUBJECT_TYPE_LIKE:
                bindSubjectLikeData(holder, position);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化点赞的页面数据
     */
    private void bindSubjectLikeData(ViewHolder holder, int position) {
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.subjectLike.contentHeadImg,
                holder.subjectLike.tvSenderName);
        Messages messages = subjectMessages.get(position);
        if (messages == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        if (messages.getIs_read()) {
            holder.statusIsRead.setVisibility(View.GONE);
        } else {
            holder.statusIsRead.setVisibility(View.VISIBLE);
        }
        holder.subjectLike.tvSenderName.setText(messages.getSenderName());
        holder.subjectLike.tvTime.setText(
                StringUtils.changeDateToString(messages.getUpdated_at()));
        String senderPicture = messages.getSenderPicture();
        if (StringUtils.isEmpty(senderPicture)) {
            holder.subjectLike.ivSenderHeadImg.setImageResource(R.drawable.ic_user_head);
        } else {
            loadImageByUrl(holder.subjectLike.ivSenderHeadImg, senderPicture);
        }
        holder.subjectLike.tvComment.setText(messages.getRelationContent());
        String targetPicture = messages.getTargetPicture();
        if (StringUtils.isEmpty(targetPicture)) {
            holder.subjectLike.ivTargetPicture.setVisibility(View.GONE);
            holder.subjectLike.tvTargetText.setVisibility(View.VISIBLE);
            holder.subjectLike.tvTargetText.setText(messages.getTargetName());
        } else {
            holder.subjectLike.ivTargetPicture.setVisibility(View.VISIBLE);
            holder.subjectLike.tvTargetText.setVisibility(View.GONE);
            loadImageByUrl(holder.subjectLike.ivTargetPicture, targetPicture);
        }
    }

    /**
     * 初始化喜欢的页面数据
     */
    private void bindSubjectFavoriteData(ViewHolder holder, int position) {
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.subjectFavorite.contentHeadImg,
                holder.subjectFavorite.tvSenderName);
        Messages messages = subjectMessages.get(position);
        if (messages == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        if (messages.getIs_read()) {
            holder.statusIsRead.setVisibility(View.GONE);
        } else {
            holder.statusIsRead.setVisibility(View.VISIBLE);
        }
        holder.subjectFavorite.tvSenderName.setText(messages.getSenderName());
        holder.subjectFavorite.tvTime.setText(
                StringUtils.changeDateToString(messages.getUpdated_at()));
        String senderPicture = messages.getSenderPicture();
        if (StringUtils.isEmpty(senderPicture)) {
            holder.subjectFavorite.ivSenderHeadImg.setImageResource(R.drawable.ic_user_head);
        } else {
            loadImageByUrl(holder.subjectFavorite.ivSenderHeadImg, senderPicture);
        }
        String targetPicture = messages.getTargetPicture();
        if (StringUtils.isEmpty(targetPicture)) {
            holder.subjectFavorite.ivTargetPicture.setVisibility(View.GONE);
            holder.subjectFavorite.tvTargetText.setVisibility(View.VISIBLE);
            holder.subjectFavorite.tvTargetText.setText(messages.getTargetName());
        } else {
            holder.subjectFavorite.ivTargetPicture.setVisibility(View.VISIBLE);
            holder.subjectFavorite.tvTargetText.setVisibility(View.GONE);
            loadImageByUrl(holder.subjectFavorite.ivTargetPicture, targetPicture);
        }
    }

    /**
     * 绑定评论及回复的数据
     */
    private void bindSubjectCommentData(ViewHolder holder, int position) {
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.subjectComment.contentHeadImg,
                holder.subjectComment.tvSenderName);
        Messages messages = subjectMessages.get(position);
        if (messages == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        if (messages.getIs_read()) {
            holder.statusIsRead.setVisibility(View.GONE);
        } else {
            holder.statusIsRead.setVisibility(View.VISIBLE);
        }
        holder.subjectComment.tvSenderName.setText(messages.getSenderName());
        holder.subjectComment.tvTime.setText(
                StringUtils.changeDateToString(messages.getUpdated_at()));
        String senderPicture = messages.getSenderPicture();
        if (StringUtils.isEmpty(senderPicture)) {
            holder.subjectComment.ivSenderHeadImg.setImageResource(R.drawable.ic_user_head);
        } else {
            loadImageByUrl(holder.subjectComment.ivSenderHeadImg, senderPicture);
        }
        holder.subjectComment.tvComment.setText(messages.getRelationContent());
        String targetPicture = messages.getTargetPicture();
        if (StringUtils.isEmpty(targetPicture)) {
            holder.subjectComment.ivTargetPicture.setVisibility(View.GONE);
            holder.subjectComment.tvTargetText.setVisibility(View.VISIBLE);
            holder.subjectComment.tvTargetText.setText(messages.getTargetName());
        } else {
            holder.subjectComment.ivTargetPicture.setVisibility(View.VISIBLE);
            holder.subjectComment.tvTargetText.setVisibility(View.GONE);
            loadImageByUrl(holder.subjectComment.ivTargetPicture, targetPicture);
        }
    }

    /**
     * 加载图片
     */
    private void loadImageByUrl(ImageView ivSenderHeadImg, String senderPicture) {
        ImageLoaderFactory.getInstance().displayImage(ivSenderHeadImg, senderPicture);
    }

    @Override
    public int getItemCount() {
        return subjectMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        int viewType;
        View contentView;
        View statusIsRead;
        SubjectFavorite subjectFavorite;
        SubjectComment subjectComment;
        SubjectLike subjectLike;
    }

    /**
     * 喜欢的动态消息
     */
    private class SubjectFavorite {
        TextView tvSenderName;
        ImageView ivSenderHeadImg;
        View contentHeadImg;
        TextView tvTime;
        ImageView ivTargetPicture;
        TextView tvTargetText;
    }

    /**
     * 喜欢的动态消息
     */
    private class SubjectComment {
        TextView tvSenderName;
        ImageView ivSenderHeadImg;
        View contentHeadImg;
        TextView tvTime;
        TextView tvComment;
        ImageView ivTargetPicture;
        TextView tvTargetText;
    }

    /**
     * 喜欢的动态消息
     */
    private class SubjectLike {
        TextView tvSenderName;
        ImageView ivSenderHeadImg;
        View contentHeadImg;
        TextView tvTime;
        TextView tvComment;
        ImageView ivTargetPicture;
        TextView tvTargetText;
    }

    /**
     * 专题的条目点击回调接口
     */
    public interface OnSubjectItemClickListener {

        /**
         * 当专题整体被点击的时候
         */
        void onSubjectItemClick(int position);

        /**
         * 当用户头像被点击的时候
         */
        void onUserItemClick(int position);
    }


    public void setOnSubjectItemClickListener(
            OnSubjectItemClickListener onSubjectItemClickListener) {
        this.onSubjectItemClickListener = onSubjectItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onSubjectItemClickListener == null) {
            return;
        }
        //判断是哪个控件
        Bundle bundle = (Bundle) v.getTag();
        if (bundle == null) {
            return;
        }
        int viewId = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_ID);
        int position = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_POSITION);
        switch (viewId) {
            case R.id.messageContent:
                onSubjectItemClickListener.onSubjectItemClick(position);
                break;
            case R.id.imsf_tv_senderNickName:
            case R.id.imsf_contentHeadImg:
            case R.id.imsl_tv_senderNickName:
            case R.id.imsl_contentHeadImg:
            case R.id.imsc_tv_senderNickName:
            case R.id.imsc_contentHeadImg:
                onSubjectItemClickListener.onUserItemClick(position);
                break;
        }
    }

}
