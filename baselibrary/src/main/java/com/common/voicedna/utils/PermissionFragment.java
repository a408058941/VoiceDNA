package com.common.voicedna.utils;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;

public class PermissionFragment extends Fragment {

    // 请求permission的权限
    private static final int PERMISSIONS_CODE = 42;

    private CallBack callBack;

    public PermissionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    // 请求权限
    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions(String[] permissions,CallBack callBack) {
        this.callBack=callBack;
        requestPermissions(permissions, PERMISSIONS_CODE);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(getUnrequestPermission(permissions).isEmpty()){
            if(callBack!=null){
                callBack.onSuccess();
            }
        }else {
            if(callBack!=null){
                callBack.onFail();
            }
        }
    }

    // 是否授权过
    @TargetApi(Build.VERSION_CODES.M)
    public boolean isGranted(String permission) {
        return getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    // 查看权限是否符合policy的规定
    @TargetApi(Build.VERSION_CODES.M)
    public boolean isRevoked(String permission) {
        return getActivity().getPackageManager().isPermissionRevokedByPolicy(permission, getActivity().getPackageName());
    }

    /**
     * 获取未受理的权限
     */
    public ArrayList<String> getUnrequestPermission(String[] permissions) {
        ArrayList<String> unrequestPermission = new ArrayList<>();
        for (String permission : permissions) {
            if (isGranted(permission)) {
                continue;
            }
            if (isRevoked(permission)) {
                continue;
            }
            unrequestPermission.add(permission);
        }
        return unrequestPermission;
    }

}
