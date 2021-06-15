package com.guiying.module.ui.activity.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.guiying.module.ui.activity.callback.IBaseDisplay;
import com.guiying.module.ui.activity.dialog.LoadingDialog;


public  abstract class BaseActivity extends Activity implements IBaseDisplay, View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        initialize();
        //显示当前的Activity路径
//        if (BuildConfig.DEBUG) L.e("当前打开的Activity:  " + getClass().getName());
        ViewManager.getInstance().addActivity(this);
    }
    protected void bindView() {
        setContentView(getLayoutId());
    }
    @LayoutRes
    protected abstract int getLayoutId();


    protected abstract void initialize();
/***********************************  LoadingDialog start   ***********************************/

    /**
     * 显示加载框
     */
    @Override
    public void showProgressDialog() {
        showLoading(this, null);
    }

    /**
     * 显示加载框（带文字）
     */
    @Override
    public void showProgressDialog(CharSequence message) {
        showLoading(this, message);
    }

    /**
     * 隐藏加载框
     */
    @Override
    public void hideProgressDialog() {
        dismissLoading();
    }


    private int mCount = 0;
    private LoadingDialog mLoadingDialog;

    public synchronized void showLoading(Activity activity, CharSequence message) {
        if (mCount == 0) {
            mLoadingDialog = new LoadingDialog(activity, message);
            mLoadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    mCount = 0;
                }
            });
            mLoadingDialog.show();
        }
        mCount++;
    }

    public synchronized void dismissLoading() {
        if (mCount == 0) {
            return;
        }
        mCount--;
        if (mCount == 0 && mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    public void clearLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mCount = 0;
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }



    public static final long INTERVAL = 1000L; //防止连续点击的时间间隔
    private static long lastClickTime = 0L; //上一次点击的时间

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < INTERVAL) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    @Override
    public void onClick(View view) {

    }


}
