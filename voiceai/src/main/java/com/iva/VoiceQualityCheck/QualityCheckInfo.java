package com.iva.VoiceQualityCheck;

import java.util.Arrays;

/**
 * Created by qing on 31/10/2018.
 */

public class QualityCheckInfo  {
    private float speechDuration;          // active speech duration in seconds
    private float speechEnergy;            // estimated average speech energy
    private float noiseEnergy;             // estimated average speech energy
    private float estSnr;                  // estimated SNR
    private float clippingRatio;
    private int[] reserve;                 //...other data



    public QualityCheckInfo(){}

    public void setSpeechDuration(float speechDuration){
        this.speechDuration = speechDuration;
    }

    public float getSpeechDuration(){
        return speechDuration;
    }

    public float getEstSnr() {
        return estSnr;
    }

    public void setEstSnr(float estSnr) {
        this.estSnr = estSnr;
    }

    public float getSpeechEnergy() {
        return speechEnergy;
    }

    public void setSpeechEnergy(float speechEnergy) {
        this.speechEnergy = speechEnergy;
    }

    public float getNoiseEnergy() {
        return noiseEnergy;
    }

    public void setNoiseEnergy(float noiseEnergy) {
        this.noiseEnergy = noiseEnergy;
    }

    public float getClippingRatio() {
        return clippingRatio;
    }

    public void setClippingRatio(float clippingRatio) {
        this.clippingRatio = clippingRatio;
    }

    public int[] getReserve() {
        return reserve;
    }
    public void setReserve(int[] reserve) {
        this.reserve = reserve;
    }

    @Override
    public String toString() {
        return "质量检测{" +
                "有效时长=" + speechDuration +
                ", 音量=" + speechEnergy +
                ", 信噪比" + estSnr +
                ", 截幅比=" + clippingRatio +
                '}';
    }
}