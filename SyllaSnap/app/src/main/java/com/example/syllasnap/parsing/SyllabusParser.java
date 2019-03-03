package com.example.syllasnap.parsing;

import com.example.syllasnap.data.OCRData;
import com.example.syllasnap.data.OCRResponse;
import com.example.syllasnap.data.SyllabusDate;
import com.example.syllasnap.data.SyllabusEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.time.Clock;
import java.util.Locale;
import java.text.DateFormatSymbols;

public class SyllabusParser {

    public Clock clock;

    public SyllabusParser() {

    }

    public List<SyllabusEvent> getSyllabusEvents(OCRResponse ocrResponse) {
        List<SyllabusEvent> syllabusEvents = new LinkedList<>();
        // TODO: Complete me (Angel)
        // Useful links:
        // https://stackoverflow.com/questions/5270195/extracting-dates-from-text-in-java
        // https://stackoverflow.com/questions/16434724/how-to-extract-date-and-from-a-string-in-java
        // TODO: Look at example of creating event (delete this code later)

        //arraylist of the possible month format
        // also figure out how to search for a month when it is in number format
        SimpleDateFormat monthDay1 = new SimpleDateFormat("mm/dd");
        SimpleDateFormat monthDay2 = new SimpleDateFormat("mm-dd");

        ArrayList<String> months = new ArrayList<String>();
        months.add("Jan");
        months.add("January");
        months.add("Feb");
        months.add("February");
        months.add("Mar");
        months.add("March");
        months.add("Apr");
        months.add("April");
        months.add("May");
        months.add("Jun");
        months.add("June");
        months.add("Jul");
        months.add("July");
        months.add("Aug");
        months.add("August");
        months.add("Sept");
        months.add("September");
        months.add("Oct");
        months.add("October");
        months.add("Nov");
        months.add("November");
        months.add("Dec");
        months.add("December");

        ArrayList<Integer> days = new ArrayList<Integer>();
        days.add(1);
        days.add(2);
        days.add(3);
        days.add(4);
        days.add(5);
        days.add(6);
        days.add(7);
        days.add(8);
        days.add(9);
        days.add(10);
        days.add(11);
        days.add(12);
        days.add(13);
        days.add(14);
        days.add(15);
        days.add(16);
        days.add(17);
        days.add(18);
        days.add(19);
        days.add(20);
        days.add(21);
        days.add(22);
        days.add(23);
        days.add(24);
        days.add(25);
        days.add(26);
        days.add(27);
        days.add(28);
        days.add(29);
        days.add(30);
        days.add(31);

        // initializing a varible to hold the text from the ocr
        String fullTxt = "";

        //iterating through the given arraylist and adding it to the fullText string
        for (int i = 0; i < ocrResponse.getNumData(); i++) {
            //sets the data at i equal to a local variable
            OCRData data = ocrResponse.getData(i);
            // sets the data equal to a string
            String txt = data.getText();
            fullTxt += txt;
        }

        int intDay;

        // init
        ArrayList<String> dayMonth = new ArrayList<String>();
        ArrayList<String> monthDay = new ArrayList<String>();

        //iterates through the months above
        for (String month : months) {
            if (fullTxt.contains(month)) {
                //if it does, then it checks for the length of the month to know if its abbreviated
                if (month.length() < 5) {
                    // given the month, we bind that months number equal to a variable
                    int monthNum = SyllabusParser.monthAsNumber(month, Locale.US, true);

                    // variables that denote the lowest possible index and highest possible index of the day
                    int lowestIndex = fullTxt.indexOf(month) - 7;
                    int highestIndex = fullTxt.indexOf(month) + 7;

                    // iterate through the days of the month
                    for (Integer day : days) {
                        //turn number into string
                        String strDay = day + "";
                        // and if that number is found within the bounds,
                        if (fullTxt.indexOf(strDay) < highestIndex && fullTxt.indexOf(strDay) > lowestIndex) {
                            //turn the day into an integer
                            intDay = Integer.parseInt(strDay);
                            if (fullTxt.indexOf(strDay) < fullTxt.indexOf(month)) {
                                String dM = strDay + month;
                                dayMonth.add(dM);
                                for (int i = 0; i < dayMonth.size(); i++) {
                                    String eventName = fullTxt.substring(i, i + 1);
                                    int y = 2019;
                                    int m = monthNum + 1;
                                    SyllabusEvent event = createSyllabusEvent(eventName, y, m, intDay);
                                    syllabusEvents.add(event);
                                }
                            }

                            if (fullTxt.indexOf(strDay) > fullTxt.indexOf(month)) {
                                String mD = month + strDay;
                                monthDay.add(mD);
                                for (int i = 0; i < dayMonth.size(); i++) {
                                    String eventName = fullTxt.substring(i, i + 1);
                                    int y = 2019;
                                    int m = monthNum + 1;
                                    SyllabusEvent event = createSyllabusEvent(eventName, y, m, intDay);
                                    syllabusEvents.add(event);
                                }

                            }
                        }
                    }

                    } else {
                        // given the month, we bind that months number equal to a variable
                        int monthNum = SyllabusParser.monthAsNumber(month, Locale.US, false);

                        // variables that denote the lowest possible index and highest possible index of the day
                        int lowestIndex = fullTxt.indexOf(month) - 14;
                        int highestIndex = fullTxt.indexOf(month) + 14;

                        // iterate through the days of the month
                        for (Integer day : days) {
                            //turn number into string
                            String strDay = day + "";
                            // and if that number is found within the bounds,
                            if (fullTxt.indexOf(strDay) < highestIndex && fullTxt.indexOf(strDay) > lowestIndex) {
                                //turn the day into an integer
                                intDay = Integer.parseInt(strDay);

                                String eventName = "testEventName";
                                int y = 2019;
                                int m = monthNum + 1;
                                SyllabusEvent event = createSyllabusEvent(eventName, y, m, day);
                                syllabusEvents.add(event);

                            }
                        }
                    }
                }
            } return syllabusEvents;
     
    }


    // Helper method for creating an event once you parse name and date
    private static SyllabusEvent createSyllabusEvent(String name, int year, int month, int day) {
        SyllabusDate start = new SyllabusDate(year, month, day, 0, 0);
        SyllabusDate end = new SyllabusDate(year, month, day, 23, 59);
        return new SyllabusEvent(name, start, end);
    }

    public static int monthAsNumber(String month, Locale locale, boolean abbreviated) {

        DateFormatSymbols dfs = new DateFormatSymbols(locale);
        String[] months = (abbreviated ? dfs.getShortMonths() : dfs.getMonths());

        for (int i = 0; i < 12; i++) {
            if (months[i].equals(month)) {
                return i; // month index is zero-based as usual in old JDK pre 8!
            }
        }
        return -1; // no match


    }
}

