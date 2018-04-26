package com.example.sumankhatiwada.vehiclebazzar.di.modules;

import com.example.sumankhatiwada.vehiclebazzar.di.scope.PerActivity;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.FcmReqRes;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.RegisterRequestAndProfileResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.LoginView;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.RegisterView;
import com.example.sumankhatiwada.vehiclebazzar.vehiclebazzarapiservices.VehicleBazzarService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by sumankhatiwada on 4/17/18.
 */

@Module
public class LoginAndRegisterModule {

    private LoginView loginView;
    private RegisterView registerView;


    public LoginAndRegisterModule(LoginView loginView) {
        this.loginView = loginView;
    }

    public LoginAndRegisterModule(RegisterView registerView) {
        this.registerView = registerView;
    }

    @PerActivity
    @Provides
    VehicleBazzarService provideLoginApiServices(Retrofit retrofit) {
        return retrofit.create(VehicleBazzarService.class);
    }

    @PerActivity
    @Provides
    LoginView provideLoginView() {
        return loginView;
    }

    @PerActivity
    @Provides
    RegisterView provideRegisterView() {
        return registerView;
    }


    @PerActivity
    @Provides
    UserModel providesUserModel() {
        return new UserModel();
    }

    @PerActivity
    @Provides
    RegisterRequestAndProfileResponses providesRegisterRequest() {
        return new RegisterRequestAndProfileResponses();
    }
}

