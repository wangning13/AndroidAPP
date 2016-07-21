package com.akari.quark.entity.asksInMain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AsksInMainMessage {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("bestAnswer")
    @Expose
    private BestAnswer bestAnswer;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("topics")
    @Expose
    private List<String> topics = new ArrayList<String>();

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
     * @return The bestAnswer
     */
    public BestAnswer getBestAnswer() {
        return bestAnswer;
    }

    /**
     * @param bestAnswer The bestAnswer
     */
    public void setBestAnswer(BestAnswer bestAnswer) {
        this.bestAnswer = bestAnswer;
    }

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

}
