package com.jkb.mrcampus.adapter.recycler.personCenter.myDynamic.circle;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.data.dynamic.myOriginal.myCircle.CircleArticleDynamic;
import com.jkb.core.data.dynamic.myOriginal.myCircle.CircleNormalDynamic;
import com.jkb.core.data.dynamic.myOriginal.myCircle.CircleTopicDynamic;
import com.jkb.core.data.dynamic.myOriginal.myCircle.DynamicInCircleDynamic;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.info.dynamic.content.DynamicContentArticleInfo;
import com.jkb.core.data.info.dynamic.content.DynamicContentNormalInfo;
import com.jkb.core.data.info.dynamic.content.DynamicContentTopicInfo;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的动态：圈子内动态适配器
 * Created by JustKiddingBaby on 2016/10/17.
 */

public class MyDynamicCircleAdapter extends RecyclerView.Adapter<MyDynamicCircleAdapter.ViewHolder> {

    //data
    private static final String TAG = "MyDynamicCircleA";
    private Context context;
    public List<DynamicInCircleDynamic> dynamicInCircleDynamics;
    public boolean isOriginal = false;

    //回调
    private OnMyCircleDynamicClickListener onMyCircleDynamicClickListener;

    //常量
    private static final int DYNAMIC_TYPE_ARTICLE = 1001;
    private static final int DYNAMIC_TYPE_NORMAL = 1002;
    private static final int DYNAMIC_TYPE_TOPIC = 1003;

    //资源文件
    private int res_default_circle_pic = R.color.default_picture;
    private int selector_bg[] = new int[]
            {R.drawable.selector_bg_gravy, R.drawable.selector_white_general};

    public MyDynamicCircleAdapter(
            Context context,
            List<DynamicInCircleDynamic> dynamicInCircleDynamics) {
        this.context = context;
        if (dynamicInCircleDynamics == null) {
            dynamicInCircleDynamics = new ArrayList<>();
        }
        this.dynamicInCircleDynamics = dynamicInCircleDynamics;
    }

