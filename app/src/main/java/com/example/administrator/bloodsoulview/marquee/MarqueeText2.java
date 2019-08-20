package com.example.administrator.bloodsoulview.marquee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class MarqueeText2 extends AppCompatTextView implements Runnable {

    private int currentScrollX; // 当前滚动的位置
    private boolean isStop = false;
    private int textWidth;
    private boolean isMeasure = false;

    public MarqueeText2(Context context) {
        super(context);
    }

    public MarqueeText2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeText2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        currentScrollX = this.getWidth();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isMeasure) {
            getTextWidth();// 文字宽度只需要获取一次就可以了
            isMeasure = true;
        }
    }

    private void getTextWidth() {
        Paint paint = this.getPaint();
        String str = this.getText().toString();
        textWidth = (int) paint.measureText(str);
    }

    @Override
    public void run() {
        currentScrollX += 2;// 滚动速度.+号表示往左边-
        scrollTo(currentScrollX, 0);
        if (isStop) {
            return;
        }
        if (getScrollX() >= (textWidth)) {
            currentScrollX = -(this.getWidth());// 当前出现的位置
        }
        postDelayed(this, 5);
    }

    // 开始滚动
    public void startScroll() {
        isStop = false;
        this.removeCallbacks(this);
        post(this);
    }

    // 停止滚动
    public void stopScroll() {
        isStop = true;
    }

    // 从头开始滚动
    public void startFromHead() {
        currentScrollX = 0;
        startScroll();
    }
}
