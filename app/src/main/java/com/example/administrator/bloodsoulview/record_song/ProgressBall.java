package com.example.administrator.bloodsoulview.record_song;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.bloodsoulview.R;

public class ProgressBall extends View {

    private float mMaxProgress = 1;
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

    public void setProgress(float progress) {
        mProgress = calculateRateProgress(progress);
        invalidate();
    }

    public void setMaxProgress(float progress) {
        mMaxProgress = progress;
    }

    private float calculateRateProgress(float progress) {
        float perProgress = progress / mMaxProgress;
        if (perProgress < 0) {
            perProgress = 0;
        } else if (perProgress > 1) {
            perProgress = 1;
        } else if (perProgress < 0.3f) {
            perProgress = 2 * perProgress;
        } else if (perProgress < 0.6f) {
            perProgress = 0.6f + (perProgress - 0.3f) * 0.7f;
        } else {
            perProgress = 0.81f + (perProgress - 0.6f) * 0.19f / 0.4f;
        }
        return perProgress;
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
