package com.akari.quark.entity.comment;

/**
 * Created by Akari on 16/7/13.
 */
public class CommentMessage {
    private long id;
    private long answer_id;
    private long answer_writer;
    private long replyer_id;
    private long replyee_id;
    private String content;
    private long create_time;
    private String replyee_name;
    private String replyer_name;
    private String img_url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(long answer_id) {
        this.answer_id = answer_id;
    }

    public long getAnswer_writer() {
        return answer_writer;
    }

    public void setAnswer_writer(long answer_writer) {
        this.answer_writer = answer_writer;
    }

    public long getReplyer_id() {
        return replyer_id;
    }

    public void setReplyer_id(long replyer_id) {
        this.replyer_id = replyer_id;
    }

    public long getReplyee_id() {
        return replyee_id;
    }

    public void setReplyee_id(long replyee_id) {
        this.replyee_id = replyee_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getReplyee_name() {
        return replyee_name;
    }

    public void setReplyee_name(String replyee_name) {
        this.replyee_name = replyee_name;
    }

    public String getReplyer_name() {
        return replyer_name;
    }

    public void setReplyer_name(String replyer_name) {
        this.replyer_name = replyer_name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}