package com.example.administrator.bloodsoulview.clickspan;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *  必须与 {@link ClickSpanMovementMethod} 配合使用
 */
public class ClickSpanTextView extends AppCompatTextView {

    private ClickSpanMovementMethod mLinkTouchMovementMethod;

    public ClickSpanTextView(Context context) {
        super(context);
    }

    public ClickSpanTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickSpanTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        return mLinkTouchMovementMethod != null ? mLinkTouchMovementMethod.isPressedSpan() : result;
    }

    public void setLinkTouchMovementMethod(ClickSpanMovementMethod linkTouchMovementMethod) {
        mLinkTouchMovementMethod = linkTouchMovementMethod;
    }

}
