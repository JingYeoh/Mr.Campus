package com.jkb.mrcampus.fragment.comment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.comment.singleAll.CommentSingleAllContract;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentReplyData;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.CommentActivity;
import com.jkb.mrcampus.base.BaseFragment;

import java.util.List;

/**
 * 单挑全部评论的View
 * Created by JustKiddingBaby on 2016/9/20.
 */

public class CommentSingleAllFragment extends BaseFragment implements CommentSingleAllContract.View, SwipeRefreshLayout.OnRefreshListener {

    //data
    private int comment_id = -1;
    private CommentActivity commentActivity;

    private CommentSingleAllFragment(int dynamic_id) {
        this.comment_id = dynamic_id;
    }

    public CommentSingleAllFragment() {
    }

    private static CommentSingleAllFragment INSTANCE = null;

    public static CommentSingleAllFragment newInstance(int dynamic_id) {
        if (INSTANCE == null || dynamic_id > 0) {
            INSTANCE = new CommentSingleAllFragment(dynamic_id);
        }
        return INSTANCE;
    }

    //Data
    private CommentSingleAllContract.Presenter mPresenter;

    //View
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout refreshLayout;
    //评论
    private EditText etCommentInput;
    private TextView tvCommentCount;
    private TextView tvCommentRemainderCount;
    private ImageView ivSendComment;

    //data
    private static final int MAX_COMMENT_COUNT = 360;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        commentActivity = (CommentActivity) mActivity;
        setRootView(R.layout.frg_comment_single_all);
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
        refreshLayout.setOnRefreshListener(this);

        recyclerView.addOnScrollListener(onScrollListener);//设置滑动监听，设置是否下拉刷新

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

    }

    @Override
    protected void initView() {
        //设置标题栏
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("评论");

        recyclerView = (RecyclerView) rootView.findViewById(R.id.icsa_rv_comment);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.icsa_srl);

        //初始化输入
        etCommentInput = (EditText) rootView.findViewById(R.id.icsa_et_commentInput);
        //初始化其他
        tvCommentCount = (TextView) rootView.findViewById(R.id.icsa_tv_inputCount);
        tvCommentRemainderCount = (TextView) rootView.findViewById(R.id.icsa_tv_inputRemainderCount);
        ivSendComment = (ImageView) rootView.findViewById(R.id.icsa_iv_sendComment);
    }

    @Override
    public int bindCommentId() {
        return comment_id;
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
    public void setCommentContent(String commentContent) {

    }

    @Override
    public void setCommentUserName(String commentUserName) {

    }

    @Override
    public void setCommentUserAvatar(String commentUserAvatar) {

    }

    @Override
    public void setCommentCreate_Time(String commentCreate_time) {

    }

    @Override
    public void setReplyData(List<DynamicDetailCommentReplyData> replyDataList) {

    }

    @Override
    public void setPresenter(CommentSingleAllContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        commentActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        commentActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        commentActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    /**
     * 滑动的监听器
     */
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int lastVisibleItem = (linearLayoutManager).findLastVisibleItemPosition();
            int totalItemCount = linearLayoutManager.getItemCount();
            if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                mPresenter.onLoadMore();//设置下拉加载
            }
        }
    };
}
