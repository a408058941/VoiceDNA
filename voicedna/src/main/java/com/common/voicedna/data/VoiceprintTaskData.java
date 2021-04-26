package com.common.voicedna.data;

import com.fd.baselibrary.utils.SPManager;

import java.util.List;

public class VoiceprintTaskData {
    private  String groupId;  //目标声纹分组ID
    private List<String> fileIds;// 文件ID数组
    private  boolean vadSwicth;//质检开关,不传默认false
    private  Integer vadLevel; //质检值:1.宽松，3中等，5严格 若vadSwicth为true则必传该字段
    private  boolean diaSwitch; //分割开关,不传默认false
    private  Integer filterType;//分割过滤类型:-1:不过滤,0关键字过滤,1声纹过滤。    若diaSwitch为true则必传
    private  String voiceFilter; //声纹过滤：过滤分组    关键字过滤：关键字串(例：你好/我好&大家好)

    public VoiceprintTaskData() {
    }

    public VoiceprintTaskData(String groupId, List<String> fileIds, boolean vadSwicth, boolean diaSwitch) {
        this.groupId = groupId;
        this.fileIds = fileIds;
        this.vadSwicth = vadSwicth;
        this.diaSwitch = diaSwitch;
    }

    public VoiceprintTaskData(String groupId, List<String> fileIds, boolean vadSwicth, Integer vadLevel, boolean diaSwitch, Integer filterType, String voiceFilter) {
        this.groupId = groupId;
        this.fileIds = fileIds;
        this.vadSwicth = vadSwicth;
        this.vadLevel = vadLevel;
        this.diaSwitch = diaSwitch;
        this.filterType = filterType;
        this.voiceFilter = voiceFilter;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<String> fileIds) {
        this.fileIds = fileIds;
    }

    public boolean isVadSwicth() {
        return vadSwicth;
    }

    public void setVadSwicth(boolean vadSwicth) {
        this.vadSwicth = vadSwicth;
    }

    public Integer getVadLevel() {
        return vadLevel;
    }

    public void setVadLevel(Integer vadLevel) {
        this.vadLevel = vadLevel;
    }

    public boolean isDiaSwitch() {
        return diaSwitch;
    }

    public void setDiaSwitch(boolean diaSwitch) {
        this.diaSwitch = diaSwitch;
    }

    public Integer getFilterType() {
        return filterType;
    }

    public void setFilterType(Integer filterType) {
        this.filterType = filterType;
    }

    public String getVoiceFilter() {
        return voiceFilter;
    }

    public void setVoiceFilter(String voiceFilter) {
        this.voiceFilter = voiceFilter;
    }
}
