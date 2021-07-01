package com.iva.FlacFormatTransformation;

public class AudioDataInfo {
    private long dataLength;
    private byte[] data;

    public long getDataLength() {
        return dataLength;
    }

    public void setDataLength(long dataLength) {
        this.dataLength = dataLength;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
