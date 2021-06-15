package com.iva.constant;

import android.media.AudioFormat;
import android.os.Environment;

import java.io.File;

/**
 * Created by qing on 11/01/2018.
 */

public class Constants {

    public static String ROOT_PATH = Environment.getExternalStorageDirectory() + "/";



    public static String SDK_ROOT_DIR = ROOT_PATH + "VoiceKey";

    public static String WAKEN_ROOT_PATH = SDK_ROOT_DIR + File.separator + "waken";

    public static String RECORD_PATH = ROOT_PATH + "record/";
    public static String MODEL_PATH = ROOT_PATH + "model/";

    //釆波率  8000, 11025, 16000, 22050, 32000,44100, 47250, 48000
    public static final int SAMPLE_RATE_IN_HZ = 16000;
    public static final int CHANNEL_IN = AudioFormat.CHANNEL_IN_MONO;
    public static final int ENCODING_PCM = AudioFormat.ENCODING_PCM_16BIT;
    public static final int DATA_QUEUE_MAX = 40;

    //声纹注册、验证时的声音质量阈值
    public static final float ENROLL_ENERGY_THRESHOLD = 0.4f;
    public static final float ENROLL_SNR_THRESHOLD = 8.0f;
    public static final float VERIFY_ENERGY_THRESHOLD = 0.08f;
    public static final float VERIFY_SNR_THRESHOLD = 5.0f;

    //音量相关
    public static final double MIN_VOLUME_SHOW = 5.0;
    public static final double MAX_VOLUME_SHOW = 100.0;

    //声纹相关
    public static final String DATA_DIR = "voice_print";
    public static final String VPR_MODEL_NAME = "lexus-16k-mn-1.2.m";
    public static final String EXTRACTOR_CONFIG = "";
    public static final String VPR_MODEL_PATH =  "/model/";//改
    public static final int VPR_ENROLL_TIMES = 3;

    //唤醒相关
    public static final String KWS_MODEL_PATH = "/model/";//改
    public static final String ENROLL_MODEL = "enroll";
    public static final String MULTI_ENROLL_MODEL = "multi_enroll";
    public static final String VERIFY_MODEL = "verify";
    public static final String VERIFY_ONE_MODEL = "verifyone";

    public static final String PERMISSION_FILE_NAME="code";

    public static final String LOG_TXT_PATH = "/log/";//改
    public static final String LOG_TXT_NAME = "log.txt";



    public static  final String KWS_WORDS_KEY="KWS_WORDS";
    public static  final String KWS_THRESHOLD_KEY="KWS_THRESHOLD";

    public static  final String VPR_VPR_MODLE_KEY="VPR_VPR_MODLE_KEY";
    public static  final String VPR_SUIJI_THRESHOLD_KEY="VPR_SUIJI_THRESHOLD_KEY";
    public static  final String VPR_GUDING_THRESHOLD_KEY="VPR_GUDING_THRESHOLD_KEY";

    //vad相关
    public static final String VAD_MODEL_NAME = "susie-vad-16k-4.1.2.mdl";
    public static final String VAD_MODEL_PATH = "/model/";

    //asv相关
    public static final String ASV_MODEL_NAME = "asvspoof-attention_n_n_null-16k-10.3.4.mdl";
    public static final String ASV_MODEL_PATH = "/model/";
}
