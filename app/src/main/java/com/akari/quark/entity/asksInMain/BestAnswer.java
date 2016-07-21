package com.akari.quark.entity.asksInMain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BestAnswer {

    @SerializedName("praise_num")
    @Expose
    private Long praiseNum;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("id")
    @Expose
    private Long answererId;
    @SerializedName("create_time")
    @Expose
    private Long createTime;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    /**
     * @return The praiseNum
     */
    public Long getPraiseNum() {
        return praiseNum;
    }

    /**
     * @param praiseNum The praise_num
     */
    public void setPraiseNum(Long praiseNum) {
        this.praiseNum = praiseNum;
    }

    /**
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return The answererId
     */
    public Long getAnswererId() {
        return answererId;
    }

    /**
     * @param answererId The answerer_id
     */
    public void setAnswererId(Long answererId) {
        this.answererId = answererId;
    }

    /**
     * @return The createTime
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime The create_time
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * @return The avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * @param avatarUrl The avatar_url
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

}
