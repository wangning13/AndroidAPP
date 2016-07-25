
package com.akari.quark.entity.questionDetailAnswer;

import java.util.ArrayList;
import java.util.List;

import com.akari.quark.entity.questionDetail.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("answers")
    @Expose
    private List<com.akari.quark.entity.questionDetail.Answer> answers = new ArrayList<com.akari.quark.entity.questionDetail.Answer>();

    /**
     * 
     * @return
     *     The answers
     */
    public List<com.akari.quark.entity.questionDetail.Answer> getAnswers() {
        return answers;
    }

    /**
     * 
     * @param answers
     *     The answers
     */
    public void setAnswers(List<com.akari.quark.entity.questionDetail.Answer> answers) {
        this.answers = answers;
    }

}
