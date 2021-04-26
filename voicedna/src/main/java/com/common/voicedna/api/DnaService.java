package com.common.voicedna.api;

import com.common.voicedna.bean.GroupBean;
import com.common.voicedna.bean.RefreshTokenBean;
import com.common.voicedna.bean.TokenBean;
import com.common.voicedna.bean.VoiceoperateDetailBean;
import com.common.voicedna.bean.VoiceoperateTaskBean;
import com.common.voicedna.bean.VoiceprintTaskBean;
import com.common.voicedna.bean.VoiceprintTaskDetailBean;
import com.fd.baselibrary.network.BaseBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface DnaService {
    /**
     * 获取TOKEN
     *
     * @param algorithm 签名算法,目前仅支持HMACSHA256
     * @param appId     APPID
     * @param nonce     6位随机自然数
     * @param signature 签名摘要
     * @param timestamp 当前时间,13位时间戳
     * @return
     */
    @GET(DnaHostUrl.TOKEN)
    Observable<BaseBean<TokenBean>> getToken(@Query("algorithm") String algorithm, @Query("appId") String appId, @Query("nonce") int nonce, @Query("signature") String signature, @Query("timestamp") String timestamp);

    /**
     * 刷新TOKEN
     *
     * @return
     */
    @GET(DnaHostUrl.REFRESH_TOKEN)
    Observable<BaseBean<RefreshTokenBean>> refresh_token();

    /**
     * 创建声纹分组
     *
     * @return
     */
    @POST()
    Observable<BaseBean<String>> group_create(@Url String Url);

    /**
     * 查看声纹分组
     *
     * @return
     */
    @GET(DnaHostUrl.GETGROUP)
    Observable<BaseBean<List<GroupBean>>> getGroup();

    /**
     * 创建注册入库任务
     *
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST(DnaHostUrl.VOICEPRINT_TASK)
    Observable<BaseBean<VoiceprintTaskBean>> VoiceprintTask(@Field("json") String json);

    /**
     * 查看注册入库结果
     * @return
     */
    @GET()
    Observable<BaseBean<VoiceprintTaskDetailBean>> VoiceprintTaskDetail(@Url String Url);


    /**
     * 5.1创建比对任务
     *
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST(DnaHostUrl.VOICEOPERATE_TASK)
    Observable<BaseBean<VoiceoperateTaskBean>> VoiceoperateTask(@Field("json") String json);

    /**
     *查看比对任务
     * @param Url
     * @return
     */
    @GET()
    Observable<BaseBean<VoiceoperateDetailBean>> VoiceoperateDetail(@Url String Url);

}
