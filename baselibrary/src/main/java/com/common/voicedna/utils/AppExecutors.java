package com.common.voicedna.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public class AppExecutors {
    //参数初始化
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**磁盘IO线程池**/
    private final Executor mDiskIO;
    /**网络IO线程池**/
    private final Executor mNetworkIO;
    /**UI线程**/
    private final Executor mMainThread;
    /**定时任务线程池**/
    private final ScheduledThreadPoolExecutor schedule;

    private static AppExecutors instance;

    private static Object object = new Object();

    public static AppExecutors getInstance() {
        if (instance == null) {
            synchronized (object) {
                if (instance == null) {
                    instance = new AppExecutors();
                }
            }
        }
        return instance;
    }

    private AppExecutors() {

        this.mDiskIO = Executors.newSingleThreadExecutor(new MyThreadFactory("single"));

        this.mNetworkIO = Executors.newFixedThreadPool(CPU_COUNT * 2 + 1, new MyThreadFactory("fixed"));

        this.mMainThread = new MainThreadExecutor();

        this.schedule = new ScheduledThreadPoolExecutor(CPU_COUNT * 2 + 1, new MyThreadFactory("sc"), new ThreadPoolExecutor.AbortPolicy());
    }

    class MyThreadFactory implements ThreadFactory {

        private final String name;
        private int count = 0;

        MyThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            count++;
            return new Thread(r, name + "-" + count + "-Thread");
        }
    }
    /**
     * 磁盘IO线程池（单线程）
     *
     * 和磁盘操作有关的进行使用此线程(如读写数据库,读写文件)
     * 禁止延迟,避免等待
     * 此线程不用考虑同步问题
     */
    public Executor diskIO() {
        return mDiskIO;
    }
    /**
     * 定时(延时)任务线程池
     *
     * 替代Timer,执行定时任务,延时任务
     */
    public ScheduledThreadPoolExecutor scheduledExecutor() {
        return schedule;
    }
    /**
     * 网络IO线程池
     *
     * 网络请求,异步任务等适用此线程
     * 不建议在这个线程 sleep 或者 wait
     */
    public Executor networkIO() {
        return mNetworkIO;
    }
    /**
     * UI线程
     *
     * Android 的MainThread
     * UI线程不能做的事情这个都不能做
     */
    public Executor mainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