    @Override
    public int getItemViewType(int position) {
        DynamicInCircleDynamic dynamicInCircleDynamic = dynamicInCircleDynamics.get(position);
        int viewType;
        if (dynamicInCircleDynamic instanceof CircleArticleDynamic) {
            viewType = DYNAMIC_TYPE_ARTICLE;
        } else if (dynamicInCircleDynamic instanceof CircleNormalDynamic) {
            viewType = DYNAMIC_TYPE_NORMAL;
        } else if (dynamicInCircleDynamic instanceof CircleTopicDynamic) {
            viewType = DYNAMIC_TYPE_TOPIC;
        } else {
            viewType = -1;
        }
        return viewType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case DYNAMIC_TYPE_ARTICLE:
                holder = initArticleDynamic(inflater, parent);
                break;
            case DYNAMIC_TYPE_NORMAL:
                holder = initNormalDynamic(inflater, parent);
                break;
            case DYNAMIC_TYPE_TOPIC:
                holder = initTopicDynamic(inflater, parent);
                break;
            default:
                holder = initBlankDynamic(inflater, parent);
                break;
        }
        return holder;
    }

    /**
     * 初始化空白动态
     */
    private ViewHolder initBlankDynamic(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_my_dynamic_blank, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.viewType = -1;
        //初始化id
        holder.dynamicContent = view.findViewById(R.id.myDynamic_content);
        return holder;
    }

    /**
     * 初始化话题动态
     */
    private ViewHolder initTopicDynamic(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_my_dynamic_circle_topic, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件
        if (holder.circleDynamicTopic == null) {
            holder.circleDynamicTopic = new CircleDynamicTopic();
        }
        holder.viewType = DYNAMIC_TYPE_TOPIC;
        holder.dynamicContent = view.findViewById(R.id.myDynamic_content);

        holder.circleDynamicTopic.tvTime = (TextView) view.findViewById(R.id.imdct_tv_time);
        holder.circleDynamicTopic.tvTopicTitle = (TextView)
                view.findViewById(R.id.imdct_tv_topicName);
        holder.circleDynamicTopic.tvDoc = (TextView) view.findViewById(R.id.imdct_tv_contentText);
        holder.circleDynamicTopic.ivPic = (ImageView) view.findViewById(R.id.imdct_iv_contentPic);
        //內容
        holder.circleDynamicTopic.tvCircleName = (TextView)
                view.findViewById(R.id.imdct_tv_circleName);
        holder.circleDynamicTopic.contentCirclePic = view.findViewById(R.id.imdct_contentCirclePic);
        holder.circleDynamicTopic.tvPartIn_count = (TextView)
                view.findViewById(R.id.imdct_tv_partInNum);
        holder.circleDynamicTopic.ivCirclePic = (ImageView)
                view.findViewById(R.id.imdct_iv_circlePic);
        //底部
        holder.circleDynamicTopic.tvDelete = (TextView) view.findViewById(R.id.iidb_tv_delete);
        holder.circleDynamicTopic.ivShare = (ImageView) view.findViewById(R.id.iidbf_iv_share);
        view.findViewById(R.id.iidbf_iv_comment).setVisibility(View.GONE);
        view.findViewById(R.id.iidbf_tv_commentNum).setVisibility(View.GONE);
        holder.circleDynamicTopic.ivHeart = (ImageView) view.findViewById(R.id.iidbf_iv_heart);
        holder.circleDynamicTopic.tvFavorite_count = (TextView)
                view.findViewById(R.id.iidbf_tv_likeNum);
        //初始化监听事件
        holder.dynamicContent.setOnClickListener(clickMyCircleMynamicItemsListener);
        holder.circleDynamicTopic.contentCirclePic.
                setOnClickListener(clickMyCircleMynamicItemsListener);
        holder.circleDynamicTopic.tvDelete.
                setOnClickListener(clickMyCircleMynamicItemsListener);
        holder.circleDynamicTopic.ivShare.
                setOnClickListener(clickMyCircleMynamicItemsListener);
        holder.circleDynamicTopic.ivHeart.
                setOnClickListener(clickMyCircleMynamicItemsListener);
        return holder;
    }

    /**
     * 初始化普通动态
     */
    private ViewHolder initNormalDynamic(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_my_dynamic_circle_normal, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件
        if (holder.circleDynamicNormal == null) {
            holder.circleDynamicNormal = new CircleDynamicNormal();
        }
        holder.viewType = DYNAMIC_TYPE_NORMAL;
        holder.dynamicContent = view.findViewById(R.id.myDynamic_content);
        holder.circleDynamicNormal.tvTime = (TextView) view.findViewById(R.id.imdcn_tv_time);
        holder.circleDynamicNormal.tvDoc = (TextView) view.findViewById(R.id.imdcn_tv_contentText);
        holder.circleDynamicNormal.ivPic = (ImageView) view.findViewById(R.id.imdcn_iv_contentPic);
        //內容
        holder.circleDynamicNormal.tvCircleName = (TextView)
                view.findViewById(R.id.imdcn_tv_circleName);
        holder.circleDynamicNormal.contentCirclePic = view.findViewById(R.id.imdcn_contentCirclePic);
        holder.circleDynamicNormal.ivCirclePic = (ImageView)
                view.findViewById(R.id.imdcn_iv_circlePic);
        //底部
        holder.circleDynamicNormal.tvDelete = (TextView) view.findViewById(R.id.iidb_tv_delete);
        holder.circleDynamicNormal.ivShare = (ImageView) view.findViewById(R.id.iidbf_iv_share);
        holder.circleDynamicNormal.ivComment = (ImageView) view.findViewById(R.id.iidbf_iv_comment);
        holder.circleDynamicNormal.tvComment_count = (TextView)
                view.findViewById(R.id.iidbf_tv_commentNum);
        holder.circleDynamicNormal.ivHeart = (ImageView) view.findViewById(R.id.iidbf_iv_heart);
        holder.circleDynamicNormal.tvFavorite_count = (TextView)
                view.findViewById(R.id.iidbf_tv_likeNum);

        //初始化监听事件
        holder.dynamicContent.setOnClickListener(clickMyCircleMynamicItemsListener);
        holder.circleDynamicNormal.contentCirclePic.
                setOnClickListener(clickMyCircleMynamicItemsListener);
        holder.circleDynamicNormal.tvDelete.
                setOnClickListener(clickMyCircleMynamicItemsListener);
        holder.circleDynamicNormal.ivShare.
                setOnClickListener(clickMyCircleMynamicItemsListener);
        holder.circleDynamicNormal.ivComment.
                setOnClickListener(clickMyCircleMynamicItemsListener);
        holder.circleDynamicNormal.ivHeart.
                setOnClickListener(clickMyCircleMynamicItemsListener);
        return holder;
    }

    /**
     * 初始化文章动态
     */
    private ViewHolder initArticleDynamic(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_my_dynamic_circle_article, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件
        if (holder.circleDynamicArticle == null) {
            holder.circleDynamicArticle = new CircleDynamicArticle();
        }
        holder.viewType = DYNAMIC_TYPE_ARTICLE;
        holder.dynamicContent = view.findViewById(R.id.myDynamic_content);
        holder.circleDynamicArticle.tvTime = (TextView) view.findViewById(R.id.imdca_tv_time);
        holder.circleDynamicArticle.tvDoc = (TextView) view.findViewById(R.id.imdca_tv_contentText);
        holder.circleDynamicArticle.ivPic = (ImageView) view.findViewById(R.id.imdca_iv_contentPic);
        //內容
        holder.circleDynamicArticle.tvArticleTitle = (TextView)
                view.findViewById(R.id.imdca_tv_articleName);
        holder.circleDynamicArticle.tvCircleName = (TextView)
                view.findViewById(R.id.imdca_tv_circleName);
        holder.circleDynamicArticle.contentCirclePic = view.findViewById(R.id.imdca_contentCirclePic);
        holder.circleDynamicArticle.ivCirclePic = (ImageView)
                view.findViewById(R.id.imdca_iv_circlePic);
        //底部
        holder.circleDynamicArticle.tvDelete = (TextView) view.findViewById(R.id.iidb_tv_delete);
        holder.circleDynamicArticle.ivShare = (ImageView) view.findViewById(R.id.iidbf_iv_share);
        holder.circleDynamicArticle.ivComment = (ImageView) view.findViewById(R.id.iidbf_iv_comment);
        holder.circleDynamicArticle.tvComment_count = (TextView)
                view.findViewById(R.id.iidbf_tv_commentNum);
        holder.circleDynamicArticle.ivHeart = (ImageView) view.findViewById(R.id.iidbf_iv_heart);
        holder.circleDynamicArticle.tvFavorite_count = (TextView)
                view.findViewById(R.id.iidbf_tv_likeNum);

        //初始化监听事件
        holder.dynamicContent.setOnClickListener(clickMyCircleMynamicItemsListener);
        holder.circleDynamicArticle.contentCirclePic.
                setOnClickListener(clickMyCircleMynamicItemsListener);
        holder.circleDynamicArticle.tvDelete.
                setOnClickListener(clickMyCircleMynamicItemsListener);
        holder.circleDynamicArticle.ivShare.
                setOnClickListener(clickMyCircleMynamicItemsListener);
        holder.circleDynamicArticle.ivComment.
                setOnClickListener(clickMyCircleMynamicItemsListener);
        holder.circleDynamicArticle.ivHeart.
                setOnClickListener(clickMyCircleMynamicItemsListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = holder.viewType;
        holder.dynamicContent.setBackgroundResource(selector_bg[position % 2]);
        switch (viewType) {
            case DYNAMIC_TYPE_ARTICLE:
                bindArticleDynamic(holder, position);
                break;
            case DYNAMIC_TYPE_NORMAL:
                bindNormalDynamic(holder, position);
                break;
            case DYNAMIC_TYPE_TOPIC:
                bindTopicDynamic(holder, position);
                break;
            default:
                bindBlankDynamic(holder, position);
                break;
        }
    }

    /**
     * 绑定空的数据
     */
    private void bindBlankDynamic(ViewHolder holder, int position) {
        holder.dynamicContent.setVisibility(View.GONE);
    }

    /**
     * 綁定話題數據
     */
    private void bindTopicDynamic(ViewHolder holder, int position) {
        CircleTopicDynamic topicDynamic = (CircleTopicDynamic) dynamicInCircleDynamics.get(position);
        holder.dynamicContent.setVisibility(View.VISIBLE);
        if (topicDynamic == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.circleDynamicTopic.contentCirclePic,
                holder.circleDynamicTopic.tvDelete,
                holder.circleDynamicTopic.ivShare,
                holder.circleDynamicTopic.ivHeart
        );
        //设置顶部数据
        holder.circleDynamicTopic.tvTime.setText(topicDynamic.getDynamic_updated_at());
        holder.circleDynamicTopic.tvTopicTitle.setText(topicDynamic.getTitle());
        holder.circleDynamicTopic.tvPartIn_count.setText
                (topicDynamic.getCount_of_participation() + "");
        CircleInfo circle = topicDynamic.getCircle();
        if (circle == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        holder.circleDynamicTopic.tvCircleName.setText(circle.getCircleName());
        String circlePic = circle.getPictureUrl();
        if (!StringUtils.isEmpty(circlePic)) {
            loadImage(holder.circleDynamicTopic.ivCirclePic, circlePic);
        } else {
            holder.circleDynamicTopic.ivCirclePic.setImageResource(res_default_circle_pic);
        }
        //文章内容
        DynamicContentTopicInfo topicInfo = topicDynamic.getTopicInfo();
        if (topicInfo == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        String img = topicInfo.getImg();
        String doc = topicInfo.getDoc();
        holder.circleDynamicTopic.tvDoc.setText(doc);
        if (StringUtils.isEmpty(img)) {
            holder.circleDynamicTopic.ivPic.setVisibility(View.GONE);
        } else {
            holder.circleDynamicTopic.ivPic.setVisibility(View.VISIBLE);
            loadImage(holder.circleDynamicTopic.ivPic, img);
        }
        //底部功能栏
        holder.circleDynamicTopic.tvFavorite_count.setText
                (topicDynamic.getCount_of_favorite() + "");
        if (topicDynamic.isHas_favorite()) {
            holder.circleDynamicTopic.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.circleDynamicTopic.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
        if (isOriginal) {
            holder.circleDynamicTopic.tvDelete.setVisibility(View.VISIBLE);
        } else {
            holder.circleDynamicTopic.tvDelete.setVisibility(View.GONE);
        }
    }

    /**
     * 绑定普通的动态数据
     */
    private void bindNormalDynamic(ViewHolder holder, int position) {
        CircleNormalDynamic normalDynamic = (CircleNormalDynamic) dynamicInCircleDynamics.get(position);
        holder.dynamicContent.setVisibility(View.VISIBLE);
        if (normalDynamic == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.circleDynamicNormal.contentCirclePic,
                holder.circleDynamicNormal.tvDelete,
                holder.circleDynamicNormal.ivShare,
                holder.circleDynamicNormal.ivComment,
                holder.circleDynamicNormal.ivHeart
        );
        //设置顶部数据
        holder.circleDynamicNormal.tvTime.setText(normalDynamic.getDynamic_updated_at());
        CircleInfo circle = normalDynamic.getCircle();
        if (circle == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        holder.circleDynamicNormal.tvCircleName.setText(circle.getCircleName());
        String circlePic = circle.getPictureUrl();
        if (!StringUtils.isEmpty(circlePic)) {
            loadImage(holder.circleDynamicNormal.ivCirclePic, circlePic);
        } else {
            holder.circleDynamicNormal.ivCirclePic.setImageResource(res_default_circle_pic);
        }
        //文章内容
        DynamicContentNormalInfo normalInfo = normalDynamic.getNormalInfo();
        if (normalInfo == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        String img;
        String doc = normalInfo.getDoc();
        if (normalInfo.getImg() == null || normalInfo.getImg().size() == 0) {
            img = null;
        } else {
            img = normalInfo.getImg().get(0);
        }
        holder.circleDynamicNormal.tvDoc.setText(doc);
        if (StringUtils.isEmpty(img)) {
            holder.circleDynamicNormal.ivPic.setVisibility(View.GONE);
        } else {
            holder.circleDynamicNormal.ivPic.setVisibility(View.VISIBLE);
            loadImage(holder.circleDynamicNormal.ivPic, img);
        }
        //底部功能栏
        holder.circleDynamicNormal.tvComment_count.setText
                (normalDynamic.getCount_of_comment() + "");
        holder.circleDynamicNormal.tvFavorite_count.setText
                (normalDynamic.getCount_of_favorite() + "");
        if (normalDynamic.isHas_favorite()) {
            holder.circleDynamicNormal.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.circleDynamicNormal.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
        if (isOriginal) {
            holder.circleDynamicNormal.tvDelete.setVisibility(View.VISIBLE);
        } else {
            holder.circleDynamicNormal.tvDelete.setVisibility(View.GONE);
        }
    }

    /**
     * 绑定文章动态数据
     */
    private void bindArticleDynamic(ViewHolder holder, int position) {
        CircleArticleDynamic articleDynamic = (CircleArticleDynamic)
                dynamicInCircleDynamics.get(position);
        holder.dynamicContent.setVisibility(View.VISIBLE);
        if (articleDynamic == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.circleDynamicArticle.contentCirclePic,
                holder.circleDynamicArticle.tvDelete,
                holder.circleDynamicArticle.ivShare,
                holder.circleDynamicArticle.ivComment,
                holder.circleDynamicArticle.ivHeart
        );
        //设置顶部数据
        holder.circleDynamicArticle.tvTime.setText(articleDynamic.getDynamic_updated_at());
        holder.circleDynamicArticle.tvArticleTitle.setText(articleDynamic.getTitle());
        CircleInfo circle = articleDynamic.getCircle();
        if (circle == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        holder.circleDynamicArticle.tvCircleName.setText(circle.getCircleName());
        String circlePic = circle.getPictureUrl();
        if (!StringUtils.isEmpty(circlePic)) {
            loadImage(holder.circleDynamicArticle.ivCirclePic, circlePic);
        } else {
            holder.circleDynamicArticle.ivCirclePic.setImageResource(res_default_circle_pic);
        }
        //文章内容
        DynamicContentArticleInfo articleInfo = articleDynamic.getArticleInfo();
        if (articleInfo == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        List<DynamicContentArticleInfo.Article> articles = articleInfo.getArticle();
        if (articles == null || articles.size() == 0) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        String img = null;
        String doc = null;
        for (DynamicContentArticleInfo.Article article :
                articles) {
            if (StringUtils.isEmpty(img)) {
                img = article.getImg();
            }
            if (StringUtils.isEmpty(doc)) {
                doc = article.getDoc();
            }
            if (!StringUtils.isEmpty(img, doc)) {
                break;
            }
        }
        holder.circleDynamicArticle.tvDoc.setText(doc);
        if (StringUtils.isEmpty(img)) {
            holder.circleDynamicArticle.ivPic.setVisibility(View.GONE);
        } else {
            holder.circleDynamicArticle.ivPic.setVisibility(View.VISIBLE);
            loadImage(holder.circleDynamicArticle.ivPic, img);
        }
        //底部功能栏
        holder.circleDynamicArticle.tvComment_count.setText
                (articleDynamic.getCount_of_comment() + "");
        holder.circleDynamicArticle.tvFavorite_count.setText
                (articleDynamic.getCount_of_favorite() + "");
        if (articleDynamic.isHas_favorite()) {
            holder.circleDynamicArticle.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.circleDynamicArticle.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
        if (isOriginal) {
            holder.circleDynamicArticle.tvDelete.setVisibility(View.VISIBLE);
        } else {
            holder.circleDynamicArticle.tvDelete.setVisibility(View.GONE);
        }
    }

    /**
     * 加载网络图片
     */
    private void loadImage(ImageView ivCirclePic, String circlePic) {
        ImageLoaderFactory.getInstance().displayImage(ivCirclePic, circlePic);
    }

    @Override
    public int getItemCount() {
        return dynamicInCircleDynamics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        int viewType;
        View dynamicContent;
        CircleDynamicNormal circleDynamicNormal;
        CircleDynamicArticle circleDynamicArticle;
        CircleDynamicTopic circleDynamicTopic;
    }

    /**
     * 圈子中普通动态用到的控件
     */
    private class CircleDynamicNormal {
        ImageView ivPic;
        TextView tvDoc;
        TextView tvTime;
        //圈子
        TextView tvCircleName;
        View contentCirclePic;
        ImageView ivCirclePic;
        //底部
        TextView tvDelete;
        ImageView ivShare;
        ImageView ivComment;
        TextView tvComment_count;
        ImageView ivHeart;
        TextView tvFavorite_count;
    }

    /**
     * 圈子中普通动态用到的控件
     */
    private class CircleDynamicArticle {
        ImageView ivPic;
        TextView tvDoc;
        TextView tvTime;
        TextView tvArticleTitle;
        //圈子
        TextView tvCircleName;
        View contentCirclePic;
        ImageView ivCirclePic;
        //底部
        TextView tvDelete;
        ImageView ivShare;
        ImageView ivComment;
        TextView tvComment_count;
        ImageView ivHeart;
        TextView tvFavorite_count;
    }

    /**
     * 圈子中普通动态用到的控件
     */
    private class CircleDynamicTopic {
        ImageView ivPic;
        TextView tvDoc;
        TextView tvTime;
        TextView tvTopicTitle;
        //圈子
        TextView tvCircleName;
        View contentCirclePic;
        ImageView ivCirclePic;
        TextView tvPartIn_count;
        //底部
        TextView tvDelete;
        ImageView ivShare;
        ImageView ivHeart;
        TextView tvFavorite_count;
    }

    /**
     * 功能条目的点击回调方法
     */
    public interface OnMyCircleDynamicClickListener {

        /**
         * 动态的点击回调方法
         *
         * @param position 条目数
         */
        void onDynamicClick(int position);

        /**
         * 圈子圖片点击的回调方法
         *
         * @param position 条目数
         */
        void onCirclePicClick(int position);

        /**
         * 喜欢的条目点击回调方法
         *
         * @param position 条目数
         */
        void onLikeClick(int position);

        /**
         * 分享的点击回调方法
         *
         * @param position 条目数
         */
        void onShareClick(int position);

        /**
         * 评论的点击回调方法
         *
         * @param position 条目数
         */
        void onCommentClick(int position);

        /**
         * 删除的点击回调方法
         *
         * @param position 条目数
         */
        void onDeleteClick(int position);
    }

    /**
     * 设置子条目的点击监听事件
     */
    public void setOnMyCircleDynamicClickListener(
            OnMyCircleDynamicClickListener onMyCircleDynamicClickListener) {
        this.onMyCircleDynamicClickListener = onMyCircleDynamicClickListener;
    }

    /**
     * 设置子条目的点击监听
     */
    private View.OnClickListener clickMyCircleMynamicItemsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onMyCircleDynamicClickListener == null) {
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
                case R.id.myDynamic_content:
                    onMyCircleDynamicClickListener.onDynamicClick(position);
                    break;
                case R.id.imdct_contentCirclePic:
                case R.id.imdca_contentCirclePic:
                case R.id.imdcn_contentCirclePic:
                    onMyCircleDynamicClickListener.onCirclePicClick(position);
                    break;
                case R.id.iidb_tv_delete:
                    onMyCircleDynamicClickListener.onDeleteClick(position);
                    break;
                case R.id.iidbf_iv_share:
                    onMyCircleDynamicClickListener.onShareClick(position);
                    break;
                case R.id.iidbf_iv_comment:
                    onMyCircleDynamicClickListener.onCommentClick(position);
                    break;
                case R.id.iidbf_iv_heart:
                    onMyCircleDynamicClickListener.onLikeClick(position);
                    break;
            }
        }
    };
}
