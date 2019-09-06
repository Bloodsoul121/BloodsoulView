package com.example.administrator.bloodsoulview.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;

/**
 * Created by chen_jingling on 2016/11/16.
 */
public class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int styleResId) {
        super(context, styleResId);
    }

    @Override
    public void show() {
        // 如果关联的activity已经结束
        if (getContext() == null ||
                (getContext() != null && getContext() instanceof Activity && ((Activity) getContext()).isFinishing())) {
            return;
        }
        try {
            super.show();
        } catch (Exception e) {
            Log.e("TEST", "BaseDialog dismiss show " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        // 如果关联的activity已经结束
        if (getContext() == null ||
                (getContext() != null && getContext() instanceof Activity && ((Activity) getContext()).isFinishing())) {
            return;
        }
        try {
            super.dismiss();
        } catch (Exception e) {
            Log.e("TEST", "BaseDialog dismiss error " + e.getMessage());
            e.printStackTrace();
        }
    }
}
