package com.example.administrator.bloodsoulview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.administrator.bloodsoulview.R;

public class CameraView extends View {

    private static final String TAG = "CameraView";

    private Camera mCamera;
    private Bitmap mBitmap;
    private Bitmap mCatBitmap;
    private Paint mPaint;
    private Paint mColorPaint;
    private Paint mPathPaint;
    private Rect mRect;
    private RectF mRectF;
    private Matrix mMatrix;
    private Picture mPicture;

    private boolean mHasRecord;
    private Path mPath;

    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCamera = new Camera();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flip_board);
        mCatBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorPaint.setColor(getResources().getColor(R.color.orange_40));
        mRect = new Rect();
        mRectF = new RectF();
        mMatrix = new Matrix();

        mPicture = new Picture();

        mPath = new Path();
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setColor(Color.GREEN);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure : " + getWidth() + " , " + getHeight() + " , " + getMeasuredWidth() + " , " + getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged : " + w + " , " + h + " , " + oldw + " , " + oldh);
    }

    private void record() {
        if (mHasRecord) return;
        mHasRecord = true;
        Canvas pCanvas = mPicture.beginRecording(500, 500);
        pCanvas.translate(250, 250);
        mColorPaint.setColor(Color.BLUE);
        pCanvas.drawCircle(0, 0, 250, mColorPaint);
        mPicture.endRecording();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = mBitmap.getWidth();
        int bitmapHeight = mBitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        Log.i(TAG, "onDraw : bitmap " + bitmapWidth + " , " + bitmapHeight);

//        mRectF.set(x, y, x + bitmapWidth, y + bitmapHeight);
//        canvas.drawRect(mRectF, mColorPaint);

        drawPolygon(canvas);

        canvas.save();
        canvas.translate(centerX, centerY);
        mRectF.set(-bitmapWidth / 2, -bitmapHeight / 2, bitmapWidth / 2, bitmapHeight / 2);
        canvas.drawRect(mRectF, mColorPaint);
        canvas.restore();


        canvas.save();
        canvas.translate(centerX - bitmapWidth / 2, centerY - bitmapHeight / 2);
//        mMatrix.preTranslate(-100, -100);
//        canvas.rotate(30);
//        mMatrix.postTranslate(100, 100);

        mCamera.save();
        mCamera.rotateX(60);
        mCamera.getMatrix(mMatrix);
        mMatrix.postTranslate(bitmapWidth / 2, bitmapHeight / 2);
        mMatrix.preTranslate(-bitmapWidth / 2, -bitmapHeight / 2);
        mCamera.restore();

        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
        canvas.restore();


        canvas.save();
        canvas.drawCircle(300, 300, 5, mColorPaint);
        canvas.drawCircle(400, 500, 5, mColorPaint);
        canvas.drawCircle(600, 300, 5, mColorPaint);
        mPath.reset();
        mPath.moveTo(300, 300);
        mPath.quadTo(400, 500, 600, 300);
        canvas.drawPath(mPath, mPathPaint);
        canvas.restore();


//        canvas.save();
//        canvas.translate(centerX, centerY);
//        mCamera.save();
////        mCamera.translate(-100, -100, 0);
////        mCamera.getMatrix(mMatrix);
////        mMatrix.postTranslate(0, 100);
//
//        mCamera.applyToCanvas(canvas);
//        mCamera.restore();
//        canvas.drawBitmap(mBitmap, -bitmapWidth / 2, -bitmapHeight / 2, mPaint);
//        canvas.restore();


//        mPath.lineTo(50, 50);
//        mPath.lineTo(100, 100);

//        mPath.setLastPoint(100, 0);
//        mPath.moveTo(100, 0);
//        mPath.lineTo(100, 100);

//        mPath.lineTo(100, 100);
//        mPath.lineTo(0, 100);
//        mPath.close();
//        canvas.drawPath(mPath, mPathPaint);


//        record();
//        canvas.drawPicture(mPicture, mRectF);

//        canvas.drawBitmap(mBitmap, x, y, mPaint);

//        canvas.save();
//        mCamera.save();
//        mCamera.translate(100, 100, 0);
//        mCamera.applyToCanvas(canvas);
//        mCamera.restore();
//        canvas.drawBitmap(mBitmap, x, y, mPaint);
//        canvas.restore();

//        canvas.save();
//        mCamera.save();
//        mCamera.rotateX(20);
//        mCamera.applyToCanvas(canvas);
//        mCamera.restore();
//        canvas.drawBitmap(mBitmap, x, y, mPaint);
//        canvas.restore();

        // Canvas 的几何变换顺序是反的，所以要把移动到中心的代码写在下面，把从中心移动回来的代码写在上面。
//        canvas.save();
//        canvas.translate(centerX, centerY); // 在三维旋转之前把绘制内容的中心点移动到原点，即旋转的轴心，看不懂，总觉得说反了
//        mCamera.save();
//        mCamera.rotateX(10);
//        mCamera.applyToCanvas(canvas);
//        mCamera.restore();
//        canvas.translate(-centerX, -centerY); // 旋转之前把绘制内容移动到轴心（原点）
//        canvas.drawBitmap(mBitmap, x, y, mPaint);
//        canvas.restore();

//        mCamera.save();
//        mCamera.setLocation(0, 0, -30);//设置相机位置
//        mCamera.rotate(0, 30, 0);
//        mCamera.getMatrix(mMatrix);
//        mCamera.restore();
//        mMatrix.postTranslate(0, 500);
//        canvas.drawBitmap(mBitmap, mMatrix, null);

//        int count = canvas.save();
//        Log.i(TAG, "count : " + count); // 返回 1
//
//        canvas.translate(centerX, centerY);
//        mColorPaint.setColor(Color.BLUE);
//        mColorPaint.setStyle(Paint.Style.STROKE);
//        mColorPaint.setStrokeWidth(10f);
//        mRectF.set(-400, -400, 400, 400);
//        for (int i = 0; i < 20; i++) {
//            canvas.scale(0.9f, 0.9f);
//            canvas.drawRect(mRectF, mColorPaint);
//        }
//        canvas.drawBitmap(mCatBitmap, null, mRectF, mPaint);
//
//        canvas.restoreToCount(count);
    }

    /**
     * 绘制正多边形
     */
    private void drawPolygon(Canvas canvas) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        canvas.save();

        canvas.scale(1, -1);
        canvas.translate(centerX, -centerY);

        int radius = 400;
        int count = 6;
        int anglePer = 60;

        Path path = new Path();
        float r = radius / (count - 1);//r是蜘蛛丝之间的间距
        for (int i = 1; i < count; i++) {//中心点不用绘制
            float curR = r * i;//当前半径
            path.reset();
            Log.i(TAG, "drawPolygon : >>>>>>>>>>>>>>>>>>>>");
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    path.moveTo(curR, 0);
                    Log.i(TAG, "drawPolygon : " + curR + " , " + 0);
                } else {
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                    int angle = anglePer * j; // 0 - 360
                    // 这里的传值是弧度，踩坑点
                    float x = (float) (curR * Math.cos(angle * Math.PI / 180)); // 1 0 -1 0 1
                    float y = (float) (curR * Math.sin(angle * Math.PI / 180)); // 0 1 0 -1 0
                    path.lineTo(x, y);
                    Log.i(TAG, "drawPolygon : " + angle + " -> " + x + " , " + y);
                }
            }
            path.close();//闭合路径
            canvas.drawPath(path, mPathPaint);
        }

        canvas.restore();
    }
}
