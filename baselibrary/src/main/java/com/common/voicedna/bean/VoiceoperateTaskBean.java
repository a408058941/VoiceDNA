package com.common.voicedna.bean;

import java.util.Map;

public class VoiceoperateTaskBean {

    /**
     * count : 0
     * result : null
     * status : null
     * beginTime : 0
     * rollback : false
     * idMap : {"b4fcde62b6974bd2a41d9d1a2ab82dfa":"GXwBc4efbiy3gIv56jWgfQqmdTyX264K"}
     */

    private Integer count;
    private Object result;
    private Object status;
    private Integer beginTime;
    private Boolean rollback;
    private Map<String ,String> idMap;

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

    public Map<String, String> getIdMap() {
        return idMap;
    }

    public void setIdMap(Map<String, String> idMap) {
        this.idMap = idMap;
    }
}
