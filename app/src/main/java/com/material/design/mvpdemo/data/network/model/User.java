package com.material.design.mvpdemo.data.network.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chan on 12/16/17.
 */

public class User implements Parcelable{

    private String id;
    private String userName;
    private String email;

    public User(){

    }

    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public User(String id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

    protected User(Parcel in) {
        id = in.readString();
        userName = in.readString();
        email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userName);
        dest.writeString(email);
    }
}
