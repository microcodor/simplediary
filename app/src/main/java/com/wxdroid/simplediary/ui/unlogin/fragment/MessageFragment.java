package com.wxdroid.simplediary.ui.unlogin.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxdroid.simplediary.R;

/**
 * Created by jinchun on 15/12/26.
 */
public class MessageFragment extends BaseFragment {
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.unlogin_messagefragment_layout, container, false);
        return mView;
    }


}
