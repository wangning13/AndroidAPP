package com.akari.quark.entity.questionDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by motoon on 2016/7/16.
 */
public class MessageFollow {

    @SerializedName("answers")
    @Expose
    private List<Answer> answers = new ArrayList<Answer>();


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
}
