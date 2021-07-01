package com.common.voicedna.Common;

import com.iva.VoiceAsvSpoofDetect.AsvSpoofDetectOutputData;
import com.iva.VoiceQualityCheck.QualityCheckInfo;

public interface VerifyListener {
    boolean onAudioDetection(int var1,QualityCheckInfo inio, AsvSpoofDetectOutputData asvSpoofDetectOutput, String msg);
}
