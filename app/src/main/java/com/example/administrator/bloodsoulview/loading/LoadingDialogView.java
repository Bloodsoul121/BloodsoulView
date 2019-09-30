package com.example.administrator.bloodsoulview.loading;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.bloodsoulview.R;

/**
 * Created by xuqian on 2016/9/22.
 */
public class LoadingDialogView extends RelativeLayout {
    private View mLayout;
    private ImageView mLoadingIv;
    private TextView mLoadingTv;
    private AnimationDrawable mAnim;

    public LoadingDialogView(Context context) {
        super(context);
        init();
    }

    public LoadingDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.loading_dialog, this);
        mLayout = findViewById(R.id.loading_layout);
        mLayout.setBackground(null);
        mLoadingIv = findViewById(R.id.loading_iv);
        mLoadingTv = findViewById(R.id.loading_tv);
        mAnim = (AnimationDrawable) mLoadingIv.getBackground();
    }

    public void show() {
        setVisibility(View.VISIBLE);
        mAnim.start();
    }

    public void dismiss() {
        setVisibility(View.GONE);
        if (mAnim != null) {
            mAnim.stop();
        }
    }

    public void setText(String text) {
        mLayout.setBackgroundResource(R.drawable.shape_loading_bg_black);
        mLoadingTv.setVisibility(View.VISIBLE);
        mLoadingTv.setText(text);
    }

}
