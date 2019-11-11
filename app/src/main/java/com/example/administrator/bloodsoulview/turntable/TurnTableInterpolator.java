package com.example.administrator.bloodsoulview.turntable;

import android.util.Log;
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

        /*
         *  时间度
         *  0 - 0.4 匀加速 1/3 路程
         *  0.4 - 0.6 匀速 1/3 路程
         *  0.6 - 1 匀减速 1/3 路程
         */

//        if (input < 0.4f) {
//            return (float) (Math.pow(input, 2) / 0.48f);
//        } else if (input < 0.6f) {
//            return 1f / 3f + (input - 0.4f) / 0.6f;
//        } else {
//            return (float) (2f / 3f + (input - 0.6f) / 0.6f - Math.pow(input - 0.6f, 2) / 0.48f);
//        }

        /*
         *  时间度
         *  0 - 0.18 匀加速 0.15 路程
         *  0.18 - 0.38 匀速 1/3 路程
         *  0.38 - 1 匀减速
         */

//        if (input < 0.18f) {
//            return (float) (Math.pow(input, 2) / 0.216f);
//        } else if (input < 0.38f) {
//            return 0.15f + (input - 0.18f) / 0.6f;
//        } else {
//            return (float) (0.15f + 1f / 3f + (input - 0.38f) / 0.6f - Math.pow(input - 0.38f, 2) / 0.744f);
//        }

        /*
         *  时间度
         *  0 - 0.18 匀加速 0.15 路程
         *  0.18 - 0.38 匀速 1/3 路程
         *  0.38 - 0.68 匀减速 v/6
         *  0.68 - 1 匀减速
         */
        if (input < 0.18f) {
            return (float) (Math.pow(input, 2) * 1000f / 177f);
        } else if (input < 0.38f) {
            return (float) ((Math.pow(0.18f, 2) * 1000f / 177f) + (input - 0.18f) * 120f / 59f);
        } else if (input < 0.68f) {
            return (float) ((Math.pow(0.18f, 2) * 1000f / 177f) + (0.38f - 0.18f) * 120f / 59f + 120f / 59f
                    * (input - 0.38f) - 1500f / 531f * Math.pow(input - 0.38f, 2));
        } else {
            return (float) ((Math.pow(0.18f, 2) * 1000f / 177f) + (0.38f - 0.18f) * 120f / 59f + 120f / 59f
                    * (0.68f - 0.38f) - 1500f / 531f * Math.pow(0.68f - 0.38f, 2) + 20f / 59f *
                    (input - 0.68f) - 125f / 236f * Math.pow(input - 0.68f, 2));
        }
    }

}
