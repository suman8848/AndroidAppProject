package com.example.sumankhatiwada.vehiclebazzar.vehiclebazzarapiservices;

import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CarPostResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.LoginAndRegisterResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.MessageDTO;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.RegisterRequestAndProfileResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.TokenDTO;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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

    @GET("/api/auth/boat")
    Observable<List<CarPostResponses>> getAllPost(@Header("x-access-token")String token, @Header("Content-Type") String contentType);

    @POST("api/auth/boat/5a7a19d159b0d358b0bbd862/comment")
    Observable<List<CarPostResponses>> getComment(@Header("x-access-token")String token, @Header("Content-Type") String contentType);

    @POST("/api/auth/notify")
    @Headers({"Content-Type: application/json"})
    Observable<MessageDTO> savePost(@Body TokenDTO body);

}
