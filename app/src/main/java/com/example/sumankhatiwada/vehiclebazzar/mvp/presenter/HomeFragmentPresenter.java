package com.example.sumankhatiwada.vehiclebazzar.mvp.presenter;

import android.content.Context;

import com.example.sumankhatiwada.vehiclebazzar.base.BasePresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CarPostResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.SharedPreferenceManager;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.HomeView;
import com.example.sumankhatiwada.vehiclebazzar.vehiclebazzarapiservices.VehicleBazzarService;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

/**
 * Created by sumankhatiwada on 4/23/18.
 */

public class HomeFragmentPresenter extends BasePresenter<HomeView> {

    @Inject
    SharedPreferenceManager msharedPreferenceManager;

    @Inject
    VehicleBazzarService vehicleBazzarService;

    @Inject
    UserModel userModel;

    @Inject
    Context mContext;

    @Inject
    public HomeFragmentPresenter() {
    }

    public void getPostOfCars() {
        getView().onShowDialog("Loading..");
        userModel = getUserModelSession();
        Observable<List<CarPostResponses>> carPostResponsesObservable = vehicleBazzarService.getAllPost(userModel.getToken(),"application/json");
        subscribe(carPostResponsesObservable, new Observer<List<CarPostResponses>>() {
            @Override
            public void onCompleted() {
                getView().onHideDialog();
            }

            @Override
            public void onError(Throwable e) {
                getView().onShowToast(e.getMessage());
                getView().onHideDialog();
                System.out.println("!!!!!!!!!"+e.getMessage());
            }

            @Override
            public void onNext(List<CarPostResponses> carPostResponses) {
                getView().onLoadPostSuccess(carPostResponses);
            }
        });
    }

    public UserModel getUserModelSession() {
        msharedPreferenceManager.initiateSharedPreferences(mContext);
        return msharedPreferenceManager.getUserModelFromPreferences();
    }
}
