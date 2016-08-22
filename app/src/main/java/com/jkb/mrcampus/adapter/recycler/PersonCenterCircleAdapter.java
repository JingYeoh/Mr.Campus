package com.jkb.mrcampus.adapter.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.presenter.personCenter.data.CircleData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心——我的圈子的适配器
 * Created by JustKiddingBaby on 2016/8/16.
 */

public class PersonCenterCircleAdapter extends RecyclerView.Adapter<PersonCenterCircleAdapter.ViewHolder> {

    private Context context;
    public List<CircleData> circleDatas = null;

    public PersonCenterCircleAdapter(Context context, List<CircleData> circleDatas) {
        this.context = context;
        if (circleDatas == null) {
            circleDatas = new ArrayList<>();
        }
        this.circleDatas = circleDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person_center_circle, parent, false);
        PersonCenterCircleAdapter.ViewHolder holder = new ViewHolder(view);
        //初始化控件
        holder.ivPicture = (ImageView) view.findViewById(R.id.ipcc_iv_picture);
        holder.tvCircleName = (TextView) view.findViewById(R.id.ipcc_tv_circleName);
        holder.tvCircleTpe = (TextView) view.findViewById(R.id.ipcc_tv_circleType);
        holder.tvDynamicsCount = (TextView) view.findViewById(R.id.ipcc_tv_dynamicsCount);
        holder.tvOperationCOunt = (TextView) view.findViewById(R.id.ipcc_tv_operationCount);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //绑定数据
        CircleData data = circleDatas.get(position);
        holder.tvCircleName.setText(data.getCircleName());
        holder.tvCircleTpe.setText(data.getCircleType());
        holder.tvDynamicsCount.setText(data.getDynamics_count() + "");
        holder.tvOperationCOunt.setText(data.getOperation_count() + "");
        String pictureUrl = data.getPictureUrl();
        //设置头像
        if (!StringUtils.isEmpty(pictureUrl)) {
            setImageLoad(holder.ivPicture, pictureUrl);
        } else {
            holder.ivPicture.setImageResource(R.drawable.aliwx_head_bg_0);
        }
    }

    /**
     * 加载头像
     */
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
    }
}
