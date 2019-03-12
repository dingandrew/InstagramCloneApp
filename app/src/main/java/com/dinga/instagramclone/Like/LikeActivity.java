package com.dinga.instagramclone.Like;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dinga.instagramclone.R;
import com.dinga.instagramclone.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class LikeActivity extends AppCompatActivity {
    private static final String TAG = "LikeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: in search activity");
        setupBottomNavigationView();
    }


    /*
     Bottom navigation view set up
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up bottom navigation view");
        BottomNavigationViewEx bottomNavigationViewEx =
                (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view_bar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(LikeActivity.this, this, bottomNavigationViewEx);

        //make sure the right icon gets highlighted
        Menu menu = bottomNavigationViewEx.getMenu();
        //number is the order of icons on bottom nav bar
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
    }
}
