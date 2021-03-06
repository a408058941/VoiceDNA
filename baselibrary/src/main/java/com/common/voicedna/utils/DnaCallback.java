package com.common.voicedna.utils;

import androidx.annotation.Nullable;

public interface DnaCallback<T>  {

    /*** 对返回数据进行操作的回调， UI线程 */
    void onSuccess(@Nullable T data);


    /*** 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程 */
    void onError(int  code,String msg);

}