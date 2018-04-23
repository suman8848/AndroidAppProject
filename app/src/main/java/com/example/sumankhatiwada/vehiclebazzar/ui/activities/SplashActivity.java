package com.example.sumankhatiwada.vehiclebazzar.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by sumankhatiwada on 4/17/18.
 */

public class SplashActivity extends BaseActivity {
    private static int SPLASH_TIME_OUT = 3000;
    @BindView(R.id.txt_splash)
    TextView textView;

    @Override
    protected int getContentView() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        runAnimation();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    private void runAnimation()
    {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        a.reset();
        textView.clearAnimation();
        textView.startAnimation(a);
    }
}
