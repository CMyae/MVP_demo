package com.material.design.mvpdemo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.material.design.mvpdemo.R;
import com.material.design.mvpdemo.data.network.model.User;
import com.material.design.mvpdemo.ui.base.BaseActivity;
import com.material.design.mvpdemo.ui.custom.PopupBox;
import com.material.design.mvpdemo.ui.login.LoginActivity;
import com.material.design.mvpdemo.ui.main.createpost.CreatePostDialog;
import com.material.design.mvpdemo.ui.main.listener.OnDialogListener;
import com.material.design.mvpdemo.ui.main.model.Post;
import com.material.design.mvpdemo.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainView, PostAdapter.OnItemClickListener,
        PopupBox.OnMenuClickListener, OnDialogListener{

    private RecyclerView recyclerView;
    private RelativeLayout mContentView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private PostAdapter adapter;
    private List<Post> posts = new ArrayList<>();
    private MainMvpPresenter mainPresenter;
    private User currentUser;
    private Post currentPost;

    private PopupBox popupBox;
    private boolean isPopupShowing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

        currentUser = CommonUtils.getCurrentUser(this);


        mainPresenter = new MainPresenter(this);

        mContentView = (RelativeLayout) findViewById(R.id.mContentView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        popupBox = new PopupBox(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new PostAdapter(this,posts);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setRefreshing(true);

        mainPresenter.getNetworkData();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isPopupShowing){
                    mainPresenter.onPopupMenuClose();
                }
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainPresenter.getNetworkData();
            }
        });

    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }


    /**
     * Click event of fab button
     * @param view
     */
    public void onFabClick(View view) {
        mainPresenter.onCreatePost();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_logout:
                mainPresenter.onLogoutMenuClick();
                break;
        }
        return true;
    }



    @Override
    public void showCreatePostDialog() {
        CreatePostDialog dialog = CreatePostDialog.getDialogInstance(currentUser,false,null);
        dialog.show(getSupportFragmentManager(),"CREATE_POST");
    }

    @Override
    public void showEditPostDialog() {
        CreatePostDialog dialog = CreatePostDialog.getDialogInstance(currentUser,true,currentPost);
        dialog.show(getSupportFragmentManager(),"EDIT_POST");
    }

    @Override
    public void showAvailablePosts(List<Post> newData) {

        posts.clear();
        posts.addAll(newData);
        adapter.resetColors();
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
        hideRefreshIcon();
    }


    private void hideRefreshIcon(){
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void imgActionClick(int[] location,int position) {
        currentPost = posts.get(position);
        mainPresenter.onActionBtnClick(location);
    }


    @Override
    public void showPopupMenu(int[] loc) {
        String[] menus = {"Edit","Delete"};
        popupBox.x(loc[0])
                .y(loc[1])
                .setMenuClickListener(this)
                .showIn(menus,mContentView);
        isPopupShowing = true;
    }

    @Override
    public void hidePopupMenu() {
        popupBox.hide();
        isPopupShowing = false;
    }

    @Override
    public void onEditMenuClick() {
        mainPresenter.onEditPost();
    }

    @Override
    public void onDeleteMenuClick() {
        mainPresenter.onDeleteMenuClick(currentPost);
    }

    @Override
    public void onDialogDismiss() {
        if(isPopupShowing){
            mainPresenter.onPopupMenuClose();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        hideRefreshIcon();
    }
}
