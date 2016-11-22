package com.jkb.mrcampus.adapter.recycler.personCenter.original.myDynamic.circle;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子选择的页面适配器
 * Created by JustKiddingBaby on 2016/10/18.
 */

public class CircleSelectorAdapter extends RecyclerView.Adapter<CircleSelectorAdapter.ViewHolder> {

    private static final String TAG = "CircleSelectorAdapter";
    private Context context;
    public List<CircleInfo> circleInfos;

    //点击监听器
    private OnCircleSelectorListener onCircleSelectorListener;

    public CircleSelectorAdapter(Context context, List<CircleInfo> circleInfos) {
        this.context = context;
        if (circleInfos == null) {
            circleInfos = new ArrayList<>();
        }
        this.circleInfos = circleInfos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder = initSubscribeCircle(parent, inflater);
        return holder;
    }

    /**
     * 初始化订阅的圈子视图
     */
    private ViewHolder initSubscribeCircle(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_float_circle_selector, parent, false);
        ViewHolder holder = new ViewHolder(view);
        if (holder.subscribeCircle == null) {
            holder.subscribeCircle = new SubscribeCircle();
        }
        //初始化id
        holder.subscribeCircle.content = view.findViewById(R.id.ifcs_content);
        holder.subscribeCircle.aboutContent = view.findViewById(R.id.ifcs_ll_aboutContent);
        holder.subscribeCircle.tvCircleName = (TextView) view.findViewById(R.id.ifcs_tv_circleName);
        holder.subscribeCircle.tvCircleType = (TextView) view.findViewById(R.id.ifcs_tv_circleType);
        holder.subscribeCircle.tvCircleDynamicCount = (TextView)
                view.findViewById(R.id.ifcs_tv_DynamicCount);
        holder.subscribeCircle.tvCircleFavoriteCount = (TextView)
                view.findViewById(R.id.ifcs_tv_subscribeCount);
        holder.subscribeCircle.ivCirclePic = (ImageView) view.findViewById(R.id.ifcs_iv_circlePic);
        //初始化监听事件
        holder.subscribeCircle.content.setOnClickListener(clickCircleSelector);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            bindAllSubscribeCircle(holder);
        } else {
            bindSubscribeCircle(holder, position - 1);
        }
    }

    /**
     * 绑定所有圈子的数据
     */
    private void bindAllSubscribeCircle(ViewHolder holder) {

        ClassUtils.bindViewsTag(-1,
                holder.subscribeCircle.content);

        holder.subscribeCircle.tvCircleName.setText("全部圈子");
        holder.subscribeCircle.ivCirclePic.setImageResource(R.drawable.ic_default_personal);
        holder.subscribeCircle.aboutContent.setVisibility(View.GONE);
    }

    /**
     * 初始化订阅的圈子
     */
    private void bindSubscribeCircle(ViewHolder holder, int position) {
        CircleInfo circleInfo = circleInfos.get(position);
        if (circleInfo == null) {
            holder.subscribeCircle.content.setVisibility(View.GONE);
            return;
        }

        ClassUtils.bindViewsTag(position,
                holder.subscribeCircle.content);

        holder.subscribeCircle.aboutContent.setVisibility(View.VISIBLE);
        holder.subscribeCircle.tvCircleName.setText(circleInfo.getCircleName());
        holder.subscribeCircle.tvCircleType.setText(circleInfo.getCircleType());
        holder.subscribeCircle.tvCircleDynamicCount.setText(circleInfo.getDynamicsCount() + "");
        holder.subscribeCircle.tvCircleFavoriteCount.setText(circleInfo.getOperatorCount() + "");
        String pictureUrl = circleInfo.getPictureUrl();
//        Log.d(TAG, "我收的到的图片网址是：" + pictureUrl);
        if (StringUtils.isEmpty(pictureUrl)) {
            holder.subscribeCircle.ivCirclePic.setImageResource(R.color.default_picture);
        } else {
            loadImage(holder.subscribeCircle.ivCirclePic, pictureUrl);
        }
    }

    /**
     * 加载图片
     */
    private void loadImage(ImageView ivCirclePic, String pictureUrl) {
        ImageLoaderFactory.getInstance().displayImage(ivCirclePic, pictureUrl);
    }

    @Override
    public int getItemCount() {
        return circleInfos.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        SubscribeCircle subscribeCircle;
    }

    private class SubscribeCircle {
        View aboutContent;
        View content;
        TextView tvCircleName;
        ImageView ivCirclePic;
        TextView tvCircleType;
        TextView tvCircleDynamicCount;
        TextView tvCircleFavoriteCount;
    }

    public interface OnCircleSelectorListener {
        /**
         * 所有圈子被点击的时候
         */
        void onAllCircleClick();

        /**
         * 当圈子被选择到的时候
         *
         * @param circleInfo 圈子信息实体类
         */
        void onCircleSelected(CircleInfo circleInfo);
    }

    public void setOnCircleSelectorListener(OnCircleSelectorListener onCircleSelectorListener) {
        this.onCircleSelectorListener = onCircleSelectorListener;
    }

    private View.OnClickListener clickCircleSelector = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onCircleSelectorListener == null) {
                return;
            }
            //判断是哪个控件
            Bundle bundle = (Bundle) v.getTag();
            if (bundle == null) {
                return;
            }
            int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
            int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
            Log.d(TAG, "我被点击了" + position);
            switch (viewId) {
                case R.id.ifcs_content:
                    if (position == -1) {
                        onCircleSelectorListener.onAllCircleClick();
                    } else {
                        onCircleSelectorListener.onCircleSelected(circleInfos.get(position));
                    }
                    break;
            }
        }
    };
}
