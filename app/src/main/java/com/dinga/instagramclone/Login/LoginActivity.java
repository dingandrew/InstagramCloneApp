package com.dinga.instagramclone.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dinga.instagramclone.Home.HomeActivity;
import com.dinga.instagramclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext;
    private ProgressBar mProgressbar;
    private EditText mEmail, mPassword;
    private TextView mPleasewait;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: activity login");

        mProgressbar = (ProgressBar) findViewById(R.id.login_request_progressbar);
        mPleasewait = (TextView) findViewById(R.id.please_wait);
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword= (EditText) findViewById(R.id.input_password);
        mContext = LoginActivity.this;

        mPleasewait.setVisibility(View.GONE);
        mProgressbar.setVisibility(View.GONE);

        setupFirebaseAuth();
        init();
    }


       /*
    ----------------------------------------------FIREBASE AUTH-------------------------------
     */


    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //check if the user is logged in

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

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void init(){
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: login button");
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if(isStringNull(email) || isStringNull(password)){
                    Toast.makeText(LoginActivity.this, "You must fill out all fields", Toast.LENGTH_LONG).show();
                }
                else{
                    mProgressbar.setVisibility(View.VISIBLE);
                    mPleasewait.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    FirebaseUser mUser = mAuth.getCurrentUser();

                                   /*
                                   If sign in fails, display a message to the user. If sign in succeeds
                                      the auth state listener will be notified and logic to handle the
                                      signed in user can be handled in the listener.
                                    */
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        try {
                                            if(mUser.isEmailVerified()){
                                                Log.d(TAG, "onComplete: email is verified");
                                                Intent mIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                                startActivity(mIntent);
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(LoginActivity.this, "Authentication Failed. Check Email",
                                                        Toast.LENGTH_SHORT).show();
                                                mProgressbar.setVisibility(View.GONE);
                                                mPleasewait.setVisibility(View.GONE);
                                                mAuth.signOut();
                                            }
                                        }catch (NullPointerException e){
                                            Log.e(TAG, "onComplete: null pointer exception" + e.getMessage() );
                                        }

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        mProgressbar.setVisibility(View.GONE);
                                        mPleasewait.setVisibility(View.GONE);

                                    }
                                }
                            });
                }
            }
        });

        TextView linksignup = (TextView) findViewById(R.id.link_signup);

        linksignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: set up new user");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //if the user is logged in then navigate to home activity
        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean isStringNull(String str){
        if (str.equals("")){
            return true;
        }
        else{
            return false;
        }
    }
}
