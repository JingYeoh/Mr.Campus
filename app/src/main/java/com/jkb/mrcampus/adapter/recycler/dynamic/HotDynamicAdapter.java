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

import com.jkb.core.data.dynamic.hot.HotDynamic;
import com.jkb.core.data.dynamic.hot.circle.CircleDynamic;
import com.jkb.core.data.dynamic.hot.dynamic.circle.CircleArticleDynamic;
import com.jkb.core.data.dynamic.hot.dynamic.circle.CircleNormalDynamic;
import com.jkb.core.data.dynamic.hot.dynamic.circle.CircleTopicDynamic;
import com.jkb.core.data.dynamic.hot.dynamic.circle.DynamicInCircleDynamic;
import com.jkb.core.data.dynamic.hot.dynamic.original.OriginalArticleDynamic;
import com.jkb.core.data.dynamic.hot.dynamic.original.OriginalDynamic;
import com.jkb.core.data.dynamic.hot.dynamic.original.OriginalNormalDynamic;
import com.jkb.core.data.dynamic.hot.dynamic.original.OriginalTopicDynamic;
import com.jkb.core.data.dynamic.hot.user.UserDynamic;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 热门动态的适配器
 * Created by JustKiddingBaby on 2016/10/2.
 */

public class HotDynamicAdapter extends RecyclerView.Adapter<HotDynamicAdapter.ViewHolder> {

    private static final String TAG = "HotDynamicAdapter";
    private Context context;
    public List<HotDynamic> hotDynamics;

    //常量
    private static final int HOT_TYPE_USER = 1001;
    private static final int HOT_TYPE_CIRCLE = 1010;
    private static final int HOT_TYPE_DYNAMIC_NORMAL = 2001;
    private static final int HOT_TYPE_DYNAMIC_TOPIC = 2002;
    private static final int HOT_TYPE_DYNAMIC_ARTICLE = 2003;
    private static final int HOT_TYPE_DYNAMIC_IN_CIRCLE_NORMAL = 3001;
    private static final int HOT_TYPE_DYNAMIC_IN_CIRCLE_TOPIC = 3002;
    private static final int HOT_TYPE_DYNAMIC_IN_CIRCLE_ARTICLE = 3003;

    //监听器
    private OnItemClickListener onItemClickListener;
    private OnUserAttentionClickListener onUserAttentionClickListener;
    private OnCircleSubscribeClickListener onCircleSubscribeClickListener;
    private OnHeadImgClickListener onHeadImgClickListener;
    private OnCommentClickListener onCommentClickListener;
    private OnLikeClickListener onLikeClickListener;
    private OnCreatorUserClickListener onCreatorUserClickListener;
    private OnShareClickListener onShareClickListener;


    public HotDynamicAdapter(Context context, List<HotDynamic> hotDynamics) {
        this.context = context;
        if (hotDynamics == null) {
            hotDynamics = new ArrayList<>();
        }
        this.hotDynamics = hotDynamics;
    }

    @Override
    public int getItemViewType(int position) {
        HotDynamic hotDynamic = hotDynamics.get(position).getHotDynamic();
        int hotDynamicType = 0;
        //判断类型
        if (hotDynamic instanceof UserDynamic) {//用户类型
            hotDynamicType = HOT_TYPE_USER;
        } else if (hotDynamic instanceof CircleDynamic) {//圈子类型
            hotDynamicType = HOT_TYPE_CIRCLE;
        } else if (hotDynamic instanceof OriginalDynamic) {//动态类型
            hotDynamicType = judgeOriginalDynamicType(hotDynamic, hotDynamicType);
        } else if (hotDynamic instanceof DynamicInCircleDynamic) {//圈子中动态类型
            hotDynamicType = judgeDynamicInCircleDynamicType(hotDynamic, hotDynamicType);
        }
        return hotDynamicType;
    }

    /**
     * 判断圈子中动态类型
     */
    private int judgeDynamicInCircleDynamicType(HotDynamic hotDynamic, int hotDynamicType) {
        if (hotDynamic instanceof CircleNormalDynamic) {
            hotDynamicType = HOT_TYPE_DYNAMIC_IN_CIRCLE_NORMAL;
        } else if (hotDynamic instanceof CircleTopicDynamic) {
            hotDynamicType = HOT_TYPE_DYNAMIC_IN_CIRCLE_TOPIC;
        } else if (hotDynamic instanceof CircleArticleDynamic) {
            hotDynamicType = HOT_TYPE_DYNAMIC_IN_CIRCLE_ARTICLE;
        }
        return hotDynamicType;
    }

