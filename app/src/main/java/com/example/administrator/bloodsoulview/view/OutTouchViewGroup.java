package com.example.administrator.bloodsoulview.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class OutTouchViewGroup extends RelativeLayout {

    private List<View> mTouchOutsideViews = new ArrayList<>();
    private OnTouchOutsideViewListener mOnTouchOutsideViewListener;

    public OutTouchViewGroup(Context context) {
        super(context);
    }

    public OutTouchViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addOnTouchOutsideView(View view) {
        mTouchOutsideViews.add(view);
    }

    public void setOnTouchOutsideViewListener(OnTouchOutsideViewListener onTouchOutsideViewListener) {
        mOnTouchOutsideViewListener = onTouchOutsideViewListener;
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (mOnTouchOutsideViewListener != null) {
                for (View view: mTouchOutsideViews) {
                    if (view.getVisibility() == View.VISIBLE) {
                        Rect viewRect = new Rect();
                        view.getGlobalVisibleRect(viewRect);
                        if (viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                            return super.dispatchTouchEvent(ev);
                        }
                    }
                }

                if (mOnTouchOutsideViewListener != null) {
                    mOnTouchOutsideViewListener.onTouchOutside(ev);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public interface OnTouchOutsideViewListener {
        void onTouchOutside(MotionEvent event);
    }

}
