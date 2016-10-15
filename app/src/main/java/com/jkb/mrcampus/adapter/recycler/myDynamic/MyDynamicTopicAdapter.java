package com.jkb.mrcampus.adapter.recycler.myDynamic;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.dynamicDetail.data.DynamicDetailTopicData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的普通动态的适配器
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyDynamicTopicAdapter extends RecyclerView.Adapter<MyDynamicTopicAdapter.ViewHolder> {

    private Context context;
    public List<DynamicDetailTopicData> dynamicDetailTopicDatas;

    private OnMyDynamicClickListener onMyDynamicClickListener;

    //两种颜色
    private int selector_bg[] = new int[]
            {R.drawable.selector_white_general, R.drawable.selector_bg_gravy};
    private int ic_heart_normal = R.drawable.ic_heart_black;

    public MyDynamicTopicAdapter(Context context,
                                 List<DynamicDetailTopicData> dynamicDetailTopicDatas) {
        this.context = context;
        if (dynamicDetailTopicDatas == null) {
            dynamicDetailTopicDatas = new ArrayList<>();
        }
        this.dynamicDetailTopicDatas = dynamicDetailTopicDatas;

        //初始化两种颜色
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        holder = initView(holder, parent);
        return holder;
    }

    /**
     * 初始化ViewHolder
     */
    private ViewHolder initView(ViewHolder holder, ViewGroup parent) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.item_my_dynamic_topic, parent, false);
        if (holder == null) {
            holder = new ViewHolder(view);
        }
        //初始化id及其他
        initMyDynamicNormal(holder, view);
        return holder;
    }

    /**
     * 初始化我的普通动态的控件
     */
    private void initMyDynamicNormal(ViewHolder holder, View view) {
        if (holder.myDynamicTopic == null) {
            holder.myDynamicTopic = new MyDynamicTopic();
        }
        holder.myDynamicContent = view.findViewById(R.id.myDynamic_content);
        holder.myDynamicTopic.tvTime = (TextView) view.findViewById(R.id.imdt_tv_time);
        holder.myDynamicTopic.tvTitle = (TextView) view.findViewById(R.id.imdt_tv_topicName);
        holder.myDynamicTopic.tvDoc = (TextView) view.findViewById(R.id.imdt_tv_contentText);
        holder.myDynamicTopic.ivPic = (ImageView) view.findViewById(R.id.imdt_iv_contentPic);
        holder.myDynamicTopic.tvPartIn_count = (TextView) view.findViewById(R.id.imdt_tv_partInNum);
        //底部
        holder.myDynamicTopic.tvDelete = (TextView) view.findViewById(R.id.iidb_tv_delete);
        holder.myDynamicTopic.ivShare = (ImageView) view.findViewById(R.id.iidbf_iv_share);
        view.findViewById(R.id.iidbf_iv_comment).setVisibility(View.GONE);
        view.findViewById(R.id.iidbf_tv_commentNum).setVisibility(View.GONE);
        holder.myDynamicTopic.ivHeart = (ImageView) view.findViewById(R.id.iidbf_iv_heart);
        holder.myDynamicTopic.tvFavorite_count = (TextView) view.findViewById(R.id.iidbf_tv_likeNum);

        //初始化监听事件
        holder.myDynamicTopic.tvDelete.setOnClickListener(clickMyDynamicListener);
        holder.myDynamicTopic.ivShare.setOnClickListener(clickMyDynamicListener);
        holder.myDynamicTopic.ivHeart.setOnClickListener(clickMyDynamicListener);
        holder.myDynamicContent.setOnClickListener(clickMyDynamicListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.myDynamicContent.setBackgroundResource(selector_bg[position % 2]);
        //绑定内容
        bindNormalDynamicData(position, holder);
    }

    /**
     * 绑定普通动态数据
     */
    private void bindNormalDynamicData(int position, ViewHolder holder) {
        DynamicDetailTopicData topicData = dynamicDetailTopicDatas.get(position);
        if (topicData == null) {
            holder.myDynamicContent.setVisibility(View.GONE);
            return;
        }

        //绑定TAG
        ClassUtils.bindViewsTag(position,
                holder.myDynamicContent,
                holder.myDynamicTopic.tvDelete,
                holder.myDynamicTopic.ivShare,
                holder.myDynamicTopic.ivHeart);

        holder.myDynamicTopic.tvTime.setText(topicData.getCreated_at());
        holder.myDynamicTopic.tvTitle.setText(topicData.getTitle());
        holder.myDynamicTopic.tvPartIn_count.setText(topicData.getPartIn_count() + "");
        //设置内容
        String img = topicData.getImg();
        String doc = topicData.getDoc();
        if (StringUtils.isEmpty(img, doc)) {
            holder.myDynamicContent.setVisibility(View.GONE);
            return;
        } else {
            holder.myDynamicTopic.tvDoc.setText(doc);
            if (StringUtils.isEmpty(img)) {
                holder.myDynamicTopic.ivPic.setVisibility(View.GONE);
            } else {
                holder.myDynamicTopic.ivPic.setVisibility(View.VISIBLE);
                loadImage(img, holder.myDynamicTopic.ivPic);
            }
        }
        if (topicData.isOriginal()) {
            holder.myDynamicTopic.tvDelete.setVisibility(View.VISIBLE);
        } else {
            holder.myDynamicTopic.tvDelete.setVisibility(View.GONE);
        }
        //底部
        holder.myDynamicTopic.tvFavorite_count.setText(topicData.getOperation_count() + "");
        if (topicData.isHasFavorite()) {
            holder.myDynamicTopic.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.myDynamicTopic.ivHeart.setImageResource(ic_heart_normal);
        }
    }

    /**
     * 加载图片
     */
    private void loadImage(String image, ImageView ivPic) {
        ImageLoaderFactory.getInstance().displayImage(ivPic, image);
    }

    @Override
    public int getItemCount() {
        return dynamicDetailTopicDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        View myDynamicContent;
        MyDynamicTopic myDynamicTopic;
    }

    private class MyDynamicTopic {
        ImageView ivPic;
        TextView tvDoc;
        TextView tvTime;
        TextView tvTitle;
        TextView tvPartIn_count;
        //底部
        TextView tvDelete;
        ImageView ivShare;
        ImageView ivHeart;
        TextView tvFavorite_count;
    }


    /**
     * 功能条目的点击回调方法
     */
    public interface OnMyDynamicClickListener {

        /**
         * 动态的点击回调方法
         *
         * @param position 条目数
         */
        void onDynamicClick(int position);

        /**
         * 喜欢的条目点击回调方法
         *
         * @param position 条目数
         */
        void onLikeClick(int position);

        /**
         * 分享的点击回调方法
         *
         * @param position 条目数
         */
        void onShareClick(int position);

        /**
         * 评论的点击回调方法
         *
         * @param position 条目数
         */
        void onCommentClick(int position);

        /**
         * 删除的点击回调方法
         *
         * @param position 条目数
         */
        void onDeleteClick(int position);
    }

    /**
     * 设置我的动态的回调方法
     */
    public void setOnMyDynamicClickListener(OnMyDynamicClickListener onMyDynamicClickListener) {
        this.onMyDynamicClickListener = onMyDynamicClickListener;
    }

    /**
     * 点击监听
     */
    private View.OnClickListener clickMyDynamicListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onMyDynamicClickListener == null) {
                return;
            }
            //判断是哪个控件
            Bundle bundle = (Bundle) view.getTag();
            if (bundle == null) {
                return;
            }
            int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
            int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
            switch (viewId) {
                case R.id.iidb_tv_delete:
                    onMyDynamicClickListener.onDeleteClick(position);
                    break;
                case R.id.iidbf_iv_share:
                    onMyDynamicClickListener.onShareClick(position);
                    break;
                case R.id.iidbf_iv_comment:
                    onMyDynamicClickListener.onCommentClick(position);
                    break;
                case R.id.iidbf_iv_heart:
                    onMyDynamicClickListener.onLikeClick(position);
                    break;
                case R.id.myDynamic_content:
                    onMyDynamicClickListener.onDynamicClick(position);
                    break;
            }
        }
    };
}
