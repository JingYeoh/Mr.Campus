package com.jkb.mrcampus.adapter.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.jkb.mrcampus.utils.BitmapUtil;
import com.jkb.mrcampus.utils.ClassUtils;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子列表的适配器
 * Created by JustKiddingBaby on 2016/9/2.
 */

public class CircleListAdapter extends RecyclerView.Adapter<CircleListAdapter.ViewHolder>
        implements View.OnClickListener {

    private Context context;
    public List<CircleData> circleDatas;

    private OnCircleItemClickListener onCircleItemClickListener;

    public CircleListAdapter(Context context, List<CircleData> circleDatas) {
        this.context = context;
        if (circleDatas == null) {
            circleDatas = new ArrayList<>();
        }
        this.circleDatas = circleDatas;
    }

    public void setOnCircleItemClickListener(OnCircleItemClickListener onCircleItemClickListener) {
        this.onCircleItemClickListener = onCircleItemClickListener;
    }

    @Override
    public CircleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_circle_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.content = view.findViewById(R.id.icl_content);
        viewHolder.tvName = (TextView) view.findViewById(R.id.icl_tv_name);
        viewHolder.tvType = (TextView) view.findViewById(R.id.icl_tv_circleType);
        viewHolder.tvOperationNum = (TextView) view.findViewById(R.id.icl_tv_operationCount);
        viewHolder.tvDynamicNum = (TextView) view.findViewById(R.id.icl_tv_dynamicsCount);
        viewHolder.ivPicture = (ImageView) view.findViewById(R.id.icl_iv_picture);

        viewHolder.content.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CircleListAdapter.ViewHolder holder, int position) {

        //绑定控件的TAG
        ClassUtils.bindViewsTag(position, holder.content);

        CircleData data = circleDatas.get(position);
        holder.tvName.setText(data.getCircleName());
        holder.tvOperationNum.setText(data.getOperation_count() + "");
        holder.tvDynamicNum.setText(data.getDynamics_count() + "");
        holder.tvType.setText(data.getCircleType());
        String picUrl = data.getPictureUrl();
        if (!StringUtils.isEmpty(picUrl)) {
            loadImage(holder.ivPicture, picUrl);
        } else {
            holder.ivPicture.setImageResource(R.color.default_picture);
        }
    }

    /**
     * 加载图片
     */
    private void loadImage(ImageView ivPicture, String picUrl) {
        ImageLoaderFactory.getInstance().displayImage(ivPicture, picUrl);
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
                case R.id.icl_content:
                    onCircleItemClickListener.onClick(position);
                    break;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        TextView tvName;
        ImageView ivPicture;
        TextView tvType;
        TextView tvDynamicNum;
        TextView tvOperationNum;
        View content;
    }

    public interface OnCircleItemClickListener {
        void onClick(int position);
    }
}
