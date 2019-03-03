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

    private final int SORT_HEIGHT_THRESH = 30; // 15 pixels

    public SyllabusParser() {

    }

    private OCRResponse resortData(OCRResponse response, OCRData fullText) {
        OCRResponse sorted = new OCRResponse();
        // TODO: go from top left downwards
        Set<OCRData> set = new HashSet<>();
        for (int i = 0; i < response.getNumData(); i++) {
            if (response.getData(i) != fullText) {
                set.add(response.getData(i));
            }
        }

//        OCRData data = findTopLeft(set);
//        set.remove(data);
//        sorted.addData(data);
//
//        while (!set.isEmpty()) {
//            data = findNext(data, set);
//            set.remove(data);
//            sorted.addData(data);
//        }
        OCRData left, right;

        while (!set.isEmpty()) {
            left = findTopLeft(set);
            right = findTopRight(set);
            List<OCRData> row = getNextRow(left, right, set);
            for (OCRData data : row) {
                sorted.addData(data);
            }
        }



        return sorted;
    }

    private List<OCRData> getNextRow(OCRData left, OCRData right, Set<OCRData> set) {
        List<OCRData> row = new LinkedList<>();
        row.add(left);
        set.remove(left);
        set.remove(right);

        OCRData lowerLeft = findClosestInX(set, left);
        OCRData lowerRight = findClosestInX(set, right);

        OCRData next = findNext(left, right, lowerLeft, lowerRight, set);
        while (next != null) {
           set.remove(next);
           row.add(next);
           next = findNext(left, right, lowerLeft, lowerRight, set);
        }
        row.add(right);
        return row;
    }

    private OCRData findNext(OCRData left, OCRData right, OCRData lowerLeft, OCRData lowerRight,
                             Set<OCRData> set) {
        OCRData next = null;
        for (OCRData data : set) {
            if (data.getLeft() > left.getLeft() && data.getRight() < right.getRight() &&
                    (data.getBottom() < lowerLeft.getBottom() || data.getBottom() < lowerRight.getBottom())) {
                if (next == null) {
                    next = data;
                } else {
                    if (data.getLeft() < next.getLeft()) {
                       next = data;
                    }
                }
            }
        }
        return next;
    }


    private OCRData findNext(OCRData last, Set<OCRData> set) {
        OCRData ret = null;
        for (OCRData data : set) {
            if (Math.abs(data.getTop() - last.getTop()) < SORT_HEIGHT_THRESH) { // same line
                if (ret == null) {
                    ret = data;
                } else {
                    if (data.getLeft() < ret.getLeft()) {
                        ret = data;
                    }
                }
            }
        }
        if (ret == null) {
            return findTopLeft(set);
        } else {
            return ret;
        }
    }

    private OCRData findTopLeft(Set<OCRData> set ){
        OCRData ret = null;
        for (OCRData data : set) {
            if (ret == null) {
                ret = data;
            } else {
                if (data.getTop() < ret.getTop() && data.getLeft() - SORT_HEIGHT_THRESH < ret.getLeft()) {
                    ret = data;
                } else if (data.getTop() - SORT_HEIGHT_THRESH < ret.getTop() && data.getLeft() < ret.getLeft()
                && !(data.getTop() < ret.getTop() && data.getLeft() - SORT_HEIGHT_THRESH < ret.getLeft())) {
                    ret = data;
                }
            }
        }
        return ret;
    }

    private OCRData findClosestInX(Set<OCRData> set, OCRData ref) {
        OCRData ret = null;
        for (OCRData data : set) {
            if (ret == null) {
                ret = data;
            } else {
                if (Math.abs(data.getLeft() - ref.getLeft()) < Math.abs(ref.getLeft() - ret.getLeft())) {
                    ret = data;
                }
            }
        }
        return ret;
    }

    private OCRData findTopRight(Set<OCRData> set) {
        OCRData ret = null;
        for (OCRData data : set) {
            if (ret == null) {
                ret = data;
            } else {
                if ((data.getTop() - SORT_HEIGHT_THRESH <= ret.getTop()) && data.getRight() > ret.getRight()) {
                    ret = data;
                }
            }
        }
        return ret;
    }
  
    public List<SyllabusEvent> getSyllabusEvents(OCRResponse ocrResponse, String className) {
        // TODO: Complete me (Angel)
        // Useful links:
        // https://stackoverflow.com/questions/5270195/extracting-dates-from-text-in-java
        // https://stackoverflow.com/questions/16434724/how-to-extract-date-and-from-a-string-in-java
        // TODO: Look at example of creating event (delete this code later)

        Map<OCRData, List<DateGroup>> dataDateGroupMap = new HashMap<>();

        OCRData fullText = findFullText(ocrResponse);

        OCRResponse sortedResponse = resortData(ocrResponse, fullText);

        List<DateGroup> dategroups = new Parser().parse(fullText.getText());

        dategroups = refineDateGroups(dategroups);

//        dataDateGroupMap.put(fullText, dategroups);
//
//
//        DateGraph graph = new DateGraph(dategroups, sortedResponse, fullText);
//
//        return graph.getSyllabusEvents();

        int position = 0;
        int dateGroupInd = 0;
        List<SyllabusEvent> events = new LinkedList<>();
        if (dategroups.size() > 0) {
            DateGroup currentDG = dategroups.get(dateGroupInd);
            while (dateGroupInd <= dategroups.size()) {
                Date date = currentDG.getDates().get(0);
                date.setYear(119);
                date.setHours(0);
                date.setMinutes(0);
                SyllabusDate start = new SyllabusDate(date);
                Date endDate = (Date)date.clone();
                endDate.setHours(23);
                endDate.setMinutes(59);
                SyllabusDate end = new SyllabusDate(date);
                int startPos = fullText.getText().indexOf(currentDG.getText()) + currentDG.getText().length();
                int endPos = fullText.getText().length();
                if (++dateGroupInd < dategroups.size()) {
                    currentDG = dategroups.get(dateGroupInd);
                    endPos = fullText.getText().indexOf(currentDG.getText(), startPos);
                }
                if (endPos >= 0 && endPos <= fullText.getText().length()) {
                    String nextEvent = fullText.getText().substring(startPos, endPos).replace('\n', ' ');
                    events.add(new SyllabusEvent(nextEvent, start, end));
                }
            }
        }
        return events;
    }

    private static List<DateGroup> refineDateGroups(List<DateGroup> dgs) {
        List<DateGroup> refined = new LinkedList<>();
        for (DateGroup dg : dgs) {
            if (dg.getText().length() > 3) {
                String text = dg.getText();
                String refinedText = text.substring(text.indexOf('\n') + 1);
                dg.setText(refinedText);
                refined.add(dg);
            }
        }
        return refined;
    }

    private static OCRData findFullText(OCRResponse response) {
        OCRData ret = null;
        for (int i = 0; i < response.getNumData(); i++) {
            OCRData data = response.getData(i);
            if (ret == null || data.getText().length() > ret.getText().length()) {
                ret = data;
            }
        }
        return ret;
    }

}

