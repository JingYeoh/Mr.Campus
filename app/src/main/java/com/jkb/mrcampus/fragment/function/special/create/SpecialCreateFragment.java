package com.jkb.mrcampus.fragment.function.special.create;

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

import com.jkb.core.contract.function.special.craete.SpecialCreateContract;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.SpecialCreateActivity;
import com.jkb.mrcampus.adapter.recycler.itemDecoration.DividerItemDecoration;
import com.jkb.mrcampus.adapter.recycler.special.create.SpecialCreatePictureAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.ChoosePictureFragment;
import com.jkb.mrcampus.helper.cropphoto.CropHandler;
import com.jkb.mrcampus.helper.cropphoto.CropHelper;
import com.jkb.mrcampus.helper.cropphoto.CropParams;

import java.util.List;

/**
 * 创建表白墙专题的V层
 * Created by JustKiddingBaby on 2016/11/19.
 */

public class SpecialCreateFragment extends BaseFragment implements
        SpecialCreateContract.View,
        View.OnClickListener, CropHandler {

    public static SpecialCreateFragment newInstance(int subjectCreateType) {
        Bundle args = new Bundle();
        args.putInt(Config.BUNDLE_KEY_SUBJECT_CREATE_TYPE, subjectCreateType);
        SpecialCreateFragment fragment = new SpecialCreateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private int subjectCreateType;
    private SpecialCreateActivity specialCreateActivity;
    private SpecialCreateContract.Presenter mPresenter;
    private int replacePicturePosition = -1;
    private CropParams mCropParams;//头像裁剪用到的

    //显示图片用的列表
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SpecialCreatePictureAdapter specialCreatePictureAdapter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        specialCreateActivity = (SpecialCreateActivity) mActivity;
        setRootView(R.layout.frg_subject_create_confession);
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
        rootView.findViewById(R.id.ts7_tv_right).setOnClickListener(this);
        rootView.findViewById(R.id.ts7_iv_right).setOnClickListener(this);
        specialCreatePictureAdapter.setOnSpecialCreateItemClickListener(
                onSpecialCreateItemClickListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            subjectCreateType = args.getInt(Config.BUNDLE_KEY_SUBJECT_CREATE_TYPE);
        } else {
            subjectCreateType = savedInstanceState.getInt(Config.BUNDLE_KEY_SUBJECT_CREATE_TYPE);
        }
        initTitleView();
        specialCreatePictureAdapter = new SpecialCreatePictureAdapter(context, null);
        recyclerView.setAdapter(specialCreatePictureAdapter);
    }

    @Override
    protected void initView() {
        //初始化列表
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fscc_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL,
                        getResources().getColor(R.color.gravy_20), 5));//添加分割线
    }

    /**
     * 初始化标题栏视图
     */
    private void initTitleView() {
        switch (subjectCreateType) {
            case SpecialCreateActivity.SUBJECT_CREATE_TYPE_CONFESSION:
                ((TextView) rootView.findViewById(R.id.ts7_tv_name)).setText("发布表白墙专题");
                break;
            case SpecialCreateActivity.SUBJECT_CREATE_TYPE_FLEAMARKET:
                ((TextView) rootView.findViewById(R.id.ts7_tv_name)).setText("发布跳蚤市场专题");
                break;
            case SpecialCreateActivity.SUBJECT_CREATE_TYPE_LOSTANDFOUND:
                ((TextView) rootView.findViewById(R.id.ts7_tv_name)).setText("发布失物招领专题");
                break;
            case SpecialCreateActivity.SUBJECT_CREATE_TYPE_TAUNTED:
                ((TextView) rootView.findViewById(R.id.ts7_tv_name)).setText("发布吐槽墙专题");
                break;
            case SpecialCreateActivity.SUBJECT_CREATE_TYPE_WANTED_PARTNER:
                ((TextView) rootView.findViewById(R.id.ts7_tv_name)).setText("发布寻伙伴专题");
                break;
            case SpecialCreateActivity.SUBJECT_CREATE_TYPE_WANTED_SAVANT:
                ((TextView) rootView.findViewById(R.id.ts7_tv_name)).setText("发布求学霸专题");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts7_ib_left:
                specialCreateActivity.onBackPressed();
                break;
            case R.id.ts7_iv_right://添加图片
                onAddPictureClick();
                break;
            case R.id.ts7_tv_right:
                postSubjectDynamic();
                break;
        }
    }

    @Override
    public void setUploadPictures(List<String> uploadPictures) {
        if (uploadPictures.size() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
        specialCreatePictureAdapter.picturesPath = uploadPictures;
        specialCreatePictureAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPictureSelectorView() {
        mCropParams = new CropParams(context);
        specialCreateActivity.showChoosePictureDialog();
        specialCreateActivity.setChoosePictureWayListener(pictureChooseWayListener);
    }

    @Override
    public void choosePictureFromCamera() {
        mCropParams.enable = false;
        mCropParams.compress = true;
        Intent intent = CropHelper.buildCameraIntent(mCropParams);
        startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
    }

    @Override
    public void choosePictureFromAlbum() {
        mCropParams.enable = false;
        mCropParams.compress = true;
        Intent intent = CropHelper.buildGalleryIntent(mCropParams);
        startActivityForResult(intent, CropHelper.REQUEST_CROP);
    }

    @Override
    public void postSubjectDynamic() {
        String title = ((EditText) rootView.findViewById(R.id.fscc_et_inputTitle)).
                getText().toString();
        String content = ((EditText) rootView.findViewById(R.id.fscc_et_inputValue)).
                getText().toString();
        switch (subjectCreateType) {
            case SpecialCreateActivity.SUBJECT_CREATE_TYPE_CONFESSION:
                mPresenter.postSubjectConfession(title, content);
                break;
            case SpecialCreateActivity.SUBJECT_CREATE_TYPE_FLEAMARKET:
                mPresenter.postSubjectFleaMarket(title, content);
                break;
            case SpecialCreateActivity.SUBJECT_CREATE_TYPE_LOSTANDFOUND:
                mPresenter.postSubjectLostAndFound(title, content);
                break;
            case SpecialCreateActivity.SUBJECT_CREATE_TYPE_TAUNTED:
                mPresenter.postSubjectTaunted(title, content);
                break;
            case SpecialCreateActivity.SUBJECT_CREATE_TYPE_WANTED_PARTNER:
                mPresenter.postSubjectWantedPartner(title, content);
                break;
            case SpecialCreateActivity.SUBJECT_CREATE_TYPE_WANTED_SAVANT:
                mPresenter.postSubjectWantedSavant(title, content);
                break;
        }
    }

    @Override
    public void onAddPictureClick() {
        replacePicturePosition = -1;//添加图片
        showPictureSelectorView();
    }

    @Override
    public void postSuccess() {
        showReqResult("发布成功");
        specialCreateActivity.onBackPressed();
    }

    @Override
    public void setPresenter(SpecialCreateContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        specialCreateActivity.showLoading(value, this);
    }

    @Override
    public void dismissLoading() {
        specialCreateActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        showShortToash(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.BUNDLE_KEY_SUBJECT_CREATE_TYPE, subjectCreateType);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //data
        specialCreateActivity = null;
        mCropParams = null;
        //view
        recyclerView = null;
        linearLayoutManager = null;
        //回调
        onSpecialCreateItemClickListener = null;
        pictureChooseWayListener = null;
    }

    /**
     * 图片条目点击事件
     */
    private SpecialCreatePictureAdapter.OnSpecialCreateItemClickListener
            onSpecialCreateItemClickListener =
            new SpecialCreatePictureAdapter.OnSpecialCreateItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    replacePicturePosition = position;
                    showPictureSelectorView();
                }

                @Override
                public void onDeleteItemClick(int position) {
                    mPresenter.deletePicture(position);
                }
            };

    //////////////////////////////////////分割线
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
                public void onAlbumSelected() {
                    choosePictureFromAlbum();
                }
            };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        Log.d(TAG, "Crop Uri in path: " + uri.getPath());
        String path = uri.getPath();
        if (replacePicturePosition != -1) {
            mPresenter.replacePicture(replacePicturePosition, path);
        } else {
            mPresenter.addPicture(path);
        }
    }

    @Override
    public void onCompressed(Uri uri) {
        Log.d(TAG, "onCompressed Uri in path: " + uri.getPath());
        String path = uri.getPath();
        if (replacePicturePosition != -1) {
            mPresenter.replacePicture(replacePicturePosition, path);
        } else {
            mPresenter.addPicture(path);
        }
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onFailed(String message) {
        showShortToash("获取图片失败");
    }

    @Override
    public void handleIntent(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public CropParams getCropParams() {
        return mCropParams;
    }
}
