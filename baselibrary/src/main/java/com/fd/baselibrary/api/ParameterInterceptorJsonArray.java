package com.fd.baselibrary.api;

import android.util.Log;

import com.fd.baselibrary.utils.L;
import com.fd.baselibrary.utils.SPManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;

/**
 * 参数格式为Json格式的拦截器
 */
public class ParameterInterceptorJsonArray implements Interceptor {
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
            //先获取到老的请求的实体内容
            RequestBody oldRequestBody = request.body();//....之前的请求参数,,,需要放到新的请求实体内容中去
            Log.e("响应头", request.headers().toString());
            //如果请求调用的是上面doPost方法
            if (oldRequestBody instanceof FormBody) {
                FormBody oldBody = (FormBody) oldRequestBody;
                //构建一个新的请求实体内容
                FormBody.Builder builder = new FormBody.Builder(Util.UTF_8);
                JSONArray json = null;
                //1.添加老的参数
                for (int i = 0; i < oldBody.size(); i++) {
                    builder.add(oldBody.name(i), oldBody.value(i));
                    try {
                        json=new JSONArray(oldBody.value(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //2.添加公共参数
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
                FormBody newBody = builder.build();//新的请求实体内容
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
                //构建一个新的请求
                request = request.newBuilder()
                        .url(oldUrl)
//                        .post(newBody)
                        .method(request.method(), body)
                        .addHeader("Accept", " application/json")//添加header
                        .addHeader("token", SPManager.getUserToken())//添加header
                        .build();
            }
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
}
