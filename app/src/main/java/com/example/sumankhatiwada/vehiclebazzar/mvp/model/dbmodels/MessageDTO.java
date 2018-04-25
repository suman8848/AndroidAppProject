package com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Krishna on 4/23/2018.
 */

public class MessageDTO {

    @SerializedName("notification")
    @Expose
    private NotificationDTO notification;

    public NotificationDTO getNotificationDTO() {
        return notification;
    }

    public void setNotificationDTO(NotificationDTO notificationDTO) {
        this.notification = notificationDTO;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "notificationDTO=" + notification +
                '}';
    }
}
