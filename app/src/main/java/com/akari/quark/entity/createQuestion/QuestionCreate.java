
package com.akari.quark.entity.createQuestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionCreate {

    @SerializedName("status")
    @Expose
    private Long status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("question_id")
    @Expose
    private Long questionId;

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

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
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

}
