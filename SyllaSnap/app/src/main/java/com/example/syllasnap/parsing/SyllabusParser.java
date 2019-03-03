package com.example.syllasnap.parsing;

import com.example.syllasnap.data.OCRData;
import com.example.syllasnap.data.OCRResponse;
import com.example.syllasnap.data.SyllabusDate;
import com.example.syllasnap.data.SyllabusEvent;
import com.joestelmach.natty.*;


import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class SyllabusParser {

    public SyllabusParser() {

    }

    public List<SyllabusEvent> getSyllabusEvents(OCRResponse ocrResponse, String className) {
        List<SyllabusEvent> syllabusEvents = new ArrayList<>();

        for (int i = 0; i < ocrResponse.getNumData(); i++) {
            OCRData data = ocrResponse.getData(i);

            //analyze text in each ocrData object
            String text = data.getText();
            Scanner scnr = new Scanner(text);

            while (scnr.hasNextLine()) {
                String s = scnr.nextLine();
                //use my boi natty for this
                List<Date> dates = new Parser().parse(s).get(0).getDates();
                int year = 2019;
                int month = dates.get(0).getMonth();
                int day = dates.get(0).getDay();
                SyllabusEvent e = createSyllabusEvent(className, year, month, day);
                syllabusEvents.add(e);
            }

        }

        return syllabusEvents;
    }


    // Helper method for creating an event once you parse name and date
    private static SyllabusEvent createSyllabusEvent(String className, int year, int month, int day) {
        SyllabusDate start = new SyllabusDate(year, month, day, 0, 0);
        SyllabusDate end = new SyllabusDate(year, month, day, 23, 59);
        return new SyllabusEvent(className, start, end);
    }

}

