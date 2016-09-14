package com.jkb.mrcampus.base;

import com.jkb.mrcampus.fragment.dialog.ShareDynamicDialogFragment;
import com.jkb.mrcampus.fragment.dialog.WriteDynamicDialogFragment;

/**
 * Activity基类的动作接口
 * Created by JustKiddingBaby on 2016/9/6.
 */

public interface BaseActivityAction {
    /**
     * 显示写动态的视图
     */
    void showWriteDynamicView(WriteDynamicDialogFragment.OnWriteDynamicClickListener listener);

    /**
     * 显示分享动态的视图
     */
    void showShareDynamicView(ShareDynamicDialogFragment.OnShareItemClickListener listener);

    /**
     * 取消显示所有视图
     */
    void dismiss();
}
