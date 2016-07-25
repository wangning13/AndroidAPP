
package com.akari.quark.entity.questionDetailAnswer;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Message {

    @SerializedName("answers")
    @Expose
    private List<Answer_> answers = new ArrayList<Answer_>();

    /**
     * 
     * @return
     *     The answers
     */
    public List<Answer_> getAnswers() {
        return answers;
    }

    /**
     * 
     * @param answers
     *     The answers
     */
    public void setAnswers(List<Answer_> answers) {
        this.answers = answers;
    }

}
