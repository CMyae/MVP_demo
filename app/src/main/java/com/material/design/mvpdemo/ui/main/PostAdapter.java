package com.material.design.mvpdemo.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.material.design.mvpdemo.R;
import com.material.design.mvpdemo.data.local.PrefHelper;
import com.material.design.mvpdemo.ui.main.holders.PostWithImageViewHolder;
import com.material.design.mvpdemo.ui.main.holders.PostWithTextViewHolder;
import com.material.design.mvpdemo.ui.main.model.Post;
import com.material.design.mvpdemo.utils.ViewUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by chan on 12/13/17.
 */

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<Post> posts;
    private HashMap<String,Integer> colors;
    private OnItemClickListener listener;
    private Random r;

    private String loginUser;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
        colors = new HashMap<>();
        r = new Random();

        loginUser = PrefHelper.getLoginUserName(context); //get current login user name

        createColorByUsers();

    }

    @Override
    public int getItemViewType(int position) {
        return posts.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        if(viewType == Post.POST_WITH_TEXT){
            view = LayoutInflater.from(context).inflate(R.layout.item_post_with_text,parent,false);
            return new PostWithTextViewHolder(view);

        }else{
            view = LayoutInflater.from(context).inflate(R.layout.item_post_with_image,parent,false);
            return new PostWithImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final Post post = posts.get(position);

        ((PostWithTextViewHolder)holder).bindViewHolder(post,loginUser,colors.get(post.getUser().getUserName()));

        if(getItemViewType(position) == Post.POST_WITH_IMAGE){
            ((PostWithImageViewHolder)holder).bindViewHolder(context,post.getImageUrl());
        }

        //click listener
        ((PostWithTextViewHolder)holder).imgAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listener != null){
                    listener.imgActionClick(ViewUtils.getLocation(v),holder.getAdapterPosition());
                }
            }
        });

    }



    public void resetColors(){
        colors = new HashMap<>();
        createColorByUsers();
    }


    /**
     * Create random color for all users
     */
    public void createColorByUsers(){
        for (int i = 0; i < posts.size(); i++) {
            String username = posts.get(i).getUser().getUserName();

            if(!colors.containsKey(username)){
                colors.put(username,r.nextInt(256));
            }
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public interface OnItemClickListener{
        void imgActionClick(int[] location,int position);
    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
