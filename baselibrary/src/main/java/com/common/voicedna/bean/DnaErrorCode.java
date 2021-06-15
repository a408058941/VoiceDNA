package com.common.voicedna.bean;


import com.voiceai.voicedna.constant.ErrorCode;

public enum DnaErrorCode {

    ERROR_UNKNOW(-9999, "系统错误"),

    ERROR_REQUEST_PARAM(100000, "请求参数错误！"),

    ERROR_REQUEST(100001, "网络请求失败！请检查网络是否畅通！"),

    ERROR_CLIENT_CREATE(200000, "创建客户端失败"),

    ERROR_CLIENT_SIGN(200001, "创建客户端签名失败"),

    ERROR_DYNAMIC_NUMBER_COUNT(300000, "生成随机数条数错误（count），范围1-5"),
    ERROR_USEFOR(300001, "用途错误（userFor）：0注册 1验证"),
    ERROR_service(401, "应用服务不匹配"),
    ERROR_VADASV(400001, "质检活检异常"),
    ERROR_SPEECH(400002, "有效时长--有效时长过短"),
    ERROR_ENERGY(400003, "平均能量--音量过小"),
    ERROR_ESTSNR(400004, "信噪比--环境噪音大"),
    ERROR_CLIPPING(400005, "截幅比--音量过大"),
    ERROR_ASV(400006, "活体--活体检测不通过"),
    ERROR_ERRER(-999, "未知错误");


    private Integer code;
    private String msg;

    DnaErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg ;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static DnaErrorCode getErrorCodeByCode(Integer code) {
        for (DnaErrorCode c : DnaErrorCode.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        DnaErrorCode errorCode=null;
        for (DnaErrorCode c : DnaErrorCode.values()) {
            errorCode=c;
        }
        return errorCode;
    }

    }
