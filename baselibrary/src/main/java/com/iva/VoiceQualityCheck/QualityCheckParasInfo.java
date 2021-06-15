package com.iva.VoiceQualityCheck;

public class QualityCheckParasInfo  {

    private byte[] parasDataBuffer;
    private long parasDataLength;
    private int parasDataType;
    private int parasSampleRate;
    private int parasChannels;
    private boolean parasInterlace;
    private boolean parasEndData;

    public QualityCheckParasInfo() {
    }

    public byte[] getParasDataBuffer() {
        return parasDataBuffer;
    }

    public void setParasDataBuffer(byte[] parasDataBuffer) {
        this.parasDataBuffer = parasDataBuffer;
    }

    public long getParasDataLength() {
        return parasDataLength;
    }

    public void setParasDataLength(long parasDataLength) {
        this.parasDataLength = parasDataLength;
    }

    public int getParasDataType() {
        return parasDataType;
    }

    public void setParasDataType(int parasDataType) {
        this.parasDataType = parasDataType;
    }

    public int getParasSampleRate() {
        return parasSampleRate;
    }

    public void setParasSampleRate(int parasSampleRate) {
        this.parasSampleRate = parasSampleRate;
    }

    public int getParasChannels() {
        return parasChannels;
    }

    public void setParasChannels(int parasChannels) {
        this.parasChannels = parasChannels;
    }

    public boolean isParasInterlace() {
        return parasInterlace;
    }

    public void setParasInterlace(boolean parasInterlace) {
        this.parasInterlace = parasInterlace;
    }

    public boolean isParasEndData() {
        return parasEndData;
    }

    public void setParasEndData(boolean parasEndData) {
        this.parasEndData = parasEndData;
    }
}



