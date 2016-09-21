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
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.base.BaseFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 左滑菜单：切换功能的View
 * Created by JustKiddingBaby on 2016/7/24.
 */
public class SwitchFunctionFragment extends BaseFragment implements SwitchFunctionContract.View {

    private SwitchFunctionContract.Presenter mPresenter;
    private MainActivity mainActivity;

    //个人中心的视图对象
    private View personView;

    private CircleImageView ivHeadImg;
    private TextView[] tvItems;
    private int[] tvItemsId = new int[]{
            R.id.fms_tv_menuIndex,
            R.id.fms_tv_menuMap,
            R.id.fms_tv_menuTopic,
            R.id.fms_tv_menuTools,
            R.id.fms_tv_menuMessage,
            R.id.fms_tv_menuSetting
    };
    private int selectedColorId;
    private int normalColorId;

    //要恢复的数据
    private static final String SAVED_ITEMS_SELECTER = "savedItemsSelecter";
    private int itemSelector = 0;

    public SwitchFunctionFragment() {
    }

    /**
     * 获得一个实例化的SwitchFunctionFragment对象
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.fms_tv_menuIndex).setOnClickListener(showClickListener);
        rootView.findViewById(R.id.fms_tv_menuSetting).setOnClickListener(showClickListener);
        rootView.findViewById(R.id.fms_tv_menuTools).setOnClickListener(showClickListener);
        rootView.findViewById(R.id.fms_tv_menuTopic).setOnClickListener(showClickListener);

        rootView.findViewById(R.id.fms_tv_menuMessage).setOnClickListener(startClickListener);
        rootView.findViewById(R.id.fms_ll_school).setOnClickListener(startClickListener);
        rootView.findViewById(R.id.fms_tv_menuMap).setOnClickListener(startClickListener);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            itemSelector = savedInstanceState.getInt(SAVED_ITEMS_SELECTER);
        }
        setItemSelected(itemSelector);//设置显示的字体样式
    }

    @Override
    protected void initView() {
        mainActivity = (MainActivity) mActivity;

        personView = rootView.findViewById(R.id.fms_ll_person);
        ivHeadImg = (CircleImageView) rootView.findViewById(R.id.fms_iv_headImg);

        //初始化View的条目信息
        tvItems = new TextView[tvItemsId.length];
        for (int i = 0; i < tvItemsId.length; i++) {
            tvItems[i] = (TextView) rootView.findViewById(tvItemsId[i]);
        }
        //初始化颜色
        selectedColorId = mActivity.getResources().getColor(R.color.white);
        normalColorId = mActivity.getResources().getColor(R.color.line);
    }

    /**
     * 打开视图的监听器
     */
    private View.OnClickListener startClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fms_tv_menuMap:
                    mainActivity.startMap();
                    break;
                case R.id.fms_tv_menuMessage:
                    mainActivity.startMessage();
                    break;
                case R.id.fms_ll_school:
                    mainActivity.startChooseSchools();
                    break;
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
                case R.id.fms_tv_menuIndex:
                    setItemSelected(0);
                    mainActivity.showIndex();
                    break;
                case R.id.fms_tv_menuSetting:
                    setItemSelected(5);
                    mainActivity.showSetting();
                    break;
                case R.id.fms_tv_menuTools:
                    setItemSelected(3);
                    mainActivity.showTools();
                    break;
                case R.id.fms_tv_menuTopic:
                    setItemSelected(2);
                    mainActivity.showSpecialModel();
                    break;
            }
        }
    };

    /**
     * 设置被选中的状态
     */
    private void setItemSelected(int position) {
        clearItemSelectar();
        itemSelector = position;
        tvItems[position].setTextColor(selectedColorId);
    }

    /**
     * 清楚按钮选中的效果
     */
    private void clearItemSelectar() {
        for (int i = 0; i < tvItemsId.length; i++) {
            tvItems[i].setTextColor(normalColorId);
        }
    }

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
    private UserState.MenuPersonViewListener personViewListener =
            new UserState.MenuPersonViewListener() {
                @Override
                public void showLoginPersonView() {
                    ((TextView) rootView.findViewById(R.id.fms_tv_nickName)).
                            setText(mPresenter.getCurrentNickName());
//                    Bitmap bm = mPresenter.getCurrentHeadImg();
                    String headImg = mPresenter.getCurrentHeadImg();
                    if (StringUtils.isEmpty(headImg)) {
                        ivHeadImg.setImageResource(R.drawable.ic_user_head);
                    } else {
                        ImageLoaderFactory.getInstance().displayImage(ivHeadImg, headImg);
                    }
                }

                @Override
                public void showLogoutPersonView() {
                    //设置未登录头像
                    ((TextView) rootView.findViewById(R.id.fms_tv_nickName)).setText(R.string.unLogin);
                    ivHeadImg.setImageResource(R.drawable.ic_user_head);
                }

                @Override
                public void onClickLoginPersonView() {
                    mainActivity.startPersonalCenterActivity(mPresenter.getUser_id());
                }

                @Override
                public void onClickLogoutPersonView() {
                    mainActivity.startLoginActivity();
                }
            };

    @Override
    public void setPresenter(SwitchFunctionContract.Presenter presenter) {
        mPresenter = presenter;
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
        return isAdded();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVED_ITEMS_SELECTER, itemSelector);
    }
}
