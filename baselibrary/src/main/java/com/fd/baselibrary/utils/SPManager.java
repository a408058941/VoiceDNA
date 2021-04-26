package com.fd.baselibrary.utils;


import java.util.HashMap;
import java.util.Map;

public class SPManager extends BaseSPManager {
    //访问网络时候的token
    public static final String USER_TOKEN = "user_token"; //token值
    public static final String USER_TOKEN_KEY = "token"; //token键
    //访问网络的公共参数
    public static final String USER_PUBLIC_MAP = "user_public_map"; //

    public static String getUserToken() {
        return (String) SPUtil.get(USER_TOKEN,"");
    }
    public static void setUserToken(String login_cookie) {
        SPUtil.put(USER_TOKEN, login_cookie);
    }


    public static String getUserTokenKey() {
        return (String) SPUtil.get(USER_TOKEN_KEY,"x-vai-token");
    }
    public static void setUserTokenKey(String token) {
        SPUtil.put(USER_TOKEN_KEY, token);
    }
    public static Map<String, String> getUserPublicMap() {
        Map<String, String> map=    (Map<String, String>) SPUtil.getSerializableObject(USER_PUBLIC_MAP);
        if (map==null){
            map=new HashMap<>();
        }
        return map;
    }
    public static void setUserPublicMap(Map<String, String> map) {
        SPUtil.putSerializableObject(USER_PUBLIC_MAP, map);
    }

}
