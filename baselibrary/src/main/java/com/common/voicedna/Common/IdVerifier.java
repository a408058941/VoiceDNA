package com.common.voicedna.Common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.common.voicedna.Record.AudioRecorder;
import com.common.voicedna.Record.PcmToWav;
import com.common.voicedna.VoiceRecognize.VoiceRegisterConfig;
import com.common.voicedna.api.DnaConstant;
import com.common.voicedna.api.DnaPresenter;
import com.common.voicedna.bean.DnaErrorCode;
import com.common.voicedna.bean.FileBean;
import com.common.voicedna.bean.GroupBean;
import com.common.voicedna.bean.TokenBean;
import com.common.voicedna.bean.VoiceoperateDetailBean;
import com.common.voicedna.bean.VoiceprintTaskDetailBean;
import com.common.voicedna.data.AutoRegisData;
import com.common.voicedna.network.RxCallback;
import com.common.voicedna.utils.AppExecutors;
import com.common.voicedna.utils.DnaCallback;
import com.common.voicedna.utils.EmptyUtil;
import com.common.voicedna.utils.InstanceUtil;
import com.common.voicedna.utils.SPManager;
import com.common.voicedna.utils.SPUtil;
import com.common.voicedna.utils.UuidUtis;
import com.iva.AsvSpoofDetect;
import com.iva.QualityCheck;
import com.iva.VoiceAsvSpoofDetect.AsvSpoofDetectConfig;
import com.iva.VoiceAsvSpoofDetect.AsvSpoofDetectListener;
import com.iva.VoiceAsvSpoofDetect.AsvSpoofDetectOutputData;
import com.iva.VoiceQualityCheck.QualityCheckInfo;
import com.iva.VoiceQualityCheck.QualityCheckListener;
import com.iva.VoiceQualityCheck.VadConfig;
import com.iva.constant.ErrorCode;
import com.iva.constant.InitListener;
import com.voiceai.voicedna.bean.dto.DynamicNumber;
import com.voiceai.voicedna.bean.dto.DynamicNumberIdentityTask;
import com.voiceai.voicedna.bean.dto.DynamicNumberRegisterTask;
import com.voiceai.voicedna.bean.dto.DynamicNumerIdnTaskResult;
import com.voiceai.voicedna.bean.dto.DynamicNumerRegisTaskFileResult;
import com.voiceai.voicedna.bean.dto.DynamicNumerRegisTaskResult;
import com.voiceai.voicedna.bean.dto.IdnTaskDetail;
import com.voiceai.voicedna.bean.dto.RegisTaskDetail;
import com.voiceai.voicedna.bean.dto.VOTask;
import com.voiceai.voicedna.bean.dto.VPTask;
import com.voiceai.voicedna.bean.dto.VoicePrintGroup;
import com.voiceai.voicedna.bean.res.dynamicnumber.DynamicNumberRegisTaskCreateRes;
import com.voiceai.voicedna.bean.res.dynamicnumber.DynamicNumberRegisTaskExecRes;
import com.voiceai.voicedna.bean.res.dynamicnumber.DynamicNumberRegisTaskSubmitRes;
import com.voiceai.voicedna.bean.res.dynamicnumber.DynamicNumberRes;
import com.voiceai.voicedna.bean.res.file.UploadFileRes;
import com.voiceai.voicedna.bean.res.other.DNIdentityRes;
import com.voiceai.voicedna.bean.res.other.IdentifyRes;
import com.voiceai.voicedna.bean.res.other.RegisterRes;
import com.voiceai.voicedna.bean.res.voiceprint.VoicePrintGroupCreateRes;
import com.voiceai.voicedna.bean.res.voiceprint.VoicePrintGroupGetRes;
import com.voiceai.voicedna.client.DefaultVoiceDnaClient;
import com.voiceai.voicedna.client.VoiceDnaClient;
import com.voiceai.voicedna.client.VoiceDnaConfiguration;
import com.voiceai.voicedna.constant.FilterType;
import com.voiceai.voicedna.constant.IdnType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static android.content.ContentValues.TAG;
import static com.common.voicedna.api.DnaHostUrl.HOST_URL;
import static com.common.voicedna.api.DnaHostUrl.fileServer;

public class IdVerifier {
    private static IdVerifier instance;
    private static Object object = new Object();
    private final static String TAG = "Dispatcher";
    private AsvSpoofDetect mAsvSpoofDetect;
    private QualityCheck mQualityCheck;
    private AsvSpoofDetectConfig asvSpoofDetectConfig;
    private VerifyListener verifyListener;
    DefaultVoiceDnaClient client;
    float Asv = 0f;

