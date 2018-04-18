package com.example.sumankhatiwada.vehiclebazzar.application;

import android.app.Application;

import com.example.sumankhatiwada.vehiclebazzar.di.components.ApplicationComponent;
import com.example.sumankhatiwada.vehiclebazzar.di.components.DaggerApplicationComponent;
import com.example.sumankhatiwada.vehiclebazzar.di.modules.ApplicationModule;

/**
 * Created by sumankhatiwada on 4/17/18.
 */

public class VehicleBazzarApplication extends Application {
    private ApplicationComponent mApplicationComponent;
    public final static String BASE_URL = "https://ancient-hamlet-60512.herokuapp.com";
    private static VehicleBazzarApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplicationComponent();
        mInstance = this;
    }

    private void initializeApplicationComponent() {

        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this, BASE_URL))
                .build();

    }

    public ApplicationComponent getmApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public static synchronized VehicleBazzarApplication getInstance() {
        return mInstance;
    }

}
