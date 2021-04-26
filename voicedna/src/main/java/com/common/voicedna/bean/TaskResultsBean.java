package com.common.voicedna.bean;

import java.util.List;

public class TaskResultsBean {
    /**
     * flag : 0
     * description : 未匹配到声纹
     * isRegis : false
     * regisTaskId : null
     * score : null
     * execResults : []
     */
    /**
     * 处理结果标识,需配合description字段
     * 0 系统错误
     * 1 验证通过（1:1）
     * 1 匹配到声纹（1:N）
     * 0 验证不通过（1:1）
     * 0 未匹配到声纹(1:N)
     * -1 暂停中
     */
    private Integer flag;
    /**
     * 描述:
     * 验证通过
     * 匹配到声纹
     * 验证不通过
     * 未匹配到声纹
     * 该目标用户已被删除
     * 质检不通过
     * 分割后无有效语音
     * 文件格式不支持
     * 文件长度小于脱敏长度
     * 文件超过100MB
     * 文件过小
     * 服务用量不足
     */
    private String description;
    private Boolean isRegis; //是否注册入库
    private Object regisTaskId; //注册任务ID
    private Object score; //相似度分数,取比对项中最高值
    private List<ExecResultsBean> execResults;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRegis() {
        return isRegis;
    }

    public void setRegis(Boolean regis) {
        isRegis = regis;
    }

    public Object getRegisTaskId() {
        return regisTaskId;
    }

    public void setRegisTaskId(Object regisTaskId) {
        this.regisTaskId = regisTaskId;
    }

    public Object getScore() {
        return score;
    }

    public void setScore(Object score) {
        this.score = score;
    }

    public List<ExecResultsBean> getExecResults() {
        return execResults;
    }

    public void setExecResults(List<ExecResultsBean> execResults) {
        this.execResults = execResults;
    }
}
