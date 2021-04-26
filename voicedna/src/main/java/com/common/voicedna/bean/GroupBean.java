package com.common.voicedna.bean;

public class GroupBean {
    private  String groupName; //声纹库分组
    private  String  id;//声纹分组ID
    private  int  counts;//声纹数

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }
}
