package com.example.sumankhatiwada.vehiclebazzar.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.ui.activities.DashBoardActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Krishna on 4/23/2018.
 */
public class FcmMessagingService  extends FirebaseMessagingService {
        public static final String NOTIFICATION_TITLE = "notificationtitle";
        public static final String NOTIFICATION_BODY = "notificationbody";
    //@TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = Objects.requireNonNull(remoteMessage.getNotification()).getTitle();
        String message = remoteMessage.getNotification().getBody();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(String.valueOf(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(NOTIFICATION_TITLE,title);
        editor.putString(NOTIFICATION_BODY,message );
        editor.apply();

        Intent intent = new Intent(this, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("checker",1);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder =new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(message);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(0,notificationBuilder.build());


        super.onMessageReceived(remoteMessage);
    }
}
