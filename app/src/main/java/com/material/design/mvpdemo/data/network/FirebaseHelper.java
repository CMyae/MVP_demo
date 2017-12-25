package com.material.design.mvpdemo.data.network;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by chan on 12/14/17.
 */

public class FirebaseHelper {

    private static final String POST_TABLE = "All_Posts";


    public static DatabaseReference getPostDbReference(){
        return FirebaseDatabase.getInstance().getReference(POST_TABLE);
    }

    public static void deleteExistingImage(String url){
        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        ref.delete();
    }


}
