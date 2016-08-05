package com.jkb.mrcampus.helper;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Fragment显示的顺序栈视图
 * Created by JustKiddingBaby on 2016/8/1.
 */
public class FragmentStack implements Serializable {

    private static final String TAG = "FragmentStack";

    public static final String SAVED_FRAGMENT_STACK = "saved_fragment_stack";
    private ArrayList<String> fragmetStackNames = null;

    public FragmentStack() {
        fragmetStackNames = new ArrayList<>();
    }

    public ArrayList<String> getFragmetStackNames() {
        return fragmetStackNames;
    }

    public void setFragmetStackNames(ArrayList<String> fragmetStackNames) {
        this.fragmetStackNames = fragmetStackNames;
    }

    /**
     * 添加到栈内
     *
     * @param fragmentName
     */
    public void addFragmentToStack(String fragmentName) {
        fragmetStackNames.add(fragmentName);
    }

    /**
     * 取消顶部的栈
     */
    public void removePopFragmentStack() {
        if (fragmetStackNames.size() > 0) {
            fragmetStackNames.remove(fragmetStackNames.size() - 1);
        }
    }


    /**
     * 得到当前的Fragment的名字
     */
    public String getCurrentFragmentName() {
        if (fragmetStackNames.size() > 0) {
            Log.d(TAG, "currentFragmentName=" + fragmetStackNames.get(fragmetStackNames.size() - 1));
            return fragmetStackNames.get(fragmetStackNames.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 移除所有的Fragment
     */
    public void clearFragmentStack() {
        fragmetStackNames.clear();
    }
}
