package com.material.design.mvpdemo.ui.signup;

/**
 * Created by chan on 12/13/17.
 */

public interface SignupMvpPresenter {
    void onSignupBtnClick(String name,String email,String password);
    void onTvLoginClick();
}
