package com.jkb.mrcampus.fragment.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.menu.SwitchFunctionContract;
import com.jkb.core.control.messageState.MessageObservable;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.model.info.SchoolInfoSingleton;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.base.BaseFragment;

import java.util.Observable;
import java.util.Observer;

import de.hdodenhof.circleimageview.CircleImageView;
import jkb.mrcampus.db.entity.Schools;

/**
 * 左滑菜单：切换功能的View
 * Created by JustKiddingBaby on 2016/7/24.
 */
public class SwitchFunctionFragment extends BaseFragment
        implements SwitchFunctionContract.View,
        View.OnClickListener, Observer {

    private SwitchFunctionContract.Presenter mPresenter;
    private MainActivity mainActivity;

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

    //头像是否加载
    private boolean isHeadImgLoaded = false;

    /**
     * 获得一个实例化的SwitchFunctionFragment对象
     */
    public static SwitchFunctionFragment newInstance() {
        return new SwitchFunctionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_menu_switchfunction);
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
        rootView.findViewById(R.id.fms_ll_person).setOnClickListener(this);//用戶藍點擊監聽

        rootView.findViewById(R.id.fms_content_menuIndex).setOnClickListener(showClickListener);
        rootView.findViewById(R.id.fms_content_menuSetting).setOnClickListener(showClickListener);
        rootView.findViewById(R.id.fms_content_menuTools).setOnClickListener(showClickListener);
        rootView.findViewById(R.id.fms_content_menuTopic).setOnClickListener(showClickListener);

        rootView.findViewById(R.id.fms_content_menuMessage).setOnClickListener(startClickListener);
        rootView.findViewById(R.id.fms_ll_school).setOnClickListener(startClickListener);
        rootView.findViewById(R.id.fms_ll_selectSchool).setOnClickListener(startClickListener);
        rootView.findViewById(R.id.fms_content_menuMap).setOnClickListener(startClickListener);

        rootView.findViewById(R.id.fms_iv_topSearch).setOnClickListener(topMenuClickListener);
        rootView.findViewById(R.id.fms_iv_topLike).setOnClickListener(topMenuClickListener);
        rootView.findViewById(R.id.fms_iv_topCircle).setOnClickListener(topMenuClickListener);

        //设置选择学校的监听器
        SchoolInfoSingleton.getInstance().addObserver(schoolSelectedObserver);
        LoginContext.getInstance().addObserver(loginObserver);
        //设置其为消息的观察者
        MessageObservable.newInstance().addObserver(this);
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
     * 顶部菜单点击的监听器
     */
    private View.OnClickListener topMenuClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fms_iv_topSearch://搜索
                    mainActivity.startSearch();
                    break;
                case R.id.fms_iv_topLike://我的喜欢
                    mPresenter.onMyFavoriteClick();
                    break;
                case R.id.fms_iv_topCircle://圈子
                    mPresenter.onCircleListClick();
                    break;
            }
        }
    };

    /**
     * 打开视图的监听器
     */
    private View.OnClickListener startClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fms_content_menuMap:
                    mainActivity.startMapView();
                    break;
                case R.id.fms_content_menuMessage:
                    mainActivity.startMessageView();
                    break;
                case R.id.fms_ll_school:
                case R.id.fms_ll_selectSchool:
                    mainActivity.startChooseSchoolsView();
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
            mainActivity.hideMenuView();//隐藏侧滑菜单
            mainActivity.hideAllView();//隐藏其余的页面视图
            switch (v.getId()) {
                case R.id.fms_content_menuIndex:
                    setItemSelected(0);
                    mainActivity.showIndexView();
                    break;
                case R.id.fms_content_menuSetting:
                    setItemSelected(5);
                    mainActivity.showSettingView();
                    break;
                case R.id.fms_content_menuTools:
                    setItemSelected(3);
                    mainActivity.showToolsView();
                    break;
                case R.id.fms_content_menuTopic:
                    setItemSelected(2);
                    mainActivity.showSpecialModelView();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fms_ll_person:
                mPresenter.onUserViewClick();
                break;
        }
    }

    /**
     * 设置被选中的状态
     */
    private void setItemSelected(int position) {
        clearItemSelector();
        itemSelector = position;
        tvItems[position].setTextColor(selectedColorId);
    }

    /**
     * 清楚按钮选中的效果
     */
    private void clearItemSelector() {
        for (int i = 0; i < tvItemsId.length; i++) {
            tvItems[i].setTextColor(normalColorId);
        }
    }

    @Override
    public void showSchoolView(String schoolName, String schoolBadge, String summary) {
        rootView.findViewById(R.id.fms_ll_school).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.fms_ll_selectSchool).setVisibility(View.GONE);
        ((TextView) rootView.findViewById(R.id.fms_tv_schoolName)).setText(schoolName);
        ((TextView) rootView.findViewById(R.id.fms_tv_schoolArea)).setText(summary);
        ImageView ivBadge = (ImageView) rootView.findViewById(R.id.fms_iv_schoolBadge);
        ImageLoaderFactory.getInstance().displayImage(ivBadge, schoolBadge);
    }

    @Override
    public void hideSchoolView() {
        rootView.findViewById(R.id.fms_ll_school).setVisibility(View.GONE);
        rootView.findViewById(R.id.fms_ll_selectSchool).setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoginView() {
        ((TextView) rootView.findViewById(R.id.fms_tv_nickName)).
                setText(mPresenter.getCurrentNickName());
        String headImg = mPresenter.getCurrentHeadImg();
        LogUtils.d(TAG, "headImg=" + headImg);
        if (StringUtils.isEmpty(headImg)) {
            isHeadImgLoaded = false;
            ivHeadImg.setImageResource(R.drawable.ic_user_head);
        } else {
            if (!isHeadImgLoaded) {
                ivHeadImg.setTag(null);
            }
            isHeadImgLoaded = true;
            ImageLoaderFactory.getInstance().displayImage(ivHeadImg, headImg);
        }
    }

    @Override
    public void showLogoutView() {
        //设置未登录头像
        isHeadImgLoaded = false;
        ((TextView) rootView.findViewById(R.id.fms_tv_nickName)).setText(R.string.unLogin);
        ivHeadImg.setImageResource(R.drawable.ic_user_head);
    }

    @Override
    public void startCircleList(@NonNull int user_id) {
        mainActivity.startCircleListActivity(user_id);
    }

    @Override
    public void startLoginActivity() {
        mainActivity.startLoginActivity();
    }

    @Override
    public void startPersonCenterActivity(@NonNull int user_id) {
        mainActivity.startPersonalCenterActivity(user_id);
    }

    @Override
    public void startMyFavoriteActivity(@NonNull int user_id) {
        mainActivity.startMyFavoriteActivity(user_id);
    }


    @Override
    public void setPresenter(SwitchFunctionContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        mainActivity.showLoading(value, this);
    }

    @Override
    public void dismissLoading() {
        mainActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        mainActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivity = null;
        tvItems = null;
        ivHeadImg = null;
        MessageObservable.newInstance().deleteObserver(this);
        LoginContext.getInstance().deleteObserver(loginObserver);
        SchoolInfoSingleton.getInstance().deleteObserver(schoolSelectedObserver);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVED_ITEMS_SELECTER, itemSelector);
    }

    /**
     * 登录状态的更新回调方法
     */
    private Observer loginObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            boolean logined = LoginContext.getInstance().isLogined();
            if (logined) {
                showLoginView();
            } else {
                showLogoutView();
            }
        }
    };
    /**
     * 选择学校的通知方法
     */
    private Observer schoolSelectedObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            boolean selectedSchool = SchoolInfoSingleton.getInstance().isSelectedSchool();
            Schools schools = SchoolInfoSingleton.getInstance().getSchool();
            if (selectedSchool) {
                LogUtils.i(TAG, "school_id=" + schools.getSchool_id());
                LogUtils.i(TAG, "school_name=" + schools.getSchool_name());
                LogUtils.i(TAG, "school_badge=" + schools.getBadge());
                String badge = schools.getBadge();
                String school_name = schools.getSchool_name();
                String summary = schools.getSummary();
                showSchoolView(school_name, badge, summary);
            } else {
                hideSchoolView();
            }
        }
    };

    @Override
    public void update(Observable o, Object arg) {
        int allUnReadMessageCount = MessageObservable.newInstance().getAllUnReadMessageCount();
        LogUtils.d(TAG, "functionMenu---unReadMessageCount->" + allUnReadMessageCount);
        if (allUnReadMessageCount > 0) {
            rootView.findViewById(R.id.fms_iv_menuMessageNotifyPoint).setVisibility(View.VISIBLE);
        } else {
            rootView.findViewById(R.id.fms_iv_menuMessageNotifyPoint).setVisibility(View.GONE);
        }
    }
}
