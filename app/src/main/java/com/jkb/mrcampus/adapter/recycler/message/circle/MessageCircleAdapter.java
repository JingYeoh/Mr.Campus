package com.jkb.mrcampus.adapter.recycler.message.circle;

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
import com.jkb.mrcampus.adapter.recycler.message.dynamic.MessageDynamicAdapter;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.Messages;

/**
 * 圈子消息的数据适配器
 * Created by JustKiddingBaby on 2016/11/9.
 */

public class MessageCircleAdapter extends RecyclerView.Adapter<MessageCircleAdapter.ViewHolder>
        implements View.OnClickListener {

    private Context context;
    public List<Messages> circleMessages;

    //常量
    private static final int TYPE_IN_BLACK_LIST_DYNAMIC = 1001;
    private static final int TYPE_OUT_BLACK_LIST_DYNAMIC = 1002;
    private static final int TYPE_IN_BLACK_LIST_USER = 2001;
    private static final int TYPE_OUT_BLACK_LIST_USER = 2002;

    //回调
    private OnMessageCircleItemClickListener onMessageCircleItemClickListener;

    //用到的资源id
    private int[] tagDynamicIds = new int[]
            {R.drawable.ic_tag_subject_right, R.drawable.ic_tag_today_right};

    public MessageCircleAdapter(Context context, List<Messages> circleMessages) {
        this.context = context;
        if (circleMessages == null) {
            circleMessages = new ArrayList<>();
        }
        this.circleMessages = circleMessages;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        Messages messages = circleMessages.get(position);
        String action = messages.getAction();
        switch (action) {
            case Config.MESSAGE_ACTION_IN_BLACK_LIST_DYNAMIC:
                viewType = TYPE_IN_BLACK_LIST_DYNAMIC;
                break;
            case Config.MESSAGE_ACTION_OUT_BLACK_LIST_DYNAMIC:
                viewType = TYPE_OUT_BLACK_LIST_DYNAMIC;
                break;
            case Config.MESSAGE_ACTION_IN_BLACK_LIST_USER:
                viewType = TYPE_IN_BLACK_LIST_USER;
                break;
            case Config.MESSAGE_ACTION_OUT_BLACK_LIST_USER:
                viewType = TYPE_OUT_BLACK_LIST_USER;
                break;
            default:
                viewType = -1;
                break;
        }
        return viewType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case TYPE_IN_BLACK_LIST_DYNAMIC:
            case TYPE_OUT_BLACK_LIST_DYNAMIC:
                holder = initBlackDynamic(inflater, parent);
                break;
            case TYPE_IN_BLACK_LIST_USER:
            case TYPE_OUT_BLACK_LIST_USER:
                holder = initBlackUser(inflater, parent);
                break;
            default:
                holder = initBlank(inflater, parent);
                break;
        }
        holder.viewType = viewType;
        return holder;
    }

    /**
     * 初始化空的页面
     */
    private ViewHolder initBlank(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_message_blank, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.viewType = -1;
        //初始化控件id
        holder.contentView = view.findViewById(R.id.messageContent);
        holder.contentView.setVisibility(View.GONE);
        return holder;
    }

    /**
     * 初始化动态用户黑名单/取消黑名单控件
     */
    private ViewHolder initBlackUser(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_message_circle_blacklist_user, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件id
        holder.contentView = view.findViewById(R.id.messageContent);
        holder.statusIsRead = view.findViewById(R.id.message_status_read);
        if (holder.blackListUser == null) {
            holder.blackListUser = new BlackListUser();
        }
        holder.blackListUser.tvTitleAction = (TextView) view.findViewById(R.id.imcbu_tv_tag_in$out);
        holder.blackListUser.contentCircleImg = view.findViewById(R.id.imcbu_contentCirclePic);
        holder.blackListUser.ivCirclePic = (ImageView) view.findViewById(R.id.imcbu_iv_circlePicture);
        holder.blackListUser.tvCircleName = (TextView) view.findViewById(R.id.imcbu_tv_circleName);
        holder.blackListUser.tvAction = (TextView) view.findViewById(R.id.imcbu_tv_content);
        holder.blackListUser.ivUserHeadImg = (ImageView) view.findViewById(R.id.imcbu_iv_userPicture);
        holder.blackListUser.tvTime = (TextView) view.findViewById(R.id.imcbu_tv_time);
        //初始化点击事件
        holder.contentView.setOnClickListener(this);
        holder.blackListUser.contentCircleImg.setOnClickListener(this);
        holder.blackListUser.tvCircleName.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化动态黑名单/取消黑名单控件
     */
    private ViewHolder initBlackDynamic(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_message_circle_blacklist_dynamic, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件id
        holder.contentView = view.findViewById(R.id.messageContent);
        holder.statusIsRead = view.findViewById(R.id.message_status_read);
        if (holder.blackListDynamic == null) {
            holder.blackListDynamic = new BlackListDynamic();
        }
        holder.blackListDynamic.tvTitleAction = (TextView) view.findViewById(R.id.imcbd_tv_tag_in$out);
        holder.blackListDynamic.contentCircleImg = view.findViewById(R.id.imcbd_contentCirclePic);
        holder.blackListDynamic.ivCirclePic = (ImageView) view.findViewById(R.id.imcbd_iv_circlePicture);
        holder.blackListDynamic.tvCircleName = (TextView) view.findViewById(R.id.imcbd_tv_circleName);
        holder.blackListDynamic.tvAction = (TextView) view.findViewById(R.id.imcbd_tv_content);
        holder.blackListDynamic.ivDynamicPic = (ImageView) view.findViewById(R.id.imcbd_iv_dynamicPicture);
        holder.blackListDynamic.tvDynamicContent = (TextView) view.findViewById(R.id.imcbd_tv_dynamicText);
        holder.blackListDynamic.tvTime = (TextView) view.findViewById(R.id.imcbd_tv_time);
        //初始化点击事件
        holder.contentView.setOnClickListener(this);
        holder.blackListDynamic.contentCircleImg.setOnClickListener(this);
        holder.blackListDynamic.tvCircleName.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = holder.viewType;
        switch (viewType) {
            case TYPE_IN_BLACK_LIST_DYNAMIC:
                bindBlackListDynamic(holder, position, 0);
                break;
            case TYPE_OUT_BLACK_LIST_DYNAMIC:
                bindBlackListDynamic(holder, position, 1);
                break;
            case TYPE_IN_BLACK_LIST_USER:
                bindBlackListUser(holder, position, 0);
                break;
            case TYPE_OUT_BLACK_LIST_USER:
                bindBlackListUser(holder, position, 1);
                break;
            default:
                break;
        }
    }

    private void bindBlackListUser(ViewHolder holder, int position, int isInBlackList) {
        Messages messages = circleMessages.get(position);
        //绑定TAG
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.blackListUser.contentCircleImg,
                holder.blackListUser.tvCircleName
        );
        if (messages.getIs_read()) {
            holder.statusIsRead.setVisibility(View.GONE);
        } else {
            holder.statusIsRead.setVisibility(View.VISIBLE);
        }
        holder.blackListUser.tvTitleAction.setBackgroundResource(tagDynamicIds[isInBlackList]);
        if (isInBlackList == 0) {
            holder.blackListUser.tvTitleAction.setText("被关禁闭室");
            holder.blackListUser.tvAction.setText("您已被该圈子拉黑");
        } else {
            holder.blackListUser.tvTitleAction.setText("走出禁闭室");
            holder.blackListUser.tvAction.setText("您已被该圈子解除拉黑");
        }
        //设置圈子信息
        holder.blackListUser.tvCircleName.setText(messages.getSenderName());
        String circlePic = messages.getSenderPicture();
        if (StringUtils.isEmpty(circlePic)) {
            holder.blackListUser.ivCirclePic.setBackgroundResource(R.color.default_picture);
        } else {
            loadImageByUrl(circlePic, holder.blackListUser.ivCirclePic);
        }
        //设置用户信息
        String userPic = messages.getTargetPicture();
        if (StringUtils.isEmpty(userPic)) {
            holder.blackListUser.ivUserHeadImg.setImageResource(R.drawable.ic_user_head);
        } else {
            loadImageByUrl(userPic, holder.blackListUser.ivUserHeadImg);
        }
        holder.blackListUser.tvTime.setText(
                StringUtils.changeDateToString(messages.getUpdated_at()));
    }

    /**
     * 绑定拉黑的动态数据
     */
    private void bindBlackListDynamic(ViewHolder holder, int position, int isInBlackList) {
        Messages messages = circleMessages.get(position);
        //初始化TAG
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.blackListDynamic.contentCircleImg,
                holder.blackListDynamic.tvCircleName
        );
        if (messages.getIs_read()) {
            holder.statusIsRead.setVisibility(View.GONE);
        } else {
            holder.statusIsRead.setVisibility(View.VISIBLE);
        }
        holder.blackListDynamic.tvTitleAction.setBackgroundResource(tagDynamicIds[isInBlackList]);
        if (isInBlackList == 0) {
            holder.blackListDynamic.tvTitleAction.setText("被关小黑屋");
            holder.blackListDynamic.tvAction.setText("您的动态已被该圈子拉黑");
        } else {
            holder.blackListDynamic.tvTitleAction.setText("走出小黑屋");
            holder.blackListDynamic.tvAction.setText("您的动态被该圈子解除拉黑");
        }
        //设置圈子信息
        holder.blackListDynamic.tvCircleName.setText(messages.getSenderName());
        String circlePic = messages.getSenderPicture();
        if (StringUtils.isEmpty(circlePic)) {
            holder.blackListDynamic.ivCirclePic.setBackgroundResource(R.color.default_picture);
        } else {
            loadImageByUrl(circlePic, holder.blackListDynamic.ivCirclePic);
        }
        //设置动态信息
        String dynamicPic = messages.getTargetPicture();
        if (StringUtils.isEmpty(dynamicPic)) {
            holder.blackListDynamic.ivDynamicPic.setVisibility(View.GONE);
            holder.blackListDynamic.tvDynamicContent.setVisibility(View.VISIBLE);
            holder.blackListDynamic.tvDynamicContent.setText(messages.getTargetName());
        } else {
            holder.blackListDynamic.ivDynamicPic.setVisibility(View.VISIBLE);
            holder.blackListDynamic.tvDynamicContent.setVisibility(View.GONE);
            loadImageByUrl(dynamicPic, holder.blackListDynamic.ivDynamicPic);
        }
        holder.blackListDynamic.tvTime.setText(
                StringUtils.changeDateToString(messages.getUpdated_at()));
    }

    /**
     * 加载网络图片
     */
    private void loadImageByUrl(String circlePic, ImageView ivCirclePic) {
        ImageLoaderFactory.getInstance().displayImage(ivCirclePic, circlePic);
    }

    @Override
    public int getItemCount() {
        return circleMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        int viewType;
        View contentView;
        View statusIsRead;
        BlackListDynamic blackListDynamic;
        BlackListUser blackListUser;
    }

    /**
     * 拉黑的动态控件
     */
    private class BlackListDynamic {
        View contentCircleImg;
        TextView tvTitleAction;
        ImageView ivCirclePic;
        TextView tvCircleName;
        TextView tvAction;
        TextView tvTime;
        ImageView ivDynamicPic;
        TextView tvDynamicContent;
    }

    /**
     * 拉黑的用户控件
     */
    private class BlackListUser {
        TextView tvTitleAction;
        View contentCircleImg;
        ImageView ivCirclePic;
        TextView tvCircleName;
        TextView tvAction;
        TextView tvTime;
        ImageView ivUserHeadImg;
    }

    /**
     * 圈子动态条目的点击回调接口
     */
    public interface OnMessageCircleItemClickListener {

        /**
         * 当条目被点击的时候
         *
         * @param position 条目数
         */
        void onItemClick(int position);

        /**
         * 当圈子条目被点击的时候
         */
        void onCircleItemClick(int position);
    }

    /**
     * 设置条目的点击监听回调
     */
    public void setOnMessageCircleItemClickListener(
            OnMessageCircleItemClickListener onMessageCircleItemClickListener) {
        this.onMessageCircleItemClickListener = onMessageCircleItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onMessageCircleItemClickListener == null) {
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
                onMessageCircleItemClickListener.onItemClick(position);
                break;
            case R.id.imcbu_contentCirclePic:
            case R.id.imcbd_contentCirclePic:
            case R.id.imcbd_tv_circleName:
            case R.id.imcbu_tv_circleName:
                onMessageCircleItemClickListener.onCircleItemClick(position);
                break;
        }
    }
}
