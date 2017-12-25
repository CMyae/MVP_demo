package com.material.design.mvpdemo.ui.login;

import android.content.Context;

/**
 * Created by chan on 12/13/17.
 */

public interface LoginMvpInteractor {

    interface onLoginFinishListener{
        void onLoginComplete();
        void onLoginFailed();
    }

    void onLogin(Context context,String email, String password, onLoginFinishListener onLoginFinishListener);
}
