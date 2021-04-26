package com.common.voicedna.api;


import android.util.Log;

import androidx.annotation.Nullable;

import com.common.voicedna.VoiceRecognize.VoiceRegisterConfig;
import com.common.voicedna.bean.GroupBean;
import com.common.voicedna.bean.RefreshTokenBean;
import com.common.voicedna.bean.TokenBean;
import com.common.voicedna.bean.VoiceoperateDetailBean;
import com.common.voicedna.bean.VoiceoperateTaskBean;
import com.common.voicedna.bean.VoiceprintTaskBean;
import com.common.voicedna.bean.VoiceprintTaskDetailBean;
import com.common.voicedna.data.AutoRegisData;
import com.common.voicedna.data.VoiceoperateTask;
import com.common.voicedna.data.VoiceprintTaskData;
import com.common.voicedna.utils.AppExecutors;
import com.common.voicedna.utils.DnaCallback;
import com.common.voicedna.utils.DnaNetworkUtils;
import com.common.voicedna.utils.NetworkUpload;
import com.fd.baselibrary.base.BasePresenter;
import com.fd.baselibrary.network.NetworkTransformer;
import com.fd.baselibrary.network.RxCallback;
import com.fd.baselibrary.utils.InstanceUtil;
import com.fd.baselibrary.utils.SPManager;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;
import cn.finalteam.okhttpfinal.RequestParams;
import io.reactivex.Observable;

import static com.common.voicedna.api.DnaConstant.APPSECRET;

public class DnaPresenter extends BasePresenter {
    private static DnaPresenter instance;
    private static Object object = new Object();

    public static DnaPresenter getInstance() {
        if (instance == null) {
            synchronized (object) {
                if (instance == null) {
                    instance = InstanceUtil.getInstance(DnaPresenter.class);
                }
            }
        }
        return instance;
    }

