package com.jkb.mrcampus.adapter.recycler.personCenter.unOriginal;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.MyFavoriteDynamicData;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.circle.CircleArticleDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.circle.CircleNormalDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.circle.CircleTopicDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.circle.DynamicInCircleDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.original.OriginalArticleDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.original.OriginalDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.original.OriginalNormalDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.original.OriginalTopicDynamic;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我喜欢的动态的页面适配器
 * Created by JustKiddingBaby on 2016/10/16.
 */

public class MyFavoriteDynamicAdapter extends
        RecyclerView.Adapter<MyFavoriteDynamicAdapter.ViewHolder> {

    private static final String TAG = "HotDynamicAdapter";
    private Context context;
    public List<MyFavoriteDynamicData> favoriteDynamicDatas;

    //常量
    private static final int MY_FAVORITE_DYNAMIC_NORMAL = 2001;
    private static final int MY_FAVORITE_DYNAMIC_TOPIC = 2002;
    private static final int MY_FAVORITE_DYNAMIC_ARTICLE = 2003;
    private static final int MY_FAVORITE_DYNAMIC_IN_CIRCLE_NORMAL = 3001;
    private static final int MY_FAVORITE_DYNAMIC_IN_CIRCLE_TOPIC = 3002;
    private static final int MY_FAVORITE_DYNAMIC_IN_CIRCLE_ARTICLE = 3003;

    //点击监听回调
    private OnMyFavoriteItemClickListener onMyFavoriteItemClickListener;

    //两种颜色
    private int bg_selector[] = new int[]{
            R.drawable.selector_white_general,
            R.drawable.selector_bg_gravy
    };

    public MyFavoriteDynamicAdapter(
            Context context, List<MyFavoriteDynamicData> favoriteDynamicDatas) {
        this.context = context;
        if (favoriteDynamicDatas == null) {
            favoriteDynamicDatas = new ArrayList<>();
        }
        this.favoriteDynamicDatas = favoriteDynamicDatas;
    }

    @Override
    public int getItemViewType(int position) {
        MyFavoriteDynamicData myFavoriteDynamic = favoriteDynamicDatas.get(position).
                getMyFavoriteDynamic();
        int hotDynamicType = 0;
        //判断类型
        if (myFavoriteDynamic instanceof OriginalDynamic) {//动态类型
            hotDynamicType = judgeOriginalDynamicType(myFavoriteDynamic, hotDynamicType);
        } else if (myFavoriteDynamic instanceof DynamicInCircleDynamic) {//圈子中动态类型
            hotDynamicType = judgeDynamicInCircleDynamicType(myFavoriteDynamic, hotDynamicType);
        }
        return hotDynamicType;
    }

    /**
     * 判断圈子中动态类型
     */
    private int judgeDynamicInCircleDynamicType(
            MyFavoriteDynamicData favoriteDynamicData, int hotDynamicType) {
        if (favoriteDynamicData instanceof CircleNormalDynamic) {
            hotDynamicType = MY_FAVORITE_DYNAMIC_IN_CIRCLE_NORMAL;
        } else if (favoriteDynamicData instanceof CircleTopicDynamic) {
            hotDynamicType = MY_FAVORITE_DYNAMIC_IN_CIRCLE_TOPIC;
        } else if (favoriteDynamicData instanceof CircleArticleDynamic) {
            hotDynamicType = MY_FAVORITE_DYNAMIC_IN_CIRCLE_ARTICLE;
        }
        return hotDynamicType;
    }

    /**
     * 判断动态类型
     */
    private int judgeOriginalDynamicType(
            MyFavoriteDynamicData myFavoriteDynamicData, int hotDynamicType) {
        if (myFavoriteDynamicData instanceof OriginalArticleDynamic) {//动态：文章
            hotDynamicType = MY_FAVORITE_DYNAMIC_ARTICLE;
        } else if (myFavoriteDynamicData instanceof OriginalNormalDynamic) {//动态：普通
            hotDynamicType = MY_FAVORITE_DYNAMIC_NORMAL;
        } else if (myFavoriteDynamicData instanceof OriginalTopicDynamic) {//动态：话题
            hotDynamicType = MY_FAVORITE_DYNAMIC_TOPIC;
        }
        return hotDynamicType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case MY_FAVORITE_DYNAMIC_ARTICLE://文章动态
                holder = initMyFavorite_DynamicArticle(parent, inflater);
                break;
            case MY_FAVORITE_DYNAMIC_NORMAL://普通动态
                holder = initMyFavorite_DynamicNormal(parent, inflater);
                break;
            case MY_FAVORITE_DYNAMIC_TOPIC://话题动态
                holder = initMyFavorite_DynamicTopic(parent, inflater);
                break;
            case MY_FAVORITE_DYNAMIC_IN_CIRCLE_ARTICLE:
                holder = initMyFavorite_InCircle_Article(parent, inflater);
                break;
            case MY_FAVORITE_DYNAMIC_IN_CIRCLE_TOPIC:
                holder = initMyFavorite_InCircle_Topic(parent, inflater);
                break;
            case MY_FAVORITE_DYNAMIC_IN_CIRCLE_NORMAL:
                holder = initMyFavorite_InCircle_Normal(parent, inflater);
                break;
            default:
                holder = initBlankDynamic(parent, inflater);
                break;
        }
        return holder;
    }

    /**
     * 初始化空的动态
     */
    private ViewHolder initBlankDynamic(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_my_favorite_dynamic_blank, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化id
        holder.dynamicContent = view.findViewById(R.id.myFavoriteDynamic_content);
        return holder;
    }

    /**
     * 初始化我喜欢的动态：文章动态
     */
    private ViewHolder initMyFavorite_DynamicArticle(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_my_favorite_dynamic_article, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件id
        if (holder.unOriginalFavoriteArticle == null) {
            holder.unOriginalFavoriteArticle = new UnOriginalFavoriteArticle();
        }
        holder.viewType = MY_FAVORITE_DYNAMIC_ARTICLE;

        holder.dynamicContent = view.findViewById(R.id.myFavoriteDynamic_content);
        //初始化作者信息
        holder.unOriginalFavoriteArticle.tvOriginalNickName = (TextView)
                view.findViewById(R.id.iidbf_tv_originator_nickname);
        //初始化文章数据
        holder.unOriginalFavoriteArticle.tvTime = (TextView) view.findViewById(R.id.imfda_tv_time);
        holder.unOriginalFavoriteArticle.tvTitle = (TextView)
                view.findViewById(R.id.imfda_tv_articleName);
        holder.unOriginalFavoriteArticle.tvContent = (TextView)
                view.findViewById(R.id.imfda_tv_contentText);
        holder.unOriginalFavoriteArticle.ivPic = (ImageView)
                view.findViewById(R.id.imfda_iv_contentPic);
        //初始化底部功能栏
        holder.unOriginalFavoriteArticle.ivShare = (ImageView)
                view.findViewById(R.id.iidbf_iv_share);
        holder.unOriginalFavoriteArticle.ivHeart = (ImageView)
                view.findViewById(R.id.iidbf_iv_heart);
        holder.unOriginalFavoriteArticle.ivComment = (ImageView)
                view.findViewById(R.id.iidbf_iv_comment);
        holder.unOriginalFavoriteArticle.tvComment_count = (TextView)
                view.findViewById(R.id.iidbf_tv_commentNum);
        holder.unOriginalFavoriteArticle.tvFavorite_count = (TextView)
                view.findViewById(R.id.iidbf_tv_likeNum);
        //设置点击事件
        holder.dynamicContent.setOnClickListener(clickMyFavoriteItemListener);
        holder.unOriginalFavoriteArticle.ivShare.setOnClickListener(clickMyFavoriteItemListener);
        holder.unOriginalFavoriteArticle.ivComment.setOnClickListener(clickMyFavoriteItemListener);
        holder.unOriginalFavoriteArticle.ivHeart.setOnClickListener(clickMyFavoriteItemListener);
        holder.unOriginalFavoriteArticle.tvOriginalNickName
                .setOnClickListener(clickMyFavoriteItemListener);
        return holder;
    }

    /**
     * 初始化我喜欢的动态：普通动态
     */
    private ViewHolder initMyFavorite_DynamicNormal(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_my_favorite_dynamic_normal, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件id
        if (holder.unOriginalFavoriteNormal == null) {
            holder.unOriginalFavoriteNormal = new UnOriginalFavoriteNormal();
        }
        holder.viewType = MY_FAVORITE_DYNAMIC_NORMAL;

        holder.dynamicContent = view.findViewById(R.id.myFavoriteDynamic_content);
        //初始化作者信息
        holder.unOriginalFavoriteNormal.tvOriginalNickName = (TextView)
                view.findViewById(R.id.iidbf_tv_originator_nickname);
        //初始化文章数据
        holder.unOriginalFavoriteNormal.tvTime = (TextView) view.findViewById(R.id.imfdn_tv_time);
        holder.unOriginalFavoriteNormal.tvContent = (TextView)
                view.findViewById(R.id.imfdn_tv_contentText);
        holder.unOriginalFavoriteNormal.ivPic = (ImageView)
                view.findViewById(R.id.imfdn_iv_contentPic);
        //初始化底部功能栏
        holder.unOriginalFavoriteNormal.ivShare = (ImageView)
                view.findViewById(R.id.iidbf_iv_share);
        holder.unOriginalFavoriteNormal.ivHeart = (ImageView)
                view.findViewById(R.id.iidbf_iv_heart);
        holder.unOriginalFavoriteNormal.ivComment = (ImageView)
                view.findViewById(R.id.iidbf_iv_comment);
        holder.unOriginalFavoriteNormal.tvComment_count = (TextView)
                view.findViewById(R.id.iidbf_tv_commentNum);
        holder.unOriginalFavoriteNormal.tvFavorite_count = (TextView)
                view.findViewById(R.id.iidbf_tv_likeNum);

        //设置监听事件
        holder.dynamicContent.setOnClickListener(clickMyFavoriteItemListener);
        holder.unOriginalFavoriteNormal.ivShare.setOnClickListener(clickMyFavoriteItemListener);
        holder.unOriginalFavoriteNormal.ivComment.setOnClickListener(clickMyFavoriteItemListener);
        holder.unOriginalFavoriteNormal.ivHeart.setOnClickListener(clickMyFavoriteItemListener);
        holder.unOriginalFavoriteNormal.tvOriginalNickName
                .setOnClickListener(clickMyFavoriteItemListener);
        return holder;
    }

    /**
     * 初始化我喜欢的动态：话题动态
     */
    private ViewHolder initMyFavorite_DynamicTopic(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_my_favorite_dynamic_topic, parent, false);
        ViewHolder holder = new ViewHolder(view);
        if (holder.unOriginalFavoriteTopic == null) {
            holder.unOriginalFavoriteTopic = new UnOriginalFavoriteTopic();
        }
        holder.viewType = MY_FAVORITE_DYNAMIC_TOPIC;

        holder.dynamicContent = view.findViewById(R.id.myFavoriteDynamic_content);
        //初始化作者信息
        holder.unOriginalFavoriteTopic.tvOriginalNickName = (TextView)
                view.findViewById(R.id.iidbf_tv_originator_nickname);
        //初始化文章数据
        holder.unOriginalFavoriteTopic.tvTime = (TextView) view.findViewById(R.id.imfdt_tv_time);
        holder.unOriginalFavoriteTopic.tvTitle = (TextView)
                view.findViewById(R.id.imfdt_tv_topicName);
        holder.unOriginalFavoriteTopic.tvPartIn_count = (TextView)
                view.findViewById(R.id.imfdt_tv_partInNum);
        holder.unOriginalFavoriteTopic.tvContent = (TextView)
                view.findViewById(R.id.imfdt_tv_contentText);
        holder.unOriginalFavoriteTopic.ivPic = (ImageView)
                view.findViewById(R.id.imfdt_iv_contentPic);
        //初始化底部功能栏
        holder.unOriginalFavoriteTopic.ivShare = (ImageView)
                view.findViewById(R.id.iidbf_iv_share);
        holder.unOriginalFavoriteTopic.ivHeart = (ImageView)
                view.findViewById(R.id.iidbf_iv_heart);
        view.findViewById(R.id.iidbf_iv_comment).setVisibility(View.GONE);
        view.findViewById(R.id.iidbf_tv_commentNum).setVisibility(View.GONE);
        holder.unOriginalFavoriteTopic.tvFavorite_count = (TextView)
                view.findViewById(R.id.iidbf_tv_likeNum);

        //设置点击事件
        holder.dynamicContent.setOnClickListener(clickMyFavoriteItemListener);
        holder.unOriginalFavoriteTopic.ivShare.setOnClickListener(clickMyFavoriteItemListener);
        holder.unOriginalFavoriteTopic.ivHeart.setOnClickListener(clickMyFavoriteItemListener);
        holder.unOriginalFavoriteTopic.tvOriginalNickName
                .setOnClickListener(clickMyFavoriteItemListener);
        return holder;
    }

    /**
     * 初始化圈子中动态：普通
     */
    private ViewHolder initMyFavorite_InCircle_Normal(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_my_favorite_circle_dynamic_normal, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化id
        if (holder.dynamicInCircleNormal == null) {
            holder.dynamicInCircleNormal = new DynamicInCircleNormal();
        }
        holder.viewType = MY_FAVORITE_DYNAMIC_IN_CIRCLE_NORMAL;

        holder.dynamicContent = view.findViewById(R.id.myFavoriteDynamic_content);
        //初始化作者信息
        holder.dynamicInCircleNormal.tvOriginalNickName = (TextView)
                view.findViewById(R.id.iidbf_tv_originator_nickname);
        //初始化文章数据
        holder.dynamicInCircleNormal.tvTime = (TextView) view.findViewById(R.id.imfcdn_tv_time);
        holder.dynamicInCircleNormal.tvContent = (TextView)
                view.findViewById(R.id.imfcdn_tv_contentText);
        holder.dynamicInCircleNormal.ivPic = (ImageView)
                view.findViewById(R.id.imfcdn_iv_contentPic);
        //初始化底部功能栏
        holder.dynamicInCircleNormal.ivShare = (ImageView)
                view.findViewById(R.id.iidbf_iv_share);
        holder.dynamicInCircleNormal.ivHeart = (ImageView)
                view.findViewById(R.id.iidbf_iv_heart);
        holder.dynamicInCircleNormal.ivComment = (ImageView)
                view.findViewById(R.id.iidbf_iv_comment);
        holder.dynamicInCircleNormal.tvComment_count = (TextView)
                view.findViewById(R.id.iidbf_tv_commentNum);
        holder.dynamicInCircleNormal.tvFavorite_count = (TextView)
                view.findViewById(R.id.iidbf_tv_likeNum);
        //初始化监听事件
        holder.dynamicContent.setOnClickListener(clickMyFavoriteItemListener);
        holder.dynamicInCircleNormal.ivShare.setOnClickListener(clickMyFavoriteItemListener);
        holder.dynamicInCircleNormal.ivComment.setOnClickListener(clickMyFavoriteItemListener);
        holder.dynamicInCircleNormal.ivHeart.setOnClickListener(clickMyFavoriteItemListener);
        holder.dynamicInCircleNormal.tvOriginalNickName
                .setOnClickListener(clickMyFavoriteItemListener);
        return holder;
    }

    /**
     * 初始化圈子中动态：话题
     */
    private ViewHolder initMyFavorite_InCircle_Topic(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_my_favorite_circle_dynamic_topic, parent, false);
        ViewHolder holder = new ViewHolder(view);
        if (holder.dynamicInCircleTopic == null) {
            holder.dynamicInCircleTopic = new DynamicInCircleTopic();
        }
        holder.viewType = MY_FAVORITE_DYNAMIC_IN_CIRCLE_TOPIC;

        holder.dynamicContent = view.findViewById(R.id.myFavoriteDynamic_content);
        //初始化作者信息
        holder.dynamicInCircleTopic.tvOriginalNickName = (TextView)
                view.findViewById(R.id.iidbf_tv_originator_nickname);
        //初始化文章数据
        holder.dynamicInCircleTopic.tvTime = (TextView) view.findViewById(R.id.imfcdt_tv_time);
        holder.dynamicInCircleTopic.tvTitle = (TextView)
                view.findViewById(R.id.imfcdt_tv_topicName);
        holder.dynamicInCircleTopic.tvPartIn_count = (TextView)
                view.findViewById(R.id.imfcdt_tv_partInNum);
        holder.dynamicInCircleTopic.tvContent = (TextView)
                view.findViewById(R.id.imfcdt_tv_contentText);
        holder.dynamicInCircleTopic.ivPic = (ImageView)
                view.findViewById(R.id.imfcdt_iv_contentPic);
        //初始化底部功能栏
        holder.dynamicInCircleTopic.ivShare = (ImageView)
                view.findViewById(R.id.iidbf_iv_share);
        holder.dynamicInCircleTopic.ivHeart = (ImageView)
                view.findViewById(R.id.iidbf_iv_heart);
        view.findViewById(R.id.iidbf_iv_comment).setVisibility(View.GONE);
        view.findViewById(R.id.iidbf_tv_commentNum).setVisibility(View.GONE);
        holder.dynamicInCircleTopic.tvFavorite_count = (TextView)
                view.findViewById(R.id.iidbf_tv_likeNum);

        //设置监听事件
        holder.dynamicContent.setOnClickListener(clickMyFavoriteItemListener);
        holder.dynamicInCircleTopic.ivShare.setOnClickListener(clickMyFavoriteItemListener);
        holder.dynamicInCircleTopic.ivHeart.setOnClickListener(clickMyFavoriteItemListener);
        holder.dynamicInCircleTopic.tvOriginalNickName
                .setOnClickListener(clickMyFavoriteItemListener);
        return holder;
    }

    /**
     * 初始化圈子中动态：文章
     */
    private ViewHolder initMyFavorite_InCircle_Article(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_my_favorite_circle_dynamic_article, parent, false);
        ViewHolder holder = new ViewHolder(view);
        if (holder.dynamicInCircleArticle == null) {
            holder.dynamicInCircleArticle = new DynamicInCircleArticle();
        }
        holder.viewType = MY_FAVORITE_DYNAMIC_IN_CIRCLE_ARTICLE;

        holder.dynamicContent = view.findViewById(R.id.myFavoriteDynamic_content);
        //初始化作者信息
        holder.dynamicInCircleArticle.tvOriginalNickName = (TextView)
                view.findViewById(R.id.iidbf_tv_originator_nickname);
        //初始化文章数据
        holder.dynamicInCircleArticle.tvTime = (TextView) view.findViewById(R.id.imfcda_tv_time);
        holder.dynamicInCircleArticle.tvTitle = (TextView)
                view.findViewById(R.id.imfcda_tv_articleName);
        holder.dynamicInCircleArticle.tvContent = (TextView)
                view.findViewById(R.id.imfcda_tv_contentText);
        holder.dynamicInCircleArticle.ivPic = (ImageView)
                view.findViewById(R.id.imfcda_iv_contentPic);
        //初始化底部功能栏
        holder.dynamicInCircleArticle.ivShare = (ImageView)
                view.findViewById(R.id.iidbf_iv_share);
        holder.dynamicInCircleArticle.ivHeart = (ImageView)
                view.findViewById(R.id.iidbf_iv_heart);
        holder.dynamicInCircleArticle.ivComment = (ImageView)
                view.findViewById(R.id.iidbf_iv_comment);
        holder.dynamicInCircleArticle.tvComment_count = (TextView)
                view.findViewById(R.id.iidbf_tv_commentNum);
        holder.dynamicInCircleArticle.tvFavorite_count = (TextView)
                view.findViewById(R.id.iidbf_tv_likeNum);
        //设置监听事件
        holder.dynamicContent.setOnClickListener(clickMyFavoriteItemListener);
        holder.dynamicInCircleArticle.ivShare.setOnClickListener(clickMyFavoriteItemListener);
        holder.dynamicInCircleArticle.ivComment.setOnClickListener(clickMyFavoriteItemListener);
        holder.dynamicInCircleArticle.ivHeart.setOnClickListener(clickMyFavoriteItemListener);
        holder.dynamicInCircleArticle.tvOriginalNickName
                .setOnClickListener(clickMyFavoriteItemListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = holder.viewType;
        holder.dynamicContent.setBackgroundResource(bg_selector[position % 2]);
//        Log.d(TAG, "我是第" + position + "个条目，我要显示的动态类型是：" + viewType);
        //绑定数据
        switch (viewType) {
            case MY_FAVORITE_DYNAMIC_ARTICLE://文章动态
                bindDynamicArticleData(holder, position);
                break;
            case MY_FAVORITE_DYNAMIC_NORMAL://普通动态
                bindDynamicNormalData(holder, position);
                break;
            case MY_FAVORITE_DYNAMIC_TOPIC://话题动态
                bindDynamicTopicData(holder, position);
                break;
            case MY_FAVORITE_DYNAMIC_IN_CIRCLE_ARTICLE://圈子动态：文章
                bindDynamicInCircleArticleData(holder, position);
                break;
            case MY_FAVORITE_DYNAMIC_IN_CIRCLE_TOPIC://圈子动态：话题
                bindDynamicInCircleTopicData(holder, position);
                break;
            case MY_FAVORITE_DYNAMIC_IN_CIRCLE_NORMAL://圈子动态：普通
                bindDynamicInCircleNormalData(holder, position);
                break;
            default:
                bindBlankDynamic(holder, position);
                break;
        }
    }

    /**
     * 绑定圈子中的文章动态数据
     */
    private void bindDynamicInCircleArticleData(ViewHolder holder, int position) {
        CircleArticleDynamic circleArticleDynamic =
                (CircleArticleDynamic) favoriteDynamicDatas.get(position);
        if (circleArticleDynamic == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        //绑定TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.dynamicInCircleArticle.ivShare,
                holder.dynamicInCircleArticle.ivComment,
                holder.dynamicInCircleArticle.ivHeart,
                holder.dynamicInCircleArticle.tvOriginalNickName);

        //设置数据进去
        CircleInfo circle = circleArticleDynamic.getCircle();
        //设置用户数据
        if (circle == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        holder.dynamicContent.setVisibility(View.VISIBLE);
        holder.dynamicInCircleArticle.tvOriginalNickName.setText(circle.getCircleName());
        //解析数据
        holder.dynamicInCircleArticle.tvTime.setText(circleArticleDynamic.getDynamic_created_at());
        holder.dynamicInCircleArticle.tvTitle.setText(circleArticleDynamic.getTitle());
        //设置文章数据
        List<CircleArticleDynamic.ArticleContent> articleContents
                = circleArticleDynamic.getArticleContents();
        if (articleContents == null || articleContents.size() == 0) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        String doc = null;
        String img = null;
        for (CircleArticleDynamic.ArticleContent articleContent :
                articleContents) {
            if (StringUtils.isEmpty(doc)) {
                doc = articleContent.getDoc();
            }
            if (StringUtils.isEmpty(img)) {
                img = articleContent.getImg();
            }
            if (!StringUtils.isEmpty(doc, img)) {
                break;
            }
        }
        holder.dynamicInCircleArticle.tvContent.setText(doc);
        if (StringUtils.isEmpty(img)) {
            holder.dynamicInCircleArticle.ivPic.setVisibility(View.GONE);
        } else {
            holder.dynamicInCircleArticle.ivPic.setVisibility(View.VISIBLE);
            loadImage(img, holder.dynamicInCircleArticle.ivPic);
        }
        holder.dynamicInCircleArticle.tvComment_count.setText(
                circleArticleDynamic.getCount_of_comment() + "");
        holder.dynamicInCircleArticle.tvFavorite_count.setText(
                circleArticleDynamic.getCount_of_favorite() + "");
        if (circleArticleDynamic.isHas_favorite()) {
            holder.dynamicInCircleArticle.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.dynamicInCircleArticle.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
    }

    /**
     * 绑定圈子中的普通动态
     */
    private void bindDynamicInCircleNormalData(ViewHolder holder, int position) {
        CircleNormalDynamic normalDynamic =
                (CircleNormalDynamic) favoriteDynamicDatas.get(position);
        if (normalDynamic == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        //绑定TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.dynamicInCircleNormal.ivShare,
                holder.dynamicInCircleNormal.ivComment,
                holder.dynamicInCircleNormal.ivHeart,
                holder.dynamicInCircleNormal.tvOriginalNickName);
        //设置数据进去
        CircleInfo circle = normalDynamic.getCircle();
        //设置用户数据
        if (circle == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        holder.dynamicContent.setVisibility(View.VISIBLE);
        holder.dynamicInCircleNormal.tvOriginalNickName.setText(circle.getCircleName());
        //解析数据
        holder.dynamicInCircleNormal.tvTime.setText(normalDynamic.getDynamic_created_at());
        //设置文章数据
        CircleNormalDynamic.NormalContent normalContent = normalDynamic.getNormalContent();
        if (normalContent == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        String img;
        String doc = normalContent.getDoc();
        if (normalContent.getImg() == null || normalContent.getImg().size() == 0) {
            img = null;
        } else {
            img = normalContent.getImg().get(0);
        }
        holder.dynamicInCircleNormal.tvContent.setText(doc);
        if (StringUtils.isEmpty(img)) {
            holder.dynamicInCircleNormal.ivPic.setVisibility(View.GONE);
        } else {
            holder.dynamicInCircleNormal.ivPic.setVisibility(View.VISIBLE);
            loadImage(img, holder.dynamicInCircleNormal.ivPic);
        }
        holder.dynamicInCircleNormal.tvComment_count.setText(
                normalDynamic.getCount_of_comment() + "");
        holder.dynamicInCircleNormal.tvFavorite_count.setText(
                normalDynamic.getCount_of_favorite() + "");
        if (normalDynamic.isHas_favorite()) {
            holder.dynamicInCircleNormal.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.dynamicInCircleNormal.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
    }

    /**
     * 绑定圈子中的话题数据
     */
    private void bindDynamicInCircleTopicData(ViewHolder holder, int position) {
        CircleTopicDynamic topicDynamic =
                (CircleTopicDynamic) favoriteDynamicDatas.get(position);
        if (topicDynamic == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        //绑定TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.dynamicInCircleTopic.ivShare,
                holder.dynamicInCircleTopic.ivHeart,
                holder.dynamicInCircleTopic.tvOriginalNickName);
        //设置数据进去
        CircleInfo circle = topicDynamic.getCircle();
        //设置用户数据
        if (circle == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        holder.dynamicContent.setVisibility(View.VISIBLE);
        holder.dynamicInCircleTopic.tvOriginalNickName.setText(circle.getCircleName());
        //解析数据
        holder.dynamicInCircleTopic.tvTime.setText(topicDynamic.getDynamic_created_at());
        holder.dynamicInCircleTopic.tvTitle.setText(topicDynamic.getTitle());
        //设置文章数据
        CircleTopicDynamic.TopicContent topicContent = topicDynamic.getTopicContent();
        if (topicContent == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        String img = topicContent.getImg();
        String doc = topicContent.getDoc();
        holder.dynamicInCircleTopic.tvContent.setText(doc);
        if (StringUtils.isEmpty(img)) {
            holder.dynamicInCircleTopic.ivPic.setVisibility(View.GONE);
        } else {
            holder.dynamicInCircleTopic.ivPic.setVisibility(View.VISIBLE);
            loadImage(img, holder.dynamicInCircleTopic.ivPic);
        }
        holder.dynamicInCircleTopic.tvPartIn_count.setText(
                topicDynamic.getCount_of_participation() + "");
        holder.dynamicInCircleTopic.tvFavorite_count.setText(
                topicDynamic.getCount_of_favorite() + "");
        if (topicDynamic.isHas_favorite()) {
            holder.dynamicInCircleTopic.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.dynamicInCircleTopic.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
    }

    /**
     * 绑定喜欢的原创文章数据
     */
    private void bindDynamicArticleData(ViewHolder holder, int position) {
        OriginalArticleDynamic articleDynamic =
                (OriginalArticleDynamic) favoriteDynamicDatas.get(position);
        if (articleDynamic == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        //绑定TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.unOriginalFavoriteArticle.ivShare,
                holder.unOriginalFavoriteArticle.ivComment,
                holder.unOriginalFavoriteArticle.ivHeart,
                holder.unOriginalFavoriteArticle.tvOriginalNickName);
        //设置数据进去
        UserInfo user = articleDynamic.getUser();
        //设置用户数据
        if (user == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        holder.dynamicContent.setVisibility(View.VISIBLE);
        holder.unOriginalFavoriteArticle.tvOriginalNickName.setText(user.getNickName());
        //解析数据
        holder.unOriginalFavoriteArticle.tvTime.setText(articleDynamic.getDynamic_created_at());
        holder.unOriginalFavoriteArticle.tvTitle.setText(articleDynamic.getTitle());
        //设置文章数据
        List<OriginalArticleDynamic.ArticleContent> articleContents
                = articleDynamic.getArticleContents();
        if (articleContents == null || articleContents.size() == 0) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        String doc = null;
        String img = null;
        for (OriginalArticleDynamic.ArticleContent articleContent :
                articleContents) {
            if (StringUtils.isEmpty(doc)) {
                doc = articleContent.getDoc();
            }
            if (StringUtils.isEmpty(img)) {
                img = articleContent.getImg();
            }
            if (!StringUtils.isEmpty(doc, img)) {
                break;
            }
        }
        holder.unOriginalFavoriteArticle.tvContent.setText(doc);
        if (StringUtils.isEmpty(img)) {
            holder.unOriginalFavoriteArticle.ivPic.setVisibility(View.GONE);
        } else {
            holder.unOriginalFavoriteArticle.ivPic.setVisibility(View.VISIBLE);
            loadImage(img, holder.unOriginalFavoriteArticle.ivPic);
        }
        holder.unOriginalFavoriteArticle.tvComment_count.setText(
                articleDynamic.getCount_of_comment() + "");
        holder.unOriginalFavoriteArticle.tvFavorite_count.setText(
                articleDynamic.getCount_of_favorite() + "");
        if (articleDynamic.isHas_favorite()) {
            holder.unOriginalFavoriteArticle.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.unOriginalFavoriteArticle.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
    }

    /**
     * 绑定原创的普通动态
     */
    private void bindDynamicNormalData(ViewHolder holder, int position) {
        OriginalNormalDynamic normalDynamic =
                (OriginalNormalDynamic) favoriteDynamicDatas.get(position);
        if (normalDynamic == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        //绑定TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.unOriginalFavoriteNormal.ivShare,
                holder.unOriginalFavoriteNormal.ivComment,
                holder.unOriginalFavoriteNormal.ivHeart,
                holder.unOriginalFavoriteNormal.tvOriginalNickName);
        //设置数据进去
        UserInfo user = normalDynamic.getUser();
        //设置用户数据
        if (user == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        holder.dynamicContent.setVisibility(View.VISIBLE);
        holder.unOriginalFavoriteNormal.tvOriginalNickName.setText(user.getNickName());
        //解析数据
        holder.unOriginalFavoriteNormal.tvTime.setText(normalDynamic.getDynamic_created_at());
        //设置文章数据
        OriginalNormalDynamic.NormalContent normalContent = normalDynamic.getNormalContent();
        if (normalContent == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        String img;
        String doc = normalContent.getDoc();
        if (normalContent.getImg() == null || normalContent.getImg().size() == 0) {
            img = null;
        } else {
            img = normalContent.getImg().get(0);
        }
        holder.unOriginalFavoriteNormal.tvContent.setText(doc);
        if (StringUtils.isEmpty(img)) {
            holder.unOriginalFavoriteNormal.ivPic.setVisibility(View.GONE);
        } else {
            holder.unOriginalFavoriteNormal.ivPic.setVisibility(View.VISIBLE);
            loadImage(img, holder.unOriginalFavoriteNormal.ivPic);
        }
        holder.unOriginalFavoriteNormal.tvComment_count.setText(
                normalDynamic.getCount_of_comment() + "");
        holder.unOriginalFavoriteNormal.tvFavorite_count.setText(
                normalDynamic.getCount_of_favorite() + "");
        if (normalDynamic.isHas_favorite()) {
            holder.unOriginalFavoriteNormal.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.unOriginalFavoriteNormal.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
    }

    /**
     * 绑定原创的话题动态数据
     */
    private void bindDynamicTopicData(ViewHolder holder, int position) {
        OriginalTopicDynamic topicDynamic =
                (OriginalTopicDynamic) favoriteDynamicDatas.get(position);
        if (topicDynamic == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        //绑定TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.unOriginalFavoriteTopic.ivShare,
                holder.unOriginalFavoriteTopic.ivHeart,
                holder.unOriginalFavoriteTopic.tvOriginalNickName);
        //设置数据进去
        UserInfo user = topicDynamic.getUser();
        //设置用户数据
        if (user == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        holder.dynamicContent.setVisibility(View.VISIBLE);
        holder.unOriginalFavoriteTopic.tvOriginalNickName.setText(user.getNickName());
        //解析数据
        holder.unOriginalFavoriteTopic.tvTime.setText(topicDynamic.getDynamic_created_at());
        holder.unOriginalFavoriteTopic.tvTitle.setText(topicDynamic.getTitle());
        //设置文章数据
        OriginalTopicDynamic.TopicContent topicContent = topicDynamic.getTopicContent();
        if (topicContent == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        String img = topicContent.getImg();
        String doc = topicContent.getDoc();
        holder.unOriginalFavoriteTopic.tvContent.setText(doc);
        if (StringUtils.isEmpty(img)) {
            holder.unOriginalFavoriteTopic.ivPic.setVisibility(View.GONE);
        } else {
            holder.unOriginalFavoriteTopic.ivPic.setVisibility(View.VISIBLE);
            loadImage(img, holder.unOriginalFavoriteTopic.ivPic);
        }
        holder.unOriginalFavoriteTopic.tvPartIn_count.setText(
                topicDynamic.getCount_of_participation() + "");
        holder.unOriginalFavoriteTopic.tvFavorite_count.setText(
                topicDynamic.getCount_of_favorite() + "");
        if (topicDynamic.isHas_favorite()) {
            holder.unOriginalFavoriteTopic.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.unOriginalFavoriteTopic.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
    }

    /**
     * 处理空的动态的数据
     */
    private void bindBlankDynamic(ViewHolder holder, int position) {
        holder.dynamicContent.setVisibility(View.GONE);
    }

    /**
     * 加载图片
     */
    private void loadImage(String img, ImageView ivPic) {
        ImageLoaderFactory.getInstance().displayImage(ivPic, img);
    }

    @Override
    public int getItemCount() {
        return favoriteDynamicDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        int viewType;//视图类型
        View dynamicContent;
        UnOriginalFavoriteNormal unOriginalFavoriteNormal;//喜欢普通动态
        UnOriginalFavoriteTopic unOriginalFavoriteTopic;//喜欢话题动态
        UnOriginalFavoriteArticle unOriginalFavoriteArticle;//喜欢ha的文章动态
        DynamicInCircleNormal dynamicInCircleNormal;//圈子内动态：普通
        DynamicInCircleTopic dynamicInCircleTopic;//圈子内动态：话题
        DynamicInCircleArticle dynamicInCircleArticle;//圈子内动态：文章
    }

    /**
     * 非原创——喜欢：普通动态
     */
    private class UnOriginalFavoriteNormal {
        //原创作者信息
        TextView tvOriginalNickName;//原创作者名称
        //相关作品信息
        ImageView ivPic;
        TextView tvTime;
        TextView tvContent;//内容
        TextView tvComment_count;//评论人数
        TextView tvFavorite_count;//喜欢的人数

        ImageView ivHeart;//是否被关注的心型图标
        ImageView ivShare;//分享的图标
        ImageView ivComment;//评论
    }

    /**
     * 非原创——喜欢：话题动态
     */
    private class UnOriginalFavoriteTopic {
        //原创作者信息
        TextView tvOriginalNickName;//原创作者名称
        //相关作品信息
        ImageView ivPic;
        TextView tvTime;
        TextView tvTitle;
        TextView tvContent;//内容
        TextView tvFavorite_count;//喜欢的人数
        TextView tvPartIn_count;//参与人数

        ImageView ivHeart;//是否被关注的心型图标
        ImageView ivShare;//分享的图标
    }

    /**
     * 非原创——喜欢：文章动态
     */
    private class UnOriginalFavoriteArticle {
        //原创作者信息
        TextView tvOriginalNickName;//原创作者名称
        //相关作品信息
        ImageView ivPic;
        TextView tvTime;
        TextView tvTitle;
        TextView tvContent;//内容
        TextView tvFavorite_count;//喜欢的人数
        TextView tvComment_count;//评论的人数

        ImageView ivHeart;//是否被关注的心型图标
        ImageView ivShare;//分享的图标
        ImageView ivComment;//评论的图标
    }


    /**
     * 圈子内动态：普通动态
     */
    private class DynamicInCircleNormal {
        //原创作者信息
        TextView tvOriginalNickName;//原创作者名称
        //相关作品信息
        ImageView ivPic;
        TextView tvTime;
        TextView tvContent;//内容
        TextView tvComment_count;//评论人数
        TextView tvFavorite_count;//喜欢的人数

        ImageView ivHeart;//是否被关注的心型图标
        ImageView ivShare;//分享的图标
        ImageView ivComment;//评论
    }

    /**
     * 圈子内动态：文章动态
     */
    private class DynamicInCircleArticle {
        //原创作者信息
        TextView tvOriginalNickName;//原创作者名称
        //相关作品信息
        ImageView ivPic;
        TextView tvTime;
        TextView tvTitle;
        TextView tvContent;//内容
        TextView tvFavorite_count;//喜欢的人数
        TextView tvComment_count;//评论的人数

        ImageView ivHeart;//是否被关注的心型图标
        ImageView ivShare;//分享的图标
        ImageView ivComment;//评论的图标
    }

    /**
     * 圈子内动态：话题动态
     */
    private class DynamicInCircleTopic {
        //原创作者信息
        TextView tvOriginalNickName;//原创作者名称
        //相关作品信息
        ImageView ivPic;
        TextView tvTime;
        TextView tvTitle;
        TextView tvContent;//内容
        TextView tvFavorite_count;//喜欢的人数
        TextView tvPartIn_count;//参与人数

        ImageView ivHeart;//是否被关注的心型图标
        ImageView ivShare;//分享的图标
    }

    /**
     * 我喜欢的动态的点击监听事件
     */
    public interface OnMyFavoriteItemClickListener {

        /**
         * 当动态内容点击的回调方法
         *
         * @param position 条目数
         */
        void onItemClick(int position);

        /**
         * 当分享条目点击的回调
         *
         * @param position 条目数
         */
        void onItemShareClick(int position);

        /**
         * 当评论条目被点击的回调
         *
         * @param position 条目数
         */
        void onItemCommentClick(int position);

        /**
         * 喜欢条目被点击的回调
         *
         * @param position 条目数
         */
        void onItemFavoriteClick(int position);

        /**
         * 名称条目的点击回调方法
         *
         * @param position 条目数
         */
        void onItemNameClick(int position);
    }

    /**
     * 设置子条目的点击监听事件
     */
    public void setOnMyFavoriteItemClickListener(
            OnMyFavoriteItemClickListener onMyFavoriteItemClickListener) {
        this.onMyFavoriteItemClickListener = onMyFavoriteItemClickListener;
    }

    /**
     * 我的喜欢的点击监听回调
     */
    private View.OnClickListener clickMyFavoriteItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onMyFavoriteItemClickListener == null) {
                return;
            }
            Bundle bundle = (Bundle) view.getTag();
            if (bundle == null) {
                return;
            }
            int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
            int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
            switch (viewId) {
                case R.id.myFavoriteDynamic_content://主体
                    onMyFavoriteItemClickListener.onItemClick(position);
                    break;
                case R.id.iidbf_iv_share://分享
                    onMyFavoriteItemClickListener.onItemShareClick(position);
                    break;
                case R.id.iidbf_iv_comment://评论
                    onMyFavoriteItemClickListener.onItemCommentClick(position);
                    break;
                case R.id.iidbf_iv_heart://喜欢
                    onMyFavoriteItemClickListener.onItemFavoriteClick(position);
                    break;
                case R.id.iidbf_tv_originator_nickname://名称
                    onMyFavoriteItemClickListener.onItemNameClick(position);
                    break;
            }
        }
    };
}
