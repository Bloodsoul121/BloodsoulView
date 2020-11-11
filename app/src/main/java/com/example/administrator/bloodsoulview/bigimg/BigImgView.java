package com.example.administrator.bloodsoulview.bigimg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import com.shizhefei.view.largeimage.LargeImageView;

import java.io.IOException;
import java.io.InputStream;

public class BigImgView extends View implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private static final String TAG = BigImgView.class.getSimpleName();

    private static final int SCROLL_MOST_SPEED = 25;
    private static final int SCROLL_MOST_SPEED_SCALE = 1;

    private int mViewWidth;
    private int mViewHeight;
    private Rect mImageRect;
    private BitmapFactory.Options mOptions;
    private int mImageWidth;
    private int mImageHeight;
    private float mScale;
    private Bitmap mBitmap;
    private Matrix mMatrix = new Matrix();
    private BitmapRegionDecoder mDecoder;
    private GestureDetector mGestureDetector;
    private Scroller mScroller;

    public BigImgView(Context context) {
        this(context, null);
    }

    public BigImgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigImgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mImageRect = new Rect();
        mOptions = new BitmapFactory.Options();
        mGestureDetector = new GestureDetector(getContext(), this);
        mScroller = new Scroller(getContext());
        setOnTouchListener(this);
    }

    public void setImage(InputStream is) {
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, mImageRect, mOptions);
        mImageWidth = mOptions.outWidth;
        mImageHeight = mOptions.outHeight;

        mOptions.inJustDecodeBounds = false;
        mOptions.inMutable = true;
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        try {
            mDecoder = BitmapRegionDecoder.newInstance(is, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();

        mScale = mViewWidth * 1f / mImageWidth; // 图片缩放到view大小的比例值
        mImageRect.left = 0;
        mImageRect.top = 0;
        mImageRect.right = mImageWidth;
        mImageRect.bottom = (int) (mViewHeight / mScale);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDecoder == null) return;
        mOptions.inBitmap = mBitmap; // 复用内存
        mBitmap = mDecoder.decodeRegion(mImageRect, mOptions);
        Log.i(TAG, "onDraw -> mBitmap " + mBitmap);
        mMatrix.setScale(mScale, mScale);
        canvas.drawBitmap(mBitmap, mMatrix, null);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        int offset = (int) Math.max(-SCROLL_MOST_SPEED, Math.min(SCROLL_MOST_SPEED, distanceY));
//        int offset = (int) distanceY;
//        int offset = (int) (distanceY / SCROLL_MOST_SPEED_SCALE);
        int offset = (int) (distanceY / mScale);
        mImageRect.offset(0, offset);
        Log.i(TAG, "offset -> " + mImageRect.left + " " + mImageRect.top + " " + mImageRect.right + " " + mImageRect.bottom);
        Log.i(TAG, "=========" + 0 + " " + (int) (distanceY / mScale) + " " + mImageWidth + " " + (int) ((distanceY + mViewHeight) / mScale));

        if (mImageRect.bottom > mImageHeight) {
            mImageRect.left = 0;
            mImageRect.top = mImageHeight - (int) (mViewHeight / mScale);
            mImageRect.right = mImageWidth;
            mImageRect.bottom = mImageHeight;
        }

        if (mImageRect.top < 0) {
            mImageRect.left = 0;
            mImageRect.top = 0;
            mImageRect.right = mImageWidth;
            mImageRect.bottom = (int) (mViewHeight / mScale);
        }

        invalidate();
//        postInvalidate();
//        ViewCompat.postInvalidateOnAnimation(this);
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling(0, mImageRect.top, 0, -(int) velocityY, 0, 0, 0, mImageHeight - (int) (mViewHeight / mScale));
        return false;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.isFinished()) {
            return;
        }
        if (mScroller.computeScrollOffset()) {
            mImageRect.top = mScroller.getCurrY();
            mImageRect.bottom = mScroller.getCurrY() + (int) (mViewHeight / mScale);
            invalidate();
        }
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }
}
