package com.jkb.mrcampus.fragment.menu;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.core.contract.menu.SwitchFunctionContract;
import com.jkb.core.control.userstate.UserState;
import com.jkb.core.presenter.menu.SwitchFunctionPresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 左滑菜单：切换功能的View
 * Created by JustKiddingBaby on 2016/7/24.
 */
public class SwitchFunctionFragment extends BaseFragment implements SwitchFunctionContract.View {

    private SwitchFunctionPresenter mPresenter;
    private MainActivity mainActivity;

    //个人中心的视图对象
    private View personView;

    public SwitchFunctionFragment() {
    }

    /**
     * 获得一个实例化的SwitchFunctionFragment对象
     *
     * @return
     */
    public static SwitchFunctionFragment newInstance() {
        return new SwitchFunctionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_menu_switchfunction);
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
        rootView.findViewById(R.id.bt_index).setOnClickListener(showClickListener);
        rootView.findViewById(R.id.bt_setting).setOnClickListener(showClickListener);
        rootView.findViewById(R.id.bt_tools).setOnClickListener(showClickListener);
        rootView.findViewById(R.id.bt_zhuanti).setOnClickListener(showClickListener);

        rootView.findViewById(R.id.bt_message).setOnClickListener(startClickListener);
        rootView.findViewById(R.id.bt_school).setOnClickListener(startClickListener);
        rootView.findViewById(R.id.bt_map).setOnClickListener(startClickListener);
//        rootView.findViewById(R.id.bt_person).setOnClickListener(startClickListener);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        mainActivity = (MainActivity) mActivity;

        personView = rootView.findViewById(R.id.bt_person);
    }

    /**
     * 打开视图的监听器
     */
    private View.OnClickListener startClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_map:
                    mainActivity.startMap();
                    break;
                case R.id.bt_message:
                    mainActivity.startMessage();
                    break;
                case R.id.bt_school:
                    mainActivity.startChooseSchools();
                    break;
//                case R.id.bt_person:
//                    mainActivity.startPersonalCenter();
//                    break;
            }
        }
    };

    /**
     * 显示视图的监听器
     */
    private View.OnClickListener showClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainActivity.hideMenu();//隐藏侧滑菜单
            mainActivity.hideAllView();//隐藏其余的页面视图
            switch (v.getId()) {
                case R.id.bt_index:
                    mainActivity.showIndex();
                    break;
                case R.id.bt_setting:
                    mainActivity.showSetting();
                    break;
                case R.id.bt_tools:
                    mainActivity.showTools();
                    break;
                case R.id.bt_zhuanti:
                    mainActivity.showSpecialModel();
                    break;
            }
        }
    };

    @Override
    public void showSchoolView(Bitmap schoolBadge, String schoolName) {

    }

    @Override
    public void showPersonalView(Bitmap headImg, String nickName) {

    }

    @Override
    public void showPersonalView() {

    }

    @Override
    public UserState.MenuPersonViewListener onPersonViewListener() {
        return personViewListener;
    }

    @Override
    public View getPersonView() {
        return personView;
    }

    /**
     * 个人信息菜单的监听器
     */
    private UserState.MenuPersonViewListener personViewListener = new UserState.MenuPersonViewListener() {
        @Override
        public void showLoginPersonView() {
            ((TextView) personView).setText("已登录");
        }

        @Override
        public void showLogoutPersonView() {
            ((TextView) personView).setText("未登录");
        }

        @Override
        public void onClickLoginPersonView() {
            mainActivity.startPersonalCenter();
        }

        @Override
        public void onClickLogoutPersonView() {
            mainActivity.startLoginActivity();
        }
    };

    @Override
    public void setPresenter(SwitchFunctionContract.Presenter presenter) {
        mPresenter = (SwitchFunctionPresenter) presenter;
    }

    @Override
    public void showLoading(String value) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showReqResult(String value) {

    }

    @Override
    public boolean isActive() {
        return false;
    }
}
