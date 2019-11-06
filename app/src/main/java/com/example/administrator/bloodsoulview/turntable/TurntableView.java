package com.example.administrator.bloodsoulview.turntable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.bloodsoulview.R;

import java.util.ArrayList;

public class TurntableView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = TurntableView.class.getSimpleName();

    private static final int CHECK_NEXT_TURN = 0x01;
    private static final int TOTAL_TURNS_NUMBLE = 4 * 360;

    private TurntableNumView mRotateTable;

    private int mCount;
    private int mPerAngle;
    private int mResult;
    private int mResultRotation;
    private boolean mIsTurning;
    private Context mContext;
    private ObjectAnimator mAnimator;
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
        mRotateTable = findViewById(R.id.turn_table_num);
        ImageView rotateGo = findViewById(R.id.turn_table_go);
        rotateGo.setOnClickListener(this);

        mResult = 1;
        mCount = 6;
        mPerAngle = 360 / mCount;
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

        int lastResult = mResult;
        int lastResultRotation = 360 - mPerAngle * (lastResult - 1);

        mResult = task.result;
        mResultRotation = 360 - mPerAngle * (mResult - 1);

        // 旋转的过程
        float process;
        if (mResult > lastResult) {
            process = TOTAL_TURNS_NUMBLE + (360 - mPerAngle * (mResult - lastResult));
        } else {
            process = TOTAL_TURNS_NUMBLE + mPerAngle * (lastResult - mResult);
        }

        // 已经播放的时间
        float progress = (task.totalTime - task.remainTime) / task.totalTime;
        if (progress < 0) {
            progress = 0;
        } else if (progress > 1) {
            progress = 1;
        }

        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }

        Path path = new Path();
        path.moveTo(0f, 0f);
        path.lineTo(0.2f, 0.12f);
        path.lineTo(0.4f, 0.36f);
//        path.lineTo(0.25f, 0.15f);
//        path.lineTo(0.5f, 0.5f);
//        path.lineTo(0.75f, 0.85f);
        path.lineTo(0.6f, 0.64f);
        path.lineTo(0.8f, 0.88f);
        path.lineTo(1, 1);

        PathInterpolator pathInterpolator = new PathInterpolator(path);

        mAnimator = ObjectAnimator.ofFloat(mRotateTable, "rotation", lastResultRotation, lastResultRotation + process);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setRepeatCount(0);
        mAnimator.setDuration(task.totalTime);
        mAnimator.setCurrentPlayTime(task.remainTime);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.turn_table_go:
                receive();
                break;
        }
    }

    static class TurnTask {
        public int id;
        public int result;
        public long totalTime;
        public long remainTime;
    }

}
