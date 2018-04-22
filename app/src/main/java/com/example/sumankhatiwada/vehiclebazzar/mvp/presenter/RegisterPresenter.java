package com.example.sumankhatiwada.vehiclebazzar.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.sumankhatiwada.vehiclebazzar.base.BasePresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.Address;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.LoginAndRegisterResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.RegisterRequestAndProfileResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.SharedPreferenceManager;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.RegisterView;
import com.example.sumankhatiwada.vehiclebazzar.vehiclebazzarapiservices.VehicleBazzarService;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

/**
 * Created by sumankhatiwada on 4/18/18.
 */

public class RegisterPresenter extends BasePresenter<RegisterView> {

    @Inject
    VehicleBazzarService mRegisterApiServices;

    @Inject
    UserModel userModel;

    @Inject
    Context mContext;

    @Inject
    RegisterRequestAndProfileResponses mRegisterRequest;

    @Inject
    SharedPreferenceManager msharedPreferenceManager;

    @Inject
    public RegisterPresenter() {

    }

    public void getRegisterResponse(String firstName, String lastName, String emailAddress, String password) {
        getView().onShowDialog("Registering user");
        mRegisterRequest.setFirstname(firstName);
        mRegisterRequest.setLastname(lastName);
        mRegisterRequest.setEmail(emailAddress);
        mRegisterRequest.setPassword(password);
        Address address = new Address("fairfied", "test", "test", 123);
        mRegisterRequest.setAddress(address);
        Observable<LoginAndRegisterResponses> registerResponsesObservable = mRegisterApiServices.registerUser(mRegisterRequest);
        subscribe(registerResponsesObservable, new Observer<LoginAndRegisterResponses>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().onRegisterFailure(e.getMessage());
            }

            @Override
            public void onNext(LoginAndRegisterResponses registerResponses) {
                userModel.setToken(registerResponses.getToken());
                userModel.setAuth(registerResponses.getAuth());
                saveUserModelSession(userModel);
                getView().onRegisterSuccess(userModel);
                getView().onShowToast("Registered Successfully");
                Log.e("helloregister", registerResponses.getToken());
            }
        });
    }

    private void saveUserModelSession(UserModel mUserModel) {
        msharedPreferenceManager.initiateSharedPreferences(mContext);
        msharedPreferenceManager.saveUserModel(mUserModel);
    }
}
