package com.example.administrator.bloodsoulview.util;

import com.blankj.utilcode.util.Utils;

public class SizeUtil {

    public static int dp2px(float dpValue) {
        float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    public static int px2dp(float pxValue) {
        float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    public static int sp2px(float spValue) {
        float fontScale = Utils.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5F);
    }

    public static int px2sp(float pxValue) {
        float fontScale = Utils.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / fontScale + 0.5F);
    }

}
