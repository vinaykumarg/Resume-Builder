package com.example.vinayg.resumebuilder.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.authorization.SessionManager;

import static com.example.vinayg.resumebuilder.activities.AboutActivity.session;

/**
 * Created by vinay.g.
 */

public class SplashScreenActivity extends Activity {
    private static int SPLASH_TIME_OUT = 5000;
    private Animation mAnimation1, mAnimation2;
    private TextView sampleTextView;
    private ImageView mImageView;
    SessionManager mManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sampleTextView = (TextView) findViewById(R.id.textView);
        mImageView = (ImageView) findViewById(R.id.imgLogo);
        mAnimation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.sample_animation);
        sampleTextView.startAnimation(mAnimation1);
        mAnimation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        mImageView.startAnimation(mAnimation2);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                session = new SessionManager(getApplicationContext());
                if(!session.isLoggedIn()) {
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashScreenActivity.this, ProfileActivity.class);
                    startActivity(i);
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

