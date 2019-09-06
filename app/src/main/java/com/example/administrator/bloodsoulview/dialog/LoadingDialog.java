package com.example.administrator.bloodsoulview.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.administrator.bloodsoulview.R;

/**
 * @author 许潜 2014年10月14日
 * 
 *         簡單的loading提示框
 */
public class LoadingDialog extends BaseDialog {
	private Context mContext;
	private LoadingDialogView mLoadingDialogView;

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
		init(context);
	}

	public LoadingDialog(Context context) {
		this(context, R.style.loading_dialog);
	}

	private void init(Context context) {
		mContext = context;
		mLoadingDialogView = new LoadingDialogView(getContext());

		setCanceledOnTouchOutside(false);
		setCancelable(true);
	}

	public Activity getActContext(){
		return (Activity) mContext;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(mLoadingDialogView);
	}
	
	@Override
	protected void onStart() {
		super.onStart();

		if(mLoadingDialogView != null) {
			mLoadingDialogView.show();
		}
	}

	@Override
	public void dismiss() {
		if(mLoadingDialogView != null) {
			mLoadingDialogView.dismiss();
		}

		// 如果关联的activity已经结束
		if (mContext instanceof Activity && ((Activity) mContext).isFinishing()) {
			return;
		}

		super.dismiss();
	}

	public void setText(String text){
		if(mLoadingDialogView != null) {
			mLoadingDialogView.setText(text);
		}
	}
}
