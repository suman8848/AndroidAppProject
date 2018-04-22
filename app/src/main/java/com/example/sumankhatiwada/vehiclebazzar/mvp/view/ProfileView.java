package com.example.sumankhatiwada.vehiclebazzar.mvp.view;

import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.RegisterRequestAndProfileResponses;

/**
 * Created by sumankhatiwada on 4/22/18.
 */

public interface ProfileView extends BaseView {

    void onViewSuccess(RegisterRequestAndProfileResponses registerRequestAndProfileResponses);
    void onShowDialog(String message);
    void onHideDialog();
    void onShowToast(String message);
}
