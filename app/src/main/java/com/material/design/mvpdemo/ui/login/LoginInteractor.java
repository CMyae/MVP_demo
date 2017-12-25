package com.material.design.mvpdemo.ui.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.material.design.mvpdemo.data.local.PrefHelper;

/**
 * Created by chan on 12/13/17.
 */

public class LoginInteractor implements LoginMvpInteractor {


    @Override
    public void onLogin(final Context context, String email, String password, final onLoginFinishListener onLoginFinishListener) {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){
                            onLoginFinishListener.onLoginFailed();

                        }else{

                            //get username from database and store for further use
                            getUsernameAndStore(context,onLoginFinishListener);
                        }
                    }
                });


    }


    /**
     * Get user name from database store in share pref
     * @param context
     * @param onLoginFinishListener
     */
    public void getUsernameAndStore(final Context context, final onLoginFinishListener onLoginFinishListener){

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser == null){
            onLoginFinishListener.onLoginFailed();
            return;
        }

        final String id = currentUser.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String username = dataSnapshot.child(id).child("userName").getValue(String.class);
                PrefHelper.saveUsernameInLocal(context,username);

                onLoginFinishListener.onLoginComplete();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onLoginFinishListener.onLoginFailed();
            }
        });
    }
}
