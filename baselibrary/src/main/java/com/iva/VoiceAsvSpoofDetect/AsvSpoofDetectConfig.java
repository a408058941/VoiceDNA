package com.iva.VoiceAsvSpoofDetect;


import android.content.Context;
import android.os.Environment;

import com.iva.constant.Constants;

import java.io.File;

public class AsvSpoofDetectConfig {
    private Context context;
    File SDK_ROOT_DIR ;
    private String filePath;
    public AsvSpoofDetectConfig(Context context) {
        this.context = context;
        SDK_ROOT_DIR = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        filePath = SDK_ROOT_DIR.getAbsolutePath();
    }

    private String fileName = Constants.ASV_MODEL_NAME ;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
