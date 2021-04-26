package com.fd.baselibrary.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fd.baselibrary.R;

public class NetworkUtil {

    /**
     * 判断网络连接是否已开
     * true 已打开  false 未打开
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        boolean isConnected = false;
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if (network != null && conManager.getActiveNetworkInfo() != null) {
            isConnected = conManager.getActiveNetworkInfo().isAvailable();
        }
        return isConnected;
    }

    /**
     * 判断是否是WIFI连接
     *
     * @param context
     * @return
     */
    public static boolean isWIFI(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null || connectivityManager.getActiveNetworkInfo() == null)
            return false;
        return connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }



}
