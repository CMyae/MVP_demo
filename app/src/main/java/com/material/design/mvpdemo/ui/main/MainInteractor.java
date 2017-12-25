package com.material.design.mvpdemo.ui.main;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.material.design.mvpdemo.data.network.FirebaseHelper;
import com.material.design.mvpdemo.data.network.model.User;
import com.material.design.mvpdemo.ui.main.model.Post;
import com.material.design.mvpdemo.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by chan on 12/16/17.
 */

public class MainInteractor implements MainMvpInteractor {


    @Override
    public void getAllPosts(final OnFinishListener listener) {

        final List<Post> allPosts = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("All_Posts");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clear existing data
                allPosts.clear();

                for(DataSnapshot data: dataSnapshot.getChildren()){

                    String id = data.getKey().toString();
                    String body = data.child("body").getValue(String.class);
                    String date = data.child("date").getValue(String.class);
                    String imageUrl = data.child("imageUrl").getValue(String.class);
                    User user = data.child("user").getValue(User.class);

                    Post post = new Post();
                    post.setId(id);
                    post.setBody(body);
                    post.setDate(date);
                    post.setImageUrl(imageUrl);
                    post.setType(imageUrl==null ? Post.POST_WITH_TEXT : Post.POST_WITH_IMAGE);
                    post.setUser(user);

                    allPosts.add(post);

                }

                //sorts by date
                Collections.sort(allPosts, new Comparator<Post>() {
                    @Override
                    public int compare(Post a, Post b) {
                        Date d1 = CommonUtils.getDate(a.getDate());
                        Date d2 = CommonUtils.getDate(b.getDate());
                        return d2.compareTo(d1);
                    }
                });

                listener.onPostsFetched(allPosts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }



    @Override
    public void singOutUser(final OnFinishListener listener) {

        FirebaseAuth.getInstance().signOut();
        listener.navigateToLogin();

    }


    @Override
    public void deletePost(final Post post, final OnFinishListener listener) {

        DatabaseReference ref = FirebaseHelper.getPostDbReference();
        ref.child(post.getId()).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                listener.onPostDeleteSuccess();
                if(post.getImageUrl() != null) {
                    FirebaseHelper.deleteExistingImage(post.getImageUrl());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onPostDeleteFailed();
            }
        });

    }
}
