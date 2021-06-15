package com.iva.VoiceAsvSpoofDetect;

public class AsvSpoofDetectOutputData {
    public long[] reserve;
    public float asvspoof_score;

    public long[] getReserve() {
        return reserve;
    }

    public void setReserve(long[] reserve) {
        this.reserve = reserve;
    }

    public float getAsvspoof_score(){
        return asvspoof_score;
    }

    public void setAsvspoof_score(float asvspoof_score){
        this.asvspoof_score = asvspoof_score;
    }

    @Override
    public String toString() {
        return "活体检测{" +
                ", asvspoof_score=" + asvspoof_score +
                '}';
    }
}
