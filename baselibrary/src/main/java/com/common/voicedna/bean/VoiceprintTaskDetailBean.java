package com.common.voicedna.bean;

import com.common.voicedna.data.TagListData;

import java.util.List;

public class VoiceprintTaskDetailBean {

    private String taskId;//任务Id
    private String fileId;//文件ID
    private String groupId;//声纹组ID
    private String groupName;//声纹组名
    private int status;//0:成功 9排队中 13音频注册中 其他数值:各种类型的错误原因,具体在description
    private String description;//status的补充描述
    private boolean isDia;//是否分割
    private List<TagListData> tagList;//注册结果,若开启分割可能会注册多个声纹目标
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDia() {
        return isDia;
    }

    public void setDia(boolean dia) {
        isDia = dia;
    }

    public List<TagListData> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagListData> tagList) {
        this.tagList = tagList;
    }
}
