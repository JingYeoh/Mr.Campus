package com.jkb.mrcampus.adapter.recycler.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.api.config.Config;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.info.dynamic.content.DynamicContentArticleInfo;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.core.data.search.detail.SearchData;
import com.jkb.core.data.search.detail.circle.SearchCircleData;
import com.jkb.core.data.search.detail.dynamic.SearchDynamicArticleData;
import com.jkb.core.data.search.detail.dynamic.SearchDynamicData;
import com.jkb.core.data.search.detail.dynamic.SearchDynamicNormalData;
import com.jkb.core.data.search.detail.dynamic.SearchDynamicTopicData;
import com.jkb.core.data.search.detail.dynamicInCircle.SearchDynamicICArticleData;
import com.jkb.core.data.search.detail.dynamicInCircle.SearchDynamicICNormalData;
import com.jkb.core.data.search.detail.dynamicInCircle.SearchDynamicICTopicData;
import com.jkb.core.data.search.detail.dynamicInCircle.SearchDynamicInCircleData;
import com.jkb.core.data.search.detail.subject.SearchSubjectData;
import com.jkb.core.data.search.detail.user.SearchUserData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 搜索详情的数据适配器
 * Created by JustKiddingBaby on 2016/11/26.
 */

public class SearchDetailAdapter extends RecyclerView.Adapter<SearchDetailAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    public List<SearchData> mSearchData;

    //常量
    private static final int SEARCH_TYPE_DYNAMIC_NORMAL = 10011;
    private static final int SEARCH_TYPE_DYNAMIC_ARTICLE = 10012;
    private static final int SEARCH_TYPE_DYNAMIC_TOPIC = 10013;
    private static final int SEARCH_TYPE_DYNAMICINCIRCLE_NORMAL = 10021;
    private static final int SEARCH_TYPE_DYNAMICINCIRCLE_ARTICLE = 10022;
    private static final int SEARCH_TYPE_DYNAMICINCIRCLE_TOPIC = 10023;
    private static final int SEARCH_TYPE_USER = 1003;
    private static final int SEARCH_TYPE_CIRCLE = 1004;
    private static final int SEARCH_TYPE_SUBJECT = 1005;

    //监听器
    private OnSearchResultItemClickListener onSearchResultItemClickListener;

    public SearchDetailAdapter(Context context, List<SearchData> mSearchData) {
        this.context = context;
        if (mSearchData == null) {
            mSearchData = new ArrayList<>();
        }
        this.mSearchData = mSearchData;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        SearchData searchData = mSearchData.get(position);
        if (searchData instanceof SearchDynamicData) {
            viewType = getDynamicViewType(searchData);
        } else if (searchData instanceof SearchDynamicInCircleData) {
            viewType = getDynamicInCircleViewType(searchData);
        } else if (searchData instanceof SearchUserData) {
            viewType = SEARCH_TYPE_USER;
        } else if (searchData instanceof SearchCircleData) {
            viewType = SEARCH_TYPE_CIRCLE;
        } else if (searchData instanceof SearchSubjectData) {
            viewType = SEARCH_TYPE_SUBJECT;
        }
        return viewType;
    }

    /**
     * 得到圈子内动态的视图类型
     */
    private int getDynamicInCircleViewType(SearchData searchData) {
        int viewType = 0;
        if (searchData instanceof SearchDynamicICArticleData) {
            viewType = SEARCH_TYPE_DYNAMICINCIRCLE_ARTICLE;
        } else if (searchData instanceof SearchDynamicICNormalData) {
            viewType = SEARCH_TYPE_DYNAMICINCIRCLE_NORMAL;
        } else if (searchData instanceof SearchDynamicICTopicData) {
            viewType = SEARCH_TYPE_DYNAMICINCIRCLE_TOPIC;
        }
        return viewType;
    }

    /**
     * 得到动态的视图类型
     */
    private int getDynamicViewType(SearchData searchData) {
        int viewType = 0;
        if (searchData instanceof SearchDynamicArticleData) {
            viewType = SEARCH_TYPE_DYNAMIC_ARTICLE;
        } else if (searchData instanceof SearchDynamicNormalData) {
            viewType = SEARCH_TYPE_DYNAMIC_NORMAL;
        } else if (searchData instanceof SearchDynamicTopicData) {
            viewType = SEARCH_TYPE_DYNAMIC_TOPIC;
        }
        return viewType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder;
        switch (viewType) {
            case SEARCH_TYPE_DYNAMIC_ARTICLE:
                holder = initSearchArticleView(inflater, parent);
                break;
            case SEARCH_TYPE_DYNAMIC_NORMAL:
                holder = initSearchNormalView(inflater, parent);
                break;
            case SEARCH_TYPE_DYNAMIC_TOPIC:
                holder = initSearchTopicView(inflater, parent);
                break;
            case SEARCH_TYPE_DYNAMICINCIRCLE_ARTICLE:
                holder = initSearchArticleInCircleView(inflater, parent);
                break;
            case SEARCH_TYPE_DYNAMICINCIRCLE_NORMAL:
                holder = initSearchNormalInCircleView(inflater, parent);
                break;
            case SEARCH_TYPE_DYNAMICINCIRCLE_TOPIC:
                holder = initSearchTopicInCircleView(inflater, parent);
                break;
            case SEARCH_TYPE_USER:
                holder = initSearchUserView(inflater, parent);
                break;
            case SEARCH_TYPE_CIRCLE:
                holder = initSearchCircleView(inflater, parent);
                break;
            case SEARCH_TYPE_SUBJECT:
                holder = initSearchSubjectView(inflater, parent);
                break;
            default:
                holder = initSearchBlankView(inflater, parent);
                break;
        }
        holder.viewType = viewType;
        holder.contentView.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化圈子内文章动态视图
     */
    private ViewHolder initSearchArticleInCircleView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(
                R.layout.item_search_result_dynamic_incircle_article, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.searchItemContent);
        if (holder.articleInCircleView == null) {
            holder.articleInCircleView = new ArticleInCircleView();
        }
        //初始化id
        holder.articleInCircleView.tvCircleName = (TextView) view.findViewById(R.id.isrdia_tv_circleName);
        holder.articleInCircleView.ivCirclePicture = (ImageView) view.findViewById(R.id.isrdia_iv_circlepicture);
        holder.articleInCircleView.contentCirclePicture = view.findViewById(R.id.isrdia_content_circlePicture);
        holder.articleInCircleView.contentPicture = view.findViewById(R.id.isrdia_content_picture);
        holder.articleInCircleView.tvTitle = (TextView) view.findViewById(R.id.isrdia_tv_articleName);
        holder.articleInCircleView.tvDoc = (TextView) view.findViewById(R.id.isrdia_tv_doc);
        holder.articleInCircleView.ivTopicPicture = (ImageView) view.findViewById(R.id.isrdia_iv_picture);
        //初始化点击事件
        holder.articleInCircleView.contentCirclePicture.setOnClickListener(this);
        holder.articleInCircleView.tvCircleName.setOnClickListener(this);
        holder.articleInCircleView.contentPicture.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化文章动态视图
     */
    private ViewHolder initSearchArticleView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_search_result_dynamic_article, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.searchItemContent);
        if (holder.articleView == null) {
            holder.articleView = new ArticleView();
        }
        //初始化id
        holder.articleView.tvTitle = (TextView) view.findViewById(R.id.isrda_tv_articleName);
        holder.articleView.tvDoc = (TextView) view.findViewById(R.id.isrda_tv_doc);
        holder.articleView.ivArticlePicture = (ImageView) view.findViewById(R.id.isrda_iv_picture);
        holder.articleView.contentPicture = view.findViewById(R.id.isrda_content_picture);
        //初始化点击事件
        holder.articleView.contentPicture.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化圈子内专题动态视图
     */
    private ViewHolder initSearchTopicInCircleView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(
                R.layout.item_search_result_dynamic_incircle_topic, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.searchItemContent);
        if (holder.topicInCircleView == null) {
            holder.topicInCircleView = new TopicInCircleView();
        }
        //初始化id
        holder.topicInCircleView.tvCircleName = (TextView) view.findViewById(R.id.isrdit_tv_circleName);
        holder.topicInCircleView.ivCirclePicture = (ImageView) view.findViewById(R.id.isrdit_iv_circlepicture);
        holder.topicInCircleView.contentCirclePicture = view.findViewById(R.id.isrdit_content_circlePicture);
        holder.topicInCircleView.tvTitle = (TextView) view.findViewById(R.id.isrdit_tv_topicName);
        holder.topicInCircleView.tvPartInNum = (TextView) view.findViewById(R.id.isrdit_tv_partInNum);
        holder.topicInCircleView.tvDoc = (TextView) view.findViewById(R.id.isrdit_tv_doc);
        holder.topicInCircleView.ivTopicPicture = (ImageView) view.findViewById(R.id.isrdit_iv_picture);
        holder.topicInCircleView.contentPicture = view.findViewById(R.id.isrdit_content_picture);
        //初始化点击事件
        holder.topicInCircleView.contentCirclePicture.setOnClickListener(this);
        holder.topicInCircleView.tvCircleName.setOnClickListener(this);
        holder.topicInCircleView.contentPicture.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化专题动态视图
     */
    private ViewHolder initSearchTopicView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_search_result_dynamic_topic, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.searchItemContent);
        if (holder.topicView == null) {
            holder.topicView = new TopicView();
        }
        //初始化id
        holder.topicView.tvTitle = (TextView) view.findViewById(R.id.isrdt_tv_topicName);
        holder.topicView.tvPartInNum = (TextView) view.findViewById(R.id.isrdt_tv_partInNum);
        holder.topicView.tvDoc = (TextView) view.findViewById(R.id.isrdt_tv_doc);
        holder.topicView.ivTopicPicture = (ImageView) view.findViewById(R.id.isrdt_iv_picture);
        holder.topicView.contentPicture = view.findViewById(R.id.isrdt_content_picture);
        //初始化点击事件
        holder.topicView.contentPicture.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化圈子内普通动态视图
     */
    private ViewHolder initSearchNormalInCircleView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(
                R.layout.item_search_result_dynamic_incircle_normal, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.searchItemContent);
        if (holder.normalInCircleView == null) {
            holder.normalInCircleView = new NormalInCircleView();
        }
        //初始化控件id
        holder.normalInCircleView.tvCircleName = (TextView)
                view.findViewById(R.id.isrdin_tv_circleName);
        holder.normalInCircleView.ivCirclePicture = (ImageView)
                view.findViewById(R.id.isrdin_iv_picture);
        holder.normalInCircleView.contentCirclePicture = view.findViewById(R.id.isrdin_content_circlePicture);
        holder.normalInCircleView.tvDoc = (TextView) view.findViewById(R.id.isrdin_tv_doc);
        holder.normalInCircleView.rvPictures = (RecyclerView) view.findViewById(R.id.isrdin_rv);
        holder.normalInCircleView.picturesAdapter = new SearchResultPicturesAdapter(context, null);
        holder.normalInCircleView.rvPictures.setAdapter(holder.normalInCircleView.picturesAdapter);
        holder.normalInCircleView.staggeredGridLayoutManager = new
                StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        holder.normalInCircleView.rvPictures.setLayoutManager(
                holder.normalInCircleView.staggeredGridLayoutManager);
        holder.normalInCircleView.rvPictures.setNestedScrollingEnabled(false);
        //初始化监听事件
        holder.normalInCircleView.picturesAdapter.setOnSearchPicturesItemClickListener(
                onSearchPicturesItemClickListener);
        holder.normalInCircleView.contentCirclePicture.setOnClickListener(this);
        holder.normalInCircleView.tvCircleName.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化普通动态视图
     */
    private ViewHolder initSearchNormalView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_search_result_dynamic_normal, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.searchItemContent);
        if (holder.normalView == null) {
            holder.normalView = new NormalView();
        }
        //初始化控件id
        holder.normalView.tvDoc = (TextView) view.findViewById(R.id.isrdn_tv_doc);
        holder.normalView.rvPictures = (RecyclerView) view.findViewById(R.id.isrdn_rv);
        holder.normalView.picturesAdapter = new SearchResultPicturesAdapter(context, null);
        holder.normalView.rvPictures.setAdapter(holder.normalView.picturesAdapter);
        holder.normalView.staggeredGridLayoutManager = new
                StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        holder.normalView.rvPictures.setLayoutManager(
                holder.normalView.staggeredGridLayoutManager);
        holder.normalView.rvPictures.setNestedScrollingEnabled(false);
        //初始化监听事件
        holder.normalView.picturesAdapter.setOnSearchPicturesItemClickListener(
                onSearchPicturesItemClickListener);
        return holder;
    }

    /**
     * 初始化专题视图
     */
    private ViewHolder initSearchSubjectView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_search_result_subject, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.searchItemContent);
        if (holder.subjectView == null) {
            holder.subjectView = new SubjectView();
        }
        //初始控件id
        holder.subjectView.tvType = (TextView) view.findViewById(R.id.isrs_tv_subjectType);
        holder.subjectView.tvTitle = (TextView) view.findViewById(R.id.isrs_tv_subjectTitle);
        holder.subjectView.tvDoc = (TextView) view.findViewById(R.id.isrs_tv_subjectDoc);
        holder.subjectView.rvPictures = (RecyclerView) view.findViewById(R.id.isrs_rv);
        holder.subjectView.ivTag = (ImageView) view.findViewById(R.id.isrs_iv_subjectTag);
        holder.subjectView.picturesAdapter = new SearchResultPicturesAdapter(context, null);
        holder.subjectView.rvPictures.setAdapter(holder.subjectView.picturesAdapter);
        holder.subjectView.staggeredGridLayoutManager = new
                StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        holder.subjectView.rvPictures.setLayoutManager(
                holder.subjectView.staggeredGridLayoutManager);
        holder.subjectView.rvPictures.setNestedScrollingEnabled(false);
        //初始化监听事件
        holder.subjectView.picturesAdapter.setOnSearchPicturesItemClickListener(
                onSearchPicturesItemClickListener);
        return holder;
    }

    /**
     * 初始化空的视图
     */
    private ViewHolder initSearchBlankView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_search_result_blank, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.searchItemContent);
        return holder;
    }

    /**
     * 初始化搜索的圈子视图
     */
    private ViewHolder initSearchCircleView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_search_result_circle, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.searchItemContent);
        if (holder.circleView == null) {
            holder.circleView = new CircleView();
        }
        //初始化id
        holder.circleView.ivPicture = (ImageView) view.findViewById(R.id.isrc_iv_picture);
        holder.circleView.contentPicture = view.findViewById(R.id.isrc_contentPicture);
        holder.circleView.tvName = (TextView) view.findViewById(R.id.isrc_tv_name);
        holder.circleView.tvBref = (TextView) view.findViewById(R.id.isrc_tv_bref);
        holder.circleView.tvSubscribeCount = (TextView)
                view.findViewById(R.id.isrc_tv_subscriptionCount);
        holder.circleView.tvTag = (TextView) view.findViewById(R.id.isrc_tv_tag);
        //初始化监听事件
        return holder;
    }

    /**
     * 初始化搜索的用户视图
     */
    private ViewHolder initSearchUserView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_search_result_user, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.searchItemContent);
        if (holder.userView == null) {
            holder.userView = new UserView();
        }
        //初始化id
        holder.userView.ivHeadImg = (CircleImageView) view.findViewById(R.id.isu_iv_headImg);
        holder.userView.tvAttention = (TextView) view.findViewById(R.id.isu_tv_attentionStatus);
        holder.userView.tvNickName = (TextView) view.findViewById(R.id.isu_tv_nickName);
        holder.userView.tvSign = (TextView) view.findViewById(R.id.isu_tv_sign);
        holder.userView.contentHeadImg = view.findViewById(R.id.isu_contentHeadImg);
        //初始化监听事件
        holder.userView.contentHeadImg.setOnClickListener(this);
        holder.userView.tvAttention.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = holder.viewType;
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.contentView);
        switch (viewType) {
            case SEARCH_TYPE_DYNAMIC_ARTICLE:
                bindArticleView(holder, position);
                break;
            case SEARCH_TYPE_DYNAMIC_NORMAL:
                bindNormalView(holder, position);
                break;
            case SEARCH_TYPE_DYNAMIC_TOPIC:
                bindTopicView(holder, position);
                break;
            case SEARCH_TYPE_DYNAMICINCIRCLE_ARTICLE:
                bindArticleInCircleView(holder, position);
                break;
            case SEARCH_TYPE_DYNAMICINCIRCLE_NORMAL:
                bindNormalInCircleView(holder, position);
                break;
            case SEARCH_TYPE_DYNAMICINCIRCLE_TOPIC:
                bindTopicInCircleView(holder, position);
                break;
            case SEARCH_TYPE_USER:
                bindUserView(holder, position);
                break;
            case SEARCH_TYPE_CIRCLE:
                bindCircleView(holder, position);
                break;
            case SEARCH_TYPE_SUBJECT:
                bindSubjectView(holder, position);
                break;
            default:
                break;
        }
    }

    /**
     * 绑定圈子内文章动态
     */
    private void bindArticleInCircleView(ViewHolder holder, int position) {
        ClassUtils.bindViewsTag(position,
                holder.articleInCircleView.contentCirclePicture,
                holder.articleInCircleView.contentPicture,
                holder.articleInCircleView.tvCircleName);
        SearchDynamicICArticleData searchData = (SearchDynamicICArticleData) mSearchData.get(position);
        //设置圈子数据
        CircleInfo circle = searchData.getCircle();
        String pictureUrl = circle.getPictureUrl();
        if (StringUtils.isEmpty(pictureUrl)) {
            holder.articleInCircleView.ivCirclePicture.setImageResource(R.color.default_picture);
        } else {
            loadImage(holder.articleInCircleView.ivCirclePicture, pictureUrl);
        }
        holder.articleInCircleView.tvCircleName.setText(circle.getCircleName());
        holder.articleInCircleView.tvTitle.setText(searchData.getTitle());
        List<DynamicContentArticleInfo.Article> articles = searchData.getArticle().getArticle();
        String doc = null;
        String img = null;
        for (DynamicContentArticleInfo.Article article : articles) {
            if (!StringUtils.isEmpty(doc, img)) {
                break;
            }
            if (StringUtils.isEmpty(doc)) {
                doc = article.getDoc();
            }
            if (StringUtils.isEmpty(img)) {
                img = article.getImg();
            }
        }
        holder.articleInCircleView.tvDoc.setText(doc);
        if (StringUtils.isEmpty(img)) {
            holder.articleInCircleView.ivTopicPicture.setVisibility(View.GONE);
        } else {
            holder.articleInCircleView.ivTopicPicture.setVisibility(View.VISIBLE);
            loadImage(holder.articleInCircleView.ivTopicPicture, img);
        }
    }

    /**
     * 绑定文章动态
     */
    private void bindArticleView(ViewHolder holder, int position) {
        ClassUtils.bindViewsTag(position,
                holder.articleView.contentPicture);
        SearchDynamicArticleData searchData = (SearchDynamicArticleData) mSearchData.get(position);
        holder.articleView.tvTitle.setText(searchData.getTitle());
        List<DynamicContentArticleInfo.Article> articles = searchData.getArticle().getArticle();
        String doc = null;
        String img = null;
        for (DynamicContentArticleInfo.Article article : articles) {
            if (!StringUtils.isEmpty(doc, img)) {
                break;
            }
            if (StringUtils.isEmpty(doc)) {
                doc = article.getDoc();
            }
            if (StringUtils.isEmpty(img)) {
                img = article.getImg();
            }
        }
        holder.articleView.tvDoc.setText(doc);
        if (StringUtils.isEmpty(img)) {
            holder.articleView.ivArticlePicture.setVisibility(View.GONE);
        } else {
            holder.articleView.ivArticlePicture.setVisibility(View.VISIBLE);
            loadImage(holder.articleView.ivArticlePicture, img);
        }
    }

    /**
     * 绑定圈子话题动态
     */
    private void bindTopicInCircleView(ViewHolder holder, int position) {
        ClassUtils.bindViewsTag(position,
                holder.topicInCircleView.contentCirclePicture,
                holder.topicInCircleView.contentPicture,
                holder.topicInCircleView.tvCircleName);
        SearchDynamicICTopicData searchData = (SearchDynamicICTopicData) mSearchData.get(position);
        //设置圈子数据
        CircleInfo circle = searchData.getCircle();
        String pictureUrl = circle.getPictureUrl();
        if (StringUtils.isEmpty(pictureUrl)) {
            holder.topicInCircleView.ivCirclePicture.setImageResource(R.color.default_picture);
        } else {
            loadImage(holder.topicInCircleView.ivCirclePicture, pictureUrl);
        }
        holder.topicInCircleView.tvCircleName.setText(circle.getCircleName());
        holder.topicInCircleView.tvTitle.setText(searchData.getTitle());
        holder.topicInCircleView.tvDoc.setText(searchData.getTopic().getDoc());
        holder.topicInCircleView.tvPartInNum.setText(searchData.getCount_of_participation() + "");
        String img = searchData.getTopic().getImg();
        if (StringUtils.isEmpty(img)) {
            holder.topicInCircleView.ivTopicPicture.setVisibility(View.GONE);
        } else {
            holder.topicInCircleView.ivTopicPicture.setVisibility(View.VISIBLE);
            loadImage(holder.topicInCircleView.ivTopicPicture, img);
        }
    }

    /**
     * 綁定话题动态
     */
    private void bindTopicView(ViewHolder holder, int position) {
        ClassUtils.bindViewsTag(position,
                holder.topicView.contentPicture);
        SearchDynamicTopicData searchData = (SearchDynamicTopicData) mSearchData.get(position);
        holder.topicView.tvTitle.setText(searchData.getTitle());
        holder.topicView.tvDoc.setText(searchData.getTopic().getDoc());
        holder.topicView.tvPartInNum.setText(searchData.getCount_of_participation() + "");
        String img = searchData.getTopic().getImg();
        if (StringUtils.isEmpty(img)) {
            holder.topicView.ivTopicPicture.setVisibility(View.GONE);
        } else {
            holder.topicView.ivTopicPicture.setVisibility(View.VISIBLE);
            loadImage(holder.topicView.ivTopicPicture, img);
        }
    }

    /**
     * 绑定圈子内动态视图
     */
    private void bindNormalInCircleView(ViewHolder holder, int position) {
        ClassUtils.bindViewsTag(position,
                holder.normalInCircleView.contentCirclePicture,
                holder.normalInCircleView.tvCircleName);
        holder.normalInCircleView.picturesAdapter.parentPosition = position;
        SearchDynamicICNormalData searchData = (SearchDynamicICNormalData) mSearchData.get(position);
        holder.normalInCircleView.tvDoc.setText(searchData.getNormal().getDoc());
        List<String> img = searchData.getNormal().getImg();
        if (img == null || img.size() == 0) {
            holder.normalInCircleView.rvPictures.setVisibility(View.GONE);
        } else {
            holder.normalInCircleView.rvPictures.setVisibility(View.VISIBLE);
            holder.normalInCircleView.picturesAdapter.picturesUlr = img;
            holder.normalInCircleView.picturesAdapter.notifyDataSetChanged();
        }
        CircleInfo circle = searchData.getCircle();
        String pictureUrl = circle.getPictureUrl();
        if (StringUtils.isEmpty(pictureUrl)) {
            holder.normalInCircleView.ivCirclePicture.setImageResource(R.color.default_picture);
        } else {
            loadImage(holder.normalInCircleView.ivCirclePicture, pictureUrl);
        }
        holder.normalInCircleView.tvCircleName.setText(circle.getCircleName());
    }

    /**
     * 绑定普通视图
     */
    private void bindNormalView(ViewHolder holder, int position) {
        holder.normalView.picturesAdapter.parentPosition = position;
        SearchDynamicNormalData searchData = (SearchDynamicNormalData) mSearchData.get(position);
        LogUtils.d(SearchDetailAdapter.class, "normal--->>id=" + searchData.getId());
        holder.normalView.tvDoc.setText(searchData.getNormal().getDoc());
        List<String> img = searchData.getNormal().getImg();
        if (img == null || img.size() == 0) {
            holder.normalView.rvPictures.setVisibility(View.GONE);
        } else {
            holder.normalView.rvPictures.setVisibility(View.VISIBLE);
            holder.normalView.picturesAdapter.picturesUlr = img;
            holder.normalView.picturesAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 绑定专题视图
     */
    private void bindSubjectView(ViewHolder holder, int position) {
        holder.subjectView.picturesAdapter.parentPosition = position;
        SearchSubjectData searchData = (SearchSubjectData) mSearchData.get(position);
        //初始化数据
        String dtype = searchData.getDtype();
        switch (dtype) {
            case Config.SUBJECT_ACTION_COMPLAINT:
                holder.subjectView.tvType.setText("吐槽墙");
                break;
            case Config.SUBJECT_ACTION_FLEAMARKET:
                holder.subjectView.tvType.setText("跳蚤市场");
                break;
            case Config.SUBJECT_ACTION_GRIND:
                holder.subjectView.tvType.setText("求学霸");
                break;
            case Config.SUBJECT_ACTION_LOSTANDFOUND:
                holder.subjectView.tvType.setText("失物招领");
                break;
            case Config.SUBJECT_ACTION_PARTNER:
                holder.subjectView.tvType.setText("寻水友");
                break;
            case Config.SUBJECT_ACTION_VINDICATION:
                holder.subjectView.tvType.setText("表白墙");
                break;
        }
        holder.subjectView.tvTitle.setText(searchData.getTitle());
        holder.subjectView.tvDoc.setText(searchData.getDoc());
        List<String> img = searchData.getImg();
        if (img == null || img.size() == 0) {
            holder.subjectView.rvPictures.setVisibility(View.GONE);
        } else {
            holder.subjectView.rvPictures.setVisibility(View.VISIBLE);
            holder.subjectView.picturesAdapter.picturesUlr = img;
            holder.subjectView.picturesAdapter.notifyDataSetChanged();
        }
        if (searchData.getTag()) {
            holder.subjectView.ivTag.setVisibility(View.VISIBLE);
        } else {
            holder.subjectView.ivTag.setVisibility(View.GONE);
        }
    }

    /**
     * 绑定圈子视图
     */
    private void bindCircleView(ViewHolder holder, int position) {
        ClassUtils.bindViewsTag(position,
                holder.circleView.contentPicture,
                holder.circleView.tvName);
        SearchCircleData searchData = (SearchCircleData) mSearchData.get(position);
        CircleInfo circle = searchData.getCircle();
        String pictureUrl = circle.getPictureUrl();
        if (StringUtils.isEmpty(pictureUrl)) {
            holder.circleView.ivPicture.setImageResource(R.color.default_picture);
        } else {
            loadImage(holder.circleView.ivPicture, pictureUrl);
        }
        holder.circleView.tvName.setText(circle.getCircleName());
        holder.circleView.tvTag.setText(circle.getCircleType());
        holder.circleView.tvSubscribeCount.setText(circle.getOperatorCount() + "");
        holder.circleView.tvBref.setText(circle.getIntroduction());
    }

    /**
     * 绑定用户视图
     */
    private void bindUserView(ViewHolder holder, int position) {
        ClassUtils.bindViewsTag(position,
                holder.userView.contentHeadImg,
                holder.userView.tvAttention);
        SearchUserData searchData = (SearchUserData) mSearchData.get(position);
        UserInfo user = searchData.getUser();
        holder.userView.tvNickName.setText(user.getNickName());
        holder.userView.tvSign.setText(user.getBref_introduction());
        String headImgUrl = user.getAvatar();
        if (!StringUtils.isEmpty(headImgUrl)) {
            loadImage(holder.userView.ivHeadImg, headImgUrl);
        } else {
            holder.userView.ivHeadImg.setImageResource(R.drawable.ic_user_head);
        }
        //设置头像
        if (!user.isHas_attention()) {
            holder.userView.tvAttention.setBackgroundResource(
                    R.drawable.bg_edittext_maintheme_white_round_content);
            holder.userView.tvAttention.setText("关注");
        } else {
            holder.userView.tvAttention.setBackgroundResource(
                    R.drawable.bg_edittext_mainthemegravy_white_round_content);
            holder.userView.tvAttention.setText("已关注");
        }
    }

    /**
     * 加載圖片
     */
    private void loadImage(ImageView imageView, String imageUrl) {
        ImageLoaderFactory.getInstance().displayImage(imageView, imageUrl);
    }

    @Override
    public int getItemCount() {
        return mSearchData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        int viewType;
        View contentView;
        UserView userView;//用户视图
        CircleView circleView;//圈子视图
        SubjectView subjectView;//专题视图
        NormalView normalView;//普通动态视图
        NormalInCircleView normalInCircleView;//圈子内普通视图
        TopicView topicView;//话题动态
        TopicInCircleView topicInCircleView;//圈子话题动态
        ArticleView articleView;//文章动态
        ArticleInCircleView articleInCircleView;//圈子文章动态
    }

    //用户视图控件
    private class UserView {
        private TextView tvNickName;//昵称
        private TextView tvSign;//签名
        private TextView tvAttention;//是否关注
        private CircleImageView ivHeadImg;//头像
        private View contentHeadImg;
    }

    //圈子视图控件
    private class CircleView {
        private TextView tvName;//昵称
        private TextView tvBref;//签名
        private TextView tvSubscribeCount;//是否关注
        private TextView tvTag;//标签
        private ImageView ivPicture;//头像
        private View contentPicture;
    }

    //专题视图控件
    private class SubjectView {
        private TextView tvType;
        private TextView tvTitle;
        private TextView tvDoc;
        private ImageView ivTag;
        private RecyclerView rvPictures;
        private SearchResultPicturesAdapter picturesAdapter;
        private StaggeredGridLayoutManager staggeredGridLayoutManager;
    }

    //普通动态视图控件
    private class NormalView {
        private TextView tvDoc;
        private RecyclerView rvPictures;
        private SearchResultPicturesAdapter picturesAdapter;
        private StaggeredGridLayoutManager staggeredGridLayoutManager;
    }

    //普通动态视图控件
    private class NormalInCircleView {
        private TextView tvCircleName;
        private ImageView ivCirclePicture;
        private View contentCirclePicture;
        private TextView tvDoc;
        private RecyclerView rvPictures;
        private SearchResultPicturesAdapter picturesAdapter;
        private StaggeredGridLayoutManager staggeredGridLayoutManager;
    }

    //话题动态视图控件
    private class TopicView {
        private TextView tvTitle;
        private TextView tvPartInNum;
        private ImageView ivTopicPicture;
        private TextView tvDoc;
        View contentPicture;
    }

    //话题圈子动态视图控件
    private class TopicInCircleView {
        private TextView tvCircleName;
        private ImageView ivCirclePicture;
        private TextView tvTitle;
        private TextView tvPartInNum;
        private ImageView ivTopicPicture;
        private View contentCirclePicture;
        private View contentPicture;
        private TextView tvDoc;
    }

    //文章动态视图控件
    private class ArticleView {
        private TextView tvTitle;
        private ImageView ivArticlePicture;
        private TextView tvDoc;
        View contentPicture;
    }

    //话题圈子动态视图控件
    private class ArticleInCircleView {
        private TextView tvCircleName;
        private ImageView ivCirclePicture;
        private TextView tvTitle;
        private ImageView ivTopicPicture;
        private View contentCirclePicture;
        private View contentPicture;
        private TextView tvDoc;
    }

    /**
     * 搜索详情的图片点击适配器
     */
    private SearchResultPicturesAdapter.OnSearchPicturesItemClickListener
            onSearchPicturesItemClickListener =
            new SearchResultPicturesAdapter.OnSearchPicturesItemClickListener() {
                @Override
                public void onPictureItemClick(int parentPosition, int position) {
                    if (onSearchResultItemClickListener != null) {
                        onSearchResultItemClickListener.
                                onPictureItemClickListener(parentPosition, position);
                    }
                }
            };

    //搜索结果的条目点击监听事件
    public interface OnSearchResultItemClickListener {
        /**
         * 条目被点击
         */
        void onItemClick(int position);

        /**
         * 图片条目点击
         */
        void onPictureItemClickListener(int position, int picturePosition);

        /**
         * 当图片条目被点击
         */
        void onPictureItemClickListener(int position);

        /**
         * 圈子条目点击
         */
        void onCircleItemClick(int position);

        /**
         * 用户关注按钮点击
         */
        void onUserAttentionItemClick(int position);

        /**
         * 用户头像条目点击
         */
        void onUserHeadItemClick(int position);
    }

    /**
     * 搜索条目点击事件
     */
    public void setOnSearchResultItemClickListener(OnSearchResultItemClickListener listener) {
        this.onSearchResultItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (onSearchResultItemClickListener == null) {
            return;
        }
        Bundle bundle = (Bundle) v.getTag();
        if (bundle == null) {
            return;
        }
        int viewId = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_ID);
        int position = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_POSITION);
        switch (viewId) {
            case R.id.searchItemContent:
                onSearchResultItemClickListener.onItemClick(position);
                break;
            case R.id.isrdia_content_picture:
            case R.id.isrdit_content_picture:
            case R.id.isrda_content_picture:
            case R.id.isrdt_content_picture:
                onSearchResultItemClickListener.onPictureItemClickListener(position);
                break;
            case R.id.isrdia_content_circlePicture:
            case R.id.isrdin_content_circlePicture:
            case R.id.isrdit_content_circlePicture:
            case R.id.isrdia_tv_circleName:
            case R.id.isrdin_tv_circleName:
            case R.id.isrdit_tv_circleName:
                onSearchResultItemClickListener.onCircleItemClick(position);
                break;
            case R.id.isu_contentHeadImg:
                onSearchResultItemClickListener.onUserHeadItemClick(position);
                break;
            case R.id.isu_tv_attentionStatus:
                onSearchResultItemClickListener.onUserAttentionItemClick(position);
                break;
        }
    }
}
