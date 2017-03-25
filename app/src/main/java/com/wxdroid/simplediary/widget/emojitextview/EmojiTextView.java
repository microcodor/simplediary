package com.wxdroid.simplediary.widget.emojitextview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by jinchun on 16/3/9.
 */
public class EmojiTextView extends TextView {


    private final Context mContext;

    public EmojiTextView(Context context) {
        super(context, null);
        mContext = context;
    }

    public EmojiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

}