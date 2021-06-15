package com.iva;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.iva.VoiceAsvSpoofDetect.AsvSpoofDetectConfig;
import com.iva.VoiceAsvSpoofDetect.AsvSpoofDetectInputData;
import com.iva.VoiceAsvSpoofDetect.AsvSpoofDetectListener;
import com.iva.VoiceAsvSpoofDetect.AsvSpoofDetectOptions;
import com.iva.VoiceAsvSpoofDetect.AsvSpoofDetectOutputData;
import com.iva.constant.CallBack;
import com.iva.constant.ErrorCode;
import com.iva.constant.IdVerifierHelper;
import com.iva.constant.InitListener;

public class AsvSpoofDetect {
    private static AsvSpoofDetect instance = null;
    private boolean isInit;
    private int errorCode;

    private long mASVModelAddress = 0;
    private long mASVInstance = 0;
    private Handler mHandler;

    static {
        System.loadLibrary("susie-asvspoof-jni");
    }

    public native long VaiAsvSpoofDetectReadModel(String file_name);

    public native void VaiAsvSpoofDetectReleaseModel(long model_addr);

    public native long VaiAsvSpoofDetectCreateInstance(long model_addr, AsvSpoofDetectOptions opts);

    public native int VaiAsvSpoofDetectDestroyInstance(long instance_addr);

    public native int VaiAsvSpoofDetectAcceptWave(long instance_addr, byte[] wave_data,
                                                  int data_len, AsvSpoofDetectOutputData result);

    public native int VaiAsvSpoofDetectAcceptPcm(long instance_addr,
                                                 AsvSpoofDetectInputData input_data,
                                                 AsvSpoofDetectOutputData output_data);

    public native String VaiAsvSpoofDetectGetModelVersion(long model_addr);

    public native String VaiAsvSpoofDetectGetLibVersion(long instance_addr);

    public native boolean VaiAsvSpoofDetectIsSampleRateSupported(long instance_addr, int sample_rate);


    public static AsvSpoofDetect getInstance() {
        if (instance == null) {
            synchronized (AsvSpoofDetect.class) {
                if (instance == null) {
                    instance = new AsvSpoofDetect();
                }
            }
        }
        return new AsvSpoofDetect();
    }

    private AsvSpoofDetect() {
        mHandler = new Handler(Looper.getMainLooper());
    }


    public void initAsvSpoofDetect(AsvSpoofDetectConfig asvSpoofDetectConfig, Context context, final InitListener listener) {

        new IdVerifierHelper(context, asvSpoofDetectConfig.getFileName(), asvSpoofDetectConfig.getFilePath(), new CallBack() {
            @Override
            public void onFail(int resultCode) {
                if (resultCode == ErrorCode.SUCCESS) {
                    isInit = true;
                    listener.onInit(ErrorCode.SUCCESS);

                } else {
                    switch (resultCode) {
                        case ErrorCode.FILE_COPY_PATH_ERROR:
                            errorCode = ErrorCode.ASV_CONFIG_ERROR;
                            break;
                        case ErrorCode.FILE_COPY_FAIL:
                            errorCode = ErrorCode.ASV_MODEL_LOAD_FAIL;
                            break;
                    }
                    listener.onInit(errorCode);

                }

            }

            @Override
            public int onDoAfterDone(boolean result, String targetPath) {
                return initASVModel(targetPath) == 0 ? ErrorCode.SUCCESS : ErrorCode.ASV_INIT_ERROR;
            }
        }).execute();

    }

    private int initASVModel(String modelPath) {
        AsvSpoofDetectOptions asvSpoofDetectOptions = new AsvSpoofDetectOptions();
        mASVModelAddress = this.VaiAsvSpoofDetectReadModel(modelPath);
        mASVInstance = this.VaiAsvSpoofDetectCreateInstance(mASVModelAddress, asvSpoofDetectOptions);
        if (mASVModelAddress == 0 || mASVInstance == 0) {
            return 1;
        }
        return 0;

    }

    public void pcmAudioASVCheck(final byte[] pcmData, final AsvSpoofDetectListener asvSpoofDetectListener) {

        if (!isInit) {
            asvSpoofDetectListener.onAsvResult(ErrorCode.ASV_INIT_ERROR, null, null);
            return;
        }
        AsvSpoofDetectInputData asvSpoofDetectInputData = new AsvSpoofDetectInputData();
        asvSpoofDetectInputData.data_buffer_b = pcmData;
        asvSpoofDetectInputData.data_len = pcmData.length;

        final AsvSpoofDetectOutputData asvSpoofDetectOutputData = new AsvSpoofDetectOutputData();
        final int ret = this.VaiAsvSpoofDetectAcceptPcm(mASVInstance, asvSpoofDetectInputData, asvSpoofDetectOutputData);
        asvSpoofDetectListener.onAsvResult(ret, asvSpoofDetectOutputData, pcmData);


    }

    public int release() {
        this.VaiAsvSpoofDetectDestroyInstance(mASVInstance);
        this.VaiAsvSpoofDetectReleaseModel(mASVModelAddress);
        mASVInstance = 0;
        mASVModelAddress = 0;
        return 1;
    }


}
