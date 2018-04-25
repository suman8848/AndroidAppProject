package com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Krishna on 4/23/2018.
 */
public class TokenDTO {


    @SerializedName("deviceToken")
    @Expose
    private String deviceToken;

    public TokenDTO(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    @Override
    public String toString() {
        return "TokenDTO{" +
                "deviceToken='" + deviceToken + '\'' +
                '}';
    }
}