    public void init(String APP_ID,String APPSECRET){
        DnaConstant.APP_ID=APP_ID;
        DnaConstant. APPSECRET=APPSECRET;
        //初始化OkHttp
        OkHttpFinalConfiguration.Builder   builder = new OkHttpFinalConfiguration.Builder();
        builder.setTimeout(20000);
        OkHttpFinal.getInstance().init(builder.build());

    }

//
//    /**
//     * 获取TOKEN
//     *
//     * @param algorithm 签名算法,目前仅支持HMACSHA256
//     * @param appId     APPID
//     * @param nonce     6位随机自然数
//     * @param signature 签名摘要
//     * @param timestamp 当前时间,13位时间戳
//     * @return
//     */
    public Observable<TokenBean> getToken() {
        int nonce = (int) ((Math.random() * 9 + 1) * 100000);
        String timestamp = DnaNetworkUtils.getTimeMillis();
        String algorithm = "HMACSHA256";
        TreeMap map = new TreeMap();
        map.put("nonce", nonce + "");
        map.put("timestamp", timestamp);
        map.put("algorithm", algorithm);
        map.put("appId", DnaConstant.APP_ID);
        String signature = null;
        try {
            signature = DnaNetworkUtils.getSignature(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DnaRetrofitClientapi.getTestService()
                .getToken(algorithm, DnaConstant.APP_ID, nonce, signature, timestamp)
                .compose(new NetworkTransformer<>(this));

    }

    /**
     * 刷新TOKEN
     *
     * @return
     */
    public Observable<RefreshTokenBean> refresh_token() {
        return DnaRetrofitClientapi.getTestService()
                .refresh_token()
                .compose(new NetworkTransformer<>(this));

    }

    /**
     * 创建声纹分组
     *
     * @param groupName 分组名
     * @return
     */
    public Observable<String> group_create(String groupName) {
        return DnaRetrofitClientapi.getTestService()
                .group_create(DnaHostUrl.GROUP_CREATE + "?groupName=" + groupName)
                .compose(new NetworkTransformer<>(this));

    }

    /**
     * 查看声纹分组
     *
     * @return
     */
    public Observable<List<GroupBean>> getGroup() {
        return DnaRetrofitClientapi.getTestService()
                .getGroup()
                .compose(new NetworkTransformer<>(this));

    }

    /**
     * 创建注册入库任务
     *
     * @param groupId 目标声纹分组ID
     * @param fileIds 文件ID数组
     * @return
     */
    public Observable<VoiceprintTaskBean> VoiceprintTask(String groupId, List<String> fileIds) {
        VoiceprintTaskData bean = new VoiceprintTaskData(groupId, fileIds, VoiceRegisterConfig.getUserVadSwicth(), VoiceRegisterConfig.getUserDiaSwitch());
        if (bean.isVadSwicth()) {
            bean.setVadLevel(VoiceRegisterConfig.getUserVadLevel());
        }
        if (bean.isDiaSwitch()) {
            bean.setFilterType(VoiceRegisterConfig.getUserFiltertype());
            bean.setVoiceFilter(VoiceRegisterConfig.getUserVoiceFilter());
        }
        return DnaRetrofitClientapi.getTestService()
                .VoiceprintTask(new Gson().toJson(bean))
                .compose(new NetworkTransformer<>(this));

    }

    /**
     * 查看注册入库结果
     *
     * @param taskId 任务ID
     * @return
     */
    public Observable<VoiceprintTaskDetailBean> VoiceprintTaskDetail(String taskId) {
        return DnaRetrofitClientapi.getTestService()
                .VoiceprintTaskDetail(DnaHostUrl.VOICEPRINT_TASK_DETAIL + taskId)
                .compose(new NetworkTransformer<>(this));

    }


    /**
     * 创建比对任务
     *
     * @param groupId    目标声纹分组ID
     * @param fileIds    文件ID数组
     * @param targetType 比对类型 1：声纹1:1验证 0：声纹1：N检索
     * @param targetUser 1:1比对用户不能为空 1:N 比对用户必须为空     取比对用户的tagName
     * @param autoRegis  自动入库条件,不传条件代表不开启
     * @return
     */
    public Observable<VoiceoperateTaskBean> VoiceoperateTask(String groupId, List<String> fileIds, int targetType, String targetUser, List<AutoRegisData> autoRegis) {
        VoiceoperateTask bean = new VoiceoperateTask(groupId, fileIds, targetType, targetUser, VoiceRegisterConfig.getVerification_Vadswicth(),
                VoiceRegisterConfig.getVerificationDiaSwitch(), autoRegis);
        if (VoiceRegisterConfig.getVerification_Vadswicth()) {
            bean.setVadLevel(VoiceRegisterConfig.getVerificationVadLevel());
        }
        if (VoiceRegisterConfig.getVerificationDiaSwitch()) {
            bean.setFilterType(VoiceRegisterConfig.getVerificationFiltertype());
            bean.setVoiceFilter(VoiceRegisterConfig.getVerificationVoiceFilter());
        }
        String json=new Gson().toJson(bean);
        return DnaRetrofitClientapi.getTestService()
                .VoiceoperateTask(json)
                .compose(new NetworkTransformer<>(this));

    }
    /**
     * 查看注册入库结果
     *
     * @param taskId 任务ID
     * @return
     */
    public Observable<VoiceoperateDetailBean> VoiceoperateDetail(String taskId) {
        return DnaRetrofitClientapi.getTestService()
                .VoiceoperateDetail(DnaHostUrl.VOICEOPERATE_DETAIL + taskId)
                .compose(new NetworkTransformer<>(this));

    }

    /**
     * 上传图片
     *
     * @param file 音频文件
     * @param type 音频用途 0注册,1验证比对
     * @param id   批量会话ID,需生成UUID格式（具体作用看接口描述）,格式为:xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
     */
    public void Uploadbycode(File file, int type, String id, DnaCallback dnaCallback) {
        RequestParams params = new RequestParams();
        List<File> files = new ArrayList<>();
        files.add(file);
        params.addFormDataPartFiles("file", files);
        params.addHeader(SPManager.getUserTokenKey(), SPManager.getUserToken());
        params.addHeader("x-use-for", type);
        params.addHeader("x-batch-id", id);
        NetworkUpload.uploadbycode(params, DnaHostUrl.UPLOAD, dnaCallback);
    }

    /**
     * 注册
     *
     * @param groupId 目标声纹分组ID
     * @param fileIds 文件ID数组
     */
    private ScheduledFuture<?> scheduledFuture;
    private int i = 0;
    private String taskId;

    public void register(String groupId, String fileIds, DnaCallback<VoiceprintTaskDetailBean> callback) {
        List<String> list = new ArrayList<>();
        list.add(fileIds);
        DnaPresenter.getInstance().VoiceprintTask(groupId, list).safeSubscribe(new RxCallback<VoiceprintTaskBean>() {
            @Override
            public void onSuccess(@Nullable VoiceprintTaskBean data) {
                i = 1;
                // 方法 二 ：
                taskId = "";
                Iterator it = data.getIdMapX().keySet().iterator();  // Set类型的key值集合，并转换为迭代器
                while (it.hasNext()) {
                    String key = (String) it.next();   // 获取 key 值，ClassRoom
                    taskId = data.getIdMapX().get(key);
                    // 获取value 值，Collection (Student)
                    System.out.println(taskId);

                }
                scheduledFuture = AppExecutors.getInstance().scheduledExecutor().scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        i++;
                        DnaPresenter.getInstance().VoiceprintTaskDetail(taskId).safeSubscribe(new RxCallback<VoiceprintTaskDetailBean>() {
                            @Override
                            public void onSuccess(@Nullable VoiceprintTaskDetailBean data) {
                                if (data.getStatus() == 0) {
                                    scheduledFuture.cancel(true);
                                    callback.onSuccess(data);
                                } else {
                                    if (i == 10) {
                                        scheduledFuture.cancel(true);
                                        callback.onSuccess(data);
                                    }
                                }

                            }

                            @Override
                            public void onError(Throwable t) {
                                super.onError(t);
                                callback.onError(t.toString());
                            }
                        });
                        try {
                            Thread.sleep(3000);// 10~90s
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }, 2, 3, TimeUnit.SECONDS);

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                callback.onError(t.toString());
            }
        });
    }

    /**
     * 验证声纹
     *
     * @param groupId    目标声纹分组ID
     * @param fileId     文件ID
     * @param targetType 比对类型 1：声纹1:1验证 0：声纹1：N检索
     * @param targetUser 1:1比对用户不能为空 1:N 比对用户必须为空     取比对用户的tagName
     * @param autoRegis  自动入库条件,不传条件代表不开启
     */
    public void Voiceoperate(String groupId, String fileId, int targetType, String targetUser, List<AutoRegisData> autoRegis,DnaCallback<VoiceoperateDetailBean> callback) {
        List<String> fileIds=new ArrayList<>();
        fileIds.add(fileId);
        DnaPresenter.getInstance().VoiceoperateTask(groupId, fileIds, targetType, targetUser, autoRegis).safeSubscribe(new RxCallback<VoiceoperateTaskBean>() {
            @Override
            public void onSuccess(@Nullable VoiceoperateTaskBean data) {
                i = 1;
                // 方法 二 ：
                taskId = "";
                Iterator it = data.getIdMap().keySet().iterator();  // Set类型的key值集合，并转换为迭代器
                while (it.hasNext()) {
                    String key = (String) it.next();   // 获取 key 值，ClassRoom
                    taskId = data.getIdMap().get(key);
                    // 获取value 值，Collection (Student)
                    System.out.println(taskId);

                }
                scheduledFuture = AppExecutors.getInstance().scheduledExecutor().scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        i++;
                        DnaPresenter.getInstance().VoiceoperateDetail(taskId).safeSubscribe(new RxCallback<VoiceoperateDetailBean>() {
                            @Override
                            public void onSuccess(@Nullable VoiceoperateDetailBean data) {
                                Log.e("11111",data.getTaskResults().get(0).getDescription());
                                if (data.getTaskResults().get(0).getFlag() != null) {
                                    scheduledFuture.cancel(true);
                                    callback.onSuccess(data);

                                } else {
                                    if (i == 10) {
                                        scheduledFuture.cancel(true);
                                        callback.onSuccess(data);
//
                                    }
                                }

                            }

                            @Override
                            public void onError(Throwable t) {
                                super.onError(t);
                                callback.onError(t.toString());
                            }
                        });
                        try {
                            Thread.sleep(3000);// 10~90s
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, 2, 3, TimeUnit.SECONDS);


            }


            @Override
            public void onError(Throwable t) {
                super.onError(t);
                callback.onError(t.toString());
            }
        });

    }

    public  void  release(){
        if (scheduledFuture!=null){
            scheduledFuture.cancel(true);
        }
    }
}
