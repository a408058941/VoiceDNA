package com.common.voicedna.VoiceRecognize;


import com.common.voicedna.utils.BaseSPManager;
import com.common.voicedna.utils.SPManager;
import com.common.voicedna.utils.SPUtil;

import java.math.BigDecimal;

/**
 * 注册验证配置
 */
public class VoiceRegisterConfig extends SPManager {
    //注册配置参数
    private static final String REGISTER_VADLEVEL = "register_vadLevel"; //质检值:1.宽松，3中等，5严格。    若vadSwicth为true则必传该字段
    private static final String REGISTER_DIASWITCH = "register_diaSwitch"; //分割开关,不传默认false
    private static final String REGISTER_FILTERTYPE = "register_filtertype"; //分割过滤类型:-1:不过滤,0关键字过滤,1声纹过滤。    若diaSwitch为true则必传
    private static final String REGISTER_VOICEFILTER = "register_voiceFilter"; //声纹过滤：过滤分组    关键字过滤：关键字串(例：你好/我好&大家好)
    private static final String REGISTER_VADSWICTH = "register_vadSwicth"; //质检开关,不传默认true
    private static final String REGISTER_VADSWICTH_SERVICE = "register_vadswicth_service"; //服务器质检开关,不传默认不传默认false
    private static final String REGISTER_SPEECHENERGY = "register_speechEnergy"; //注册音量大小（dB） 默认0.65f
    private static final String REGISTER_SPEECHDURATION = "register_speechduration"; //注册有效时长（秒） 默认-15
    private static final String REGISTER_VADSNR = "register_vadsnr"; //注册信噪比（dB） 默认8
    private static final String REGISTER_VADCLIPPINGRATIO = "register_vadClippingRatio"; //注册截幅比（%） 默认5
    private static final String REGISTER_VOICEASV = "register_voiceAsv"; //活体检测开关 不传默认true
    private static final String REGISTER_SPOOFSCORE = "register_spoofscore"; //活体检测阈值 默认0.6
    private static final String REGISTER_ASRSWITCH = "register_asrSwitch"; //文本检测开关
    private static final String REGISTER_ASRTHRESHOLD = "register_asrThreshold"; //文本检测阈值:0-1.0
    private static final String REGISTER_VPRCSWITCH = "register_vprcSwitch"; //同人检测开关
    private static final String REGISTER_VPRCTHRSHOLD = "register_vprcThrshold"; //同人检测阈值0-100
    //同人检测开关,不传默认true
    public static Boolean getRegisterVprcSwitch() {
        return (Boolean) SPUtil.get(REGISTER_VPRCSWITCH, true);
    }


    private static void setRegisterVprcSwitch(Boolean register_vprcSwitch) {
        SPUtil.put(REGISTER_ASRSWITCH, register_vprcSwitch);
    }

    //同人检测阈值:0-1.0 默认0.6
    public static float getRegisterVprcThrshold() {
        return (float) SPUtil.get(REGISTER_VPRCTHRSHOLD, 0.6f);
    }

    private static void setRegisterVprcThrshold(float vprcThrshold) {
        SPUtil.put(REGISTER_VPRCTHRSHOLD, vprcThrshold);
    }
    //文本检测开关,不传默认true
    public static Boolean getRegisterAsrSwitch() {
        return (Boolean) SPUtil.get(REGISTER_ASRSWITCH, true);
    }


    private static void setRegisterAsrSwitch(Boolean vadSwicth) {
        SPUtil.put(REGISTER_ASRSWITCH, vadSwicth);
    }

    //文本检测阈值:0-1.0 默认0.75f
    public static float getRegisterAsrThreshold() {
        return (float) SPUtil.get(REGISTER_ASRTHRESHOLD, 0.75f);
    }

    public static void setRegisterAsrThreshold(float asrThreshold) {
        SPUtil.put(REGISTER_ASRTHRESHOLD, asrThreshold);
    }



    //质检开关,不传默认true
    public static Boolean getRegisterVadSwicth() {
        return (Boolean) SPUtil.get(REGISTER_VADSWICTH, true);
    }


    public static void setRegisterVadSwicth(Boolean vadSwicth) {
        SPUtil.put(REGISTER_VADSWICTH, vadSwicth);
    }
    //服务器质检开关,不传默认不传默认false
    private static Boolean getRegisterVadSwicthService() {
        return (Boolean) SPUtil.get(REGISTER_VADSWICTH_SERVICE, false);
    }


    private static void setRegisterVadSwicthService(Boolean vadSwicth) {
        SPUtil.put(REGISTER_VADSWICTH_SERVICE, vadSwicth);
    }
    //注册音量大小（dB） 默认-35
    public static float getRegisterSpeechenergy() {
        return (float) SPUtil.get(REGISTER_SPEECHENERGY, -35f);
    }

    public static void setRegisterSpeechenergy(float Volume) {
        SPUtil.put(REGISTER_SPEECHENERGY, Volume);
    }

    //注册有效时长（秒） 默认8
    public static int getRegisterSpeechDurationStandard() {
        return (int) SPUtil.get(REGISTER_SPEECHDURATION, 15);
    }

