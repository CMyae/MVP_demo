package com.material.design.mvpdemo.ui.main.createpost;

import android.net.Uri;

import com.material.design.mvpdemo.data.network.model.User;
import com.material.design.mvpdemo.ui.main.MainActivity;
import com.material.design.mvpdemo.ui.main.model.Post;

/**
 * Created by chan on 12/14/17.
 */

public interface PostMvpPresenter {

    void openImageChooser(MainActivity activity);
    void loadImagePreview(Uri uri);
    void hideImagePreview();
    void onBtnCancelClick();
    void onUploadPost(String postBody,Uri image, User user);
    void onEditPost(Post previousPost, String postBody, Uri image, User user,boolean isImageUpdate);
    void onEditPostMode();
}
