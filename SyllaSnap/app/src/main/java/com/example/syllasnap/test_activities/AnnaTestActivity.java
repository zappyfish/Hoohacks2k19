package com.example.syllasnap.test_activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.syllasnap.R;

public class AnnaTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anna_test);
    }

    private void initTestButton() {
        Button button = (Button) findViewById(R.id.anna_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: add test here
                Toast.makeText(AnnaTestActivity.this, "sending OCR request...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
