package com.akari.quark.entity.follow;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Akari on 16/7/18.
 */
public class Follow {
    /**
     * status : 1
     * error_code : null
     * message : [{"id":12,"img_url":"default.png","name":"无名氏","introduction":null},{"id":8,"img_url":"default.png","name":"BOSS","introduction":null}]
     */

    private int status;
    private int error_code;
    /**
     * id : 12
     * img_url : default.png
     * name : 无名氏
     * introduction : null
     */

    @SerializedName("message")
    private List<FollowMessage> followList;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<FollowMessage> getFollowList() {
        return followList;
    }

    public void setFollowList(List<FollowMessage> followList) {
        this.followList = followList;
    }
}
