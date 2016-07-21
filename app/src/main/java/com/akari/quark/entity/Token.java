package com.akari.quark.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("token")
    @Expose
    public static String token;

    /**
     * @return The token
     */
    public static String getToken() {
        return token;
    }

    /**
     * @param token The token
     */
    public static void setToken(String token) {
        Token.token = token;
    }
}
