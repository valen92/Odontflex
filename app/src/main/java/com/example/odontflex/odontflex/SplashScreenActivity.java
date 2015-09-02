package com.example.odontflex.odontflex;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

public class SplashScreenActivity extends Activity {

    //Duracion del splash
    private long splashDuration = 3000;
    //Parametros configuracion progress bar
    private boolean splashActive = true;
    private boolean paused = false;
    private long ms = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_sceen);

        Thread mythread = new Thread() {
            public void run() {
                try {
                    while (splashActive && ms < splashDuration) {
                        if (!paused)
                            ms = ms + 100;
                        sleep(100);
                    }
                } catch (Exception e) {
                } finally {
                    Intent intent = new Intent(SplashScreenActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        mythread.start();
    }

}