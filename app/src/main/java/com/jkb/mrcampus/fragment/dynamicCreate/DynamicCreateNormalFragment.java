package com.jkb.mrcampus.fragment.dynamicCreate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.jkb.core.contract.dynamicCreate.normal.DynamicCreateNormalContract;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.DynamicCreateActivity;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.ChoosePictureFragment;
import com.jkb.mrcampus.helper.cropphoto.CropHandler;
import com.jkb.mrcampus.helper.cropphoto.CropHelper;
import com.jkb.mrcampus.helper.cropphoto.CropParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建普通动态类型的View层
 * Created by JustKiddingBaby on 2016/9/26.
 */

public class DynamicCreateNormalFragment extends BaseFragment
        implements DynamicCreateNormalContract.View,
        CropHandler,
        View.OnClickListener {


    public DynamicCreateNormalFragment() {
    }

    private static DynamicCreateNormalFragment INSTANCE = null;

    public static DynamicCreateNormalFragment newInstance(int circle_id) {
        INSTANCE = new DynamicCreateNormalFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.INTENT_KEY_CIRCLE_ID, circle_id);
        INSTANCE.setArguments(bundle);
        return INSTANCE;
    }

    //data
    private DynamicCreateActivity dynamicCreateActivity;
    private DynamicCreateNormalContract.Presenter mPresenter;
    private int circle_id = 0;

    private CropParams mCropParams;

    //View
    private EditText etInput;

    //图片
    private int replaceImgPosition = -1;
    private ImageView contentImgs[];
    private int contentImgsId[] = new int[]{
            R.id.fdcn_iv_pic1, R.id.fdcn_iv_pic2,
            R.id.fdcn_iv_pic3, R.id.fdcn_iv_pic4,
            R.id.fdcn_iv_pic5, R.id.fdcn_iv_pic6};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dynamicCreateActivity = (DynamicCreateActivity) mActivity;
        setRootView(R.layout.frg_dynamic_create_normal);
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
        rootView.findViewById(R.id.ts7_iv_right).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            circle_id = args.getInt(Config.INTENT_KEY_CIRCLE_ID, 0);
        } else {
            circle_id = savedInstanceState.getInt(Config.INTENT_KEY_CIRCLE_ID);
        }
    }

    @Override
    protected void initView() {
        //初始化图片控件
        contentImgs = new ImageView[contentImgsId.length];
        for (int i = 0; i < contentImgsId.length; i++) {
            contentImgs[i] = (ImageView) rootView.findViewById(contentImgsId[i]);
            contentImgs[i].setOnClickListener(onImageClickListener);
        }
        etInput = (EditText) rootView.findViewById(R.id.fdcn_et_inputValue);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts7_ib_left:
                dynamicCreateActivity.onBackPressed();
                break;
            case R.id.ts7_tv_right://发布
                postNormalDynamic();
                break;
            case R.id.ts7_iv_right://添加图片
                if (mPresenter.isAllowAddPicture()) {
                    showChoosePictureView();
                } else {
                    showReqResult("最多添加六张图片哦");
                }
                break;
        }
    }

    /**
     * 图片的点击事件
     */
    private View.OnClickListener onImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fdcn_iv_pic1:
                    replaceImgPosition = 0;
                    break;
                case R.id.fdcn_iv_pic2:
                    replaceImgPosition = 1;
                    break;
                case R.id.fdcn_iv_pic3:
                    replaceImgPosition = 2;
                    break;
                case R.id.fdcn_iv_pic4:
                    replaceImgPosition = 3;
                    break;
                case R.id.fdcn_iv_pic5:
                    replaceImgPosition = 4;
                    break;
                case R.id.fdcn_iv_pic6:
                    replaceImgPosition = 5;
                    break;
            }
            showChoosePictureView();
        }
    };

    @Override
    public int getCircleId() {
        return circle_id;
    }

    @Override
    public void setContentImgs(String[] imgUrls) {
        initContentImgs(imgUrls);
        for (int i = 0; i < imgUrls.length; i++) {
            ImageLoaderFactory.getInstance().displayImage(contentImgs[i], imgUrls[i]);
        }
    }

    /**
     * 初始化图片张数
     */
    private void initContentImgs(String[] imageUrls) {
        View contentImgAll = rootView.findViewById(R.id.fdcn_ll_pictures);
        View contentImg1To3 = rootView.findViewById(R.id.fdcn_ll_pic1To3);
        View contentImg2To3 = rootView.findViewById(R.id.fdcn_ll_pic2To3);
        View contentImg4To6 = rootView.findViewById(R.id.fdcn_ll_pic4To6);
        //设置图
        contentImgAll.setVisibility(View.GONE);
        contentImg1To3.setVisibility(View.GONE);
        contentImg2To3.setVisibility(View.GONE);
        contentImg4To6.setVisibility(View.GONE);
        int picNum = 0;
        if (imageUrls != null) {
            picNum = imageUrls.length;
        }
        //如果没图片不执行下面动作
        if (picNum <= 0) {
            return;
        }
        //有图片时肯定至少一张
        contentImgAll.setVisibility(View.VISIBLE);
        contentImg1To3.setVisibility(View.VISIBLE);
        if (picNum >= 1) {
            contentImgs[0].setVisibility(View.VISIBLE);
        }
        if (picNum >= 2) {
            contentImg2To3.setVisibility(View.VISIBLE);
            contentImgs[1].setVisibility(View.VISIBLE);
            contentImgs[2].setVisibility(View.INVISIBLE);
        }
        if (picNum >= 3) {
            contentImgs[2].setVisibility(View.VISIBLE);
        }
        if (picNum >= 4) {
            contentImg4To6.setVisibility(View.VISIBLE);
            contentImgs[3].setVisibility(View.VISIBLE);
            contentImgs[4].setVisibility(View.INVISIBLE);
            contentImgs[5].setVisibility(View.INVISIBLE);
        }
        if (picNum >= 5) {
            contentImgs[4].setVisibility(View.VISIBLE);
        }
        if (picNum >= 6) {
            contentImgs[5].setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void postNormalDynamic() {
        String content = etInput.getText().toString();
        mPresenter.postNormalDynamic(content);
    }

    @Override
    public void postSuccess() {
        dynamicCreateActivity.onBackPressed();
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
    public void setPresenter(DynamicCreateNormalContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        if (!isHidden())
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
        mPresenter = null;
        mCropParams = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.INTENT_KEY_CIRCLE_ID, circle_id);
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

    //////////////////////////////////////////////////图片裁剪区域start///////////////////////////

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        Log.d(TAG, "Crop Uri in path: " + uri.getPath());
//        if (replaceImgPosition == -1) {
//            mPresenter.uploadImage(uri.getPath());
//        } else {
//            mPresenter.replaceImage(replaceImgPosition, uri.getPath());
//        }
//        replaceImgPosition = -1;
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
}
