package com.example.sumankhatiwada.vehiclebazzar.mvp.presenter;

import android.content.Context;

import com.example.sumankhatiwada.vehiclebazzar.base.BasePresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.RegisterRequestAndProfileResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.SharedPreferenceManager;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.DashBoardView;
import com.example.sumankhatiwada.vehiclebazzar.vehiclebazzarapiservices.VehicleBazzarService;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

/**
 * Created by sumankhatiwada on 4/18/18.
 */

public class DashBoardPresenter extends BasePresenter<DashBoardView> {

    @Inject
    SharedPreferenceManager msharedPreferenceManager;

    @Inject
    VehicleBazzarService vehicleBazzarService;

    @Inject
    UserModel userModel;

    @Inject
    Context mContext;

    @Inject
    protected DashBoardPresenter() {
    }

    public UserModel getUserModelSession() {
        msharedPreferenceManager.initiateSharedPreferences(mContext);
        return msharedPreferenceManager.getUserModelFromPreferences();
    }

    public void clearAllPreferences() {
        msharedPreferenceManager.clearAllPreferences();
        getView().onLogoutSuccess();
    }


    public void getMyAccount() {
        getView().onShowDialog("Showing Profile....");
        userModel = getUserModelSession();
        Observable<RegisterRequestAndProfileResponses> registerRequestAndProfileResponsesObservable = vehicleBazzarService.getMyProfile(userModel.getToken(), "application/json");
        subscribe(registerRequestAndProfileResponsesObservable, new Observer<RegisterRequestAndProfileResponses>() {
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
                getView().onViewSuccess(registerRequestAndProfileResponses);
            }
        });
    }


}
