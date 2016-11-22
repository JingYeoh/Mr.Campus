package com.jkb.core.presenter.personCenter.original.mySubject;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.subject.SpecialListEntity;
import com.jkb.api.entity.subject.SubjectActionEntity;
import com.jkb.core.contract.personCenter.original.mySubject.MyOriginalSubjectContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.data.special.SpecialData;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.personCenter.original.mySubject.MyOriginalSubjectRepertory;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * 我的原创专题P层
 * Created by JustKiddingBaby on 2016/11/22.
 */

public class MyOriginalSubjectPresenter implements MyOriginalSubjectContract.Presenter {

    private MyOriginalSubjectContract.View view;
    private MyOriginalSubjectRepertory repertory;

    //data
    private boolean isCached = false;
    private List<SpecialData> mSpecialData;
    private PageControlEntity pageControl;
    private boolean isRefreshing = false;
    private int subjectType = -1;

    public MyOriginalSubjectPresenter(
            MyOriginalSubjectContract.View view, MyOriginalSubjectRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        mSpecialData = new ArrayList<>();
        pageControl = new PageControlEntity();

        this.view.setPresenter(this);
    }

    @Override
    public void bindDataToView() {
        isCached = (mSpecialData.size() > 0);
        isRefreshing = false;
        if (!view.isActive()) {
            return;
        }
        view.dismissLoading();
        view.hideRefreshingView();
        view.setSubjectData(mSpecialData);
    }

    @Override
    public void initSubjectData() {
        subjectType = view.getSubjectType();
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
        mSpecialData.clear();
        pageControl.setCurrent_page(1);
        view.showRefreshingView();
        reqMySubject();
    }

    @Override
    public void onLoadMore() {
        if (pageControl.getCurrent_page() > pageControl.getLast_page()) {
            return;
        }
        isCached = false;
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        reqMySubject();
    }

