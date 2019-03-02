package com.example.syllasnap.ocr;

import android.graphics.Bitmap;

import java.io.File;

public class OCRManager {

    private static OCRManager sInstance;

    // TODO: fill in constructor as appropriate
    private OCRManager() {

    }

    public static synchronized OCRManager getInstance() {
        if (sInstance == null) {
            sInstance = new OCRManager();
        }
        return sInstance;
    }

    // TODO: Choose one of the three to implement, whatever is easiest, I'm not sure.
    // Useful links:
    // https://cloud.google.com/vision/docs/libraries#client-libraries-install-java


    // TODO: Complete me (don't worry about the return type, I'll write a callback) assuming vision
    // API is async
    public void makeOCRRequest(Bitmap syllabusImage) {

    }

    // TODO: Complete me (don't worry about the return type, I'll write a callback) assuming vision
    // API is async
    public void makeOCRRequest(File syllabusImageFile) {

    }

    // TODO: Complete me (don't worry about the return type, I'll write a callback) assuming vision
    // API is async
    public void makeOCRRequest(String syllabusImageFilePath) {

    }
}