    public static void setRegisterSpeechDurationStandard(int Speech) {
        SPUtil.put(REGISTER_SPEECHDURATION, Speech);
    }

    //注册信噪比（dB） 默认8
    public static float getRegisterVadSNR() {
        return (float) SPUtil.get(REGISTER_VADSNR, 8.0f);
    }

    public static void setRegisterVadSNR(float snr) {
        SPUtil.put(REGISTER_VADSNR, snr);
    }

    //注册截幅比（%） 默认5
    public static float getRegisterClippingRatio() {
        return (float) SPUtil.get(REGISTER_VADCLIPPINGRATIO, 5.0f);
    }

    public static void setRegisterVadClippingRatio(float ClippingRatio) {
        SPUtil.put(REGISTER_VADCLIPPINGRATIO, ClippingRatio);
    }


    //    活体检测开关 不传默认true
    public static Boolean getRegisterVoiceAsv() {
        return (Boolean) SPUtil.get(REGISTER_VOICEASV, true);
    }

    public static void setRegisterVoiceAsv(Boolean voiceAsv) {
        SPUtil.put(REGISTER_VOICEASV, voiceAsv);
    }

    //活体检测阈值 默认0.6
    public static float getRegisterSpoofscore() {
        return (float) SPUtil.get(REGISTER_SPOOFSCORE, 0.6f);
    }

    public static void setRegisterSpoofscore(float voiceAsvValue) {
        SPUtil.put(REGISTER_SPOOFSCORE, voiceAsvValue);
    }

    //质检值:1.宽松，3中等，5严格。    若vadSwicth为true则必传该字段
    public static Integer getRegisterVadLevel() {
        return (Integer) SPUtil.get(REGISTER_VADLEVEL, 3);
    }

    public static void setRegisterVadLevel(Integer vadLevel) {
        if (vadLevel!=1|vadLevel!=3|vadLevel!=5){
            SPUtil.put(REGISTER_VADLEVEL, vadLevel);
        }

    }

    //分割开关,不传默认false
    public static Boolean getRegisterDiaSwitch() {
        return (Boolean) SPUtil.get(REGISTER_DIASWITCH, false);
    }

    private static void setRegisterDiaSwitch(Boolean DiaSwitch) {
        SPUtil.put(REGISTER_DIASWITCH, DiaSwitch);
    }

    //分割过滤类型:-1:不过滤,0关键字过滤,1声纹过滤。    若diaSwitch为true则必传
    public static Integer getRegisterFiltertype() {
        return (Integer) SPUtil.get(REGISTER_FILTERTYPE, -1);
    }

    private static void setRegisterFiltertype(Integer filtertype) {
        SPUtil.put(REGISTER_FILTERTYPE, filtertype);
    }

    //声纹过滤：过滤分组    关键字过滤：关键字串(例：你好/我好&大家好)
    public static String getRegisterVoiceFilter() {
        return (String) SPUtil.get(REGISTER_VOICEFILTER, "");
    }

    private static void setRegisterVoiceFilter(int VoiceFilter) {
        SPUtil.put(REGISTER_VOICEFILTER, VoiceFilter);
    }


    //验证配置参数
    public static final String VERIFICATION_VADLEVEL = "verification_vadLevel"; //质检值:1.宽松，3中等，5严格。    若vadSwicth为true则必传该字段
    public static final String VERIFICATION_DIASWITCH = "verification_diaSwitch"; //分割开关,不传默认false
    public static final String VERIFICATION_FILTERTYPE = "verification_filtertype"; //分割过滤类型:-1:不过滤,0关键字过滤,1声纹过滤。    若diaSwitch为true则必传
    public static final String VERIFICATION_VOICEFILTER = "verification_voiceFilter"; //声纹过滤：过滤分组    关键字过滤：关键字串(例：你好/我好&大家好)
    public static final String VERIFICATION_VADSWICTH = "verification_vadSwicth"; //质检开关,不传默认true
    public static final String VERIFICATION_VADSWICTH_SERVICE = "verification_vadSwicth_service"; //服务端质检开关,不传默认false
    public static final String VERIFICATION_SPEECHENERGY = "verification_speechEnergy"; //验证音量大小（dB） 默认0.65
    private static final String VERIFICATION_SPEECHDURATION = "verification_speechduration"; ///验证有效时长（秒） 默认15
    private static final String VERIFICATION_VADSNR = "verification_vadsnr"; ///验证信噪比（dB） 默认8
    private static final String VERIFICATION_VADCLIPPINGRATIO = "verification_vadClippingRatio"; //验证截幅比（%） 默认5
    public static final String VERIFICATION_VOICEASV = "verification_voiceAsv"; //活体检测开关 不传默认true
    public static final String VERIFICATION_SPOOFSCORE = "verification_spoofscore"; //活体检测阈值 默认0.6
    private static final String VERIFICATION_ASRSWITCH = "verification_asrSwitch"; //文本检测开关
    private static final String VERIFICATION_ASRTHRESHOLD = "verification_asrThreshold"; //文本检测阈值:0-1.0



