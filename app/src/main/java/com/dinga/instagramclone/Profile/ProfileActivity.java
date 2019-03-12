package com.dinga.instagramclone.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.dinga.instagramclone.Models.Photo;
import com.dinga.instagramclone.R;
import com.dinga.instagramclone.Utils.ViewCommentFragment;
import com.dinga.instagramclone.Utils.ViewPostFragment;

public class ProfileActivity extends AppCompatActivity implements ProfileFragment.OnGridSelectedListener
                                                                    , ViewPostFragment.OnCommentThreadSelectedListner{
    private static final String TAG = "ProfileActivity";

    @Override
    public void onCommentThreadSelectedListner(Photo photo) {
        Log.d(TAG, "onCommentThreadSelectedListner: selected a comment thread");

        ViewCommentFragment fragment = new ViewCommentFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.photo), photo);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container02, fragment);
        transaction.addToBackStack(getString(R.string.view_comments_fragment));
        transaction.commit();
    }

    @Override
    public void onGridImageSelected(Photo photo, int activityNumber) {
        Log.d(TAG, "onGridImageSelected: selected an image from gridview" + photo.toString());

        ViewPostFragment fragment = new ViewPostFragment();
        Bundle args = new Bundle();

        args.putParcelable(getString(R.string.photo), photo);
        args.putInt(getString(R.string.activity_number), activityNumber);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container02, fragment);
        transaction.addToBackStack(getString(R.string.view_post_fragment));
        transaction.commit();
    }

    private ProgressBar mProgressBar;
    private ImageView profilePhoto;
    private final int NUM_OF_COLLUMNS = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: in search activity");
        init();
    }

    private void init(){
        Log.d(TAG, "init: " + getString(R.string.profile_fragment));

        ProfileFragment fragment = new ProfileFragment();
        FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container02, fragment);
        transaction.addToBackStack(getString(R.string.profile_fragment));
        transaction.commit();
    }



}
