package com.material.design.mvpdemo.ui.base;

import android.content.Context;

/**
 * Created by chan on 12/13/17.
 */

public interface MvpView {

    void navigateToHome();
    void showInputErrors(String message);
    void showLoadingProgress(String message);
    void hideLoadingProgress();
    Context getAppContext();

}
