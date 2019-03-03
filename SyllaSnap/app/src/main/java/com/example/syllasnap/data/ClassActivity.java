package com.example.syllasnap.data;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syllasnap.R;
import com.example.syllasnap.calendar.CalendarManager;

import androidx.appcompat.app.AppCompatActivity;

public class ClassActivity extends AppCompatActivity {

    private static final int REQUEST_AUTHORIZATION = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        initTestButton();
    }

    private void initTestButton() {
        Button button = (Button) findViewById(R.id.class_name);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeTestRequest();
            }
        });
    }

    private void makeTestRequest() {
        String name = ((TextView)findViewById(R.id.class_input_name)).getText().toString();
        // SyllabusDate start = new SyllabusDate(2019, 03, 03, 0, 0);
        // SyllabusDate end = new SyllabusDate(2019, 03, 03, 23, 59);
        SyllabusEvent testEvent = new SyllabusEvent(name, null, null);
        CalendarManager.getInstance().uploadEventToCalendar(testEvent, ClassActivity.this);
        Toast.makeText(ClassActivity.this, "Sent event upload request", Toast.LENGTH_SHORT).show();
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
