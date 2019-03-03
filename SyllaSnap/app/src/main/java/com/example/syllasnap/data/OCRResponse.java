package com.example.syllasnap.data;

import java.util.ArrayList;
import java.util.List;

public class OCRResponse {

    private final List<OCRData> mOCRDataList;

    public OCRResponse() {
        mOCRDataList = new ArrayList<>();
    }

    public OCRData getData(int pos) {
        return mOCRDataList.get(pos);
    }

    public void addData(OCRData data) {
        mOCRDataList.add(data);
    }

    public int getNumData() {
        return mOCRDataList.size();
    }

    public boolean contains(OCRData data) {
        for (OCRData check : mOCRDataList) {
            if (data == check) {
                return true;
            }
        }
        return false;
    }
}
