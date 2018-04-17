package com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class UserModel implements Parcelable {

    public String userPassword;
    public String userEmail;
    public String uid;
    public String name;


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
        userPassword = parcel.readString();
        userEmail = parcel.readString();
        uid = parcel.readString();
        name = parcel.readString();


    }


    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeString(userPassword);
        parcel.writeString(userEmail);
        parcel.writeString(uid);
        parcel.writeString(name);

    }

    @Override
    public int describeContents() {
        return 0;
    }
}
