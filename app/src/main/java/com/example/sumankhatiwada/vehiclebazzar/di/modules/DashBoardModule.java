package com.example.sumankhatiwada.vehiclebazzar.di.modules;

import com.example.sumankhatiwada.vehiclebazzar.di.scope.PerActivity;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.DashBoardView;
import com.example.sumankhatiwada.vehiclebazzar.vehiclebazzarapiservices.VehicleBazzarService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by sumankhatiwada on 4/18/18.
 */
@Module
public class DashBoardModule {

    DashBoardView dashBoardView;

    public DashBoardModule(DashBoardView dashBoardView) {
        this.dashBoardView = dashBoardView;
    }

    @PerActivity
    @Provides
    public DashBoardView provideDashBoardView() {
        return dashBoardView;
    }

    @PerActivity
    @Provides
    public VehicleBazzarService provideVehicleBazzarService(Retrofit retrofit) {
        return retrofit.create(VehicleBazzarService.class);
    }

    @PerActivity
    @Provides
    UserModel providesUserModel() {
        return new UserModel();
    }
}