    @Override
    public void onDeleteItemClick(final int position) {
        view.showDeletedHintSelectorView(new MyOriginalSubjectContract.OnDeleteSelectorItemClickListener() {
            @Override
            public void onDeletedClick() {
                deleteSubject(position);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        SpecialData messages = mSpecialData.get(position);
        if (messages == null) {
            return;
        }
        String targetType = messages.getDtype();
        int targetId = messages.getId();
        switch (targetType) {
            case Config.SUBJECT_TYPE_LOSTANDFOUND:
                view.startSpecialDetailLostAndFound(targetId);
                break;
            case Config.SUBJECT_TYPE_VINDICATOIN:
                view.startSpecialDetailConfession(targetId);
                break;
            case Config.SUBJECT_TYPE_COMPLAINT:
                view.startSpecialDetailTaunted(targetId);
                break;
            case Config.SUBJECT_TYPE_GRIND:
                view.startSpecialDetailWantedSavant(targetId);
                break;
            case Config.SUBJECT_TYPE_PARTNER:
                view.startSpecialDetailWantedPartner(targetId);
                break;
            case Config.SUBJECT_TYPE_FLEAMARKET:
                view.startSpecialDetailFleaMarket(targetId);
                break;
        }
    }

    @Override
    public void onLikeItemClick(final int position) {
        SpecialData specialData = mSpecialData.get(position);
        int id = specialData.getId();
        String authorization = getAuthorization();
        if (StringUtils.isEmpty(authorization)) {
            view.showReqResult("请先登录再进行操作");
            return;
        }
        view.showLoading("");
        repertory.favorite(authorization, getCurrentUserId(), id,
                new ApiCallback<ApiResponse<OperationActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                        if (view.isActive()) {
                            SpecialData specialData = mSpecialData.get(position);
                            boolean hasFavorite = specialData.getHasFavorite();
                            hasFavorite = !hasFavorite;
                            specialData.setHasFavorite(hasFavorite);
                            int count_of_favorite = specialData.getCount_of_favorite();
                            if (hasFavorite) {
                                count_of_favorite++;
                            } else {
                                count_of_favorite--;
                            }
                            specialData.setCount_of_favorite(count_of_favorite);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResponse<OperationActionEntity>> response,
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

    @Override
    public void onCommentItemClick(int position) {
        SpecialData specialData = mSpecialData.get(position);
        int id = specialData.getId();
        view.startCommentList(id);
    }

    @Override
    public void onShareItemClick(int position) {

    }

    @Override
    public void onMarkItemClick(final int position) {
        SpecialData specialData = mSpecialData.get(position);
        int id = specialData.getId();
        String authorization = getAuthorization();
        if (StringUtils.isEmpty(authorization)) {
            view.showReqResult("请先登录再进行操作");
            return;
        }
        view.showLoading("");
        repertory.changeSubjectMarkStatus(authorization, id,
                new ApiCallback<ApiResponse<SubjectActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<SubjectActionEntity>> response) {
                        if (view.isActive()) {
                            SpecialData specialData = mSpecialData.get(position);
                            boolean hasMarked = specialData.getTag();
                            hasMarked = !hasMarked;
                            specialData.setTag(hasMarked);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResponse<SubjectActionEntity>> response,
                                        String error, ApiResponse<SubjectActionEntity> apiResponse) {
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

    @Override
    public void start() {
        initSubjectData();
    }

    /**
     * 得到authorization
     */
    private String getAuthorization() {
        String authorization = null;
        if (LoginContext.getInstance().isLogined()) {
            authorization = Config.HEADER_BEARER +
                    UserInfoSingleton.getInstance().getUserAuths().getToken();
        }
        return authorization;
    }

    /**
     * 得到当前的用户id
     */
    private int getCurrentUserId() {
        int id = -1;
        if (LoginContext.getInstance().isLogined()) {
            id = UserInfoSingleton.getInstance().getUserAuths().getUser_id();
        }
        return id;
    }

    /**
     * 得到专题类型
     */
    private String getSubjectType() {
        switch (subjectType) {
            case SUBJECT_TYPE_FLEAMARKET:
                return Config.SUBJECT_TYPE_FLEAMARKET;
            case SUBJECT_TYPE_LOSTANDFOUND:
                return Config.SUBJECT_TYPE_LOSTANDFOUND;
            case SUBJECT_TYPE_TAUNTED:
                return Config.SUBJECT_TYPE_COMPLAINT;
            case SUBJECT_TYPE_WANTED_PARTNER:
                return Config.SUBJECT_TYPE_PARTNER;
            case SUBJECT_TYPE_WANTED_SAVANT:
                return Config.SUBJECT_TYPE_GRIND;
            case SUBJECY_TYPE_CONFESSION:
                return Config.SUBJECT_TYPE_VINDICATOIN;
            default:
                return null;
        }
    }

    /**
     * 请求我的专题接口
     */
    private void reqMySubject() {
        String authorization = getAuthorization();
        if (StringUtils.isEmpty(authorization)) {
            mSpecialData.clear();
            view.showReqResult("请先登录再进行操作");
            bindDataToView();
        }
        String type = getSubjectType();
        if (StringUtils.isEmpty(type)) {
            view.showReqResult("无该类型专题");
            bindDataToView();
            return;
        }
        LogUtils.d(MyOriginalSubjectPresenter.class, "reqMySubject");
        repertory.getMySubject(authorization, pageControl.getCurrent_page(), type,
                subjectApiCallback);
    }

    /**
     * 删除专题
     */
    private void deleteSubject(final int position) {
        String authorization = getAuthorization();
        if (StringUtils.isEmpty(authorization)) {
            view.showReqResult("请先登录再进行操作");
            return;
        }
        SpecialData specialData = mSpecialData.get(position);
        int id = specialData.getId();
        view.showLoading("");
        repertory.deleteDynamic(authorization, id, getCurrentUserId(),
                new ApiCallback<ApiResponse<DynamicActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<DynamicActionEntity>> response) {
                        if (view.isActive()) {
                            mSpecialData.remove(position);
                            view.showReqResult("操作成功");
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResponse<DynamicActionEntity>> response,
                                        String error, ApiResponse<DynamicActionEntity> apiResponse) {
                        if (view.isActive()) {
                            view.showReqResult(error);
                            view.dismissLoading();
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (view.isActive()) {
                            view.showReqResult("请求失败");
                            view.dismissLoading();
                            bindDataToView();
                        }
                    }
                });
    }

    /**
     * 专题回调
     */
    private ApiCallback<ApiResponse<SpecialListEntity>> subjectApiCallback =
            new ApiCallback<ApiResponse<SpecialListEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<SpecialListEntity>> response) {
                    if (view.isActive()) {
                        handleData(response.body().getMsg());
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(SpecialListEntity msg) {
                    if (msg == null) {
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

                    //处理数据
                    List<SpecialListEntity.DataBean> data = msg.getData();
                    if (data == null || data.size() == 0) {
                        bindDataToView();
                        return;
                    }
                    for (SpecialListEntity.DataBean dataBean : data) {
                        changeReqDataToModel(dataBean);
                    }
                    bindDataToView();
                }

                /**
                 * 转换请求数据为model
                 */
                private void changeReqDataToModel(SpecialListEntity.DataBean dataBean) {
                    if (dataBean == null) {
                        return;
                    }
                    SpecialData special = new SpecialData(dataBean).getSpecial();
                    if (special != null) {
                        mSpecialData.add(special);
                    }
                }

                @Override
                public void onError(Response<ApiResponse<SpecialListEntity>> response,
                                    String error, ApiResponse<SpecialListEntity> apiResponse) {
                    if (view.isActive()) {
                        bindDataToView();
                        view.dismissLoading();
                        view.showReqResult(error);
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("请求失败");
                        view.dismissLoading();
                        bindDataToView();
                    }
                }
            };
}
