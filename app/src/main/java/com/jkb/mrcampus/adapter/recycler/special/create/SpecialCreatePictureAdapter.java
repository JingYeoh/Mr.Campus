package com.jkb.mrcampus.adapter.recycler.special.create;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建专题的图片显示的数据适配器
 * Created by JustKiddingBaby on 2016/11/21.
 */

public class SpecialCreatePictureAdapter extends
        RecyclerView.Adapter<SpecialCreatePictureAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    public List<String> picturesPath;

    private OnSpecialCreateItemClickListener onSpecialCreateItemClickListener;

    public SpecialCreatePictureAdapter(Context context, List<String> picturesPath) {
        this.context = context;
        if (picturesPath == null) {
            picturesPath = new ArrayList<>();
        }
        this.picturesPath = picturesPath;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_subject_create_picture_show, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化ui
        holder.contentView = view.findViewById(R.id.iscps_content);
        holder.ivPicture = (ImageView) view.findViewById(R.id.iscps_iv_picture);
        holder.ivDelete = view.findViewById(R.id.iscps_iv_delete);
        //设置监听事件
        holder.contentView.setOnClickListener(this);
        holder.ivDelete.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.ivDelete);
        String path = picturesPath.get(position);
        //加载图片
        ImageLoaderFactory.getInstance().displayFromSDCard(path, holder.ivPicture);
    }

    @Override
    public int getItemCount() {
        return picturesPath.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        View contentView;
        ImageView ivPicture;
        View ivDelete;
    }

    public interface OnSpecialCreateItemClickListener {

        /**
         * 整体条目被点击
         */
        void onItemClick(int position);

        /**
         * 删除条目被点击
         */
        void onDeleteItemClick(int position);
    }

    public void setOnSpecialCreateItemClickListener(
            OnSpecialCreateItemClickListener onSpecialCreateItemClickListener) {
        this.onSpecialCreateItemClickListener = onSpecialCreateItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onSpecialCreateItemClickListener == null) {
            return;
        }
        Bundle bundle = (Bundle) v.getTag();
        if (bundle == null) {
            return;
        }
        int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
        int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
        switch (viewId) {
            case R.id.iscps_content:
                onSpecialCreateItemClickListener.onItemClick(position);
                break;
            case R.id.iscps_iv_delete:
                onSpecialCreateItemClickListener.onDeleteItemClick(position);
                break;
        }
    }
}
