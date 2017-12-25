package com.material.design.mvpdemo.ui.main.holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.material.design.mvpdemo.R;

/**
 * Created by chan on 12/13/17.
 */

public class PostWithImageViewHolder extends PostWithTextViewHolder {

    public ImageView imgThumbnail;

    public PostWithImageViewHolder(View itemView) {
        super(itemView);

        imgThumbnail = (ImageView) itemView.findViewById(R.id.imgThumbnail);
    }


    public void bindViewHolder(Context context,String imgUrl){

        Glide.with(context)
                .load(imgUrl)
                .into(imgThumbnail);
    }
}
