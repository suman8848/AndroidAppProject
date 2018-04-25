package com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Krishna on 4/23/2018.
 */
public class NotificationDTO {
    @SerializedName("title")
    @Expose
    private  String title;

    @SerializedName("body")
    @Expose
    private  String body;

    @SerializedName("sound")
    @Expose
    private  String sound;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", sound='" + sound + '\'' +
                '}';
    }
}
