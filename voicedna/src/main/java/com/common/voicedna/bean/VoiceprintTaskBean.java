package com.common.voicedna.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class VoiceprintTaskBean {
    /**
     * count : 0
     * result : null
     * status : null
     * beginTime : 0
     * rollback : false
     * idMap : {"2aed1b37f5f546b78375b6263f48a8bd":"76c282eaf805571ed04cb9bd631f9ada"}
     */

    private Integer count;
    private Object result;
    private Object status;
    private Integer beginTime;
    private Boolean rollback;
    private Map<String,String> idMap;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Integer getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Integer beginTime) {
        this.beginTime = beginTime;
    }

    public Boolean getRollback() {
        return rollback;
    }

    public void setRollback(Boolean rollback) {
        this.rollback = rollback;
    }

    public Map<String,String> getIdMapX() {
        return idMap;
    }

    public void setIdMapX(Map<String,String> idMapX) {
        this.idMap = idMapX;
    }


}
