package com.example.sumankhatiwada.vehiclebazzar.mvp.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.example.sumankhatiwada.vehiclebazzar.base.BasePresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.Address;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CarPostRequest;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    CarPostRequest carPostRequest;

    @Inject
    protected DashBoardPresenter() {
    }

    public UserModel getUserModelSession() {
        msharedPreferenceManager.initiateSharedPreferences(mContext);
        return msharedPreferenceManager.getUserModelFromPreferences();
    }
    public void saveUserModelSession(UserModel mUserModel) {
        msharedPreferenceManager.initiateSharedPreferences(mContext);
        msharedPreferenceManager.saveUserModel(mUserModel);
    }

    public void clearAllPreferences() {
        msharedPreferenceManager.clearAllPreferences();
        getView().onLogoutSuccess();
    }

    private String userName = "";

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
                userModel.setName(registerRequestAndProfileResponses.getFirstname() + " "+ registerRequestAndProfileResponses.getLastname());
                saveUserModelSession(userModel);
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
        co.setUser(userModel.getName());
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

    public void sendPost(String carName, String carMakeYear, String carModel, String carColor, String carMileage, String carPrice, final Bitmap bitmap) {

        getView().onShowDialog("Adding post");

        carPostRequest.setName(carName);
        carPostRequest.setMake(carMakeYear);
        carPostRequest.setColor(carColor);
        carPostRequest.setMileage(carMileage);
        carPostRequest.setPrice(Integer.parseInt(carPrice));
        carPostRequest.setModel(carModel);
        carPostRequest.setDescription("Hello Descriptions");
        Address address = new Address("fairfied", "test", "test", 123);
        carPostRequest.setAddress(address);
        carPostRequest.setCategories("Sports");
        carPostRequest.setStatus(0);
        String imgArr [] ={"sdfghjk"};
        carPostRequest.setBoatImage(imgArr);
        final Observable<CarPostResponses> carPostRequestResponsesObservable = vehicleBazzarService.addPost(userModel.getToken(), "application/json", carPostRequest);
        subscribe(carPostRequestResponsesObservable, new Observer<CarPostResponses>() {
            @Override
            public void onCompleted() {
                getView().onHideDialog();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("ADDING ERROR"+ e.getMessage());
            }

            @Override
            public void onNext(CarPostResponses carPostResponses) {
                System.out.println("CAR----->>>"+ carPostResponses.getId());

                /*Bitmap thumbnail = (Bitmap) data.getExtras().get("data");*/
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES),"temp.jpg");
                FileOutputStream fo;
                try {
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(destination.getAbsolutePath());

                /*RequestBody.create(MediaType.parse("multipart/form-data"),destination.getAbsolutePath()),*/

                String fullUrl = "https://ancient-hamlet-60512.herokuapp.com/api/auth/boat/"+carPostResponses.getId()+"/uploadImage";

                File file = new File(destination.getAbsolutePath());

                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"),file);
                //MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file",
                        file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

                Observable<CarPostResponses> sendImage = vehicleBazzarService.sendImage(
                        fullUrl,
                        filePart,
                        userModel.getToken());
                subscribe(sendImage, new Observer<CarPostResponses>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("ADDING ERROR"+ e.getMessage());
                    }

                    @Override
                    public void onNext(CarPostResponses carPostResponses) {
                        System.out.println("On next");
                    }
                });
               // new uploadFileToServerTask().execute();

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
