package com.guiying.module.ui.activity.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.guiying.module.main.R;


public class LoadingDialog extends ProgressDialog {
    TextView mLoadingTextView;
    View mView;

    /**
     * 这里的Context 必须用actiivty 不能用applicationContext
     *
     * @param context
     */
    public LoadingDialog(Context context) {
        this(context, "");
    }

    public LoadingDialog(Context context, CharSequence msg) {
        super(context, com.fd.baselibrary.R.style.LoadingDialogStyle);

        mView = View.inflate(context, R.layout.dialog_loading, null);
        mLoadingTextView = (TextView) mView.findViewById(R.id.mLoadingTextView);
        if (!TextUtils.isEmpty(msg)) {
            mLoadingTextView.setText(msg);
            mLoadingTextView.setVisibility(View.VISIBLE);
        } else mLoadingTextView.setVisibility(View.GONE);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(true);
    }


    @Override
    public void show() {
        try {
            if (this.isShowing()) this.dismiss();
            else super.show();
            //setContentView（）一定要在show之后调用
            this.setContentView(mView);
        } catch (WindowManager.BadTokenException exception) {
        }
    }


    public void setMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            if (mLoadingTextView.getVisibility() != View.VISIBLE) {
                mLoadingTextView.setVisibility(View.VISIBLE);
            }
            mLoadingTextView.setText(message);
        }
    }

    public void setMessage(@StringRes int message) {
        if (mLoadingTextView.getVisibility() != View.VISIBLE) {
            mLoadingTextView.setVisibility(View.VISIBLE);
        }
        mLoadingTextView.setText(message);
    }
}
