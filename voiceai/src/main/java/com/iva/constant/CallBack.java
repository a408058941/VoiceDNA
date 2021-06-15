package com.iva.constant;

public interface CallBack {
   void onFail(int resultCode);
   int onDoAfterDone(boolean result, String targetPath);
}
