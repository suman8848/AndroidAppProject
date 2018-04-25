package com.example.sumankhatiwada.vehiclebazzar.mvp.presenter;

import android.content.Context;

import com.example.sumankhatiwada.vehiclebazzar.base.BasePresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.Comment;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.MessageDTO;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.NotificationRequest;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.RegisterRequestAndProfileResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.TokenDTO;
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
    NotificationRequest notificationRequest;

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
        getView().onShowDialog("Loading....");
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

    public void sendNotification(String value,String token) {
        getView().onShowDialog("Loading....");
        userModel = getUserModelSession();
        TokenDTO tokenDTO = new TokenDTO(token);
        Observable<MessageDTO> registerRequestAndProfileResponsesObservable = vehicleBazzarService.savePost(tokenDTO);
        subscribe(registerRequestAndProfileResponsesObservable, new Observer<MessageDTO>() {
            @Override
            public void onCompleted() {
                getView().onHideDialog();
            }

            @Override
            public void onError(Throwable e) {
                getView().onShowToast(e.getMessage());
                System.out.println("ERRROORRRR__------------->"+e.getMessage());
                getView().onHideDialog();
            }

            @Override
            public void onNext(MessageDTO messageDTO) {
                getView().onNotifiedSuccess(messageDTO);
            }
        });
    }


//    public void sendComment() {
//        getView().onShowDialog("Loading....");
//        userModel = getUserModelSession();
//        Observable<Comment> registerRequestAndProfileResponsesObservable = vehicleBazzarService.get(userModel.getToken(), "application/json");
//        subscribe(registerRequestAndProfileResponsesObservable, new Observer<RegisterRequestAndProfileResponses>() {
//            @Override
//            public void onCompleted() {
//                getView().onHideDialog();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                getView().onShowToast(e.getMessage());
//            }
//
//            @Override
//            public void onNext(RegisterRequestAndProfileResponses registerRequestAndProfileResponses) {
//                getView().onViewSuccess(registerRequestAndProfileResponses);
//            }
//        });
//    }
}
