package com.material.design.mvpdemo.ui.main;

import com.material.design.mvpdemo.ui.base.MvpView;
import com.material.design.mvpdemo.ui.main.model.Post;

import java.util.List;

/**
 * Created by chan on 12/13/17.
 */

public interface MainView extends MvpView{

    void showCreatePostDialog();
    void showEditPostDialog();
    void showPopupMenu(int[] location);
    void hidePopupMenu();
    void showAvailablePosts(List<Post> newData);
    void navigateToLogin();
    void showMessage(String message);
}
