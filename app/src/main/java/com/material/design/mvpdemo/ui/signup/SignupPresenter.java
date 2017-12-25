package com.material.design.mvpdemo.ui.signup;

import com.material.design.mvpdemo.utils.NetworkUtils;

/**
 * Created by chan on 12/13/17.
 */

public class SignupPresenter implements SignupMvpPresenter,SignupMvpInteractor.onSignupFinishListener {

    private SignupView singupView;
    private SignupMvpInteractor singupInteractor;

    public SignupPresenter(SignupView singupView) {
        this.singupView = singupView;
        singupInteractor = new SignupInteractor(this);
    }

    @Override
    public void onSignupBtnClick(String name, String email, String password) {

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
            singupView.showInputErrors("Enter all fields");
            return;
        }

        if(password.length() < 8 ){
            singupView.showInputErrors("Password must be at least 8 characters");
            return;
        }


        //if offline
        if( !NetworkUtils.isNetworkConnected(singupView.getAppContext()) ){
            singupView.showInputErrors("Connection lost");
            return;
        }


        singupView.showLoadingProgress("Creating New Account..");
        singupInteractor.onSingup(singupView.getAppContext(),name,email,password);

    }

    @Override
    public void onTvLoginClick() {
        singupView.navigateToLogin();
    }


    @Override
    public void onSignupComplete() {
        singupView.hideLoadingProgress();
        singupView.navigateToHome();
    }

    @Override
    public void onSignupFailed() {
        singupView.hideLoadingProgress();
        singupView.showInputErrors("Please fill correctly");
    }
}
