package com.iva.constant;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

public class IdVerifierHelper extends AsyncTask<Void, Void, Integer> {
    private static String TAG="IdVerifierHelper";
    private WeakReference<Context> mContext;
    private String fileName;
    private String outPath;
    private CallBack callBack;
    public IdVerifierHelper(Context context, String fileName, String outPath, CallBack callBack) {
        mContext = new WeakReference<Context>(
                context);
        this.fileName=fileName;
        this.outPath=outPath;
        this.callBack=callBack;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        File targrtFile = new File(outPath,fileName);
        String targetPath=targrtFile.getPath();
        targrtFile=null;
        int rsultCode=-1;
        try {
            rsultCode=FileUtil.copyFileToSD(mContext.get(), fileName,outPath);
        } catch (FileNotFoundException e) {
            Log.e(TAG,"doInBackground:  copy "+targetPath+"error: FileNotFoundException:"+e.getMessage());
            return ErrorCode.FILE_COPY_PATH_ERROR;
        }catch(Exception e) {
            e.printStackTrace();
            Log.e(TAG,"doInBackground:  copy "+targetPath+"error: Exception:"+e.getMessage());
            return ErrorCode.FILE_COPY_FAIL;
        }
        Log.e("AA","YRHH  The targetPath = " +  targetPath);
        return rsultCode== ErrorCode.SUCCESS?callBack.onDoAfterDone(true,targetPath):rsultCode;
    }

    @Override
    protected void onPostExecute(Integer aBoolean) {
        super.onPostExecute(aBoolean);
        callBack.onFail(aBoolean);
    }
}
