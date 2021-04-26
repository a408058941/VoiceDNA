package com.common.voicedna.data;

public class AutoRegisData {
    private  int condition; //匹配条件 1.验证通过 2.验证不通过 3.匹配到声纹 4.匹配不到声纹
    private  float param; //参数，-100 - 100
    private  String groupId; //入库目标声纹分组ID

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public float getParam() {
        return param;
    }

    public void setParam(float param) {
        this.param = param;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
