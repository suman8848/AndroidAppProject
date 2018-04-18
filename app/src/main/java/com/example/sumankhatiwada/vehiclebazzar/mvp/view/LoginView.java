package com.example.sumankhatiwada.vehiclebazzar.mvp.view;

import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;

import javax.annotation.Nullable;

/**
 * Created by sumankhatiwada on 4/17/18.
 */

public interface LoginView extends BaseView {
    void onShowDialog(String message);
    void onHideDialog();
    void onShowToast(String message);
    void onLoginSuccess(UserModel userModel);
    void onShowSnack(@Nullable String e);
    void onLoginFailure(String message);
    void onCheckInternet(Throwable e);

}
