package com.jkb.mrcampus.adapter.recycler.circle.dynamicInBlackList;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.data.circle.dynamicInBlackList.ArticleDynamicInBlackList;
import com.jkb.core.data.circle.dynamicInBlackList.DynamicInBlackList;
import com.jkb.core.data.circle.dynamicInBlackList.NormalDynamicInBlackList;
import com.jkb.core.data.circle.dynamicInBlackList.TopicDynamicInBlackList;
import com.jkb.core.data.info.dynamic.content.DynamicContentArticleInfo;
import com.jkb.core.data.info.dynamic.content.DynamicContentNormalInfo;
import com.jkb.core.data.info.dynamic.content.DynamicContentTopicInfo;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态圈子的拉黑数据适配器
 * Created by JustKiddingBaby on 2016/11/5.
 */

public class CircleDynamicInBlackListAdapter extends RecyclerView.Adapter
        <CircleDynamicInBlackListAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    public List<DynamicInBlackList> dynamicInBlackLists;

    //常量
    private static final int DYNAMIC_TYPE_ARTICLE = 1001;
    private static final int DYNAMIC_TYPE_NORMAL = 1002;
    private static final int DYNAMIC_TYPE_TOPIC = 1003;

    //监听器
    private OnDynamicInCircleBlackListItemClickListener onDynamicInCircleBlackListItemClickListener;

    //两种颜色
    private int selector_bg[] = new int[]
            {R.drawable.selector_white_general, R.drawable.selector_bg_general};

    public CircleDynamicInBlackListAdapter(
            Context context, List<DynamicInBlackList> dynamicInBlackLists) {
        this.context = context;
        if (dynamicInBlackLists == null) {
            dynamicInBlackLists = new ArrayList<>();
        }
        this.dynamicInBlackLists = dynamicInBlackLists;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        DynamicInBlackList dynamicInBlackList = dynamicInBlackLists.get(position);
        if (dynamicInBlackList instanceof ArticleDynamicInBlackList) {
            viewType = DYNAMIC_TYPE_ARTICLE;
        } else if (dynamicInBlackList instanceof NormalDynamicInBlackList) {
            viewType = DYNAMIC_TYPE_NORMAL;
        } else if (dynamicInBlackList instanceof TopicDynamicInBlackList) {
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
                holder = initArticle(parent, inflater);
                break;
            case DYNAMIC_TYPE_NORMAL:
                holder = initNormal(parent, inflater);
                break;
            case DYNAMIC_TYPE_TOPIC:
                holder = initTopic(parent, inflater);
                break;
            default:
                holder = initBlank(parent, inflater);
                break;
        }
        holder.viewType = viewType;
        return holder;
    }

    /**
     * 初始化空白的
     */
    private ViewHolder initBlank(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_circle_dynamic_black_list_blank, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.dynamicInCircleBlackListContent);
        holder.contentView.setVisibility(View.GONE);
        return holder;
    }

    /**
     * 初始化文章
     */
    private ViewHolder initArticle(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_circle_dynamic_black_list_article, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.dynamicInCircleBlackListContent);
        if (holder.articleDynamic == null) {
            holder.articleDynamic = new ArticleDynamic();
        }
        holder.articleDynamic.ivHeadImg = (ImageView) view.findViewById(R.id.icdbla_iv_headImg);
        holder.articleDynamic.contentHeadImg = view.findViewById(R.id.icdbla_contentHeadImg);
        holder.articleDynamic.tvNickName = (TextView) view.findViewById(R.id.icdbla_tv_nickName);
        holder.articleDynamic.tvContentValue = (TextView) view.findViewById(R.id.icdbla_tv_contentText);
        holder.articleDynamic.ivPicture = (ImageView) view.findViewById(R.id.icdbla_iv_picture);
        holder.articleDynamic.tvTime = (TextView) view.findViewById(R.id.icdbla_tv_time);
        holder.articleDynamic.tvRestore = (TextView) view.findViewById(R.id.icdbla_tv_restore);
        holder.articleDynamic.tvArticleTitle = (TextView) view.findViewById(R.id.icdbla_tv_dynamicName);
        //初始化监听事件
        holder.articleDynamic.contentHeadImg.setOnClickListener(this);
        holder.articleDynamic.tvNickName.setOnClickListener(this);
        holder.articleDynamic.tvRestore.setOnClickListener(this);
        holder.contentView.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化普通
     */
    private ViewHolder initNormal(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_circle_dynamic_black_list_normal, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.dynamicInCircleBlackListContent);
        if (holder.normalDynamic == null) {
            holder.normalDynamic = new NormalDynamic();
        }
        holder.normalDynamic.ivHeadImg = (ImageView) view.findViewById(R.id.icdbln_iv_headImg);
        holder.normalDynamic.contentHeadImg = view.findViewById(R.id.icdbln_contentHeadImg);
        holder.normalDynamic.tvNickName = (TextView) view.findViewById(R.id.icdbln_tv_nickName);
        holder.normalDynamic.tvContentValue = (TextView) view.findViewById(R.id.icdbln_tv_contentText);
        holder.normalDynamic.ivPicture = (ImageView) view.findViewById(R.id.icdbln_iv_picture);
        holder.normalDynamic.tvTime = (TextView) view.findViewById(R.id.icdbln_tv_time);
        holder.normalDynamic.tvRestore = (TextView) view.findViewById(R.id.icdbln_tv_restore);
        //初始化监听事件
        holder.normalDynamic.contentHeadImg.setOnClickListener(this);
        holder.normalDynamic.tvNickName.setOnClickListener(this);
        holder.normalDynamic.tvRestore.setOnClickListener(this);
        holder.contentView.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化话题
     */
    private ViewHolder initTopic(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_circle_dynamic_black_list_topic, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.dynamicInCircleBlackListContent);
        if (holder.topicDynamic == null) {
            holder.topicDynamic = new TopicDynamic();
        }
        holder.topicDynamic.ivHeadImg = (ImageView) view.findViewById(R.id.icdblt_iv_headImg);
        holder.topicDynamic.contentHeadImg = view.findViewById(R.id.icdblt_contentHeadImg);
        holder.topicDynamic.tvNickName = (TextView) view.findViewById(R.id.icdblt_tv_nickName);
        holder.topicDynamic.tvContentValue = (TextView) view.findViewById(R.id.icdblt_tv_contentText);
        holder.topicDynamic.ivPicture = (ImageView) view.findViewById(R.id.icdblt_iv_picture);
        holder.topicDynamic.tvTime = (TextView) view.findViewById(R.id.icdblt_tv_time);
        holder.topicDynamic.tvRestore = (TextView) view.findViewById(R.id.icdblt_tv_restore);
        holder.topicDynamic.tvTopicTitle = (TextView) view.findViewById(R.id.icdblt_tv_dynamicName);
        holder.topicDynamic.tvPartInNum = (TextView) view.findViewById(R.id.icdblt_tv_partInNum);
        //初始化监听事件
        holder.topicDynamic.contentHeadImg.setOnClickListener(this);
        holder.topicDynamic.tvNickName.setOnClickListener(this);
        holder.topicDynamic.tvRestore.setOnClickListener(this);
        holder.contentView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = holder.viewType;
        holder.contentView.setBackgroundResource(selector_bg[position % 2]);
        switch (viewType) {
            case DYNAMIC_TYPE_ARTICLE:
                bindArticleDynamicData(holder, position);
                break;
            case DYNAMIC_TYPE_NORMAL:
                bindNormalDynamicData(holder, position);
                break;
            case DYNAMIC_TYPE_TOPIC:
                bindTopicDynamicData(holder, position);
                break;
            default:
                break;
        }
    }

    /**
     * 绑定话题动态数据
     */
    private void bindTopicDynamicData(ViewHolder holder, int position) {
        TopicDynamicInBlackList dynamic = (TopicDynamicInBlackList)
                dynamicInBlackLists.get(position);
        UserInfo user = dynamic.getUser();
        DynamicContentTopicInfo topic = dynamic.getTopicInfo();
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.topicDynamic.contentHeadImg,
                holder.topicDynamic.tvNickName,
                holder.topicDynamic.tvRestore);
        //设置通用数据
        if (StringUtils.isEmpty(user.getAvatar())) {
            holder.topicDynamic.ivHeadImg.setVisibility(View.GONE);
            holder.topicDynamic.contentHeadImg.setVisibility(View.GONE);
        } else {
            holder.topicDynamic.ivHeadImg.setVisibility(View.VISIBLE);
            holder.topicDynamic.contentHeadImg.setVisibility(View.VISIBLE);
            loadImageByUrl(holder.topicDynamic.ivHeadImg, user.getAvatar());
        }
        holder.topicDynamic.tvNickName.setText(user.getNickName());
        holder.topicDynamic.tvTime.setText(dynamic.getDynamic_created_at());
        //普通相关
        holder.topicDynamic.tvTopicTitle.setText(dynamic.getTitle());
        holder.topicDynamic.tvPartInNum.setText(dynamic.getCount_of_partIn());
        String img = topic.getImg();
        String doc = topic.getDoc();
        holder.topicDynamic.tvContentValue.setText(doc);
        if (StringUtils.isEmpty(img)) {
            holder.topicDynamic.ivPicture.setVisibility(View.GONE);
        } else {
            holder.topicDynamic.ivPicture.setVisibility(View.VISIBLE);
            loadImageByUrl(holder.topicDynamic.ivPicture, img);
        }
    }

    /**
     * 绑定普通动态数据
     */
    private void bindNormalDynamicData(ViewHolder holder, int position) {
        NormalDynamicInBlackList dynamic = (NormalDynamicInBlackList)
                dynamicInBlackLists.get(position);
        UserInfo user = dynamic.getUser();
        DynamicContentNormalInfo normal = dynamic.getNormalInfo();
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.normalDynamic.contentHeadImg,
                holder.normalDynamic.tvNickName,
                holder.normalDynamic.tvRestore);
        //设置通用数据
        if (StringUtils.isEmpty(user.getAvatar())) {
            holder.normalDynamic.ivHeadImg.setVisibility(View.GONE);
            holder.normalDynamic.contentHeadImg.setVisibility(View.GONE);
        } else {
            holder.normalDynamic.ivHeadImg.setVisibility(View.VISIBLE);
            holder.normalDynamic.contentHeadImg.setVisibility(View.VISIBLE);
            loadImageByUrl(holder.normalDynamic.ivHeadImg, user.getAvatar());
        }
        holder.normalDynamic.tvNickName.setText(user.getNickName());
        holder.normalDynamic.tvTime.setText(dynamic.getDynamic_created_at());
        //普通相关
        List<String> imgs = normal.getImg();
        String img;
        String doc = normal.getDoc();
        if (imgs == null || imgs.size() == 0) {
            img = null;
        } else {
            img = imgs.get(0);
        }
        holder.normalDynamic.tvContentValue.setText(doc);
        if (StringUtils.isEmpty(img)) {
            holder.normalDynamic.ivPicture.setVisibility(View.GONE);
        } else {
            holder.normalDynamic.ivPicture.setVisibility(View.VISIBLE);
            loadImageByUrl(holder.normalDynamic.ivPicture, img);
        }
    }

    /**
     * 绑定文章动态数据
     */
    private void bindArticleDynamicData(ViewHolder holder, int position) {
        ArticleDynamicInBlackList dynamic = (ArticleDynamicInBlackList)
                dynamicInBlackLists.get(position);
        holder.contentView.setVisibility(View.VISIBLE);
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.articleDynamic.contentHeadImg,
                holder.articleDynamic.tvNickName,
                holder.articleDynamic.tvRestore);
        UserInfo user = dynamic.getUser();
        DynamicContentArticleInfo article = dynamic.getArticleInfo();
        //设置通用数据
        if (StringUtils.isEmpty(user.getAvatar())) {
            holder.articleDynamic.ivHeadImg.setVisibility(View.GONE);
            holder.articleDynamic.contentHeadImg.setVisibility(View.GONE);
        } else {
            holder.articleDynamic.ivHeadImg.setVisibility(View.VISIBLE);
            holder.articleDynamic.contentHeadImg.setVisibility(View.VISIBLE);
            loadImageByUrl(holder.articleDynamic.ivHeadImg, user.getAvatar());
        }
        holder.articleDynamic.tvNickName.setText(user.getNickName());
        holder.articleDynamic.tvTime.setText(dynamic.getDynamic_created_at());
        //文章相关
        holder.articleDynamic.tvArticleTitle.setText(dynamic.getTitle());
        List<DynamicContentArticleInfo.Article> articles = article.getArticle();
        String img = null;
        String doc = null;
        for (DynamicContentArticleInfo.Article articleItem :
                articles) {
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
        holder.articleDynamic.tvContentValue.setText(doc);
        if (StringUtils.isEmpty(img)) {
            holder.articleDynamic.ivPicture.setVisibility(View.GONE);
        } else {
            holder.articleDynamic.ivPicture.setVisibility(View.VISIBLE);
            loadImageByUrl(holder.articleDynamic.ivPicture, img);
        }
    }

    /**
     * 加载网络图片
     */
    private void loadImageByUrl(ImageView ivHeadImg, String avatar) {
        ImageLoaderFactory.getInstance().displayImage(ivHeadImg, avatar);
    }

    @Override
    public int getItemCount() {
        return dynamicInBlackLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        int viewType;
        View contentView;
        ArticleDynamic articleDynamic;
        NormalDynamic normalDynamic;
        TopicDynamic topicDynamic;
    }

    private class ArticleDynamic {
        //通用的数据
        ImageView ivHeadImg;
        View contentHeadImg;
        TextView tvNickName;
        TextView tvContentValue;
        ImageView ivPicture;
        TextView tvTime;
        TextView tvRestore;
        //文章
        TextView tvArticleTitle;
    }

    private class NormalDynamic {
        //通用的数据
        ImageView ivHeadImg;
        View contentHeadImg;
        TextView tvNickName;
        TextView tvContentValue;
        ImageView ivPicture;
        TextView tvTime;
        TextView tvRestore;
    }

    private class TopicDynamic {
        //通用的数据
        ImageView ivHeadImg;
        View contentHeadImg;
        TextView tvNickName;
        TextView tvContentValue;
        ImageView ivPicture;
        TextView tvTime;
        TextView tvRestore;
        //话题
        TextView tvPartInNum;
        TextView tvTopicTitle;
    }

    /**
     * 被拉黑的动态条目的点击监听事件
     */
    public interface OnDynamicInCircleBlackListItemClickListener {

        /**
         * 动态被点击的时候
         */
        void onDynamicItemClick(int position);

        /**
         * 用户条目被点击的时候
         */
        void onUserItemClick(int position);

        /**
         * 恢复条目被点击的时候
         */
        void onRestoreItemClick(int position);
    }

    /**
     * 设置动态条目的监听器
     */
    public void setOnDynamicInCircleBlackListItemClickListener(
            OnDynamicInCircleBlackListItemClickListener
                    onDynamicInCircleBlackListItemClickListener) {
        this.onDynamicInCircleBlackListItemClickListener = onDynamicInCircleBlackListItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onDynamicInCircleBlackListItemClickListener == null) {
            return;
        }
        Bundle bundle = (Bundle) v.getTag();
        if (bundle == null) {
            return;
        }
        int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
        int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
        switch (viewId) {
            case R.id.icdbla_contentHeadImg:
            case R.id.icdbln_contentHeadImg:
            case R.id.icdblt_contentHeadImg:
            case R.id.icdbla_tv_nickName:
            case R.id.icdbln_tv_nickName:
            case R.id.icdblt_tv_nickName:
                onDynamicInCircleBlackListItemClickListener.onUserItemClick(position);
                break;
            case R.id.icdbla_tv_restore:
            case R.id.icdbln_tv_restore:
            case R.id.icdblt_tv_restore:
                onDynamicInCircleBlackListItemClickListener.onRestoreItemClick(position);
                break;
            case R.id.dynamicInCircleBlackListContent:
                onDynamicInCircleBlackListItemClickListener.onDynamicItemClick(position);
                break;
        }
    }
}
