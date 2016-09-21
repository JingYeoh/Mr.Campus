package com.jkb.mrcampus.adapter.recycler.personCenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.presenter.personCenter.data.CircleData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心——我的圈子的适配器
 * Created by JustKiddingBaby on 2016/8/16.
 */

public class PersonCenterCircleAdapter extends RecyclerView.Adapter<PersonCenterCircleAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    public List<CircleData> circleDatas = null;
    private OnCircleItemClickListener onCircleItemClickListener;

    public PersonCenterCircleAdapter(Context context, List<CircleData> circleDatas) {
        this.context = context;
        if (circleDatas == null) {
            circleDatas = new ArrayList<>();
        }
        this.circleDatas = circleDatas;
    }

    /**
     * 设置条目点击事件的监听器
     */
    public void setOnCircleItemClickListener(
            @NonNull OnCircleItemClickListener onCircleItemClickListener) {
        this.onCircleItemClickListener = onCircleItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person_center_circle, parent, false);
        PersonCenterCircleAdapter.ViewHolder holder = new ViewHolder(view);
        //初始化控件
        holder.contentView = view.findViewById(R.id.ipcc_content);
        holder.ivPicture = (ImageView) view.findViewById(R.id.ipcc_iv_picture);
        holder.tvCircleName = (TextView) view.findViewById(R.id.ipcc_tv_circleName);
        holder.tvCircleTpe = (TextView) view.findViewById(R.id.ipcc_tv_circleType);
        holder.tvDynamicsCount = (TextView) view.findViewById(R.id.ipcc_tv_dynamicsCount);
        holder.tvOperationCOunt = (TextView) view.findViewById(R.id.ipcc_tv_operationCount);

        holder.contentView.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //绑定控件的TAG
        ClassUtils.bindViewsTag(position, holder.contentView);

        //绑定数据
        CircleData data = circleDatas.get(position);
        holder.tvCircleName.setText(data.getCircleName());
        holder.tvCircleTpe.setText(data.getCircleType());
        holder.tvDynamicsCount.setText(data.getDynamics_count() + "");
        holder.tvOperationCOunt.setText(data.getOperation_count() + "");
        String pictureUrl = data.getPictureUrl();
        //设置头像
        if (!StringUtils.isEmpty(pictureUrl)) {
//            setImageLoad(holder.ivPicture, pictureUrl);
            loadImage(holder.ivPicture, pictureUrl);
        } else {
            holder.ivPicture.setImageResource(R.drawable.aliwx_head_bg_0);
        }
    }

    /**
     * 加載圖片
     */
    private void loadImage(ImageView imageView, String imageUrl) {
        ImageLoaderFactory.getInstance().displayImage(imageView, imageUrl);
    }

    /**
     * 加载头像
     */
    @Deprecated
    private void setImageLoad(final ImageView tvHeadImg, String headImgUrl) {
        ImageLoaderFactory.getInstance().loadImage(headImgUrl, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                tvHeadImg.setImageResource(R.drawable.aliwx_head_bg_0);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                tvHeadImg.setImageResource(R.drawable.aliwx_head_bg_0);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                tvHeadImg.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                tvHeadImg.setImageResource(R.drawable.aliwx_head_bg_0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return circleDatas.size();
    }

    @Override
    public void onClick(View view) {
        if (onCircleItemClickListener != null) {
            //判断是哪个控件
            Bundle bundle = (Bundle) view.getTag();
            if (bundle == null) {
                return;
            }
            int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
            int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
            switch (viewId) {
                case R.id.ipcc_content:
                    onCircleItemClickListener.onItemClick(position);
                    break;
            }
        }
    }

    /**
     * ViewHolder类
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        TextView tvCircleName;//圈子名称
        TextView tvCircleTpe;//圈子类型
        TextView tvDynamicsCount;//动态总数
        TextView tvOperationCOunt;//订阅总数
        ImageView ivPicture;
        View contentView;//内容
    }

    /**
     * 圈子条目的点击事件的监听器
     */
    public interface OnCircleItemClickListener {
        /**
         * 条目点击方法
         *
         * @param position 被点击的条目
         */
        void onItemClick(int position);
    }
}
