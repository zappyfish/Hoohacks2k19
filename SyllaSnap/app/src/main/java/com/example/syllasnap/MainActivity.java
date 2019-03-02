package com.example.syllasnap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.syllasnap.test_activities.AngelTestActivity;
import com.example.syllasnap.test_activities.AnnaTestActivity;
import com.example.syllasnap.test_activities.MaraTestActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpTestActivityButton(R.id.angel, new Intent(MainActivity.this, AngelTestActivity.class));
        setUpTestActivityButton(R.id.anna, new Intent(MainActivity.this, AnnaTestActivity.class));
        setUpTestActivityButton(R.id.mara, new Intent(MainActivity.this, MaraTestActivity.class));
    }

    private void setUpTestActivityButton(int btnId, final Intent startIntent) {
        Button button = (Button) findViewById(btnId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(startIntent);
            }
        });
    }

}
