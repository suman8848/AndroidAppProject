package com.example.sumankhatiwada.vehiclebazzar.mvp.view;

import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CarPostResponses;

import java.util.List;

/**
 * Created by sumankhatiwada on 4/23/18.
 */

public interface HomeView  extends BaseView{
    void onLoadPostSuccess(List<CarPostResponses> carPostResponses);
    void onShowDialog(String message);
    void onHideDialog();
    void onShowToast(String message);

}
