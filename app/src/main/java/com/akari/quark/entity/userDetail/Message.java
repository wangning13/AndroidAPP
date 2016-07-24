
package com.akari.quark.entity.userDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("introduction")
    @Expose
    private String introduction;
    @SerializedName("gender")
    @Expose
    private Long gender;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("work")
    @Expose
    private String work;
    @SerializedName("residence")
    @Expose
    private String residence;
    @SerializedName("major")
    @Expose
    private String major;
    @SerializedName("profession")
    @Expose
    private Long profession;
    @SerializedName("create_time")
    @Expose
    private Long createTime;

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
     *     The imgUrl
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 
     * @param imgUrl
     *     The img_url
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The introduction
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 
     * @param introduction
     *     The introduction
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 
     * @return
     *     The gender
     */
    public Long getGender() {
        return gender;
    }

    /**
     * 
     * @param gender
     *     The gender
     */
    public void setGender(Long gender) {
        this.gender = gender;
    }

    /**
     * 
     * @return
     *     The unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 
     * @param unit
     *     The unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 
     * @return
     *     The education
     */
    public String getEducation() {
        return education;
    }

    /**
     * 
     * @param education
     *     The education
     */
    public void setEducation(String education) {
        this.education = education;
    }

    /**
     * 
     * @return
     *     The work
     */
    public String getWork() {
        return work;
    }

    /**
     * 
     * @param work
     *     The work
     */
    public void setWork(String work) {
        this.work = work;
    }

    /**
     * 
     * @return
     *     The residence
     */
    public String getResidence() {
        return residence;
    }

    /**
     * 
     * @param residence
     *     The residence
     */
    public void setResidence(String residence) {
        this.residence = residence;
    }

    /**
     * 
     * @return
     *     The major
     */
    public String getMajor() {
        return major;
    }

    /**
     * 
     * @param major
     *     The major
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * 
     * @return
     *     The profession
     */
    public Long getProfession() {
        return profession;
    }

    /**
     * 
     * @param profession
     *     The profession
     */
    public void setProfession(Long profession) {
        this.profession = profession;
    }

    /**
     * 
     * @return
     *     The createTime
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime
     *     The create_time
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

}
