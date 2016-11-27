package com.jkb.core.presenter.search.detail;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.search.SearchEntity;
import com.jkb.core.contract.search.SearchDetailContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.data.info.dynamic.content.DynamicContentArticleInfo;
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
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.search.detail.SearchDetailRepertory;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * 搜索详情的P层
 * Created by JustKiddingBaby on 2016/11/26.
 */

public class SearchDetailPresenter implements SearchDetailContract.Presenter {

    private SearchDetailContract.View view;
    private SearchDetailRepertory repertory;

    //data
    private int searchType;
    private String keyWords;
    private SearchDetailData mSearchDetailData;
    private PageControlEntity pageControl;
    private boolean isRefreshing = false;
    private boolean isCached = false;

    public SearchDetailPresenter(
            SearchDetailContract.View view, SearchDetailRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        mSearchDetailData = new SearchDetailData();
        mSearchDetailData.setSearchData(new ArrayList<SearchData>());
        pageControl = new PageControlEntity();

        this.view.setPresenter(this);
    }

    @Override
    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    @Override
    public void bindDataToView() {
        LogUtils.d(SearchDetailPresenter.class, "bindDataToView");
        isRefreshing = false;
        isCached = mSearchDetailData.getSearchData().size() > 0;
        if (!view.isActive()) {
            return;
        }
        view.hideRefreshingView();
        view.dismissLoading();
        //设置数据
        if (searchType == SEARCH_TYPE_ALL) {//只有总数的时候设置全部数据
            view.setSearchResultCount(
                    mSearchDetailData.getAllCount(), mSearchDetailData.getUserCount(),
                    mSearchDetailData.getCircleCount(), mSearchDetailData.getDynamicCount(),
                    mSearchDetailData.getSubjectCount());
        }
        if (mSearchDetailData.getSearchData().size() == 0) {
            view.showNonResultView();
        } else {
            view.setSearchResult(mSearchDetailData.getSearchData());
        }
    }

