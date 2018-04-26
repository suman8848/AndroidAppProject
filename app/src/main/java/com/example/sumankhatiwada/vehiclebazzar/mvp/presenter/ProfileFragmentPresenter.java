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

public class ProfileFragmentPresenter extends BasePresenter<ProfileView> {

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


    public void updatePhoneNo() {

    }
}
