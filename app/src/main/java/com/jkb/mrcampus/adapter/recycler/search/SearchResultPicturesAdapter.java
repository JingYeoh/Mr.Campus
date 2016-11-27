package com.jkb.mrcampus.adapter.recycler.search;

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
 * 专题详情：吐槽墙的图片数据适配器
 * Created by JustKiddingBaby on 2016/11/24.
 */

public class SearchResultPicturesAdapter extends RecyclerView.Adapter
        <SearchResultPicturesAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    public List<String> picturesUlr;
    public int parentPosition;

    private OnSearchPicturesItemClickListener onSearchPicturesItemClickListener;

    public SearchResultPicturesAdapter(Context context, List<String> pictureUlrs) {
        this.context = context;
        if (pictureUlrs == null) {
            pictureUlrs = new ArrayList<>();
        }
        this.picturesUlr = pictureUlrs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_search_result_pictures, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.ivPic = (ImageView) view.findViewById(R.id.isrp_iv_picture);
        holder.picPlace = view.findViewById(R.id.isrp_iv_picturePlace);
        holder.picPlace.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassUtils.bindViewsTag(position,
                holder.picPlace);
        String url = picturesUlr.get(position);
        ImageLoaderFactory.getInstance().displayImage(holder.ivPic, url);
    }

    @Override
    public int getItemCount() {
        return picturesUlr.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        ImageView ivPic;
        View picPlace;
    }

    public interface OnSearchPicturesItemClickListener {

        /**
         * 当吐槽图片呗点击
         */
        void onPictureItemClick(int parentPosition, int position);
    }

    public void setOnSearchPicturesItemClickListener(
            OnSearchPicturesItemClickListener onSearchPicturesItemClickListener) {
        this.onSearchPicturesItemClickListener = onSearchPicturesItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onSearchPicturesItemClickListener == null) {
            return;
        }
        Bundle bundle = (Bundle) v.getTag();
        if (bundle == null) {
            return;
        }
        int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
        int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
        switch (viewId) {
            case R.id.isrp_iv_picturePlace:
                onSearchPicturesItemClickListener.onPictureItemClick(parentPosition, position);
                break;
        }
    }
}
