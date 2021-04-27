package com.common.voicedna.base;

import android.app.Activity;
import android.content.Context;

import com.common.voicedna.network.ApiException;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * 用于Activity Fragment 界面交互
 */
public interface IBaseDisplay {
    Context getContext();

    Activity getBaseActivity();

    void showProgressDialog();

    void showProgressDialog(CharSequence message);

    void hideProgressDialog();

    void showError(Throwable t);

    void onApiException(ApiException e);

    void onRequestFinish();

    void changeDayNightMode(boolean isNightMode);

    <T> LifecycleTransformer<T> bindToLifecycle();
}
