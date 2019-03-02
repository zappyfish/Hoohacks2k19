package com.example.syllasnap.data;


import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;

public class SyllabusDate {

    private final EventDateTime mEventDateTime;

    // Create two of these for an event (i.e. start + end)
    public SyllabusDate(int year, int month, int day, int hour, int minute) {
        mEventDateTime = new EventDateTime().setDateTime(createDateTime(year, month, day, hour, minute));
    }

    public EventDateTime getEventDateTime() {
        return mEventDateTime;
    }

    private DateTime createDateTime(int year, int month, int day, int hour, int minute) {
        return null;
    }
}
