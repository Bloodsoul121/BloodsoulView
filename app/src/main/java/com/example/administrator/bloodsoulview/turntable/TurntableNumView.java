package com.example.administrator.bloodsoulview.turntable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.bloodsoulview.R;

import java.util.ArrayList;
import java.util.List;

public class TurntableNumView extends View {

    private Context mContext;
    private int mCount;
    private int mPerAngle;
    private int mRadius;
    private int mBgRadius;
    private int mCenterX;
    private int mCenterY;
    private RectF mRectF;
    private Rect mBgRect;
    private Paint mPaint;
    private Paint mNumPaint;
    private Paint mBgPaint;
    private Bitmap mBg;
    private List<String> mNums = new ArrayList<>();

    public TurntableNumView(Context context) {
        this(context, null);
    }

    public TurntableNumView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TurntableNumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mRectF = new RectF();
        mCount = 6;
        mPerAngle = 360 / mCount;

        for (int i = 0; i < mCount; i++) {
            mNums.add(String.valueOf(i + 1));
        }

        mBg = BitmapFactory.decodeResource(getResources(), R.drawable.turn_table_bg);
        mBgRect = new Rect(0, 0, mBg.getWidth(), mBg.getHeight());

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);

        mNumPaint = new Paint();
        mNumPaint.setColor(Color.parseColor("#555555"));
        mNumPaint.setAntiAlias(true);
        mNumPaint.setTextSize(dp2px(26));
        mNumPaint.setTextAlign(Paint.Align.CENTER);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
    }

    public void config(List<String> nums) {
        mNums.clear();
        mNums.addAll(nums);
        mCount = mNums.size();
        mPerAngle = 360 / mCount;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        calculate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculate();
    }

    private void calculate() {
        int width = getWidth();
        int height = getHeight();
        int dia = Math.min(width, height);
        mBgRadius = dia / 2;
        mRadius = dia / 2 - dp2px(12);
        mCenterX = width / 2;
        mCenterY = height / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRectF.set(mCenterX - mBgRadius, mCenterY - mBgRadius, mCenterX + mBgRadius, mCenterY + mBgRadius);
        canvas.drawBitmap(mBg, mBgRect, mRectF, mBgPaint);

        mRectF.set(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);

        for (int i = 0; i < mCount; i++) {
            if (i % 2 == 0) {
                mPaint.setColor(Color.parseColor("#E7FCFF"));
            } else {
                mPaint.setColor(Color.parseColor("#FFFFFF"));
            }
            canvas.drawArc(mRectF, mPerAngle * i, mPerAngle, true, mPaint);
        }

        canvas.save();
        for (int i = 0; i < mCount; i++) {
            if (i > 0) {
                canvas.rotate(mPerAngle, mCenterX, mCenterY);
            }
            Paint.FontMetrics metrics = mNumPaint.getFontMetrics();
            float textY = mCenterY / 2f + (-metrics.top - metrics.bottom - metrics.leading) / 2;
            canvas.drawText(mNums.get(i), mCenterX, textY, mNumPaint);
        }
        canvas.restore();
    }

    private int dp2px(float dpValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }
}
