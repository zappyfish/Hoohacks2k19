package com.example.syllasnap.data;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class SyllabusEvent {

    private final Event mCalendarEvent;

    public SyllabusEvent(String name, SyllabusDate start, SyllabusDate end) {
        mCalendarEvent = createCalendarEvent(name, start.getEventDateTime(), end.getEventDateTime());
    }

    // TODO: Complete me (Mara). Useful links below
    // // https://stackoverflow.com/questions/16587138/google-calendar-v3-java-api-insert-event
    private Event createCalendarEvent(String name, EventDateTime start, EventDateTime end) {
        return null;
    }

    public Event getCalendarEvent() {
        return mCalendarEvent;
    }
}
