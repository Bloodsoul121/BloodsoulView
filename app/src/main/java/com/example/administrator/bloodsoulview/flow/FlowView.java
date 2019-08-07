package com.example.administrator.bloodsoulview.flow;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class FlowView extends RelativeLayout {

    public FlowView(Context context) {
        super(context);
    }

    public FlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {

        }
    }
}
