package com.example.administrator.bloodsoulview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.bloodsoulview.R;

public class ScrollPageView extends LinearLayout {

    private View mRootView;

    public ScrollPageView(Context context) {
        this(context, null);
    }

    public ScrollPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.layout_view_scroll_page, this, true);

    }

}
