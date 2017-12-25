package com.material.design.mvpdemo.ui.main.createpost;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.material.design.mvpdemo.R;
import com.material.design.mvpdemo.data.network.model.User;
import com.material.design.mvpdemo.ui.custom.BorderDashView;
import com.material.design.mvpdemo.ui.custom.RoundIconView;
import com.material.design.mvpdemo.ui.main.MainActivity;
import com.material.design.mvpdemo.ui.main.listener.OnDialogListener;
import com.material.design.mvpdemo.ui.main.model.Post;
import com.material.design.mvpdemo.utils.Constants;
import com.material.design.mvpdemo.utils.ViewUtils;

/**
 * Created by chan on 12/13/17.
 */

public class CreatePostDialog extends DialogFragment implements View.OnClickListener, CreatePostView {

    private EditText etPostBody;
    private PostPresenter presenter;
    private ImageView imgPreview;
    private ImageView imgCamera;
    private RoundIconView imgRemove;
    private Uri imageUri;
    private ProgressDialog dialog;
    private ProgressBar imageLoader;

    private User currentUser;
    private Post currentPost;
    private boolean isEdit;
    private TextView tvTitle;
    private TextView btnUpload;

    private boolean isImageUpdate;
    private OnDialogListener dialogListener;



    public static CreatePostDialog getDialogInstance(User user, boolean isEdit, Post currentPost){

        CreatePostDialog dialog = new CreatePostDialog();
        Bundle bdn = new Bundle();
        bdn.putParcelable("USER",user);
        bdn.putBoolean("IS_EDIT",isEdit);
        bdn.putParcelable("CURRENT_POST",currentPost);
        dialog.setArguments(bdn);
        return dialog;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dialogListener = (OnDialogListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Light_Dialog_Alert);
        setCancelable(false);
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_create_post, container);

        Bundle bdn = getArguments();
        if(bdn != null){
            this.currentUser = bdn.getParcelable("USER");
            this.currentPost = bdn.getParcelable("CURRENT_POST");
            isEdit = bdn.getBoolean("IS_EDIT");
        }

        presenter = new PostPresenter(this);

        imageLoader = (ProgressBar) view.findViewById(R.id.imageLoader);

        BorderDashView imageAddView = (BorderDashView) view.findViewById(R.id.imageAddView);
        TextView btnCancel = (TextView) view.findViewById(R.id.btnCancel);
        btnUpload = (TextView) view.findViewById(R.id.btnUpload);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        imgRemove = (RoundIconView) view.findViewById(R.id.imgRemove);
        imgPreview = (ImageView) view.findViewById(R.id.imgPreview);
        imgCamera = (ImageView) view.findViewById(R.id.imgCamera);
        etPostBody = (EditText) view.findViewById(R.id.text_input_postbody);


        btnUpload.setBackground(ViewUtils.getStateListDrawable(
                Constants.colorNormal, Constants.colorPress));

        btnCancel.setBackground(ViewUtils.getStrokeDrawable(Constants.colorNormal));

        imageLoader.setVisibility(View.GONE);

        if(isEdit){
            presenter.onEditPostMode();
        }

        imageAddView.setOnClickListener(this);
        imgRemove.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnUpload.setOnClickListener(this);



        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnCancel:
                presenter.onBtnCancelClick();
                break;

            case R.id.btnUpload:

                String bodyText = etPostBody.getText().toString().trim();

                if(!isEdit) {
                    //upload new post
                    presenter.onUploadPost(bodyText, imageUri, currentUser); //send body and uri

                }else{
                    //edit post
                    presenter.onEditPost(currentPost,bodyText,imageUri,currentUser,isImageUpdate);
                }
                break;


            case R.id.imageAddView:
                presenter.openImageChooser((MainActivity) getActivity());
                break;

            case R.id.imgRemove:
                presenter.hideImagePreview();
                break;
        }
    }

    @Override
    public void dismissDialog() {
        getDialog().dismiss();
        if(dialogListener != null){
            dialogListener.onDialogDismiss();
        }
    }


    @Override
    public void showProgressDialog(String message) {
        dialog = new ProgressDialog(getContext(),ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.show();
    }


    @Override
    public void hideProgressDialog() {
        if(dialog != null){
            dialog.dismiss();
        }
    }

    /**
     * Show image preview when user select image from storage
     * @param imgUri
     */
    @Override
    public void showImagePreview(final Uri imgUri) {

        if (imgUri == null) return;

        imageUri = imgUri;
        isImageUpdate = true;
        imgPreview.setVisibility(View.VISIBLE);
        imageLoader.setVisibility(View.VISIBLE);
        imgCamera.setVisibility(View.GONE);

        Glide.with(this)
                .load(imgUri)
                .listener(new RequestListener<Uri, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                        imageLoader.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        imageLoader.setVisibility(View.GONE);
                        imgRemove.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(imgPreview);

    }

    @Override
    public void showEditPostData() {

        tvTitle.setText("Edit Post");
        btnUpload.setText("EDIT");
        etPostBody.setText(currentPost.getBody());

        if(currentPost.getImageUrl() != null) {

            imgPreview.setVisibility(View.VISIBLE);
            imageLoader.setVisibility(View.VISIBLE);
            imgCamera.setVisibility(View.GONE);

            Glide.with(this)
                    .load(currentPost.getImageUrl())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            imageLoader.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            imageLoader.setVisibility(View.GONE);
                            imgRemove.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(imgPreview);
        }
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void removeImagePreview() {
        imgPreview.setVisibility(View.GONE);
        imgRemove.setVisibility(View.GONE);
        imgCamera.setVisibility(View.VISIBLE);
        imageLoader.setVisibility(View.GONE);
        imageUri = null;
        isImageUpdate = true;
    }


    @Override
    public void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.SELECT_PICTURE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constants.SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
            presenter.loadImagePreview(data.getData());
        }
    }
}
