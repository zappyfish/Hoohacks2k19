package com.example.syllasnap.camera;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.syllasnap.R;
import com.example.syllasnap.data.OCRResponse;
import com.example.syllasnap.ocr.OCRManager;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropActivity extends AppCompatActivity {

    private CropImageView mCropView;
    private OCRManager.OCRCallback callback = new OCRManager.OCRCallback() {
        @Override
        public void onOCRComplete(OCRResponse response) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

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
                    Toast.makeText(CropActivity.this, "Analyzing image...", Toast.LENGTH_SHORT).show();
                    OCRManager.getInstance().makeOCRRequest(cropped, CropActivity.this, callback);
                } catch (Exception e) {
                    Log.d("ocr", e.toString());
                }
            }
        });
    }
}
