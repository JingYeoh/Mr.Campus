package com.jkb.mrcampus.adapter.recycler.dynamic;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.data.index.dynamic.IndexDynamicData;
import com.jkb.core.data.index.dynamic.original.IndexOriginalArticleDynamicData;
import com.jkb.core.data.index.dynamic.original.IndexOriginalDynamicData;
import com.jkb.core.data.index.dynamic.original.IndexOriginalNormalDynamicData;
import com.jkb.core.data.index.dynamic.original.IndexOriginalTopicDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.circle.IndexSubscribeDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.favorite.IndexFavoriteArticleDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.favorite.IndexFavoriteDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.favorite.IndexFavoriteNormalDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.favorite.IndexFavoriteTopicDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.postInCircle.IndexCircleArticleDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.postInCircle.IndexCircleDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.postInCircle.IndexCircleNormalDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.postInCircle.IndexCircleTopicDynamicData;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.info.dynamic.content.DynamicContentArticleInfo;
import com.jkb.core.data.info.dynamic.content.DynamicContentNormalInfo;
import com.jkb.core.data.info.dynamic.content.DynamicContentTopicInfo;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 动态的适配器
 * Created by JustKiddingBaby on 2016/8/23.
 */

public class DynamicAdapter2 extends RecyclerView.Adapter<DynamicAdapter2.ViewHolder>
        implements View.OnClickListener {

    private static final String TAG = "DynamicAdapter";
    private Context context;

    public List<IndexDynamicData> dynamicDatas;

    //回调
    private OnDynamicItemClickListener onDynamicItemClickListener;

    //用到的常量
    private static final int ORIGINAL_TYPE_NORMAL = 1001;
    private static final int ORIGINAL_TYPE_TOPIC = 1002;
    private static final int ORIGINAL_TYPE_ARTICLE = 1003;
    private static final int UNORIGINAL_TYPE_SUBSCRIBE_CIRCLE = 2001;
    private static final int UNORIGINAL_TYPE_FAVORITE_NORMAL = 2010;//喜欢的动态
    private static final int UNORIGINAL_TYPE_FAVORITE_TOPIC = 2011;//喜欢的话题动态
    private static final int UNORIGINAL_TYPE_FAVORITE_ARTICLE = 2012;//喜欢的文章动态
    private static final int UNORIGINAL_TYPE_CIRCLE_TOPIC = 2020;//设为常用圈子：话题
    private static final int UNORIGINAL_TYPE_CIRCLE_NORMAL = 2021;//设为常用圈子：普通
    private static final int UNORIGINAL_TYPE_CIRCLE_ARTICLE = 2022;//设为常用圈子：文章
    private static final int DYNAMIC_BLANK = 3000;//空白


    public DynamicAdapter2(Context context, List<IndexDynamicData> dynamicBaseData) {
        this.context = context;
        if (dynamicBaseData == null) {
            dynamicBaseData = new ArrayList<>();
        }
        this.dynamicDatas = dynamicBaseData;
    }

    @Override
    public int getItemViewType(int position) {
        int itemType;
        IndexDynamicData dynamicBaseData = dynamicDatas.get(position);
        if (dynamicBaseData instanceof IndexOriginalDynamicData) {
            itemType = judgeOriginalDynamic(dynamicBaseData);
        } else if (dynamicBaseData instanceof IndexFavoriteDynamicData) {
            itemType = judgeFavoriteDynamic(dynamicBaseData);
        } else if (dynamicBaseData instanceof IndexCircleDynamicData) {
            itemType = judgeCircleDynamic(dynamicBaseData);
        } else if (dynamicBaseData instanceof IndexSubscribeDynamicData) {
            itemType = UNORIGINAL_TYPE_SUBSCRIBE_CIRCLE;
        } else {
            itemType = DYNAMIC_BLANK;
        }
        return itemType;
    }

    /**
     * 判断圈子动态
     */
    private int judgeCircleDynamic(IndexDynamicData dynamicBaseData) {
        if (dynamicBaseData instanceof IndexCircleNormalDynamicData) {
            return UNORIGINAL_TYPE_CIRCLE_NORMAL;
        } else if (dynamicBaseData instanceof IndexCircleTopicDynamicData) {
            return UNORIGINAL_TYPE_CIRCLE_TOPIC;
        } else if (dynamicBaseData instanceof IndexCircleArticleDynamicData) {
            return UNORIGINAL_TYPE_CIRCLE_ARTICLE;
        } else {
            return DYNAMIC_BLANK;
        }
    }

    /**
     * 判断喜欢的动态
     */
    private int judgeFavoriteDynamic(IndexDynamicData dynamicBaseData) {
        if (dynamicBaseData instanceof IndexFavoriteNormalDynamicData) {
            return UNORIGINAL_TYPE_FAVORITE_NORMAL;
        } else if (dynamicBaseData instanceof IndexFavoriteTopicDynamicData) {
            return UNORIGINAL_TYPE_FAVORITE_TOPIC;
        } else if (dynamicBaseData instanceof IndexFavoriteArticleDynamicData) {
            return UNORIGINAL_TYPE_FAVORITE_ARTICLE;
        } else {
            return DYNAMIC_BLANK;
        }
    }

    /**
     * 判断原创动态
     */
    private int judgeOriginalDynamic(IndexDynamicData dynamicBaseData) {
        if (dynamicBaseData instanceof IndexOriginalNormalDynamicData) {
            return ORIGINAL_TYPE_NORMAL;
        } else if (dynamicBaseData instanceof IndexOriginalTopicDynamicData) {
            return ORIGINAL_TYPE_TOPIC;
        } else if (dynamicBaseData instanceof IndexOriginalArticleDynamicData) {
            return ORIGINAL_TYPE_ARTICLE;
        } else {
            return DYNAMIC_BLANK;
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        DynamicAdapter2.ViewHolder holder;
        switch (viewType) {
            case ORIGINAL_TYPE_NORMAL:
                holder = initOriginalNormal(inflater, parent);//初始化原创——文章组件
                break;
            case ORIGINAL_TYPE_ARTICLE:
                holder = initOriginalArticle(inflater, parent);//初始化原创——文章组件
                break;
            case ORIGINAL_TYPE_TOPIC:
                holder = initOriginalTopic(inflater, parent);//初始化原创——话题组件
                break;
            case UNORIGINAL_TYPE_SUBSCRIBE_CIRCLE:
                holder = initSubscribeCircle(inflater, parent);//初始化非原创——订阅圈子的相关组件
                break;
            case UNORIGINAL_TYPE_FAVORITE_NORMAL://喜欢的普通动态
                holder = initFavoriteNormal(inflater, parent);//初始化非原创——普通组件
                break;
            case UNORIGINAL_TYPE_FAVORITE_TOPIC://喜欢的话题动态
                holder = initFavoriteTopic(inflater, parent);//初始化非原创——话题组件
                break;
            case UNORIGINAL_TYPE_FAVORITE_ARTICLE://喜欢的文章动态
                holder = initFavoriteArticle(inflater, parent);//初始化非原创——文章组件
                break;
            case UNORIGINAL_TYPE_CIRCLE_NORMAL://圈子内发布：普通
                holder = initCircleDynamicNormal(inflater, parent);
                break;
            case UNORIGINAL_TYPE_CIRCLE_ARTICLE://圈子内发布：文章
                holder = initCircleDynamicArticle(inflater, parent);
                break;
            case UNORIGINAL_TYPE_CIRCLE_TOPIC://圈子内发布：话题
                holder = initCircleDynamicTopic(inflater, parent);
                break;
            default://默认....
                holder = initBlackDynamic(inflater, parent);
                break;
        }
        holder.viewType = viewType;
        return holder;
    }

    /**
     * 初始化空白的动态
     */
    private ViewHolder initBlackDynamic(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_dynamic_blank,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.dynamic_content);
        return holder;
    }

    /**
     * 圈子动态——话题动态相关组件
     * 已设置监听事件：原创作者、头像、内容（待）、喜欢、分享
     */
    private ViewHolder initCircleDynamicTopic(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_dynamic_circleincommonuse_topic,
                parent, false);
        ViewHolder holder = new DynamicAdapter2.ViewHolder(view);
        if (holder.circleInCommonUseTopic == null) {
            holder.circleInCommonUseTopic = new UnOriginalCircleInCommonUseTopic();
        }
        holder.contentView = view.findViewById(R.id.dynamic_content);
        //初始化作者信息
        holder.circleInCommonUseTopic.iv_headImg = (ImageView) view.findViewById(R.id.iidc_iv_headImg);
        holder.circleInCommonUseTopic.tvName = (TextView) view.findViewById(R.id.iidc_tv_name);
        holder.circleInCommonUseTopic.tvTime = (TextView) view.findViewById(R.id.iidc_tv_time);
        holder.circleInCommonUseTopic.tvAction = (TextView) view.findViewById(R.id.iidc_tv_action);
        //原创作者信息
        holder.circleInCommonUseTopic.tvOriginalNickName =
                (TextView) view.findViewById(R.id.idct_tv_originator_nickname);
        //作品信息
        holder.circleInCommonUseTopic.tvTopicTitle =
                (TextView) view.findViewById(R.id.idct_tv_topicName);//话题名称
        holder.circleInCommonUseTopic.tvPartNum =
                (TextView) view.findViewById(R.id.idct_tv_partInNum);//参与人数
        holder.circleInCommonUseTopic.ivPic =
                (ImageView) view.findViewById(R.id.idct_iv_contentPic);
        holder.circleInCommonUseTopic.tvContent =
                (TextView) view.findViewById(R.id.idct_tv_contentText);
        holder.circleInCommonUseTopic.tvLikeNum =
                (TextView) view.findViewById(R.id.idct_tv_likeNum);
        holder.circleInCommonUseTopic.tvCommentNum =
                (TextView) view.findViewById(R.id.idct_tv_commentNum);
        holder.circleInCommonUseTopic.content = view.findViewById(R.id.idct_ll_content);
        holder.circleInCommonUseTopic.ivHeart = (ImageView) view.findViewById(R.id.idct_iv_heart);
        holder.circleInCommonUseTopic.ivShare = (ImageView) view.findViewById(R.id.idct_iv_share);
        holder.circleInCommonUseTopic.contentHeadImg = view.findViewById(R.id.iidc_contentHeadImg);

        //设置监听事件
        holder.circleInCommonUseTopic.tvOriginalNickName.setOnClickListener(this);
        holder.circleInCommonUseTopic.contentHeadImg.setOnClickListener(this);
        holder.circleInCommonUseTopic.ivHeart.setOnClickListener(this);
        holder.circleInCommonUseTopic.ivShare.setOnClickListener(this);
        holder.circleInCommonUseTopic.content.setOnClickListener(this);
        return holder;
    }

    /**
     * 圈子动态——普通动态相关组件
     * 已设置监听事件：原创作者、头像、内容（待）、喜欢、分享、评论
     */
    private ViewHolder initCircleDynamicNormal(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_dynamic_circleincommonuse_normal,
                parent, false);
        ViewHolder holder = new DynamicAdapter2.ViewHolder(view);
        if (holder.circleInCommonUseNormal == null) {
            holder.circleInCommonUseNormal = new UnOriginalCircleInCommonUseNormal();
        }
        holder.contentView = view.findViewById(R.id.dynamic_content);
        //初始化作者信息
        holder.circleInCommonUseNormal.iv_headImg = (ImageView) view.findViewById(R.id.iidc_iv_headImg);
        holder.circleInCommonUseNormal.tvName = (TextView) view.findViewById(R.id.iidc_tv_name);
        holder.circleInCommonUseNormal.tvTime = (TextView) view.findViewById(R.id.iidc_tv_time);
        holder.circleInCommonUseNormal.tvAction = (TextView) view.findViewById(R.id.iidc_tv_action);
        //原创作者信息
        holder.circleInCommonUseNormal.tvOriginalNickName =
                (TextView) view.findViewById(R.id.idcn_tv_originator_nickname);
        //作品信息
        holder.circleInCommonUseNormal.ivPic =
                (ImageView) view.findViewById(R.id.idcn_iv_contentPic);
        holder.circleInCommonUseNormal.tvContent =
                (TextView) view.findViewById(R.id.idcn_tv_contentText);
        holder.circleInCommonUseNormal.tvLikeNum =
                (TextView) view.findViewById(R.id.idcn_tv_likeNum);
        holder.circleInCommonUseNormal.tvCommentNum =
                (TextView) view.findViewById(R.id.idcn_tv_commentNum);
        holder.circleInCommonUseNormal.ivHeart =
                (ImageView) view.findViewById(R.id.idcn_iv_heart);
        holder.circleInCommonUseNormal.ivShare = (ImageView) view.findViewById(R.id.idcc_iv_share);
        holder.circleInCommonUseNormal.ivComment = (ImageView) view.findViewById(R.id.idcc_iv_comment);
        holder.circleInCommonUseNormal.content = view.findViewById(R.id.idcn_ll_content);
        holder.circleInCommonUseNormal.contentHeadImg = view.findViewById(R.id.iidc_contentHeadImg);

        //设置监听事件
        holder.circleInCommonUseNormal.ivHeart.setOnClickListener(this);
        holder.circleInCommonUseNormal.tvOriginalNickName.
                setOnClickListener(this);
        holder.circleInCommonUseNormal.contentHeadImg.setOnClickListener(this);
        holder.circleInCommonUseNormal.ivShare.setOnClickListener(this);
        holder.circleInCommonUseNormal.ivComment.setOnClickListener(this);
        holder.circleInCommonUseNormal.content.setOnClickListener(this);
        return holder;
    }

    /**
     * 圈子动态——文章动态相关组件
     * 已设置监听事件：原创作者、头像、内容（待）、喜欢、分享、评论
     */
    private ViewHolder initCircleDynamicArticle(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_dynamic_circleincommonuse_article,
                parent, false);
        ViewHolder holder = new DynamicAdapter2.ViewHolder(view);
        if (holder.circleInCommonUseArticle == null) {
            holder.circleInCommonUseArticle = new UnOriginalCircleInCommonUseArticle();
        }
        holder.contentView = view.findViewById(R.id.dynamic_content);
        //初始化作者信息
        holder.circleInCommonUseArticle.iv_headImg = (ImageView) view.findViewById(R.id.iidc_iv_headImg);
        holder.circleInCommonUseArticle.tvName = (TextView) view.findViewById(R.id.iidc_tv_name);
        holder.circleInCommonUseArticle.tvTime = (TextView) view.findViewById(R.id.iidc_tv_time);
        holder.circleInCommonUseArticle.tvAction = (TextView) view.findViewById(R.id.iidc_tv_action);
        //原创作者信息
        holder.circleInCommonUseArticle.tvOriginalNickName =
                (TextView) view.findViewById(R.id.idca_tv_originator_nickname);
        //作品信息
        holder.circleInCommonUseArticle.tvArticleTitle =
                (TextView) view.findViewById(R.id.idca_tv_articleName);//话题名称
        holder.circleInCommonUseArticle.ivPic =
                (ImageView) view.findViewById(R.id.idca_iv_contentPic);
        holder.circleInCommonUseArticle.tvContent =
                (TextView) view.findViewById(R.id.idca_tv_contentText);
        holder.circleInCommonUseArticle.tvLikeNum =
                (TextView) view.findViewById(R.id.idca_tv_likeNum);
        holder.circleInCommonUseArticle.tvCommentNum =
                (TextView) view.findViewById(R.id.idca_tv_commentNum);
        holder.circleInCommonUseArticle.content = view.findViewById(R.id.idca_ll_content);
        holder.circleInCommonUseArticle.ivHeart = (ImageView) view.findViewById(R.id.idca_iv_heart);
        holder.circleInCommonUseArticle.ivShare = (ImageView) view.findViewById(R.id.idca_iv_share);
        holder.circleInCommonUseArticle.ivComment = (ImageView) view.findViewById(R.id.idca_iv_comment);
        holder.circleInCommonUseArticle.contentHeadImg = view.findViewById(R.id.iidc_contentHeadImg);

        //设置监听事件
        holder.circleInCommonUseArticle.tvOriginalNickName.
                setOnClickListener(this);
        holder.circleInCommonUseArticle.contentHeadImg.setOnClickListener(this);
        holder.circleInCommonUseArticle.content.setOnClickListener(this);
        holder.circleInCommonUseArticle.ivHeart.setOnClickListener(this);
        holder.circleInCommonUseArticle.ivComment.setOnClickListener(this);
        holder.circleInCommonUseArticle.ivShare.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化非原创——喜欢-文章动态相关组件
     * 已设置监听事件：头像、喜欢、原创作者、内容、分享、评论
     */
    private ViewHolder initFavoriteArticle(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_dynamic_favorite_article,
                parent, false);
        ViewHolder holder = new DynamicAdapter2.ViewHolder(view);
        if (holder.unOriginalFavoriteArticle == null) {
            holder.unOriginalFavoriteArticle = new UnOriginalFavoriteArticle();
        }
        holder.contentView = view.findViewById(R.id.dynamic_content);
        //初始化作者信息
        holder.unOriginalFavoriteArticle.iv_headImg = (ImageView) view.findViewById(R.id.iidu_iv_headImg);
        holder.unOriginalFavoriteArticle.tvName = (TextView) view.findViewById(R.id.iidu_tv_name);
        holder.unOriginalFavoriteArticle.tvTime = (TextView) view.findViewById(R.id.iidu_tv_time);
        holder.unOriginalFavoriteArticle.tvAction = (TextView) view.findViewById(R.id.iidu_tv_action);
        //原创作者信息
        holder.unOriginalFavoriteArticle.tvOriginalNickName =
                (TextView) view.findViewById(R.id.idfa_tv_originator_nickname);
        //作品信息
        holder.unOriginalFavoriteArticle.tvArticleTitle =
                (TextView) view.findViewById(R.id.idfa_tv_articleName);//话题名称
        holder.unOriginalFavoriteArticle.ivPic =
                (ImageView) view.findViewById(R.id.idfa_iv_contentPic);
        holder.unOriginalFavoriteArticle.tvContent =
                (TextView) view.findViewById(R.id.idfa_tv_contentText);
        holder.unOriginalFavoriteArticle.tvLikeNum =
                (TextView) view.findViewById(R.id.idfa_tv_likeNum);
        holder.unOriginalFavoriteArticle.tvCommentNum =
                (TextView) view.findViewById(R.id.idfa_tv_commentNum);
        holder.unOriginalFavoriteArticle.ivHeart = (ImageView) view.findViewById(R.id.idfa_iv_heart);
        holder.unOriginalFavoriteArticle.ivShare = (ImageView) view.findViewById(R.id.idfa_iv_share);
        holder.unOriginalFavoriteArticle.ivComment = (ImageView) view.findViewById(R.id.idfa_iv_comment);
        holder.unOriginalFavoriteArticle.content = view.findViewById(R.id.idfa_ll_content);
        holder.unOriginalFavoriteArticle.contentHeadImg = view.findViewById(R.id.iidu_contentHeadImg);


        //设置点击事件
        holder.unOriginalFavoriteArticle.ivHeart.setOnClickListener(this);
        holder.unOriginalFavoriteArticle.contentHeadImg.setOnClickListener(this);
        holder.unOriginalFavoriteArticle.tvOriginalNickName.setOnClickListener(
                this);
        holder.unOriginalFavoriteArticle.ivShare.setOnClickListener(this);
        holder.unOriginalFavoriteArticle.ivComment.setOnClickListener(this);

        holder.unOriginalFavoriteArticle.content.setOnClickListener(this);//设置内容点击事件
        return holder;
    }

    /**
     * 初始化非原创——喜欢-话题动态相关组件
     * 已设置监听事件：头像、喜欢、原创作者、内容、分享、评论
     */
    private ViewHolder initFavoriteTopic(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_dynamic_favorite_topic,
                parent, false);
        ViewHolder holder = new DynamicAdapter2.ViewHolder(view);
        if (holder.unOriginalFavoriteTopic == null) {
            holder.unOriginalFavoriteTopic = new UnOriginalFavoriteTopic();
        }
        holder.contentView = view.findViewById(R.id.dynamic_content);
        //初始化作者信息
        holder.unOriginalFavoriteTopic.iv_headImg = (ImageView) view.findViewById(R.id.iidu_iv_headImg);
        holder.unOriginalFavoriteTopic.tvName = (TextView) view.findViewById(R.id.iidu_tv_name);
        holder.unOriginalFavoriteTopic.tvTime = (TextView) view.findViewById(R.id.iidu_tv_time);
        holder.unOriginalFavoriteTopic.tvAction = (TextView) view.findViewById(R.id.iidu_tv_action);
        //原创作者信息
        holder.unOriginalFavoriteTopic.tvOriginalNickName =
                (TextView) view.findViewById(R.id.idft_tv_originator_nickname);
        //作品信息
        holder.unOriginalFavoriteTopic.tvTopicTitle =
                (TextView) view.findViewById(R.id.idft_tv_topicName);//话题名称
        holder.unOriginalFavoriteTopic.tvPartNum =
                (TextView) view.findViewById(R.id.idft_tv_partInNum);//参与人数
        holder.unOriginalFavoriteTopic.ivPic =
                (ImageView) view.findViewById(R.id.idft_iv_contentPic);
        holder.unOriginalFavoriteTopic.tvContent =
                (TextView) view.findViewById(R.id.idft_tv_contentText);
        holder.unOriginalFavoriteTopic.tvLikeNum =
                (TextView) view.findViewById(R.id.idft_tv_likeNum);
        holder.unOriginalFavoriteTopic.tvCommentNum =
                (TextView) view.findViewById(R.id.idft_tv_commentNum);
        holder.unOriginalFavoriteTopic.ivHeart = (ImageView) view.findViewById(R.id.idft_iv_heart);
        holder.unOriginalFavoriteTopic.ivShare = (ImageView) view.findViewById(R.id.idft_iv_share);
        holder.unOriginalFavoriteTopic.content = view.findViewById(R.id.idft_ll_content);
        holder.unOriginalFavoriteTopic.ivComment = (ImageView) view.findViewById(R.id.idft_iv_comment);
        holder.unOriginalFavoriteTopic.contentHeadImg = view.findViewById(R.id.iidu_contentHeadImg);

        //设置点击事件
        holder.unOriginalFavoriteTopic.ivHeart.setOnClickListener(this);
        holder.unOriginalFavoriteTopic.contentHeadImg.setOnClickListener(this);
        holder.unOriginalFavoriteTopic.tvOriginalNickName.setOnClickListener(this);
        holder.unOriginalFavoriteTopic.ivShare.setOnClickListener(this);
        holder.unOriginalFavoriteTopic.ivComment.setOnClickListener(this);
        holder.unOriginalFavoriteTopic.content.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化非原创——喜欢-普通动态相关组件
     * 已设置监听事件：头像、喜欢、原创作者、内容、分享、评论
     */
    private ViewHolder initFavoriteNormal(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_dynamic_favorite_normal,
                parent, false);
        ViewHolder holder = new DynamicAdapter2.ViewHolder(view);
        if (holder.unOriginalFavoriteNormal == null) {
            holder.unOriginalFavoriteNormal = new UnOriginalFavoriteNormal();
        }
        holder.contentView = view.findViewById(R.id.dynamic_content);
        //初始化作者信息
        holder.unOriginalFavoriteNormal.iv_headImg = (ImageView) view.findViewById(R.id.iidu_iv_headImg);
        holder.unOriginalFavoriteNormal.tvName = (TextView) view.findViewById(R.id.iidu_tv_name);
        holder.unOriginalFavoriteNormal.tvTime = (TextView) view.findViewById(R.id.iidu_tv_time);
        holder.unOriginalFavoriteNormal.tvAction = (TextView) view.findViewById(R.id.iidu_tv_action);
        //原创作者信息
        holder.unOriginalFavoriteNormal.tvOriginalNickName =
                (TextView) view.findViewById(R.id.idfn_tv_originator_nickname);
        //作品信息
        holder.unOriginalFavoriteNormal.ivPic =
                (ImageView) view.findViewById(R.id.idfn_iv_contentPic);
        holder.unOriginalFavoriteNormal.tvContent =
                (TextView) view.findViewById(R.id.idfn_tv_contentText);
        holder.unOriginalFavoriteNormal.tvLikeNum =
                (TextView) view.findViewById(R.id.idfn_tv_likeNum);
        holder.unOriginalFavoriteNormal.tvCommentNum =
                (TextView) view.findViewById(R.id.idfn_tv_commentNum);
        holder.unOriginalFavoriteNormal.ivHeart =
                (ImageView) view.findViewById(R.id.idfn_iv_heart);
        holder.unOriginalFavoriteNormal.ivShare =
                (ImageView) view.findViewById(R.id.idfn_iv_share);
        holder.unOriginalFavoriteNormal.content = view.findViewById(R.id.idfn_ll_content);
        holder.unOriginalFavoriteNormal.ivComment = (ImageView) view.findViewById(R.id.idfn_iv_comment);
        holder.unOriginalFavoriteNormal.contentHeadImg = view.findViewById(R.id.iidu_contentHeadImg);
        //设置监听事件
        holder.unOriginalFavoriteNormal.contentHeadImg.setOnClickListener(this);
        holder.unOriginalFavoriteNormal.tvOriginalNickName.setOnClickListener(this);
        holder.unOriginalFavoriteNormal.ivHeart.setOnClickListener(this);
        holder.unOriginalFavoriteNormal.ivShare.setOnClickListener(this);
        holder.unOriginalFavoriteNormal.ivComment.setOnClickListener(this);
        holder.unOriginalFavoriteNormal.content.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化非原创——订阅圈子的相关组件
     * 已设置监听事件：头像、圈子内容主体
     */
    private ViewHolder initSubscribeCircle(LayoutInflater inflater, ViewGroup parent) {
        //随机方向
        View view;
        view = inflater.inflate(R.layout.item_dynamic_subscribe_circle_right,
                parent, false);
        ViewHolder holder = new DynamicAdapter2.ViewHolder(view);
        if (holder.unOriginalSubscribeCircle == null) {
            holder.unOriginalSubscribeCircle = new UnOriginalSubscribeCircle();
        }
        holder.contentView = view.findViewById(R.id.dynamic_content);
        //圈子相关
        holder.unOriginalSubscribeCircle.ivCirclePicBg =
                (ImageView) view.findViewById(R.id.idsc_iv_picBg);
        holder.unOriginalSubscribeCircle.ivCirclePicture =
                (ImageView) view.findViewById(R.id.idsc_iv_picture);
        holder.unOriginalSubscribeCircle.tvCircleName =
                (TextView) view.findViewById(R.id.idsc_tv_name);
        holder.unOriginalSubscribeCircle.tvCircleContent =
                (TextView) view.findViewById(R.id.idsc_tv_content);
        holder.unOriginalSubscribeCircle.tvCircleType =
                (TextView) view.findViewById(R.id.idsc_tv_circleType);
        holder.unOriginalSubscribeCircle.tvCircleOperationNum =
                (TextView) view.findViewById(R.id.idsc_tv_operationCount);
        holder.unOriginalSubscribeCircle.tvCircleSubscribeNum =
                (TextView) view.findViewById(R.id.idsc_tv_dynamicsCount);
        //作者相关
        holder.unOriginalSubscribeCircle.ivHeadImg = (ImageView) view.findViewById(R.id.ius_iv_headImg);
        holder.unOriginalSubscribeCircle.tvName = (TextView) view.findViewById(R.id.ius_tv_name);
        holder.unOriginalSubscribeCircle.tvTime = (TextView) view.findViewById(R.id.ius_tv_time);
        holder.unOriginalSubscribeCircle.tvAction = (TextView) view.findViewById(R.id.ius_tv_action);
        holder.unOriginalSubscribeCircle.contentHeadImg = view.findViewById(R.id.ius_contentHeadImg);

        holder.unOriginalSubscribeCircle.circleContent = view.findViewById(R.id.idsc_circleContent);
        holder.contentView = view.findViewById(R.id.dynamic_content);

        //设置监听事件
        holder.unOriginalSubscribeCircle.contentHeadImg.setOnClickListener(this);
//        holder.unOriginalSubscribeCircle.circleContent.setOnClickListener(this);
        holder.contentView.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化原创——文章的相关组件
     * 已设置监听事件：头像,喜欢、分享、评论、主体
     */
    private ViewHolder initOriginalArticle(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_dynamic_orginal_article,
                parent, false);
        ViewHolder holder = new DynamicAdapter2.ViewHolder(view);
        if (holder.originalArticle == null) {
            holder.originalArticle = new OriginalArticle();
        }
        holder.contentView = view.findViewById(R.id.dynamic_content);
        holder.originalArticle.iv_headImg = (ImageView) view.findViewById(R.id.idoa_iv_headImg);
        holder.originalArticle.iv_picture = (ImageView) view.findViewById(R.id.idoa_iv_pic);
        holder.originalArticle.contentPicture = view.findViewById(R.id.idoa_contentPicture);
        holder.originalArticle.tvName = (TextView) view.findViewById(R.id.idoa_tv_name);
        holder.originalArticle.tvTime = (TextView) view.findViewById(R.id.idoa_tv_time);
        holder.originalArticle.tvTitle = (TextView) view.findViewById(R.id.idoa_tv_title);
        holder.originalArticle.tvContent = (TextView) view.findViewById(R.id.idoa_tv_content);
        holder.originalArticle.tvLikeNum = (TextView) view.findViewById(R.id.idoa_tv_likeNum);
        holder.originalArticle.tvCommentNum = (TextView) view.findViewById(R.id.idoa_tv_commentNum);
        holder.originalArticle.tvAction = (TextView) view.findViewById(R.id.idoa_tv_action);
        holder.originalArticle.ivHeart =
                (ImageView) view.findViewById(R.id.idoa_iv_heart);
        holder.originalArticle.ivShare = (ImageView) view.findViewById(R.id.idoa_iv_share);
        holder.originalArticle.ivComment = (ImageView) view.findViewById(R.id.idoa_iv_comment);
        holder.originalArticle.contentHeadImg = view.findViewById(R.id.idoa_contentHeadImg);

        //设置监听事件
        holder.originalArticle.contentHeadImg.setOnClickListener(this);
        holder.originalArticle.ivHeart.setOnClickListener(this);
        holder.originalArticle.ivShare.setOnClickListener(this);
        holder.originalArticle.ivComment.setOnClickListener(this);
        holder.originalArticle.contentPicture.setOnClickListener(this);
        holder.contentView.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化原创——话题的相关组件
     * 已设置监听事件：头像,喜欢、分享、主体
     */
    private ViewHolder initOriginalTopic(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_dynamic_original_topic,
                parent, false);
        ViewHolder holder = new DynamicAdapter2.ViewHolder(view);
        if (holder.originalTopic == null) {
            holder.originalTopic = new OriginalTopic();
        }
        holder.contentView = view.findViewById(R.id.dynamic_content);
        holder.originalTopic.iv_headImg = (ImageView) view.findViewById(R.id.idot_iv_headImg);
        holder.originalTopic.iv_picture = (ImageView) view.findViewById(R.id.idot_iv_pic);
        holder.originalTopic.contentPicture = view.findViewById(R.id.idot_contentPicture);
        holder.originalTopic.tvName = (TextView) view.findViewById(R.id.idot_tv_name);
        holder.originalTopic.tvTime = (TextView) view.findViewById(R.id.idot_tv_time);
        holder.originalTopic.tvTitle = (TextView) view.findViewById(R.id.idot_tv_title);
        holder.originalTopic.tvContent = (TextView) view.findViewById(R.id.idot_tv_content);
        holder.originalTopic.tvPartNum = (TextView) view.findViewById(R.id.idot_tv_partInNum);
        holder.originalTopic.tvLikeNum = (TextView) view.findViewById(R.id.idot_tv_likeNum);
        holder.originalTopic.tvAction = (TextView) view.findViewById(R.id.idot_tv_action);
        holder.originalTopic.ivHeart =
                (ImageView) view.findViewById(R.id.idot_iv_heart);
        holder.originalTopic.ivShare = (ImageView) view.findViewById(R.id.idot_iv_share);
        holder.originalTopic.contentHeadImg = view.findViewById(R.id.idot_contentHeadImg);

        //设置监听事件
        holder.originalTopic.contentHeadImg.setOnClickListener(this);
        holder.originalTopic.ivHeart.setOnClickListener(this);
        holder.originalTopic.ivShare.setOnClickListener(this);
        holder.originalTopic.contentPicture.setOnClickListener(this);
        holder.contentView.setOnClickListener(this);

        return holder;
    }

    /**
     * 初始化原创——普通的相关组件
     * 已设置监听事件：头像,喜欢、分享、评论、主体
     */
    private ViewHolder initOriginalNormal(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_dynamic_orginal_normal,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        if (holder.originalNormal == null) {
            holder.originalNormal = new OriginalNormal();
        }
        holder.contentView = view.findViewById(R.id.dynamic_content);
        //初始化图片相关
        holder.originalNormal.pictures = view.findViewById(R.id.idon_ll_pictures);
        holder.originalNormal.pic1To3 = view.findViewById(R.id.idon_ll_pic1To3);
        holder.originalNormal.pic2To3 = view.findViewById(R.id.idon_ll_pic2To3);
        holder.originalNormal.pic4To6 = view.findViewById(R.id.idon_ll_pic4To6);
        holder.originalNormal.ivPic1 = (ImageView) view.findViewById(R.id.idon_iv_pic1);
        holder.originalNormal.ivPic2 = (ImageView) view.findViewById(R.id.idon_iv_pic2);
        holder.originalNormal.ivPic3 = (ImageView) view.findViewById(R.id.idon_iv_pic3);
        holder.originalNormal.ivPic4 = (ImageView) view.findViewById(R.id.idon_iv_pic4);
        holder.originalNormal.ivPic5 = (ImageView) view.findViewById(R.id.idon_iv_pic5);
        holder.originalNormal.ivPic6 = (ImageView) view.findViewById(R.id.idon_iv_pic6);
        holder.originalNormal.contentPic1 = view.findViewById(R.id.idon_contentPic1);
        holder.originalNormal.contentPic2 = view.findViewById(R.id.idon_contentPic2);
        holder.originalNormal.contentPic3 = view.findViewById(R.id.idon_contentPic3);
        holder.originalNormal.contentPic4 = view.findViewById(R.id.idon_contentPic4);
        holder.originalNormal.contentPic5 = view.findViewById(R.id.idon_contentPic5);
        holder.originalNormal.contentPic6 = view.findViewById(R.id.idon_contentPic6);
        //初始化其它组件
        holder.originalNormal.tvName = (TextView) view.findViewById(R.id.idon_tv_name);
        holder.originalNormal.tvTime = (TextView) view.findViewById(R.id.idon_tv_time);
        holder.originalNormal.ivHeadImg = (ImageView) view.findViewById(R.id.idon_iv_headImg);
        holder.originalNormal.tvContent = (TextView) view.findViewById(R.id.idon_tv_content);
        holder.originalNormal.tvCommentNum = (TextView) view.findViewById(R.id.idon_tv_commentNum);
        holder.originalNormal.tvLikeNum = (TextView) view.findViewById(R.id.idon_tv_likeNum);
        holder.originalNormal.tvAction = (TextView) view.findViewById(R.id.idon_tv_action);
        holder.originalNormal.ivHeart =
                (ImageView) view.findViewById(R.id.idon_iv_heart);
        holder.originalNormal.ivShare = (ImageView) view.findViewById(R.id.idon_iv_share);
        holder.originalNormal.ivComment = (ImageView) view.findViewById(R.id.idon_iv_comment);
        holder.originalNormal.contentHeadImg = view.findViewById(R.id.idon_contentHeadImg);

        //设置监听事件
        holder.originalNormal.ivHeart.setOnClickListener(this);
        holder.originalNormal.contentHeadImg.setOnClickListener(this);
        holder.originalNormal.ivShare.setOnClickListener(this);
        holder.originalNormal.ivComment.setOnClickListener(this);
        holder.contentView.setOnClickListener(this);
        holder.originalNormal.contentPic1.setOnClickListener(this);
        holder.originalNormal.contentPic2.setOnClickListener(this);
        holder.originalNormal.contentPic3.setOnClickListener(this);
        holder.originalNormal.contentPic4.setOnClickListener(this);
        holder.originalNormal.contentPic5.setOnClickListener(this);
        holder.originalNormal.contentPic6.setOnClickListener(this);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置作者信息
        handleDynamicData(holder, position);
    }

    /**
     * 处理用户数据
     */
    private void handleDynamicData(ViewHolder holder, int position) {
        IndexDynamicData dynamicBaseData = dynamicDatas.get(position);
        if (dynamicBaseData == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        //设置作者信息
        switch (holder.viewType) {
            case ORIGINAL_TYPE_NORMAL:
                handleDynamicNormalData(holder, position);//绑定普通数据
                break;
            case ORIGINAL_TYPE_ARTICLE:
                handleDynamicArticleData(holder, position);//绑定文章数据
                break;
            case ORIGINAL_TYPE_TOPIC:
                handleDynamicTopicData(holder, position);//绑定话题数据
                break;
            case UNORIGINAL_TYPE_SUBSCRIBE_CIRCLE:
                handleCircleSubscribe(holder, position);//绑定圈子数据
                break;
            case UNORIGINAL_TYPE_FAVORITE_NORMAL:
                handleFavoriteNormal(holder, position);//绑定喜欢普通动态数据
                break;
            case UNORIGINAL_TYPE_FAVORITE_ARTICLE://绑定喜欢文章动态数据
                handleFavoriteArticle(holder, position);//绑定喜欢普通动态数据
                break;
            case UNORIGINAL_TYPE_FAVORITE_TOPIC:
                handleFavoriteTopic(holder, position);//绑定喜欢话题动态数据
                break;
            case UNORIGINAL_TYPE_CIRCLE_ARTICLE:
                handleCircleDynamicArticle(holder, position);//绑定圈子内文章动态
                break;
            case UNORIGINAL_TYPE_CIRCLE_NORMAL:
                handleCircleDynamicNormal(holder, position);//绑定圈子内普通动态
                break;
            case UNORIGINAL_TYPE_CIRCLE_TOPIC:
                handleCircleDynamicTopic(holder, position);//绑定圈子内话题动态
                break;
            default:
                handleBlackDynamic(holder);
                break;
        }
    }

    /**
     * 处理空的数据
     */
    private void handleBlackDynamic(ViewHolder holder) {
        holder.contentView.setVisibility(View.GONE);
    }

    /**
     * 解析圈子内动态：话题
     * 已绑定Tag：原创作者、头像、图片、分享
     */
    private void handleCircleDynamicTopic(ViewHolder holder, int position) {
        Log.d(TAG, "handleCircleDynamicTopic");
        IndexCircleTopicDynamicData dynamicData = (IndexCircleTopicDynamicData)
                dynamicDatas.get(position);
        //绑定Tag对象
        ClassUtils.bindViewsTag(position,
                holder.circleInCommonUseTopic.tvOriginalNickName,
                holder.circleInCommonUseTopic.ivShare,
                holder.circleInCommonUseTopic.content,
                holder.circleInCommonUseTopic.contentHeadImg);
        //设置圈子信息
        holder.circleInCommonUseTopic.tvAction.setText(dynamicData.getActionTitle());
        holder.circleInCommonUseTopic.tvTime.setText(dynamicData.getUpdated_at());

        CircleInfo circleBean = dynamicData.getCircle();
        holder.circleInCommonUseTopic.tvName.setText(circleBean.getCircleName());
        //设置头像
        String headImgUrl = circleBean.getPictureUrl();
        if (!StringUtils.isEmpty(headImgUrl)) {
            bindImageViewAndUrl(holder.circleInCommonUseTopic.iv_headImg, headImgUrl);
        }
        //设置原创作者信息
        UserInfo user = dynamicData.getUser();
        holder.circleInCommonUseTopic.tvOriginalNickName.setText(user.getNickName());
        //设置作品信息
        DynamicContentTopicInfo topic = dynamicData.getTopic();
        if (topic == null) {
            holder.contentView.setVisibility(View.GONE);
        } else {
            String image = topic.getImg();
            if (StringUtils.isEmpty(image)) {
                holder.circleInCommonUseTopic.ivPic.setVisibility(View.GONE);
            } else {
                holder.circleInCommonUseTopic.ivPic.setVisibility(View.VISIBLE);
                bindImageViewAndUrl(holder.circleInCommonUseTopic.ivPic, image);
            }
            holder.circleInCommonUseTopic.tvContent.setText(topic.getDoc());
        }
        holder.circleInCommonUseTopic.tvTopicTitle.setText(
                dynamicData.getTitle());
        holder.circleInCommonUseTopic.tvCommentNum.setText(
                dynamicData.getCount_of_comment() + "");
        holder.circleInCommonUseTopic.tvLikeNum.setText(
                dynamicData.getCount_of_favorite() + "");
        holder.circleInCommonUseTopic.tvPartNum.setText(
                dynamicData.getCount_of_participation() + "");
    }

    /**
     * 解析圈子内动态：普通
     * 已绑定Tag：原创作者、头像、图片、分享、评论
     */
    private void handleCircleDynamicNormal(ViewHolder holder, int position) {
        Log.d(TAG, "handleCircleDynamicNormal");
        IndexCircleNormalDynamicData dynamicData = (IndexCircleNormalDynamicData)
                dynamicDatas.get(position);
        //绑定Tag对象
        ClassUtils.bindViewsTag(position,
                holder.circleInCommonUseNormal.ivHeart,
                holder.circleInCommonUseNormal.tvOriginalNickName,
                holder.circleInCommonUseNormal.ivShare,
                holder.circleInCommonUseNormal.content,
                holder.circleInCommonUseNormal.ivComment,
                holder.circleInCommonUseNormal.contentHeadImg);

        //设置圈子信息
        holder.circleInCommonUseNormal.tvAction.setText(dynamicData.getActionTitle());
        holder.circleInCommonUseNormal.tvTime.setText(dynamicData.getUpdated_at());

        CircleInfo circleBean = dynamicData.getCircle();
        holder.circleInCommonUseNormal.tvName.setText(circleBean.getCircleName());
        //设置头像
        String headImgUrl = circleBean.getPictureUrl();
        if (!StringUtils.isEmpty(headImgUrl)) {
            bindImageViewAndUrl(holder.circleInCommonUseNormal.iv_headImg, headImgUrl);
        }
        //设置原创作者信息
        UserInfo user = dynamicData.getUser();
        holder.circleInCommonUseNormal.tvOriginalNickName.setText(user.getNickName());
        //设置作品信息
        DynamicContentNormalInfo normal = dynamicData.getNormal();
        if (normal == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        List<String> image = normal.getImg();
        if (image != null && image.size() > 0) {
            //绑定图片
            bindImageViewAndUrl(holder.circleInCommonUseNormal.ivPic, image.get(0));
        } else {
            holder.circleInCommonUseNormal.ivPic.setVisibility(View.GONE);
        }
        holder.circleInCommonUseNormal.tvContent.setText(normal.getDoc());
        holder.circleInCommonUseNormal.tvCommentNum.setText(
                dynamicData.getCount_of_comment() + "");
        holder.circleInCommonUseNormal.tvLikeNum.setText(
                dynamicData.getCount_of_favorite() + "");

        //动态是否被关注
        if (dynamicData.isHasFavorite()) {
            holder.circleInCommonUseNormal.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.circleInCommonUseNormal.ivHeart.setImageResource(R.drawable.ic_heart_list_white);
        }
    }

    /**
     * 解析圈子内动态：文章
     * 已绑定Tag：原创作者、头像、图片、分享、评论
     */
    private void handleCircleDynamicArticle(ViewHolder holder, int position) {
        Log.d(TAG, "handleCircleDynamicArticle");
        IndexCircleArticleDynamicData dynamicData = (IndexCircleArticleDynamicData)
                dynamicDatas.get(position);
        //绑定Tag对象
        ClassUtils.bindViewsTag(position,
                holder.circleInCommonUseArticle.tvOriginalNickName,
                holder.circleInCommonUseArticle.ivShare,
                holder.circleInCommonUseArticle.ivComment,
                holder.circleInCommonUseArticle.content,
                holder.circleInCommonUseArticle.contentHeadImg);

        //设置圈子信息
        holder.circleInCommonUseArticle.tvAction.setText(dynamicData.getActionTitle());
        holder.circleInCommonUseArticle.tvTime.setText(dynamicData.getUpdated_at());

        CircleInfo circleBean = dynamicData.getCircle();
        if (circleBean == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        holder.circleInCommonUseArticle.tvName.setText(circleBean.getCircleName());
        //设置头像
        String headImgUrl = circleBean.getPictureUrl();
        if (!StringUtils.isEmpty(headImgUrl)) {
            bindImageViewAndUrl(holder.circleInCommonUseArticle.iv_headImg, headImgUrl);
        }
        UserInfo user = dynamicData.getUser();
        //设置原创作者信息
        holder.circleInCommonUseArticle.tvOriginalNickName.setVisibility(View.VISIBLE);
        holder.circleInCommonUseArticle.tvOriginalNickName.
                setText(user.getNickName());
        //设置作品信息
        DynamicContentArticleInfo article = dynamicData.getArticle();
        List<DynamicContentArticleInfo.Article> articleBeans = article.getArticle();
        if (articleBeans == null || articleBeans.size() == 0) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        String img = null;
        String doc = null;
        for (DynamicContentArticleInfo.Article articleItem :
                articleBeans) {
            if (!StringUtils.isEmpty(img, doc)) {
                break;
            }
            if (StringUtils.isEmpty(img)) {
                img = articleItem.getImg();
            }
            if (StringUtils.isEmpty(doc)) {
                doc = articleItem.getDoc();
            }
        }
        if (StringUtils.isEmpty(img)) {
            holder.circleInCommonUseArticle.ivPic.setVisibility(View.GONE);
        } else {
            holder.circleInCommonUseArticle.ivPic.setVisibility(View.VISIBLE);
            bindImageViewAndUrl(holder.circleInCommonUseArticle.ivPic, img);
        }
        holder.circleInCommonUseArticle.tvContent.setText(doc);
        holder.circleInCommonUseArticle.tvArticleTitle.setText(
                dynamicData.getTitle());//设置文章名称
        holder.circleInCommonUseArticle.tvCommentNum.setText(
                dynamicData.getCount_of_comment() + "");
        holder.circleInCommonUseArticle.tvLikeNum.setText(
                dynamicData.getCount_of_favorite() + "");
    }

    /**
     * 解析喜欢——话题动态数据
     * 绑定Tag对象：头像、喜欢、原创作者、图片、分享、评论、主体
     */
    private void handleFavoriteTopic(ViewHolder holder, int position) {
        Log.d(TAG, "handleFavoriteTopic");
        IndexFavoriteTopicDynamicData dynamicData = (IndexFavoriteTopicDynamicData)
                dynamicDatas.get(position);
        //绑定Tag对象
        ClassUtils.bindViewsTag(position,
                holder.unOriginalFavoriteTopic.tvOriginalNickName,
                holder.unOriginalFavoriteTopic.ivHeart,
                holder.unOriginalFavoriteTopic.ivShare,
                holder.unOriginalFavoriteTopic.ivComment,
                holder.unOriginalFavoriteTopic.content,
                holder.unOriginalFavoriteTopic.contentHeadImg);
        //设置用户数据
        holder.unOriginalFavoriteTopic.tvName.setText(dynamicData.getCreator_nickname());
        holder.unOriginalFavoriteTopic.tvTime.setText(dynamicData.getUpdated_at());
        holder.unOriginalFavoriteTopic.tvAction.setText(dynamicData.getActionTitle());
        //设置头像
        String headImgUrl = dynamicData.getCreator_avatar();
        if (!StringUtils.isEmpty(headImgUrl)) {
            bindImageViewAndUrl(holder.unOriginalFavoriteTopic.iv_headImg, headImgUrl);
        }
        //设置原创作者信息
        if (dynamicData.isCircleDynamic()) {
            String circleName = dynamicData.getCircle().getCircleName();
            holder.unOriginalFavoriteTopic.tvOriginalNickName.setText(circleName);
        } else {
            holder.unOriginalFavoriteTopic.tvOriginalNickName.
                    setText(dynamicData.getCreator_nickname());
        }
        //设置作品信息
        DynamicContentTopicInfo topic = dynamicData.getTopic();
        if (topic == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        String image = topic.getImg();
        if (!StringUtils.isEmpty(image)) {
            //绑定图片
            holder.unOriginalFavoriteTopic.ivPic.setVisibility(View.VISIBLE);
            bindImageViewAndUrl(holder.unOriginalFavoriteTopic.ivPic, image);
        } else {
            holder.unOriginalFavoriteTopic.ivPic.setVisibility(View.GONE);
        }
        holder.unOriginalFavoriteTopic.tvContent.setText(topic.getDoc());
        holder.unOriginalFavoriteTopic.tvCommentNum.setText(
                dynamicData.getCount_of_comment() + "");
        holder.unOriginalFavoriteTopic.tvLikeNum.setText(
                dynamicData.getCount_of_favorite() + "");
        holder.unOriginalFavoriteTopic.tvPartNum.setText(
                dynamicData.getCount_of_participation() + "");

        holder.unOriginalFavoriteTopic.tvTopicTitle.setText(
                dynamicData.getTitle());

        //动态是否被关注
        if (dynamicData.isHasFavorite()) {
            holder.unOriginalFavoriteTopic.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.unOriginalFavoriteTopic.ivHeart.setImageResource(R.drawable.ic_heart_list_white);
        }
    }

    /**
     * 解析喜欢——文章动态数据
     * 绑定Tag对象：头像、喜欢、原创作者、图片、分享、评论、主体
     */
    private void handleFavoriteArticle(ViewHolder holder, int position) {
        Log.d(TAG, "handleFavoriteArticle");
        IndexFavoriteArticleDynamicData articleDynamicData = (IndexFavoriteArticleDynamicData)
                dynamicDatas.get(position);
        //绑定Tag对象
        ClassUtils.bindViewsTag(position,
                holder.unOriginalFavoriteArticle.tvOriginalNickName,
                holder.unOriginalFavoriteArticle.ivHeart,
                holder.unOriginalFavoriteArticle.ivShare,
                holder.unOriginalFavoriteArticle.ivComment,
                holder.unOriginalFavoriteArticle.content,
                holder.unOriginalFavoriteArticle.contentHeadImg);

        //设置用户数据
        holder.unOriginalFavoriteArticle.tvName.setText(articleDynamicData.getCreator_nickname());
        holder.unOriginalFavoriteArticle.tvTime.setText(articleDynamicData.getUpdated_at());
        holder.unOriginalFavoriteArticle.tvAction.setText(articleDynamicData.getActionTitle());
        //设置头像
        String headImgUrl = articleDynamicData.getCreator_avatar();
        if (!StringUtils.isEmpty(headImgUrl)) {
            bindImageViewAndUrl(holder.unOriginalFavoriteArticle.iv_headImg, headImgUrl);
        }
        //设置原创作者信息
        if (articleDynamicData.isCircleDynamic()) {
            String circleName = articleDynamicData.getCircle().getCircleName();
            holder.unOriginalFavoriteArticle.tvOriginalNickName.setText(circleName);
        } else {
            holder.unOriginalFavoriteArticle.tvOriginalNickName.
                    setText(articleDynamicData.getCreator_nickname());
        }
        //设置作品信息
        DynamicContentArticleInfo article = articleDynamicData.getArticle();
        if (article == null) {
            holder.contentView.setVisibility(View.GONE);
        } else {
            List<DynamicContentArticleInfo.Article> articleBeen = article.getArticle();
            if (articleBeen == null || articleBeen.size() == 0) {
                holder.contentView.setVisibility(View.GONE);
                return;
            }
            String img = null;
            String doc = null;
            for (DynamicContentArticleInfo.Article articleItem :
                    articleBeen) {
                if (!StringUtils.isEmpty(img, doc)) {
                    break;
                }
                if (StringUtils.isEmpty(img)) {
                    img = articleItem.getImg();
                }
                if (StringUtils.isEmpty(doc)) {
                    doc = articleItem.getDoc();
                }
            }
            if (!StringUtils.isEmpty(img)) {
                //绑定图片
                bindImageViewAndUrl(holder.unOriginalFavoriteArticle.ivPic, img);
            } else {
                holder.unOriginalFavoriteArticle.ivPic.setVisibility(View.GONE);
            }
            holder.unOriginalFavoriteArticle.tvContent.setText(doc);
        }
        holder.unOriginalFavoriteArticle.tvCommentNum.setText(
                articleDynamicData.getCount_of_comment() + "");
        holder.unOriginalFavoriteArticle.tvLikeNum.setText(
                articleDynamicData.getCount_of_favorite() + "");

        holder.unOriginalFavoriteArticle.tvArticleTitle.setText(
                articleDynamicData.getTitle());

        //动态是否被关注
        if (articleDynamicData.isHasFavorite()) {
            holder.unOriginalFavoriteArticle.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.unOriginalFavoriteArticle.ivHeart.setImageResource(R.drawable.ic_heart_list_white);
        }
    }

    /**
     * 解析喜欢——普通动态数据
     * 绑定Tag对象：头像、喜欢、原创作者、图片、分享、评论、主体
     */
    private void handleFavoriteNormal(ViewHolder holder, int position) {
        IndexFavoriteNormalDynamicData normalDynamicData = (IndexFavoriteNormalDynamicData) dynamicDatas.get(position);
        if (normalDynamicData == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        //绑定Tag对象
        ClassUtils.bindViewsTag(position,
                holder.unOriginalFavoriteNormal.tvOriginalNickName,
                holder.unOriginalFavoriteNormal.ivHeart,
                holder.unOriginalFavoriteNormal.ivShare,
                holder.unOriginalFavoriteNormal.ivComment,
                holder.unOriginalFavoriteNormal.content,
                holder.unOriginalFavoriteNormal.contentHeadImg);

        //设置用户数据
        holder.unOriginalFavoriteNormal.tvName.setText(normalDynamicData.getCreator_nickname());
        holder.unOriginalFavoriteNormal.tvTime.setText(normalDynamicData.getUpdated_at());
        holder.unOriginalFavoriteNormal.tvAction.setText(normalDynamicData.getActionTitle());
        //设置头像
        String headImgUrl = normalDynamicData.getCreator_avatar();
        if (!StringUtils.isEmpty(headImgUrl)) {
            bindImageViewAndUrl(holder.unOriginalFavoriteNormal.iv_headImg, headImgUrl);
        }
        //设置原创作者信息
        if (normalDynamicData.isCircleDynamic()) {
            String circleName = normalDynamicData.getCircle().getCircleName();
            holder.unOriginalFavoriteNormal.tvOriginalNickName.setText(circleName);
        } else {
            holder.unOriginalFavoriteNormal.tvOriginalNickName.
                    setText(normalDynamicData.getCreator_nickname());
        }
        //设置作品信息
        DynamicContentNormalInfo normal = normalDynamicData.getNormal();
        if (normal == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        List<String> image = normal.getImg();
        if (image != null && image.size() > 0) {
            //绑定图片
            holder.unOriginalFavoriteNormal.ivPic.setVisibility(View.VISIBLE);
            bindImageViewAndUrl(holder.unOriginalFavoriteNormal.ivPic, image.get(0));
        } else {
            holder.unOriginalFavoriteNormal.ivPic.setVisibility(View.GONE);
        }
        holder.unOriginalFavoriteNormal.tvContent.setText(normal.getDoc());
        holder.unOriginalFavoriteNormal.tvCommentNum.setText(
                normalDynamicData.getCount_of_comment() + "");
        holder.unOriginalFavoriteNormal.tvLikeNum.setText(
                normalDynamicData.getCount_of_favorite() + "");

        //动态是否被关注
        if (normalDynamicData.isHasFavorite()) {
            holder.unOriginalFavoriteNormal.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.unOriginalFavoriteNormal.ivHeart.setImageResource(R.drawable.ic_heart_list_white);
        }
    }

    /**
     * 解析普通數據
     * 绑定Tag对象：头像、喜欢、图片、分享、评论、主体
     */
    private void handleDynamicNormalData(ViewHolder holder, int position) {
        Log.d(TAG, "handleDynamicNormalData");
        IndexOriginalNormalDynamicData normalDynamicData = (IndexOriginalNormalDynamicData)
                dynamicDatas.get(position);
        if (normalDynamicData == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        //绑定Tag对象
        ClassUtils.bindViewsTag(position,
                holder.originalNormal.ivHeart,
                holder.originalNormal.ivShare,
                holder.originalNormal.ivComment,
                holder.contentView,
                holder.originalNormal.contentHeadImg,
                holder.originalNormal.contentPic1,
                holder.originalNormal.contentPic2,
                holder.originalNormal.contentPic3,
                holder.originalNormal.contentPic4,
                holder.originalNormal.contentPic5,
                holder.originalNormal.contentPic6
        );

        //设置用户数据
        holder.originalNormal.tvName.setText(normalDynamicData.getCreator_nickname());
        holder.originalNormal.tvTime.setText(normalDynamicData.getUpdated_at());
        holder.originalNormal.tvAction.setText(normalDynamicData.getActionTitle());
        //设置头像
        String headImgUrl = normalDynamicData.getCreator_avatar();
        if (!StringUtils.isEmpty(headImgUrl)) {
            bindImageViewAndUrl(holder.originalNormal.ivHeadImg, headImgUrl);
        }
        DynamicContentNormalInfo normal = normalDynamicData.getNormal();
        int imageNum = 0;
        if (normal == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        //設置圖片
        initOriginalPicturesView(imageNum, holder);
        List<String> image = normal.getImg();
        if (image == null || image.size() == 0) {
            imageNum = 0;
        } else {
            imageNum = image.size();
            bindNormalImage(holder, image);
        }
        //設置圖片
        initOriginalPicturesView(imageNum, holder);
        //设置内容和图片
        holder.originalNormal.tvContent.setText(normal.getDoc());
        //设置其他
        holder.originalNormal.tvCommentNum.setText(
                normalDynamicData.getCount_of_comment() + "");
        holder.originalNormal.tvLikeNum.setText(
                normalDynamicData.getCount_of_favorite() + "");
        //动态是否被关注
        if (normalDynamicData.isHasFavorite()) {
            holder.originalNormal.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.originalNormal.ivHeart.setImageResource(R.drawable.ic_heart_list_white);
        }
    }

    /**
     * 绑定普通动态的图片
     */
    private void bindNormalImage(ViewHolder holder, List<String> image) {
        ImageView imageView[] = new ImageView[6];
        imageView[0] = holder.originalNormal.ivPic1;
        imageView[1] = holder.originalNormal.ivPic2;
        imageView[2] = holder.originalNormal.ivPic3;
        imageView[3] = holder.originalNormal.ivPic4;
        imageView[4] = holder.originalNormal.ivPic5;
        imageView[5] = holder.originalNormal.ivPic6;
        for (int i = 0; i < image.size(); i++) {
            bindImageViewAndUrl(imageView[i], image.get(i));
        }
    }

    /**
     * 解析话题数据
     * 绑定Tag对象：头像、喜欢、图片、分享、主体
     */
    private void handleDynamicTopicData(ViewHolder holder, int position) {
        Log.d(TAG, "handleDynamicTopicData");
        IndexOriginalTopicDynamicData topicDynamicData = (IndexOriginalTopicDynamicData)
                dynamicDatas.get(position);
        if (topicDynamicData == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }

        //绑定Tag对象
        ClassUtils.bindViewsTag(position,
                holder.originalTopic.ivHeart,
                holder.originalTopic.ivShare,
                holder.contentView,
                holder.originalTopic.contentPicture,
                holder.originalTopic.contentHeadImg);

        //设置用户数据
        holder.originalTopic.tvName.setText(topicDynamicData.getCreator_nickname());
        holder.originalTopic.tvTime.setText(topicDynamicData.getUpdated_at());
        holder.originalTopic.tvAction.setText(topicDynamicData.getActionTitle());
        //设置头像
        String headImgUrl = topicDynamicData.getCreator_avatar();
        if (!StringUtils.isEmpty(headImgUrl)) {
            bindImageViewAndUrl(holder.originalTopic.iv_headImg, headImgUrl);
        } else {
            holder.originalTopic.iv_headImg.setImageResource(R.drawable.ic_user_head);
        }
        //解析话题的数据
        holder.originalTopic.tvTitle.setText(topicDynamicData.getTitle());
        holder.originalTopic.tvPartNum.setText(topicDynamicData.getCount_of_participation() + "");
        //设置图片和内容
        DynamicContentTopicInfo topic = topicDynamicData.getTopic();
        if (topic != null) {
            holder.originalTopic.tvContent.setText(topic.getDoc());
            String img = topic.getImg();
            if (StringUtils.isEmpty(img)) {
                holder.originalTopic.iv_picture.setVisibility(View.GONE);
            } else {
                bindImageViewAndUrl(holder.originalTopic.iv_picture, topic.getImg());
            }
        }

        //动态是否被关注
        if (topicDynamicData.isHasFavorite()) {
            holder.originalTopic.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.originalTopic.ivHeart.setImageResource(R.drawable.ic_heart_list_white);
        }
    }

    /**
     * 解析订阅圈子操作的数据
     * 绑定Tag对象：头像、圈子主体、图片
     */
    private void handleCircleSubscribe(ViewHolder holder, int position) {
        Log.d(TAG, "handleCircleSubscribe");
        IndexSubscribeDynamicData subscribeDynamicData = (IndexSubscribeDynamicData) dynamicDatas.get(position);
        if (subscribeDynamicData == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        //绑定Tag对象
        ClassUtils.bindViewsTag(position,
                holder.unOriginalSubscribeCircle.circleContent,
                holder.contentView,
                holder.unOriginalSubscribeCircle.contentHeadImg);

        //设置用户数据
        holder.unOriginalSubscribeCircle.tvName.setText(subscribeDynamicData.getCreator_nickname());
        holder.unOriginalSubscribeCircle.tvTime.setText(subscribeDynamicData.getCreated_at());
        holder.unOriginalSubscribeCircle.tvAction.setText(subscribeDynamicData.getActionTitle());
        //设置头像
        String headImgUrl = subscribeDynamicData.getCreator_avatar();
        if (!StringUtils.isEmpty(headImgUrl)) {
            //设置TAG
            bindImageViewAndUrl(holder.unOriginalSubscribeCircle.ivHeadImg, headImgUrl);
        }
        CircleInfo circleData = subscribeDynamicData.getCircle();
        //设置圈子数据
        holder.unOriginalSubscribeCircle.tvCircleName.setText(circleData.getCircleName());
        holder.unOriginalSubscribeCircle.tvCircleContent.setText(circleData.getIntroduction());
        holder.unOriginalSubscribeCircle.tvCircleOperationNum.setText(circleData.getOperatorCount() + "");
        holder.unOriginalSubscribeCircle.tvCircleSubscribeNum.setText(circleData.getDynamicsCount() + "");
        holder.unOriginalSubscribeCircle.tvCircleType.setText(circleData.getCircleType());
        //设置图片
        bindImageViewAndUrl(holder.unOriginalSubscribeCircle.ivCirclePicture,
                circleData.getPictureUrl());
        bindBlurImageViewAndUrl(holder.unOriginalSubscribeCircle.ivCirclePicBg,
                circleData.getPictureUrl(), 25, 5);
    }

    /**
     * 解析文章数据
     * 绑定Tag对象：头像、喜欢、图片、分享、评论、主体
     */
    private void handleDynamicArticleData(ViewHolder holder, int position) {
        IndexOriginalArticleDynamicData articleDynamicData = (IndexOriginalArticleDynamicData)
                dynamicDatas.get(position);
        if (articleDynamicData == null) {
            holder.contentView.setVisibility(View.GONE);
            return;
        }
        //绑定Tag对象
        ClassUtils.bindViewsTag(position,
                holder.originalArticle.ivHeart,
                holder.originalArticle.ivShare,
                holder.originalArticle.ivComment,
                holder.contentView,
                holder.originalArticle.contentPicture,
                holder.originalArticle.contentHeadImg);

        //设置用户数据
        holder.originalArticle.tvName.setText(articleDynamicData.getCreator_nickname());
        holder.originalArticle.tvTime.setText(articleDynamicData.getUpdated_at());
        holder.originalArticle.tvAction.setText(articleDynamicData.getActionTitle());
        //设置头像
        String headImgUrl = articleDynamicData.getCreator_avatar();
        if (!StringUtils.isEmpty(headImgUrl)) {
            bindImageViewAndUrl(holder.originalArticle.iv_headImg, headImgUrl);
        }
        //设置文章数据
        DynamicContentArticleInfo article = articleDynamicData.getArticle();
        if (article != null) {
            List<DynamicContentArticleInfo.Article> articleBeen = article.getArticle();
            if (articleBeen != null && articleBeen.size() > 0) {
                DynamicContentArticleInfo.Article bean = articleBeen.get(0);
                holder.originalArticle.tvContent.setText(bean.getDoc());
                String picUrl = bean.getImg();
                if (StringUtils.isEmpty(picUrl)) {
                    holder.originalArticle.iv_picture.setVisibility(View.GONE);
                } else {
                    holder.originalArticle.iv_picture.setVisibility(View.VISIBLE);
                    bindImageViewAndUrl(holder.originalArticle.iv_picture, picUrl);
                }
            }
        }
        //设置文章标题
        holder.originalArticle.tvTitle.setText(articleDynamicData.getTitle());
        holder.originalArticle.tvCommentNum.setText(
                articleDynamicData.getCount_of_comment() + "");
        holder.originalArticle.tvLikeNum.setText(
                articleDynamicData.getCount_of_favorite() + "");

        //动态是否被关注
        if (articleDynamicData.isHasFavorite()) {
            holder.originalArticle.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.originalArticle.ivHeart.setImageResource(R.drawable.ic_heart_list_white);
        }
    }


    /**
     * 绑定图片到网址上
     */
    private void bindImageViewAndUrl(ImageView iv_headImg, String headImgUrl) {
        ImageLoaderFactory.getInstance().displayImage(iv_headImg, headImgUrl);
    }

    /**
     * 绑定图片到网址上并设置高斯模糊效果
     */
    private void bindBlurImageViewAndUrl(
            ImageView iv_headImg, String headImgUrl, int radius, int downSampling) {
        ImageLoaderFactory.getInstance().displayBlurImage(iv_headImg, headImgUrl, radius,
                downSampling);
    }

    /**
     * 初始化图片控件
     *
     * @param picNum 图片数量
     * @param holder holder
     */
    private void initOriginalPicturesView(int picNum, ViewHolder holder) {
        Log.d(TAG, "picNum=" + picNum);
        holder.originalNormal.pictures.setVisibility(View.GONE);
        holder.originalNormal.pic1To3.setVisibility(View.GONE);
        holder.originalNormal.pic2To3.setVisibility(View.GONE);
        holder.originalNormal.pic4To6.setVisibility(View.GONE);
        //如果没图片不执行下面动作
        if (picNum <= 0) {
            return;
        }
        //有图片时肯定至少一张
        holder.originalNormal.pictures.setVisibility(View.VISIBLE);
        holder.originalNormal.pic1To3.setVisibility(View.VISIBLE);
        if (picNum >= 1) {
            holder.originalNormal.contentPic1.setVisibility(View.VISIBLE);
        }
        if (picNum >= 2) {
            holder.originalNormal.pic2To3.setVisibility(View.VISIBLE);
            holder.originalNormal.contentPic2.setVisibility(View.VISIBLE);
            holder.originalNormal.contentPic3.setVisibility(View.INVISIBLE);
        }
        if (picNum >= 3) {
            holder.originalNormal.contentPic3.setVisibility(View.VISIBLE);
        }
        if (picNum >= 4) {
            holder.originalNormal.pic4To6.setVisibility(View.VISIBLE);
            holder.originalNormal.contentPic4.setVisibility(View.VISIBLE);
            holder.originalNormal.contentPic5.setVisibility(View.INVISIBLE);
            holder.originalNormal.contentPic6.setVisibility(View.INVISIBLE);
        }
        if (picNum >= 5) {
            holder.originalNormal.contentPic5.setVisibility(View.VISIBLE);
        }
        if (picNum >= 6) {
            holder.originalNormal.contentPic6.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dynamicDatas.size();
    }

    /**
     * ViewHolder类
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }

        View contentView;
        int viewType;
        //原创
        OriginalNormal originalNormal;//原创：普通
        OriginalTopic originalTopic;//原创：话题
        OriginalArticle originalArticle;//原创：文章
        //转载
        UnOriginalSubscribeCircle unOriginalSubscribeCircle;//订阅圈子
        UnOriginalFavoriteNormal unOriginalFavoriteNormal;//喜欢普通动态
        UnOriginalFavoriteTopic unOriginalFavoriteTopic;//喜欢话题动态
        UnOriginalFavoriteArticle unOriginalFavoriteArticle;//喜欢ha的文章动态
        //圈子内动态
        UnOriginalCircleInCommonUseNormal circleInCommonUseNormal;//圈子内普通
        UnOriginalCircleInCommonUseArticle circleInCommonUseArticle;//圈子内文章
        UnOriginalCircleInCommonUseTopic circleInCommonUseTopic;//圈子内话题
    }

    /**
     * 原创——普通
     */
    private class OriginalNormal {
        //关于图片
        View pictures;
        View pic1To3;
        View pic2To3;
        View pic4To6;
        ImageView ivPic1, ivPic2, ivPic3, ivPic4, ivPic5, ivPic6;
        View contentPic1, contentPic2, contentPic3, contentPic4, contentPic5, contentPic6;
        //其他
        TextView tvName;
        TextView tvTime;
        ImageView ivHeadImg;//头像
        TextView tvContent;//内容
        TextView tvCommentNum;//评论人数
        TextView tvLikeNum;//喜欢的人数
        TextView tvAction;//动作
        View contentHeadImg;//包裹头像的View

        ImageView ivHeart;//是否被关注的心型图标
        ImageView ivShare;//分享
        ImageView ivComment;//评论
    }

    /**
     * 原创——话题
     */
    private class OriginalTopic {
        ImageView iv_picture;
        View contentPicture;
        ImageView iv_headImg;
        TextView tvName;
        TextView tvTime;
        TextView tvPartNum;//参与人数
        TextView tvTitle;//标题
        TextView tvContent;//内容
        TextView tvLikeNum;//喜欢的人数
        TextView tvAction;//动作
        View contentHeadImg;//包裹头像的View

        ImageView ivHeart;//是否被关注的心型图标
        ImageView ivShare;//分享
    }

    /**
     * 原创——文章
     */
    private class OriginalArticle {
        ImageView iv_picture;
        View contentPicture;
        ImageView iv_headImg;
        TextView tvName;
        TextView tvTime;
        TextView tvTitle;//标题
        TextView tvContent;//内容
        TextView tvCommentNum;//评论人数
        TextView tvLikeNum;//喜欢的人数
        TextView tvAction;//动作
        ImageView ivHeart;//是否被关注的心型图标
        ImageView ivShare;//分享
        ImageView ivComment;//评论
        View contentHeadImg;//包裹头像的View
    }

    /**
     * 非原创：订阅圈子
     */
    private class UnOriginalSubscribeCircle {
        ImageView ivCirclePicBg;
        ImageView ivCirclePicture;
        TextView tvCircleName;
        TextView tvCircleContent;
        TextView tvCircleOperationNum;//动态数
        TextView tvCircleType;
        TextView tvCircleSubscribeNum;//订阅数
        ImageView ivHeadImg;
        TextView tvName;
        TextView tvTime;
        TextView tvAction;
        View contentHeadImg;//包裹头像的View

        View circleContent;//圈子包含内容
    }

    /**
     * 非原创——喜欢：普通动态
     */
    private class UnOriginalFavoriteNormal {
        //作者信息
        ImageView iv_headImg;
        TextView tvName;
        TextView tvTime;
        TextView tvAction;//动作
        View contentHeadImg;//包裹头像的View
        //原创作者信息
        TextView tvOriginalNickName;//原创作者名称
        //相关作品信息
        ImageView ivPic;
        TextView tvContent;//内容
        TextView tvCommentNum;//评论人数
        TextView tvLikeNum;//喜欢的人数

        ImageView ivHeart;//是否被关注的心型图标
        ImageView ivShare;//分享的图标
        View content;//动态内容
        ImageView ivComment;//评论
    }

    /**
     * 非原创——喜欢：话题动态
     */
    private class UnOriginalFavoriteTopic {
        //作者信息
        ImageView iv_headImg;
        TextView tvName;
        TextView tvTime;
        TextView tvAction;//动作
        View contentHeadImg;//包裹头像的View
        //原创作者信息
        TextView tvOriginalNickName;//原创作者名称
        //相关作品信息
        ImageView ivPic;
        TextView tvTopicTitle;
        TextView tvPartNum;//参与人数
        TextView tvContent;//内容
        TextView tvCommentNum;//评论人数
        TextView tvLikeNum;//喜欢的人数

        ImageView ivHeart;//是否被关注的心型图标
        ImageView ivShare;//分享的图标
        View content;//动态内容
        ImageView ivComment;//评论
    }

    /**
     * 非原创——喜欢：文章动态
     */
    private class UnOriginalFavoriteArticle {
        //作者信息
        ImageView iv_headImg;
        TextView tvName;
        TextView tvTime;
        TextView tvAction;//动作
        View contentHeadImg;//包裹头像的View
        //原创作者信息
        TextView tvOriginalNickName;//原创作者名称
        //相关作品信息
        ImageView ivPic;
        TextView tvArticleTitle;
        TextView tvContent;//内容
        TextView tvCommentNum;//评论人数
        TextView tvLikeNum;//喜欢的人数

        ImageView ivHeart;//是否被关注的心型图标
        ImageView ivShare;//分享的图标
        View content;//动态内容
        ImageView ivComment;//评论
    }

    /**
     * 圈子内动态：普通动态
     */
    private class UnOriginalCircleInCommonUseNormal {
        //作者信息
        ImageView iv_headImg;
        TextView tvName;
        TextView tvTime;
        TextView tvAction;//动作
        View contentHeadImg;//包裹头像的View
        //原创作者信息
        TextView tvOriginalNickName;//原创作者名称
        //相关作品信息
        ImageView ivPic;
        TextView tvContent;//内容
        TextView tvCommentNum;//评论人数
        TextView tvLikeNum;//喜欢的人数

        View content;//动态内容
        ImageView ivHeart;//喜欢的内容
        ImageView ivShare;//分享
        ImageView ivComment;//评论
    }

    /**
     * 圈子内动态：文章动态
     */
    private class UnOriginalCircleInCommonUseArticle {
        //作者信息
        ImageView iv_headImg;
        TextView tvName;
        TextView tvTime;
        TextView tvAction;//动作
        View contentHeadImg;//包裹头像的View
        //原创作者信息
        TextView tvOriginalNickName;//原创作者名称
        //相关作品信息
        ImageView ivPic;
        TextView tvArticleTitle;
        TextView tvContent;//内容
        TextView tvCommentNum;//评论人数
        TextView tvLikeNum;//喜欢的人数
        View content;//动态内容
        ImageView ivHeart;//喜欢的内容
        ImageView ivShare;//分享
        ImageView ivComment;//评论
    }

    /**
     * 圈子内动态：话题动态
     */
    private class UnOriginalCircleInCommonUseTopic {
        //作者信息
        ImageView iv_headImg;
        TextView tvName;
        TextView tvTime;
        TextView tvAction;//动作
        View contentHeadImg;//包裹头像的View
        //原创作者信息
        TextView tvOriginalNickName;//原创作者名称
        //相关作品信息
        ImageView ivPic;
        TextView tvTopicTitle;
        TextView tvPartNum;//参与人数
        TextView tvContent;//内容
        TextView tvCommentNum;//评论人数
        TextView tvLikeNum;//喜欢的人数
        View content;//动态内容
        ImageView ivHeart;//喜欢的内容
        ImageView ivShare;//分享
    }

    /**
     * 动态条目的点击监听
     */
    public interface OnDynamicItemClickListener {

        /**
         * 当主体被点击的时候
         */
        void onContentItemClick(int position);

        /**
         * 用户条目被点击的时候
         */
        void onHeadImgItemClick(int position);

        /**
         * 当作者条目被点击的时候
         */
        void onCreatorItemClick(int position);

        /**
         * 当分享条目被点击的时候
         */
        void onShareItemClick(int position);

        /**
         * 当评论条目被点击的时候
         */
        void onCommentItemClick(int position);

        /**
         * 当喜欢条目被点击的时候
         */
        void onLikeItemClick(int position);

        /**
         * 图片被点击的时候
         *
         * @param position        被点击的动态条目
         * @param picturePosition 被点击的图片
         */
        void onPictureClick(int position, int picturePosition);
    }

    public void setOnDynamicItemClickListener(OnDynamicItemClickListener onDynamicItemClickListener) {
        this.onDynamicItemClickListener = onDynamicItemClickListener;
    }

    @Override
    public void onClick(View view) {
        if (onDynamicItemClickListener == null) {
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
            case R.id.idfa_ll_content:
            case R.id.idfn_ll_content:
            case R.id.idft_ll_content:
            case R.id.idcn_ll_content:
            case R.id.idct_ll_content:
            case R.id.idca_ll_content:
            case R.id.idsc_circleContent:
                onDynamicItemClickListener.onContentItemClick(position);//主体
                break;
            case R.id.idfa_iv_comment:
            case R.id.idfn_iv_comment:
            case R.id.idft_iv_comment:
            case R.id.idca_iv_comment:
            case R.id.idcc_iv_comment:
            case R.id.idct_iv_comment:
            case R.id.idoa_iv_comment:
            case R.id.idon_iv_comment:
                onDynamicItemClickListener.onCommentItemClick(position);//评论
                break;
            case R.id.idfa_iv_share:
            case R.id.idfn_iv_share:
            case R.id.idft_iv_share:
            case R.id.idca_iv_share:
            case R.id.idcc_iv_share:
            case R.id.idct_iv_share:
            case R.id.idoa_iv_share:
            case R.id.idon_iv_share:
            case R.id.idot_iv_share:
                onDynamicItemClickListener.onShareItemClick(position);//分享
                break;
            case R.id.iidc_contentHeadImg:
            case R.id.idon_contentHeadImg:
            case R.id.idoa_contentHeadImg:
            case R.id.idot_contentHeadImg:
            case R.id.iidu_contentHeadImg:
            case R.id.ius_contentHeadImg:
                onDynamicItemClickListener.onHeadImgItemClick(position);//头像
                break;
            case R.id.idon_iv_heart:
            case R.id.idoa_iv_heart:
            case R.id.idot_iv_heart:
            case R.id.idfn_iv_heart:
            case R.id.idca_iv_heart:
            case R.id.idcn_iv_heart:
            case R.id.idct_iv_heart:
            case R.id.idfa_iv_heart:
            case R.id.idft_iv_heart:
                onDynamicItemClickListener.onLikeItemClick(position);//喜欢
                break;
            case R.id.idca_tv_originator_nickname:
            case R.id.idcn_tv_originator_nickname:
            case R.id.idct_tv_originator_nickname:
            case R.id.idfa_tv_originator_nickname:
            case R.id.idfn_tv_originator_nickname:
            case R.id.idft_tv_originator_nickname:
                onDynamicItemClickListener.onCreatorItemClick(position);
                break;
            //图片被点击
            case R.id.idca_iv_contentPic://文章图片被点击
            case R.id.idot_contentPicture://话题图片被点击
                onDynamicItemClickListener.onPictureClick(position, 0);
                break;
            case R.id.idon_contentPic1://普通动态
                onDynamicItemClickListener.onPictureClick(position, 0);
                break;
            case R.id.idon_contentPic2:
                onDynamicItemClickListener.onPictureClick(position, 1);
                break;
            case R.id.idon_contentPic3:
                onDynamicItemClickListener.onPictureClick(position, 2);
                break;
            case R.id.idon_contentPic4:
                onDynamicItemClickListener.onPictureClick(position, 3);
                break;
            case R.id.idon_contentPic5:
                onDynamicItemClickListener.onPictureClick(position, 4);
                break;
            case R.id.idon_contentPic6:
                onDynamicItemClickListener.onPictureClick(position, 5);
                break;
        }
    }
}
