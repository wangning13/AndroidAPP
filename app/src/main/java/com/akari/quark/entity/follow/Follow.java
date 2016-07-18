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
    private Object error_code;
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

    public Object getError_code() {
        return error_code;
    }

    public void setError_code(Object error_code) {
        this.error_code = error_code;
    }

    public List<FollowMessage> getFollowList() {
        return followList;
    }

    public void setFollowList(List<FollowMessage> followList) {
        this.followList = followList;
    }

    public static class FollowMessage {
        private long id;
        private String img_url;
        private String name;
        private Object introduction;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getIntroduction() {
            return introduction;
        }

        public void setIntroduction(Object introduction) {
            this.introduction = introduction;
        }
    }
}
