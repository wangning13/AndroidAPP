package com.akari.quark.entity.asksInMain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AsksInMain {

    @SerializedName("message")
    @Expose
    private List<AsksInMainMessage> message = new ArrayList<AsksInMainMessage>();
    @SerializedName("status")
    @Expose
    private Long status;

    /**
     * @return The message
     */
    public List<AsksInMainMessage> getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(List<AsksInMainMessage> message) {
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

}
