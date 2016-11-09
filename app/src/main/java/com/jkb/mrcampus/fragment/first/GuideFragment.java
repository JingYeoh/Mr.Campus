package com.jkb.mrcampus.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.FirstActivity;
import com.jkb.mrcampus.adapter.pager.GuidePagerAdapter;
import com.jkb.mrcampus.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页的Fragment：用于更新或者安装时候的引导应用
 * Created by JustKiddingBaby on 2016/7/21.
 */
public class GuideFragment extends BaseFragment implements View.OnClickListener {


    public GuideFragment() {
    }

    /**
     * 获得一个实例化的GuideFragment对象
     */
    public static GuideFragment newInstance() {
        return new GuideFragment();
    }

    private FirstActivity firstActivity;

    //view
    private ViewPager viewPager;
    private LinearLayout mDotsLayout;
    private ImageButton mBtn;
    private View guideContent;

    //data
    private GuidePagerAdapter guidePagerAdapter;
    private List<View> viewList;
    private int[] images = new int[]{
            R.drawable.ic_guide_01,
            R.drawable.ic_guide_02,
            R.drawable.ic_guide_03,
            R.drawable.ic_guide_04,
            R.drawable.ic_guide_05,
    };
    private int[] colors;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        firstActivity = (FirstActivity) mActivity;
        setRootView(R.layout.frg_first_guide);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    protected void initListener() {
        viewPager.setOnPageChangeListener(onPageChangeListener);
        mBtn.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initBgColors();
        initPager();
        guideContent.setBackgroundColor(colors[0]);
        guidePagerAdapter = new GuidePagerAdapter(viewList, images);
        viewPager.setAdapter(guidePagerAdapter);
    }

    @Override
    protected void initView() {
        viewPager = (ViewPager) rootView.findViewById(R.id.guide_viewpager);
        mDotsLayout = (LinearLayout) rootView.findViewById(R.id.guide_dots);
        mBtn = (ImageButton) rootView.findViewById(R.id.guide_btn);
        guideContent = rootView.findViewById(R.id.guide_content);
    }

    /**
     * 初始化背景颜色
     */
    private void initBgColors() {
        int[] bgImages = new int[]{
                R.color.guide_1,
                R.color.guide_2,
                R.color.guide_3,
                R.color.guide_4,
                R.color.guide_5
        };
        colors = new int[bgImages.length];
        for (int i = 0; i < bgImages.length; i++) {
            colors[i] = getResources().getColor(bgImages[i]);
        }
    }

    /**
     * 初始化ViewPager
     */
    private void initPager() {
        viewList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView img = new ImageView(mActivity);
            viewList.add(img);
        }
        initDots(images.length);
    }

    /**
     * 初始化引导的指针
     */
    private void initDots(int count) {
        for (int j = 0; j < count; j++) {
            mDotsLayout.addView(initDot());
        }
        mDotsLayout.getChildAt(0).setSelected(true);
    }

    /**
     * 初始化点的view
     */
    private View initDot() {
        return LayoutInflater.from(context).inflate(R.layout.layout_dot, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        firstActivity = null;
        guidePagerAdapter = null;
        onPageChangeListener = null;
        mDotsLayout = null;
        guideContent = null;
        mBtn = null;
        if (viewList != null) {
            viewList.clear();
            viewList = null;
        }
    }

    /**
     * 改变导航点的状态
     */
    private void changeDotState(final int position) {
        for (int i = 0; i < mDotsLayout.getChildCount(); i++) {
            if (i == position) {
                mDotsLayout.getChildAt(i).setSelected(true);
            } else {
                mDotsLayout.getChildAt(i).setSelected(false);
            }
        }
    }

    /**
     * pager变化的监听器
     */
    private ViewPager.OnPageChangeListener onPageChangeListener =
            new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int arg0) {
                    // TODO Auto-generated method stub
                    changeDotState(arg0);
                    if (arg0 == mDotsLayout.getChildCount() - 1) {
                        mBtn.setVisibility(View.VISIBLE);
                    } else {
                        mBtn.setVisibility(View.GONE);
                    }
                    guideContent.setBackgroundColor(colors[arg0]);
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                    // TODO Auto-generated method stub
                }
            };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guide_btn:
                //打开首页
                firstActivity.startMenuActivity();
                break;
        }
    }
}
