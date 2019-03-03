package com.example.syllasnap.data;


import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SyllabusDate {

    private final EventDateTime mEventDateTime;

    // Create two of these for an event (i.e. start + end)
    public SyllabusDate(Date date) {
        mEventDateTime = new EventDateTime().setDateTime(createDateTime(date));
    }

    public EventDateTime getEventDateTime() {
        return mEventDateTime;
    }

    private DateTime createDateTime(Date date) {
        return new DateTime(date);
    }
}
