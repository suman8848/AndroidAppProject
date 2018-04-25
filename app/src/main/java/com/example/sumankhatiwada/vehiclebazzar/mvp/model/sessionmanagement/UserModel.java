package com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class UserModel implements Parcelable {

    private boolean auth;
    private String token;
    private String name;

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {

        @Override
        public UserModel createFromParcel(Parcel parcel) {
            return new UserModel(parcel);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public UserModel() {

    }

    private UserModel(Parcel parcel) {
        auth = parcel.readByte() != 0; //myBoolean == true if byte != 0
        token = parcel.readString();
        name = parcel.readString();


    }


    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeByte((byte) (auth ? 1 : 0));  //if myBoolean == true, byte == 1
        parcel.writeString(token);
        parcel.writeString(name);

    }

    @Override
    public int describeContents() {
        return 0;
    }
}
