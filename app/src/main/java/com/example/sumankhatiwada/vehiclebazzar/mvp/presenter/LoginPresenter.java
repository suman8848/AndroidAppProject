package com.example.sumankhatiwada.vehiclebazzar.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.sumankhatiwada.vehiclebazzar.base.BasePresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.LoginAndRegisterResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.SharedPreferenceManager;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.LoginView;
import com.example.sumankhatiwada.vehiclebazzar.vehiclebazzarapiservices.VehicleBazzarService;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;

/**
 * Created by sumankhatiwada on 4/17/18.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    @Inject
    protected VehicleBazzarService vehicleBazzarService;

    @Inject
    UserModel mUserModel;

    @Inject
    Context mContext;

    @Inject
    SharedPreferenceManager msharedPreferenceManager;

    @Inject
    protected LoginPresenter() {

    }


    public void getLoginResponse(String username, String password) {
        // getView().onShowSnack();
        getView().onShowDialog("Logging in...");

        Observable<LoginAndRegisterResponses> loginResponsesObservable = vehicleBazzarService.checkUserLogin(username, password);
        subscribe(loginResponsesObservable, new Observer<LoginAndRegisterResponses>() {
            @Override
            public void onCompleted() {
                getView().onHideDialog();
            }

            @Override
            public void onError(Throwable e) {
                getView().onHideDialog();
                getView().onShowSnack(((HttpException) e).code() + "");
            }
            @Override
            public void onNext(LoginAndRegisterResponses loginResponses) {
                Log.e("Login", loginResponses.getToken() + loginResponses.getAuth());

                if (loginResponses.getToken() != null && loginResponses.getAuth() != null) {
                    mUserModel.setAuth(loginResponses.getAuth());
                    mUserModel.setToken(loginResponses.getToken());
                    saveUserModelSession(mUserModel);
                    getView().onLoginSuccess(mUserModel);
                } else {
                    getView().onLoginFailure("Cannot Logged In..");
                }
            }
        });
    }

    public UserModel getUserModelSession() {
        msharedPreferenceManager.initiateSharedPreferences(mContext);
        return msharedPreferenceManager.getUserModelFromPreferences();
    }
    public void saveUserModelSession(UserModel mUserModel) {
        msharedPreferenceManager.initiateSharedPreferences(mContext);
        msharedPreferenceManager.saveUserModel(mUserModel);
    }

}
