package com.material.design.mvpdemo.ui.main;

import com.material.design.mvpdemo.ui.main.model.Post;
import com.material.design.mvpdemo.utils.NetworkUtils;

import java.util.List;

/**
 * Created by chan on 12/13/17.
 */

public class MainPresenter implements MainMvpPresenter,MainMvpInteractor.OnFinishListener {

    private MainView mainView;
    private MainMvpInteractor mainInteractor;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        mainInteractor = new MainInteractor();
    }

    @Override
    public void onLogoutMenuClick() {
        if(NetworkUtils.isNetworkConnected(mainView.getAppContext())) {
            mainInteractor.singOutUser(this);
        }
    }

    @Override
    public void onActionBtnClick(int[] location) {
        mainView.showPopupMenu(location);
    }

    @Override
    public void onPopupMenuClose() {
        mainView.hidePopupMenu();
    }


    @Override
    public void onDeleteMenuClick(Post post) {
        if(!NetworkUtils.isNetworkConnected(mainView.getAppContext())){
            mainView.showMessage("Connection lost");
            return;
        }
        mainInteractor.deletePost(post,this);
    }


    @Override
    public void onCreatePost() {
        mainView.showCreatePostDialog();
    }


    @Override
    public void onEditPost() {
        mainView.showEditPostDialog();
    }

    @Override
    public void getNetworkData() {
        //if no internet connection,send back no data
        if(!NetworkUtils.isNetworkConnected(mainView.getAppContext())){
            mainView.showMessage("No Connection");
            return;
        }
        mainInteractor.getAllPosts(this);
    }

    @Override
    public void onPostsFetched(List<Post> posts) {
        mainView.showAvailablePosts(posts);
    }

    @Override
    public void navigateToLogin() {
        mainView.navigateToLogin();
    }


    @Override
    public void onPostDeleteSuccess() {
        mainView.showMessage("Deleted");
        mainView.hidePopupMenu();
    }

    @Override
    public void onPostDeleteFailed() {
        mainView.showMessage("Error deleting post");
        mainView.hidePopupMenu();
    }
}
