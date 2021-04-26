package com.common.voicedna.utils;

import java.util.UUID;

public class UuidUtis {
    public static  String  randomUuid(){
            //注意replaceAll前面的是正则表达式
            String uuid = UUID.randomUUID().toString();
            return  uuid;
    }
}