    @Override
    public void initSearchData() {
        if (isCached) {
            bindDataToView();
        } else {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        isCached = false;
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        pageControl.setCurrent_page(1);
        mSearchDetailData.getSearchData().clear();
        view.showRefreshingView();
        reqSearchData();
    }

    @Override
    public void onLoadMore() {
        if (isRefreshing) {
            return;
        }
        if (pageControl.getCurrent_page() >= pageControl.getLast_page()) {
            return;
        }
        isCached = false;
        isRefreshing = true;
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        reqSearchData();
    }

    @Override
    public void updateKeyWords(String keyWords) {
        this.keyWords = keyWords;
        isCached = false;
        onRefresh();
    }

    @Override
    public void onSearchItemClick(int position) {
        setOnSearchTypeCallBack(position, new OnSearchTypeCallBack() {
            @Override
            public void onUserType(SearchUserData searchData) {
                int id = searchData.getUser().getId();
                view.startPersonCenter(id);
            }

            @Override
            public void onCircleType(SearchCircleData searchData) {
                int circleId = searchData.getCircle().getCircleId();
                view.startCircleIndex(circleId);
            }

            @Override
            public void onDynamicType(SearchDynamicData searchData) {
                handleDynamicSearchItemClick(searchData);
            }

            @Override
            public void onDynamicInCircleType(SearchDynamicInCircleData searchData) {
                handleDynamicICSearchItemClick(searchData);
            }

            @Override
            public void onSubjectType(SearchSubjectData searchData) {
                handleSubjectSearchItemClick(searchData);
            }
        });
    }

    //处理专题的条目点击跳转
    private void handleSubjectSearchItemClick(SearchSubjectData searchData) {
        final int id = searchData.getId();
        setOnSearchSubjectTypeCallBack(searchData, new OnSearchSubjectTypeCallBack() {
            @Override
            public void onConfessionType(SearchSubjectData subjectData) {
                view.startSubjectConfession(id);
            }

            @Override
            public void onTauntedType(SearchSubjectData subjectData) {
                view.startSubjectTaunted(id);
            }

            @Override
            public void onLostAndFoundType(SearchSubjectData subjectData) {
                view.startSubjectLostAndFound(id);
            }

            @Override
            public void onFleaMarketType(SearchSubjectData subjectData) {
                view.startSubjectFleaMarket(id);
            }

            @Override
            public void onPartnerType(SearchSubjectData subjectData) {
                view.startSubjectPartner(id);
            }

            @Override
            public void onSavantType(SearchSubjectData subjectData) {
                view.startSubjectWantedSavant(id);
            }
        });
    }

    //处理圈子内动态条目点击跳转
    private void handleDynamicICSearchItemClick(SearchDynamicInCircleData searchData) {
        final int id = searchData.getId();
        setOnSearchDynamicICTypeCallBack(searchData, new OnSearchDynamicICTypeCallBack() {
            @Override
            public void onDynamicICArticleType(SearchDynamicICArticleData searchData) {
                view.startArticleDynamic(id);
            }

            @Override
            public void onDynamicICNormalType(SearchDynamicICNormalData searchData) {
                view.startNormalDynamic(id);
            }

            @Override
            public void onDynamicICTopicType(SearchDynamicICTopicData searchData) {
                view.startTopicDynamic(id);
            }
        });
    }

    //处理动态条目点击跳转
    private void handleDynamicSearchItemClick(SearchDynamicData searchData) {
        final int id = searchData.getId();
        setOnSearchDynamicTypeCallBack(searchData, new OnSearchDynamicTypeCallBack() {
            @Override
            public void onDynamicArticleType(SearchDynamicArticleData searchData) {
                view.startArticleDynamic(id);
            }

            @Override
            public void onDynamicNormalType(SearchDynamicNormalData searchData) {
                view.startNormalDynamic(id);
            }

            @Override
            public void onDynamicTopicType(SearchDynamicTopicData searchData) {
                view.startTopicDynamic(id);
            }
        });
    }

    @Override
    public void onPictureItemClick(int position, final int childPosition) {
        setOnSearchTypeCallBack(position, new OnSearchTypeCallBack() {
            @Override
            public void onUserType(SearchUserData searchData) {
            }

            @Override
            public void onCircleType(SearchCircleData searchData) {
            }

            @Override
            public void onDynamicType(SearchDynamicData searchData) {
                handleDynamicPicture(searchData, childPosition);
            }

            @Override
            public void onDynamicInCircleType(SearchDynamicInCircleData searchData) {
                handleDynamicICPicture(searchData, childPosition);
            }

            @Override
            public void onSubjectType(SearchSubjectData searchData) {
                List<String> img = searchData.getImg();
                view.showPicturesBrowserView((ArrayList<String>) img, childPosition);
            }
        });
    }

    //处理圈子内动态图片显示
    private void handleDynamicICPicture(
            SearchDynamicInCircleData searchData, final int childPosition) {
        setOnSearchDynamicICTypeCallBack(searchData, new OnSearchDynamicICTypeCallBack() {
            @Override
            public void onDynamicICArticleType(SearchDynamicICArticleData searchData) {
            }

            @Override
            public void onDynamicICNormalType(SearchDynamicICNormalData searchData) {
                List<String> img = searchData.getNormal().getImg();
                view.showPicturesBrowserView((ArrayList<String>) img, childPosition);
            }

            @Override
            public void onDynamicICTopicType(SearchDynamicICTopicData searchData) {
            }
        });
    }

    //处理动态图片显示
    private void handleDynamicPicture(SearchDynamicData searchData, final int childPosition) {
        setOnSearchDynamicTypeCallBack(searchData, new OnSearchDynamicTypeCallBack() {
            @Override
            public void onDynamicArticleType(SearchDynamicArticleData searchData) {
            }

            @Override
            public void onDynamicNormalType(SearchDynamicNormalData searchData) {
                List<String> img = searchData.getNormal().getImg();
                view.showPicturesBrowserView((ArrayList<String>) img, childPosition);
            }

            @Override
            public void onDynamicTopicType(SearchDynamicTopicData searchData) {
            }
        });
    }

    @Override
    public void onPictureItemClick(int position) {
        setOnSearchTypeCallBack(position, new OnSearchTypeCallBack() {
            @Override
            public void onUserType(SearchUserData searchData) {
            }

            @Override
            public void onCircleType(SearchCircleData searchData) {
            }

            @Override
            public void onDynamicType(SearchDynamicData searchData) {
                handleDynamicSinglePicture(searchData);
            }

            @Override
            public void onDynamicInCircleType(SearchDynamicInCircleData searchData) {
                handleDynamicICSinglePicture(searchData);
            }

            @Override
            public void onSubjectType(SearchSubjectData searchData) {
            }
        });
    }

    //处理单张图片的圈子动态显示
    private void handleDynamicICSinglePicture(SearchDynamicInCircleData searchData) {
        setOnSearchDynamicICTypeCallBack(searchData, new OnSearchDynamicICTypeCallBack() {
            @Override
            public void onDynamicICArticleType(SearchDynamicICArticleData searchData) {
                List<DynamicContentArticleInfo.Article> articles =
                        searchData.getArticle().getArticle();
                ArrayList<String> imgs = new ArrayList<>();
                for (int i = 0; i < articles.size(); i++) {
                    DynamicContentArticleInfo.Article article = articles.get(i);
                    if (!StringUtils.isEmpty(article.getImg())) {
                        imgs.add(article.getImg());
                        break;
                    }
                }
                view.showPicturesBrowserView(imgs, 0);
            }

            @Override
            public void onDynamicICNormalType(SearchDynamicICNormalData searchData) {
            }

            @Override
            public void onDynamicICTopicType(SearchDynamicICTopicData searchData) {
                String img = searchData.getTopic().getImg();
                ArrayList<String> imgs = new ArrayList<>();
                imgs.add(img);
                view.showPicturesBrowserView(imgs, 0);
            }
        });
    }

    //处理单张图片的动态显示
    private void handleDynamicSinglePicture(SearchDynamicData searchData) {
        setOnSearchDynamicTypeCallBack(searchData, new OnSearchDynamicTypeCallBack() {
            @Override
            public void onDynamicArticleType(SearchDynamicArticleData searchData) {
                List<DynamicContentArticleInfo.Article> articles =
                        searchData.getArticle().getArticle();
                ArrayList<String> imgs = new ArrayList<>();
                for (int i = 0; i < articles.size(); i++) {
                    DynamicContentArticleInfo.Article article = articles.get(i);
                    if (!StringUtils.isEmpty(article.getImg())) {
                        imgs.add(article.getImg());
                        break;
                    }
                }
                view.showPicturesBrowserView(imgs, 0);
            }

            @Override
            public void onDynamicNormalType(SearchDynamicNormalData searchData) {
            }

            @Override
            public void onDynamicTopicType(SearchDynamicTopicData searchData) {
                String img = searchData.getTopic().getImg();
                ArrayList<String> imgs = new ArrayList<>();
                imgs.add(img);
                view.showPicturesBrowserView(imgs, 0);
            }
        });
    }

    @Override
    public void onUserHeadImgClick(int position) {
        SearchData searchData = mSearchDetailData.getSearchData().get(position);
        if (searchData instanceof SearchUserData) {
            int id = ((SearchUserData) searchData).getUser().getId();
            view.startPersonCenter(id);
        }
    }

    @Override
    public void onCircleItemClick(int position) {
        setOnSearchTypeCallBack(position, new OnSearchTypeCallBack() {
            @Override
            public void onUserType(SearchUserData searchData) {
            }

            @Override
            public void onCircleType(SearchCircleData searchData) {
                int circleId = searchData.getCircle().getCircleId();
                view.startCircleIndex(circleId);
            }

            @Override
            public void onDynamicType(SearchDynamicData searchData) {
            }

            @Override
            public void onDynamicInCircleType(SearchDynamicInCircleData searchData) {
                int circleId = searchData.getCircle().getCircleId();
                view.startCircleIndex(circleId);
            }

            @Override
            public void onSubjectType(SearchSubjectData searchData) {
            }
        });
    }

    @Override
    public void onUserAttentionItemClick(int position) {
        SearchData searchData = mSearchDetailData.getSearchData().get(position);
        if (searchData instanceof SearchUserData) {
            reqAttentionUser(position);
        }
    }

    @Override
    public void start() {
        keyWords = view.getSearchKeyWords();
        initSearchData();
    }

    /**
     * 请求搜索数据
     */
    private void reqSearchData() {
        keyWords = view.getSearchKeyWords();
        if (StringUtils.isEmpty(keyWords)) {
            mSearchDetailData.getSearchData().clear();
            bindDataToView();
            return;
        }
        LogUtils.d(SearchDetailPresenter.class, "reqSearchData");
        String authorization = getAuthorization();
        int page = pageControl.getCurrent_page();
        switch (searchType) {
            case SEARCH_TYPE_ALL:
                repertory.searchAllByKeyWords(authorization, keyWords, page, searchApiCallback);
                break;
            case SEARCH_TYPE_CIRCLE:
                repertory.searchCircleByKeyWords(authorization, keyWords, page, searchApiCallback);
                break;
            case SEARCH_TYPE_DYNAMIC:
                repertory.searchDynamicByKeyWords(authorization, keyWords, page, searchApiCallback);
                break;
            case SEARCH_TYPE_SUBJECT:
                repertory.searchSubjectByKeyWords(authorization, keyWords, page, searchApiCallback);
                break;
            case SEARCH_TYPE_USER:
                repertory.searchUserByKeyWords(authorization, keyWords, page, searchApiCallback);
                break;
            case SEARCH_TYPE_DYNAMICINCIRCLE:
                repertory.searchDynamicInCircleByKeyWords(
                        authorization, keyWords, page, searchApiCallback);
                break;
            default:
                bindDataToView();
                break;
        }
    }

    /**
     * 请求关注用户接口
     */
    private void reqAttentionUser(final int position) {
        String authorization = getAuthorization();
        if (StringUtils.isEmpty(authorization)) {
            view.showReqResult("请先登录再进行操作");
            return;
        }
        SearchUserData searchData = (SearchUserData) mSearchDetailData.getSearchData().get(position);
        int id = searchData.getUser().getId();
        view.showLoading("");
        repertory.payAttentionOrCancel(authorization, getCurrentUserId(), id,
                new ApiCallback<ApiResponse<OperationActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                        if (view.isActive()) {
                            view.showReqResult("操作成功");
                            SearchUserData searchData = (SearchUserData)
                                    mSearchDetailData.getSearchData().get(position);
                            boolean has_attention = searchData.getUser().isHas_attention();
                            has_attention = !has_attention;
                            searchData.getUser().setHas_attention(has_attention);
                            mSearchDetailData.getSearchData().set(position, searchData);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(
                            Response<ApiResponse<OperationActionEntity>> response,
                            String error, ApiResponse<OperationActionEntity> apiResponse) {
                        if (view.isActive()) {
                            view.showReqResult(error);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (view.isActive()) {
                            view.showReqResult("请求失败");
                            bindDataToView();
                        }
                    }
                });
    }

    /**
     * 得到authorization
     */
    private String getAuthorization() {
        String authorization = null;
        if (LoginContext.getInstance().isLogined()) {
            authorization = Config.HEADER_BEARER
                    + UserInfoSingleton.getInstance().getUserAuths().getToken();
        }
        return authorization;
    }

    /**
     * 得到当前用户id
     */
    private int getCurrentUserId() {
        int userId = -1;
        if (LoginContext.getInstance().isLogined()) {
            userId = UserInfoSingleton.getInstance().getUserAuths().getUser_id();
        }
        return userId;
    }

    /**
     * 搜索的回调接口
     */
    private ApiCallback<ApiResponse<SearchEntity>> searchApiCallback =
            new ApiCallback<ApiResponse<SearchEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<SearchEntity>> response) {
                    if (view.isActive()) {
                        handleData(response.body().getMsg());
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(SearchEntity msg) {
                    LogUtils.d(SearchDetailPresenter.class, "handleData");
                    if (msg == null) {
                        mSearchDetailData.getSearchData().clear();
                        bindDataToView();
                        return;
                    }
                    //处理分页数据
                    pageControl.setTotal(msg.getTotal());
                    pageControl.setPer_page(msg.getPer_page());
                    pageControl.setCurrent_page(msg.getCurrent_page());
                    pageControl.setLast_page(msg.getLast_page());
                    pageControl.setNext_page_url(msg.getNext_page_url());
                    pageControl.setPrev_page_url(msg.getPrev_page_url());
                    pageControl.setFrom(msg.getFrom());
                    pageControl.setTo(msg.getTo());
                    //处理计算总数数据
                    handleSearchCount(msg);
                    //处理数据
                    handleSearchData(msg);
                    bindDataToView();
                }

                /**
                 * 处理搜索的数目
                 */
                private void handleSearchCount(SearchEntity msg) {
                    if (searchType != SEARCH_TYPE_ALL) {
                        return;
                    }
                    SearchEntity.CountBean count = msg.getCount();
                    if (count == null) {
                        bindDataToView();
                        return;
                    }
                    mSearchDetailData.setAllCount(count.getAllCount());
                    mSearchDetailData.setDynamicCount(count.getDynamicCount());
                    mSearchDetailData.setUserCount(count.getUserCount());
                    mSearchDetailData.setSubjectCount(count.getSubjectCount());
                    mSearchDetailData.setCircleCount(count.getCircleCount());
                }

                /**
                 * 处理搜索结果
                 */
                private void handleSearchData(SearchEntity msg) {
                    LogUtils.d(SearchDetailPresenter.class, "handleSearchData");
                    List<SearchEntity.DataBean> data = msg.getData();
                    if (data == null || data.size() == 0) {
                        mSearchDetailData.getSearchData().clear();
                        bindDataToView();
                        return;
                    }
                    for (SearchEntity.DataBean bean : data) {
                        SearchData searchData = new SearchData(bean).getSearchData();
                        if (searchData != null) {
                            mSearchDetailData.getSearchData().add(searchData);
                        }
                    }
                }

                @Override
                public void onError(Response<ApiResponse<SearchEntity>> response, String error,
                                    ApiResponse<SearchEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult(error);
                        bindDataToView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("请求失败");
                        bindDataToView();
                    }
                }
            };

    /**
     * 设置搜索类型回调
     */
    private void setOnSearchTypeCallBack(int position, OnSearchTypeCallBack callBack) {
        if (callBack == null) {
            return;
        }
        SearchData searchData = mSearchDetailData.getSearchData().get(position);
        if (searchData instanceof SearchUserData) {
            callBack.onUserType((SearchUserData) searchData);
        } else if (searchData instanceof SearchCircleData) {
            callBack.onCircleType((SearchCircleData) searchData);
        } else if (searchData instanceof SearchDynamicData) {
            callBack.onDynamicType((SearchDynamicData) searchData);
        } else if (searchData instanceof SearchDynamicInCircleData) {
            callBack.onDynamicInCircleType((SearchDynamicInCircleData) searchData);
        } else if (searchData instanceof SearchSubjectData) {
            callBack.onSubjectType((SearchSubjectData) searchData);
        }
    }

    /**
     * 设置动态类型回调
     */
    private void setOnSearchDynamicICTypeCallBack(
            SearchDynamicInCircleData searchData, OnSearchDynamicICTypeCallBack callBack) {
        if (callBack == null) {
            return;
        }
        if (searchData instanceof SearchDynamicICArticleData) {
            callBack.onDynamicICArticleType((SearchDynamicICArticleData) searchData);
        } else if (searchData instanceof SearchDynamicICNormalData) {
            callBack.onDynamicICNormalType((SearchDynamicICNormalData) searchData);
        } else if (searchData instanceof SearchDynamicICTopicData) {
            callBack.onDynamicICTopicType((SearchDynamicICTopicData) searchData);
        }
    }

    /**
     * 设置动态类型回调
     */
    private void setOnSearchDynamicTypeCallBack(
            SearchDynamicData dynamicData, OnSearchDynamicTypeCallBack callBack) {
        if (callBack == null) {
            return;
        }
        if (dynamicData instanceof SearchDynamicArticleData) {
            callBack.onDynamicArticleType((SearchDynamicArticleData) dynamicData);
        } else if (dynamicData instanceof SearchDynamicNormalData) {
            callBack.onDynamicNormalType((SearchDynamicNormalData) dynamicData);
        } else if (dynamicData instanceof SearchDynamicTopicData) {
            callBack.onDynamicTopicType((SearchDynamicTopicData) dynamicData);
        }
    }

    /**
     * 搜索专题类型回调
     */
    private void setOnSearchSubjectTypeCallBack(
            SearchSubjectData searchData, OnSearchSubjectTypeCallBack callback) {
        if (callback == null) {
            return;
        }
        String dtype = searchData.getDtype();
        switch (dtype) {
            case Config.SUBJECT_TYPE_COMPLAINT:
                callback.onTauntedType(searchData);
                break;
            case Config.SUBJECT_TYPE_FLEAMARKET:
                callback.onFleaMarketType(searchData);
                break;
            case Config.SUBJECT_TYPE_GRIND:
                callback.onSavantType(searchData);
                break;
            case Config.SUBJECT_TYPE_LOSTANDFOUND:
                callback.onLostAndFoundType(searchData);
                break;
            case Config.SUBJECT_TYPE_PARTNER:
                callback.onPartnerType(searchData);
                break;
            case Config.SUBJECT_TYPE_VINDICATOIN:
                callback.onConfessionType(searchData);
                break;
        }
    }

    /**
     * 搜索的类型回调
     */
    private interface OnSearchTypeCallBack {
        //用户搜索类型
        void onUserType(SearchUserData searchData);

        //圈子搜索类型
        void onCircleType(SearchCircleData searchData);

        //文章动态类型
        void onDynamicType(SearchDynamicData searchData);

        //普通动态类型
        void onDynamicInCircleType(SearchDynamicInCircleData searchData);

        //话题动态类型
        void onSubjectType(SearchSubjectData searchData);
    }

    /**
     * 搜索的动态类型回调
     */
    private interface OnSearchDynamicTypeCallBack {
        //文章动态类型
        void onDynamicArticleType(SearchDynamicArticleData searchData);

        //普通动态类型
        void onDynamicNormalType(SearchDynamicNormalData searchData);

        //话题动态类型
        void onDynamicTopicType(SearchDynamicTopicData searchData);
    }

    /**
     * 搜索的圈内动态类型回调
     */
    private interface OnSearchDynamicICTypeCallBack {
        //文章动态类型
        void onDynamicICArticleType(SearchDynamicICArticleData searchData);

        //普通动态类型
        void onDynamicICNormalType(SearchDynamicICNormalData searchData);

        //话题动态类型
        void onDynamicICTopicType(SearchDynamicICTopicData searchData);
    }

    /**
     * 搜索的专题类型回调
     */
    private interface OnSearchSubjectTypeCallBack {

        void onConfessionType(SearchSubjectData subjectData);

        void onTauntedType(SearchSubjectData subjectData);

        void onLostAndFoundType(SearchSubjectData subjectData);

        void onFleaMarketType(SearchSubjectData subjectData);

        void onPartnerType(SearchSubjectData subjectData);

        void onSavantType(SearchSubjectData subjectData);
    }
}
