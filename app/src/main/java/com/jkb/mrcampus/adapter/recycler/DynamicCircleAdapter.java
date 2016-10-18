package com.jkb.mrcampus.adapter.recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.api.config.Config;
import com.jkb.core.data.dynamic.circle.DynamicInCircle;
import com.jkb.core.data.dynamic.circle.original.DynamicInCircleOriginalArticle;
import com.jkb.core.data.dynamic.circle.original.DynamicInCircleOriginalNormal;
import com.jkb.core.data.dynamic.circle.original.DynamicInCircleOriginalTopic;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子动态的适配器
 * Created by JustKiddingBaby on 2016/8/31.
 */

public class DynamicCircleAdapter extends RecyclerView.Adapter<DynamicCircleAdapter.ViewHolder> {

    private static final String TAG = "DynamicCircleAdapter";
    private Context context;
    public List<DynamicInCircle> dynamicInCircles;

    //常量
    private static final int DYNAMIC_TYPE_ARTICLE = 1001;
    private static final int DYNAMIC_TYPE_NORMAL = 1002;
    private static final int DYNAMIC_TYPE_TOPIC = 1003;

    //回调
    private OnDynamicInCircleItemClickListener onDynamicInCircleItemClickListener;
    private OnHeadImgClickListener onHeadImgClickListener;
    private OnCommentClickListener onCommentClickListener;
    private OnLikeClickListener onLikeClickListener;
    private OnShareClickListener onShareClickListener;


    public DynamicCircleAdapter(Context context, List<DynamicInCircle> dynamicInCircles) {
        this.context = context;
        if (dynamicInCircles == null) {
            dynamicInCircles = new ArrayList<>();
        }
        this.dynamicInCircles = dynamicInCircles;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        DynamicInCircle dynamicInCircle = dynamicInCircles.get(position);
        String dType = dynamicInCircle.getDtype();
        switch (dType) {
            case Config.D_TYPE_ARTICLE:
                viewType = DYNAMIC_TYPE_ARTICLE;
                break;
            case Config.D_TYPE_NORMAL:
                viewType = DYNAMIC_TYPE_NORMAL;
                break;
            case Config.D_TYPE_TOPIC:
                viewType = DYNAMIC_TYPE_TOPIC;
                break;
            default:
                viewType = 0;
                break;
        }
        return viewType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder;
        switch (viewType) {
            case DYNAMIC_TYPE_ARTICLE:
                holder = initDynamic_article(parent, inflater);
                break;
            case DYNAMIC_TYPE_NORMAL:
                holder = initDynamic_normal(parent, inflater);
                break;
            case DYNAMIC_TYPE_TOPIC:
                holder = initDynamic_topic(parent, inflater);
                break;
            default:
                holder = initBlankDynamic(parent, inflater);
                break;
        }
        holder.viewType = viewType;
        //初始化id
        return holder;
    }


    /**
     * 初始化空白的布局
     */
    private ViewHolder initBlankDynamic(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_dynamic_blank, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化id
        holder.dynamicContent = view.findViewById(R.id.dynamic_content);
        return holder;
    }

    /**
     * 初始化动态：文章
     */
    private ViewHolder initDynamic_article(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_dynamic_circle_article, parent, false);
        ViewHolder holder = new ViewHolder(view);

        if (holder.original_article == null) {
            holder.original_article = new DynamicOriginal_Article();
        }

        //初始化id
        holder.dynamicContent = view.findViewById(R.id.dynamic_content);
        holder.original_article.tvTag = (TextView) view.findViewById(R.id.idc_tv_tag);
        //作者
        holder.original_article.ivUserHeadImg =
                (ImageView) view.findViewById(R.id.idc_iv_headImg);
        holder.original_article.contentUserHeadImg = view.findViewById(R.id.idc_contentHeadImg);
        holder.original_article.tvUserName = (TextView) view.findViewById(R.id.idc_tv_authorName);
        //内容
        holder.original_article.tvTime = (TextView) view.findViewById(R.id.idc_tv_time);
        holder.original_article.ivPic = (ImageView) view.findViewById(R.id.idc_iv_image);
        holder.original_article.tvDynamicTitle = (TextView) view.findViewById(R.id.idc_tv_dynamicName);
        holder.original_article.tvDynamicContent = (TextView) view.findViewById(R.id.idc_tv_dynamicValue);
        //工具栏
        holder.original_article.ivShare = (ImageView) view.findViewById(R.id.iidbf_iv_share);
        holder.original_article.ivLike = (ImageView) view.findViewById(R.id.iidbf_iv_heart);
        holder.original_article.ivComment = (ImageView) view.findViewById(R.id.iidbf_iv_comment);
        holder.original_article.tvCommentCount = (TextView) view.findViewById(R.id.iidbf_tv_commentNum);
        holder.original_article.tvLikeCount = (TextView) view.findViewById(R.id.iidbf_tv_likeNum);

