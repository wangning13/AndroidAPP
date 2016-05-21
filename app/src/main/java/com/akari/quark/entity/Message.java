
package com.akari.quark.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message {

    private String title;
    private String content;
    private int answerNum;
    private int focusNum;
    private List<String> topics = new ArrayList<String>();
    private List<Answer> answers = new ArrayList<Answer>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    public Message withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    public Message withContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * 
     * @return
     *     The answerNum
     */
    public int getAnswerNum() {
        return answerNum;
    }

    /**
     * 
     * @param answerNum
     *     The answer_num
     */
    public void setAnswerNum(int answerNum) {
        this.answerNum = answerNum;
    }

    public Message withAnswerNum(int answerNum) {
        this.answerNum = answerNum;
        return this;
    }

    /**
     * 
     * @return
     *     The focusNum
     */
    public int getFocusNum() {
        return focusNum;
    }

    /**
     * 
     * @param focusNum
     *     The focus_num
     */
    public void setFocusNum(int focusNum) {
        this.focusNum = focusNum;
    }

    public Message withFocusNum(int focusNum) {
        this.focusNum = focusNum;
        return this;
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

    public Message withTopics(List<String> topics) {
        this.topics = topics;
        return this;
    }

    /**
     * 
     * @return
     *     The answers
     */
    public List<Answer> getAnswers() {
        return answers;
    }

    /**
     * 
     * @param answers
     *     The answers
     */
    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Message withAnswers(List<Answer> answers) {
        this.answers = answers;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Message withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
