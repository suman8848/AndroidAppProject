package com.example.sumankhatiwada.vehiclebazzar.di.modules;

import com.example.sumankhatiwada.vehiclebazzar.di.scope.PerActivity;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CarPostRequest;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CarPostResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CommentObject;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.DashBoardView;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.HomeView;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.ProfileView;
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
    ProfileView profileView;
    HomeView homeView;

    public DashBoardModule(DashBoardView dashBoardView) {
        this.dashBoardView = dashBoardView;
    }

    public DashBoardModule(ProfileView profileView) {
        this.profileView = profileView;
    }

    public DashBoardModule(HomeView homeView) {
        this.homeView = homeView;
    }

    @PerActivity
    @Provides
    public HomeView provideHomeView() {
        return homeView;
    }

    @PerActivity
    @Provides
    public ProfileView provideProfileView() {
        return profileView;
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
    public CarPostRequest provideCarPostRequest(){
        return new CarPostRequest();
    }

    @PerActivity
    @Provides
    UserModel providesUserModel() {
        return new UserModel();
    }

    @PerActivity
    @Provides
    CommentObject providesComment() {
        return new CommentObject();
    }

}
