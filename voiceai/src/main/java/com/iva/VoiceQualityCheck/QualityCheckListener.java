package com.iva.VoiceQualityCheck;

public interface QualityCheckListener {

    void onVadResult(int error, QualityCheckInfo qualityCheckInfo, byte[] data);
}
