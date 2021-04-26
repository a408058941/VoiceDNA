package com.common.voicedna.VoiceRecognize;

import com.fd.baselibrary.utils.BaseSPManager;
import com.fd.baselibrary.utils.SPUtil;

/**
 * 注册配置
 */
public class VoiceRegisterConfig extends BaseSPManager {
    //注册配置参数
    public static final String USER_VADSWICTH = "user_vadSwicth"; //质检开关,不传默认false
    public static final String USER_VADLEVEL = "user_vadLevel"; //质检值:1.宽松，3中等，5严格。    若vadSwicth为true则必传该字段
    public static final String USER_DIASWITCH = "user_diaSwitch"; //分割开关,不传默认false
    public static final String USER_FILTERTYPE = "user_filtertype"; //分割过滤类型:-1:不过滤,0关键字过滤,1声纹过滤。    若diaSwitch为true则必传
    public static final String USER_VOICEFILTER = "user_voiceFilter"; //声纹过滤：过滤分组    关键字过滤：关键字串(例：你好/我好&大家好)
    public static Boolean getUserVadSwicth() {
        return (Boolean) SPUtil.get(USER_VADSWICTH, false);
    }

    public static void setUserVadSwicth(Boolean vadSwicth) {
        SPUtil.put(USER_VADSWICTH, vadSwicth);
    }



    public static Integer getUserVadLevel() {
        return (Integer) SPUtil.get(USER_VADLEVEL, 3);
    }

    public static void setUserVadLevel(Integer vadLevel) {
        SPUtil.put(USER_VADLEVEL, vadLevel);
    }


    public static Boolean getUserDiaSwitch() {
        return (Boolean) SPUtil.get(USER_DIASWITCH, false);
    }

    public static void setUserDiaSwitch(Boolean DiaSwitch) {
        SPUtil.put(USER_DIASWITCH, DiaSwitch);
    }



    public static Integer getUserFiltertype() {
        return (Integer) SPUtil.get(USER_FILTERTYPE, -1);
    }

    public static void setUserFiltertype(Integer filtertype) {
        SPUtil.put(USER_FILTERTYPE, filtertype);
    }


    public static String getUserVoiceFilter() {
        return (String) SPUtil.get(USER_VOICEFILTER, "");
    }

    public static void setUserVoiceFilter(int VoiceFilter) {
        SPUtil.put(USER_VOICEFILTER, VoiceFilter);
    }



    //验证配置参数
    public static final String VERIFICATION_VADSWICTH = "verification_vadSwicth"; //质检开关,不传默认false
    public static final String VERIFICATION_VADLEVEL = "verification_vadLevel"; //质检值:1.宽松，3中等，5严格。    若vadSwicth为true则必传该字段
    public static final String VERIFICATION_DIASWITCH = "verification_diaSwitch"; //分割开关,不传默认false
    public static final String VERIFICATION_FILTERTYPE = "verification_filtertype"; //分割过滤类型:-1:不过滤,0关键字过滤,1声纹过滤。    若diaSwitch为true则必传
    public static final String VERIFICATION_VOICEFILTER = "verification_voiceFilter"; //声纹过滤：过滤分组    关键字过滤：关键字串(例：你好/我好&大家好)
    public static Boolean getVerification_Vadswicth() {
        return (Boolean) SPUtil.get(VERIFICATION_VADSWICTH, false);
    }

    public static void setVerificationVadswicth(Boolean vadSwicth) {
        SPUtil.put(VERIFICATION_VADSWICTH, vadSwicth);
    }


    public static Integer getVerificationVadLevel() {
        return (Integer) SPUtil.get(VERIFICATION_VADLEVEL, 3);
    }

    public static void setVerificationVadLevel(Integer vadSwicth) {
        SPUtil.put(VERIFICATION_VADLEVEL, vadSwicth);
    }

    public static Boolean getVerificationDiaSwitch() {
        return (Boolean) SPUtil.get(VERIFICATION_DIASWITCH, false);
    }

    public static void setVerificationDiaSwitch(Boolean diaSwitch) {
        SPUtil.put(VERIFICATION_DIASWITCH, diaSwitch);
    }
    public static Integer getVerificationFiltertype() {
        return (Integer) SPUtil.get(VERIFICATION_FILTERTYPE, -1);
    }

    public static void setVerificationFiltertype(Integer filtertype) {
        SPUtil.put(VERIFICATION_FILTERTYPE, filtertype);
    }

    public static String getVerificationVoiceFilter() {
        return (String) SPUtil.get(VERIFICATION_VOICEFILTER, "");
    }

    public static void setVerificationVoiceFilter(int VoiceFilter) {
        SPUtil.put(VERIFICATION_VOICEFILTER, VoiceFilter);
    }
}
