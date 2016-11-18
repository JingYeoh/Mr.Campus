package com.jkb.mrcampus.adapter.pager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.LogUtils;
import com.jkb.mrcampus.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * 大图浏览的适配器
 * Created by JustKiddingBaby on 2016/11/17.
 */

public class ImageBrowserAdapter extends PagerAdapter {

    private Context context;
    public ArrayList<String> picUrls;
    private ArrayList<View> picViews;

    //
//    private List<PhotoViewAttacher> mAttachers;

    public ImageBrowserAdapter(Context context, ArrayList<String> picUrls) {
        this.context = context;
        if (picUrls == null) {
            picUrls = new ArrayList<>();
        }
        LogUtils.d(ImageBrowserAdapter.class, "picUrls size=" + picUrls.size());
        this.picUrls = picUrls;
        initImgs();
    }

    /**
     * 初始化图片
     */
    private void initImgs() {
        picViews = new ArrayList<>();
//        mAttachers = new ArrayList<>();
        for (int i = 0; i < picUrls.size(); i++) {
            // 填充显示图片的页面布局
            View view = View.inflate(context, R.layout.item_large_picture, null);
            picViews.add(view);
//            mAttachers.add(null);
        }
    }

    @Override
    public int getCount() {
        /*if (picUrls.size() > 1) {
            return Integer.MAX_VALUE;
        }*/
        return picUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        int index = position % picViews.size();
        View view = picViews.get(index);
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
//            parent.removeViewInLayout(view);
            parent.removeView(view);
        }
        PhotoView iv_image_browser = (PhotoView) view.findViewById(R.id.ilp_iv_image_browser);
        iv_image_browser.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        mAttachers.set(position, new PhotoViewAttacher(iv_image_browser));
        String picUrl = picUrls.get(index);
        LogUtils.d(ImageBrowserAdapter.class, "position=" + position);
        LogUtils.d(ImageBrowserAdapter.class, "picUrl=" + picUrl);
        loadLargePicture(picUrl, iv_image_browser, position);
        container.addView(picViews.get(index));
        return picViews.get(index);
    }

    /**
     * 加载大图
     */
    private void loadLargePicture(String picUrl, final PhotoView imageView, final int position) {
        ImageLoaderFactory.getInstance().loadImage(picUrl, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onLoadingFailed(String imageUri, View view,
                                        FailReason failReason) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                /*float scale = (float) loadedImage.getHeight() / loadedImage.getWidth();

                int screenWidthPixels = DisplayUtils.getScreenWidthPixels((Activity) context);
                int screenHeightPixels = DisplayUtils.getScreenHeightPixels((Activity) context);
                int height = (int) (screenWidthPixels * scale);
                if (height < screenHeightPixels) {
                    height = screenHeightPixels;
                }
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                params.height = height;
                params.width = screenWidthPixels;*/
                imageView.setImageBitmap(loadedImage);
//                mAttachers.get(position).update();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(picViews.get(position % picViews.size()));
//        mAttachers.get(position).cleanup();
//        mAttachers.remove(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    /**
     * 得到当前图片
     */
    public String getPic(int position) {
        return picUrls.get(position % picUrls.size());
    }

    /**
     * 返回Bitmap实例
     */
    public Bitmap getBitmap(int position) {
        Bitmap bitmap = null;
        View view = picViews.get(position % picViews.size());
        ImageView iv_image_browser = (ImageView) view.findViewById(R.id.ilp_iv_image_browser);
        Drawable drawable = iv_image_browser.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            bitmap = bd.getBitmap();
        }
        return bitmap;
    }
}
