package com.example.syllasnap.calendar;

import com.example.syllasnap.data.SyllabusEvent;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;

import java.io.IOException;
import java.util.List;

public class CalendarManager {

    private static CalendarManager sInstance;
    private GoogleAccountCredential mCredential;

    // TODO: fill in constructor as appropriate
    private CalendarManager(GoogleAccountCredential mCredential) {
        this.mCredential = mCredential;
    }

    public static synchronized CalendarManager getInstance(GoogleAccountCredential mCredential) {
        if (sInstance == null) {
            sInstance = new CalendarManager(mCredential);
        }
         return sInstance;
    }

    // TODO: Complete me (Mara)
    // Useful links:
    // https://stackoverflow.com/questions/16587138/google-calendar-v3-java-api-insert-event
    // https://developers.google.com/api-client-library/java/apis/calendar/v3
    // https://developers.google.com/calendar/v3/reference/events/insert

    public Calendar createCalendar(){
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, this.mCredential)
                .setApplicationName("R_D_Location Calendar")
                .build();

        Calendar calendar = new Calendar.Builder(transport, jsonFactory, this.mCredential)
                .setApplicationName("applicationName").build();

        return calendar;
    }

    public boolean calendarPermission(String calendarId) {

        boolean permission = false;
        String pageToken = null;
        Calendar calendar = createCalendar();

        try {
            do {
                CalendarList calendarList = calendar.calendarList().list().setPageToken(pageToken).execute();
                List<CalendarListEntry> items = calendarList.getItems();

                for (CalendarListEntry calendarListEntry : items) {
                    if (calendarListEntry.getSummary().equals(calendarId)) {
                        permission = true;
                    }
                }
            } while (pageToken != null && permission == false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return permission;
    }


    public void uploadEventToCalendar(SyllabusEvent event) {
        // TODO: look at SyllabusEvent class. You will probably have to use the method:
        // event.getCalendarEvent()

        String calendarId = "test";  // CHANGE THIS LATER!!!! to primary
        boolean permission = calendarPermission(calendarId);
        // Iterate through entries to make sure person has access to calendar
        Calendar calendar = createCalendar(this.mCredential);

        if (permission = true) {
            event = calendar.events().insert(calendarId, event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());
        }



    }
}
