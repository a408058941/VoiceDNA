package com.iva.constant;

/**
 * Created by qing on 11/01/2018.
 */

public class ErrorCode {
    public static final int SUCCESS = 0;
    public static final int FAIL = -1;
    //录音组件相关错误码
    public static final int RECORDER_PARAMS_NOT_RIGHT = 1101;
    public static final int RECORDER_WITHOUT_INIT = 1102;
    public static final int RECORDER_START_FAIL = 1103;
    public static final int RECORDER_STOP_FAIL = 1104;

    //用户管理相关错误码
    public static final int ADD_NULL_USER = 2001;
    public static final int DELETE_NULL_USER = 2002;
    public static final int FIND_NO_USER = 2201;
    public static final int USER_IS_NULL = 2202;
    public static final int USER_PARAMS_NULL = 2203;
    public static final int USER_MANAGER_WITHOUT_INIT = 5555;

    //声纹验证、注册相关错误码
    public static final int VPR_WITHOUT_INIT = 3333;
    public static final int VPR_MODEL_CONFIG_PATH_ERROR = 3001;
    public static final int VPR_MODEL_COPY_ERROR = 3002;
    public static final int VPR_INIT_ERROR =  3003;
    public static final int VPR_CODE_COPY_ERROR = 3004;
    public static final int VPR_WITHNULL_ARRAY = 3005;
    public static final int VPR_WITHNULL_ARRAY2 = 3006;
    //SDK错误码
    public static final int SDK_WITHOUT_INIT = 6666;
    public static final int SDK_KWS_TRETHLOD_ERROR = 6001;
    public static final int SDK_VPR_INIT_ERROR = 6002;
    public static final int SDK_PERMISSION_FAIL = 6003;
    public static final int FILE_COPY_FAIL = 6004;
    public static final int FILE_COPY_PATH_ERROR = 6005;
    public static final int SDK_VPR_TRETHLOD_ERROR = 6006;

    //vad相关错误码
    //用户管理相关错误码
    public static final int VAD_MODEL_LOAD_FAIL = 7001;
    public static final int VAD_CONFIG_ERROR = 7002;
    public static final int VAD_INIT_ERROR = 7003;
    public static final int VAD_CHOPPED_TOPPING = 7004;
    public static final int VAD_CREATE_INSTANCE_ERROR = 7005;

    //asv相关错误码
    public static final int ASV_INIT_ERROR = 8003;
    public static final int ASV_MODEL_LOAD_FAIL = 8001;
    public static final int ASV_CONFIG_ERROR = 8002;

}
