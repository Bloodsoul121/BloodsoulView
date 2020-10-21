package com.example.administrator.bloodsoulview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import android.util.AttributeSet;
import android.view.View;

public class RoundImageView extends View {

    private float mPaddingLeft;
    private float mPaddingRight;
    private float mPaddingTop;
    private float mPaddingBottom;
    private int mRadius;
    private Paint mPaint;
    private Bitmap mRoundBitmap;
    private Matrix mMatrix;
    private BitmapShader mBitmapShader;

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        assert attrs != null;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        int src = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", 0);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), src);
        mRoundBitmap = toRound(bitmap, true);
        mPaint = new Paint();
        mMatrix = new Matrix();
        mBitmapShader = new BitmapShader(mRoundBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int srcWidth = (int) (mRoundBitmap.getWidth() + mPaddingLeft + mPaddingRight);
        int srcHeight = (int) (mRoundBitmap.getHeight() + mPaddingTop + mPaddingBottom);
        int realWidth = resolveSize(srcWidth, widthMeasureSpec); // 1
        int realHeight = resolveSize(srcHeight, heightMeasureSpec); // 2
        //因为是圆形图片，所以应该让宽高保持一致
        int size = Math.min(realWidth, realHeight);
        mRadius = size / 2;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //计算缩放比例
        float scale = (mRadius * 2.0f) / Math.min(mRoundBitmap.getHeight(), mRoundBitmap.getWidth());
        mMatrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mBitmapShader);

        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
    }

    /**
     * 转为圆形图片
     *
     * @param src     源图片
     * @param recycle 是否回收
     * @return 圆形图片
     */
    private Bitmap toRound(final Bitmap src, final boolean recycle) {
        return toRound(src, 0, 0, recycle);
    }

    /**
     * 转为圆形图片
     *
     * @param src         源图片
     * @param borderSize  边框尺寸
     * @param borderColor 边框颜色
     * @return 圆形图片
     */
    private Bitmap toRound(final Bitmap src,
                           @IntRange(from = 0) int borderSize,
                           @ColorInt int borderColor) {
        return toRound(src, borderSize, borderColor, false);
    }

    /**
     * 转为圆形图片
     *
     * @param src         源图片
     * @param recycle     是否回收
     * @param borderSize  边框尺寸
     * @param borderColor 边框颜色
     * @return 圆形图片
     */
    private Bitmap toRound(final Bitmap src,
                           @IntRange(from = 0) int borderSize,
                           @ColorInt int borderColor,
                           final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        int size = Math.min(width, height);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap ret = Bitmap.createBitmap(width, height, src.getConfig());
        float center = size / 2f;
        RectF rectF = new RectF(0, 0, width, height);
        rectF.inset((width - size) / 2f, (height - size) / 2f);
        Matrix matrix = new Matrix();
        matrix.setTranslate(rectF.left, rectF.top);
        matrix.preScale((float) size / width, (float) size / height);
        BitmapShader shader = new BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
        Canvas canvas = new Canvas(ret);
        canvas.drawRoundRect(rectF, center, center, paint);
        if (borderSize > 0) {
            paint.setShader(null);
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(borderSize);
            float radius = center - borderSize / 2f;
            canvas.drawCircle(width / 2f, height / 2f, radius, paint);
        }
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 判断 bitmap 对象是否为空
     *
     * @param src 源图片
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private boolean isEmptyBitmap(final Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

}
