package com.jkb.mrcampus.adapter.recycler.message.dynamic;

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
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.Messages;

/**
 * 动态消息的适配器
 * Created by JustKiddingBaby on 2016/10/26.
 */

public class MessageDynamicAdapter extends RecyclerView.Adapter<MessageDynamicAdapter.ViewHolder> {

    private Context context;
    public List<Messages> dynamicMessages;

    //常量
    private static final int TYPE_DYNAMIC_COMMENT = 1001;
    private static final int TYPE_DYNAMIC_FAVORITE = 1002;
    private static final int TYPE_DYNAMIC_REPLY = 1003;
    private static final int TYPE_DYNAMIC_LIKE = 1004;

    //监听器
    private OnMessageDynamicItemClickListener onMessageDynamicItemClickListener;

    public MessageDynamicAdapter(Context context, List<Messages> dynamicMessages) {
        this.context = context;
        if (dynamicMessages == null) {
            dynamicMessages = new ArrayList<>();
        }
        this.dynamicMessages = dynamicMessages;
    }

    @Override
    public int getItemViewType(int position) {
        //判断类型
        int viewType;
        Messages messages = dynamicMessages.get(position);
        String action = messages.getAction();
//        LogUtils.d(MessageDynamicAdapter.class,"第-----"+position+"个action是--->"+action);
        switch (action) {
            case Config.MESSAGE_ACTION_FAVORITE://喜欢动态
                viewType = TYPE_DYNAMIC_FAVORITE;
                break;
            case Config.MESSAGE_ACTION_LIKE://点赞评论
                viewType = TYPE_DYNAMIC_LIKE;
                break;
            case Config.MESSAGE_ACTION_MAKECOMMENT://评论
                viewType = TYPE_DYNAMIC_COMMENT;
                break;
            case Config.MESSAGE_ACTION_MAKEREPLY://回复
                viewType = TYPE_DYNAMIC_REPLY;
                break;
            default:
                viewType = -1;
        }
        return viewType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        //判断动态类型
        switch (viewType) {
            case TYPE_DYNAMIC_FAVORITE:
                holder = initFavoriteDynamicMessage(inflater, parent);
                break;
            case TYPE_DYNAMIC_LIKE:
                holder = initLikeDynamicMessage(inflater, parent);
                break;
            case TYPE_DYNAMIC_COMMENT:
            case TYPE_DYNAMIC_REPLY:
                holder = initCommentDynamicMessage(inflater, parent);
                break;
            default:
                holder = initBlankDynamicMessage(inflater, parent);
                break;
        }
        return holder;
    }


    /**
     * 初始化空白的动态消息页面
     */
    private ViewHolder initBlankDynamicMessage(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_message_blank, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.viewType = -1;
        holder.contentView = view.findViewById(R.id.messageContent);
        return holder;
    }

    /**
     * 初始化喜欢动态的页面
     */
    private ViewHolder initFavoriteDynamicMessage(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_message_dynamic_favorite,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.viewType = TYPE_DYNAMIC_FAVORITE;
        holder.contentView = view.findViewById(R.id.messageContent);
        holder.statusIsRead = view.findViewById(R.id.message_status_read);
        if (holder.messageDynamicFavorite == null) {
            holder.messageDynamicFavorite = new MessageDynamicFavorite();
        }
        //初始化id
        holder.messageDynamicFavorite.tvSenderName = (TextView)
                view.findViewById(R.id.imdf_tv_senderNickName);
        holder.messageDynamicFavorite.ivSenderHeadImg = (ImageView)
                view.findViewById(R.id.imdf_iv_sendUserHeadImg);
        holder.messageDynamicFavorite.tvTime = (TextView)
                view.findViewById(R.id.imdf_tv_time);
        holder.messageDynamicFavorite.ivTargetPicture = (ImageView)
                view.findViewById(R.id.imdf_iv_targetPicture);
        holder.messageDynamicFavorite.tvTargetText = (TextView)
                view.findViewById(R.id.imdf_tv_targetText);
        //初始化监听事件
        holder.contentView.setOnClickListener(clickMessageDynamicItemListener);
        return holder;
    }

    /**
     * 初始化点赞的动态消息
     */
    private ViewHolder initLikeDynamicMessage(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_message_dynamic_like,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.viewType = TYPE_DYNAMIC_LIKE;
        holder.contentView = view.findViewById(R.id.messageContent);
        holder.statusIsRead = view.findViewById(R.id.message_status_read);
        if (holder.messageDynamicLike == null) {
            holder.messageDynamicLike = new MessageDynamicLike();
        }
        //初始化id
        holder.messageDynamicLike.tvSenderName = (TextView)
                view.findViewById(R.id.imdl_tv_senderNickName);
        holder.messageDynamicLike.ivSenderHeadImg = (ImageView)
                view.findViewById(R.id.imdl_iv_sendUserHeadImg);
        holder.messageDynamicLike.tvComment = (TextView)
                view.findViewById(R.id.imdl_tv_comment);
        holder.messageDynamicLike.tvTime = (TextView)
                view.findViewById(R.id.imdl_tv_time);
        holder.messageDynamicLike.ivTargetPicture = (ImageView)
                view.findViewById(R.id.imdl_iv_targetPicture);
        holder.messageDynamicLike.tvTargetText = (TextView)
                view.findViewById(R.id.imdl_tv_targetText);
        //初始化监听事件
        holder.contentView.setOnClickListener(clickMessageDynamicItemListener);
        return holder;
    }

