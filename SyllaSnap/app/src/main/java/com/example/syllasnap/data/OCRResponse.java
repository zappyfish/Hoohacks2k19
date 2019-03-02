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
}
