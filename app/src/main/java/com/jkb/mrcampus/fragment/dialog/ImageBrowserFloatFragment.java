package com.jkb.mrcampus.fragment.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.jkb.model.utils.BitmapUtils;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.adapter.pager.ImageBrowserAdapter;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 查看大图的浮动视图
 * Created by JustKiddingBaby on 2016/9/14.
 */

public class ImageBrowserFloatFragment extends DialogFragment implements View.OnClickListener {

    //data
    private View rootView;
    private Activity mActivity;
    private Dialog mDialog;

    private ArrayList<String> imageUrls;
    private int currentImage = 0;

    //view
    private ViewPager viewPager;
    private TextView tvGuidePoint;
    private ImageBrowserAdapter imageBrowserAdapter;

    public static ImageBrowserFloatFragment newInstance(
            ArrayList<String> imageUrls, int currentImage) {
        Bundle args = new Bundle();
        args.putSerializable(Config.BUNDLE_KEY_IMAGE_BROWER, imageUrls);
        args.putInt(Config.BUNDLE_KEY_CURRENT_IMAGE, currentImage);
        LogUtils.d(ImageBrowserFloatFragment.class, "imageUrls size=" + imageUrls.size());
        LogUtils.d(ImageBrowserFloatFragment.class, "currentImage=" + currentImage);
        ImageBrowserFloatFragment fragment = new ImageBrowserFloatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mActivity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.frg_dialog_image_brower, null);
        builder.setView(rootView);
        mDialog = builder.create();
        initConfig();//初始化配置
        //初始化操作
        init(savedInstanceState);
        return mDialog;
    }

    /**
     * 初始化
     */
    private void init(Bundle savedInstanceState) {
        initView();
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            imageUrls = (ArrayList<String>) args.getSerializable(Config.BUNDLE_KEY_IMAGE_BROWER);
            currentImage = args.getInt(Config.BUNDLE_KEY_CURRENT_IMAGE);
        } else {
            imageUrls = (ArrayList<String>) savedInstanceState
                    .getSerializable(Config.BUNDLE_KEY_IMAGE_BROWER);
            currentImage = savedInstanceState.getInt(Config.BUNDLE_KEY_CURRENT_IMAGE);
        }
        initData(savedInstanceState);
        initListener();
    }

    /**
     * 初始化数据
     */
    private void initData(Bundle savedInstanceState) {
        imageBrowserAdapter = new ImageBrowserAdapter(mActivity, imageUrls);
        viewPager.setAdapter(imageBrowserAdapter);

        final int size = imageUrls.size();

        if (size > 1) {
            tvGuidePoint.setVisibility(View.VISIBLE);
            tvGuidePoint.setText((currentImage + 1) + "/" + size);
        } else {
            tvGuidePoint.setVisibility(View.GONE);
        }
        viewPager.setOnPageChangeListener(onPageChangeListener);
//        int initPosition = Integer.MAX_VALUE / 2 / size * size + currentImage;
        if (currentImage < 0) {
            currentImage = 0;
        }
        viewPager.setCurrentItem(currentImage);
    }

    /**
     * 初始化View
     */
    private void initView() {
        viewPager = (ViewPager) rootView.findViewById(R.id.vp_image_brower);
        tvGuidePoint = (TextView) rootView.findViewById(R.id.tv_image_index);
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        rootView.findViewById(R.id.btn_save).setOnClickListener(this);
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        mDialog.setCanceledOnTouchOutside(true);
        //设置动画
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.animate_dialog_bottomIn);
        //设置宽度和高度为全屏
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置为全屏
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDialog.getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
        mDialog = null;
        rootView = null;
        viewPager = null;
        onPageChangeListener = null;
        tvGuidePoint = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                //在此使用异步
                saveBitmapToDesc();
                break;
        }
    }

    /**
     * 保存到手机中
     */
    private void saveBitmapToDesc() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Bitmap bitmap = imageBrowserAdapter.getBitmap(viewPager.getCurrentItem());
                String result = BitmapUtils.saveBitmapWithName(
                        com.jkb.api.config.Config.PATH_ROOT_IMAGE +
                                com.jkb.api.config.Config.PATH_DOWNLOAD,
                        BitmapUtils.getSystemTime(), bitmap);
                subscriber.onNext(result);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast("图片保存失败");
                    }

                    @Override
                    public void onNext(String result) {
                        if (StringUtils.isEmpty(result)) {
                            showToast("图片保存失败");
                        } else {
                            showToast("图片保存成功");
                        }
                    }
                });
    }

    /**
     * 滑动切换的监听器
     */
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int index = position % imageUrls.size();
            tvGuidePoint.setText((index + 1) + "/" + imageUrls.size());
//            String pic = imageBrowserAdapter.getPic(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 显示Toast
     */
    private void showToast(String value) {
        Toast.makeText(getContext(), value, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Config.BUNDLE_KEY_IMAGE_BROWER, imageUrls);
    }
}
