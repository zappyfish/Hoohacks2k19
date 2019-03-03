package com.example.syllasnap.parsing;

import com.example.syllasnap.data.OCRData;
import com.example.syllasnap.data.OCRResponse;
import com.example.syllasnap.data.SyllabusDate;
import com.example.syllasnap.data.SyllabusEvent;
import com.joestelmach.natty.DateGroup;

import java.util.Date;

public class DateNode {

    private final int x;
    private final int y;
    private final int top;
    private final int bottom;
    private final DateGroup mDateGroup;

    public DateNode(int[] bounds, int line, int column, int numCols, int numRows, DateGroup dateGroup) {
        x = bounds[0] + line * getSizePerSpace(bounds[0], bounds[2], numCols);
        y = bounds[1] + column * getSizePerSpace(bounds[1], bounds[3], numRows);
        top = bounds[1];
        bottom = bounds[3];
        mDateGroup = dateGroup;
    }

    private static int getSizePerSpace(int min, int max, int spaces) {
        return (max - min) / spaces;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public DateGroup getDateGroup() {
        return mDateGroup;
    }

    // TODO: for now, assume orientation is vertical. We'll
    public SyllabusEvent getEventHorizontal(OCRResponse allOCRData) {
        String eventDescription = "";

        for (int i = 0; i < allOCRData.getNumData(); i++) {
            OCRData data = allOCRData.getData(i);
            if (onSameLine(data)) {
                eventDescription += data.getText() + " ";
            }
        }


        Date dayOfEvent = mDateGroup.getDates().get(0);
        SyllabusDate start = new SyllabusDate(dayOfEvent);
        SyllabusDate end = new SyllabusDate(dayOfEvent);
        return new SyllabusEvent(eventDescription.trim(), start, end);
    }

    private boolean surrounds(OCRData data) {
        return top <= data.getTop() && bottom >= data.getBottom();
    }

    private boolean surroundedBy(OCRData data) {
        return data.getTop() <= top && data.getBottom() >= bottom;
    }

    private boolean onSameLine(OCRData data) {
        float thresh = 30;
        return Math.abs(data.getTop() - top) < thresh  && Math.abs(data.getBottom() - bottom) < thresh;
    }

    private int getToStart() {
        int line = 1;
        while (line != mDateGroup.getLine()) {
            line++;
        }
        line += mDateGroup.getPosition();
        return line;
    }
}
