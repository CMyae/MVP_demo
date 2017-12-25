package com.material.design.mvpdemo.ui.main;

import com.material.design.mvpdemo.ui.main.model.Post;

import java.util.List;

/**
 * Created by chan on 12/16/17.
 */

public interface MainMvpInteractor {

    interface OnFinishListener{
        void onPostsFetched(List<Post> posts);
        void navigateToLogin();
        void onPostDeleteSuccess();
        void onPostDeleteFailed();
    }

    void getAllPosts(OnFinishListener listener);
    void singOutUser(OnFinishListener listener);
    void deletePost(Post post,OnFinishListener listener);
}
