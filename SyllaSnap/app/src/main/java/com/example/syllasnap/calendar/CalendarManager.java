package com.example.syllasnap.calendar;

import android.util.Log;
import android.widget.Toast;

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
    private Calendar mCalendar;

    // TODO: fill in constructor as appropriate
    private CalendarManager() {
        mCalendar= null;
    }

    public static synchronized CalendarManager getInstance() {
        if (sInstance == null) {
            sInstance = new CalendarManager();
        }
         return sInstance;
    }

    public void setCredentials(GoogleAccountCredential credentials) {
        mCalendar = createCalendar(credentials);
    }

    // TODO: Complete me (Mara)
    // Useful links:
    // https://stackoverflow.com/questions/16587138/google-calendar-v3-java-api-insert-event
    // https://developers.google.com/api-client-library/java/apis/calendar/v3
    // https://developers.google.com/calendar/v3/reference/events/insert

    public Calendar createCalendar(GoogleAccountCredential credentials){
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, credentials)
                .setApplicationName("R_D_Location Calendar")
                .build();

        Calendar calendar = new Calendar.Builder(transport, jsonFactory, credentials)
                .setApplicationName("applicationName").build();

        return calendar;
    }

//    public boolean calendarPermission(String calendarId) {
//
//
//        if (mCalendar != null) {
//            boolean permission = false;
//            String pageToken = null;
//
//            try {
//                do {
//                    CalendarList calendarList = mCalendar.calendarList().list().setPageToken(pageToken).execute();
//                    List<CalendarListEntry> items = calendarList.getItems();
//
//                    for (CalendarListEntry calendarListEntry : items) {
//                        if (calendarListEntry.getSummary().equals(calendarId)) {
//                            permission = true;
//                        }
//                    }
//                } while (pageToken != null && permission == false);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return permission;
//        } else {
//            return false;
//        }
//    }


    public void uploadEventToCalendar(SyllabusEvent event) {
        // TODO: look at SyllabusEvent class. You will probably have to use the method:
        if (mCalendar != null) {
            // event.getCalendarEvent()

            String calendarId = "primary";  // CHANGE THIS LATER!!!! to primary
            // boolean permission = calendarPermission(calendarId);
//            boolean permission = true;
            // Iterate through entries to make sure person has access to calendar

            try {
                    mCalendar.events().insert(calendarId, event.getCalendarEvent()).execute();
                } catch (Exception e) {
                    Log.d("calendar upload", e.toString());
            }

        }

    }
}
