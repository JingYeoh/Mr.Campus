package com.jkb.mrcampus.adapter.recycler.menuRight;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.menu.data.FriendsData;
import com.jkb.model.net.GlideImageLoader;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.adapter.recycler.CircleListAdapter;
import com.jkb.mrcampus.utils.ClassUtils;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 好友列表的数据适配器
 * Created by JustKiddingBaby on 2016/9/6.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> implements View.OnClickListener {


    private Context context;
    public List<FriendsData> friendsDatas;

    private FriendsAdapter.OnCircleItemClickListener onCircleItemClickListener;

    public FriendsAdapter(Context context, List<FriendsData> friendsDatas) {
        this.context = context;
        if (friendsDatas == null) {
            friendsDatas = new ArrayList<>();
        }
        this.friendsDatas = friendsDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_friend, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        //初始化id
        viewHolder.ivHeadImg = (ImageView) view.findViewById(R.id.icf_iv_headImg);
        viewHolder.tvNickName = (TextView) view.findViewById(R.id.icf_tv_name);
        viewHolder.tvSign = (TextView) view.findViewById(R.id.icf_tv_sign);
        viewHolder.content = view.findViewById(R.id.icf_content);

        viewHolder.content.findViewById(R.id.icf_content).setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //绑定控件的TAG
        ClassUtils.bindViewsTag(position, holder.content);

        FriendsData data = friendsDatas.get(position);
        if (data == null) {
            friendsDatas.remove(position);
            return;
        }
        holder.tvNickName.setText(data.getNickName());
        holder.tvSign.setText(data.getBref());
        //设置头像
        String avatar = data.getHeadImgUrl();
        if (!StringUtils.isEmpty(avatar)) {
            //加載頭像
//            setImageLoad(holder.ivHeadImg, avatar);
            loadImage(holder.ivHeadImg, avatar);
        }
    }

    /**
     * 加载图片
     *
     * @param ivHeadImg 头像
     * @param avatar    头像地址
     */
    private void loadImage(ImageView ivHeadImg, String avatar) {
//        MyBannerImageLoader.loadNormalImage(context, ivHeadImg, avatar);
        ImageLoaderFactory.getInstance().displayImage(ivHeadImg,avatar);
    }


    /**
     * 加载头像
     */
    private void setImageLoad(final ImageView tvHeadImg, String headImgUrl) {
        ImageLoaderFactory.getInstance().loadImage(headImgUrl, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                tvHeadImg.setImageResource(R.color.default_picture);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                tvHeadImg.setImageResource(R.color.default_picture);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                tvHeadImg.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                tvHeadImg.setImageResource(R.color.default_picture);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendsDatas.size();
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
                case R.id.icf_content:
                    onCircleItemClickListener.onClick(position);
                    break;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        ImageView ivHeadImg;
        TextView tvNickName;
        TextView tvSign;
        View content;
    }

    /**
     * 设置条目监听点击事件
     */
    public void setOnUserItemClickListener(FriendsAdapter.OnCircleItemClickListener onCircleItemClickListener) {
        this.onCircleItemClickListener = onCircleItemClickListener;
    }

    public interface OnCircleItemClickListener {
        void onClick(int position);
    }
}
