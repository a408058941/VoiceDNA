package com.guiying.module.ui.activity.base;

import android.app.Activity;

import java.util.Stack;

public class ViewManager {
    private  static Stack<Activity> activityStack;
    public static ViewManager getInstance() {
        return ViewManagerHolder.sInstance;
    }

    private static class ViewManagerHolder {
        private static final ViewManager sInstance = new ViewManager();
    }

    private ViewManager() {
    }
    /**
     * 添加指定Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }
    /**
     * 结束全部的Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }


}
