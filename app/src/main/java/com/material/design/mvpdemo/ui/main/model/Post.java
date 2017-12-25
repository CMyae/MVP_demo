package com.material.design.mvpdemo.ui.main.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.material.design.mvpdemo.data.network.model.User;

/**
 * Created by chan on 12/13/17.
 */

public class Post implements Parcelable{

    public static final int POST_WITH_TEXT = 0;
    public static final int POST_WITH_IMAGE = 1;

    private String id;
    private String body;
    private String date;
    private String imageUrl;
    private int type;
    private User user;


    public Post(){

    }


    protected Post(Parcel in) {
        id = in.readString();
        body = in.readString();
        date = in.readString();
        imageUrl = in.readString();
        type = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(body);
        dest.writeString(date);
        dest.writeString(imageUrl);
        dest.writeInt(type);
        dest.writeParcelable(user, flags);
    }
}
