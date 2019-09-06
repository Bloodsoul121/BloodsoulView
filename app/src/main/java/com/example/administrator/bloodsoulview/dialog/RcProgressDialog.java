package com.example.administrator.bloodsoulview.dialog;

import android.app.Activity;
import android.content.Context;

/**
 * 顯示進度條的對話框
 *
 * @author 朱遠昕
 *
 */
public class RcProgressDialog {

    private static LoadingDialog mProgressDialog = null;

    public static LoadingDialog showProgressDialog(Context context, String message, boolean cancelable) {
        closeProgressDialog();
        if (context != null) {
            mProgressDialog = new LoadingDialog(context);
            if (message != null) {
                mProgressDialog.setText(message);
            }

            mProgressDialog.show();
            mProgressDialog.setCancelable(cancelable);
            mProgressDialog.setCanceledOnTouchOutside(cancelable);
            return mProgressDialog;
        }

        return null;
    }


    public static LoadingDialog showProgressDialogWithText(Context context, String message, boolean cancelable) {
        return showProgressDialog(context, message, cancelable);
    }

    public static LoadingDialog showProgressDialog(Context context) {
        return showProgressDialog(context, null, true);
    }

    public static LoadingDialog showProgressDialog(Context context, int res) {
        return showProgressDialog(context);
    }

    public static LoadingDialog showProgressDialog(Context context, int res,
                                                   boolean cancelable) {
        return showProgressDialog(context, null, cancelable);
    }

    /**
     * 關閉進度對話框
     */
    public static void closeProgressDialog() {
        if (mProgressDialog != null) {
            Context context = mProgressDialog.getContext();
            if (mProgressDialog.getContext() != null && mProgressDialog.isShowing()) {
                if (context instanceof Activity && ((Activity) context).isFinishing()) {
                } else {
                    mProgressDialog.dismiss();
                }
            }
            mProgressDialog = null;
        }
    }

}
