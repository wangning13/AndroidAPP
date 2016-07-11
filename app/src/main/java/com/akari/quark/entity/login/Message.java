
package com.akari.quark.entity.login;

import com.google.gson.annotations.Expose;

public class Message {

//    @SerializedName("token")
    @Expose
    private String info;

    /**
     * 
     * @return
     *     The info
     */
    public String getInfo() {
        return info;
    }

    /**
     * 
     * @param info
     *     The info
     */
    public void setInfo(String info) {
        this.info = info;
    }

}
