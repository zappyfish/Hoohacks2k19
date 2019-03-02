package com.example.syllasnap.calendar;

import com.example.syllasnap.data.SyllabusEvent;

public class CalendarManager {

    private static CalendarManager sInstance;

    // TODO: fill in constructor as appropriate
    private CalendarManager() {

    }

    public static CalendarManager getInstance() {
        if (sInstance == null) {
            sInstance = new CalendarManager();
        }
         return sInstance;
    }

    // TODO: Complete me (Mara)
    // Useful links:
    // https://stackoverflow.com/questions/16587138/google-calendar-v3-java-api-insert-event
    // https://developers.google.com/api-client-library/java/apis/calendar/v3
    // https://developers.google.com/calendar/v3/reference/events/insert
    public void uploadEventToCalendar(SyllabusEvent event) {
        // TODO: look at SyllabusEvent class. You will probably have to use the method:
        // event.getCalendarEvent()
    }
}
