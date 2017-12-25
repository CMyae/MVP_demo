package com.material.design.mvpdemo.ui.signup;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.material.design.mvpdemo.data.local.PrefHelper;
import com.material.design.mvpdemo.data.network.model.User;

/**
 * Created by chan on 12/13/17.
 */

public class SignupInteractor implements SignupMvpInteractor {

    //change code efficiently later

    private onSignupFinishListener onSignupFinishListener;

    public SignupInteractor(SignupMvpInteractor.onSignupFinishListener onSingupFinishListener) {
        this.onSignupFinishListener = onSingupFinishListener;
    }

    @Override
    public void onSingup(final Context context, final String name, String email, String password) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    createNewUser(context,task.getResult().getUser(),name);
                }else{
                    onSignupFinishListener.onSignupFailed();
                }
            }
        });
    }


    public void createNewUser(final Context context, FirebaseUser user, String name){

        final String username = name;
        String email = user.getEmail();
        String userId = user.getUid();

        User newUser = new User(username,email);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(userId).setValue(newUser, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if(databaseError == null) {
                    //store user name in share pref
                    PrefHelper.saveUsernameInLocal(context,username);
                    onSignupFinishListener.onSignupComplete();
                }
            }
        });
    }

}
