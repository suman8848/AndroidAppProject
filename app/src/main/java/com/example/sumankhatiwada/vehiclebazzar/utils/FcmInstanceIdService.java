package com.example.sumankhatiwada.vehiclebazzar.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Krishna on 4/23/2018.
 */
public class FcmInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(String.valueOf(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(R.string.FCM_TOKEN), recent_token);
        editor.apply();

    }
}
