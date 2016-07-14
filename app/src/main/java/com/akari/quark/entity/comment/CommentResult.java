package com.akari.quark.entity.comment;

/**
 * Created by Akari on 16/7/15.
 */
public class CommentResult {
    /**
     * status : 1
     * error_code : null
     * message : {"id":17}
     */

    private int status;
    private int error_code;
    /**
     * id : 17
     */

    private CommentResultMessage message;

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

    public CommentResultMessage getMessage() {
        return message;
    }

    public void setMessage(CommentResultMessage message) {
        this.message = message;
    }

    public static class CommentResultMessage {
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}
