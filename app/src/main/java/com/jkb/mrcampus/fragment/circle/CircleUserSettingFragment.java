package com.jkb.mrcampus.fragment.circle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.circle.CircleSettingUserContract;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.CircleActivity;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.ChoosePictureFragment;
import com.jkb.mrcampus.fragment.dialog.InputTextFloatFragment;
import com.jkb.mrcampus.helper.cropphoto.CropHandler;
import com.jkb.mrcampus.helper.cropphoto.CropHelper;
import com.jkb.mrcampus.helper.cropphoto.CropParams;

/**
 * 圈子创建者的圈子设置页面
 * Created by JustKiddingBaby on 2016/10/7.
 */

public class CircleUserSettingFragment extends BaseFragment implements
        CircleSettingUserContract.View,
        View.OnClickListener,
        CropHandler, ChoosePictureFragment.PictureChooseWayListener {


    private int circle_id;//圈子id

    public static CircleUserSettingFragment newInstance(int circle_id) {
        CircleUserSettingFragment INSTANCE = new CircleUserSettingFragment();
        Bundle args = new Bundle();
        args.putInt(Config.INTENT_KEY_CIRCLE_ID, circle_id);
        INSTANCE.setArguments(args);
        return INSTANCE;
    }

    //data
    private CircleActivity circleActivity;
    private CircleSettingUserContract.Presenter mPresenter;

    //图片裁剪
    private CropParams mCropParams;

    //常量
    private static final int INPUT_TYPE_NAME = 1001;
    private static final int INPUT_TYPE_BREF = 1002;
    private static final int INPUT_TYPE_TAG = 1003;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        circleActivity = (CircleActivity) mActivity;
        setRootView(R.layout.frg_circle_setting_user);
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
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
        //功能
        rootView.findViewById(R.id.fcsu_iv_picture).setOnClickListener(this);
        rootView.findViewById(R.id.fcsu_ll_circleName).setOnClickListener(this);
        rootView.findViewById(R.id.fcsu_ll_bref).setOnClickListener(this);
        rootView.findViewById(R.id.fcsu_ll_tag).setOnClickListener(this);
        rootView.findViewById(R.id.fcsu_iv_switch_inCommonUse).setOnClickListener(this);
        rootView.findViewById(R.id.fcsu_ll_userList).setOnClickListener(this);
        rootView.findViewById(R.id.fcsu_ll_userInBlackList).setOnClickListener(this);
        rootView.findViewById(R.id.fcsu_ll_dynamicInBlackList).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle arg = getArguments();
            circle_id = arg.getInt(Config.INTENT_KEY_CIRCLE_ID);
        } else {
            circle_id = savedInstanceState.getInt(Config.INTENT_KEY_CIRCLE_ID);
        }
    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("圈子设置");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts4_ib_left:
                circleActivity.onBackPressed();
                break;
            case R.id.fcsu_iv_picture://选择图片
                showChoosePictureView();
                break;
            case R.id.fcsu_ll_circleName://修改圈子名称
                showInputTextView(INPUT_TYPE_NAME);
                break;
            case R.id.fcsu_ll_bref://简介
                showInputTextView(INPUT_TYPE_BREF);
                break;
            case R.id.fcsu_ll_tag://标签
                showInputTextView(INPUT_TYPE_TAG);
                break;
            case R.id.fcsu_iv_switch_inCommonUse://设置为常用圈子开关
                mPresenter.onInCommonUseSwitchClick();
                break;
            case R.id.fcsu_ll_userList://用户列表
                showAttentionCircleUserList(mPresenter.isCircleCreator());
                break;
            case R.id.fcsu_ll_userInBlackList://用户黑名单
                showUserInCircleBlackList();
                break;
            case R.id.fcsu_ll_dynamicInBlackList://动态黑名单
                showDynamicInCircleBlackList();
                break;
        }
    }

    @Override
    public int getCircleId() {
        return circle_id;
    }

    @Override
    public void setCirclePicture(String circlePicture) {
        ImageView imageView = (ImageView) rootView.findViewById(R.id.fcsu_iv_picture);
        if (StringUtils.isEmpty(circlePicture)) {
            imageView.setImageResource(R.color.default_picture);
        } else {
            ImageLoaderFactory.getInstance().displayImage(imageView, circlePicture);
        }
    }

    @Override
    public void setCircleName(String circleName) {
        TextView textView = (TextView) rootView.findViewById(R.id.fcsu_tv_circleName);
        textView.setText(circleName);
    }

    @Override
    public void setCircleBref(String circleBref) {
        TextView textView = (TextView) rootView.findViewById(R.id.fcsu_tv_bref);
        textView.setText(circleBref);
    }

    @Override
    public void setCircleTag(String circleTag) {
        TextView textView = (TextView) rootView.findViewById(R.id.fcsu_tv_tag);
        textView.setText(circleTag);
    }

    @Override
    public void setInCommonUseStatus(boolean inCommonUseStatus) {
        ImageView imageView = (ImageView) rootView.findViewById(R.id.fcsu_iv_switch_inCommonUse);
        imageView.setSelected(inCommonUseStatus);
    }

    @Override
    public void showChoosePictureView() {
        //再次处初始化主要为了解决某些系统问题：再次选图片后无法裁剪
        mCropParams = new CropParams(context);
        circleActivity.showChoosePictureDialog();
        circleActivity.setChoosePictureWayListener(this);
    }

    @Override
    public void choosePictureFromCamera() {
        mCropParams.enable = false;
        mCropParams.compress = false;
        Intent intent = CropHelper
                .buildCameraIntent(mCropParams);
        startActivityForResult(intent,
                CropHelper.REQUEST_CAMERA);
    }

    @Override
    public void choosePictureFromAlbum() {
        mCropParams.enable = false;
        mCropParams.compress = false;
        Intent intent = CropHelper
                .buildGalleryIntent(mCropParams);
        startActivityForResult(intent, CropHelper.REQUEST_CROP);
    }

    @Override
    public void showInputTextView(final int type) {
        TextView tvInputText = null;
        switch (type) {
            case INPUT_TYPE_NAME:
                tvInputText = (TextView) rootView.findViewById(R.id.fcsu_tv_circleName);
                break;
            case INPUT_TYPE_TAG:
                tvInputText = (TextView) rootView.findViewById(R.id.fcsu_tv_tag);
                break;
            case INPUT_TYPE_BREF:
                tvInputText = (TextView) rootView.findViewById(R.id.fcsu_tv_bref);
                break;
        }
        if (tvInputText == null) {
            return;
        }
        final String value = tvInputText.getText().toString();
        circleActivity.showInputTextFloatView(value,
                new InputTextFloatFragment.OnSubmitClickListener() {
                    @Override
                    public void onSure(String value2) {
                        if (!value2.equals(value)) {
                            circleActivity.dismiss();
                            updateInputText(type, value2);
                        }
                    }
                });
    }

    @Override
    public void updateInputText(int type, String value) {
        switch (type) {
            case INPUT_TYPE_NAME:
                mPresenter.updateCircleName(value);
                break;
            case INPUT_TYPE_TAG:
                mPresenter.updateCircleTag(value);
                break;
            case INPUT_TYPE_BREF:
                mPresenter.updateCircleBref(value);
                break;
        }
    }

    @Override
    public void showAttentionCircleUserList(boolean isCircleCreator) {
        circleActivity.showAttentionUserList(isCircleCreator);
    }

    @Override
    public void showUserInCircleBlackList() {
        circleActivity.showUserInCircleBlackList();
    }

    @Override
    public void showDynamicInCircleBlackList() {
        circleActivity.showDynamicInBlackList();
    }

    @Override
    public void setPresenter(CircleSettingUserContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        circleActivity.showLoading(value, this);
    }

    @Override
    public void dismissLoading() {
        circleActivity.dismissLoading();
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
        outState.putInt(Config.INTENT_KEY_CIRCLE_ID, circle_id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        circleActivity = null;
        mCropParams = null;
    }

    ////////////////////////////////////////////图片裁剪start------------------->>>>>>>>>>>>>>>>>
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        LogUtils.d(TAG, "Crop Uri in path: " + uri.getPath());
        String path = uri.getPath();
        mPresenter.updateCirclePicture(path);
    }

    @Override
    public void onCompressed(Uri uri) {
        LogUtils.d(TAG, "onCompressed: " + uri.getPath());
    }

    @Override
    public void onCancel() {
        //裁剪取消
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

    @Override
    public void onCameraSelected() {
        choosePictureFromCamera();
    }

    @Override
    public void onAlbumSelected() {
        choosePictureFromAlbum();
    }
    ////////////////////////////////////////////图片裁剪end------------------->>>>>>>>>>>>>>>>>
}
