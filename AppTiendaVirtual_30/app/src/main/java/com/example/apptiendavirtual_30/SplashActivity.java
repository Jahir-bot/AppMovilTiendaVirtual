package com.example.apptiendavirtual_30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Intent menu = new Intent(this, LoginActivity.class);
        Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);
                }catch (InterruptedException Ex)
                {

                }finally {
                    startActivity(menu);
                    finish();
                }
            }
        };
        timer.start();
    }
}