package com.wxdroid.simplediary.widget.endlessrecyclerview;

/**
 * Created by jinchun on 16/4/27.
 */
public interface IEndlessRecyclerView {


    public abstract void initRecyclerView();

    public abstract void pullToRefreshData();

    public abstract void requestMoreData();

    public abstract void loadMoreData(String response);

    public abstract void updateList();


}
