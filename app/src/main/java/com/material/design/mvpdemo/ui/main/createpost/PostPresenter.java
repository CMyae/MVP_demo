package com.material.design.mvpdemo.ui.main.createpost;

import android.net.Uri;

import com.material.design.mvpdemo.R;
import com.material.design.mvpdemo.data.network.model.User;
import com.material.design.mvpdemo.ui.main.MainActivity;
import com.material.design.mvpdemo.ui.main.model.Post;
import com.material.design.mvpdemo.utils.CommonUtils;
import com.material.design.mvpdemo.utils.NetworkUtils;

/**
 * Created by chan on 12/14/17.
 */

public class PostPresenter implements PostMvpPresenter,PostMvpInteractor.OnFinishListener {

    private CreatePostView postView;
    private PostMvpInteractor postInteractor;

    public PostPresenter(CreatePostView postView) {
        this.postView = postView;
        postInteractor = new PostInteractor();
    }


    @Override
    public void openImageChooser(MainActivity activity) {
        postView.openImageChooser();
    }

    @Override
    public void loadImagePreview(Uri imageUri) {
        postView.showImagePreview(imageUri);
    }

    @Override
    public void hideImagePreview() {
        postView.removeImagePreview();
    }

    @Override
    public void onBtnCancelClick() {
        postView.dismissDialog();
    }



    @Override
    public void onUploadPost(String postBody,Uri image, User user) {

        if(!checkIfAvailable(postBody)){
            return;
        }
        Post newPost = CommonUtils.createPostObj(postBody,user);

        postView.showProgressDialog("Uploading Post");
        postInteractor.uploadPost(newPost,image,this);
    }



    @Override
    public void onEditPost(Post previousPost,String postBody, Uri image, User user,boolean isImageUpdate) {

        if(!checkIfAvailable(postBody)){
            return;
        }


        Post editPost = CommonUtils.createPostObj(postBody,user);
        editPost.setId(previousPost.getId());
        editPost.setImageUrl(previousPost.getImageUrl());

        postView.showProgressDialog("Editing Post");
        postInteractor.editPost(isImageUpdate,editPost,image,this);

    }

    @Override
    public void onCompleted(String message) {
        postView.hideProgressDialog();
        postView.dismissDialog();
        postView.showSuccessMessage(message);
    }

    @Override
    public void onFailed() {
        postView.hideProgressDialog();
        postView.showErrorMessage(postView.getViewContext().getString(R.string.message_post_create_fail));
    }

    @Override
    public void onEditPostMode() {
        postView.showEditPostData();
    }


    /**
     * Check if post body is empty and connection available
     * @param postBody
     */
    private boolean checkIfAvailable(String postBody){
        if(postBody.isEmpty()){
            postView.showErrorMessage("Please enter something");
            return false;
        }

        if(!NetworkUtils.isNetworkConnected(postView.getViewContext())){
            postView.showErrorMessage("Connection lost");
            return false;
        }

        return true;
    }
}
