package com.akari.quark.entity.questionDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Message {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("answer_num")
    @Expose
    private Long answerNum;
    @SerializedName("focus_num")
    @Expose
    private Long focusNum;
    @SerializedName("create_time")
    @Expose
    private Long createTime;
    @SerializedName("topics")
    @Expose
    private List<String> topics = new ArrayList<String>();
    @SerializedName("is_focused")
    @Expose
    private Boolean isFocused;
    @SerializedName("answers")
    @Expose
    private List<Answer> answers = new ArrayList<Answer>();

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @return The answerNum
     */
    public Long getAnswerNum() {
        return answerNum;
    }

    /**
     * @param answerNum The answer_num
     */
    public void setAnswerNum(Long answerNum) {
        this.answerNum = answerNum;
    }

    /**
     * @return The focusNum
     */
    public Long getFocusNum() {
        return focusNum;
    }

    /**
     * @param focusNum The focus_num
     */
    public void setFocusNum(Long focusNum) {
        this.focusNum = focusNum;
    }

    /**
     * @return The topics
     */
    public List<String> getTopics() {
        return topics;
    }

    /**
     * @param topics The topics
     */
    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    /**
     * @return The answers
     */
    public List<Answer> getAnswers() {
        return answers;
    }

    /**
     * @param answers The answers
     */
    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Boolean getFocused() {
        return isFocused;
    }

    public void setFocused(Boolean focused) {
        isFocused = focused;
    }
}