    /**
     * 判断动态类型
     */
    private int judgeOriginalDynamicType(HotDynamic hotDynamic, int hotDynamicType) {
        if (hotDynamic instanceof OriginalArticleDynamic) {//动态：文章
            hotDynamicType = HOT_TYPE_DYNAMIC_ARTICLE;
        } else if (hotDynamic instanceof OriginalNormalDynamic) {//动态：普通
            hotDynamicType = HOT_TYPE_DYNAMIC_NORMAL;
        } else if (hotDynamic instanceof OriginalTopicDynamic) {//动态：话题
            hotDynamicType = HOT_TYPE_DYNAMIC_TOPIC;
        }
        return hotDynamicType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case HOT_TYPE_USER://用户
                holder = initHotDynamic_User(parent, inflater);
                break;
            case HOT_TYPE_CIRCLE://圈子
                holder = initHotDynamic_Circle(parent, inflater);
                break;
            case HOT_TYPE_DYNAMIC_ARTICLE://文章动态
                holder = initHotDynamic_DynamicArticle(parent, inflater);
                break;
            case HOT_TYPE_DYNAMIC_NORMAL://普通动态
                holder = initHotDynamic_DynamicNormal(parent, inflater);
                break;
            case HOT_TYPE_DYNAMIC_TOPIC://话题动态
                holder = initHotDynamic_DynamicTopic(parent, inflater);
                break;
            case HOT_TYPE_DYNAMIC_IN_CIRCLE_ARTICLE:
                holder = initHotDynamic_InCircle_Article(parent, inflater);
                break;
            case HOT_TYPE_DYNAMIC_IN_CIRCLE_TOPIC:
                holder = initHotDynamic_InCircle_Topic(parent, inflater);
                break;
            case HOT_TYPE_DYNAMIC_IN_CIRCLE_NORMAL:
                holder = initHotDynamic_InCircle_Normal(parent, inflater);
                break;
            default:
                holder = initBlankDynamic(parent, inflater);
                break;
        }
        holder.viewType = viewType;
        return holder;
    }

    /**
     * 初始化空的动态
     */
    private ViewHolder initBlankDynamic(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_dynamic_blank, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化id
        holder.dynamicContent = view.findViewById(R.id.dynamic_content);
        return holder;
    }

    /**
     * 初始化热门动态：用户
     */
    private ViewHolder initHotDynamic_User(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_dynamic_original_user, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件id
        if (holder.user == null) {
            holder.user = new HotDynamic_User();
        }
        holder.dynamicContent = view.findViewById(R.id.dynamic_content);
        holder.user.ivUserBg = (ImageView) view.findViewById(R.id.idou_iv_userBg);
        holder.user.ivUserHeadImg = (ImageView) view.findViewById(R.id.idou_iv_userHeadImg);
        holder.user.tvAttentionCount = (TextView) view.findViewById(R.id.idou_tv_attentionCount);
        holder.user.tvFansCount = (TextView) view.findViewById(R.id.idou_tv_fansCount);
        holder.user.tvVisitorCount = (TextView) view.findViewById(R.id.idou_tv_visitorCount);
        holder.user.tvPayAttention = (TextView) view.findViewById(R.id.idou_tv_patAttention);
        holder.user.tvUserName = (TextView) view.findViewById(R.id.idou_tv_userName);
        holder.user.tvUserBref = (TextView) view.findViewById(R.id.idou_tv_userBref);
        //初始化点击事件的监听
        holder.dynamicContent.setOnClickListener(clickItemListener);
        holder.user.tvPayAttention.setOnClickListener(clickUserAttentionListener);
        return holder;
    }

    /**
     * 初始化热门动态：圈子
     */
    private ViewHolder initHotDynamic_Circle(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_dynamic_original_circle, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件的id
        if (holder.circle == null) {
            holder.circle = new HotDynamic_Circle();
        }
        holder.dynamicContent = view.findViewById(R.id.dynamic_content);
        holder.circle.ivCircleBg = (ImageView) view.findViewById(R.id.idoc_iv_circleBg);
        holder.circle.ivCirclePic = (ImageView) view.findViewById(R.id.idoc_iv_circlePic);
        holder.circle.tvCircleType = (TextView) view.findViewById(R.id.idoc_tv_circleType);
        holder.circle.tvDynamicsCount = (TextView) view.findViewById(R.id.idoc_tv_dynamicsCount);
        holder.circle.tvAttentionCount = (TextView) view.findViewById(R.id.idoc_tv_operationCount);
        holder.circle.tvCircleName = (TextView) view.findViewById(R.id.idoc_tv_circleName);
        holder.circle.tvCircleBref = (TextView) view.findViewById(R.id.idoc_tv_circleBref);
        holder.circle.ivSubscribe = (ImageView) view.findViewById(R.id.idoc_iv_subscribe);
        //初始化点击事件的监听
        holder.dynamicContent.setOnClickListener(clickItemListener);
        holder.circle.ivSubscribe.setOnClickListener(clickCircleSubscribeListener);
        return holder;
    }

    /**
     * 初始化热门动态：文章动态
     */
    private ViewHolder initHotDynamic_DynamicArticle(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_dynamic_orginal_article, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件id
        if (holder.dynamicArticle == null) {
            holder.dynamicArticle = new HotDynamic_DynamicArticle();
        }
        holder.dynamicContent = view.findViewById(R.id.dynamic_content);
        holder.dynamicArticle.iv_headImg = (ImageView) view.findViewById(R.id.idoa_iv_headImg);
        holder.dynamicArticle.iv_picture = (ImageView) view.findViewById(R.id.idoa_iv_pic);
        holder.dynamicArticle.tvName = (TextView) view.findViewById(R.id.idoa_tv_name);
        holder.dynamicArticle.tvTime = (TextView) view.findViewById(R.id.idoa_tv_time);
        holder.dynamicArticle.tvTitle = (TextView) view.findViewById(R.id.idoa_tv_title);
        holder.dynamicArticle.tvContent = (TextView) view.findViewById(R.id.idoa_tv_content);
        holder.dynamicArticle.tvLikeNum = (TextView) view.findViewById(R.id.idoa_tv_likeNum);
        holder.dynamicArticle.tvCommentNum = (TextView) view.findViewById(R.id.idoa_tv_commentNum);
        holder.dynamicArticle.tvAction = (TextView) view.findViewById(R.id.idoa_tv_action);
        holder.dynamicArticle.ivHeart =
                (ImageView) view.findViewById(R.id.idoa_iv_heart);
        holder.dynamicArticle.ivShare = (ImageView) view.findViewById(R.id.idoa_iv_share);
        holder.dynamicArticle.ivComment = (ImageView) view.findViewById(R.id.idoa_iv_comment);
        holder.dynamicArticle.contentHeadImg = view.findViewById(R.id.idoa_contentHeadImg);
        //初始化点击事件的监听
        holder.dynamicContent.setOnClickListener(clickItemListener);
        holder.dynamicArticle.contentHeadImg.setOnClickListener(clickHeadImgListener);
        holder.dynamicArticle.ivComment.setOnClickListener(clickCommentListener);
        holder.dynamicArticle.ivHeart.setOnClickListener(clickLikeListener);
        holder.dynamicArticle.ivShare.setOnClickListener(clickShareListener);
        return holder;
    }

    /**
     * 初始化热门动态：普通动态
     */
    private ViewHolder initHotDynamic_DynamicNormal(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_dynamic_orginal_normal, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件的id
        if (holder.dynamicNormal == null) {
            holder.dynamicNormal = new HotDynamic_DynamicNormal();
        }
        holder.dynamicContent = view.findViewById(R.id.dynamic_content);
        //初始化图片相关
        holder.dynamicNormal.pictures = view.findViewById(R.id.idon_ll_pictures);
        holder.dynamicNormal.pic1To3 = view.findViewById(R.id.idon_ll_pic1To3);
        holder.dynamicNormal.pic2To3 = view.findViewById(R.id.idon_ll_pic2To3);
        holder.dynamicNormal.pic4To6 = view.findViewById(R.id.idon_ll_pic4To6);
        holder.dynamicNormal.ivPic1 = (ImageView) view.findViewById(R.id.idon_iv_pic1);
        holder.dynamicNormal.ivPic2 = (ImageView) view.findViewById(R.id.idon_iv_pic2);
        holder.dynamicNormal.ivPic3 = (ImageView) view.findViewById(R.id.idon_iv_pic3);
        holder.dynamicNormal.ivPic4 = (ImageView) view.findViewById(R.id.idon_iv_pic4);
        holder.dynamicNormal.ivPic5 = (ImageView) view.findViewById(R.id.idon_iv_pic5);
        holder.dynamicNormal.ivPic6 = (ImageView) view.findViewById(R.id.idon_iv_pic6);
        //初始化其它组件
        holder.dynamicNormal.tvName = (TextView) view.findViewById(R.id.idon_tv_name);
        holder.dynamicNormal.tvTime = (TextView) view.findViewById(R.id.idon_tv_time);
        holder.dynamicNormal.ivHeadImg = (ImageView) view.findViewById(R.id.idon_iv_headImg);
        holder.dynamicNormal.tvContent = (TextView) view.findViewById(R.id.idon_tv_content);
        holder.dynamicNormal.tvCommentNum = (TextView) view.findViewById(R.id.idon_tv_commentNum);
        holder.dynamicNormal.tvLikeNum = (TextView) view.findViewById(R.id.idon_tv_likeNum);
        holder.dynamicNormal.tvAction = (TextView) view.findViewById(R.id.idon_tv_action);
        holder.dynamicNormal.ivHeart =
                (ImageView) view.findViewById(R.id.idon_iv_heart);
        holder.dynamicNormal.ivShare = (ImageView) view.findViewById(R.id.idon_iv_share);
        holder.dynamicNormal.ivComment = (ImageView) view.findViewById(R.id.idon_iv_comment);
        holder.dynamicNormal.contentHeadImg = view.findViewById(R.id.idon_contentHeadImg);

        //设置监听事件
        holder.dynamicNormal.contentHeadImg.setOnClickListener(clickHeadImgListener);
        holder.dynamicNormal.ivComment.setOnClickListener(clickCommentListener);
        holder.dynamicNormal.ivHeart.setOnClickListener(clickLikeListener);
        holder.dynamicNormal.ivShare.setOnClickListener(clickShareListener);
        return holder;
    }

    /**
     * 初始化热门动态：话题动态
     */
    private ViewHolder initHotDynamic_DynamicTopic(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_dynamic_original_topic, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化控件的id
        if (holder.dynamicTopic == null) {
            holder.dynamicTopic = new HotDynamic_DynamicTopic();
        }
        holder.dynamicContent = view.findViewById(R.id.dynamic_content);
        holder.dynamicTopic.iv_headImg = (ImageView) view.findViewById(R.id.idot_iv_headImg);
        holder.dynamicTopic.iv_picture = (ImageView) view.findViewById(R.id.idot_iv_pic);
        holder.dynamicTopic.tvName = (TextView) view.findViewById(R.id.idot_tv_name);
        holder.dynamicTopic.tvTime = (TextView) view.findViewById(R.id.idot_tv_time);
        holder.dynamicTopic.tvTitle = (TextView) view.findViewById(R.id.idot_tv_title);
        holder.dynamicTopic.tvContent = (TextView) view.findViewById(R.id.idot_tv_content);
        holder.dynamicTopic.tvPartNum = (TextView) view.findViewById(R.id.idot_tv_partInNum);
        holder.dynamicTopic.tvLikeNum = (TextView) view.findViewById(R.id.idot_tv_likeNum);
        holder.dynamicTopic.tvAction = (TextView) view.findViewById(R.id.idot_tv_action);
        holder.dynamicTopic.ivHeart =
                (ImageView) view.findViewById(R.id.idot_iv_heart);
        holder.dynamicTopic.ivShare = (ImageView) view.findViewById(R.id.idot_iv_share);
        holder.dynamicTopic.contentHeadImg = view.findViewById(R.id.idot_contentHeadImg);
        //初始化监听器
        holder.dynamicContent.setOnClickListener(clickItemListener);
        holder.dynamicTopic.contentHeadImg.setOnClickListener(clickHeadImgListener);
        holder.dynamicTopic.ivHeart.setOnClickListener(clickLikeListener);
        holder.dynamicTopic.ivShare.setOnClickListener(clickShareListener);
        return holder;
    }

    /**
     * 初始化圈子中动态：普通
     */
    private ViewHolder initHotDynamic_InCircle_Normal(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_dynamic_circleincommonuse_normal, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //初始化id
        if (holder.dynamicInCircleNormal == null) {
            holder.dynamicInCircleNormal = new DynamicInCircleNormal();
        }
        holder.dynamicContent = view.findViewById(R.id.dynamic_content);
        //初始化作者信息
        holder.dynamicInCircleNormal.iv_headImg = (ImageView) view.findViewById(R.id.iidc_iv_headImg);
        holder.dynamicInCircleNormal.tvName = (TextView) view.findViewById(R.id.iidc_tv_name);
        holder.dynamicInCircleNormal.tvTime = (TextView) view.findViewById(R.id.iidc_tv_time);
        holder.dynamicInCircleNormal.tvAction = (TextView) view.findViewById(R.id.iidc_tv_action);
        //原创作者信息
        holder.dynamicInCircleNormal.tvOriginalNickName =
                (TextView) view.findViewById(R.id.idcn_tv_originator_nickname);
        //作品信息
        holder.dynamicInCircleNormal.ivPic =
                (ImageView) view.findViewById(R.id.idcn_iv_contentPic);
        holder.dynamicInCircleNormal.tvContent =
                (TextView) view.findViewById(R.id.idcn_tv_contentText);
        holder.dynamicInCircleNormal.tvLikeNum =
                (TextView) view.findViewById(R.id.idcn_tv_likeNum);
        holder.dynamicInCircleNormal.tvCommentNum =
                (TextView) view.findViewById(R.id.idcn_tv_commentNum);
        holder.dynamicInCircleNormal.ivHeart =
                (ImageView) view.findViewById(R.id.idcn_iv_heart);
        holder.dynamicInCircleNormal.ivShare = (ImageView) view.findViewById(R.id.idcc_iv_share);
        holder.dynamicInCircleNormal.ivComment = (ImageView) view.findViewById(R.id.idcc_iv_comment);
        holder.dynamicInCircleNormal.content = view.findViewById(R.id.idcn_ll_content);
        holder.dynamicInCircleNormal.contentHeadImg = view.findViewById(R.id.iidc_contentHeadImg);
        //初始化监听事件
        holder.dynamicInCircleNormal.content.setOnClickListener(clickItemListener);
        holder.dynamicInCircleNormal.contentHeadImg.setOnClickListener(clickHeadImgListener);
        holder.dynamicInCircleNormal.ivComment.setOnClickListener(clickCommentListener);
        holder.dynamicInCircleNormal.ivHeart.setOnClickListener(clickLikeListener);
        holder.dynamicInCircleNormal.tvOriginalNickName.setOnClickListener(clickCreatorUserListener);
        holder.dynamicInCircleNormal.ivShare.setOnClickListener(clickShareListener);
        return holder;
    }

    /**
     * 初始化圈子中动态：话题
     */
    private ViewHolder initHotDynamic_InCircle_Topic(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_dynamic_circleincommonuse_topic, parent, false);
        ViewHolder holder = new ViewHolder(view);
        if (holder.dynamicInCircleTopic == null) {
            holder.dynamicInCircleTopic = new DynamicInCircleTopic();
        }
        holder.dynamicContent = view.findViewById(R.id.dynamic_content);
        //初始化作者信息
        holder.dynamicInCircleTopic.iv_headImg = (ImageView) view.findViewById(R.id.iidc_iv_headImg);
        holder.dynamicInCircleTopic.tvName = (TextView) view.findViewById(R.id.iidc_tv_name);
        holder.dynamicInCircleTopic.tvTime = (TextView) view.findViewById(R.id.iidc_tv_time);
        holder.dynamicInCircleTopic.tvAction = (TextView) view.findViewById(R.id.iidc_tv_action);
        //原创作者信息
        holder.dynamicInCircleTopic.tvOriginalNickName =
                (TextView) view.findViewById(R.id.idct_tv_originator_nickname);
        //作品信息
        holder.dynamicInCircleTopic.tvTopicTitle =
                (TextView) view.findViewById(R.id.idct_tv_topicName);//话题名称
        holder.dynamicInCircleTopic.tvPartNum =
                (TextView) view.findViewById(R.id.idct_tv_partInNum);//参与人数
        holder.dynamicInCircleTopic.ivPic =
                (ImageView) view.findViewById(R.id.idct_iv_contentPic);
        holder.dynamicInCircleTopic.tvContent =
                (TextView) view.findViewById(R.id.idct_tv_contentText);
        holder.dynamicInCircleTopic.tvLikeNum =
                (TextView) view.findViewById(R.id.idct_tv_likeNum);
        holder.dynamicInCircleTopic.tvCommentNum =
                (TextView) view.findViewById(R.id.idct_tv_commentNum);
        holder.dynamicInCircleTopic.content = view.findViewById(R.id.idct_ll_content);
        holder.dynamicInCircleTopic.ivHeart = (ImageView) view.findViewById(R.id.idct_iv_heart);
        holder.dynamicInCircleTopic.ivShare = (ImageView) view.findViewById(R.id.idct_iv_share);
        holder.dynamicInCircleTopic.contentHeadImg = view.findViewById(R.id.iidc_contentHeadImg);

        //设置监听事件
        holder.dynamicInCircleTopic.content.setOnClickListener(clickItemListener);
        holder.dynamicInCircleTopic.contentHeadImg.setOnClickListener(clickHeadImgListener);
        holder.dynamicInCircleTopic.ivHeart.setOnClickListener(clickLikeListener);
        holder.dynamicInCircleTopic.tvOriginalNickName.setOnClickListener(clickCreatorUserListener);
        holder.dynamicInCircleTopic.ivShare.setOnClickListener(clickShareListener);
        return holder;
    }

    /**
     * 初始化圈子中动态：文章
     */
    private ViewHolder initHotDynamic_InCircle_Article(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_dynamic_circleincommonuse_article, parent, false);
        ViewHolder holder = new ViewHolder(view);
        if (holder.dynamicInCircleArticle == null) {
            holder.dynamicInCircleArticle = new DynamicInCircleArticle();
        }
        holder.dynamicContent = view.findViewById(R.id.dynamic_content);
        //初始化作者信息
        holder.dynamicInCircleArticle.iv_headImg = (ImageView) view.findViewById(R.id.iidc_iv_headImg);
        holder.dynamicInCircleArticle.tvName = (TextView) view.findViewById(R.id.iidc_tv_name);
        holder.dynamicInCircleArticle.tvTime = (TextView) view.findViewById(R.id.iidc_tv_time);
        holder.dynamicInCircleArticle.tvAction = (TextView) view.findViewById(R.id.iidc_tv_action);
        //原创作者信息
        holder.dynamicInCircleArticle.tvOriginalNickName =
                (TextView) view.findViewById(R.id.idca_tv_originator_nickname);
        //作品信息
        holder.dynamicInCircleArticle.tvArticleTitle =
                (TextView) view.findViewById(R.id.idca_tv_articleName);//话题名称
        holder.dynamicInCircleArticle.ivPic =
                (ImageView) view.findViewById(R.id.idca_iv_contentPic);
        holder.dynamicInCircleArticle.tvContent =
                (TextView) view.findViewById(R.id.idca_tv_contentText);
        holder.dynamicInCircleArticle.tvLikeNum =
                (TextView) view.findViewById(R.id.idca_tv_likeNum);
        holder.dynamicInCircleArticle.tvCommentNum =
                (TextView) view.findViewById(R.id.idca_tv_commentNum);
        holder.dynamicInCircleArticle.content = view.findViewById(R.id.idca_ll_content);
        holder.dynamicInCircleArticle.ivHeart = (ImageView) view.findViewById(R.id.idca_iv_heart);
        holder.dynamicInCircleArticle.ivShare = (ImageView) view.findViewById(R.id.idca_iv_share);
        holder.dynamicInCircleArticle.ivComment = (ImageView) view.findViewById(R.id.idca_iv_comment);
        holder.dynamicInCircleArticle.contentHeadImg = view.findViewById(R.id.iidc_contentHeadImg);

        //设置监听事件
        holder.dynamicInCircleArticle.content.setOnClickListener(clickItemListener);
        holder.dynamicInCircleArticle.contentHeadImg.setOnClickListener(clickHeadImgListener);
        holder.dynamicInCircleArticle.ivComment.setOnClickListener(clickCommentListener);
        holder.dynamicInCircleArticle.ivHeart.setOnClickListener(clickLikeListener);
        holder.dynamicInCircleArticle.tvOriginalNickName.setOnClickListener(clickCreatorUserListener);
        holder.dynamicInCircleArticle.ivShare.setOnClickListener(clickShareListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = holder.viewType;
        //绑定数据
        switch (viewType) {
            case HOT_TYPE_USER://用户
                bindUserData(holder, position);
                break;
            case HOT_TYPE_CIRCLE://圈子
                bindCircleData(holder, position);
                break;
            case HOT_TYPE_DYNAMIC_ARTICLE://文章动态
                bindDynamicArticleData(holder, position);
                break;
            case HOT_TYPE_DYNAMIC_NORMAL://普通动态
                bindDynamicNormalData(holder, position);
                break;
            case HOT_TYPE_DYNAMIC_TOPIC://话题动态
                bindDynamicTopicData(holder, position);
                break;
            case HOT_TYPE_DYNAMIC_IN_CIRCLE_ARTICLE://圈子动态：文章
                bindDynamicInCircleArticleData(holder, position);
                break;
            case HOT_TYPE_DYNAMIC_IN_CIRCLE_TOPIC://圈子动态：话题
                bindDynamicInCircleTopicData(holder, position);
                break;
            case HOT_TYPE_DYNAMIC_IN_CIRCLE_NORMAL://圈子动态：普通
                bindDynamicInCircleNormalData(holder, position);
                break;
            default:
                bindBlankDynamic(holder, position);
                break;
        }
    }


    /**
     * 处理空的动态的数据
     */
    private void bindBlankDynamic(ViewHolder holder, int position) {
        holder.dynamicContent.setVisibility(View.GONE);
    }

    /**
     * 绑定热门动态用户的数据
     */
    private void bindUserData(ViewHolder holder, int position) {
        UserDynamic userDynamic = (UserDynamic) hotDynamics.get(position);
        if (userDynamic == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.user.tvPayAttention);

        holder.user.tvUserName.setText(userDynamic.getNickname());
        holder.user.tvUserBref.setText(userDynamic.getBref_introduction());
        //设置是否关注
        if (!userDynamic.isHas_attention()) {
            holder.user.tvPayAttention.setBackgroundResource(
                    R.drawable.bg_edittext_maintheme_white_round_content);
            holder.user.tvPayAttention.setText("关注");
        } else {
            holder.user.tvPayAttention.setBackgroundResource(
                    R.drawable.bg_edittext_mainthemegravy_white_round_content);
            holder.user.tvPayAttention.setText("已关注");
        }
        //设置用户关注/粉丝/访客数目
        holder.user.tvAttentionCount.setText(userDynamic.getCount_of_fan() + "");
        holder.user.tvFansCount.setText(userDynamic.getCount_of_fan() + "");
        holder.user.tvVisitorCount.setText(userDynamic.getCount_of_visitor() + "");
        //设置头像
        String avatar = userDynamic.getAvatar();
        if (StringUtils.isEmpty(avatar)) {
            holder.user.ivUserBg.setImageResource(R.color.default_picture);
            holder.user.ivUserHeadImg.setImageResource(R.color.default_picture);
        } else {
            loadImageViewAndUrl(holder.user.ivUserHeadImg, avatar);
            loadBlurImageViewAndUrl(holder.user.ivUserBg, avatar, 25, 5);
        }
    }

    /**
     * 绑定热门动态圈子的数据
     */
    private void bindCircleData(ViewHolder holder, int position) {
        CircleDynamic circleDynamic = (CircleDynamic) hotDynamics.get(position);
        if (circleDynamic == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }

        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.circle.ivSubscribe);

        holder.circle.tvCircleName.setText(circleDynamic.getCircle_name());
        holder.circle.tvCircleBref.setText(circleDynamic.getCircle_introduction());
        //设置是否订阅
        if (circleDynamic.isHas_subscribe()) {
            holder.circle.ivSubscribe.setImageResource(R.drawable.ic_subscription_already_essays);
        } else {
            holder.circle.ivSubscribe.setImageResource(R.drawable.ic_subscription_essays);
        }
        //设置类型/动态数/关注量
        holder.circle.tvCircleType.setText(circleDynamic.getCircle_type());
        holder.circle.tvDynamicsCount.setText(circleDynamic.getCount_of_dynamic() + "");
        holder.circle.tvAttentionCount.setText(circleDynamic.getCount_of_subscription() + "");
        //设置图片
        String picture = circleDynamic.getCircle_picture();
        if (StringUtils.isEmpty(picture)) {
            holder.circle.ivCirclePic.setImageResource(R.color.default_picture);
            holder.circle.ivCircleBg.setImageResource(R.color.default_picture);
        } else {
            loadImageViewAndUrl(holder.circle.ivCirclePic, picture);
            loadBlurImageViewAndUrl(holder.circle.ivCircleBg, picture, 25, 5);
        }
    }

    /**
     * 绑定热门文章动态的数据
     */
    private void bindDynamicArticleData(ViewHolder holder, int position) {
        OriginalArticleDynamic articleDynamic = (OriginalArticleDynamic) hotDynamics.get(position);
        if (articleDynamic == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }

        //绑定Tag
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.dynamicArticle.contentHeadImg,
                holder.dynamicArticle.ivComment,
                holder.dynamicArticle.ivShare,
                holder.dynamicArticle.ivHeart);

        holder.dynamicArticle.tvTime.setText(articleDynamic.getDynamic_created_at());
        holder.dynamicArticle.tvAction.setText("发布了一篇文章");
        //解析用户数据
        UserInfo user = articleDynamic.getUser();
        if (user == null) {
            holder.dynamicArticle.iv_headImg.setVisibility(View.GONE);
            holder.dynamicArticle.tvName.setVisibility(View.GONE);
        } else {
            holder.dynamicArticle.tvName.setText(user.getNickName());
            String avatar = user.getAvatar();
            if (StringUtils.isEmpty(avatar)) {
                holder.dynamicArticle.iv_headImg.setImageResource(R.drawable.ic_user_head);
            } else {
                loadImageViewAndUrl(holder.dynamicArticle.iv_headImg, avatar);
            }
        }
        //文章数据
        List<OriginalArticleDynamic.ArticleContent> articleContents =
                articleDynamic.getArticleContents();
        if (articleContents == null || articleContents.size() <= 0) {
            holder.dynamicContent.setVisibility(View.GONE);
        } else {
            OriginalArticleDynamic.ArticleContent articleContent = articleContents.get(0);
            holder.dynamicArticle.tvTitle.setText(articleDynamic.getTitle());
            holder.dynamicArticle.tvContent.setText(articleContent.getDoc());
            String img = articleContent.getImg();
            if (StringUtils.isEmpty(img)) {
                holder.dynamicArticle.iv_picture.setVisibility(View.GONE);
            } else {
                loadImageViewAndUrl(holder.dynamicArticle.iv_picture, img);
            }
        }
        //设置是否喜欢
        if (articleDynamic.isHas_favorite()) {
            holder.dynamicArticle.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.dynamicArticle.ivHeart.setImageResource(R.drawable.ic_heart_gray);
        }
        //设置喜欢/评论数
        holder.dynamicArticle.tvCommentNum.setText(articleDynamic.getCount_of_comment() + "");
        holder.dynamicArticle.tvLikeNum.setText(articleDynamic.getCount_of_favorite() + "");
    }

    /**
     * 绑定热门普通动态的数据
     */
    private void bindDynamicNormalData(ViewHolder holder, int position) {
        OriginalNormalDynamic normalDynamic = (OriginalNormalDynamic) hotDynamics.get(position);
    }

    /**
     * 绑定热门话题动态的数据
     */
    private void bindDynamicTopicData(ViewHolder holder, int position) {
        OriginalTopicDynamic topicDynamic = (OriginalTopicDynamic) hotDynamics.get(position);
        if (topicDynamic == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }

        //绑定Tag
        ClassUtils.bindViewsTag(position,
                holder.dynamicContent,
                holder.dynamicTopic.contentHeadImg,
                holder.dynamicTopic.ivShare,
                holder.dynamicTopic.ivHeart);

        holder.dynamicTopic.tvTime.setText(topicDynamic.getDynamic_created_at());
        holder.dynamicTopic.tvAction.setText("发布了一个话题");
        //解析用户数据
        UserInfo user = topicDynamic.getUser();
        if (user == null) {
            holder.dynamicTopic.iv_headImg.setVisibility(View.GONE);
            holder.dynamicTopic.tvName.setVisibility(View.GONE);
        } else {
            holder.dynamicTopic.tvName.setText(user.getNickName());
            String avatar = user.getAvatar();
            if (StringUtils.isEmpty(avatar)) {
                holder.dynamicTopic.iv_headImg.setImageResource(R.drawable.ic_user_head);
            } else {
                loadImageViewAndUrl(holder.dynamicTopic.iv_headImg, avatar);
            }
        }
        //解析话题数据
        OriginalTopicDynamic.TopicContent topicContent = topicDynamic.getTopicContent();
        if (topicContent == null) {
            holder.dynamicContent.setVisibility(View.GONE);
        } else {
            holder.dynamicTopic.tvTitle.setText(topicDynamic.getTitle());
            holder.dynamicTopic.tvContent.setText(topicContent.getDoc());
            String img = topicContent.getImg();
            if (StringUtils.isEmpty(img)) {
                holder.dynamicTopic.iv_picture.setVisibility(View.GONE);
            } else {
                holder.dynamicTopic.iv_picture.setVisibility(View.VISIBLE);
                loadImageViewAndUrl(holder.dynamicTopic.iv_picture, img);
            }
        }
        //设置是否喜欢
        if (topicDynamic.isHas_favorite()) {
            holder.dynamicTopic.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.dynamicTopic.ivHeart.setImageResource(R.drawable.ic_heart_gray);
        }
        //设置喜欢/参与/评论数
        holder.dynamicTopic.tvLikeNum.setText(topicDynamic.getCount_of_favorite() + "");
        holder.dynamicTopic.tvPartNum.setText(topicDynamic.getCount_of_participation() + "");
    }

    /**
     * 绑定圈子内动态：文章
     */
    private void bindDynamicInCircleArticleData(ViewHolder holder, int position) {
        CircleArticleDynamic circleArticleDynamic = (CircleArticleDynamic) hotDynamics.get(position);
        if (circleArticleDynamic == null) {
            Log.d(TAG, "文章解析数据为空");
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }

        holder.dynamicContent.setVisibility(View.VISIBLE);
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicInCircleArticle.content,
                holder.dynamicInCircleArticle.contentHeadImg,
                holder.dynamicInCircleArticle.ivComment,
                holder.dynamicInCircleArticle.ivShare,
                holder.dynamicInCircleArticle.tvOriginalNickName,
                holder.dynamicInCircleArticle.ivHeart);

        holder.dynamicInCircleArticle.tvTime.setText(circleArticleDynamic.getDynamic_created_at());
        holder.dynamicInCircleArticle.tvAction.setText("发布了一篇文章");
        //解析圈子数据
        CircleInfo circle = circleArticleDynamic.getCircle();
        if (circle == null) {
            Log.d(TAG, "圈子数据为空");
            holder.dynamicInCircleArticle.iv_headImg.setVisibility(View.GONE);
            holder.dynamicInCircleArticle.tvName.setVisibility(View.GONE);
        } else {
            holder.dynamicInCircleArticle.tvName.setText(circle.getCircleName());
            String picture = circle.getPictureUrl();
            if (StringUtils.isEmpty(picture)) {
                holder.dynamicInCircleArticle.iv_headImg.setImageResource(R.color.default_picture);
            } else {
                loadImageViewAndUrl(holder.dynamicInCircleArticle.iv_headImg, picture);
            }
        }
        //设置用户数据
        UserInfo user = circleArticleDynamic.getUser();
        if (user == null) {
            Log.d(TAG, "用户数据为空");
            holder.dynamicContent.setVisibility(View.GONE);
        } else {
            holder.dynamicInCircleArticle.tvOriginalNickName.setText(user.getNickName());
        }
        //文章数据
        List<CircleArticleDynamic.ArticleContent> articleContents =
                circleArticleDynamic.getArticleContents();
        if (articleContents == null || articleContents.size() <= 0) {
            Log.d(TAG, "文章数据为空");
            holder.dynamicContent.setVisibility(View.GONE);
        } else {
            CircleArticleDynamic.ArticleContent articleContent = articleContents.get(0);
            holder.dynamicInCircleArticle.tvArticleTitle.setText(circleArticleDynamic.getTitle());
            holder.dynamicInCircleArticle.tvContent.setText(articleContent.getDoc());
            String img = articleContent.getImg();
            if (StringUtils.isEmpty(img)) {
                holder.dynamicInCircleArticle.ivPic.setVisibility(View.GONE);
            } else {
                loadImageViewAndUrl(holder.dynamicInCircleArticle.ivPic, img);
            }
        }
        //设置是否喜欢
        if (circleArticleDynamic.isHas_favorite()) {
            holder.dynamicInCircleArticle.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.dynamicInCircleArticle.ivHeart.setImageResource(R.drawable.ic_heart_gray);
        }
        //设置喜欢/评论数
        holder.dynamicInCircleArticle.tvCommentNum.setText(circleArticleDynamic.getCount_of_comment() + "");
        holder.dynamicInCircleArticle.tvLikeNum.setText(circleArticleDynamic.getCount_of_favorite() + "");
    }

    /**
     * 绑定圈子内动态：话题
     */
    private void bindDynamicInCircleTopicData(ViewHolder holder, int position) {
        CircleTopicDynamic circleTopicDynamic = (CircleTopicDynamic) hotDynamics.get(position);
        if (circleTopicDynamic == null) {
            holder.dynamicContent.setVisibility(View.GONE);
            return;
        }

        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.dynamicInCircleTopic.content,
                holder.dynamicInCircleTopic.contentHeadImg,
                holder.dynamicInCircleTopic.ivShare,
                holder.dynamicInCircleTopic.tvOriginalNickName,
                holder.dynamicInCircleTopic.ivHeart);

        holder.dynamicInCircleTopic.tvTime.setText(circleTopicDynamic.getDynamic_created_at());
        holder.dynamicInCircleTopic.tvAction.setText("发布了一个话题");
        //解析圈子数据
        CircleInfo circle = circleTopicDynamic.getCircle();
        if (circle == null) {
            holder.dynamicInCircleTopic.iv_headImg.setVisibility(View.GONE);
            holder.dynamicInCircleTopic.tvName.setVisibility(View.GONE);
        } else {
            holder.dynamicInCircleTopic.tvName.setText(circle.getCircleName());
            String picture = circle.getPictureUrl();
            if (StringUtils.isEmpty(picture)) {
                holder.dynamicInCircleTopic.iv_headImg.setImageResource(R.color.default_picture);
            } else {
                loadImageViewAndUrl(holder.dynamicInCircleTopic.iv_headImg, picture);
            }
        }
        //设置用户数据
        UserInfo user = circleTopicDynamic.getUser();
        if (user == null) {
            holder.dynamicContent.setVisibility(View.GONE);
        } else {
            holder.dynamicInCircleTopic.tvOriginalNickName.setText(user.getNickName());
        }
        //解析话题数据
        CircleTopicDynamic.TopicContent topicContent = circleTopicDynamic.getTopicContent();
        if (topicContent == null) {
            holder.dynamicContent.setVisibility(View.GONE);
        } else {
            holder.dynamicInCircleTopic.tvTopicTitle.setText(circleTopicDynamic.getTitle());
            holder.dynamicInCircleTopic.tvContent.setText(topicContent.getDoc());
            String img = topicContent.getImg();
            if (StringUtils.isEmpty(img)) {
                holder.dynamicInCircleTopic.ivPic.setVisibility(View.GONE);
            } else {
                loadImageViewAndUrl(holder.dynamicInCircleTopic.ivPic, img);
            }
        }
        //设置是否喜欢
        if (circleTopicDynamic.isHas_favorite()) {
            holder.dynamicInCircleTopic.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.dynamicInCircleTopic.ivHeart.setImageResource(R.drawable.ic_heart_gray);
        }
        //设置喜欢/参与/评论数
        holder.dynamicInCircleTopic.tvLikeNum.setText(circleTopicDynamic.getCount_of_favorite() + "");
        holder.dynamicInCircleTopic.tvPartNum.setText(circleTopicDynamic.getCount_of_participation() + "");
    }

    /**
     * 绑定圈子内动态：普通
     */
    private void bindDynamicInCircleNormalData(ViewHolder holder, int position) {

    }

    /**
     * 绑定图片到网址上
     */
    private void loadImageViewAndUrl(ImageView iv_headImg, String headImgUrl) {
        ImageLoaderFactory.getInstance().displayImage(iv_headImg, headImgUrl);
    }

    /**
     * 绑定图片到网址上并设置高斯模糊效果
     */
    private void loadBlurImageViewAndUrl(
            ImageView iv_headImg, String headImgUrl, int radius, int downSampling) {
        ImageLoaderFactory.getInstance().displayBlurImage(iv_headImg, headImgUrl, radius,
                downSampling);
    }

    @Override
    public int getItemCount() {
        return hotDynamics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        int viewType;//视图类型
        View dynamicContent;
        HotDynamic_User user;//用户
        HotDynamic_Circle circle;//圈子
        HotDynamic_DynamicArticle dynamicArticle;//文章动态
        HotDynamic_DynamicNormal dynamicNormal;//普通动态
        HotDynamic_DynamicTopic dynamicTopic;//话题动态
        DynamicInCircleNormal dynamicInCircleNormal;//圈子内动态：普通
        DynamicInCircleTopic dynamicInCircleTopic;//圈子内动态：话题
        DynamicInCircleArticle dynamicInCircleArticle;//圈子内动态：文章
    }

    /**
     * 用户用到的控件
     */
    private class HotDynamic_User {
        ImageView ivUserBg;
        ImageView ivUserHeadImg;
        TextView tvAttentionCount;
        TextView tvFansCount;
        TextView tvVisitorCount;
        TextView tvUserName;
        TextView tvUserBref;
        TextView tvPayAttention;
    }

    /**
     * 圈子用到的控件
     */
    private class HotDynamic_Circle {
        ImageView ivCircleBg;
        ImageView ivCirclePic;
        TextView tvCircleType;
        TextView tvAttentionCount;
        TextView tvDynamicsCount;
        TextView tvCircleName;
        TextView tvCircleBref;
        ImageView ivSubscribe;
    }

    /**
     * 文章动态用到的控件
     */
    private class HotDynamic_DynamicArticle {
        ImageView iv_picture;
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
     * 普通动态用到的控件
     */
    private class HotDynamic_DynamicNormal {
        //关于图片
        View pictures;
        View pic1To3;
        View pic2To3;
        View pic4To6;
        ImageView ivPic1, ivPic2, ivPic3, ivPic4, ivPic5, ivPic6;
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
     * 话题动态用到的控件
     */
    private class HotDynamic_DynamicTopic {
        ImageView iv_picture;
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
     * 圈子内动态：普通动态
     */
    private class DynamicInCircleNormal {
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
    private class DynamicInCircleArticle {
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
    private class DynamicInCircleTopic {
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
     * 点击条目的监听器
     */
    public interface OnItemClickListener {
        /**
         * 条目被点击的触发方法
         *
         * @param position 条目数
         */
        void onItemClick(int position);
    }

    /**
     * 用户关注按钮的监听器
     */
    public interface OnUserAttentionClickListener {
        /**
         * 当用户关注按钮的点击回调方法
         */
        void onUserAttentionClick(int position);
    }

    /**
     * 圈子訂閱按钮的监听器
     */
    public interface OnCircleSubscribeClickListener {
        /**
         * 当圈子订阅按钮的点击回调方法
         */
        void onCircleSubscribeClick(int position);
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
     * 原创作者的点击监听器
     */
    public interface OnCreatorUserClickListener {

        /**
         * 创建动态的用户点击回调方法
         *
         * @param position 条目数
         */
        void onCreatorUserClick(int position);
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
     * 设置条目点击的监听器
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置用户关注按钮点击的监听器
     */
    public void setOnUserAttentionClickListener(
            OnUserAttentionClickListener onUserAttentionClickListener) {
        this.onUserAttentionClickListener = onUserAttentionClickListener;
    }

    /**
     * 设置头像的点击监听器
     */
    public void setOnHeadImgClickListener(OnHeadImgClickListener onHeadImgClickListener) {
        this.onHeadImgClickListener = onHeadImgClickListener;
    }

    /**
     * 设置圈子订阅按钮点击的监听器
     */
    public void setOnCircleSubscribeClickListener(
            OnCircleSubscribeClickListener onCircleSubscribeClickListener) {
        this.onCircleSubscribeClickListener = onCircleSubscribeClickListener;
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
     * 设置原创作者的点击监听
     */
    public void setOnCreatorUserClickListener(OnCreatorUserClickListener onCreatorUserClickListener) {
        this.onCreatorUserClickListener = onCreatorUserClickListener;
    }


    /**
     * 设置分享的点击监听
     */
    public void setOnShareClickListener(OnShareClickListener onShareClickListener) {
        this.onShareClickListener = onShareClickListener;
    }

    /**
     * 条目点击的监听器
     */
    private View.OnClickListener clickItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onItemClickListener == null) {
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
                case R.id.idcn_ll_content:
                case R.id.idct_ll_content:
                case R.id.idca_ll_content:
                    onItemClickListener.onItemClick(position);
                    break;
            }
        }
    };

    /**
     * 用户关注按钮点击的监听器
     */
    private View.OnClickListener clickUserAttentionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onUserAttentionClickListener == null) {
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
                case R.id.idou_tv_patAttention:
                    onUserAttentionClickListener.onUserAttentionClick(position);
                    break;
            }
        }
    };
    /**
     * 圈子订阅按钮点击的监听器
     */
    private View.OnClickListener clickCircleSubscribeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onCircleSubscribeClickListener == null) {
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
                case R.id.idoc_iv_subscribe:
                    onCircleSubscribeClickListener.onCircleSubscribeClick(position);
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
                case R.id.idoa_contentHeadImg:
                case R.id.idon_contentHeadImg:
                case R.id.idot_contentHeadImg:
                case R.id.iidc_contentHeadImg:
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
                case R.id.idoa_iv_comment:
                case R.id.idon_iv_comment:
                case R.id.idca_iv_comment:
                case R.id.idcc_iv_comment:
                case R.id.idct_iv_comment:
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
                case R.id.idoa_iv_heart:
                case R.id.idot_iv_heart:
                case R.id.idon_iv_heart:
                case R.id.idca_iv_heart:
                case R.id.idcn_iv_heart:
                case R.id.idct_iv_heart:
                    onLikeClickListener.onLikeClick(position);
                    break;
            }
        }
    };
    /**
     * 原创作者点击的监听器
     */
    private View.OnClickListener clickCreatorUserListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onCreatorUserClickListener == null) {
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
                case R.id.idcn_tv_originator_nickname:
                case R.id.idct_tv_originator_nickname:
                case R.id.idca_tv_originator_nickname:
                    onCreatorUserClickListener.onCreatorUserClick(position);
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
                case R.id.idoa_iv_share:
                case R.id.idon_iv_share:
                case R.id.idot_iv_share:
                case R.id.idca_iv_share:
                case R.id.idcc_iv_share:
                case R.id.idct_iv_share:
                    onShareClickListener.onShareClick(position);
                    break;
            }
        }
    };
}
