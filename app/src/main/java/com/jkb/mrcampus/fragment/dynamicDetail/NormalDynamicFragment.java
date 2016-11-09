package com.jkb.mrcampus.fragment.dynamicDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;
import com.jkb.core.contract.dynamicDetail.normal.DynamicDetailNormalContract;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.TimeUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.CommentActivity;
import com.jkb.mrcampus.activity.DynamicDetailActivity;
import com.jkb.mrcampus.adapter.recycler.dynamicDetail.comment.DynamicCommentAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.ShareDynamicDialogFragment;

import java.util.List;

/**
 * 普通的动态类型
 * Created by JustKiddingBaby on 2016/9/14.
 */

public class NormalDynamicFragment extends BaseFragment
        implements DynamicDetailNormalContract.View, SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener {

    //data
    private int dynamic_id = -1;

    public static NormalDynamicFragment newInstance(@NonNull int dynamic_id) {
        NormalDynamicFragment INSTANCE = new NormalDynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.INTENT_KEY_DYNAMIC_ID, dynamic_id);
        INSTANCE.setArguments(bundle);
        return INSTANCE;
    }

    //data
    private static final String SAVED_DYNAMIC_ID = "saved_dynamic_id";
    private DynamicDetailActivity dynamicDetailActivity;
    private DynamicDetailNormalContract.Presenter mPresenter;
    private DynamicCommentAdapter dynamicCommentAdapter;
    private LinearLayoutManager linearLayoutManager;

    private static final int MAX_COMMENT_COUNT = 360;

    //View
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView commentRecyclerView;
    //图片
    private ImageView contentImgs[];
    private int contentImgsId[] = new int[]{
            R.id.fddn_iv_pic1, R.id.fddn_iv_pic2,
            R.id.fddn_iv_pic3, R.id.fddn_iv_pic4,
            R.id.fddn_iv_pic5, R.id.fddn_iv_pic6};
    private EditText etCommentInput;
    private TextView tvCommentCount;
    private TextView tvCommentRemainderCount;
    private ImageView ivSendComment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dynamicDetailActivity = (DynamicDetailActivity) mActivity;
        setRootView(R.layout.frg_dynamic_detail_normal);
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
        refreshLayout.setOnRefreshListener(this);
        //标题栏的点击事件
        rootView.findViewById(R.id.ts6_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.ts6_ib_right_0).setOnClickListener(this);
        rootView.findViewById(R.id.ts6_ib_right_1).setOnClickListener(this);
        rootView.findViewById(R.id.ts6_ib_right_2).setOnClickListener(this);

        rootView.findViewById(R.id.ffdn_ll_sendComment).setOnClickListener(this);
        //评论列表
        rootView.findViewById(R.id.fddn_content_commentList).setOnClickListener(this);
        rootView.findViewById(R.id.fddn_commentNone).setOnClickListener(this);
        rootView.findViewById(R.id.fddn_ll_allComment).setOnClickListener(this);
        commentRecyclerView.setOnClickListener(this);

        //评论
        dynamicCommentAdapter.setOnLikeClickListener(onLikeClickListener);
        dynamicCommentAdapter.setOnHeadImgClickListener(onHeadImgClickListener);

        //设置评论的内容数目的监听器
        etCommentInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int nSelLength = etCommentInput.getText().toString().length();
                if (nSelLength > 0) {
                    ivSendComment.setImageResource(R.drawable.ic_comment_send);
                } else {
                    ivSendComment.setImageResource(R.drawable.ic_comment_send_gray);
                }
                tvCommentCount.setText(nSelLength + "");
                tvCommentRemainderCount.setText(String.valueOf(String
                        .valueOf(MAX_COMMENT_COUNT - nSelLength)));
            }

            @Override
            public void afterTextChanged(Editable s) {
                int nSelStart;
                int nSelEnd;
                nSelStart = etCommentInput.getSelectionStart();
                nSelEnd = etCommentInput.getSelectionEnd();
                boolean nOverMaxLength;
                nOverMaxLength = (s.length() > MAX_COMMENT_COUNT);
                if (nOverMaxLength) {
                    s.delete(nSelStart - 1, nSelEnd);
                    etCommentInput.setTextKeepState(s);
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //设置软键盘不弹出
        dynamicDetailActivity.hideSoftKeyboard(etCommentInput);
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            dynamic_id = arguments.getInt(Config.INTENT_KEY_DYNAMIC_ID);
        } else {
            dynamic_id = savedInstanceState.getInt(SAVED_DYNAMIC_ID);//恢复数据
        }
        dynamicCommentAdapter = new DynamicCommentAdapter(context, null);
        commentRecyclerView.setAdapter(dynamicCommentAdapter);
    }

    @Override
    protected void initView() {
        dynamicDetailActivity.hideSoftInputView();//隐藏软键盘的显示
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fddn_srl);
        //初始化评论列表
        commentRecyclerView = (RecyclerView) rootView.findViewById(R.id.fddn_rv_comment);
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commentRecyclerView.setLayoutManager(linearLayoutManager);
        commentRecyclerView.setNestedScrollingEnabled(false);
        //初始化图片
        contentImgs = new ImageView[contentImgsId.length];
        for (int i = 0; i < contentImgsId.length; i++) {
            contentImgs[i] = (ImageView) rootView.findViewById(contentImgsId[i]);
        }
        //初始化输入
        etCommentInput = (EditText) rootView.findViewById(R.id.fddn_et_commentInput);
        //初始化其他
        tvCommentCount = (TextView) rootView.findViewById(R.id.fddn_tv_inputCount);
        tvCommentRemainderCount = (TextView) rootView.findViewById(R.id.fddn_tv_inputRemainderCount);
        ivSendComment = (ImageView) rootView.findViewById(R.id.fddn_iv_sendComment);
    }


    @Override
    public int bindDynamicId() {
        return this.dynamic_id;
    }

    @Override
    public void setContentImages(String[] imageUrls) {
        //设置图片张数等等
        initContentImgs(imageUrls);
        //设置图片
        if (imageUrls == null) {
            return;
        }
        for (int i = 0; i < imageUrls.length; i++) {
            ImageLoaderFactory.getInstance().displayImage(contentImgs[i], imageUrls[i]);
        }
    }

    @Override
    public void hideNormalContentView() {
        rootView.findViewById(R.id.fddn_contentView).setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNormalContentView() {
        rootView.findViewById(R.id.fddn_contentView).setVisibility(View.VISIBLE);
    }

    /**
     * 初始化图片张数
     */
    private void initContentImgs(String[] imageUrls) {
        View contentImgAll = rootView.findViewById(R.id.fddn_ll_pictures);
        View contentImg1To3 = rootView.findViewById(R.id.fddn_ll_pic1To3);
        View contentImg2To3 = rootView.findViewById(R.id.fddn_ll_pic2To3);
        View contentImg4To6 = rootView.findViewById(R.id.fddn_ll_pic4To6);
        //设置图
        contentImgAll.setVisibility(View.GONE);
        contentImg1To3.setVisibility(View.GONE);
        contentImg2To3.setVisibility(View.GONE);
        contentImg4To6.setVisibility(View.GONE);
        int picNum = 0;
        if (imageUrls != null) {
            picNum = imageUrls.length;
        }
        //如果没图片不执行下面动作
        if (picNum <= 0) {
            return;
        }
        //有图片时肯定至少一张
        contentImgAll.setVisibility(View.VISIBLE);
        contentImg1To3.setVisibility(View.VISIBLE);
        if (picNum >= 1) {
            contentImgs[0].setVisibility(View.VISIBLE);
        }
        if (picNum >= 2) {
            contentImg2To3.setVisibility(View.VISIBLE);
            contentImgs[1].setVisibility(View.VISIBLE);
            contentImgs[2].setVisibility(View.INVISIBLE);
        }
        if (picNum >= 3) {
            contentImgs[2].setVisibility(View.VISIBLE);
        }
        if (picNum >= 4) {
            contentImg4To6.setVisibility(View.VISIBLE);
            contentImgs[3].setVisibility(View.VISIBLE);
            contentImgs[4].setVisibility(View.INVISIBLE);
            contentImgs[5].setVisibility(View.INVISIBLE);
        }
        if (picNum >= 5) {
            contentImgs[4].setVisibility(View.VISIBLE);
        }
        if (picNum >= 6) {
            contentImgs[5].setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setContentValue(String contentValue) {
        TextView tvContent = (TextView) rootView.findViewById(R.id.fddn_tv_content);
        tvContent.setText(contentValue);
    }

    @Override
    public void setUserHeadImg(String userHeadImg) {
        ImageView headImg = (ImageView) rootView.findViewById(R.id.fddn_iv_headImg);
        ImageLoaderFactory.getInstance().displayImage(headImg, userHeadImg);
    }

    @Override
    public void setUserNickName(String userNickName) {
        TextView tvUserName = (TextView) rootView.findViewById(R.id.fddn_tv_name);
        TextView tvAuth = (TextView) rootView.findViewById(R.id.fddn_tv_auth);
        tvUserName.setText(userNickName);
        tvAuth.setText(userNickName);
    }

    @Override
    public void setPostTime(String postTime) {
        TextView tvTime = (TextView) rootView.findViewById(R.id.fddn_tv_time);
        TextView tvDaytime = (TextView) rootView.findViewById(R.id.fddn_tv_dayTime);
        tvTime.setText(postTime);
        tvDaytime.setText(TimeUtils.changeTimeToDay(postTime));
    }

    @Override
    public void setCommentCount(int commentCount) {
        TextView tvCommentCount = (TextView) rootView.findViewById(R.id.fddn_tv_commentCount);
        TextView tvTitleCommentCount = (TextView) rootView.findViewById(R.id.ts6_tv_right_1);
        tvCommentCount.setText(commentCount + "");
        tvTitleCommentCount.setText(commentCount + "");
    }

    @Override
    public void setFavoriteCount(int favoriteCount) {
        TextView tvTitleFavoriteCount = (TextView) rootView.findViewById(R.id.ts6_tv_right_0);
        if (favoriteCount > 0) {
            tvTitleFavoriteCount.setText(favoriteCount + "");
        } else {
            tvTitleFavoriteCount.setText("");
        }
    }

    @Override
    public void setHasFavorite(boolean hasFavorite) {
        ImageView favorite = (ImageView) rootView.findViewById(R.id.ts6_ib_right_0);
        if (hasFavorite) {
            favorite.setImageResource(R.drawable.ic_heart_red);
        } else {
            favorite.setImageResource(R.drawable.ic_heart_black);
        }
    }

    @Override
    public void setTag(String tag) {
        TextView tvTag = (TextView) rootView.findViewById(R.id.fddn_tv_tag);
        tvTag.setText(tag);
    }

    @Override
    public void setTagCount(int tagCount) {
        TextView tvTagCount = (TextView) rootView.findViewById(R.id.fddn_tv_tagNum);
        tvTagCount.setText(tagCount + "");
    }

    @Override
    public void showRefreshView() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshView() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showNoneCommentView() {
        Log.d(TAG, "showNoneCommentView");
        rootView.findViewById(R.id.fddn_commentNone).setVisibility(View.VISIBLE);
        commentRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setCommentData(List<DynamicDetailCommentData> commentsData) {
        if (commentsData == null || commentsData.size() == 0) {
            showNoneCommentView();
            return;
        }
        Log.d(TAG, "setCommentData");
        rootView.findViewById(R.id.fddn_commentNone).setVisibility(View.GONE);
        commentRecyclerView.setVisibility(View.VISIBLE);
        //设置数据
        dynamicCommentAdapter.commentsData = commentsData;
        dynamicCommentAdapter.notifyDataSetChanged();
    }

    @Override
    public void commitComment() {
        String comment = etCommentInput.getText().toString();
        mPresenter.sendComment(comment);
    }

    @Override
    public void clearComment$HideSoftInputView() {
        etCommentInput.setText("");//清楚文本框信息
        dynamicDetailActivity.hideSoftKeyboard(etCommentInput);//设置软键盘不可见
    }

    @Override
    public void startCommentListView() {
        Log.d(TAG, "startCommentListView");
        dynamicDetailActivity.startCommentListActivity(dynamic_id);
    }

    @Override
    public void setPresenter(DynamicDetailNormalContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        if (!isHidden()) dynamicDetailActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        dynamicDetailActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        dynamicDetailActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dynamicDetailActivity = null;
        refreshLayout = null;
        commentRecyclerView = null;
        dynamicCommentAdapter = null;
        contentImgs = null;
        tvCommentCount = null;
        tvCommentRemainderCount = null;
        ivSendComment = null;
        linearLayoutManager = null;
        dynamicCommentAdapter = null;
        linearLayoutManager = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_DYNAMIC_ID, dynamic_id);
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts6_ib_left://返回
                dynamicDetailActivity.onBackPressed();
                break;
            case R.id.ts6_ib_right_0://喜欢
                mPresenter.onLikeDynamicClick();
                break;
            case R.id.ts6_ib_right_1://评论
                startCommentListView();
                break;
            case R.id.ts6_ib_right_2://更多
                dynamicDetailActivity.showShareDynamicView(onShareItemClickListener);
                break;
            case R.id.ffdn_ll_sendComment://提交
                commitComment();
                break;
            case R.id.fddn_content_commentList://评论区域
            case R.id.fddn_commentNone:
            case R.id.fddn_rv_comment:
            case R.id.fddn_ll_allComment:
                startCommentListView();
                break;
        }
    }

    /**
     * 分享的点击监听事件
     */
    private ShareDynamicDialogFragment.OnShareItemClickListener onShareItemClickListener = new
            ShareDynamicDialogFragment.OnShareItemClickListener() {
                @Override
                public void onWechatClick() {

                }

                @Override
                public void onWechatCircleClick() {

                }

                @Override
                public void onQQClick() {

                }

                @Override
                public void onQQZoneClick() {

                }

                @Override
                public void onSinaClick() {

                }

                @Override
                public void onCircleClick() {

                }
            };
    /**
     * 设置点击喜欢的点击事件监听
     */
    private DynamicCommentAdapter.OnLikeClickListener onLikeClickListener = new
            DynamicCommentAdapter.OnLikeClickListener() {
                @Override
                public void onLikeClick(int position) {
                    mPresenter.onLikeCommentClick(position);
                }
            };
    /**
     * 设置点击喜欢的点击事件监听
     */
    private DynamicCommentAdapter.OnHeadImgClickListener onHeadImgClickListener = new
            DynamicCommentAdapter.OnHeadImgClickListener() {
                @Override
                public void onHeadImgClick(int position) {
                    int user_id = mPresenter.getUser_id(position);
                    dynamicDetailActivity.startPersonalCenterActivity(user_id);
                }
            };
}
