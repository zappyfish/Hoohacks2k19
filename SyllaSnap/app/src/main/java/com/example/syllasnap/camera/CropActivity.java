package com.example.syllasnap.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.syllasnap.HackyActivityGrabber;
import com.example.syllasnap.R;
import com.example.syllasnap.data.OCRResponse;
import com.example.syllasnap.ocr.OCRManager;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class CropActivity extends AppCompatActivity {

    private CropImageView mCropView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        HackyActivityGrabber.currentActivity = this;

        mCropView = (CropImageView)findViewById(R.id.cropImageView);
        mCropView.setImageBitmap(BitmapHolder.mBitmap);
        mCropView.invalidate();

        initUploadButton();
    }

    private void initUploadButton() {
        Button button = (Button)findViewById(R.id.crop_upload);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap cropped = mCropView.getCroppedImage();
                // Send it up to to the cloud now for OCR.
                try {
                    OCRManager.getInstance().makeOCRRequest(cropped, CropActivity.this, new OCRManager.OCRCallback() {
                        @Override
                        public void onOCRComplete(OCRResponse response) {

                        }
                    });
                } catch (Exception e) {
                    Log.d("ocr", e.toString());
                }
            }
        });
    }
}
