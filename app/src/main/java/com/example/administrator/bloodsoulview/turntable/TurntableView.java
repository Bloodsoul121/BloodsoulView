package com.example.administrator.bloodsoulview.turntable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.bloodsoulview.R;

import java.util.ArrayList;

public class TurntableView extends LinearLayout {

    private static final String TAG = TurntableView.class.getSimpleName();

    private static final int TOTAL_TURNS_NUMBLE = 10 * 360;
    private static final int TURNS_TIME = 8000;
    private static final int COUNT = 6;

    private static final int CHECK_NEXT_TURN = 0x01;

    private static final int result_1 = 1;
    private static final int result_2 = 2;
    private static final int result_3 = 3;
    private static final int result_4 = 4;
    private static final int result_5 = 5;
    private static final int result_6 = 6;

    private Context mContext;
    private ImageView mRotateTable;
    private ObjectAnimator mAnimator;
    private int mResultRotation;
    private boolean mIsTurning;
    private ArrayList<TurnTask> mTurnTasks = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHECK_NEXT_TURN:
                    checkNextTask();
                    break;
            }
        }
    };

    private void checkNextTask() {
        if (mTurnTasks.size() == 0) {
            return;
        }
        if (mTurnTasks.size() == 1) {
            TurnTask task = mTurnTasks.remove(0);
            startTurnTask(task);
            return;
        }
        TurnTask task = mTurnTasks.remove(0);
        toastEnd("result : " + task.result);
    }

    public TurntableView(Context context) {
        this(context, null);
    }

    public TurntableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TurntableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_truntable_view, this, true);
        mRotateTable = findViewById(R.id.rotate_table);
    }

    public void receive() {
        TurnTask task = new TurnTask();
        task.id = (int) System.currentTimeMillis();
        task.result = (int) (Math.random() * 6) + 1;
        task.totalTime = 8000;
        task.remainTime = 8000;
        startTurnTask(task);
    }

    public void startTurnTask(TurnTask task) {
        if (task == null) {
            Toast.makeText(mContext, "转盘异常", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mIsTurning) {
            mTurnTasks.add(task);
            Toast.makeText(mContext, "转动还未结束，请耐心等待", Toast.LENGTH_SHORT).show();
            return;
        }
        start(task);
    }

    private void start(final TurnTask task) {
        mIsTurning = true;
        int taskId = task.id;
        int result = task.result;
        mResultRotation = 360 / COUNT * (result - 1);
        float total = TOTAL_TURNS_NUMBLE + mResultRotation;
        Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
        Keyframe keyframe2 = Keyframe.ofFloat(0.25f, total / 5f);
        Keyframe keyframe3 = Keyframe.ofFloat(0.5f, total / 2f);
        Keyframe keyframe4 = Keyframe.ofFloat(0.75f, total * 4f / 5f);
        Keyframe keyframe5 = Keyframe.ofFloat(1f, total);

        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }

        mRotateTable.setRotation(0);

        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("rotation",
                keyframe1, keyframe2, keyframe3, keyframe4, keyframe5);
        mAnimator = ObjectAnimator.ofPropertyValuesHolder(mRotateTable, propertyValuesHolder);
        mAnimator.setRepeatCount(0);
        mAnimator.setDuration(TURNS_TIME);
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                toastEnd("result : " + task.result);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                toastEnd("result : " + task.result);
            }
        });
        mAnimator.start();
    }

    public void forceStop() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
        mRotateTable.setRotation(mResultRotation);
    }

    private void toastEnd(String msg) {
        Toast toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                Log.i(TAG, "toastEnd onViewAttachedToWindow");
                mIsTurning = true;
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                Log.i(TAG, "toastEnd onViewDetachedFromWindow");
                mIsTurning = false;
                if (mHandler != null) {
                    mHandler.sendEmptyMessage(CHECK_NEXT_TURN);
                }
            }
        });
        toast.show();
    }

    static class TurnTask {
        public int id;
        public int result;
        public int totalTime;
        public int remainTime;
    }

}
