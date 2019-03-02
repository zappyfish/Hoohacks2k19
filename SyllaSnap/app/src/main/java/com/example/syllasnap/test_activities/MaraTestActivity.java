package com.example.syllasnap.test_activities;

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
                String name = ((TextView)findViewById(R.id.mara_test_event_name)).getText().toString();
                SyllabusDate start = new SyllabusDate(2019, 03, 03, 0, 0);
                SyllabusDate end = new SyllabusDate(2019, 03, 03, 23, 59);
                SyllabusEvent testEvent = new SyllabusEvent(name, start, end);
                CalendarManager.getInstance().uploadEventToCalendar(testEvent);
                Toast.makeText(MaraTestActivity.this, "Sent event upload request", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
