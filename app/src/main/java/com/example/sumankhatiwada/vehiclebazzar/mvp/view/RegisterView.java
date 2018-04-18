package com.example.sumankhatiwada.vehiclebazzar.mvp.view;

import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;

/**
 * Created by sumankhatiwada on 4/17/18.
 */

public interface RegisterView extends BaseView {

    void onRegisterSuccess(UserModel userModel);
    void onRegisterFailure(String error);
    void onShowToast(String message);
    void onShowDialog(String message);
    void onHideDialog();
    void onCheckInternet(Throwable e);

}
