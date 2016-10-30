package com.jkb.mrcampus.fragment.dynamicCreate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.dynamicCreate.data.CategoryTypeData;
import com.jkb.core.contract.dynamicCreate.topic.DynamicCreateTopicContract;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.DynamicCreateActivity;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.ChoosePictureFragment;
import com.jkb.mrcampus.fragment.dialog.TagFloatFragment;
import com.jkb.mrcampus.helper.cropphoto.CropHandler;
import com.jkb.mrcampus.helper.cropphoto.CropHelper;
import com.jkb.mrcampus.helper.cropphoto.CropParams;

import java.util.List;

/**
 * 创建话题动态类型的View层
 * Created by JustKiddingBaby on 2016/9/26.
 */

public class DynamicCreateTopicFragment extends BaseFragment
        implements DynamicCreateTopicContract.View, View.OnClickListener,
        CropHandler,
        ChoosePictureFragment.PictureChooseWayListener {


    public DynamicCreateTopicFragment() {
    }

    private static DynamicCreateTopicFragment INSTANCE = null;

    public static DynamicCreateTopicFragment newInstance() {
        INSTANCE = new DynamicCreateTopicFragment();
        return INSTANCE;
    }

    //data
    private DynamicCreateActivity dynamicCreateActivity;
    private DynamicCreateTopicContract.Presenter mPresenter;

    private CropParams mCropParams;

    private static final int MAX_TAG_INPUT_COUNT = 10;

    //View
    private EditText etTitle;
    private EditText etInputTag;
    private EditText etContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dynamicCreateActivity = (DynamicCreateActivity) mActivity;
        setRootView(R.layout.frg_dynamic_create_topic);
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
        //标题栏
        rootView.findViewById(R.id.ts7_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.ts7_tv_right).setOnClickListener(this);
        //点击添加图片
        rootView.findViewById(R.id.fdct_addPic).setOnClickListener(this);
        //点击添加标签
        rootView.findViewById(R.id.fdct_iv_addTag).setOnClickListener(this);

        ((EditText) rootView.findViewById(R.id.fdct_et_inputTag)).
                addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        int nSelStart;
                        int nSelEnd;
                        nSelStart = etInputTag.getSelectionStart();
                        nSelEnd = etInputTag.getSelectionEnd();
                        boolean nOverMaxLength;
                        nOverMaxLength = (s.length() > MAX_TAG_INPUT_COUNT);
                        if (nOverMaxLength) {
                            s.delete(nSelStart - 1, nSelEnd);
                            etInputTag.setTextKeepState(s);
                        }
                    }
                });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        //初始化标题栏
        rootView.findViewById(R.id.ts7_iv_right).setVisibility(View.GONE);
        ((TextView) rootView.findViewById(R.id.ts7_tv_name)).setText(R.string.post_topic);
        //内容
        etTitle = (EditText) rootView.findViewById(R.id.fdct_et_inputTitle);
        etContent = (EditText) rootView.findViewById(R.id.fdct_et_inputValue);
        etInputTag = ((EditText) rootView.findViewById(R.id.fdct_et_inputTag));
    }

    @Override
    public void setPresenter(DynamicCreateTopicContract.Presenter presenter) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts7_ib_left:
                dynamicCreateActivity.onBackPressed();
                break;
            case R.id.ts7_tv_right://发布
                postTopicDynamic();
                break;
            case R.id.fdct_addPic://添加图片
                showChoosePictureView();
                break;
            case R.id.fdct_iv_addTag://添加标签
                mPresenter.getAllTag();
                break;
        }
    }

    @Override
    public void postSuccess() {
//        dynamicCreateActivity.onBackPressed();
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
    public void setPicture(String picture) {
        ImageView ivPic = (ImageView) rootView.findViewById(R.id.fdct_iv_picture);
        if (StringUtils.isEmpty(picture)) {
            ivPic.setVisibility(View.INVISIBLE);
            return;
        }
        ivPic.setVisibility(View.VISIBLE);
        ImageLoaderFactory.getInstance().displayImage(ivPic, picture);
    }

    @Override
    public void postTopicDynamic() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        String tag = etInputTag.getText().toString();
        mPresenter.postTopicDynamic(title, content, tag);
    }

    @Override
    public void showChoosePictureView() {
        mCropParams = new CropParams(context);
        dynamicCreateActivity.showChoosePictureDialog();
        dynamicCreateActivity.setChoosePictureWayListener(this);
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
    public void onCameraSelected() {
        choosePictureFromCamera();
    }

    @Override
    public void onAlbusSelected() {
        choosePictureFromAlbum();
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
        mPresenter.uploadImage(uri.getPath());
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
}
