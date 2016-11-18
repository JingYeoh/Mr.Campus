package com.jkb.mrcampus.adapter.recycler.dynamicDetail.article;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.dynamicDetail.data.DynamicDetailArticleData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章主体展示的适配器
 * Created by JustKiddingBaby on 2016/9/25.
 */

public class ArticleContentShowAdapter extends
        RecyclerView.Adapter<ArticleContentShowAdapter.ViewHolder> implements View.OnClickListener {

    private static final String TAG = "ArticleContentAdapter";
    private Context context;
    public List<DynamicDetailArticleData.ArticleContent> articleContents;
    private List<String> pictures;
    private OnArticleContentItemClickListener onArticleContentItemClickListener;

    public ArticleContentShowAdapter(Context context,
                                     List<DynamicDetailArticleData.ArticleContent> articleContents) {
        this.context = context;
        if (articleContents == null) {
            articleContents = new ArrayList<>();
        }
        this.articleContents = articleContents;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_dynamic_detail_article_content_show, parent, false);
        ViewHolder holder = new ViewHolder(view);
        initView(holder, view);
        return holder;
    }

    /**
     * 初始化View
     */
    private void initView(ViewHolder holder, View view) {
        holder.ivImg = (ImageView) view.findViewById(R.id.iddacs_img);
        holder.tvDoc = (TextView) view.findViewById(R.id.iddacs_doc);
        holder.contentImg = view.findViewById(R.id.iddacs_contentPicture);
        holder.contentImg.setOnClickListener(this);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DynamicDetailArticleData.ArticleContent content = articleContents.get(position);
        if (content == null) {
            return;
        }
        ClassUtils.bindViewsTag(position,
                holder.contentImg);
        String doc = content.getDoc();
        if (StringUtils.isEmpty(doc)) {
            holder.tvDoc.setVisibility(View.GONE);
        } else {
            holder.tvDoc.setVisibility(View.VISIBLE);
            holder.tvDoc.setText(content.getDoc());
        }
        String img = content.getImg();
//        Log.d(TAG, "position=" + position + "\n"
//                + "img=" + img);
        if (StringUtils.isEmpty(img)) {
            holder.ivImg.setVisibility(View.GONE);
        } else {
            holder.ivImg.setVisibility(View.VISIBLE);
            //加载图片
            loaderImg(holder.ivImg, img);
        }
    }

    /**
     * 加载图片
     */
    private void loaderImg(ImageView ivImg, String img) {
        ImageLoaderFactory.getInstance().displayImage(ivImg, img);
    }

    @Override
    public int getItemCount() {
        return articleContents.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        View contentImg;
        ImageView ivImg;
        TextView tvDoc;
    }

    public interface OnArticleContentItemClickListener {

        /**
         * 图片被点击的时候
         */
        void onArticlePictureClick(int position);
    }

    public void setOnArticleContentItemClickListener(
            OnArticleContentItemClickListener onArticleContentItemClickListener) {
        this.onArticleContentItemClickListener = onArticleContentItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onArticleContentItemClickListener == null) {
            return;
        }
        //判断是哪个控件
        Bundle bundle = (Bundle) v.getTag();
        if (bundle == null) {
            return;
        }
        int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
        int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
        switch (viewId) {
            case R.id.iddacs_contentPicture:
                onArticleContentItemClickListener.onArticlePictureClick(position);
                break;
        }
    }
}
