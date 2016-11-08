package com.jkb.mrcampus.adapter.recycler.message.subscribe;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.Messages;

/**
 * 订阅消息的适配器
 * Created by JustKiddingBaby on 2016/11/6.
 */

public class MessageSubscribeAdapter extends RecyclerView.Adapter<MessageSubscribeAdapter.ViewHolder>
        implements View.OnClickListener {


    private Context context;
    public List<Messages> messages;

    private int[] bgColors = new int[]{R.color.white, R.color.background_general};
    //点击事件
    private OnSubscribeMessageItemClickListener onSubscribeMessageItemClickListener;

    public MessageSubscribeAdapter(Context context, List<Messages> messages) {
        this.context = context;
        if (messages == null) {
            messages = new ArrayList<>();
        }
        this.messages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        holder = initSubscribeCircle(parent, inflater);
        return holder;
    }

    /**
     * 初始化订阅的圈子的控件
     */
    private ViewHolder initSubscribeCircle(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_message_subscribe, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件
        if (holder.subscribeCircleView == null) {
            holder.subscribeCircleView = new SubscribeCircleView();
        }
        holder.subscribeCircleView.contentView = view.findViewById(R.id.messageContent);
        holder.subscribeCircleView.ivHeadImg = (ImageView) view.findViewById(R.id.ims_iv_headImg);
        holder.subscribeCircleView.contentHeadImg = view.findViewById(R.id.ims_contentHeadImg);
        holder.subscribeCircleView.tvUserName = (TextView) view.findViewById(R.id.ims_tv_nickName);
        holder.subscribeCircleView.tvTime = (TextView) view.findViewById(R.id.ims_tv_time);
        holder.subscribeCircleView.contentCirclePicture =
                view.findViewById(R.id.ims_contentCirclePicture);
        holder.subscribeCircleView.ivCirclePicture = (ImageView)
                view.findViewById(R.id.ims_iv_circlePicture);
        holder.subscribeCircleView.tvCircleName = (TextView)
                view.findViewById(R.id.ims_tv_circleName);
        //初始化监听事件
        holder.subscribeCircleView.contentCirclePicture.setOnClickListener(this);
        holder.subscribeCircleView.contentHeadImg.setOnClickListener(this);
        holder.subscribeCircleView.tvCircleName.setOnClickListener(this);
        holder.subscribeCircleView.tvUserName.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        bindSubscribeCircle(holder, position);
    }

    /**
     * 绑定订阅的圈子数据
     */
    private void bindSubscribeCircle(ViewHolder holder, int position) {
        Messages message = messages.get(position);
        //綁定TAG
        ClassUtils.bindViewsTag(position,
                holder.subscribeCircleView.contentCirclePicture,
                holder.subscribeCircleView.contentHeadImg,
                holder.subscribeCircleView.tvCircleName,
                holder.subscribeCircleView.tvUserName);
        holder.subscribeCircleView.contentView.setBackgroundResource(bgColors[position % 2]);
        holder.subscribeCircleView.tvUserName.setText(message.getSenderName());
        holder.subscribeCircleView.tvCircleName.setText(message.getTargetName());
        holder.subscribeCircleView.tvTime.setText
                (StringUtils.changeDateToString(message.getUpdated_at()));
        String targetPicture = message.getTargetPicture();
        if (StringUtils.isEmpty(targetPicture)) {
            holder.subscribeCircleView.contentCirclePicture.setVisibility(View.GONE);
        } else {
            holder.subscribeCircleView.contentCirclePicture.setVisibility(View.VISIBLE);
            loadImageByUrl(holder.subscribeCircleView.ivCirclePicture, targetPicture);
        }
        String senderPicture = message.getSenderPicture();
        if (StringUtils.isEmpty(targetPicture)) {
            holder.subscribeCircleView.ivHeadImg.setImageResource(R.drawable.ic_user_head);
        } else {
            loadImageByUrl(holder.subscribeCircleView.ivHeadImg, senderPicture);
        }
    }

    /**
     * 加载图片
     */
    private void loadImageByUrl(ImageView ivCirclePicture, String targetPicture) {
        ImageLoaderFactory.getInstance().displayImage(ivCirclePicture, targetPicture);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        SubscribeCircleView subscribeCircleView;
    }

    /**
     * 订阅的圈子
     */
    private class SubscribeCircleView {
        View contentView;
        ImageView ivHeadImg;
        View contentHeadImg;
        TextView tvUserName;
        TextView tvTime;
        TextView tvCircleName;
        ImageView ivCirclePicture;
        View contentCirclePicture;
    }

    /**
     * 当订阅的圈子条目被点击的监听事件
     */
    public interface OnSubscribeMessageItemClickListener {

        /**
         * 用户被点击的回调方法
         */
        void onUserItemClick(int position);

        /**
         * 圈子被点击的回调方法
         */
        void onCircleItemClick(int position);
    }

    public void setOnSubscribeMessageItemClickListener(
            OnSubscribeMessageItemClickListener onSubscribeMessageItemClickListener) {
        this.onSubscribeMessageItemClickListener = onSubscribeMessageItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onSubscribeMessageItemClickListener == null) {
            return;
        }
        Bundle bundle = (Bundle) v.getTag();
        if (bundle == null) {
            return;
        }
        int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
        int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
        switch (viewId) {
            case R.id.ims_contentCirclePicture:
            case R.id.ims_tv_circleName:
                onSubscribeMessageItemClickListener.onCircleItemClick(position);
                break;
            case R.id.ims_tv_nickName:
            case R.id.ims_contentHeadImg:
                onSubscribeMessageItemClickListener.onUserItemClick(position);
                break;
        }
    }
}
