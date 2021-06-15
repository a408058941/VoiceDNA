package com.iva.VoiceAsvSpoofDetect;

public interface AsvSpoofDetectListener {
    void onAsvResult(int error, AsvSpoofDetectOutputData asvSpoofDetectOutputData, byte[] data);
}
