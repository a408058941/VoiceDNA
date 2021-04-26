package com.common.voicedna.utils;


import com.common.voicedna.bean.FileBean;
import com.fd.baselibrary.api.RetrofitClientJsonObject;
import com.fd.baselibrary.network.BaseBean;
import com.fd.baselibrary.utils.EmptyUtil;
import com.fd.baselibrary.utils.SPManager;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class NetworkUpload {
    private static final MediaType FROM_DATA = MediaType.parse("multipart/form-data");

    public static void uploadbycode(RequestParams params, String url, DnaCallback dnaCallback) {
        HttpRequest.post(url, params, new BaseHttpRequestCallback() {

            @Override
            public void onStart() {
                super.onStart();
//                Log.e("111111", "onStart");
            }

            @Override
            public void onResponse(String response, Headers headers) {
                super.onResponse(response, headers);
                try {
//                    Log.e("111111", response);
                    if (EmptyUtil.isEmpty(response)) {
                        dnaCallback.onError(null);
                        return;
                    }
                    BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                    String json = new Gson().toJson(baseBean.getData());
                    FileBean fileBean = new Gson().fromJson(json, FileBean.class);
                    dnaCallback.onSuccess(fileBean);
                } catch (Exception e) {
                    dnaCallback.onError(e.toString());
                }
            }

            //请求失败（服务返回非法JSON、服务器异常、网络异常）
            @Override
            public void onFailure(int errorCode, String msg) {
                dnaCallback.onError(errorCode+"===="+msg);
//                Log.e("111111", "onFailure===="+"errorCode="+errorCode+"===msg"+msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
//                Log.e("111111", "onFinish");
            }
        });

    }

    public static String sendFromDataPostRequest(String url, File file, int type, String id) throws IOException {
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody body = new MultipartBody.Builder()
                .setType(FROM_DATA)
                .addFormDataPart("file", file.getName(), fileBody)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .addHeader(SPManager.getUserTokenKey(), SPManager.getUserToken())
                .addHeader("x-use-for", type + "")
                .addHeader("x-batch-id", id)
                .build();
        return RetrofitClientJsonObject.getOkHttpClient().newCall(request).execute().body().string();


    }
}
