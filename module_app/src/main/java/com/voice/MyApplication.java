package com.voice;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;



/**
 * <p>这里仅需做一些初始化的工作</p>
 *
 * @author 张华洋 2017/2/15 20:14
 * @version V1.2.0
 * @name MyApplication
 */

public class MyApplication extends Application {

    private String TAG = "MyApplication";


    @Override
    public void onCreate() {
        super.onCreate();


    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // dex突破65535的限制
        MultiDex.install(this);
    }


}
