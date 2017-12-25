package com.material.design.mvpdemo.ui.login;

import com.material.design.mvpdemo.utils.NetworkUtils;

/**
 * Created by chan on 12/13/17.
 */

public class LoginPresenter implements LoginMvpPresenter,LoginMvpInteractor.onLoginFinishListener {

    private LoginMvpInteractor loginInteractor;
    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        loginInteractor = new LoginInteractor();
    }

    @Override
    public void onLoginBtnClick(String email, String password) {
        //check if inputs are empty
        if(email.isEmpty() || password.isEmpty()){
            loginView.showInputErrors("Enter all fields");
            return;
        }

        //if offline
        if( !NetworkUtils.isNetworkConnected(loginView.getAppContext()) ){
            loginView.showInputErrors("Connection lost");
            return;
        }

        loginView.showLoadingProgress("Signing in");
        loginInteractor.onLogin(loginView.getAppContext(),email,password,this);

    }

    @Override
    public void onCreateAccClick() {
        loginView.navigateToSignUp();
    }

    @Override
    public void onLoginComplete() {
        loginView.hideLoadingProgress();
        loginView.navigateToHome();
    }

    @Override
    public void onLoginFailed() {
        loginView.hideLoadingProgress();
        loginView.showInputErrors("Email and password not correct");
    }
}
