package com.example.sumankhatiwada.vehiclebazzar.di.components;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.sumankhatiwada.vehiclebazzar.di.modules.ApplicationModule;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.SharedPreferenceManager;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by sumankhatiwada on 4/17/18.
 */
@Singleton
@Component(modules = ApplicationModule.class)

public interface ApplicationComponent {
    Retrofit exposeRetrofit();
    Context exposeContext();
    SharedPreferences exposeSharedPreferences();
    SharedPreferenceManager exposeSharedPreferencesManager();
}
