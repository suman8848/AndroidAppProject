package com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sumankhatiwada on 4/25/18.
 */

public class FcmReqRes {

    @SerializedName("fcmToken")
    @Expose
    private String fcmToken;
    @SerializedName("result")
    @Expose
    private String result;

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
