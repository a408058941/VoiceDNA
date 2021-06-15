package com.common.voicedna.api;


import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.common.voicedna.Record.PcmToWav;
import com.common.voicedna.VoiceRecognize.VoiceRegisterConfig;
import com.common.voicedna.base.BasePresenter;
import com.common.voicedna.bean.FileBean;
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
import com.common.voicedna.network.BaseBean;
import com.common.voicedna.network.NetworkTransformer;
import com.common.voicedna.network.RxCallback;
import com.common.voicedna.utils.AppExecutors;
import com.common.voicedna.utils.DnaCallback;
import com.common.voicedna.utils.DnaNetworkUtils;
import com.common.voicedna.utils.InstanceUtil;
import com.common.voicedna.utils.SPManager;
import com.common.voicedna.utils.SPUtil;
import com.common.voicedna.utils.UuidUtis;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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


    /**
     * 上传图片
     *
     * @param bytes 音频流
     * @param type  音频用途 0注册,1验证比对
     * @param id    批量会话ID,需生成UUID格式（具体作用看接口描述）,格式为:xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
     */
    public void Uploadbycode(String mUserName, byte[] bytes, int type, String id, DnaCallback dnaCallback) {
        long byteRate = 16 * 16000 * 1 / 8;
        byte[] wavHead = PcmToWav.getWavHeader(bytes.length, bytes.length + 36, 16000, 1, byteRate);
        byte[] bt3 = byteMerger(wavHead, bytes);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), bt3);
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", mUserName + ".wav", requestBody).build();
        Request request = new Request.Builder()
                .addHeader(SPManager.getUserTokenKey(), SPManager.getUserToken())
                .addHeader("x-use-for", type + "")
                .addHeader("x-batch-id", id)
                .url(DnaHostUrl.UPLOAD)
                .post(body)
                .build();
        RetrofitClient.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                dnaCallback.onError("上传失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BaseBean baseBean = new Gson().fromJson(response.body().string(), BaseBean.class);
                if (baseBean.code == 0) {
                    String json = new Gson().toJson(baseBean.getData());
                    FileBean fileBean = new Gson().fromJson(json, FileBean.class);
                    dnaCallback.onSuccess(fileBean);
                } else {
//                    dnaCallback.onError(baseBean.getMsg());
                }

            }
        });

    }

    //System.arraycopy()方法
    public static byte[] byteMerger(byte[] bt1, byte[] bt2) {
        byte[] bt3 = new byte[bt1.length + bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }

}
