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
    public SyllabusDate(int year, int month, int day, int hour, int minute) {
        mEventDateTime = new EventDateTime().setDateTime(createDateTime(year, month, day, hour, minute));
    }

    public EventDateTime getEventDateTime() {
        return mEventDateTime;
    }

    private DateTime createDateTime(int year, int month, int day, int hour, int minute) {
        ArrayList<Integer> dates = new ArrayList<>();
        ArrayList<String> changedDates = new ArrayList<>();
        dates.add(month);
        dates.add(day);
        dates.add(hour);
        dates.add(minute);

        for (int date : dates) {
            if (date < 10) {
                changedDates.add("0" + date);
            }
            else{
                changedDates.add("" + date);
            }
        }
        return DateTime.parseRfc3339(year+"-"+changedDates.get(0)+"-"+changedDates.get(1)+"T"+
                changedDates.get(2)+":"+changedDates.get(3)+":00Z");
    }
}
