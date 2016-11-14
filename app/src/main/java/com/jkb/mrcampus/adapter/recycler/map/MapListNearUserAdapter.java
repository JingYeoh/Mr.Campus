package com.jkb.mrcampus.adapter.recycler.map;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;
import com.jkb.mrcampus.utils.DistanceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近的人的页面适配器
 * Created by JustKiddingBaby on 2016/11/13.
 */

public class MapListNearUserAdapter extends RecyclerView.Adapter<MapListNearUserAdapter.ViewHolder> implements View.OnClickListener {

    //data
    private Context context;
    public List<UserInfo> userInfo;

    private int[] bgColors = new int[]{R.drawable.selecter_clearity_gravy,
            R.drawable.selector_white_general};

    //监听器
    private OnNearUserItemClickListener onNearUserItemClickListener;

    public MapListNearUserAdapter(Context context, List<UserInfo> userInfo) {
        this.context = context;
        if (userInfo == null) {
            userInfo = new ArrayList<>();
        }
        this.userInfo = userInfo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder = initNearUserView(inflater, parent);
        return holder;
    }

    /**
     * 初始化附近的用户的View控件
     */
    private ViewHolder initNearUserView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_near_user, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件
        if (holder.nearUser == null) {
            holder.nearUser = new NearUser();
        }
        holder.contentView = view.findViewById(R.id.inu_content);
        holder.nearUser.ivHeadImg = (ImageView) view.findViewById(R.id.inu_iv_headImg);
        holder.nearUser.tvNickName = (TextView) view.findViewById(R.id.inu_tv_nickName);
        holder.nearUser.tvDistance = (TextView) view.findViewById(R.id.inu_tv_distance);
        holder.nearUser.tvSchoolName = (TextView) view.findViewById(R.id.inu_tv_schoolName);
        holder.nearUser.tvSex = (TextView) view.findViewById(R.id.inu_tv_sex);
        //初始化监听事件
        holder.contentView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //绑定控件
        bindNearUserData(holder, position);
    }

    /**
     * 绑定附近的人的控件
     */
    private void bindNearUserData(ViewHolder holder, int position) {
        holder.contentView.setBackgroundResource(bgColors[position % 2]);
        //设置TAG
        ClassUtils.bindViewsTag(position, holder.contentView);
        //设置信息
        UserInfo userInfo = this.userInfo.get(position);
        holder.nearUser.tvNickName.setText(userInfo.getNickName());
        holder.nearUser.tvSex.setText(userInfo.getSex());
        if (userInfo.getSchool() != null) {
            holder.nearUser.tvSchoolName.setVisibility(View.VISIBLE);
            holder.nearUser.tvSchoolName.setText(userInfo.getSchool().getSname());
        }else{
            holder.nearUser.tvSchoolName.setVisibility(View.GONE);
        }
        //设置头像
        String avatar = userInfo.getAvatar();
        if (StringUtils.isEmpty(avatar)) {
            holder.nearUser.ivHeadImg.setVisibility(View.GONE);
        } else {
            holder.nearUser.ivHeadImg.setVisibility(View.VISIBLE);
            loadImageByUrl(holder.nearUser.ivHeadImg, avatar);
        }
        //判断距离
        holder.nearUser.tvDistance.setText(DistanceUtils.changeDistance(
                DistanceUtils.Calculate(userInfo.getLatitude(), userInfo.getLongitude())));
    }

    /**
     * 加载网络头像
     */
    private void loadImageByUrl(ImageView ivHeadImg, String avatar) {
        ImageLoaderFactory.getInstance().displayImage(ivHeadImg, avatar);
    }

    @Override
    public int getItemCount() {
        return userInfo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        //用户数据
        View contentView;
        NearUser nearUser;
    }

    private class NearUser {
        TextView tvNickName;
        ImageView ivHeadImg;
        TextView tvDistance;
        TextView tvSex;
        TextView tvSchoolName;
    }

    /**
     * 附近的人的条目点击监听器
     */
    public interface OnNearUserItemClickListener {

        /**
         * 当条目被点击时触发
         */
        void onItemClick(int position);
    }

    /**
     * 设置附近的人的点击监听事件
     */
    public void setOnNearUserItemClickListener(
            OnNearUserItemClickListener onNearUserItemClickListener) {
        this.onNearUserItemClickListener = onNearUserItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onNearUserItemClickListener == null) {
            return;
        }
        //判断是哪个控件
        Bundle bundle = (Bundle) v.getTag();
        if (bundle == null) return;
        int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
        int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
        switch (viewId) {
            case R.id.inu_content:
                onNearUserItemClickListener.onItemClick(position);
                break;
        }
    }
}
