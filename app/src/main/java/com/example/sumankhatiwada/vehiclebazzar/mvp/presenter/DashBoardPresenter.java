package com.example.sumankhatiwada.vehiclebazzar.mvp.presenter;

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
    protected DashBoardPresenter() {
    }


    public void clearAllPreferences() {
        msharedPreferenceManager.clearAllPreferences();
        getView().onLogoutSuccess();
    }

}