        //初始化监听事件
        holder.dynamicContent.setOnClickListener(clickItemListener);
        holder.original_article.contentUserHeadImg.setOnClickListener(clickHeadImgListener);
        holder.original_article.ivLike.setOnClickListener(clickLikeListener);
        holder.original_article.ivComment.setOnClickListener(clickCommentListener);
        holder.original_article.ivShare.setOnClickListener(clickShareListener);
        return holder;
    }

    private ViewHolder initDynamic_topic(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_dynamic_circle_topic, parent, false);
        ViewHolder holder = new ViewHolder(view);

        if (holder.original_topic == null) {
            holder.original_topic = new DynamicOriginal_Topic();
        }

        //初始化id
        holder.dynamicContent = view.findViewById(R.id.dynamic_content);
        holder.original_topic.tvTag = (TextView) view.findViewById(R.id.idc_tv_tag);
        //作者
        holder.original_topic.ivUserHeadImg =
                (ImageView) view.findViewById(R.id.idc_iv_headImg);
        holder.original_topic.contentUserHeadImg = view.findViewById(R.id.idc_contentHeadImg);
        holder.original_topic.tvUserName = (TextView) view.findViewById(R.id.idc_tv_authorName);
        //内容
        holder.original_topic.tvTime = (TextView) view.findViewById(R.id.idc_tv_time);
        holder.original_topic.ivPic = (ImageView) view.findViewById(R.id.idc_iv_image);
        holder.original_topic.tvDynamicTitle = (TextView) view.findViewById(R.id.idc_tv_dynamicName);
        holder.original_topic.tvDynamicContent = (TextView) view.findViewById(R.id.idc_tv_dynamicValue);
        holder.original_topic.tvPartInCount = (TextView) view.findViewById(R.id.idc_tv_partInNum);
        //工具栏
        holder.original_topic.ivShare = (ImageView) view.findViewById(R.id.iidbf_iv_share);
        holder.original_topic.ivLike = (ImageView) view.findViewById(R.id.iidbf_iv_heart);
        holder.original_topic.ivComment = (ImageView) view.findViewById(R.id.iidbf_iv_comment);
        holder.original_topic.tvCommentCount = (TextView) view.findViewById(R.id.iidbf_tv_commentNum);
        holder.original_topic.tvLikeCount = (TextView) view.findViewById(R.id.iidbf_tv_likeNum);

        //初始化监听事件
        holder.dynamicContent.setOnClickListener(clickItemListener);
        holder.original_topic.contentUserHeadImg.setOnClickListener(clickHeadImgListener);
        holder.original_topic.ivLike.setOnClickListener(clickLikeListener);
        holder.original_topic.ivComment.setOnClickListener(clickCommentListener);
        holder.original_topic.ivShare.setOnClickListener(clickShareListener);
        return holder;
    }

    private ViewHolder initDynamic_normal(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_dynamic_circle_normal, parent, false);
        ViewHolder holder = new ViewHolder(view);

        if (holder.original_normal == null) {
            holder.original_normal = new DynamicOriginal_Normal();
        }

        //初始化id
        holder.dynamicContent = view.findViewById(R.id.dynamic_content);
        holder.original_normal.tvTag = (TextView) view.findViewById(R.id.idc_tv_tag);
        //作者
        holder.original_normal.ivUserHeadImg =
                (ImageView) view.findViewById(R.id.idc_iv_headImg);
        holder.original_normal.contentUserHeadImg = view.findViewById(R.id.idc_contentHeadImg);
        holder.original_normal.tvUserName = (TextView) view.findViewById(R.id.idc_tv_authorName);
        //内容
        holder.original_normal.tvTime = (TextView) view.findViewById(R.id.idc_tv_time);
        holder.original_normal.ivPic = (ImageView) view.findViewById(R.id.idc_iv_image);
        holder.original_normal.tvDynamicTitle = (TextView) view.findViewById(R.id.idc_tv_dynamicName);
        holder.original_normal.tvDynamicContent = (TextView) view.findViewById(R.id.idc_tv_dynamicValue);
        //工具栏
        holder.original_normal.ivShare = (ImageView) view.findViewById(R.id.iidbf_iv_share);
        holder.original_normal.ivLike = (ImageView) view.findViewById(R.id.iidbf_iv_heart);
        holder.original_normal.ivComment = (ImageView) view.findViewById(R.id.iidbf_iv_comment);
        holder.original_normal.tvCommentCount = (TextView) view.findViewById(R.id.iidbf_tv_commentNum);
        holder.original_normal.tvLikeCount = (TextView) view.findViewById(R.id.iidbf_tv_likeNum);

        //初始化监听事件
        holder.dynamicContent.setOnClickListener(clickItemListener);
        holder.original_normal.contentUserHeadImg.setOnClickListener(clickHeadImgListener);
        holder.original_normal.ivLike.setOnClickListener(clickLikeListener);
        holder.original_normal.ivComment.setOnClickListener(clickCommentListener);
        holder.original_normal.ivShare.setOnClickListener(clickShareListener);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = holder.viewType;
        switch (viewType) {
            case DYNAMIC_TYPE_ARTICLE:
                bindDynamic_article(holder, position);
                break;
            case DYNAMIC_TYPE_NORMAL:
                bindDynamic_normal(holder, position);
                break;
            case DYNAMIC_TYPE_TOPIC:
                bindDynamic_topic(holder, position);
                break;
            default:
                bindBlankDynamic(holder, position);
                break;
        }
    }

    /**
     * 绑定空白数据
     */
    private void bindBlankDynamic(ViewHolder holder, int position) {
        holder.dynamicContent.setVisibility(View.GONE);
    }

    /**
     * 绑定原创动态：文章
     */
    private void bindDynamic_article(ViewHolder holder, int position) {
        DynamicInCircle dynamicInCircle = dynamicInCircles.get(position);
        DynamicInCircleOriginalArticle article = (DynamicInCircleOriginalArticle) dynamicInCircle;

        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.original_article.contentUserHeadImg,
                holder.original_article.ivComment,
                holder.original_article.ivLike,
                holder.original_article.ivShare);

        if (article == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        holder.dynamicContent.setVisibility(View.VISIBLE);
        //设置标签
        holder.original_article.tvTag.setBackgroundResource(R.drawable.ic_tag_article);
        holder.original_article.tvTag.setText("文章");
        //作者信息
        UserInfo user = article.getUser();
        if (user == null) {
            holder.dynamicContent.setVisibility(View.GONE);
        } else {
            holder.original_article.tvUserName.setText(user.getNickName());
            String avatar = user.getAvatar();
            if (StringUtils.isEmpty(avatar)) {
                holder.original_article.ivUserHeadImg.setImageResource(R.drawable.ic_user_head);
            } else {
                loadImageByUrl(holder.original_article.ivUserHeadImg, avatar);
            }
        }
        //时间
        holder.original_article.tvTime.setText(article.getDynamic_created_at());
        //内容
        holder.original_article.tvDynamicTitle.setText(article.getTitle());
        List<DynamicInCircleOriginalArticle.ArticleContent> articleContents =
                article.getArticleContents();
        if (articleContents == null || articleContents.size() <= 0) {
            holder.dynamicContent.setVisibility(View.GONE);
        } else {
            String img = null;
            String doc = null;
            for (DynamicInCircleOriginalArticle.ArticleContent content : articleContents) {
                if (StringUtils.isEmpty(content.getImg())) {
                    continue;
                }
                img = content.getImg();
                doc = content.getDoc();
                if (!StringUtils.isEmpty(img, doc)) {
                    break;
                }
            }
            if (StringUtils.isEmpty(img)) {
                holder.original_article.ivPic.setVisibility(View.GONE);
            } else {
                loadImageByUrl(holder.original_article.ivPic, img);
            }
            holder.original_article.tvDynamicContent.setText(doc);
        }
        //底部工具栏
        holder.original_article.tvCommentCount.setText(article.getCount_of_comment() + "");
        holder.original_article.tvLikeCount.setText(article.getCount_of_favorite() + "");
        if (article.isHas_favorite()) {
            holder.original_article.ivLike.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.original_article.ivLike.setImageResource(R.drawable.ic_heart_gray);
        }
    }

    /**
     * 绑定原创动态：文章
     */
    private void bindDynamic_normal(ViewHolder holder, int position) {
        DynamicInCircle dynamicInCircle = dynamicInCircles.get(position);
        DynamicInCircleOriginalNormal normal = (DynamicInCircleOriginalNormal) dynamicInCircle;

        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.original_normal.contentUserHeadImg,
                holder.original_normal.ivComment,
                holder.original_normal.ivLike,
                holder.original_normal.ivShare);

        if (normal == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        holder.dynamicContent.setVisibility(View.VISIBLE);
        //设置标签
        holder.original_normal.tvTag.setBackgroundResource(R.drawable.ic_tag_essays);
        holder.original_normal.tvTag.setText("普通");
        //作者信息
        UserInfo user = normal.getUser();
        if (user == null) {
            holder.dynamicContent.setVisibility(View.GONE);
        } else {
            holder.original_normal.tvUserName.setText(user.getNickName());
            String avatar = user.getAvatar();
            if (StringUtils.isEmpty(avatar)) {
                holder.original_normal.ivUserHeadImg.setImageResource(R.drawable.ic_user_head);
            } else {
                loadImageByUrl(holder.original_normal.ivUserHeadImg, avatar);
            }
        }
        //时间
        holder.original_normal.tvTime.setText(normal.getDynamic_created_at());
        //内容
        holder.original_normal.tvDynamicTitle.setText(normal.getTitle());
        DynamicInCircleOriginalNormal.NormalContent normalContent = normal.getNormalContent();
        if (normalContent == null) {
            holder.dynamicContent.setVisibility(View.GONE);
        } else {
            List<String> imgs = normalContent.getImg();
            String doc = normalContent.getDoc();
            if (imgs == null || imgs.size() == 0) {
                holder.original_normal.ivPic.setVisibility(View.GONE);
            } else {
                loadImageByUrl(holder.original_normal.ivPic, imgs.get(0));
            }
            holder.original_normal.tvDynamicContent.setText(doc);
        }
        //底部工具栏
        holder.original_normal.tvCommentCount.setText(normal.getCount_of_comment() + "");
        holder.original_normal.tvLikeCount.setText(normal.getCount_of_favorite() + "");
        if (normal.isHas_favorite()) {
            holder.original_normal.ivLike.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.original_normal.ivLike.setImageResource(R.drawable.ic_heart_gray);
        }
    }

    /**
     * 绑定原创动态：文章
     */
    private void bindDynamic_topic(ViewHolder holder, int position) {
        DynamicInCircle dynamicInCircle = dynamicInCircles.get(position);
        DynamicInCircleOriginalTopic topic = (DynamicInCircleOriginalTopic) dynamicInCircle;

        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.original_topic.contentUserHeadImg,
                holder.original_topic.ivComment,
                holder.original_topic.ivLike,
                holder.original_topic.ivShare);

        if (topic == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        holder.dynamicContent.setVisibility(View.VISIBLE);
        //设置标签
        holder.original_topic.tvTag.setBackgroundResource(R.drawable.ic_tag_topic);
        holder.original_topic.tvTag.setText("话题");
        //作者信息
        UserInfo user = topic.getUser();
        if (user == null) {
            holder.dynamicContent.setVisibility(View.GONE);
        } else {
            holder.original_topic.tvUserName.setText(user.getNickName());
            String avatar = user.getAvatar();
            if (StringUtils.isEmpty(avatar)) {
                holder.original_topic.ivUserHeadImg.setImageResource(R.drawable.ic_user_head);
            } else {
                loadImageByUrl(holder.original_topic.ivUserHeadImg, avatar);
            }
        }
        //时间
        holder.original_topic.tvTime.setText(topic.getDynamic_created_at());
        //内容
        holder.original_topic.tvDynamicTitle.setText(topic.getTitle());
        DynamicInCircleOriginalTopic.TopicContent topicContent = topic.getTopicContent();
        if (topicContent == null) {
            holder.dynamicContent.setVisibility(View.GONE);
        } else {
            String img = topicContent.getImg();
            String doc = topicContent.getDoc();
            if (StringUtils.isEmpty(img)) {
                holder.original_topic.ivPic.setVisibility(View.GONE);
            } else {
                loadImageByUrl(holder.original_topic.ivPic, img);
            }
            holder.original_topic.tvDynamicContent.setText(doc);
        }
        //底部工具栏
        holder.original_topic.tvCommentCount.setText(topic.getCount_of_comment() + "");
        holder.original_topic.tvLikeCount.setText(topic.getCount_of_favorite() + "");
        holder.original_topic.tvPartInCount.setText(topic.getCount_of_participation() + "");
        if (topic.isHas_favorite()) {
            holder.original_topic.ivLike.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.original_topic.ivLike.setImageResource(R.drawable.ic_heart_gray);
        }
    }

    /**
     * 加载图片
     */
    private void loadImageByUrl(ImageView imageView, String url) {
        ImageLoaderFactory.getInstance().displayImage(imageView, url);
    }

    @Override
    public int getItemCount() {
        return dynamicInCircles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        int viewType;
        View dynamicContent;
        //原创
        DynamicOriginal_Article original_article;
        DynamicOriginal_Normal original_normal;
        DynamicOriginal_Topic original_topic;
    }

    /**
     * 文章动态中用到的控件
     */
    private class DynamicOriginal_Article {
        TextView tvTag;
        //作者
        View contentUserHeadImg;
        TextView tvUserName;
        ImageView ivUserHeadImg;
        //时间
        TextView tvTime;
        ImageView ivPic;
        TextView tvDynamicTitle;
        TextView tvDynamicContent;
        //下方工具栏
        TextView tvCommentCount;
        TextView tvLikeCount;
        ImageView ivLike;
        ImageView ivComment;
        ImageView ivShare;
    }

    /**
     * 文章动态中用到的控件
     */
    private class DynamicOriginal_Normal {
        TextView tvTag;
        //作者
        View contentUserHeadImg;
        TextView tvUserName;
        ImageView ivUserHeadImg;
        //时间
        TextView tvTime;
        ImageView ivPic;
        TextView tvDynamicTitle;
        TextView tvDynamicContent;
        //下方工具栏
        TextView tvCommentCount;
        TextView tvLikeCount;
        ImageView ivLike;
        ImageView ivComment;
        ImageView ivShare;
    }

    /**
     * 文章动态中用到的控件
     */
    private class DynamicOriginal_Topic {
        TextView tvTag;
        //作者
        View contentUserHeadImg;
        TextView tvUserName;
        ImageView ivUserHeadImg;
        //时间
        TextView tvTime;
        ImageView ivPic;
        TextView tvDynamicTitle;
        TextView tvDynamicContent;
        TextView tvPartInCount;
        //下方工具栏
        TextView tvCommentCount;
        TextView tvLikeCount;
        ImageView ivLike;
        ImageView ivComment;
        ImageView ivShare;
    }


    /**
     * 圈子内动态的条目点击监听事件
     */
    public interface OnDynamicInCircleItemClickListener {

        /**
         * 圈子内动态的条目点击回调方法
         *
         * @param position 条目数
         */
        void onDynamicInCircleClick(int position);
    }

    /**
     * 头像的点击监听器
     */
    public interface OnHeadImgClickListener {

        /**
         * 头像的点击回调方法
         *
         * @param position 条目数
         */
        void onHeadImgClick(int position);
    }

    /**
     * 评论的点击监听器
     */
    public interface OnCommentClickListener {

        /**
         * 当评论条目的点击回调
         *
         * @param position 条目数
         */
        void onCommentClick(int position);
    }

    /**
     * 喜欢的点击监听器
     */
    public interface OnLikeClickListener {

        /**
         * 喜欢点击的回调方法
         *
         * @param position 条木数
         */
        void onLikeClick(int position);
    }

    /**
     * 分享的点击监听器
     */
    public interface OnShareClickListener {

        /**
         * 点击分享的回调方法
         *
         * @param position 条木数
         */
        void onShareClick(int position);
    }

    /**
     * 设置圈子内动态的条目点击监听事件
     */
    public void setOnDynamicInCircleItemClickListener(
            OnDynamicInCircleItemClickListener onDynamicInCircleItemClickListener) {
        this.onDynamicInCircleItemClickListener = onDynamicInCircleItemClickListener;
    }

    /**
     * 设置评论的点击回调
     */
    public void setOnCommentClickListener(OnCommentClickListener onCommentClickListener) {
        this.onCommentClickListener = onCommentClickListener;
    }

    /**
     * 设置喜欢的点击回调
     */
    public void setOnLikeClickListener(OnLikeClickListener onLikeClickListener) {
        this.onLikeClickListener = onLikeClickListener;
    }

    /**
     * 设置分享的点击监听
     */
    public void setOnShareClickListener(OnShareClickListener onShareClickListener) {
        this.onShareClickListener = onShareClickListener;
    }

    /**
     * 设置头像的点击监听器
     */
    public void setOnHeadImgClickListener(OnHeadImgClickListener onHeadImgClickListener) {
        this.onHeadImgClickListener = onHeadImgClickListener;
    }


    /**
     * 条目点击的监听器
     */
    private View.OnClickListener clickItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onDynamicInCircleItemClickListener == null) {
                return;
            }
            //判断各种控件id
            Bundle bundle = (Bundle) view.getTag();
            if (bundle == null) {
                return;
            }
            int viewId = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_ID);
            int position = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_POSITION);
            switch (viewId) {
                case R.id.dynamic_content:
                    onDynamicInCircleItemClickListener.onDynamicInCircleClick(position);
                    break;
            }
        }
    };
    /**
     * 头像点击的监听器
     */
    private View.OnClickListener clickHeadImgListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onHeadImgClickListener == null) {
                return;
            }
            //判断各种控件id
            Bundle bundle = (Bundle) view.getTag();
            if (bundle == null) {
                return;
            }
            int viewId = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_ID);
            int position = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_POSITION);
            switch (viewId) {
                case R.id.idc_contentHeadImg:
                    onHeadImgClickListener.onHeadImgClick(position);
                    break;
            }
        }
    };
    /**
     * 评论点击的监听器
     */
    private View.OnClickListener clickCommentListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onCommentClickListener == null) {
                return;
            }
            //判断各种控件id
            Bundle bundle = (Bundle) view.getTag();
            if (bundle == null) {
                return;
            }
            int viewId = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_ID);
            int position = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_POSITION);
            switch (viewId) {
                case R.id.iidbf_iv_comment:
                    onCommentClickListener.onCommentClick(position);
                    break;
            }
        }
    };
    /**
     * 喜欢点击的监听器
     */
    private View.OnClickListener clickLikeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onLikeClickListener == null) {
                return;
            }
            //判断各种控件id
            Bundle bundle = (Bundle) view.getTag();
            if (bundle == null) {
                return;
            }
            int viewId = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_ID);
            int position = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_POSITION);
            switch (viewId) {
                case R.id.iidbf_iv_heart:
                    onLikeClickListener.onLikeClick(position);
                    break;
            }
        }
    };
    /**
     * 喜欢点击的监听器
     */
    private View.OnClickListener clickShareListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onShareClickListener == null) {
                return;
            }
            //判断各种控件id
            Bundle bundle = (Bundle) view.getTag();
            if (bundle == null) {
                return;
            }
            int viewId = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_ID);
            int position = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_POSITION);
            switch (viewId) {
                case R.id.iidbf_iv_share:
                    onShareClickListener.onShareClick(position);
                    break;
            }
        }
    };
}
