package com.akari.quark.entity.comment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Akari on 16/7/13.
 */
public class Comment {

    /**
     * status : 1
     * error_code : null
     * message : [{"id":10,"answer_id":1,"answer_writer":1,"replyer_id":1,"replyee_id":1,"content":"测试，第m个回复","create_time":1468047256329,"replyee_name":"小春","replyer_name":"小春","img_url":"test.png"},{"id":11,"answer_id":1,"answer_writer":1,"replyer_id":1,"replyee_id":1,"content":"测试，第m个回复","create_time":1468047273954,"replyee_name":"小春","replyer_name":"小春","img_url":"test.png"}]
     */

    private int status;
    private int error_code;
    /**
     * id : 10
     * answer_id : 1
     * answer_writer : 1
     * replyer_id : 1
     * replyee_id : 1
     * content : 测试，第m个回复
     * create_time : 1468047256329
     * replyee_name : 小春
     * replyer_name : 小春
     * img_url : test.png
     */

    @SerializedName("message")
    private List<CommentMessage> messageList;

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

    public List<CommentMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<CommentMessage> messageList) {
        this.messageList = messageList;
    }
}
