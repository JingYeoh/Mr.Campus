package com.jkb.mrcampus.adapter.recycler.special.function;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.data.special.SpecialData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 跳蚤市场的数据适配器
 * Created by JustKiddingBaby on 2016/11/19.
 */

public class SpecialFleaMarketAdapter extends
        RecyclerView.Adapter<SpecialFleaMarketAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    public List<SpecialData> specialData;
    private OnSpecialConfessionItemClickListener onSpecialConfessionItemClickListener;

    public SpecialFleaMarketAdapter(Context context, List<SpecialData> specialData) {
        this.context = context;
        if (specialData == null) {
            specialData = new ArrayList<>();
        }
        this.specialData = specialData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder = initConfession(parent, inflater);
        return holder;
    }

    /**
     * 初始化视图
     */
    private ViewHolder initConfession(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_special_confession, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件
        if (holder.confession == null) {
            holder.confession = new Confession();
        }
        holder.contentView = view.findViewById(R.id.isc_content);
        holder.confession.ivBgPic = (ImageView) view.findViewById(R.id.isc_iv_BgPic);
        holder.confession.ivPic = (ImageView) view.findViewById(R.id.isc_iv_pic);
        holder.confession.ivTag = (ImageView) view.findViewById(R.id.isc_iv_tag);
        holder.confession.tvTitle = (TextView) view.findViewById(R.id.isc_tv_title);
        holder.confession.tvText = (TextView) view.findViewById(R.id.isc_tv_content);
        //底部功能栏
        holder.confession.ivShare = (ImageView) view.findViewById(R.id.iidbf_iv_share);
        holder.confession.ivCommit = (ImageView) view.findViewById(R.id.iidbf_iv_comment);
        holder.confession.ivHeart = (ImageView) view.findViewById(R.id.iidbf_iv_heart);
        holder.confession.tvCommit = (TextView) view.findViewById(R.id.iidbf_tv_commentNum);
        holder.confession.tvHeart = (TextView) view.findViewById(R.id.iidbf_tv_likeNum);
        //初始化点击事件
        holder.confession.ivShare.setOnClickListener(this);
        holder.confession.ivCommit.setOnClickListener(this);
        holder.confession.ivHeart.setOnClickListener(this);
        holder.contentView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.confession.ivShare,
                holder.confession.ivCommit,
                holder.confession.ivHeart);
        SpecialData specialData = this.specialData.get(position);
        holder.confession.tvTitle.setText(specialData.getTitle());
        holder.confession.tvText.setText(specialData.getDoc());
        //设置图片
        List<String> img = specialData.getImg();
        if (img == null || img.size() == 0) {
            holder.confession.ivPic.setImageResource(R.color.default_picture);
            holder.confession.ivBgPic.setImageResource(R.color.default_picture);
        } else {
            String picUrl = img.get(0);
            String picBg = picUrl;
            if (img.size() > 1) {
                picBg = img.get(1);
            }
            loadImageByUrl(holder.confession.ivPic, picUrl);
            loadBlurImage(holder.confession.ivBgPic, picBg);
        }
        if (specialData.getHasFavorite()) {
            holder.confession.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.confession.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
        //其他
        holder.confession.tvCommit.setText(specialData.getCount_of_comment() + "");
        holder.confession.tvHeart.setText(specialData.getCount_of_favorite() + "");
        //是否标识
        if (specialData.getTag()) {
            holder.confession.ivTag.setVisibility(View.VISIBLE);
        } else {
            holder.confession.ivTag.setVisibility(View.GONE);
        }
    }

    private void loadBlurImage(ImageView ivBgPic, String picUrl) {
        ImageLoaderFactory.getInstance().displayBlurImage(ivBgPic, picUrl, 7, 2);
    }

    private void loadImageByUrl(ImageView ivPic, String picUrl) {
        ImageLoaderFactory.getInstance().displayImage(ivPic, picUrl);
    }

    @Override
    public int getItemCount() {
        return specialData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        View contentView;
        Confession confession;
    }

    private class Confession {
        ImageView ivBgPic;
        ImageView ivPic;
        TextView tvTitle;
        TextView tvText;
        ImageView ivTag;
        //功能栏
        ImageView ivShare;
        ImageView ivCommit;
        ImageView ivHeart;
        TextView tvCommit;
        TextView tvHeart;
    }

    public interface OnSpecialConfessionItemClickListener {

        /**
         * 条目被点击的额时候
         */
        void onItemClick(int position);

        /**
         * 当评论条目被点击的时候
         */
        void onCommentItemClick(int position);

        /**
         * 当分享条目被电击的时候
         */
        void onShareItemClick(int position);

        /**
         * 当喜欢条目被点击的时候
         */
        void onHeartItemClick(int position);
    }

    public void setOnSpecialConfessionItemClickListener(
            OnSpecialConfessionItemClickListener onSpecialConfessionItemClickListener) {
        this.onSpecialConfessionItemClickListener = onSpecialConfessionItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onSpecialConfessionItemClickListener == null) {
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
            case R.id.isc_content:
                onSpecialConfessionItemClickListener.onItemClick(position);
                break;
            case R.id.iidbf_iv_share:
                onSpecialConfessionItemClickListener.onShareItemClick(position);
                break;
            case R.id.iidbf_iv_comment:
                onSpecialConfessionItemClickListener.onCommentItemClick(position);
                break;
            case R.id.iidbf_iv_heart:
                onSpecialConfessionItemClickListener.onHeartItemClick(position);
                break;
        }
    }
}
