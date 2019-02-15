package com.example.user.loftmoney;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    private String status;
    private int id;

    @SerializedName("auth_token")
    private String token;

    public AuthResponse() {
    }

    public AuthResponse(String status, int id, String token) {
        this.status = status;
        this.id = id;
        this.token = token;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
