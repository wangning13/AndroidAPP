
package com.akari.quark.entity.questionDetailAnswer;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Answer_ {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("praise_num")
    @Expose
    private Long praiseNum;
    @SerializedName("user")
    @Expose
    private User user;

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

    /**
     * 
     * @return
     *     The praiseNum
     */
    public Long getPraiseNum() {
        return praiseNum;
    }

    /**
     * 
     * @param praiseNum
     *     The praise_num
     */
    public void setPraiseNum(Long praiseNum) {
        this.praiseNum = praiseNum;
    }

    /**
     * 
     * @return
     *     The user
     */
    public User getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(User user) {
        this.user = user;
    }

}