    /**
     * 初始化评论的动态消息页面
     */
    private ViewHolder initCommentDynamicMessage(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_message_dynamic_reply_comment,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.viewType = TYPE_DYNAMIC_COMMENT;
        holder.contentView = view.findViewById(R.id.messageContent);
        holder.statusIsRead = view.findViewById(R.id.message_status_read);
        if (holder.messageDynamicComment == null) {
            holder.messageDynamicComment = new MessageDynamicComment();
        }
        //初始化id
        holder.messageDynamicComment.tvSenderName = (TextView)
                view.findViewById(R.id.imdrc_tv_senderNickName);
        holder.messageDynamicComment.ivSenderHeadImg = (ImageView)
                view.findViewById(R.id.imdrc_iv_sendUserHeadImg);
        holder.messageDynamicComment.tvComment = (TextView)
                view.findViewById(R.id.imdrc_tv_comment);
        holder.messageDynamicComment.tvTime = (TextView)
                view.findViewById(R.id.imdrc_tv_time);
        holder.messageDynamicComment.ivTargetPicture = (ImageView)
                view.findViewById(R.id.imdrc_iv_targetPicture);
        holder.messageDynamicComment.tvTargetText = (TextView)
                view.findViewById(R.id.imdrc_tv_targetText);
        //初始化监听事件
        holder.contentView.setOnClickListener(clickMessageDynamicItemListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = holder.viewType;
        ClassUtils.bindViewsTag(position, holder.contentView);
        switch (viewType) {
            case TYPE_DYNAMIC_FAVORITE:
                bindMessageDynamicFavorite(holder, position);
                break;
            case TYPE_DYNAMIC_LIKE:
                bindMessageDynamicLike(holder, position);
                break;
            case TYPE_DYNAMIC_COMMENT:
            case TYPE_DYNAMIC_REPLY:
                bindMessageDynamicComment$Reply(holder, position);
                break;
            default:
                bindBlankMessageDynamic(holder, position);
        }
    }

    /**
     * 绑定评论或者回复的动态数据
     */
    private void bindMessageDynamicComment$Reply(ViewHolder holder, int position) {
        Messages messages = dynamicMessages.get(position);
        if (messages == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        if (messages.getIs_read()) {
            holder.statusIsRead.setVisibility(View.GONE);
        } else {
            holder.statusIsRead.setVisibility(View.VISIBLE);
        }
        holder.messageDynamicComment.tvSenderName.setText(messages.getSenderName());
        holder.messageDynamicComment.tvTime.setText(
                StringUtils.changeDateToString(messages.getUpdated_at()));
        String senderPicture = messages.getSenderPicture();
        if (StringUtils.isEmpty(senderPicture)) {
            holder.messageDynamicComment.ivSenderHeadImg.setImageResource(R.drawable.ic_user_head);
        } else {
            loadImageByUrl(holder.messageDynamicComment.ivSenderHeadImg, senderPicture);
        }
        holder.messageDynamicComment.tvComment.setText(messages.getRelationContent());
        String targetPicture = messages.getTargetPicture();
        if (StringUtils.isEmpty(targetPicture)) {
            holder.messageDynamicComment.ivTargetPicture.setVisibility(View.GONE);
            holder.messageDynamicComment.tvTargetText.setVisibility(View.VISIBLE);
            holder.messageDynamicComment.tvTargetText.setText(messages.getTargetName());
        } else {
            holder.messageDynamicComment.ivTargetPicture.setVisibility(View.VISIBLE);
            holder.messageDynamicComment.tvTargetText.setVisibility(View.GONE);
            loadImageByUrl(holder.messageDynamicComment.ivTargetPicture, targetPicture);
        }
    }

    /**
     * 绑定喜欢的动态数据
     */
    private void bindMessageDynamicFavorite(ViewHolder holder, int position) {
        Messages messages = dynamicMessages.get(position);
        if (messages == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        if (messages.getIs_read()) {
            holder.statusIsRead.setVisibility(View.GONE);
        } else {
            holder.statusIsRead.setVisibility(View.VISIBLE);
        }
        holder.messageDynamicFavorite.tvSenderName.setText(messages.getSenderName());
        holder.messageDynamicFavorite.tvTime.setText(
                StringUtils.changeDateToString(messages.getUpdated_at()));
        String senderPicture = messages.getSenderPicture();
        if (StringUtils.isEmpty(senderPicture)) {
            holder.messageDynamicFavorite.ivSenderHeadImg.setImageResource(R.drawable.ic_user_head);
        } else {
            loadImageByUrl(holder.messageDynamicFavorite.ivSenderHeadImg, senderPicture);
        }
        String targetPicture = messages.getTargetPicture();
        if (StringUtils.isEmpty(targetPicture)) {
            holder.messageDynamicFavorite.ivTargetPicture.setVisibility(View.GONE);
            holder.messageDynamicFavorite.tvTargetText.setVisibility(View.VISIBLE);
            holder.messageDynamicFavorite.tvTargetText.setText(messages.getTargetName());
        } else {
            holder.messageDynamicFavorite.ivTargetPicture.setVisibility(View.VISIBLE);
            holder.messageDynamicFavorite.tvTargetText.setVisibility(View.GONE);
            loadImageByUrl(holder.messageDynamicFavorite.ivTargetPicture, targetPicture);
        }
    }

    /**
     * 绑定喜欢的动态消息数据
     */
    private void bindMessageDynamicLike(ViewHolder holder, int position) {
        Messages messages = dynamicMessages.get(position);
        if (messages == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        if (messages.getIs_read()) {
            holder.statusIsRead.setVisibility(View.GONE);
        } else {
            holder.statusIsRead.setVisibility(View.VISIBLE);
        }
        holder.messageDynamicLike.tvSenderName.setText(messages.getSenderName());
        holder.messageDynamicLike.tvTime.setText(
                StringUtils.changeDateToString(messages.getUpdated_at()));
        String senderPicture = messages.getSenderPicture();
        if (StringUtils.isEmpty(senderPicture)) {
            holder.messageDynamicLike.ivSenderHeadImg.setImageResource(R.drawable.ic_user_head);
        } else {
            loadImageByUrl(holder.messageDynamicLike.ivSenderHeadImg, senderPicture);
        }
        holder.messageDynamicLike.tvComment.setText(messages.getRelationContent());
        String targetPicture = messages.getTargetPicture();
        if (StringUtils.isEmpty(targetPicture)) {
            holder.messageDynamicLike.ivTargetPicture.setVisibility(View.GONE);
            holder.messageDynamicLike.tvTargetText.setVisibility(View.VISIBLE);
            holder.messageDynamicLike.tvTargetText.setText(messages.getTargetName());
        } else {
            holder.messageDynamicLike.ivTargetPicture.setVisibility(View.VISIBLE);
            holder.messageDynamicLike.tvTargetText.setVisibility(View.GONE);
            loadImageByUrl(holder.messageDynamicLike.ivTargetPicture, targetPicture);
        }
    }

    /**
     * 加载图片
     */
    private void loadImageByUrl(ImageView ivSenderHeadImg, String senderPicture) {
        ImageLoaderFactory.getInstance().displayImage(ivSenderHeadImg, senderPicture);
    }

    /**
     * 绑定空的动态消息
     */
    private void bindBlankMessageDynamic(ViewHolder holder, int position) {
        holder.contentView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return dynamicMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        int viewType;
        View contentView;
        View statusIsRead;
        MessageDynamicFavorite messageDynamicFavorite;
        MessageDynamicComment messageDynamicComment;
        MessageDynamicLike messageDynamicLike;
    }

    /**
     * 喜欢的动态消息
     */
    private class MessageDynamicFavorite {
        TextView tvSenderName;
        ImageView ivSenderHeadImg;
        TextView tvTime;
        ImageView ivTargetPicture;
        TextView tvTargetText;
    }

    /**
     * 喜欢的动态消息
     */
    private class MessageDynamicComment {
        TextView tvSenderName;
        ImageView ivSenderHeadImg;
        TextView tvTime;
        TextView tvComment;
        ImageView ivTargetPicture;
        TextView tvTargetText;
    }

    /**
     * 喜欢的动态消息
     */
    private class MessageDynamicLike {
        TextView tvSenderName;
        ImageView ivSenderHeadImg;
        TextView tvTime;
        TextView tvComment;
        ImageView ivTargetPicture;
        TextView tvTargetText;
    }

    /**
     * 动态消息的条目点击监听事件
     */
    public interface OnMessageDynamicItemClickListener {

        /**
         * 消息条目被点击的监听事件
         *
         * @param position 条目数
         */
        void onMessageItemClick(int position);
    }

    /**
     * 设置动态消息的条目点击监听事件
     */
    public void setOnMessageDynamicItemClickListener(
            OnMessageDynamicItemClickListener onMessageDynamicItemClickListener) {
        this.onMessageDynamicItemClickListener = onMessageDynamicItemClickListener;
    }

    /**
     * 动态消息的条目点击监听事件
     */
    private View.OnClickListener clickMessageDynamicItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onMessageDynamicItemClickListener == null) {
                return;
            }
            Bundle bundle = (Bundle) v.getTag();
            if (bundle == null) {
                return;
            }
            int viewId = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_ID);
            int position = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_POSITION);
            switch (viewId) {
                case R.id.messageContent:
                    onMessageDynamicItemClickListener.onMessageItemClick(position);
                    break;
            }
        }
    };
}
