package com.akari.quark.entity.questionDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionDetail {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("status")
    @Expose
    private Long status;
    @SerializedName("error_code")
    @Expose
    private String error_code;

    /**
     * @return The message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(Message message) {
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

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }
}
