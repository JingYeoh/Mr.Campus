package com.jkb.mrcampus.adapter.recycler.personCenter.myDynamic;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.dynamicDetail.data.DynamicDetailNormalData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的普通动态的适配器
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyDynamicNormalAdapter extends RecyclerView.Adapter<MyDynamicNormalAdapter.ViewHolder> {

    private Context context;
    public List<DynamicDetailNormalData> dynamicDetailNormalDatas;

    private OnMyDynamicClickListener onMyDynamicClickListener;

    //两种颜色
    private int selector_bg[] = new int[]
            {R.drawable.selector_white_general, R.drawable.selector_bg_gravy};
    private int ic_heart_normal = R.drawable.ic_heart_black;

    public MyDynamicNormalAdapter(Context context,
                                  List<DynamicDetailNormalData> dynamicDetailNormalDatas) {
        this.context = context;
        if (dynamicDetailNormalDatas == null) {
            dynamicDetailNormalDatas = new ArrayList<>();
        }
        this.dynamicDetailNormalDatas = dynamicDetailNormalDatas;

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
                inflate(R.layout.item_my_dynamic_normal, parent, false);
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
        if (holder.myDynamicNormal == null) {
            holder.myDynamicNormal = new MyDynamicNormal();
        }
        holder.myDynamicContent = view.findViewById(R.id.myDynamic_content);
        holder.myDynamicNormal.tvTime = (TextView) view.findViewById(R.id.imdn_tv_time);
        holder.myDynamicNormal.tvDoc = (TextView) view.findViewById(R.id.imdn_tv_contentText);
        holder.myDynamicNormal.ivPic = (ImageView) view.findViewById(R.id.imdn_iv_contentPic);
        //底部
        holder.myDynamicNormal.tvDelete = (TextView) view.findViewById(R.id.iidb_tv_delete);
        holder.myDynamicNormal.ivShare = (ImageView) view.findViewById(R.id.iidbf_iv_share);
        holder.myDynamicNormal.ivComment = (ImageView) view.findViewById(R.id.iidbf_iv_comment);
        holder.myDynamicNormal.tvComment_count = (TextView) view.findViewById(R.id.iidbf_tv_commentNum);
        holder.myDynamicNormal.ivHeart = (ImageView) view.findViewById(R.id.iidbf_iv_heart);
        holder.myDynamicNormal.tvFavorite_count = (TextView) view.findViewById(R.id.iidbf_tv_likeNum);

        //初始化监听事件
        holder.myDynamicNormal.tvDelete.setOnClickListener(clickMyDynamicListener);
        holder.myDynamicNormal.ivShare.setOnClickListener(clickMyDynamicListener);
        holder.myDynamicNormal.ivComment.setOnClickListener(clickMyDynamicListener);
        holder.myDynamicNormal.ivHeart.setOnClickListener(clickMyDynamicListener);
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
        DynamicDetailNormalData normalData = dynamicDetailNormalDatas.get(position);
        if (normalData == null) {
            holder.myDynamicContent.setVisibility(View.GONE);
            return;
        }

        //绑定TAG
        ClassUtils.bindViewsTag(position,
                holder.myDynamicContent,
                holder.myDynamicNormal.tvDelete,
                holder.myDynamicNormal.ivShare,
                holder.myDynamicNormal.ivComment,
                holder.myDynamicNormal.ivHeart);

        holder.myDynamicNormal.tvTime.setText(normalData.getCreated_at());
        holder.myDynamicNormal.tvDoc.setText(normalData.getDoc());
        String[] imgs = normalData.getImgs();
        if (imgs == null || imgs.length == 0) {
            holder.myDynamicNormal.ivPic.setVisibility(View.GONE);
        } else {
            holder.myDynamicNormal.ivPic.setVisibility(View.VISIBLE);
            String image = imgs[0];
            loadImage(image, holder.myDynamicNormal.ivPic);
        }
        if (normalData.isOriginal()) {
            holder.myDynamicNormal.tvDelete.setVisibility(View.VISIBLE);
        } else {
            holder.myDynamicNormal.tvDelete.setVisibility(View.GONE);
        }
        //底部
        holder.myDynamicNormal.tvComment_count.setText(normalData.getComments_count() + "");
        holder.myDynamicNormal.tvFavorite_count.setText(normalData.getOperation_count() + "");
        if (normalData.isHasFavorite()) {
            holder.myDynamicNormal.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.myDynamicNormal.ivHeart.setImageResource(ic_heart_normal);
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
        return dynamicDetailNormalDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        View myDynamicContent;
        MyDynamicNormal myDynamicNormal;
    }

    private class MyDynamicNormal {
        ImageView ivPic;
        TextView tvDoc;
        TextView tvTime;
        //底部
        TextView tvDelete;
        ImageView ivShare;
        ImageView ivComment;
        TextView tvComment_count;
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
