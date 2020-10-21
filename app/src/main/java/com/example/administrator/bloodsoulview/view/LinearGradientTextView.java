package com.example.administrator.bloodsoulview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

public class LinearGradientTextView extends AppCompatTextView {

    private Rect mRect;
    private Matrix mMatrix;
    private LinearGradient mLinearGradient;

    public LinearGradientTextView(Context context) {
        this(context, null);
    }

    public LinearGradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mRect = new Rect();
        mMatrix = new Matrix();
    }

    public void startShow() {
        int viewWidth = getMeasuredWidth();
        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mLinearGradient = new LinearGradient(0, 0, viewWidth, 0, new int[]{Color.parseColor("#ff4800"), Color.parseColor("#ffc000"), Color.parseColor("#ff4800")}, new float[]{0, 0.5f, 1}, Shader.TileMode.CLAMP);
        getPaint().setShader(mLinearGradient);
        getPaint().getTextBounds(getText().toString(), 0, getText().length(), mRect);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(-viewWidth, mRect.width() + viewWidth);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setDuration((long) (mRect.width() / 0.2f));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mMatrix.setTranslate(value, 0);
                mLinearGradient.setLocalMatrix(mMatrix);
                invalidate();
            }
        });
        valueAnimator.start();
    }

}
