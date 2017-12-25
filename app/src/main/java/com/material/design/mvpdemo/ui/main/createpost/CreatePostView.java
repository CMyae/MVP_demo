package com.material.design.mvpdemo.ui.main.createpost;

import android.content.Context;
import android.net.Uri;

/**
 * Created by chan on 12/14/17.
 */

public interface CreatePostView{

    Context getViewContext();
    void dismissDialog();
    void showProgressDialog(String message);
    void hideProgressDialog();
    void showErrorMessage(String message);
    void showSuccessMessage(String message);
    void showImagePreview(Uri imgUri);
    void removeImagePreview();
    void openImageChooser();
    void showEditPostData();

}
