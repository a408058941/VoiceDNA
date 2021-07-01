package com.common.voicedna.bean;

public enum ServiceType {
    TEXT(10, "自由文本"),
    NUMBER(17, "动态数字");
   public int i;
    public  String s;

    ServiceType(int i, String s) {
        this.i = i;
        this.s = s;
    }
}
