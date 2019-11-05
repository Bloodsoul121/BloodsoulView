package com.example.administrator.bloodsoulview.voiceprogress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.bloodsoulview.R;
import com.example.administrator.bloodsoulview.view.MainApplication;

public class RtlVoiceProgress extends View {

    private static final int COUNT = 30;

    private int mLevel;
    private int mMaxLevel = 10;
    private int mCurrentIndex;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mPaddingBottom;
    private float mLength;
    private float mHeight;
    private float mBlockWidth;
    private float mMinHeight;
    private float mSplitLength;
    private float mIncreaseHeight;
    private float mTouchLastX;
    private float mTouchOffset;
    private int mTouchOffsetIndex;
    private int mPreLevel;
    private Context mContext;
    private Paint mPaint;
    private Callback mCallback;
    private RectF mRectF;

    public RtlVoiceProgress(Context context) {
        this(context, null);
    }

    public RtlVoiceProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RtlVoiceProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mPaddingLeft = dp2px(5);
        mPaddingRight = dp2px(5);
        mPaddingTop = dp2px(2);
        mPaddingBottom = dp2px(2);

        mRectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initStatus();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initStatus();
    }

    private void initStatus() {
        mLength = getWidth();
        mHeight = getHeight();
        float scrollLength = mLength - mPaddingLeft - mPaddingRight;

        mSplitLength = scrollLength * 1.0f / (COUNT - 1 + 0.4f);
        mBlockWidth = mSplitLength * 0.4f;

        float maxHeight = mHeight - mPaddingTop - mPaddingBottom;
        mMinHeight = maxHeight * 0.2f;
        mIncreaseHeight = (maxHeight - mMinHeight) / (COUNT - 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float startX = mLength - mPaddingRight;

        mPaint.setColor(mContext.getResources().getColor(R.color.pure_white, null));
        for (int i = 0; i <= mCurrentIndex; i++) {
            mRectF.set(startX - i * mSplitLength - mBlockWidth, mHeight / 2 - (mMinHeight + i * mIncreaseHeight) / 2,
                    startX - i * mSplitLength, mHeight / 2 + (mMinHeight + i * mIncreaseHeight) / 2);
            canvas.drawRoundRect(mRectF, mBlockWidth / 2, mBlockWidth / 2, mPaint);
        }

        mPaint.setColor(mContext.getResources().getColor(R.color.pure_white_10, null));
        for (int i = mCurrentIndex + 1; i < COUNT; i++) {
            mRectF.set(startX - i * mSplitLength - mBlockWidth, mHeight / 2 - (mMinHeight + i * mIncreaseHeight) / 2,
                    startX - i * mSplitLength, mHeight / 2 + (mMinHeight + i * mIncreaseHeight) / 2);
            canvas.drawRoundRect(mRectF, mBlockWidth / 2, mBlockWidth / 2, mPaint);
        }
    }

    public void setMaxLevel(int maxLevel) {
        mMaxLevel = maxLevel;
        mPreLevel = mMaxLevel / COUNT;
    }

    public void setLevel(int level) {
        if (level < 0) {
            level = 0;
        }
        if (level > mMaxLevel) {
            level = mMaxLevel;
        }
        mLevel = level;
        mCurrentIndex = (COUNT - 1) * mLevel / mMaxLevel;
        invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchLastX = event.getX();
                mTouchOffset = 0;
                mTouchOffsetIndex = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = event.getX();
                mTouchOffset += currentX - mTouchLastX;
                int offsetIndex = (int) (mTouchOffset / mSplitLength);
                mLevel -= mPreLevel * (offsetIndex - mTouchOffsetIndex);
                setLevel(mLevel);
                invalidate();
                mTouchLastX = currentX;
                mTouchOffsetIndex = offsetIndex;
                break;
            case MotionEvent.ACTION_UP:
                mTouchOffset = 0;
                mTouchOffsetIndex = 0;
                if (mCallback != null) {
                    mCallback.onProgressChanged(mLevel, mMaxLevel);
                }
                break;
        }
        return true;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {

        void onProgressChanged(int level, int maxLevel);
    }

    private int dp2px(final float dpValue) {
        final float scale = MainApplication.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
