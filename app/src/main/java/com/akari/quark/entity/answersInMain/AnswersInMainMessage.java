
package com.akari.quark.entity.answersInMain;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswersInMainMessage {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("focus_num")
    @Expose
    private Long focusNum;
    @SerializedName("answer_num")
    @Expose
    private Long answerNum;
    @SerializedName("topics")
    @Expose
    private List<String> topics = new ArrayList<String>();

    /**
     * 
     * @return
     *     The id
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The focusNum
     */
    public Long getFocusNum() {
        return focusNum;
    }

    /**
     * 
     * @param focusNum
     *     The focus_num
     */
    public void setFocusNum(Long focusNum) {
        this.focusNum = focusNum;
    }

    /**
     * 
     * @return
     *     The answerNum
     */
    public Long getAnswerNum() {
        return answerNum;
    }

    /**
     * 
     * @param answerNum
     *     The answer_num
     */
    public void setAnswerNum(Long answerNum) {
        this.answerNum = answerNum;
    }

    /**
     * 
     * @return
     *     The topics
     */
    public List<String> getTopics() {
        return topics;
    }

    /**
     * 
     * @param topics
     *     The topics
     */
    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

}
