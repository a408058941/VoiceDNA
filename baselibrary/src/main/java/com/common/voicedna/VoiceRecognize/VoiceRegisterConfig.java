package com.common.voicedna.VoiceRecognize;


import com.common.voicedna.utils.BaseSPManager;
import com.common.voicedna.utils.SPManager;
import com.common.voicedna.utils.SPUtil;

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

    //质检开关,不传默认true
    public static Boolean getRegisterVadSwicth() {
        return (Boolean) SPUtil.get(REGISTER_VADSWICTH, true);
    }


    public static void setRegisterVadSwicth(Boolean vadSwicth) {
        SPUtil.put(REGISTER_VADSWICTH, vadSwicth);
    }
    //服务器质检开关,不传默认不传默认false
    public static Boolean getRegisterVadSwicthService() {
        return (Boolean) SPUtil.get(REGISTER_VADSWICTH_SERVICE, false);
    }


    public static void setRegisterVadSwicthService(Boolean vadSwicth) {
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
    public static int getRegisterSpeechDuration() {
        return (int) SPUtil.get(REGISTER_SPEECHDURATION, 15);
    }

    public static void setRegisterSpeechDuration(int Speech) {
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

    public static void setRegisterSpoofscore(Boolean voiceAsvValue) {
        SPUtil.put(REGISTER_SPOOFSCORE, voiceAsvValue);
    }

    //质检值:1.宽松，3中等，5严格。    若vadSwicth为true则必传该字段
    public static Integer getRegisterVadLevel() {
        return (Integer) SPUtil.get(REGISTER_VADLEVEL, 3);
    }

    public static void setUserVadLevel(Integer vadLevel) {
        SPUtil.put(REGISTER_VADLEVEL, vadLevel);
    }

    //分割开关,不传默认false
    public static Boolean getRegisterDiaSwitch() {
        return (Boolean) SPUtil.get(REGISTER_DIASWITCH, false);
    }

    public static void setRegisterDiaSwitch(Boolean DiaSwitch) {
        SPUtil.put(REGISTER_DIASWITCH, DiaSwitch);
    }

    //分割过滤类型:-1:不过滤,0关键字过滤,1声纹过滤。    若diaSwitch为true则必传
    public static Integer getRegisterFiltertype() {
        return (Integer) SPUtil.get(REGISTER_FILTERTYPE, -1);
    }

    public static void setRegisterFiltertype(Integer filtertype) {
        SPUtil.put(REGISTER_FILTERTYPE, filtertype);
    }

    //声纹过滤：过滤分组    关键字过滤：关键字串(例：你好/我好&大家好)
    public static String getRegisterVoiceFilter() {
        return (String) SPUtil.get(REGISTER_VOICEFILTER, "");
    }

    public static void setRegisterVoiceFilter(int VoiceFilter) {
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
    public static final String VERIFICATION_ASV = "verification_asv1spoofscore"; //1:1阈值 默认65
    public static final String VERIFICATION_ASV1N = "verification_asv1Nspoofscore"; //1:N阈值 默认65
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

    public static void setVerificationVadswicthService(Boolean vadSwicth) {
        SPUtil.put(VERIFICATION_VADSWICTH_SERVICE, vadSwicth);
    }



    //    验证音量大小（dB） 默认35f
    public static float getVerificationVadsnr() {
        return (float) SPUtil.get(VERIFICATION_VADSNR, -35f);
    }

    public static void setVerificationVadsnr(float Vadsnr) {
        SPUtil.put(VERIFICATION_VADSNR, Vadsnr);
    }

    //验证有效时长（秒） 默认3
    public static int getVerificationSpeechduration() {
        return (int) SPUtil.get(VERIFICATION_SPEECHDURATION, 15);
    }

    public static void setVerificationSpeechduration(int Speechduration) {
        SPUtil.put(VERIFICATION_SPEECHDURATION, Speechduration);
    }

    //验证信噪比（dB） 默认8
    public static float getVerificationSpeechEnergy() {
        return (float) SPUtil.get(VERIFICATION_SPEECHENERGY, 8.0f);
    }

    public static void setVerificationSpeechEnergy(float SpeechEnergy) {
        SPUtil.put(VERIFICATION_SPEECHENERGY, SpeechEnergy);
    }

    ////验证截幅比（%） 默认5
    public static float getVerificationVadclippingratio() {
        return (float) SPUtil.get(VERIFICATION_VADCLIPPINGRATIO, 5.f);
    }

    public static void setVerificationVadclippingratio(float Vadclippingratio) {
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
    public static float getVerificationSpoofscore() {
        return (float) SPUtil.get(VERIFICATION_SPOOFSCORE, 0.6f);
    }

    public static void setVerificationSpoofscore(float Spoofscore) {
        SPUtil.put(VERIFICATION_SPOOFSCORE, Spoofscore);
    }

    //质检值:1.宽松，3中等，5严格。    若vadSwicth为true则必传该字段
    public static Integer getVerificationVadLevel() {
        return (Integer) SPUtil.get(VERIFICATION_VADLEVEL, 3);
    }

    public static void setVerificationVadLevel(Integer vadSwicth) {
        SPUtil.put(VERIFICATION_VADLEVEL, vadSwicth);
    }
    //分割开关,不传默认false
    public static Boolean getVerificationDiaSwitch() {
        return (Boolean) SPUtil.get(VERIFICATION_DIASWITCH, false);
    }

    public static void setVerificationDiaSwitch(Boolean diaSwitch) {
        SPUtil.put(VERIFICATION_DIASWITCH, diaSwitch);
    }
    //分割过滤类型:-1:不过滤,0关键字过滤,1声纹过滤。    若diaSwitch为true则必传
    public static Integer getVerificationFiltertype() {
        return (Integer) SPUtil.get(VERIFICATION_FILTERTYPE, -1);
    }

    public static void setVerificationFiltertype(Integer filtertype) {
        SPUtil.put(VERIFICATION_FILTERTYPE, filtertype);
    }
    //声纹过滤：过滤分组    关键字过滤：关键字串(例：你好/我好&大家好)
    public static String getVerificationVoiceFilter() {
        return (String) SPUtil.get(VERIFICATION_VOICEFILTER, "");
    }

    public static void setVerificationVoiceFilter(int VoiceFilter) {
        SPUtil.put(VERIFICATION_VOICEFILTER, VoiceFilter);
    }

    //1:1阈值 默认65
    public static Integer getVerificationAsv() {
        return (Integer) SPUtil.get(VERIFICATION_ASV, 65);
    }

    public static void setVerificationAsv(Integer asv) {
        SPUtil.put(VERIFICATION_FILTERTYPE, asv);
    }

    //1:N阈值 默认65
    public static Integer getVerificationAsv1N() {
        return (Integer) SPUtil.get(VERIFICATION_ASV1N, 65);
    }

    public static void setVerificationAsv1N(Integer asv) {
        SPUtil.put(VERIFICATION_ASV1N, asv);
    }
}
