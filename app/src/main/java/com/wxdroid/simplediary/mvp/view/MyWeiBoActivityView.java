package com.wxdroid.simplediary.mvp.view;

import com.wxdroid.simplediary.entity.Status;

import java.util.ArrayList;

/**
 * Created by jinchun on 16/5/16.
 */
public interface MyWeiBoActivityView {
    public void updateListView(ArrayList<Status> statuselist);

    /**
     * 显示loading动画
     */
    public void showLoadingIcon();

    /**
     * 隐藏loadding动画
     */
    public void hideLoadingIcon();

    /**
     * 显示正在加载的FooterView
     */
    public void showLoadFooterView();

    /**
     * 隐藏FooterView
     */
    public void hideFooterView();

    /**
     * 显示FooterView，提示没有任何内容了
     */
    public void showEndFooterView();

    /**
     * 显示FooterView，提示没有网络
     */
    public void showErrorFooterView();

    /**
     * 滑动到顶部
     */
    public void scrollToTop(boolean refreshData);


    public void delete_item(int positon);

}
