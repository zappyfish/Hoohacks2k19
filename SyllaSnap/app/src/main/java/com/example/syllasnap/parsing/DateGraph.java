package com.example.syllasnap.parsing;

import com.example.syllasnap.data.OCRData;
import com.example.syllasnap.data.OCRResponse;
import com.example.syllasnap.data.SyllabusEvent;
import com.joestelmach.natty.DateGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DateGraph {

    private final List<DateNode> mNodes;
    private final List<SyllabusEvent> mSyllabusEvents;

    public DateGraph(List<DateGroup> dateGroups, OCRResponse allOCRData, OCRData fullText) {
        mNodes = new ArrayList<>();

        createNodes(allOCRData, fullText, dateGroups);

        // TODO: sort nodes
        sortNodesVertically();
        mSyllabusEvents = new LinkedList<>();
        for (DateNode node : mNodes) {
            mSyllabusEvents.add(node.getEventHorizontal(allOCRData));
        }
    }

    public List<SyllabusEvent> getSyllabusEvents() {
        return mSyllabusEvents;
    }

    private void sortNodesVertically() {

    }

    private void createNodes(OCRResponse allData, OCRData data, List<DateGroup> dateGroups) {
        for (DateGroup dateGroup : dateGroups) {
            if (dateGroup.getDates().size() == 1 && !exists(dateGroup.getDates().get(0))) {
                OCRData matchedData = matchToOCRData(allData, data, dateGroup);
                if (matchedData != null) {
                    mNodes.add(createNode(matchedData, dateGroup));
                }
            }
        }
    }

    private static DateNode createNode(OCRData data, DateGroup group) {
        int[] bounds = {data.getLeft(), data.getTop(), data.getRight(), data.getBottom()};

        int numRows = findNumRows(data.getText());
        int numCols = findNumCols(data.getText());

        int[] rowAndCol = new int[2]; // Row then col

        findRowAndCol(data.getText(), group, rowAndCol);

        return new DateNode(bounds, rowAndCol[0], rowAndCol[1], numCols, numRows, group);
    }

    private OCRData matchToOCRData(OCRResponse fullResponse, OCRData fullData, DateGroup group) {
        if (group.getText().length() == 1) {
            return null;
        }
        OCRData match = null;
        for (int i = 0; i < fullResponse.getNumData(); i++) {
            OCRData data = fullResponse.getData(i);
            if (data != fullData) {
                int splitCount = 0;
                String[] split = group.getText().split(" ");

                while (data.getText().contains(split[splitCount]) || split[splitCount].contains(data.getText())) {
                    if (splitCount == 0) {
                        match = data;
                    }
                    ++splitCount;
                    if (split.length == splitCount) {
                        return match;
                    }
                    i++;
                    if (i >= fullResponse.getNumData()) {
                        return null;
                    }
                    data = fullResponse.getData(i);
                }
                i -= splitCount; // go back
            }
        }
        return null;
    }



    private boolean exists(Date date ) {
        for (DateNode node : mNodes) {
            Date existing = node.getDateGroup().getDates().get(0);
            if (existing.getDay() == date.getDay() && existing.getMonth() == date.getMonth()) {
                return true;
            }
        }
        return false;
    }

    private static int findNumRows(String text) {
        int count = 1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n' && (i +1 ) != text.length()) count++;
        }
        return count;
    }

    private static int findNumCols(String text) {
        int max = 0;
        String[] lines = text.split("\n");
        for (String line : lines) {
            max = Math.max(max, line.length());
        }
        return max;
    }

    private static void findRowAndCol(String text, DateGroup dateGroup, int[] rowAndCol) {
        // Line is given by dateGroup. Look left to find row
        rowAndCol[0] = dateGroup.getLine();
        int position = dateGroup.getPosition();


    }

    // Comparators for x and y sorting for computing orientation
}
