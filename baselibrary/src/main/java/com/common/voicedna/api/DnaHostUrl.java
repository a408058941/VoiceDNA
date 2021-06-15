package com.common.voicedna.api;

public interface DnaHostUrl {
    String HOST_URL = "https://test.cloudv3.voiceaitech.com";
    String fileServer = "https://test.dna.upload.voiceaitech.com";

    //获取TOKEN
    public static final String TOKEN = HOST_URL + "api/dna/application/token";
    //刷新TOKEN
    public static final String REFRESH_TOKEN = HOST_URL + "api/dna/application/refresh/token";
    //创建声纹分组
    public static final String GROUP_CREATE = HOST_URL + "api/dna/voiceprint/group/create";
    //查看声纹分组
    public static final String GETGROUP = HOST_URL + "api/dna/voiceprint/group/get";
    //创建注册入库任务
    public static final String VOICEPRINT_TASK= HOST_URL + "api/dna/voiceprint/task";
    //查看注册入库结果
    public static final String VOICEPRINT_TASK_DETAIL= HOST_URL + "api/dna/voiceprint/task/detail/";
    //上传文件接口
    public static final String UPLOAD= HOST_URL + "api/file/audio/upload";

    //5.1创建比对任务
    public static final String VOICEOPERATE_TASK= HOST_URL + "api/dna/voiceoperate/task";
    //5.查看比对任务
    public static final String VOICEOPERATE_DETAIL= HOST_URL + "api/dna/voiceoperate/task/detail/";
}
