package com.fd.baselibrary.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.fd.baselibrary.BuildConfig;
import com.fd.baselibrary.utils.L;
import com.fd.baselibrary.utils.SPManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;

/**
 * 参数格式为Json格式的拦截器
 */
public class ParameterInterceptorform implements Interceptor {
    private static final String TAG = ParameterInterceptorJsonArray.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取到请求
        Request request = chain.request();
        //获取请求的方式
        String method = request.method();
        //获取请求的路径
        String oldUrl = request.url().toString();
        Log.e("---拦截器", request.url() + "---" + request.method() + "--" + request.header("User-agent"));
        Log.e("---拦截器", SPManager.getUserToken());
        //要添加的公共参数...map
        Map<String, String> map  = SPManager.getUserPublicMap();
        map.put("source", "android");
//        Log.e("token", SPManager.getLogin_token());
        if ("GET".equals(method)) {
            StringBuilder stringBuilder = new StringBuilder();//创建一个stringBuilder
            stringBuilder.append(oldUrl);
            if (oldUrl.contains("?")) {
                //?在最后面....2类型
                if (oldUrl.indexOf("?") == oldUrl.length() - 1) {
                } else {
                    //3类型...拼接上&
                    stringBuilder.append("&");
                }
            } else {
                //不包含? 属于1类型,,,先拼接上?号
                stringBuilder.append("?");
            }
            //添加公共参数....
            for (Map.Entry<String, String> entry : map.entrySet()) {
                //拼接
                stringBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }

            //删掉最后一个&符号
            if (stringBuilder.indexOf("&") != -1) {
                stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
            }
            String newUrl = stringBuilder.toString();//新的路径
            //拿着新的路径重新构建请求
            request = request.newBuilder()
                    .url(newUrl)
                    .addHeader("token", SPManager.getUserToken())//添加header
                    .build();

        } else if ("POST".equals(method)) {
            RequestBody requestBody = makeRequestBody(request);
            request = request.newBuilder()
                    .addHeader("token", SPManager.getUserToken())//添加header
                    .post(requestBody)
                    .build();
        }
        String token = SPManager.getUserToken();
        Log.e("token", token);
        Response response = chain.proceed(request);
        ResponseBody resultBody = response.body();
        String result = resultBody.string();
        try {
            L.json(result);
        } catch (Exception e) {
            L.e(result);
        }
        /*** 因为调用ResponseBody.string()后即关闭，后续无法获取内容 */
        response = response.newBuilder()
                .body(ResponseBody.create(resultBody.contentType(), result))
                .build();
        return response;
    }

    private RequestBody makeRequestBody(Request oldRequest) {
        TreeMap map = new TreeMap();
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        if (oldRequest.method().equals("POST")) {
            if (oldRequest.body() instanceof FormBody) {
                FormBody body = (FormBody) oldRequest.body();
                if (body != null) {
                    for (int i = body.size() - 1; i >= 0; i--) {
                        String name = body.name(i);
                        String value = body.value(i);
                        map.put(name, value);
                    }
                }
            } else if (oldRequest.body() instanceof MultipartBody) {
                /*** 当参数以 @MultipartBody 提交时 */
                L.d(TAG, "instanceof MultipartBody");
//
                multipartBodyBuilder.setType(MultipartBody.FORM);

                for (MultipartBody.Part part : ((MultipartBody) oldRequest.body()).parts()) {
                    multipartBodyBuilder.addPart(part);
                }

                return multipartBodyBuilder.build();
            }
        }
        /** * 添加Sign参数 */
        FormBody.Builder newBodyBuilder = new FormBody.Builder();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() instanceof String) {
                newBodyBuilder.add(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        return newBodyBuilder.build();
    }


}
