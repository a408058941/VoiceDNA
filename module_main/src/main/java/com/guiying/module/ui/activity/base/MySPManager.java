package com.guiying.module.ui.activity.base;

import com.common.voicedna.utils.SPManager;
import com.common.voicedna.utils.SPUtil;

public class MySPManager extends SPManager {
    public static final String USER_GROUPID = "user_groupid";
    public static final String USER_GROUPNAME = "user_groupname";
    public static final String USER_NAME = "user_name";
    private  static  final  String APP_ID = "app_id";
    private  static  final  String APPSECRET = "appsecret";
    private  static  final  String APPTYPE = "apptype";
    private  static  final  String TYPE = "type"; //1 测试服务器，2 正式服务器

    public static void setAppType(int apptype) {
        SPUtil.put(APPTYPE, apptype);
    }
    public static int getAppType() {
        return (Integer) SPUtil.get(APPTYPE, 1);
    }

    public static void setType(int type) {
        SPUtil.put(TYPE, type);
    }
    public static int getType() {
        return (Integer) SPUtil.get(TYPE, 1);
    }

    public static void setAppId(String app_id) {
        SPUtil.put(APP_ID, app_id);
    }
    public static String getAppId() {
        return (String) SPUtil.get(APP_ID, "");
    }

    public static void setAppSecret(String appsecret) {
        SPUtil.put(APPSECRET, appsecret);
    }

    public static String getAppSecret() {
        return (String) SPUtil.get(APPSECRET, "");
    }

    public static String getUserGroupid() {
        return (String) SPUtil.get(USER_GROUPID, "");
    }

    public static void setUserGroupid(String user_groupid) {
        SPUtil.put(USER_GROUPID, user_groupid);
    }

    public static String getUserGroupName() {
        return (String) SPUtil.get(USER_GROUPNAME, "");
    }

    public static void setUserGroupName(String user_groupName) {
        SPUtil.put(USER_GROUPNAME, user_groupName);
    }



    public static String getUserName() {
        return (String) SPUtil.get(USER_NAME, "");
    }

    public static void setUserName(String user_groupid) {
        SPUtil.put(USER_NAME, user_groupid);
    }

}
