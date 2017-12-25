package com.material.design.mvpdemo.ui.main.createpost;

import android.net.Uri;

import com.material.design.mvpdemo.ui.main.model.Post;

/**
 * Created by chan on 12/15/17.
 */

public interface PostMvpInteractor {

    interface OnFinishListener{
        void onCompleted(String message);
        void onFailed();
    }

    void uploadPost(Post post, Uri image, OnFinishListener listener);
    void editPost(boolean isImageUpdate,Post post, Uri image, OnFinishListener listener);
}
