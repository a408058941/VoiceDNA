package com.iva.VoiceQualityCheck;


import com.iva.constant.Constants;

public class VadConfig {
    private String filePath = Constants.SDK_ROOT_DIR+ Constants.VAD_MODEL_PATH;
    private String fileName = Constants.VAD_MODEL_NAME ;

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
