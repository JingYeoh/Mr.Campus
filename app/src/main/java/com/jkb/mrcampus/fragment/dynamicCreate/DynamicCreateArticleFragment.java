package com.jkb.mrcampus.fragment.dynamicCreate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jkb.core.contract.dynamicCreate.article.DynamicCreateArticleContract;
import com.jkb.core.contract.dynamicCreate.data.CategoryTypeData;
import com.jkb.core.contract.dynamicCreate.data.DynamicCreateArticleData;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.DynamicCreateActivity;
import com.jkb.mrcampus.adapter.recycler.dynamicCreate.DynamicCreateArticleAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.ChoosePictureFragment;
import com.jkb.mrcampus.fragment.dialog.TagFloatFragment;
import com.jkb.mrcampus.helper.cropphoto.CropHandler;
import com.jkb.mrcampus.helper.cropphoto.CropHelper;
import com.jkb.mrcampus.helper.cropphoto.CropParams;

import java.util.List;

/**
 * 创建文章动态类型的View层
 * Created by JustKiddingBaby on 2016/9/26.
 */

public class DynamicCreateArticleFragment extends BaseFragment
        implements DynamicCreateArticleContract.View,
        CropHandler,
        View.OnClickListener {


    public DynamicCreateArticleFragment() {
    }

    private static DynamicCreateArticleFragment INSTANCE = null;

    public static DynamicCreateArticleFragment newInstance() {
        INSTANCE = new DynamicCreateArticleFragment();
        return INSTANCE;
    }

    //data
    private DynamicCreateArticleContract.Presenter mPresenter;
    private DynamicCreateActivity dynamicCreateActivity;

    //关于图片
    private int replaceImgPosition = -1;
    private CropParams mCropParams;

    //View
    private EditText etInputTag;
    private EditText etTitle;

    //显示的文章内容
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DynamicCreateArticleAdapter dynamicCreateArticleAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dynamicCreateActivity = (DynamicCreateActivity) mActivity;
        setRootView(R.layout.frg_dynamic_create_article);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.ts7_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.ts7_iv_right).setOnClickListener(this);
        rootView.findViewById(R.id.ts7_tv_right).setOnClickListener(this);
        rootView.findViewById(R.id.fdca_iv_addTag).setOnClickListener(this);
        //设置文章内容的监听器
        dynamicCreateArticleAdapter.setOnItemImgClickListener(onItemImgClickListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        dynamicCreateArticleAdapter = new DynamicCreateArticleAdapter(context, null);
        recyclerView.setAdapter(dynamicCreateArticleAdapter);
    }

    @Override
    protected void initView() {
        //初始化页面内容
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fdca_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //初始化标题栏
        ((TextView) rootView.findViewById(R.id.ts7_tv_name)).setText("发布文章");
        etInputTag = (EditText) rootView.findViewById(R.id.fdca_et_inputTag);
        etTitle = (EditText) rootView.findViewById(R.id.fdca_et_inputTitle);
    }

    @Override
    public void postSuccess() {

    }

    @Override
    public void setArticleData(List<DynamicCreateArticleData> articleData) {
        dynamicCreateArticleAdapter.dynamicCreateArticleDatas = articleData;
        dynamicCreateArticleAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategoryTypeView(List<CategoryTypeData> categoryTypeDatas) {
        if (categoryTypeDatas == null || categoryTypeDatas.size() == 0) {
            showReqResult("暂时没有相关的标签数据");
            return;
        }
        dynamicCreateActivity.showTagFloatView(categoryTypeDatas,
                new TagFloatFragment.OnTagItemClickListener() {
                    @Override
                    public void onTagItemClick(String tag) {
                        etInputTag.setText(tag);
                    }
                });
    }

    @Override
    public void postArticleDynamic() {
        String tag = etInputTag.getText().toString();
        String title = etTitle.getText().toString();
        List<DynamicCreateArticleData> articleDatas =
                dynamicCreateArticleAdapter.dynamicCreateArticleDatas;
        for (int i = 0; i < articleDatas.size(); i++) {
            View childAt = recyclerView.getChildAt(i);
            EditText etDoc = (EditText) childAt.findViewById(dynamicCreateArticleAdapter.getDocId());
            String doc = etDoc.getText().toString();
            DynamicCreateArticleData data = articleDatas.get(i);
            data.setArticleContent(doc);
            articleDatas.set(i, data);
        }
        mPresenter.postArticleDynamic(title,
                articleDatas,
                tag);
    }

    @Override
    public void showChoosePictureView() {
        mCropParams = new CropParams(context);
        dynamicCreateActivity.showChoosePictureDialog();
        dynamicCreateActivity.setChoosePictureWayListener(pictureChooseWayListener);
    }

    @Override
    public void choosePictureFromCamera() {
        mCropParams.enable = false;
        mCropParams.compress = true;
        Intent intent = CropHelper
                .buildCameraIntent(mCropParams);
        startActivityForResult(intent,
                CropHelper.REQUEST_CAMERA);
    }

    @Override
    public void choosePictureFromAlbum() {
        mCropParams.enable = false;
        mCropParams.compress = true;
        Intent intent = CropHelper
                .buildGalleryIntent(mCropParams);
        startActivityForResult(intent, CropHelper.REQUEST_CROP);
    }

    @Override
    public void setPresenter(DynamicCreateArticleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        dynamicCreateActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        dynamicCreateActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        dynamicCreateActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dynamicCreateActivity = null;
        mCropParams = null;
    }

    /**
     * 选择图片的监听器
     */
    private ChoosePictureFragment.PictureChooseWayListener pictureChooseWayListener =
            new ChoosePictureFragment.PictureChooseWayListener() {
                @Override
                public void onCameraSelected() {
                    choosePictureFromCamera();
                }

                @Override
                public void onAlbusSelected() {
                    choosePictureFromAlbum();
                }
            };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts7_ib_left://返回
                dynamicCreateActivity.onBackPressed();
                break;
            case R.id.ts7_iv_right://添加图片
                showChoosePictureView();
                break;
            case R.id.ts7_tv_right://发表
                postArticleDynamic();
                break;
            case R.id.fdca_iv_addTag://添加标签
                mPresenter.getAllTag();
                break;
        }
    }

    //////////////////////////////////////////////////图片裁剪区域start///////////////////////////

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        Log.d(TAG, "Crop Uri in path: " + uri.getPath());
    }

    @Override
    public void onCompressed(Uri uri) {
        Log.d(TAG, "onCompressed: " + uri.getPath());
        if (replaceImgPosition == -1) {
            mPresenter.uploadImage(uri.getPath());
        } else {
            mPresenter.replaceImage(replaceImgPosition, uri.getPath());
        }
        replaceImgPosition = -1;
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onFailed(String message) {
        showShortToash("裁剪失败");
    }

    @Override
    public void handleIntent(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public CropParams getCropParams() {
        return mCropParams;
    }
    //////////////////////////////////////////////////图片裁剪区域end///////////////////////////

    /**
     * 文章内容条目图片的点击监听器
     */
    private DynamicCreateArticleAdapter.OnItemImgClickListener onItemImgClickListener =
            new DynamicCreateArticleAdapter.OnItemImgClickListener() {
                @Override
                public void onItemImgClick(int position) {
                    replaceImgPosition = position;
                    showChoosePictureView();
                }
            };
}
