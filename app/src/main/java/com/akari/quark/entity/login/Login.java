package com.akari.quark.entity.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("status")
    @Expose
    private Long status;
    @SerializedName("error_code")
    @Expose
    private String errorCode;
    @SerializedName("message")
    @Expose
    private Message message;
//    @SerializedName("message")
//    @Expose
//    private String error;

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

    /**
     * @return The errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode The error_code
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return The message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(Message message) {
        this.message = message;
    }

//    /**
//     *
//     * @return
//     *     The error
//     */
//    public String getError() {
//        return error;
//    }
//
//    /**
//     *
//     * @param error
//     *     The error
//     */
//    public void setError(String error) {
//        this.error = error;
//    }

}
