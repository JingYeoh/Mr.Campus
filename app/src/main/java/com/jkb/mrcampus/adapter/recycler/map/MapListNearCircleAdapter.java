package com.jkb.mrcampus.adapter.recycler.map;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;
import com.jkb.mrcampus.utils.DistanceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近的圈子的数据适配器
 * Created by JustKiddingBaby on 2016/11/14.
 */

public class MapListNearCircleAdapter extends
        RecyclerView.Adapter<MapListNearCircleAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    public List<CircleInfo> mCircleInfo;

    //监听器
    private CircleInSchoolItemClickListener circleInSchoolItemClickListener;

    public MapListNearCircleAdapter(Context context, List<CircleInfo> circleInfo) {
        this.context = context;
        if (circleInfo == null) {
            circleInfo = new ArrayList<>();
        }
        this.mCircleInfo = circleInfo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder = initCircleInSchool(inflater, parent);
        return holder;
    }

    /**
     * 初始化学校中的圈子数据
     */
    private ViewHolder initCircleInSchool(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_map_list_near_circle_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件
        holder.content = view.findViewById(R.id.imlncl_content);
        if (holder.circleInSchool == null) {
            holder.circleInSchool = new CircleInSchool();
        }
        holder.circleInSchool.tvName = (TextView) view.findViewById(R.id.imlncl_tv_name);
        holder.circleInSchool.tvType = (TextView) view.findViewById(R.id.imlncl_tv_circleType);
        holder.circleInSchool.tvOperationNum = (TextView) view.findViewById(R.id.imlncl_tv_operationCount);
        holder.circleInSchool.tvDynamicNum = (TextView) view.findViewById(R.id.imlncl_tv_dynamicsCount);
        holder.circleInSchool.ivPicture = (ImageView) view.findViewById(R.id.imlncl_iv_picture);
        holder.circleInSchool.tvDistance = (TextView) view.findViewById(R.id.imlncl_tv_distance);
        //初始化监听事件
        holder.content.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //绑定TAG
        ClassUtils.bindViewsTag(position, holder.content);
        //绑定数据
        CircleInfo circleInfo = mCircleInfo.get(position);
        holder.circleInSchool.tvName.setText(circleInfo.getCircleName());
        holder.circleInSchool.tvOperationNum.setText(circleInfo.getOperatorCount() + "");
        holder.circleInSchool.tvDynamicNum.setText(circleInfo.getDynamicsCount() + "");
        holder.circleInSchool.tvType.setText(circleInfo.getCircleType());
        String picUrl = circleInfo.getPictureUrl();
        if (!StringUtils.isEmpty(picUrl)) {
            loadImage(holder.circleInSchool.ivPicture, picUrl);
        } else {
            holder.circleInSchool.ivPicture.setImageResource(R.color.default_picture);
        }
        //判断距离
        holder.circleInSchool.tvDistance.setText(DistanceUtils.changeDistance(
                DistanceUtils.Calculate(circleInfo.getLatitude(), circleInfo.getLongitude())));
    }

    /**
     * 加载网络图片
     */
    private void loadImage(ImageView ivPicture, String picUrl) {
        ImageLoaderFactory.getInstance().displayImage(ivPicture, picUrl);
    }

    @Override
    public int getItemCount() {
        return mCircleInfo.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        View content;
        CircleInSchool circleInSchool;
    }

    /**
     * 学校中的圈子数据
     */
    private class CircleInSchool {
        TextView tvName;
        ImageView ivPicture;
        TextView tvType;
        TextView tvDynamicNum;
        TextView tvOperationNum;
        TextView tvDistance;
    }

    public interface CircleInSchoolItemClickListener {

        /**
         * 当条目被点击的时候
         */
        void onItemClick(int position);
    }

    public void setCircleInSchoolItemClickListener(
            CircleInSchoolItemClickListener circleInSchoolItemClickListener) {
        this.circleInSchoolItemClickListener = circleInSchoolItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (circleInSchoolItemClickListener == null) {
            return;
        }
        Bundle bundle = (Bundle) v.getTag();
        if (bundle == null) {
            return;
        }
        int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
        int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
        switch (viewId) {
            case R.id.imlncl_content:
                circleInSchoolItemClickListener.onItemClick(position);
                break;
        }
    }
}
