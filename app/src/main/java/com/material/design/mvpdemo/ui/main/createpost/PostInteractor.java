package com.material.design.mvpdemo.ui.main.createpost;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.material.design.mvpdemo.data.network.FirebaseHelper;
import com.material.design.mvpdemo.ui.main.model.Post;

import java.util.UUID;

/**
 * Created by chan on 12/15/17.
 */

public class PostInteractor implements PostMvpInteractor {


    /**
     * If image is not null, upload image first to storage,then get url of image
     * and store post data to db.
     * If image is null, only store post data in db
     * @param post
     * @param image
     * @param listener
     */
    @Override
    public void uploadPost(final Post post, final Uri image, final OnFinishListener listener) {

        //upload image
        if(image != null){

            //create random image file name
            final String imgName = UUID.randomUUID().toString();

            final StorageReference ref = FirebaseStorage.getInstance().getReference("upload_images");

            ref.child(imgName).putFile(image)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //get image url of uploaded image and store post data in database
                            ref.child(imgName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String imageUrl = uri.toString();

                                    storePostDataInDB(post,imageUrl,listener);
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onFailed();
                        }
                    });


        }else{
            //image is null,store post data only
            storePostDataInDB(post,null,listener);
        }

    }


    /**
     * Post editing has three conditions.
     * -User change text and update image
     * -User only change text
     * -User change text and also remove image
     * @param post
     * @param image
     * @param listener
     */
    @Override
    public void editPost(boolean isImageUpdate,final Post post, Uri image, final OnFinishListener listener) {

        if(image != null && isImageUpdate){ // change text and update image

            final String imgName = UUID.randomUUID().toString();

            final StorageReference ref = FirebaseStorage.getInstance().getReference("upload_images");
            ref.child(imgName).putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.child(imgName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            editPostDateInDB(post,url,true,listener);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    listener.onFailed();
                }
            });




        }if(image == null && !isImageUpdate){ //don't need to update image,just only text
            editPostDateInDB(post,post.getImageUrl(),false,listener);



        }else if(image == null && isImageUpdate){ //change text and remove image
            editPostDateInDB(post,null,true,listener);
        }

    }



    public void storePostDataInDB(Post post, String imgUrl, final OnFinishListener listener){

        post.setImageUrl(imgUrl);

        DatabaseReference databaseReference = FirebaseHelper.getPostDbReference();
        String id = databaseReference.push().getKey();

        databaseReference.child(id).setValue(post, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference databaseReference) {
                if(error == null){
                    listener.onCompleted("Successfully Uploaded");
                }else{
                    listener.onFailed();
                }
            }
        });
    }


    public void editPostDateInDB(Post post, String newImgUrl, final boolean isImageUpdate, final OnFinishListener listener){

        final String deleteImageUrl = post.getImageUrl();

        if(isImageUpdate) {
            post.setImageUrl(newImgUrl);
        }

        String id = post.getId();
        post.setId(null); //don't need to insert repeat id into database

        DatabaseReference ref = FirebaseHelper.getPostDbReference();
        ref.child(id).setValue(post, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference databaseReference) {
                if(error == null){
                    listener.onCompleted("Edited Post");

                    if(deleteImageUrl != null && isImageUpdate) {
                        FirebaseHelper.deleteExistingImage(deleteImageUrl);
                    }

                }else{
                    listener.onFailed();
                }
            }
        });
    }



}
