package com.material.design.mvpdemo.ui.main;

import com.material.design.mvpdemo.ui.main.model.Post;

/**
 * Created by chan on 12/13/17.
 */

public interface MainMvpPresenter {

    void onLogoutMenuClick();
    void onActionBtnClick(int[] location);
    void onPopupMenuClose();
    void onDeleteMenuClick(Post post);
    void onCreatePost();
    void onEditPost();
    void getNetworkData();
}