    //    文本检测开关,不传默认true
    public static Boolean getVerificationAsrswitch() {
        return (Boolean) SPUtil.get(VERIFICATION_ASRSWITCH, true);
    }

    private static void setVerificationAsrswitch(Boolean asrSwitch) {
        SPUtil.put(VERIFICATION_ASRSWITCH, asrSwitch);
    }

    // 文本阈值,0.75
    public static float getVerificationAsrthreshold () {
        return (float) SPUtil.get(VERIFICATION_ASRTHRESHOLD, 0.75f);
    }

    private static void setVerificationAsrthreshold (float asrThreshold) {
        SPUtil.put(VERIFICATION_ASRTHRESHOLD, asrThreshold);
    }




    //    验证质检开关,不传默认true
    public static Boolean getVerificationVadswicth() {
        return (Boolean) SPUtil.get(VERIFICATION_VADSWICTH, true);
    }

    public static void setVerificationVadswicth(Boolean vadSwicth) {
        SPUtil.put(VERIFICATION_VADSWICTH, vadSwicth);
    }
//    服务端质检开关,不传默认false
    public static Boolean getVerificationVadswicthService() {
        return (Boolean) SPUtil.get(VERIFICATION_VADSWICTH_SERVICE, false);
    }

    private static void setVerificationVadswicthService(Boolean vadSwicth) {
        SPUtil.put(VERIFICATION_VADSWICTH_SERVICE, vadSwicth);
    }



    //    验证音量大小（dB） 默认35f
    public static float getSpeechAvgEnergyStandard() {
        return (float) SPUtil.get(VERIFICATION_VADSNR, -35f);
    }

    public static void setSpeechAvgEnergyStandard(float Vadsnr) {
        SPUtil.put(VERIFICATION_VADSNR, Vadsnr);
    }

    //验证有效时长（秒） 默认3
    public static int getVerifySpeechDurationStandard() {
        return (int) SPUtil.get(VERIFICATION_SPEECHDURATION, 15);
    }

    public static void setVerifySpeechDurationStandard(int Speechduration) {
        SPUtil.put(VERIFICATION_SPEECHDURATION, Speechduration);
    }

    //验证信噪比（dB） 默认8
    public static float getSnrStandard() {
        return (float) SPUtil.get(VERIFICATION_SPEECHENERGY, 8.0f);
    }

    public static void setSnrStandard(float SpeechEnergy) {
        SPUtil.put(VERIFICATION_SPEECHENERGY, SpeechEnergy);
    }

    ////验证截幅比（%） 默认5
    public static float getClipPercentStandard() {
        return (float) SPUtil.get(VERIFICATION_VADCLIPPINGRATIO, 5.f);
    }

    public static void setClipPercentStandard(float Vadclippingratio) {
        SPUtil.put(VERIFICATION_VADCLIPPINGRATIO, Vadclippingratio);
    }


    //活体检测开关 不传默认true
    public static Boolean getVerificationVoiceAsv() {
        return (Boolean) SPUtil.get(VERIFICATION_VOICEASV, true);
    }

    public static void setVerificationVoiceAsv(Boolean voiceAsv) {
        SPUtil.put(VERIFICATION_VOICEASV, voiceAsv);
    }

    //活体检测阈值 默认0.52
    public static float getVerificationAsvspoof_score() {
        return (float) SPUtil.get(VERIFICATION_SPOOFSCORE, 0.6f);
    }

    public static void getVerificationAsvspoof_score(float Spoofscore) {
        SPUtil.put(VERIFICATION_SPOOFSCORE, Spoofscore);
    }

    //质检值:1.宽松，3中等，5严格。    若vadSwicth为true则必传该字段
    public static Integer getVerificationVadLevel() {
        return (Integer) SPUtil.get(VERIFICATION_VADLEVEL, 3);
    }

    public static void setVerificationVadLevel(Integer vadLevel) {
        if (vadLevel!=1|vadLevel!=3|vadLevel!=5){
            SPUtil.put(VERIFICATION_VADLEVEL, vadLevel);
        }
    }
    //分割开关,不传默认false
    public static Boolean getVerificationDiaSwitch() {
        return (Boolean) SPUtil.get(VERIFICATION_DIASWITCH, false);
    }

    private static void setVerificationDiaSwitch(Boolean diaSwitch) {
        SPUtil.put(VERIFICATION_DIASWITCH, diaSwitch);
    }
    //分割过滤类型:-1:不过滤,0关键字过滤,1声纹过滤。    若diaSwitch为true则必传
    public static Integer getVerificationFiltertype() {
        return (Integer) SPUtil.get(VERIFICATION_FILTERTYPE, -1);
    }

    private static void setVerificationFiltertype(Integer filtertype) {
        SPUtil.put(VERIFICATION_FILTERTYPE, filtertype);
    }
    //声纹过滤：过滤分组    关键字过滤：关键字串(例：你好/我好&大家好)
    public static String getVerificationVoiceFilter() {
        return (String) SPUtil.get(VERIFICATION_VOICEFILTER, "");
    }

    private static void setVerificationVoiceFilter(int VoiceFilter) {
        SPUtil.put(VERIFICATION_VOICEFILTER, VoiceFilter);
    }
}
