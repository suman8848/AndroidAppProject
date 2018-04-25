package com.example.sumankhatiwada.vehiclebazzar.mvp.presenter;

import android.content.Context;

import com.example.sumankhatiwada.vehiclebazzar.base.BasePresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CarPostResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CommentObject;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CommentReq;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.MessageDTO;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.RegisterRequestAndProfileResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.TokenDTO;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.SharedPreferenceManager;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.DashBoardView;
import com.example.sumankhatiwada.vehiclebazzar.vehiclebazzarapiservices.VehicleBazzarService;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    CommentObject comment;


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

    public void comment(String id,String commentBody) {
        getView().onShowDialog("Posting Comment");
        System.out.println("ID------->"+ id);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        CommentObject co = new CommentObject();
        co.setBody(commentBody);
        co.setDate(formatter.format(date));
        co.setUser("test");
        CommentReq commentReq = new CommentReq();
        commentReq.setComments(co);
        String fullUrl = "https://ancient-hamlet-60512.herokuapp.com/api/auth/boat/"+id+"/comment";
        System.out.println("URL---->"+fullUrl+":::::"+ comment +"9999>"+userModel.getToken());
        Observable<CarPostResponses> setComment = vehicleBazzarService.comment(fullUrl,commentReq,userModel.getToken(),"application/json");
        subscribe(setComment, new Observer<CarPostResponses>() {
            @Override
            public void onCompleted() {
                getView().onHideDialog();
            }

            @Override
            public void onError(Throwable e) {
                getView().onHideDialog();
                System.out.println("FORBIDDEN"+ e.getMessage());
                getView().onShowToast(e.getMessage());
            }

            @Override
            public void onNext(CarPostResponses carPostResponses) {
                getView().onHideDialog();
               // System.out.println("RESPONSECARE------->"+carPostResponses.getComments().get(0).getBody());
                getView().onCommentSuccess();
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
