package com.example.sumankhatiwada.vehiclebazzar.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.sumankhatiwada.vehiclebazzar.base.BaseFragment;
import com.example.sumankhatiwada.vehiclebazzar.base.BasePresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.RegisterRequestAndProfileResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.SharedPreferenceManager;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.ProfileView;
import com.example.sumankhatiwada.vehiclebazzar.vehiclebazzarapiservices.VehicleBazzarService;


import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.observers.Observers;

/**
 * Created by sumankhatiwada on 4/22/18.
 */

public class ProfileFragmentPresenter extends BasePresenter<ProfileView>implements Observer<RegisterRequestAndProfileResponses> {

    @Inject
    VehicleBazzarService vehicleBazzarService;

    @Inject
    UserModel userModel;

    @Inject
    Context context;
    @Inject
    SharedPreferenceManager mSharedPreferenceManager;
    @Inject
    public ProfileFragmentPresenter() {
    }


    public void getMyAccount() {
        getView().onShowDialog("Showing Profile....");
        userModel = getUserModelSession();
        Observable<RegisterRequestAndProfileResponses> registerRequestAndProfileResponsesObservable = vehicleBazzarService.getMyProfile(userModel.getToken(), "application/json");
        subscribe(registerRequestAndProfileResponsesObservable,this);
    }

    public UserModel getUserModelSession() {
        mSharedPreferenceManager.initiateSharedPreferences(context);
        return mSharedPreferenceManager.getUserModelFromPreferences();
    }

    @Override
    public void onCompleted() {
        getView().onHideDialog();
    }

    @Override
    public void onError(Throwable e) {
        getView().onShowToast(e.getMessage());

    }

    @Override
    public void onNext(RegisterRequestAndProfileResponses registerRequestAndProfileResponses) {
        Log.e("String",registerRequestAndProfileResponses.getFirstname()+ "~~~~~"+registerRequestAndProfileResponses.getEmail());
        getView().onViewSuccess(registerRequestAndProfileResponses);
    }

}
