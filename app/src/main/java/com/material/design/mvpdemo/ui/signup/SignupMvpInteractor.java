package com.material.design.mvpdemo.ui.signup;

import android.content.Context;

/**
 * Created by chan on 12/13/17.
 */

public interface SignupMvpInteractor {

    interface onSignupFinishListener{
        void onSignupComplete();
        void onSignupFailed();
    }

    void onSingup(Context context, String name, String email, String password);
}
