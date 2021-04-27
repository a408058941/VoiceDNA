package com.common.voicedna.utils;

import android.util.Log;

import com.common.voicedna.api.DnaConstant;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class DnaNetworkUtils {


    /**
     * HmacSHA256 签名
     *
     * @param params 请求参数集，所有参数必须已转换为字符串类型
     * @return 签名
     * @throws IOException
     */
    public static String getSignature(TreeMap<String, String> params) throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            if (EmptyUtil.isNotEmpty(basestring)) {
                basestring.append("&");
            }
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        String sign = null;
        try {
            sign = HMACSHA256(basestring.toString(), DnaConstant.APPSECRET);
             sign = sign.toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sign.toString();
    }


    /**
     * HmacSHA256 签名
     *
     * @param data 请求参数集，所有参数必须已转换为字符串类型
     * @param key  签名密钥
     * @return 签名
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Log.e("sin", "data====" + data);
        Log.e("sin", "key====" + key);
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");

        sha256_HMAC.init(secret_key);

        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();

        for (byte item : array) {

            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));

        }
        Log.e("sin", "sin====" + sb.toString().toUpperCase());
        return sb.toString().toUpperCase();
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static String getTimeMillis() {
        String request_time = (System.currentTimeMillis()) + "";
        return request_time;
    }
}
