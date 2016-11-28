package com.jkb.core.presenter.personCenter.original.myDynamic;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicNormalListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailNormalData;
import com.jkb.core.contract.personCenter.original.myDynamic.MyDynamicNormalContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.personCenter.original.myDynamic.normal.MyDynamicNormalRepertory;
import com.jkb.model.info.UserInfoSingleton;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import retrofit2.Response;

/**
 * 我的普通动态P层
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyDynamicNormalPresenter implements MyDynamicNormalContract.Presenter {

    private static final String TAG = "MyDynamicNormalP";
    private MyDynamicNormalContract.View view;
    private MyDynamicNormalRepertory repertory;

    //data
    private int user_id = -1;
    private boolean isCached = false;
    private List<DynamicDetailNormalData> dynamicDetailNormalDatas;

    //刷新
    private boolean isLoading = false;
    private PageControlEntity pageControl;


    public MyDynamicNormalPresenter(
            @NonNull MyDynamicNormalContract.View view, @NonNull MyDynamicNormalRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        dynamicDetailNormalDatas = new ArrayList<>();
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

        view.setNormalMyDynamic(dynamicDetailNormalDatas);
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        isCached = false;
        isLoading = true;
        dynamicDetailNormalDatas.clear();
        pageControl.setCurrent_page(1);
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
        DynamicDetailNormalData normalData = dynamicDetailNormalDatas.get(position);
        int id = normalData.getId();
        String Authorization = Config.HEADER_BEARER + getUserAuths().getToken();
        view.showLoading("");
        repertory.deleteDynamic(Authorization, id, getUserAuths().getUser_id(),
                new ApiCallback<ApiResponse<DynamicActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<DynamicActionEntity>> response) {
                        if (view.isActive()) {
                            view.showReqResult("删除成功");
                            dynamicDetailNormalDatas.remove(position);
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
        DynamicDetailNormalData normalData = dynamicDetailNormalDatas.get(position);
        int id = normalData.getId();
        view.startDynamicDetailNormal(id);
    }

    @Override
    public void onItemLikeClick(final int position) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请您先去登录再进行操作");
            return;
        }
        DynamicDetailNormalData normalData = dynamicDetailNormalDatas.get(position);
        int id = normalData.getId();
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
                            DynamicDetailNormalData normal = dynamicDetailNormalDatas.get(position);
                            boolean hasFavorite = normal.isHasFavorite();
                            hasFavorite = !hasFavorite;
                            normal.setHasFavorite(hasFavorite);
                            int operation_count = normal.getOperation_count();
                            if (hasFavorite) {
                                operation_count++;
                            } else {
                                operation_count--;
                            }
                            normal.setOperation_count(operation_count);
                            dynamicDetailNormalDatas.set(position, normal);
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
        DynamicDetailNormalData normalData = dynamicDetailNormalDatas.get(position);
        int id = normalData.getId();
        view.startCommentList(id);
    }

    @Override
    public void onItemShareClick(int position) {
        DynamicDetailNormalData normalData = dynamicDetailNormalDatas.get(position);
        String title = normalData.getTitle();
        String doc = normalData.getDoc();
        String picture = (normalData.getImgs() == null || normalData.getImgs().length == 0)
                ? null : normalData.getImgs()[0];
        String url = Config.APP_DOWNLOAD_ADDRESS;
        view.share(title, url, doc, picture, url, "校园菌菌", url);
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
        repertory.getMyDynamicNormal(Authorization, user_id,
                pageControl.getCurrent_page(), myDynamicNormalApiCallback);
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
    private ApiCallback<ApiResponse<DynamicNormalListEntity>> myDynamicNormalApiCallback =
            new ApiCallback<ApiResponse<DynamicNormalListEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<DynamicNormalListEntity>> response) {
                    if (view.isActive()) {
                        handleData(response.body());
                    }
                }

                /**
                 * 解析数据
                 */
                private void handleData(ApiResponse<DynamicNormalListEntity> body) {
                    if (body == null) {
                        bindDataToView();
                        dynamicDetailNormalDatas.clear();
                        return;
                    }
                    handleDynamicData(body.getMsg());
                }

                /**
                 * 解析动态数据
                 */
                private void handleDynamicData(DynamicNormalListEntity msg) {
                    if (msg == null) {
                        bindDataToView();
                        dynamicDetailNormalDatas.clear();
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
                    List<DynamicNormalListEntity.DataBean> data = msg.getData();
                    if (data == null || data.size() == 0) {
                        bindDataToView();
                        return;
                    }
                    for (DynamicNormalListEntity.DataBean bean : data) {
                        DynamicDetailNormalData normalData = handleDynamicNormalData(bean);
                        if (normalData != null) {
                            dynamicDetailNormalDatas.add(normalData);
                        }
                    }
                    bindDataToView();
                }

                /**
                 * 解析普通动态数据
                 */
                private DynamicDetailNormalData handleDynamicNormalData
                (DynamicNormalListEntity.DataBean bean) {
                    if (bean == null) {
                        return null;
                    }
                    DynamicDetailNormalData normalData = new DynamicDetailNormalData();
                    normalData.setId(bean.getDynamic_id());
                    normalData.setTitle(bean.getTitle());
                    //设置内容
                    DynamicNormalListEntity.DataBean.ContentBean content = bean.getContent();
                    if (content == null) {
                        return null;
                    }
                    DynamicNormalListEntity.DataBean.ContentBean.NormalBean normal =
                            content.getNormal();
                    if (normal == null) {
                        return null;
                    }
                    normalData.setDoc(normal.getDoc());
                    if (normal.getImg() != null) {
                        normalData.setImgs(
                                normal.getImg().toArray(new String[normal.getImg().size()]));
                    }
                    //其他数据
                    normalData.setTag(bean.getTag());
                    normalData.setComments_count(bean.getCount_of_comment());
                    normalData.setOperation_count(bean.getCount_of_favorite());
                    normalData.setCreated_at(bean.getDynamic_create_time());
                    if (bean.getHas_favorite() == 0) {
                        normalData.setHasFavorite(false);
                    } else {
                        normalData.setHasFavorite(true);
                    }
                    normalData.setOriginal(isOriginal());
                    return normalData;
                }

                @Override
                public void onError(Response<ApiResponse<DynamicNormalListEntity>> response, String error,
                                    ApiResponse<DynamicNormalListEntity> apiResponse) {
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
