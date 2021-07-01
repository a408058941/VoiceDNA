package com.common.voicedna.Record;

import java.io.File;

public interface RecorderListener {
    void recordOfByte(byte[] data, int bufferSizeInBytes, int readsize);
    void onRecordStart(int resultCode);
    void onRecordingError(String error);
    void onRecordStop(byte[] bytes);
}
