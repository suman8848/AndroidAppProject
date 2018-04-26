package com.example.sumankhatiwada.vehiclebazzar.vehiclebazzarapiservices;

import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CarPostRequest;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CarPostResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CommentObject;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CommentReq;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.FcmReqRes;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.LoginAndRegisterResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.MessageDTO;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.RegisterRequestAndProfileResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.TokenDTO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PUT;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by sumankhatiwada on 4/17/18.
 */

public interface VehicleBazzarService {

    @POST("/api/auth/login")
    @FormUrlEncoded
    Observable<LoginAndRegisterResponses> checkUserLogin(@Field("email") String email, @Field("password") String password);

    @POST("/api/auth/register")
    Observable<LoginAndRegisterResponses> registerUser(@Body RegisterRequestAndProfileResponses requestRegister);

    @GET("/api/auth/me")
    Observable<RegisterRequestAndProfileResponses> getMyProfile(@Header("x-access-token")String token, @Header("Content-Type") String contentType);

    @GET("/api/auth/boat?limit=9")
    Observable<List<CarPostResponses>> getAllPost(@Header("x-access-token")String token, @Header("Content-Type") String contentType);


    @POST("/api/auth/notify")
    @Headers({"Content-Type: application/json"})
    Observable<MessageDTO> savePost(@Body TokenDTO body);

    @POST
    Observable<CarPostResponses> comment(@Url String fullUrl, @Body CommentReq comment, @Header("x-access-token") String token, @Header("Content-Type") String contentType);

    @Multipart
    @Headers({"Content-Type: multipart/form-data"})
    @POST
    Observable<CarPostResponses> sendImage(@Url String fullUrl,
                                           @Part MultipartBody.Part filePart,
                                           @Header("x-access-token") String token);


    @POST("/api/auth/boat")
    Observable<CarPostRequest> addPost(@Header("x-access-token") String token, @Header("Content-Type") String s, @Body CarPostRequest carPostResponses);

    @PUT("/api/auth/fcmtoken")
    Observable<FcmReqRes> sendFcm(@Header("x-access-token") String token, @Header("Content-Type") String contentType, @Body FcmReqRes fcmReqRes);
//    Observable<CarPostResponses> addPost(@Header("x-access-token") String token, @Header("Content-Type") String s, @Body CarPostRequest carPostResponses);
}
