
package com.akari.quark.entity;

import java.util.HashMap;
import java.util.Map;


public class User {

    private String name;
    private String imgUrl;
    private String introduction;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    public User withName(String name) {
        this.name = name;
        return this;
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

    public User withImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
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

    public User withIntroduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public User withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
