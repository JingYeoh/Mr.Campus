package com.jkb.mrcampus.adapter.custom.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.info.map.MapMarkCircleInfo;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.BitmapUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图标注：圈子适配器类
 * Created by JustKiddingBaby on 2016/10/4.
 */

public class MapMarkCircleAdapter {

    private Context context;
    private BaiduMap mBaiduMap;
    public MapMarkCircleInfo mapMarkCircleInfo;

    //data
    private List<View> contentViews;

    public MapMarkCircleAdapter(Context context, BaiduMap mBaiduMap,
                                MapMarkCircleInfo mapMarkCircleInfo) {
        this.context = context;
        this.mBaiduMap = mBaiduMap;
        if (mapMarkCircleInfo == null) {
            mapMarkCircleInfo = new MapMarkCircleInfo();
        }
        this.mapMarkCircleInfo = mapMarkCircleInfo;

        contentViews = new ArrayList<>();
        initViews();
    }

    /**
     * 初始化视图
     */
    private void initViews() {
        contentViews.clear();
        for (int i = 0; i < getCount(); i++) {
            contentViews.add(getView(i, null));
        }
    }

    /**
     * 显示地图圈子标注
     */
    public void showMapMarkCircleOverlay() {
        addInfosOverlay();
    }

    /**
     * 更新数据
     */
    public void notifyDataSetChanged() {
        initViews();
        showMapMarkCircleOverlay();
    }

    /**
     * 添加标注覆盖物
     */
    private void addInfosOverlay() {
        mBaiduMap.clear();//移除所有的覆盖物
        if (mapMarkCircleInfo == null) {
            return;
        }
        List<CircleInfo> circles = mapMarkCircleInfo.getCircles();
        if (circles == null || circles.size() == 0) {
            return;
        }
        LatLng latLng;
        OverlayOptions overlayOptions;
        for (int i = 0; i < circles.size(); i++) {
            CircleInfo circle = circles.get(i);
            // 构建位置
            latLng = new LatLng(circle.getLatitude(), circle.getLongitude());
            //构建Marker图标
//            Bitmap bitmapMarker = BitmapUtil.getViewBitmap(contentViews.get(i));
//            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmapMarker);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.
                    fromResource(R.drawable.car_ic_map_stop_focus);
            //构建MarkerOption，用于在地图上添加
            overlayOptions = new MarkerOptions().position(latLng)
                    .icon(bitmapDescriptor).zIndex(5);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(overlayOptions);
        }
    }

    /**
     * 得到条目数
     */
    private int getCount() {
        if (mapMarkCircleInfo == null) {
            return 0;
        }
        List<CircleInfo> circles = mapMarkCircleInfo.getCircles();
        if (circles == null) {
            return 0;
        } else {
            return circles.size();
        }
    }

    /**
     * 设置View视图
     *
     * @param position 条目数
     */
    private View getView(int position, View contentView) {
        ViewHolder holder;
        if (contentView == null) {
            contentView = LayoutInflater.from(context).
                    inflate(R.layout.item_map_mark_overlay_circle, null);
            holder = new ViewHolder();
            //初始化id
            initViewId(contentView, holder);
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }
        initView(position, holder);
        return contentView;
    }


    /**
     * 初始化视图
     */
    private void initView(int position, ViewHolder holder) {
        List<CircleInfo> circles = mapMarkCircleInfo.getCircles();
        if (circles == null) {
            return;
        }
        CircleInfo circleInfo = circles.get(position);
        holder.tvName.setText(circleInfo.getCircleName());
        String pictureUrl = circleInfo.getPictureUrl();
        if (StringUtils.isEmpty(pictureUrl)) {
            holder.ivPic.setImageResource(R.color.default_picture);
        } else {
            loadImage(pictureUrl, holder.ivPic);
        }
    }


    /**
     * 初始化视图id
     */
    private void initViewId(View contentView, ViewHolder holder) {
        holder.ivPic = (ImageView) contentView.findViewById(R.id.immoc_iv_pic);
        holder.tvName = (TextView) contentView.findViewById(R.id.immoc_tv_name);
    }

    /**
     * 加载图片
     */
    private void loadImage(String pictureUrl, ImageView ivPic) {
        ImageLoaderFactory.getInstance().displayImage(ivPic, pictureUrl);
    }

    class ViewHolder {
        ImageView ivPic;
        TextView tvName;
    }
}
