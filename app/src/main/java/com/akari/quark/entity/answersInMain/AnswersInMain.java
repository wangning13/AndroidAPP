package com.akari.quark.entity.answersInMain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AnswersInMain {

    @SerializedName("message")
    @Expose
    private List<AnswersInMainMessage> message = new ArrayList<AnswersInMainMessage>();
    @SerializedName("status")
    @Expose
    private Long status;

    /**
     * @return The message
     */
    public List<AnswersInMainMessage> getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(List<AnswersInMainMessage> message) {
        this.message = message;
    }

    /**
     * @return The status
     */
    public Long getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Long status) {
        this.status = status;
    }

}
