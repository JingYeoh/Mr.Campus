package com.jkb.core.presenter.personCenter.original.myDynamic;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicTopicListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailTopicData;
import com.jkb.core.contract.personCenter.original.myDynamic.MyDynamicTopicContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.personCenter.original.myDynamic.topic.MyDynamicTopicRepertory;
import com.jkb.model.info.UserInfoSingleton;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import retrofit2.Response;

/**
 * 我的普通动态P层
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyDynamicTopicPresenter implements MyDynamicTopicContract.Presenter {

    private static final String TAG = "MyDynamicNormalP";
    private MyDynamicTopicContract.View view;
    private MyDynamicTopicRepertory repertory;

    //data
    private int user_id = -1;
    private boolean isCached = false;
    private List<DynamicDetailTopicData> dynamicDetailTopicDatas;

    //刷新
    private boolean isLoading = false;
    private PageControlEntity pageControl;


    public MyDynamicTopicPresenter(
            @NonNull MyDynamicTopicContract.View view, @NonNull MyDynamicTopicRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        dynamicDetailTopicDatas = new ArrayList<>();
        pageControl = new PageControlEntity();

        this.view.setPresenter(this);
    }

    @Override
    public void initUser_id() {
        user_id = view.bindUser_id();
    }

    @Override
    public void initDynamic() {
        if (isCached) {
            bindDataToView();
        } else {
            onRefresh();
        }
    }

    @Override
    public void bindDataToView() {
        isCached = true;
        isLoading = false;
        if (!view.isActive()) {
            return;
        }
        view.dismissLoading();
        view.hideRefreshingView();

        view.setTopicMyDynamic(dynamicDetailTopicDatas);
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        isCached = false;
        isLoading = true;
        dynamicDetailTopicDatas.clear();
        pageControl.setCurrent_page(0);
        reqMyDynamicNormal();
    }


    @Override
    public void onLoadMore() {
        if (isLoading) {
            return;
        }
        if (pageControl.getCurrent_page() >= pageControl.getLast_page()) {
            return;
        }
        isCached = false;
        isLoading = true;
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        reqMyDynamicNormal();
    }

    @Override
    public void onItemDeleteClick(final int position) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请您先去登录再进行操作");
            return;
        }
        DynamicDetailTopicData topicData = dynamicDetailTopicDatas.get(position);
        int id = topicData.getId();
        String Authorization = Config.HEADER_BEARER + getUserAuths().getToken();
        view.showLoading("");
        repertory.deleteDynamic(Authorization, id, getUserAuths().getUser_id(),
                new ApiCallback<ApiResponse<DynamicActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<DynamicActionEntity>> response) {
                        if (view.isActive()) {
                            view.showReqResult("删除成功");
                            dynamicDetailTopicDatas.remove(position);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResponse<DynamicActionEntity>> response, String error,
                                        ApiResponse<DynamicActionEntity> apiResponse) {
                        if (view.isActive()) {
//                            view.showReqResult("删除失败");
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
    public void onItemDynamicClick(int position) {
        DynamicDetailTopicData topicData = dynamicDetailTopicDatas.get(position);
        int id = topicData.getId();
        view.startDynamicDetailTopic(id);
    }

    @Override
    public void onItemLikeClick(final int position) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请您先去登录再进行操作");
            return;
        }
        DynamicDetailTopicData topicData = dynamicDetailTopicDatas.get(position);
        int id = topicData.getId();
        String Authorization = Config.HEADER_BEARER + getUserAuths().getToken();
        view.showLoading("");
        //局部刷新
        repertory.favorite(Authorization, getUserAuths().getUser_id(), id,
                new ApiCallback<ApiResponse<OperationActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                        if (view.isActive()) {
                            view.showReqResult("操作成功");
                            //局部刷新
                            DynamicDetailTopicData topic = dynamicDetailTopicDatas.get(position);
                            boolean hasFavorite = topic.isHasFavorite();
                            hasFavorite = !hasFavorite;
                            topic.setHasFavorite(hasFavorite);
                            int operation_count = topic.getOperation_count();
                            if (hasFavorite) {
                                operation_count++;
                            } else {
                                operation_count--;
                            }
                            topic.setOperation_count(operation_count);
                            dynamicDetailTopicDatas.set(position, topic);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResponse<OperationActionEntity>> response,
                                        String error, ApiResponse<OperationActionEntity> apiResponse) {
                        if (view.isActive()) {
                            bindDataToView();
                            view.showReqResult(error);
                        }
                    }

                    @Override
                    public void onFail() {
                        if (view.isActive()) {
                            bindDataToView();
                            view.showReqResult("请求失败");
                        }
                    }
                });
    }

    @Override
    public void onItemCommentClick(int position) {
        DynamicDetailTopicData topicData = dynamicDetailTopicDatas.get(position);
        int id = topicData.getId();
        view.startCommentList(id);
    }

    @Override
    public void start() {
        initUser_id();
        initDynamic();
    }

    /**
     * 请求我的普通动态数据接口
     */
    private void reqMyDynamicNormal() {
        String Authorization;
        if (LoginContext.getInstance().isLogined()) {
            Authorization = Config.HEADER_BEARER + getUserAuths().getToken();
        } else {
            Authorization = null;
        }
        Log.d(TAG, "user_id=" + user_id);
        repertory.getMyDynamicTopic(Authorization, user_id,
                pageControl.getCurrent_page(), myDynamicTopicApiCallback);
    }

    /**
     * 得到用戶信息
     */
    private UserAuths getUserAuths() {
        UserAuths userAuths = UserInfoSingleton.getInstance().getUserAuths();
        return userAuths;
    }

    /**
     * 是否原创
     *
     * @return 判断是否原创
     */
    private boolean isOriginal() {
        boolean isOriginal;
        if (LoginContext.getInstance().isLogined()) {
            if (getUserAuths().getUser_id() == user_id) {
                isOriginal = true;
            } else {
                isOriginal = false;
            }
        } else {
            isOriginal = false;
        }
        return isOriginal;
    }

    /**
     * 我的普通动态接口回调
     */
    private ApiCallback<ApiResponse<DynamicTopicListEntity>> myDynamicTopicApiCallback =
            new ApiCallback<ApiResponse<DynamicTopicListEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<DynamicTopicListEntity>> response) {
                    if (view.isActive()) {
                        handleData(response.body());
                    }
                }

                /**
                 * 解析数据
                 */
                private void handleData(ApiResponse<DynamicTopicListEntity> body) {
                    if (body == null) {
                        bindDataToView();
                        dynamicDetailTopicDatas.clear();
                        return;
                    }
                    handleDynamicData(body.getMsg());
                }

                /**
                 * 解析动态数据
                 */
                private void handleDynamicData(DynamicTopicListEntity msg) {
                    if (msg == null) {
                        bindDataToView();
                        dynamicDetailTopicDatas.clear();
                        return;
                    }

                    //设置页码控制器
                    pageControl.setTotal(msg.getTotal());
                    pageControl.setPer_page(msg.getPer_page());
                    pageControl.setCurrent_page(msg.getCurrent_page());
                    pageControl.setLast_page(msg.getLast_page());
                    pageControl.setNext_page_url(msg.getNext_page_url());
                    pageControl.setPrev_page_url(msg.getPrev_page_url());
                    pageControl.setFrom(msg.getFrom());
                    pageControl.setTo(msg.getTo());

                    //解析具体的数据
                    List<DynamicTopicListEntity.DataBean> data = msg.getData();
                    if (data == null || data.size() == 0) {
                        bindDataToView();
                        return;
                    }
                    for (DynamicTopicListEntity.DataBean bean : data) {
                        DynamicDetailTopicData topicData = handleDynamicNormalData(bean);
                        if (topicData != null) {
                            dynamicDetailTopicDatas.add(topicData);
                        }
                    }
                    bindDataToView();
                }

                /**
                 * 解析普通动态数据
                 */
                private DynamicDetailTopicData handleDynamicNormalData
                (DynamicTopicListEntity.DataBean bean) {
                    if (bean == null) {
                        return null;
                    }
                    DynamicDetailTopicData topicData = new DynamicDetailTopicData();
                    topicData.setId(bean.getDynamic_id());
                    topicData.setTitle(bean.getTitle());
                    //设置内容
                    DynamicTopicListEntity.DataBean.ContentBean content = bean.getContent();
                    if (content == null) {
                        return null;
                    }
                    DynamicTopicListEntity.DataBean.ContentBean.TopicBean topicBean =
                            content.getTopic();
                    if (topicBean == null) {
                        return null;
                    }
                    topicData.setDoc(topicBean.getDoc());
                    topicData.setImg(topicBean.getImg());
                    //其他数据
                    topicData.setTag(bean.getTag());
                    topicData.setComments_count(bean.getCount_of_comment());
                    topicData.setOperation_count(bean.getCount_of_favorite());
                    topicData.setPartIn_count(bean.getCount_of_participation());
                    topicData.setCreated_at(bean.getDynamic_create_time());
                    if (bean.getHas_favorite() == 0) {
                        topicData.setHasFavorite(false);
                    } else {
                        topicData.setHasFavorite(true);
                    }
                    topicData.setOriginal(isOriginal());
                    return topicData;
                }

                @Override
                public void onError(Response<ApiResponse<DynamicTopicListEntity>> response, String error,
                                    ApiResponse<DynamicTopicListEntity> apiResponse) {
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
}
