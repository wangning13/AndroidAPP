package com.akari.quark.entity.answerDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("question_id")
    @Expose
    private Long questionId;
    @SerializedName("answerer_id")
    @Expose
    private Long answererId;
    @SerializedName("create_time")
    @Expose
    private Long createTime;
    @SerializedName("delete_flag")
    @Expose
    private Long deleteFlag;
    @SerializedName("parise_num")
    @Expose
    private Long pariseNum;
    @SerializedName("comment_num")
    @Expose
    private Long commentNum;
    @SerializedName("down_num")
    @Expose
    private Long downNum;
    @SerializedName("collect_num")
    @Expose
    private Long collectNum;
    @SerializedName("read_num")
    @Expose
    private Long readNum;
    @SerializedName("writer")
    @Expose
    private Writer writer;
    @SerializedName("is_collected")
    @Expose
    private Boolean isCollected;
    @SerializedName("is_down")
    @Expose
    private Boolean isDown;
    @SerializedName("is_praised")
    @Expose
    private Boolean isPraised;

    /**
     * @return The id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return The questionId
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId The question_id
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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
     * @return The deleteFlag
     */
    public Long getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * @param deleteFlag The delete_flag
     */
    public void setDeleteFlag(Long deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * @return The pariseNum
     */
    public Long getPariseNum() {
        return pariseNum;
    }

    /**
     * @param pariseNum The parise_num
     */
    public void setPariseNum(Long pariseNum) {
        this.pariseNum = pariseNum;
    }

    /**
     * @return The commentNum
     */
    public Long getCommentNum() {
        return commentNum;
    }

    /**
     * @param commentNum The comment_num
     */
    public void setCommentNum(Long commentNum) {
        this.commentNum = commentNum;
    }

    /**
     * @return The downNum
     */
    public Long getDownNum() {
        return downNum;
    }

    /**
     * @param downNum The down_num
     */
    public void setDownNum(Long downNum) {
        this.downNum = downNum;
    }

    /**
     * @return The collectNum
     */
    public Long getCollectNum() {
        return collectNum;
    }

    /**
     * @param collectNum The collect_num
     */
    public void setCollectNum(Long collectNum) {
        this.collectNum = collectNum;
    }

    /**
     * @return The readNum
     */
    public Long getReadNum() {
        return readNum;
    }

    /**
     * @param readNum The read_num
     */
    public void setReadNum(Long readNum) {
        this.readNum = readNum;
    }

    /**
     * @return The writer
     */
    public Writer getWriter() {
        return writer;
    }

    /**
     * @param writer The writer
     */
    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    /**
     * @return The isCollected
     */
    public Boolean getIsCollected() {
        return isCollected;
    }

    /**
     * @param isCollected The is_collected
     */
    public void setIsCollected(Boolean isCollected) {
        this.isCollected = isCollected;
    }

    /**
     * @return The isDown
     */
    public Boolean getIsDown() {
        return isDown;
    }

    /**
     * @param isDown The is_down
     */
    public void setIsDown(Boolean isDown) {
        this.isDown = isDown;
    }

    /**
     * @return The isPraised
     */
    public Boolean getIsPraised() {
        return isPraised;
    }

    /**
     * @param isPraised The is_praised
     */
    public void setIsPraised(Boolean isPraised) {
        this.isPraised = isPraised;
    }

}
