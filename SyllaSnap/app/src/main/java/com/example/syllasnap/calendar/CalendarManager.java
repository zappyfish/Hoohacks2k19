package com.example.syllasnap.calendar;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.syllasnap.data.SyllabusEvent;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class CalendarManager {

    private static final long MS_PER_WEEK = 600000000;

    private static final int REQUEST_AUTHORIZATION = 8;

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


    public void uploadEventToCalendar(SyllabusEvent event, Activity callingActivity) {
        // TODO: look at SyllabusEvent class. You will probably have to use the method:
        if (mCalendar != null) {
            // event.getCalendarEvent()

            final String calendarId = "primary";  // CHANGE THIS LATER!!!! to primary
            // boolean permission = calendarPermission(calendarId);
//            boolean permission = true;
            // Iterate through entries to make sure person has access to calendar

            CalendarRequest request = new CalendarRequest();
            request.calendar = mCalendar;
            request.calendarId = calendarId;
            request.event = event.getCalendarEvent();
            request.callingActivity = callingActivity;

            CalendarTask uploadTask = new CalendarTask();
            uploadTask.execute(request);
        }
    }

    private static class CalendarRequest {
        private Calendar calendar;
        private String calendarId;
        private Event event;
        private Activity callingActivity;

        private CalendarRequest() {}
    }

    public void requestWeekData(WeekDataCallback callback) {
        WeekData data = new WeekData();
        data.callback = callback;
        data.calendar = mCalendar;

        WeekDataTask task = new WeekDataTask();
        task.execute(data);
    }

    private static class CalendarTask extends AsyncTask<CalendarRequest, Void, Void> {
        @Override
        protected Void doInBackground(CalendarRequest... params) {
            CalendarRequest request = params[0];
            try {
                request.calendar.events().insert(request.calendarId, request.event).execute();
            } catch (UserRecoverableAuthIOException e) {
                request.callingActivity.startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
            }
            catch (Exception e) {
                Log.d("calendar upload", e.toString());
            }
            return null;
        }
    }

    private static class WeekData {
        private Calendar calendar;
        private WeekDataCallback callback;
    }

    private static class WeekDataTask extends AsyncTask<WeekData, Void, Void> {
        @Override
        protected Void doInBackground(WeekData... params) {
            WeekData data = params[0];
            try {
                Events events = data.calendar.events().list("primary").setPageToken(null).execute();
                List<Event> items = events.getItems();
                int[] eventsPerDay = new int[7];
                for (int i = 0; i < 7; i++) {
                    eventsPerDay[i] = 0;
                }
                Date todayDate = new Date();
                DateTime today = new DateTime(todayDate);
                for (Event event : items) {
                    EventDateTime start = event.getStart();
                    DateTime dTime = start.getDateTime();
                    long diffMs = dTime.getValue() - today.getValue();
                    if (diffMs < MS_PER_WEEK && diffMs > 0) {
                        long msPerDay = MS_PER_WEEK / 7;
                        int day = (int)(diffMs / msPerDay);
                        eventsPerDay[day]++;
                    }
                }
                data.callback.onWeekData(eventsPerDay);
            } catch (IOException e) {
                // nada
            }
            return null;
        }
    }

    public interface WeekDataCallback {

        void onWeekData(int[] weekData);
    }

}
