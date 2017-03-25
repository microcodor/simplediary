package com.wxdroid.simplediary.ui.login.fragment.discovery.hotweibo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wxdroid.simplediary.R;
import com.wxdroid.simplediary.entity.Status;
import com.wxdroid.simplediary.mvp.presenter.HotWeiBoPresent;
import com.wxdroid.simplediary.mvp.presenter.imp.HotWeiBoPresentImp;
import com.wxdroid.simplediary.mvp.view.HotWeiBoActivityView;
import com.wxdroid.simplediary.ui.common.BaseActivity;
import com.wxdroid.simplediary.ui.common.dialog.ArrowDialog;
import com.wxdroid.simplediary.ui.login.fragment.home.weiboitem.SeachHeadView;
import com.wxdroid.simplediary.ui.login.fragment.home.weiboitem.TimelineArrowWindow;
import com.wxdroid.simplediary.ui.login.fragment.home.weiboitem.WeiboAdapter;
import com.wxdroid.simplediary.ui.login.fragment.home.weiboitem.WeiboItemSapce;
import com.wxdroid.simplediary.utils.DensityUtil;
import com.wxdroid.simplediary.utils.ScreenUtil;
import com.wxdroid.simplediary.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.wxdroid.simplediary.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.wxdroid.simplediary.widget.endlessrecyclerview.RecyclerViewUtils;
import com.wxdroid.simplediary.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.wxdroid.simplediary.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;

/**
 * Created by jinchun on 16/4/27.
 */
public class HotWeiBoActivity extends BaseActivity implements HotWeiBoActivityView {
    private ArrayList<Status> mDatas = new ArrayList<Status>();
    public WeiboAdapter mAdapter;
    public Context mContext;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public RecyclerView mRecyclerView;
    public boolean mRefrshAllData;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private HotWeiBoPresent mHotWeiBoPresent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_popweibo_layout);
        mContext = this;
        mHotWeiBoPresent = new HotWeiBoPresentImp(this);
        initRefreshLayout();
        initRecyclerView();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mHotWeiBoPresent.pullToRefreshData(mContext);
            }
        });
    }


    protected void initRefreshLayout() {
        mRefrshAllData = true;
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.base_swipe_refresh_widget);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHotWeiBoPresent.pullToRefreshData(mContext);
            }
        });
    }


    public void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.base_RecyclerView);
        mAdapter = new WeiboAdapter(mDatas, mContext) {
            @Override
            public void arrowClick(Status status, int position) {
//                TimelineArrowWindow popupWindow = new TimelineArrowWindow(mContext, mDatas.get(position), mAdapter, position, "");
//                popupWindow.showAtLocation(mRecyclerView, Gravity.CENTER, 0, 0);

                ArrowDialog arrowDialog = new TimelineArrowWindow.Builder(mContext, mDatas.get(position), mAdapter, position, "")
                        .setCanceledOnTouchOutside(true)
                        .setCancelable(true)
                        .create();
                int width = ScreenUtil.getScreenWidth(mContext) - DensityUtil.dp2px(mContext, 80);
                arrowDialog.show();
                arrowDialog.getWindow().setLayout(width, (ViewGroup.LayoutParams.WRAP_CONTENT));

            }
        };
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setHeaderView(mRecyclerView, new SeachHeadView(mContext));
        mRecyclerView.addItemDecoration(new WeiboItemSapce((int) mContext.getResources().getDimension(R.dimen.home_weiboitem_space)));
    }


    public EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            if (mDatas != null && mDatas.size() > 0) {
                showLoadFooterView();
                mHotWeiBoPresent.requestMoreData(mContext);
            }
        }
    };

    public void onArrorClick(View view) {
        finish();
    }

    @Override
    public void updateListView(final ArrayList<Status> statuselist) {
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mDatas = statuselist;
        mAdapter.setData(statuselist);
        mHeaderAndFooterRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingIcon() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoadingIcon() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFooterView() {
        RecyclerViewStateUtils.setFooterViewState(HotWeiBoActivity.this, mRecyclerView, mDatas.size(), LoadingFooter.State.Loading, null);
    }

    @Override
    public void hideFooterView() {
        RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void showErrorFooterView() {
        RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.NetWorkError);
    }

}
