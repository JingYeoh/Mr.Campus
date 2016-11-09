package com.jkb.mrcampus.fragment.create$circle;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.jkb.core.contract.create$circle.EnteringCircleMessageContract;
import com.jkb.core.presenter.create$circle.EnteringCircleMessagePresenter;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.CreateCircleActivity;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.ChoosePictureFragment;
import com.jkb.mrcampus.fragment.dialog.InputTextFloatFragment;
import com.jkb.mrcampus.helper.cropphoto.CropHandler;
import com.jkb.mrcampus.helper.cropphoto.CropHelper;
import com.jkb.mrcampus.helper.cropphoto.CropParams;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 录入圈子信息的Fragment
 * Created by JustKiddingBaby on 2016/8/11.
 */
public class EnteringCircleMessageFragment extends BaseFragment implements View.OnClickListener,
        EnteringCircleMessageContract.View, CropHandler {

    public static EnteringCircleMessageFragment newInstance() {
        EnteringCircleMessageFragment INSTANCE = new EnteringCircleMessageFragment();
        return INSTANCE;
    }

    private static final String TAG = "EnteringCircleMessage";
    //本页面相关
    private EnteringCircleMessagePresenter mPresenter;
    private CreateCircleActivity createCircleActivity;

    private CropParams mCropParams;//选择图片用到的工具类
    private String photoPath = null;

    //常量
    private static final int INPUT_TYPE_NAME = 1001;
    private static final int INPUT_TYPE_BREF = 1002;
    private static final int INPUT_TYPE_TAG = 1003;

    //选择坐标地址
    private SelectCircleCoordinateFragment selectCircleCoordinateFragment;

    //创建圈子用到的数据
    private LatLng determineLatLng = null;//决定的地址坐标


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_create_circle_entering_circle_message);
        createCircleActivity = (CreateCircleActivity) mActivity;
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
        rootView.findViewById(R.id.fccecm_contentInputCircleName).setOnClickListener(this);
        rootView.findViewById(R.id.fccecm_contentInputCircleBref).setOnClickListener(this);
        rootView.findViewById(R.id.fccecm_contentInputCircleTag).setOnClickListener(this);
        rootView.findViewById(R.id.fccecm_contentChooseCoordinate).setOnClickListener(this);
        rootView.findViewById(R.id.fccecm_contentChooseSchool).setOnClickListener(this);
        rootView.findViewById(R.id.fccecm_iv_chooseBackground).setOnClickListener(this);
        rootView.findViewById(R.id.ts8_tv_right).setOnClickListener(this);
        rootView.findViewById(R.id.ts8_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.fccecm_iv_addTag).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mCropParams = new CropParams(context);
        //内存重启之后初始化对象
        if (savedInstanceState != null) {
            restoreCoordinateFragment();
        } else {

        }
    }

    /**
     * 恢复Fragment的数据
     */
    private void restoreCoordinateFragment() {
        FragmentManager fm = mActivity.getFragmentManager();
        selectCircleCoordinateFragment = (SelectCircleCoordinateFragment)
                fm.findFragmentByTag(ClassUtils.getClassName(SelectCircleCoordinateFragment.class));
        if (selectCircleCoordinateFragment == null) {
            selectCircleCoordinateFragment = SelectCircleCoordinateFragment.newInstance();
        }
    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts8_tv_right)).setText("完成");
        ((TextView) rootView.findViewById(R.id.ts8_tv_name)).setText("创建圈子");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ts8_ib_left://返回
                createCircleActivity.onBackPressed();
                break;
            case R.id.fccecm_contentInputCircleName:
                showInputTextView(INPUT_TYPE_NAME);
                break;
            case R.id.fccecm_contentInputCircleBref:
                showInputTextView(INPUT_TYPE_BREF);
                break;
            case R.id.fccecm_contentInputCircleTag:
                showInputTextView(INPUT_TYPE_TAG);
                break;
            case R.id.fccecm_iv_addTag://选择标签

                break;
            case R.id.fccecm_contentChooseSchool://选择学校
                chooseSchool();
                break;
            case R.id.fccecm_contentChooseCoordinate://选择坐标
                chooseCoordinate();
                break;
            case R.id.fccecm_iv_chooseBackground://选择背景
                chooseBackground();
                break;
            case R.id.ts8_tv_right://创建完成
                createCircle();
                break;
        }
    }

    @Override
    public void setPresenter(EnteringCircleMessageContract.Presenter presenter) {
        mPresenter = (EnteringCircleMessagePresenter) presenter;
    }

    @Override
    public void showLoading(String value) {
        if (!isHidden()) createCircleActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        createCircleActivity.dismissLoading();
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
    public void onDestroy() {
        super.onDestroy();
        createCircleActivity = null;
        mCropParams = null;
        determineLatLng = null;
    }

    @Override
    public void chooseSchool() {
        createCircleActivity.showSelectSchoolView();
        mPresenter.initSchoolInfo();
    }

    @Override
    public void chooseBackground() {
        showSelectWayOfChoosePhotoView();
    }

    @Override
    public void chooseCoordinate() {
        if (selectCircleCoordinateFragment == null) {
            selectCircleCoordinateFragment = SelectCircleCoordinateFragment.newInstance();
        }
        selectCircleCoordinateFragment.show(createCircleActivity.getFragmentManager(),
                ClassUtils.getClassName(SelectCircleCoordinateFragment.class));
    }


    @Override
    public void showSelectWayOfChoosePhotoView() {
        createCircleActivity.showChoosePictureDialog();
        createCircleActivity.setChoosePictureWayListener(choosePictureListener);
    }

    @Override
    public void choosePictureFromCamera() {
        mCropParams.enable = true;
        mCropParams.compress = false;
        Intent intent = CropHelper
                .buildCameraIntent(mCropParams);
        startActivityForResult(intent,
                CropHelper.REQUEST_CAMERA);
    }

    @Override
    public void choosePictureFromAlbum() {
        mCropParams.enable = true;
        mCropParams.compress = false;
        Intent intent = CropHelper
                .buildGalleryIntent(mCropParams);
        startActivityForResult(intent, CropHelper.REQUEST_CROP);
    }

    @Override
    public void createCircle() {
        String circleName = ((TextView) rootView.findViewById(R.id.fccecm_et_circleName))
                .getText().toString();
        String circleTag = ((TextView) rootView.findViewById(R.id.fccecm_et_circleTag))
                .getText().toString();
        String circleBref = ((TextView) rootView.findViewById(R.id.fccecm_et_circleIntroduction))
                .getText().toString();
        if (determineLatLng == null) {
            showReqResult("请选择根据地~");
            return;
        }
        int school_id = 1;
        mPresenter.createCircle(school_id, circleName, circleBref, circleTag,
                determineLatLng.longitude, determineLatLng.latitude, photoPath);
    }

    @Override
    public void showInputTextView(final int type) {
        TextView tvInputText = null;
        switch (type) {
            case INPUT_TYPE_NAME:
                tvInputText = (TextView) rootView.findViewById(R.id.fccecm_et_circleName);
                break;
            case INPUT_TYPE_BREF:
                tvInputText = (TextView) rootView.findViewById(R.id.fccecm_et_circleIntroduction);
                break;
            case INPUT_TYPE_TAG:
                tvInputText = (TextView) rootView.findViewById(R.id.fccecm_et_circleTag);
                break;
        }
        if (tvInputText == null) {
            return;
        }
        final String value = tvInputText.getText().toString();
        createCircleActivity.showInputTextFloatView(value,
                new InputTextFloatFragment.OnSubmitClickListener() {
                    @Override
                    public void onSure(String value2) {
                        if (!value2.equals(value)) {
                            createCircleActivity.dismiss();
                            updateInputText(type, value2);
                        }
                    }
                });
    }

    @Override
    public void updateInputText(int type, String value) {
        TextView tvInputText = null;
        switch (type) {
            case INPUT_TYPE_NAME:
                tvInputText = (TextView) rootView.findViewById(R.id.fccecm_et_circleName);
                break;
            case INPUT_TYPE_BREF:
                tvInputText = (TextView) rootView.findViewById(R.id.fccecm_et_circleIntroduction);
                break;
            case INPUT_TYPE_TAG:
                tvInputText = (TextView) rootView.findViewById(R.id.fccecm_et_circleTag);
                break;
        }
        tvInputText.setText(value);
    }

    @Override
    public void setSchoolName(String schoolName) {
        TextView tvSchoolName = (TextView) rootView.findViewById(R.id.fccecm_tv_schoolName);
        tvSchoolName.setText(schoolName);
    }

    @Override
    public void createCircleCompleted(@NonNull int circle_id) {
        showReqResult("创建成功，宝宝真棒");
        createCircleActivity.finish();
        createCircleActivity.startCircleActivity(circle_id);
    }

    /**
     * 设置坐标地址
     */
    public void setDetermineLatLng(LatLng determineLatLng) {
        TextView tvDetermineLatLng = (TextView) rootView.findViewById(R.id.fccecm_tv_coordinate);
        tvDetermineLatLng.setText("已选择驻扎地");
        this.determineLatLng = determineLatLng;
        //设置为选择完成状态
        showReqResult("设置驻扎地点成功，宝宝真棒！");
    }

    /**
     * 选择图片的监听器
     */
    private ChoosePictureFragment.PictureChooseWayListener choosePictureListener =
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        Log.d(TAG, "onPhotoCropped: " + uri.getPath());
        photoPath = uri.getPath();
        ImageView ivPic = (ImageView) rootView.findViewById(R.id.fccecm_iv_chooseBackground);
        ImageLoaderFactory.getInstance().displayFromSDCard(uri.getPath(), ivPic);
    }

    @Override
    public void onCompressed(Uri uri) {
        Log.d(TAG, "onPhotoCropped: " + uri.getPath());
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onFailed(String message) {
        showReqResult("裁剪失败" + message);
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
