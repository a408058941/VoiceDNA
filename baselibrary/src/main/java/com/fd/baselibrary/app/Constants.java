package com.fd.baselibrary.app;

/**
 * @author by DELL
 * @date on 2018/2/6
 * @describe
 */

public class Constants {

    public static final String RECEIVE = "com.voiceai.pubsub.receive"; //接收
    public static final String SEND  = "com.voiceai.pubsub.send";//发送
    public static final String ERROR  = "com.voiceai.pubsub.error";//错误
    public static final String SUCCESS  = "com.voiceai.pubsub.success";//成功



//     生产环境的服务器地址
    public static final String DEFAULT_HOST = "prod.bmwpay.saas.voiceaitech.com";
//     生产环境的消息频道
    public static final String DEFAULT_CHANNEL = "prod.bmw.pay.broadcast";


//    // 测试环境的服务器地址
//    public static final String DEFAULT_HOST = "test.bmwpay.saas.voiceaitech.com";
//    // 测试环境的消息频道
//    public static final String DEFAULT_CHANNEL = "test.bmw.pay.broadcast";

    public static final int DEFAULT_PORT = 33333; //端口
    public static final String DEFAULT_AUTH_TOKEN = "2c0dd8502c6e4292059df098e957c6c8745799b110ec48ef85b47a381b772dfc";//token
}
