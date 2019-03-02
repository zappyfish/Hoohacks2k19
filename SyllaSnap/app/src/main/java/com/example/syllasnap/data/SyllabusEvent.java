package com.example.syllasnap.data;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class SyllabusEvent {

    private final Event mCalendarEvent;

    public SyllabusEvent(String name, SyllabusDate start, SyllabusDate end) {
        this.mCalendarEvent = createCalendarEvent(name, start.getEventDateTime(), end.getEventDateTime());

    }

    // TODO: Complete me (Mara). Useful links below
    // // https://stackoverflow.com/questions/16587138/google-calendar-v3-java-api-insert-event
    // https://developers.google.com/resources/api-libraries/documentation/calendar/v3/java/latest/com/google/api/services/calendar/model/Calendar.html
    private Event createCalendarEvent(String name, EventDateTime start, EventDateTime end) {
        // https://developers.google.com/calendar/create-events

        Event event = new Event();
        event.setSummary(name);  // the summary is the name
        event.setDescription("Auto generated by Sylla-Snap");  // a description that plugs us

        event.setStart(start);  // set the start time
        event.setEnd(end);  // set the end time

        // create event reminders for both email and pop up
        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24*60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders();
        reminders.setUseDefault(false);
        reminders.setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        return event;
    }

    public Event getCalendarEvent() {
        return this.mCalendarEvent;
    }
}
