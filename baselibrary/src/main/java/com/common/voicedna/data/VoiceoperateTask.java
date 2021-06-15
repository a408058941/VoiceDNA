package com.common.voicedna.data;

import java.util.List;

public class VoiceoperateTask {
    private  String groupId; //目标声纹分组ID
    private List<String> fileIds; //文件ID数组
    private  Integer targetType; //比对类型 1：声纹1:1验证 0：声纹1：N检索
    private  String targetUser;// 1:1比对用户不能为空 1:N 比对用户必须为空     取比对用户的tagName
    private  boolean vadSwitch; //质检开关,不传默认false
    private  Integer vadLevel;  //质检值:1.宽松，3中等，5严格。 若vadSwicth为true则必传该字段
    private  boolean diaSwitch;//分割开关,不传默认false
    private  Integer filterType;//分割过滤类型:-1:不过滤,0关键字过滤,1声纹过滤。  若diaSwitch为true则必传
    private  String voiceFilter;//声纹过滤：过滤分组     关键字过滤：关键字串(例：你好/我好)
    private  List<AutoRegisData> autoRegis; //自动入库条件,不传条件代表不开启

    public VoiceoperateTask() {
    }

    public VoiceoperateTask(String groupId, List<String> fileIds, int targetType, String targetUser, boolean vadSwitch, boolean diaSwitch, List<AutoRegisData> autoRegis) {
        this.groupId = groupId;
        this.fileIds = fileIds;
        this.targetType = targetType;
        this.targetUser = targetUser;
        this.vadSwitch = vadSwitch;
        this.diaSwitch = diaSwitch;
        this.autoRegis = autoRegis;
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

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public boolean isVadSwicth() {
        return vadSwitch;
    }

    public void setVadSwicth(boolean vadSwitch) {
        this.vadSwitch = vadSwitch;
    }

    public int getVadLevel() {
        return vadLevel;
    }

    public void setVadLevel(int vadLevel) {
        this.vadLevel = vadLevel;
    }

    public boolean isDiaSwitch() {
        return diaSwitch;
    }

    public void setDiaSwitch(boolean diaSwitch) {
        this.diaSwitch = diaSwitch;
    }

    public int getFilterType() {
        return filterType;
    }

    public void setFilterType(int filterType) {
        this.filterType = filterType;
    }

    public String getVoiceFilter() {
        return voiceFilter;
    }

    public void setVoiceFilter(String voiceFilter) {
        this.voiceFilter = voiceFilter;
    }

    public List<AutoRegisData> getAutoRegis() {
        return autoRegis;
    }

    public void setAutoRegis(List<AutoRegisData> autoRegis) {
        this.autoRegis = autoRegis;
    }
}
