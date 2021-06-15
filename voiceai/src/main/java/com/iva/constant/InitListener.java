package com.iva.constant;

public interface InitListener {
    /**
     * 初始化完成回调  主线程
     * @param errorCode  操作码
     */
    void onInit(int errorCode);
}
