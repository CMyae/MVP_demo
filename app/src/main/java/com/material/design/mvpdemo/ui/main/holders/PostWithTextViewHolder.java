package com.material.design.mvpdemo.ui.main.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.material.design.mvpdemo.R;
import com.material.design.mvpdemo.ui.main.model.Post;
import com.material.design.mvpdemo.utils.ViewUtils;

/**
 * Created by chan on 12/13/17.
 */

public class PostWithTextViewHolder extends RecyclerView.ViewHolder{

    public TextView tvFirstLetter,tvUserName;
    public TextView tvDate;
    public TextView tvBody;
    public ImageView imgAction;

    public PostWithTextViewHolder(View v) {
        super(v);

        tvFirstLetter = (TextView) v.findViewById(R.id.tvFirstLetter);
        tvUserName = (TextView) v.findViewById(R.id.tvUserName);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvBody = (TextView) v.findViewById(R.id.tv_body);
        imgAction = (ImageView) v.findViewById(R.id.imgAction);
    }


    public void bindViewHolder(Post post,String loginUser,int color){

        String username = post.getUser().getUserName().trim();

        tvFirstLetter.setText(username.substring(0,1).toUpperCase());
        tvFirstLetter.setBackground(ViewUtils.getRoundDrawable(color));//get color by username
        tvBody.setText(post.getBody());

        int length = post.getDate().length();
        tvDate.setText(post.getDate().substring(0,length-2)); //show date removing seconds

        tvUserName.setText(username);

        //check if owner of the post is currently logged in user
        if(username.toLowerCase().equals(loginUser.toLowerCase())){
            imgAction.setVisibility(View.VISIBLE);
        }else{
            imgAction.setVisibility(View.GONE);
        }

    }
}
