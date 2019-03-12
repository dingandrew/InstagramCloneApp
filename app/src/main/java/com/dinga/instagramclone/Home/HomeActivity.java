package com.dinga.instagramclone.Home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dinga.instagramclone.R;
import com.dinga.instagramclone.Utils.BottomNavigationViewHelper;
import com.dinga.instagramclone.Utils.SectionsPagerAdapter;
import com.dinga.instagramclone.Utils.UniversalImageLoader;
import com.dinga.instagramclone.Login.LoginActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupFirebaseAuth();

        setupBottomNavigationView();
        setupViewPager();
        initImageLoader();
    }





    /**
     * adds the 3 tabs camera home messages
     */
    private void setupViewPager(){
       SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
       adapter.addFragment(new CameraFragment());
       adapter.addFragment(new HomeFragment());
       adapter.addFragment(new MessagesFragment());

        //create a view pages from layout_center
        ViewPager viewPager = (ViewPager)findViewById(R.id.container01);
        viewPager.setAdapter(adapter);

        //add to tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs02);
        tabLayout.setupWithViewPager(viewPager);

        //add icons to tablayout
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.instagram_logo11);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_messages);
    }


    /*
     Bottom navigation view set up
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up bottom navigation view");
        BottomNavigationViewEx bottomNavigationViewEx =
                    (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view_bar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(HomeActivity.this, this, bottomNavigationViewEx);

        //make sure the right icon gets highlighted
        Menu menu = bottomNavigationViewEx.getMenu();
        //number is the order of icons on bottom nav bar
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }


    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(HomeActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }


    /*
    ----------------------------------------------FIREBASE AUTH-------------------------------
     */

    /**
     * checks to see if the @param 'user' is logged in
     * @param user
     */
    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if(user == null){
            Log.d(TAG, "checkCurrentUser: Go to login screen");

            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");
        mAuth = FirebaseAuth.getInstance();

        //uncomment to start app at login screen each time
        //mAuth.signOut();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in
                checkCurrentUser(user);

                if (user != null) {
                    // Users is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // Users is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
