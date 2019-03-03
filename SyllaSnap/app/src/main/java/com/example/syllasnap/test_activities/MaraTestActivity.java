package com.example.syllasnap.test_activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syllasnap.R;
import com.example.syllasnap.calendar.CalendarManager;
import com.example.syllasnap.data.SyllabusDate;
import com.example.syllasnap.data.SyllabusEvent;

public class MaraTestActivity extends AppCompatActivity {

    private static final int REQUEST_AUTHORIZATION = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mara_test);

        initTestButton();
    }

    private void initTestButton() {
        Button button = (Button) findViewById(R.id.mara_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeTestRequest();
            }
        });
    }

    private void makeTestRequest() {
        String name = ((TextView)findViewById(R.id.mara_test_event_name)).getText().toString();
        // SyllabusDate start = new SyllabusDate(2019, 03, 03, 0, 0);
        // SyllabusDate end = new SyllabusDate(2019, 03, 03, 23, 59);
        SyllabusEvent testEvent = new SyllabusEvent(name, null, null);
        CalendarManager.getInstance().uploadEventToCalendar(testEvent, MaraTestActivity.this);
        Toast.makeText(MaraTestActivity.this, "Sent event upload request", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_AUTHORIZATION) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                makeTestRequest();
            }
        }
    }

}
