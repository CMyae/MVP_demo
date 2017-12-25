package com.material.design.mvpdemo.utils;

import android.content.Context;
import android.graphics.Typeface;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.material.design.mvpdemo.data.local.PrefHelper;
import com.material.design.mvpdemo.data.network.model.User;
import com.material.design.mvpdemo.ui.main.model.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chan on 12/13/17.
 */

public class CommonUtils {


    public static Typeface getHeaderTypeface(Context context){
        return Typeface.createFromAsset(context.getAssets(),"typeface/Caviar_Dreams_Bold.ttf");
    }


    public static User getCurrentUser(Context context){

        User currentUser = new User();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            currentUser.setId(user.getUid());
            currentUser.setEmail(user.getEmail());
            currentUser.setUserName(PrefHelper.getLoginUserName(context));
        }
        return currentUser;

    }



    public static Post createPostObj(String body, User user){
        Post post = new Post();
        post.setBody(body);
        post.setType(Post.POST_WITH_TEXT);
        post.setDate(getTimeStamp());
        post.setUser(user);
        return post;
    }


    public static String getTimeStamp(){
        return new SimpleDateFormat(Constants.DATE_PATTERN).format(new Date());
    }


    public static Date getDate(String date){

        try {
            return new SimpleDateFormat(Constants.DATE_PATTERN).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null; //just in case,not to be null to avoid nullPointerException
    }


}
