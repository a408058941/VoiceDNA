package com.common.voicedna.Record;

import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Environment;

public class Constants {

    //釆波率  8000, 11025, 16000, 22050, 32000,44100, 47250, 48000
    public static final int SAMPLE_RATE_IN_HZ = 16000;
    public static final int AUDIOROUCE = MediaRecorder.AudioSource.MIC;
    public static final int CHANNEL_IN = AudioFormat.CHANNEL_IN_MONO;
    public static final int ENCODING_PCM = AudioFormat.ENCODING_PCM_16BIT;
    public static final int DATA_QUEUE_MAX = 60;

    public final static String ROOT_PATH = Environment.getExternalStorageDirectory() + "/";
    public static String SDK_ROOT_DIR=ROOT_PATH+"VoiceKWS";

}
