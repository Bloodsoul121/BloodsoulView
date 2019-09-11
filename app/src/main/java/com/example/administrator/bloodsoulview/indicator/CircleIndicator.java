package com.example.administrator.bloodsoulview.indicator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.annotation.AnimatorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.example.administrator.bloodsoulview.R;

/**
 * CircleIndicator work with ViewPager
 */
public class CircleIndicator extends LinearLayout {

    private final static int DEFAULT_INDICATOR_WIDTH = 5;

    protected int mIndicatorMargin = -1;
    protected int mIndicatorWidth = -1;
    protected int mIndicatorHeight = -1;

    protected int mIndicatorBackgroundResId;
    protected int mIndicatorUnselectedBackgroundResId;

    protected Animator mAnimatorOut;
    protected Animator mAnimatorIn;
    protected Animator mImmediateAnimatorOut;
    protected Animator mImmediateAnimatorIn;

    protected int mLastPosition = -1;

    private ViewPager mViewpager;

    @Nullable
    private IndicatorCreatedListener mIndicatorCreatedListener;

    public CircleIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Config config = handleTypedArray(context, attrs);
        initialize(config);

        if (isInEditMode()) {
            createIndicators(3, 1);
        }
    }

    private Config handleTypedArray(Context context, AttributeSet attrs) {
        Config config = new Config();
        if (attrs == null) {
            return config;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);
        config.width = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
        config.height = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_height, -1);
        config.margin = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_margin, -1);
        config.animatorResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator, R.animator.scale_with_alpha);
        config.animatorReverseResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator_reverse, 0);
        config.backgroundResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable, R.drawable.white_radius);
        config.unselectedBackgroundId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable_unselected, config.backgroundResId);
        config.orientation = typedArray.getInt(R.styleable.CircleIndicator_ci_orientation, -1);
        config.gravity = typedArray.getInt(R.styleable.CircleIndicator_ci_gravity, -1);
        typedArray.recycle();

        return config;
    }

    public void initialize(Config config) {
        int miniSize = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_INDICATOR_WIDTH, getResources().getDisplayMetrics()) + 0.5f);
        mIndicatorWidth = (config.width < 0) ? miniSize : config.width;
        mIndicatorHeight = (config.height < 0) ? miniSize : config.height;
        mIndicatorMargin = (config.margin < 0) ? miniSize : config.margin;

        mAnimatorOut = createAnimatorOut(config);
        mImmediateAnimatorOut = createAnimatorOut(config);
        mImmediateAnimatorOut.setDuration(0);

        mAnimatorIn = createAnimatorIn(config);
        mImmediateAnimatorIn = createAnimatorIn(config);
        mImmediateAnimatorIn.setDuration(0);

        mIndicatorBackgroundResId = (config.backgroundResId == 0) ? R.drawable.white_radius : config.backgroundResId;
        mIndicatorUnselectedBackgroundResId = (config.unselectedBackgroundId == 0) ? config.backgroundResId : config.unselectedBackgroundId;

        setOrientation(config.orientation == VERTICAL ? VERTICAL : HORIZONTAL);
        setGravity(config.gravity >= 0 ? config.gravity : Gravity.CENTER);
    }

    public interface IndicatorCreatedListener {
        /**
         * IndicatorCreatedListener
         *
         * @param view     internal indicator view
         * @param position position
         */
        void onIndicatorCreated(View view, int position);
    }

    public void setIndicatorCreatedListener(
            @Nullable IndicatorCreatedListener indicatorCreatedListener) {
        mIndicatorCreatedListener = indicatorCreatedListener;
    }

    protected Animator createAnimatorOut(Config config) {
        return AnimatorInflater.loadAnimator(getContext(), config.animatorResId);
    }

    protected Animator createAnimatorIn(Config config) {
        Animator animatorIn;
        if (config.animatorReverseResId == 0) {
            animatorIn = AnimatorInflater.loadAnimator(getContext(), config.animatorResId);
            animatorIn.setInterpolator(new ReverseInterpolator());
        } else {
            animatorIn = AnimatorInflater.loadAnimator(getContext(), config.animatorReverseResId);
        }
        return animatorIn;
    }

    public void createIndicators(int count, int currentPosition) {
        if (mImmediateAnimatorOut.isRunning()) {
            mImmediateAnimatorOut.end();
            mImmediateAnimatorOut.cancel();
        }

        if (mImmediateAnimatorIn.isRunning()) {
            mImmediateAnimatorIn.end();
            mImmediateAnimatorIn.cancel();
        }

        // Diff View
        int childViewCount = getChildCount();
        if (count < childViewCount) {
            removeViews(count, childViewCount - count);
        } else if (count > childViewCount) {
            int addCount = count - childViewCount;
            int orientation = getOrientation();
            for (int i = 0; i < addCount; i++) {
                addIndicator(orientation);
            }
        }

        // Bind Style
        View indicator;
        for (int i = 0; i < count; i++) {
            indicator = getChildAt(i);
            if (currentPosition == i) {
                indicator.setBackgroundResource(mIndicatorBackgroundResId);
                mImmediateAnimatorOut.setTarget(indicator);
                mImmediateAnimatorOut.start();
                mImmediateAnimatorOut.end();
            } else {
                indicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId);
                mImmediateAnimatorIn.setTarget(indicator);
                mImmediateAnimatorIn.start();
                mImmediateAnimatorIn.end();
            }

            if (mIndicatorCreatedListener != null) {
                mIndicatorCreatedListener.onIndicatorCreated(indicator, i);
            }
        }

        mLastPosition = currentPosition;
    }

    protected void addIndicator(int orientation) {
        View indicator = new View(getContext());
        final LayoutParams params = generateDefaultLayoutParams();
        params.width = mIndicatorWidth;
        params.height = mIndicatorHeight;
        if (orientation == HORIZONTAL) {
            params.leftMargin = mIndicatorMargin;
            params.rightMargin = mIndicatorMargin;
        } else {
            params.topMargin = mIndicatorMargin;
            params.bottomMargin = mIndicatorMargin;
        }
        addView(indicator, params);
    }

    public void animatePageSelected(int position) {

        if (mLastPosition == position) {
            return;
        }

        if (mAnimatorIn.isRunning()) {
            mAnimatorIn.end();
            mAnimatorIn.cancel();
        }

        if (mAnimatorOut.isRunning()) {
            mAnimatorOut.end();
            mAnimatorOut.cancel();
        }

        View currentIndicator;
        if (mLastPosition >= 0 && (currentIndicator = getChildAt(mLastPosition)) != null) {
            currentIndicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId);
            mAnimatorIn.setTarget(currentIndicator);
            mAnimatorIn.start();
        }

        View selectedIndicator = getChildAt(position);
        if (selectedIndicator != null) {
            selectedIndicator.setBackgroundResource(mIndicatorBackgroundResId);
            mAnimatorOut.setTarget(selectedIndicator);
            mAnimatorOut.start();
        }
        mLastPosition = position;
    }

    protected class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }

    public void setViewPager(@Nullable ViewPager viewPager) {
        mViewpager = viewPager;
        if (mViewpager != null && mViewpager.getAdapter() != null) {
            mLastPosition = -1;
            createIndicators();
            mViewpager.removeOnPageChangeListener(mInternalPageChangeListener);
            mViewpager.addOnPageChangeListener(mInternalPageChangeListener);
            mInternalPageChangeListener.onPageSelected(mViewpager.getCurrentItem());
        }
    }

    private void createIndicators() {
        PagerAdapter adapter = mViewpager.getAdapter();
        int count;
        if (adapter == null) {
            count = 0;
        } else {
            count = adapter.getCount();
        }
        createIndicators(count, mViewpager.getCurrentItem());
    }

    private final ViewPager.OnPageChangeListener mInternalPageChangeListener =
            new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {

                    if (mViewpager.getAdapter() == null
                            || mViewpager.getAdapter().getCount() <= 0) {
                        return;
                    }
                    animatePageSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            };

    public DataSetObserver getDataSetObserver() {
        return mInternalDataSetObserver;
    }

    private final DataSetObserver mInternalDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            if (mViewpager == null) {
                return;
            }
            PagerAdapter adapter = mViewpager.getAdapter();
            int newCount = adapter != null ? adapter.getCount() : 0;
            int currentCount = getChildCount();
            if (newCount == currentCount) {
                // No change
                return;
            } else if (mLastPosition < newCount) {
                mLastPosition = mViewpager.getCurrentItem();
            } else {
                mLastPosition = -1;
            }
            createIndicators();
        }
    };

    private class Config {

        int width = -1;
        int height = -1;
        int margin = -1;
        @AnimatorRes
        int animatorResId = R.animator.scale_with_alpha;
        @AnimatorRes
        int animatorReverseResId = 0;
        @DrawableRes
        int backgroundResId = R.drawable.white_radius;
        @DrawableRes
        int unselectedBackgroundId;
        int orientation = LinearLayout.HORIZONTAL;
        int gravity = Gravity.CENTER;

        Config() {
        }

        private class Builder {

            private final Config mConfig;

            public Builder() {
                mConfig = new Config();
            }

            public Builder width(int width) {
                mConfig.width = width;
                return this;
            }

            public Builder height(int height) {
                mConfig.height = height;
                return this;
            }

            public Builder margin(int margin) {
                mConfig.margin = margin;
                return this;
            }

            public Builder animator(@AnimatorRes int animatorResId) {
                mConfig.animatorResId = animatorResId;
                return this;
            }

            public Builder animatorReverse(@AnimatorRes int animatorReverseResId) {
                mConfig.animatorReverseResId = animatorReverseResId;
                return this;
            }

            public Builder drawable(@DrawableRes int backgroundResId) {
                mConfig.backgroundResId = backgroundResId;
                return this;
            }

            public Builder drawableUnselected(@DrawableRes int unselectedBackgroundId) {
                mConfig.unselectedBackgroundId = unselectedBackgroundId;
                return this;
            }

            public Builder orientation(int orientation) {
                mConfig.orientation = orientation;
                return this;
            }

            public Builder gravity(int gravity) {
                mConfig.gravity = gravity;
                return this;
            }

            public Config build() {
                return mConfig;
            }
        }
    }

}