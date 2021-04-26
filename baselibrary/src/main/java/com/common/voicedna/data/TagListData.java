package com.common.voicedna.data;

public class TagListData {
    private String tagName; //入库用户名
    private int status; //0:成功 9排队中 13音频注册中 其他数值:各种类型的错误原因,具体在description
    private String description;//status的补充描述
}
