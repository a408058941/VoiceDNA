package com.iva;

import android.content.Context;
import android.os.Handler;

import com.iva.VoiceQualityCheck.QualityCheckInfo;
import com.iva.VoiceQualityCheck.QualityCheckListener;
import com.iva.VoiceQualityCheck.QualityCheckOptions;
import com.iva.VoiceQualityCheck.QualityCheckParasInfo;
import com.iva.VoiceQualityCheck.VadConfig;
import com.iva.constant.CallBack;
import com.iva.constant.ErrorCode;
import com.iva.constant.IdVerifierHelper;
import com.iva.constant.InitListener;

/***
 * 质量检测
 */
public class QualityCheck {

    private VadConfig vadConfig;
    private int errorCode;
    private boolean isInit;

    private long mVADModelAddress = 0 ;
    private long mVADInstance = 0;

    public native long  vaiVadReadModel(String file_name);
    public native void vaiVadReleaseModel(long model_address);
    public native long vaiVadCreateInstance(long model_address, QualityCheckOptions opts);
    public native void vaiVadReleaseInstance(long instance_address);

    public native int vaiVadAcceptPcmData(long instance_address, QualityCheckParasInfo input_data,
                                          QualityCheckInfo output_data);

    // model 和instance绑定
    public native String vaiVadGetModelVersion(long instance_address);
    public native String vaiVadGetLibVersion(long instance_address);
    public native boolean VaiVADIsSampleRateSupported(long instance_address, int sample_rate);

    private Handler mHandler;


    static {
        System.loadLibrary("qualitycheck_jni");
    }

    public static QualityCheck getInstance(){
        return new QualityCheck();
    }


    private QualityCheck(){
        mHandler = new Handler();
    }



    public void audioVADCheck(final byte[] pcmData, int sampleRate, boolean isEndData,final QualityCheckListener qualityCheckListener) {
        if(!isInit){
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
                    qualityCheckListener.onVadResult(ErrorCode.VAD_INIT_ERROR,null,null);
//                }
//            });
            return ;
        }

        QualityCheckOptions qualityCheckOptions = new QualityCheckOptions();
        if( mVADInstance ==0) {
            mVADInstance = vaiVadCreateInstance(mVADModelAddress, qualityCheckOptions);
        }
        if(mVADInstance == 0){
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
                    qualityCheckListener.onVadResult(ErrorCode.VAD_CREATE_INSTANCE_ERROR,null,null);
//                }
//            });
            return ;
        }
        QualityCheckParasInfo qualityCheckParasInfo = new QualityCheckParasInfo();
        qualityCheckParasInfo.setParasDataBuffer(pcmData);
        qualityCheckParasInfo.setParasDataLength(pcmData.length);
        qualityCheckParasInfo.setParasChannels(1);
        qualityCheckParasInfo.setParasDataType(2);
        qualityCheckParasInfo.setParasSampleRate(sampleRate);
        qualityCheckParasInfo.setParasEndData(isEndData);
        qualityCheckParasInfo.setParasInterlace(false);

        final QualityCheckInfo qualityCheckInfo = new QualityCheckInfo();
       vaiVadAcceptPcmData(mVADInstance,qualityCheckParasInfo,qualityCheckInfo);
        if(isEndData) {
           vaiVadReleaseInstance(mVADInstance);
            mVADInstance = 0;
        }
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
                qualityCheckListener.onVadResult(ErrorCode.SUCCESS,qualityCheckInfo,pcmData);
//            }
//        });

    }


    public void initVad(VadConfig config, Context context, final InitListener listener) {
        vadConfig = config;

        new IdVerifierHelper(context, config.getFileName(), config.getFilePath(), new CallBack() {
            @Override
            public void onFail(int resultCode) {
                if (resultCode == ErrorCode.SUCCESS) {
                    isInit = true;
                    listener.onInit(ErrorCode.SUCCESS);

                } else {
                    switch (resultCode) {
                        case ErrorCode.FILE_COPY_PATH_ERROR:
                            errorCode = ErrorCode.VAD_CONFIG_ERROR;
                            break;
                        case ErrorCode.FILE_COPY_FAIL:
                            errorCode = ErrorCode.VAD_MODEL_LOAD_FAIL;
                            break;
                    }
                    listener.onInit(errorCode);

                }
            }

            @Override
            public int onDoAfterDone(boolean result, String targetPath) {
                return initModel(targetPath) == 0 ? ErrorCode.SUCCESS : ErrorCode.VAD_INIT_ERROR;
            }
        }).execute();

    }

    private int  initModel(String modelPath){

        mVADModelAddress = vaiVadReadModel(modelPath);
        if(mVADModelAddress ==0 ){
            return 1;
        }
        return 0;
    }

    public  int release(){
        if(!isInit){
            return 1;
        }
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
        vaiVadReleaseModel(mVADModelAddress);
        mVADModelAddress =0;
        return 0;
    }


}
