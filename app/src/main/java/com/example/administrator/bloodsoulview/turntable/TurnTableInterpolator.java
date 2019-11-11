package com.example.administrator.bloodsoulview.turntable;

import android.view.animation.BaseInterpolator;

/**
 * 时间度
 * 0 - 0.4 匀加速 1/3 路程
 * 0.4 - 0.6 匀速 1/3 路程
 * 0.6 - 1 匀减速 1/3 路程
 */
public class TurnTableInterpolator extends BaseInterpolator {

    @Override
    public float getInterpolation(float input) {
        if (input < 0.4f) {
            return (float) (Math.pow(input, 2) / 0.48f);
        } else if (input < 0.6f) {
            return 1f / 3f + (input - 0.4f) / 0.6f;
        } else {
            return (float) (2f / 3f + (input - 0.6f) / 0.6f - Math.pow(input - 0.6f, 2) / 0.48f);
        }
    }

}
