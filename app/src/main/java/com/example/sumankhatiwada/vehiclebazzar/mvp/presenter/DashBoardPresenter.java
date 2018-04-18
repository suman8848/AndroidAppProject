package com.example.sumankhatiwada.vehiclebazzar.mvp.presenter;

import com.example.sumankhatiwada.vehiclebazzar.base.BasePresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.SharedPreferenceManager;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.DashBoardView;

import javax.inject.Inject;

/**
 * Created by sumankhatiwada on 4/18/18.
 */

public class DashBoardPresenter extends BasePresenter<DashBoardView> {

    @Inject
    SharedPreferenceManager msharedPreferenceManager;

    @Inject
    protected DashBoardPresenter() {
    }


    public void clearAllPreferences() {
        msharedPreferenceManager.clearAllPreferences();
        getView().onLogoutSuccess();
    }
}