    public static IdVerifier getInstance() {
        if (instance == null) {
            synchronized (object) {
                if (instance == null) {
                    instance = InstanceUtil.getInstance(IdVerifier.class);
                }
            }
        }
        return instance;
    }


    public void setVerifyListener(VerifyListener verifyListener) {
        this.verifyListener = verifyListener;
    }

    /**
     * 初始化
     *
     * @param context
     * @param app_id
     * @param appsecret
     * @param callback
     */
    public void init(Context context, String app_id, String appsecret, DnaCallback<Integer> callback) {
        SPUtil.getSP(context);
        mAsvSpoofDetect = AsvSpoofDetect.getInstance();
        mQualityCheck = QualityCheck.getInstance();
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    VoiceDnaConfiguration voiceDnaConfiguration = new VoiceDnaConfiguration(app_id, appsecret, HOST_URL, fileServer);
                    client = new DefaultVoiceDnaClient(voiceDnaConfiguration);
                    initAsvSpoofDetect(context, callback);
                } catch (Exception e) {
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(-9999, e.getMessage());
                        }
                    });

                }

            }
        });
    }

    /**
     * 创建分组
     *
     * @param name     分组名
     * @param callback
     */
    public void GreateGroup(String name, DnaCallback<String> callback) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                if (client == null)
                    return;
                VoicePrintGroupCreateRes groupId = client.createVoicePrintGroup(name);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (groupId.getCode() == 0) {
                            callback.onSuccess(groupId.getData().getId());
                        } else {
                            callback.onError(groupId.getCode(), groupId.getMsg());
                        }

                    }
                });

            }
        });
    }

    /**
     * 获取分组
     *
     * @param callback
     */
    public void getGroup(DnaCallback<List<VoicePrintGroup>> callback) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                if (client == null)
                    return;
                VoicePrintGroupGetRes res = client.getVoicePrintGroup();
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (res.getCode() == 0) {
                            callback.onSuccess(res.getData());
                        } else {
                            callback.onError(res.getCode(), res.getMsg());
                        }
                    }
                });

            }
        });
    }


    /**
     * 注册
     *
     * @param groupName 目标声纹分组ID
     * @param file      文件
     */
    public void register(String groupName, File file, DnaCallback<RegisTaskDetail> callback) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    int var1 = RegisterVADASV(PcmToWav.toByteArray(file));
                    if (var1 != 0) {
                        return;
                    }
                } catch (Exception e) {
                    var1 = 400001;
                    callback.onError(var1, "质检活检异常");
                    return;
                }
                List<File> files = new ArrayList<>();
                files.add(file);
                VPTask vpTask = VPTask.builder()
                        .groupName(groupName)
                        .filterType(FilterType.NoFilter)
                        .diaSwitch(false)
                        .vadSwitch(false)
                        .files(files)
                        .build();
                RegisterRes res = client.register(vpTask);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {

                        if (res.getCode() == 0) {
                            if (res.getData() != null && res.getData().size() > 0) {
                                callback.onSuccess(res.getData().get(0));
                            }

                        } else {
                            callback.onError(res.getCode(), res.getMsg());
                        }
                    }
                });
            }
        });


    }


    /**
     * 验证声纹
     *
     * @param groupName  目标声纹名称
     * @param file       文件
     * @param targetUser 1:1比对用户不能为空 1:N 比对用户必须为空
     * @param autoRegis  自动入库条件,不传条件代表不开启
     */
    public void Voiceoperate(String groupName, File file, String targetUser, List<AutoRegisData> autoRegis, DnaCallback<IdnTaskDetail> callback) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    int var1 = VerificationVadasv(PcmToWav.toByteArray(file));
                    if (var1 != 0) {
                        return;
                    }
                } catch (Exception e) {
                    var1 = 400001;
                    callback.onError(var1, "质检活检异常");
                    return;
                }
                List<File> files = new ArrayList<>();
                files.add(file);
                VOTask voTask;

                if (EmptyUtil.isEmpty(targetUser)) {
                    voTask = VOTask.builder()
                            .groupName(groupName)
                            .filterType(FilterType.NoFilter)
                            .idnType(IdnType.N)
                            .diaSwitch(false)
                            .vadSwitch(false)
                            .files(files)
                            .build();
                    Asv = VoiceRegisterConfig.getVerificationAsv();
                } else {
                    voTask = VOTask.builder()
                            .groupName(groupName)
                            .filterType(FilterType.NoFilter)
                            .idnType(IdnType.ONE)
                            .diaSwitch(false)
                            .vadSwitch(false)
                            .targetUser(targetUser)
                            .files(files)
                            .build();
                    Asv = VoiceRegisterConfig.getVerificationAsv1N();
                }
                IdentifyRes res = client.identify(voTask);

                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (res.getCode() == 0) {
                            if (res.getData() != null) {
                                List<IdnTaskDetail.ExecResultItem> taskResults = new ArrayList<>();
                                if (res.getData() != null && res.getData().size() > 0) {
                                    for (IdnTaskDetail.ExecResultItem item : res.getData().get(0).getTaskResults().get(0).getExecResults()) {
                                        if (Double.valueOf(item.getScore()) >= Asv) {
                                            taskResults.add(item);
                                        }
                                    }
                                }

                                res.getData().get(0).getTaskResults().get(0).setExecResults(taskResults);
                                callback.onSuccess(res.getData().get(0));
                            }
                        } else {
                            callback.onError(res.getCode(), res.getMsg());

                        }


                    }
                });
            }
        });
    }


    /**
     * 生成随机数
     *
     * @param count  生成1-5条随机数,默认1条
     * @param useFor 用途:0注册 1验证
     */
    public void getDynamicNumber(int count, int useFor, DnaCallback<List<DynamicNumber>> callback) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                if (client == null)
                    return;
                DynamicNumberRes res = client.getDynamicNumber(count, useFor);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (res.getCode() == 0) {
                            callback.onSuccess(res.getData().getNumbers());
                        } else {
                            callback.onError(res.getCode(), res.getMsg());
                        }


                    }
                });

            }
        });
    }


    /**
     * 创建注册任务
     *
     * @param groupId
     * @param tagName
     * @param callback
     */
    public void createDynamicNumberRegisTask(String groupId, String tagName, DnaCallback<String> callback) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                DynamicNumberRegisTaskCreateRes res = client.createDynamicNumberRegisTask(groupId, tagName,
                        true, 3, true, true, true, 0.75f, 0.5f, 0.5f);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (res.getCode() == 0) {
                            callback.onSuccess(res.getData().getTaskId());
                        } else {
                            callback.onError(res.getCode(), res.getMsg());
                        }
                    }
                });
            }
        });
    }

    /**
     * 提交单音频（注册任务）
     *
     * @param taskId   任务ID
     * @param file     文件
     * @param numberId 随机数ID
     */
    public void submitDynamicNumberRegisTask(String taskId, File file, String numberId, DnaCallback<DynamicNumerRegisTaskFileResult> callback) {

        try {
            AppExecutors.getInstance().networkIO().execute(new Runnable() {
                @Override
                public void run() {
                    List<File> files = new ArrayList<>();
                    files.add(file);
                    Map<File, UploadFileRes> map = client.uploadFile("0", files);
                    List<UploadFileRes> list = new ArrayList<>(map.values());
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (list.get(0).getCode() != 0) {
                                callback.onError(list.get(0).getCode(), list.get(0).getMsg());
                                return;
                            }
                        }
                    });
                    DynamicNumberRegisTaskSubmitRes res = client.submitDynamicNumberRegisTask(taskId, list.get(0).getData().getFileId(), numberId);
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (res.getCode()==0){
                                if (res.getData().getStatus() == 0) {
                                    callback.onSuccess(res.getData());
                                } else {
                                    callback.onError(res.getData().getStatus(), res.getData().getDescription());
                                }
                            }else {
                                callback.onError(res.getCode(), res.getMsg());
                            }

                        }
                    });
                }
            });
        }catch ( Exception e){
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    callback.onError(-9999, "任务超时");
                }
            });
        }


    }

    /**
     * 动态数字注册
     *
     * @param taskId   任务id
     * @param callback 回调
     */
    public void createDynamicNumberRegisTask(String taskId, DnaCallback<DynamicNumerRegisTaskResult> callback) {
        try {
            AppExecutors.getInstance().networkIO().execute(new Runnable() {
                @Override
                public void run() {
                    DynamicNumberRegisTaskExecRes res = client.execDynamicNumberRegisTask(taskId);
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (res.getCode()==0){
                                if (res.getData().getStatus() == 0) {
                                    callback.onSuccess(res.getData());
                                } else {
                                    callback.onError(res.getData().getStatus(), res.getData().getDescription());
                                }
                            }else {
                                callback.onError(res.getCode(), res.getMsg());
                            }

                        }
                    });
                }
            });
        } catch (Exception e) {
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    callback.onError(-9999, "任务超时");
                }
            });
        }
    }

    /**
     * 动态数字声纹比对
     *
     * @param numberId   随机数ID
     * @param file       文件
     * @param groupName  分组名称
     * @param targetUser 昵称
     * @param callback   回调
     */
    public void dynamicNumberIdentity(String numberId, File file, String groupName, String targetUser, DnaCallback<DynamicNumerIdnTaskResult> callback) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                List<File> files = new ArrayList<>();
                files.add(file);
                Map<File, UploadFileRes> map = client.uploadFile("1", files);
                List<UploadFileRes> list = new ArrayList<>(map.values());
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (list.get(0).getCode() != 0) {
                            callback.onError(list.get(0).getCode(), list.get(0).getMsg());
                            return;
                        }
                    }
                });
                DynamicNumberIdentityTask identityTask;
                if (EmptyUtil.isEmpty(targetUser)) {
                    identityTask = DynamicNumberIdentityTask.builder()
                            .numberId(numberId)
                            .fileId(list.get(0).getData().getFileId())
                            .groupName(groupName)
                            .vadSwitch(true)
                            .vadLevel(3)
                            .asvSwitch(true)
                            .asrSwitch(true)
                            .asrThreshold(0.75f)
                            .build();
                    Asv = VoiceRegisterConfig.getVerificationAsv();
                } else {
                    identityTask = DynamicNumberIdentityTask.builder()
                            .numberId(numberId)
                            .fileId(list.get(0).getData().getFileId())
                            .groupName(groupName)
                            .targetUser(targetUser)
                            .vadSwitch(true)
                            .vadLevel(3)
                            .asvSwitch(true)
                            .asrThreshold(0.75f)
                            .build();
                    Asv = VoiceRegisterConfig.getVerificationAsv1N();
                }
                DNIdentityRes res = client.dynamicNumberIdentity(identityTask);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (res.getData().getStatus() == 0) {
                            List<DynamicNumerIdnTaskResult.ExecResultItem> items = new ArrayList<>();
                            if (res.getData().getExecResults() != null) {
                                for (int i = 0; i < res.getData().getExecResults().size(); i++) {
                                    if (Double.valueOf(res.getData().getExecResults().get(i).getScore()) >= Asv) {
                                        items.add(res.getData().getExecResults().get(i));
                                    }
                                }
                            }
                            res.getData().setExecResults(items);
                            callback.onSuccess(res.getData());
                        } else {
                            callback.onError(res.getData().getStatus(), res.getData().getDescription());
                        }
                    }
                });


            }
        });
    }


    /**
     * 初始化活检
     */
    private void initAsvSpoofDetect(Context context, DnaCallback<Integer> callback) {
        if (asvSpoofDetectConfig == null) {
            asvSpoofDetectConfig = new AsvSpoofDetectConfig(context);
        }
        mAsvSpoofDetect.initAsvSpoofDetect(asvSpoofDetectConfig, context, new InitListener() {
            @Override
            public void onInit(int errorCode) {
                if (errorCode == ErrorCode.SUCCESS) {
                    initVad(context, callback);
                } else {
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(errorCode, "活检初始化失败");
                        }
                    });

                }

            }
        });
    }

    /**
     * 初始化质检
     */
    private void initVad(Context context, DnaCallback<Integer> callback) {
        mQualityCheck.initVad(new VadConfig(), context, new InitListener() {
            @Override
            public void onInit(int errorCode) {
                if (errorCode == ErrorCode.SUCCESS) {
                    Log.e(TAG, "Voiceai_Init VAD 初始化成功");

                    callback.onSuccess(0);
                } else {
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(errorCode, "质检初始化失败");
                        }
                    });
                }
            }
        });
    }

    private int var1 = 0;
    private String msg;
    private QualityCheckInfo info;
    private AsvSpoofDetectOutputData asvSpoofDetectOutput;

    /**
     * 注册质检活检
     *
     * @param bytes
     * @return
     */
    public int RegisterVADASV(byte[] bytes) {
        var1 = 0;
        msg = "";
        info = null;
        asvSpoofDetectOutput = null;
        if (VoiceRegisterConfig.getRegisterVadSwicth()) {
            mQualityCheck.audioVADCheck(bytes, 16000, true, new QualityCheckListener() {
                @Override
                public void onVadResult(int error, QualityCheckInfo qualityCheckInfo, byte[] data) {
                    info = qualityCheckInfo;
                    if (qualityCheckInfo.getSpeechDuration() < VoiceRegisterConfig.getRegisterSpeechDuration()) {
                        msg = "有效时长--有效时长过短";
                        var1 = 400002;
                    } else if (qualityCheckInfo.getSpeechEnergy() < VoiceRegisterConfig.getRegisterSpeechenergy()) {
                        var1 = 400003;
                        msg = "平均能量--音量过小";
                    } else if (qualityCheckInfo.getEstSnr() < VoiceRegisterConfig.getRegisterVadSNR()) {
                        msg = "信噪比--环境噪音大";
                        var1 = 400004;
                    } else if (qualityCheckInfo.getClippingRatio() > VoiceRegisterConfig.getVerificationVadclippingratio()) {
                        msg = "截幅比--音量过大";
                        var1 = 400005;
                    }
                }
            });
        }
        if (VoiceRegisterConfig.getRegisterVoiceAsv()) {
            mAsvSpoofDetect.pcmAudioASVCheck(bytes, new AsvSpoofDetectListener() {
                @Override
                public void onAsvResult(int error, AsvSpoofDetectOutputData asvSpoofDetectOutputData, byte[] data) {
                    asvSpoofDetectOutput = asvSpoofDetectOutputData;
                    if (VoiceRegisterConfig.getRegisterSpoofscore() > asvSpoofDetectOutputData.getAsvspoof_score()) {
                        if (var1 == 0) {
                            msg = "  活体--活体检测不通过";
                            var1 = 400006;
                        }
                    }

                }
            });
        }
        if (verifyListener != null) {
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    verifyListener.onAudioDetection(var1, info, asvSpoofDetectOutput, msg);
                }
            });
        }
        return var1;

    }


    /**
     * 验证质检活检
     *
     * @param bytes
     * @return
     */
    public int VerificationVadasv(byte[] bytes) {
        var1 = 0;
        msg = "";
        info = null;
        asvSpoofDetectOutput = null;
        if (VoiceRegisterConfig.getVerificationVadswicth()) {
            mQualityCheck.audioVADCheck(bytes, 16000, true, new QualityCheckListener() {
                @Override
                public void onVadResult(int error, QualityCheckInfo qualityCheckInfo, byte[] data) {
                    info = qualityCheckInfo;
                    if (VoiceRegisterConfig.getVerificationSpeechduration() > qualityCheckInfo.getSpeechDuration()) {
                        msg = "有效时长--有效时长过短";
                        var1 = 400002;
                    } else if (qualityCheckInfo.getSpeechEnergy() < VoiceRegisterConfig.getVerificationVadsnr()) {
                        var1 = 400003;
                        msg = "平均能量--音量过小";
                    } else if (qualityCheckInfo.getEstSnr() < VoiceRegisterConfig.getVerificationSpeechEnergy()) {
                        msg = "信噪比--环境噪音大";
                        var1 = 400004;
                    } else if (qualityCheckInfo.getClippingRatio() > VoiceRegisterConfig.getVerificationVadclippingratio()) {
                        msg = "截幅比--音量过大";
                        var1 = 400005;
                    }
                }
            });
        }
        if (VoiceRegisterConfig.getVerificationVoiceAsv()) {
            mAsvSpoofDetect.pcmAudioASVCheck(bytes, new AsvSpoofDetectListener() {
                @Override
                public void onAsvResult(int error, AsvSpoofDetectOutputData asvSpoofDetectOutputData, byte[] data) {
                    asvSpoofDetectOutput = asvSpoofDetectOutputData;
                    if (VoiceRegisterConfig.getVerificationSpoofscore() > asvSpoofDetectOutputData.getAsvspoof_score()) {
                        if (var1 == 0) {
                            msg = "活体--活体检测不通过";
                            var1 = 400006;
                        }
                    }

                }
            });
        }
        if (verifyListener != null) {
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    verifyListener.onAudioDetection(var1, info, asvSpoofDetectOutput, msg);
                }
            });
        }

        return var1;

    }

    /**
     * 释放资源
     */
    public void release() {
        AudioRecorder.getInstance().release();
        mAsvSpoofDetect.release();
        mQualityCheck.release();
    }
}
