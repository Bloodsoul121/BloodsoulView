package com.example.administrator.bloodsoulview.record_song;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.bloodsoulview.R;

public class ProgressBall extends View {

    private float mProgress;
    private Paint mBallPaint;
    private Paint mTextPaint;
    private int mDiameter;
    private int mRadius;
    private Context mContext;

    public ProgressBall(Context context) {
        this(context, null);
    }

    public ProgressBall(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBall(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mBallPaint = new Paint();
        mBallPaint.setColor(context.getResources().getColor(R.color.color_39d8ae));
        mBallPaint.setAntiAlias(true);
        mBallPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(context.getResources().getColor(R.color.pure_white));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(20);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mDiameter = Math.min(getWidth(), getHeight());
        mRadius = mDiameter / 2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDiameter = Math.min(getWidth(), getHeight());
        mRadius = mDiameter / 2;
    }

    /**
     * 进度
     *
     * @param progress 0 - 1
     */
    public void setProgress(float progress) {
        mProgress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mBallPaint.setColor(mContext.getResources().getColor(R.color.pure_white_4, null));
        canvas.clipRect(0, 0, mDiameter, mDiameter - mDiameter * mProgress);
        canvas.drawCircle(mRadius, mRadius, mRadius, mBallPaint);
        canvas.restore();

        canvas.save();
        mBallPaint.setColor(mContext.getResources().getColor(R.color.color_39d8ae, null));
        canvas.clipRect(0, mDiameter - mDiameter * mProgress, mDiameter, mDiameter);
        canvas.drawCircle(mRadius, mRadius, mRadius, mBallPaint);
        canvas.restore();

        String text = (int) (100 * mProgress) + "%";
//        float length = mTextPaint.measureText(text);
//        float textX = mRadius - length / 2;
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        float textY = mRadius + (-metrics.top - metrics.bottom - metrics.leading) / 2;
        canvas.drawText(text, mRadius, textY, mTextPaint);
    }
}
