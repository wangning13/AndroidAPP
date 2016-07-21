package com.akari.quark.entity.answerdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Writer {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("introduction")
    @Expose
    private String introduction;

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
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The imgUrl
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * @param imgUrl The img_url
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * @return The introduction
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * @param introduction The introduction
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

}
