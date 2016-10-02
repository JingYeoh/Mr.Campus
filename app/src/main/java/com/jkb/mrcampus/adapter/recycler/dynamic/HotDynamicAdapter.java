package com.jkb.mrcampus.adapter.recycler.dynamic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.function.data.hot.HotDynamic;
import com.jkb.core.contract.function.data.hot.circle.CircleDynamic;
import com.jkb.core.contract.function.data.hot.dynamic.OriginalArticleDynamic;
import com.jkb.core.contract.function.data.hot.dynamic.OriginalDynamic;
import com.jkb.core.contract.function.data.hot.dynamic.OriginalNormalDynamic;
import com.jkb.core.contract.function.data.hot.dynamic.OriginalTopicDynamic;
import com.jkb.core.contract.function.data.hot.user.UserDynamic;
import com.jkb.mrcampus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 热门动态的适配器
 * Created by JustKiddingBaby on 2016/10/2.
 */

public class HotDynamicAdapter extends RecyclerView.Adapter<HotDynamicAdapter.ViewHolder> {

    private Context context;
    public List<HotDynamic> hotDynamics;

    //常量
    private static final int HOT_TYPE_USER = 1001;
    private static final int HOT_TYPE_CIRCLE = 1010;
    private static final int HOT_TYPE_DYNAMIC_NORMAL = 2001;
    private static final int HOT_TYPE_DYNAMIC_TOPIC = 2002;
    private static final int HOT_TYPE_DYNAMIC_ARTICLE = 2003;


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
        int hotDynamicType = -1;
        //判断类型
        if (hotDynamic instanceof UserDynamic) {//用户类型
            hotDynamicType = HOT_TYPE_USER;
        } else if (hotDynamic instanceof CircleDynamic) {//圈子类型
            hotDynamicType = HOT_TYPE_CIRCLE;
        } else if (hotDynamic instanceof OriginalDynamic) {//动态类型
            hotDynamicType = judgeOriginalDynamicType(hotDynamic, hotDynamicType);
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
        ViewHolder holder = null;
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
        }
        holder.viewType = viewType;
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
        holder.user.ivUserBg = (ImageView) view.findViewById(R.id.idou_iv_userBg);
        holder.user.ivUserHeadImg = (ImageView) view.findViewById(R.id.idou_iv_userHeadImg);
        holder.user.tvAttentionCount = (TextView) view.findViewById(R.id.idou_tv_attentionCount);
        holder.user.tvFansCount = (TextView) view.findViewById(R.id.idou_tv_fansCount);
        holder.user.tvVisitorCount = (TextView) view.findViewById(R.id.idou_tv_visitorCount);
        holder.user.tvPayAttention = (TextView) view.findViewById(R.id.idou_tv_patAttention);
        holder.user.tvUserName = (TextView) view.findViewById(R.id.idou_tv_userName);
        holder.user.tvUserBref = (TextView) view.findViewById(R.id.idou_tv_userBref);
        //初始化点击事件的监听
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
        holder.circle.ivCircleBg = (ImageView) view.findViewById(R.id.idoc_iv_circleBg);
        holder.circle.ivCirclePic = (ImageView) view.findViewById(R.id.idoc_iv_circlePic);
        holder.circle.tvCircleType = (TextView) view.findViewById(R.id.idoc_tv_circleType);
        holder.circle.tvDynamicsCount = (TextView) view.findViewById(R.id.idoc_tv_dynamicsCount);
        holder.circle.tvAttentionCount = (TextView) view.findViewById(R.id.idoc_tv_operationCount);
        holder.circle.tvCircleName = (TextView) view.findViewById(R.id.idoc_tv_circleName);
        holder.circle.tvCircleBref = (TextView) view.findViewById(R.id.idoc_tv_circleBref);
        //初始化点击事件的监听
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
        }
    }

    /**
     * 绑定热门动态用户的数据
     */
    private void bindUserData(ViewHolder holder, int position) {
        UserDynamic userDynamic = (UserDynamic) hotDynamics.get(position);
    }

    /**
     * 绑定热门动态圈子的数据
     */
    private void bindCircleData(ViewHolder holder, int position) {
        CircleDynamic circleDynamic = (CircleDynamic) hotDynamics.get(position);
    }

    /**
     * 绑定热门文章动态的数据
     */
    private void bindDynamicArticleData(ViewHolder holder, int position) {
        OriginalArticleDynamic articleDynamic = (OriginalArticleDynamic) hotDynamics.get(position);
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
        HotDynamic_User user;//用户
        HotDynamic_Circle circle;//圈子
        HotDynamic_DynamicArticle dynamicArticle;//文章动态
        HotDynamic_DynamicNormal dynamicNormal;//普通动态
        HotDynamic_DynamicTopic dynamicTopic;//话题动态
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
        ImageView ivComment;//评论
    }
}
