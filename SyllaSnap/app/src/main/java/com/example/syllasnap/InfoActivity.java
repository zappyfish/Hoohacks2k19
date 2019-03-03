package com.example.syllasnap;

import android.content.Intent;
import android.os.Bundle;

import com.example.syllasnap.auth.AuthActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;

public class InfoActivity extends AppCompatActivity {

    private static final int READ_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Handler authHandler = new Handler();
        Runnable authStarter = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(InfoActivity.this, AuthActivity.class));
            }
        };

        authHandler.postDelayed(authStarter, READ_DELAY);
    }

}
