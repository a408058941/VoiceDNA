package com.common.voicedna.bean;

import java.util.List;

public class VoiceoperateDetailBean {

    /**
     * taskId : Jg2OyPNL3UufHBvHZDFUhqyClsjWOYS9
     * fileId : null
     * groupId : 1cdcb88a98d24de491028154eab57f15
     * groupName : 111
     * targetUser :
     * targetType : 0
     * taskResults : [{"flag":0,"description":"未匹配到声纹","isRegis":false,"regisTaskId":null,"score":null,"execResults":[]}]
     */

    private String taskId; //  任务Id
    private String fileId; //  文件ID
    private String groupId; //  目标分组ID
    private String groupName; //  目标分组名
    private String targetUser; //  目标用户,1:N时为空
    private Integer targetType; //  比对类型 1：声纹1:1验证 0：声纹1：N检索
    private List<TaskResultsBean> taskResults; //  若开启分割,可能会产生多个结果项
    private boolean completed;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public List<TaskResultsBean> getTaskResults() {
        return taskResults;
    }

    public void setTaskResults(List<TaskResultsBean> taskResults) {
        this.taskResults = taskResults;
    }
}
