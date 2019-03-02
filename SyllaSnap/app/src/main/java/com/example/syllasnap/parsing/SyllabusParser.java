package com.example.syllasnap.parsing;

import com.example.syllasnap.data.SyllabusDate;
import com.example.syllasnap.data.SyllabusEvent;

import java.util.LinkedList;
import java.util.List;

public class SyllabusParser {

    public SyllabusParser() {

    }

    public List<SyllabusEvent> getSyllabusEvents(String OCRDataString) {
        List<SyllabusEvent> syllabusEvents = new LinkedList<>();
        // TODO: Complete me (Angel)
        // Useful links:
        // https://stackoverflow.com/questions/5270195/extracting-dates-from-text-in-java
        // https://stackoverflow.com/questions/16434724/how-to-extract-date-and-from-a-string-in-java
        // TODO: Look at example of creating event (delete this code later)
        String eventName = "testEventName";
        int year = 2019;
        int month = 3;
        int day = 2;
        SyllabusEvent event = createSyllabusEvent(eventName, year, month, day);
        syllabusEvents.add(event);

        // Return the syllabus events here!
        return syllabusEvents;
    }

    // Helper method for creating an event once you parse name and date
    private static SyllabusEvent createSyllabusEvent(String name, int year, int month, int day) {
        SyllabusDate start = new SyllabusDate(year, month, day, 0, 0);
        SyllabusDate end = new SyllabusDate(year, month, day, 23, 59);
        return new SyllabusEvent(name, start, end);
    }
}
