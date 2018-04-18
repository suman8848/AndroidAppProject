package com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sumankhatiwada on 4/17/18.
 */

public class LoginAndRegisterResponses {
    @SerializedName("auth")
    @Expose
    private Boolean auth;
    @SerializedName("token")
    @Expose
    private String token;

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
