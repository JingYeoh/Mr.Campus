package com.jkb.mrcampus.fragment.create$circle;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.model.LatLng;
import com.jkb.core.contract.create$circle.EnteringCircleMessageContract;
import com.jkb.core.presenter.create$circle.EnteringCircleMessagePresenter;
import com.jkb.core.presenter.create$circle.SelectCircleCoordinatePresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.CreateCircleActivity;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 录入圈子信息的Fragment
 * Created by JustKiddingBaby on 2016/8/11.
 */
public class EnteringCircleMessageFragment extends BaseFragment implements View.OnClickListener, EnteringCircleMessageContract.View {

    private static EnteringCircleMessageFragment INSTANCE;

    public EnteringCircleMessageFragment() {
    }

    public static EnteringCircleMessageFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EnteringCircleMessageFragment();
        }
        return INSTANCE;
    }


    //本页面相关
    private EnteringCircleMessagePresenter mPresenter;

    private CreateCircleActivity createCircleActivity;

    //选择坐标地址
    private SelectCircleCoordinateFragment selectCircleCoordinateFragment;

    //数据相关
    private LatLng determineLatLng = null;//决定的地址坐标


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_create_circle_entering_circle_message);
        createCircleActivity = (CreateCircleActivity) mActivity;
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.fccecm_ib_chooseHeadImg).setOnClickListener(this);
        rootView.findViewById(R.id.fccecm_bt_chooseSchool).setOnClickListener(this);
        rootView.findViewById(R.id.fccecm_bt_chooseCoordinate).setOnClickListener(this);
        rootView.findViewById(R.id.fccecm_bt_chooseBackground).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fccecm_ib_chooseHeadImg:
                chooseHeadImg();
                break;
            case R.id.fccecm_bt_chooseSchool:
                chooseSchool();
                break;
            case R.id.fccecm_bt_chooseCoordinate:
                chooseCoordinate();
                break;
            case R.id.fccecm_bt_chooseBackground:
                chooseBackground();
                break;
        }
    }

    @Override
    public void setPresenter(EnteringCircleMessageContract.Presenter presenter) {
        mPresenter = (EnteringCircleMessagePresenter) presenter;
    }

    @Override
    public void showLoading(String value) {
        createCircleActivity.showLoading(value);
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
    public void chooseSchool() {

    }

    @Override
    public void chooseBackground() {

    }

    @Override
    public void chooseHeadImg() {

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

    }

    /**
     * 设置坐标地址
     */
    public void setDetermineLatLng(LatLng determineLatLng) {
        this.determineLatLng = determineLatLng;
        //设置为选择完成状态
        showReqResult("设置驻扎地点成功，宝宝真棒！");
    }
}
