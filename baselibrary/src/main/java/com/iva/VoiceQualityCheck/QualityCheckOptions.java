package com.iva.VoiceQualityCheck;

public class QualityCheckOptions  {
    private String config = "--nosmoothing";
    private int sampleRate = 16000;
    private float threshold = 0.5f;
    private float sampleThreshold = 0.005f;
    private  int blockSize = 1;
    private String vadOutputFilename = "";

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public float getSampleThreshold() {
        return sampleThreshold;
    }

    public void setSampleThreshold(float sampleThreshold) {
        this.sampleThreshold = sampleThreshold;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public String getVadOutputFilename() {
        return vadOutputFilename;
    }

    public void setVadOutputFilename(String vadOutputFilename) {
        this.vadOutputFilename = vadOutputFilename;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
