package com.common.voicedna.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Build;

import java.util.ArrayList;

public class PermissionUtil {
    private PermissionFragment mRxFragment;
    private String TAG = "com.rx.Rx.voiceai.kws";
    // 未申请的权限
    private ArrayList<String> mUnRequestPermissions;

    public PermissionUtil(Activity activity) {
        mRxFragment = getRxFragment(activity);
    }

    private PermissionFragment getRxFragment(Activity activity) {
        // 根据tag找Fragment
        PermissionFragment systemFragment = findRxFragment(activity);

        // 判断是否有此Activity
        boolean isNewInstance = systemFragment == null;
        // 如果没有
        if (isNewInstance) {
            // 创建一个
            systemFragment = new PermissionFragment();
            // 拿FragmentManager
            FragmentManager fragmentManager = activity.getFragmentManager();
            // 通过事物添加
            fragmentManager
                    .beginTransaction()
                    .add(systemFragment, TAG)
                    .commitAllowingStateLoss();// 防止手机横竖屏切换时崩溃
            fragmentManager.executePendingTransactions();// 强制创建没有调用的Fragment
        }
        return systemFragment;
    }

    // 根据tag找Fragment
    private PermissionFragment findRxFragment(Activity activity) {
        return (PermissionFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }




    // 请求权限
    public PermissionUtil permission(String... permissions) {
        // 未申请的权限
        // 未申请的权限
        mUnRequestPermissions = new ArrayList<>();
        // 遍历权限
        for (String requestPermission : permissions) {
            if (isGranted(requestPermission)) {
                continue;
            }
            if (isRevoked(requestPermission)) {
                continue;
            }
            // 添加到未授权的集合
            mUnRequestPermissions.add(requestPermission);
        }
        return this;
    }

    // 请求权限
    public void request(CallBack callBack) {

        // 6.0 以下或者是没有未申请的权限 直接返回true
        if (!isMarshmallow() || mUnRequestPermissions.isEmpty()) {
            callBack.onSuccess();
            return;
        }

        requestPermissionsFromFragment(mUnRequestPermissions.toArray(new String[mUnRequestPermissions.size()]), callBack);
    }

    /*******************************************************************************************************************************************/
    // 申请权限
    @TargetApi(Build.VERSION_CODES.M)
    void requestPermissionsFromFragment(String[] permissions, CallBack callBack) {
        mRxFragment.requestPermissions(permissions, callBack);
    }

    // 查看权限是否符合policy的规定
    boolean isRevoked(String permission) {
        return isMarshmallow() && mRxFragment.isRevoked(permission);
    }

    // 是否已经申请过
    boolean isGranted(String permission) {
        return !isMarshmallow() || mRxFragment.isGranted(permission);
    }

    // 是否是6.0
    boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
