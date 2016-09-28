package com.jkb.mrcampus.adapter.recycler.dynamicCreate;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.jkb.core.contract.dynamicCreate.data.DynamicCreateArticleData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建文章动态的适配器
 * Created by JustKiddingBaby on 2016/9/27.
 */

public class DynamicCreateArticleAdapter extends
        RecyclerView.Adapter<DynamicCreateArticleAdapter.ViewHolder> {

    private Context context;
    public List<DynamicCreateArticleData> dynamicCreateArticleDatas;

    //data
    private OnItemImgClickListener onItemImgClickListener;


    public DynamicCreateArticleAdapter(
            Context context, List<DynamicCreateArticleData> dynamicCreateArticleDatas) {
        this.context = context;
        if (dynamicCreateArticleDatas == null) {
            dynamicCreateArticleDatas = new ArrayList<>();
        }
        this.dynamicCreateArticleDatas = dynamicCreateArticleDatas;
    }

    @Override
    public DynamicCreateArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_create_dynamic_article,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        initView(holder, view);
        return holder;
    }

    /**
     * 初始化视图
     */
    private void initView(DynamicCreateArticleAdapter.ViewHolder holder, View view) {
        holder.etDoc = (EditText) view.findViewById(R.id.icda_et_doc);
        holder.ivImg = (ImageView) view.findViewById(R.id.icda_iv_img);
        holder.contentImg = view.findViewById(R.id.icda_contentImg);

        holder.contentImg.setOnClickListener(clickItemImgListener);
    }

    @Override
    public void onBindViewHolder(final DynamicCreateArticleAdapter.ViewHolder holder, final int position) {
        final DynamicCreateArticleData data = dynamicCreateArticleDatas.get(position);
        //绑定TAG
        ClassUtils.bindViewsTag(position, holder.contentImg, holder.etDoc);
        String url = data.getArticleImgUrl();
        if (StringUtils.isEmpty(url)) {
            holder.ivImg.setVisibility(View.GONE);
        } else {
            holder.ivImg.setVisibility(View.VISIBLE);
            loaderImg(holder.ivImg, url);
        }
        holder.etDoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String doc = holder.etDoc.getText().toString();
                data.setArticleContent(doc);
                dynamicCreateArticleDatas.set(position, data);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String doc = holder.etDoc.getText().toString();
                data.setArticleContent(doc);
                dynamicCreateArticleDatas.set(position, data);
            }
        });
    }

    /**
     * 加载图片
     */
    private void loaderImg(ImageView ivImg, String url) {
        ImageLoaderFactory.getInstance().displayImage(ivImg, url);
    }

    @Override
    public int getItemCount() {
        return dynamicCreateArticleDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        EditText etDoc;
        ImageView ivImg;
        View contentImg;
    }

    /**
     * 设置条目中的图片点击的监听器
     *
     * @param onItemImgClickListener 图片点击的监听器
     */
    public void setOnItemImgClickListener(OnItemImgClickListener onItemImgClickListener) {
        this.onItemImgClickListener = onItemImgClickListener;
    }

    private View.OnClickListener clickItemImgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onItemImgClickListener == null) {
                return;
            }
            Bundle bundle = (Bundle) v.getTag();
            if (bundle == null) {
                return;
            }
            int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
            int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
            switch (viewId) {
                case R.id.icda_contentImg:
                    onItemImgClickListener.onItemImgClick(position);
                    break;
            }
        }
    };

    /**
     * 得到DOC的id
     */
    public int getDocId() {
        return R.id.icda_et_doc;
    }

    /**
     * 条目的图片点击的监听器
     */
    public interface OnItemImgClickListener {

        /**
         * 当条目中的图片被点击的时候
         *
         * @param position 条目数
         */
        void onItemImgClick(int position);
    }
}
