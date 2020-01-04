package com.example.administrator.bloodsoulview.clickspan;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 *  必须与 {@link ClickSpanTextView} 配合使用
 */
public class ClickSpanMovementMethod extends android.text.method.LinkMovementMethod {

    private static ClickSpanMovementMethod sInstance;

    private ClickableSpan mPressedSpan;

    public static ClickSpanMovementMethod getInstance() {
        synchronized (ClickSpanMovementMethod.class) {
            if (sInstance == null) {
                synchronized (ClickSpanMovementMethod.class) {
                    sInstance = new ClickSpanMovementMethod();
                }
            }
        }
        return sInstance;
    }

    @Override
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mPressedSpan = getPressedSpan(textView, spannable, event);
            if (mPressedSpan != null) {
                Selection.setSelection(spannable, spannable.getSpanStart(mPressedSpan),
                        spannable.getSpanEnd(mPressedSpan));
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            ClickableSpan touchedSpan = getPressedSpan(textView, spannable, event);
            if (mPressedSpan != null && touchedSpan != mPressedSpan) {
                mPressedSpan = null;
                Selection.removeSelection(spannable);
            }
        } else {
            if (mPressedSpan != null) {
                super.onTouchEvent(textView, spannable, event);
            }
            mPressedSpan = null;
            Selection.removeSelection(spannable);
        }
        return mPressedSpan != null;
    }

    private ClickableSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= textView.getTotalPaddingLeft();
        y -= textView.getTotalPaddingTop();

        x += textView.getScrollX();
        y += textView.getScrollY();

        Layout layout = textView.getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        ClickableSpan[] link = spannable.getSpans(off, off, ClickableSpan.class);
        ClickableSpan touchedSpan = null;
        if (link.length > 0) {
            touchedSpan = link[0];
        }
        return touchedSpan;
    }

    public boolean isPressedSpan() {
        return mPressedSpan != null;
    }

}
