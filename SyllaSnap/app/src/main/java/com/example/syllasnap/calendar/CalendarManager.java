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

        String calenderId = "test";  // CHANGE THIS LATER!!!! to primary

        // Iterate through entries to make sure person has access to calendar
        boolean permission = false;
        string pageToken = null;
        do {
            CalendarList calendarList = service.calendarList().list().setPageToken(pageToken).execute();
            List<CalendarListEntry> items = calendarList.getItems();

            for (CalendarListEntry calendarListEntry : items) {
                if (calendarListEntry.getSummary().equals(calendarId)) {
                    permission = true;
                }
            }
        } while (pageToken != null && permission == false);

        if (permission = true) {
            event = service.events().insert(calendarId, event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());
        }



    }
}
