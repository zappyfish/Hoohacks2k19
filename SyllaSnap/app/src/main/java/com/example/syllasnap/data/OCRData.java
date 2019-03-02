package com.example.syllasnap.data;

public class OCRData {

    private final int mLeft;
    private final int mRight;
    private final int mTop;
    private final int mBottom;

    private final String mText;

    public OCRData(String text, int left, int right, int top, int bottom) {
        mText = text;
        mLeft = left;
        mRight = right;
        mTop = top;
        mBottom = bottom;
    }

    public String getText() {
        return mText;
    }

    public int getLeft() {
        return mLeft;
    }

    public int getRight() {
        return mRight;
    }

    public int getTop() {
        return mTop;
    }

    public int getBottom() {
        return mBottom;
    }

}
