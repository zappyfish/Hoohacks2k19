package com.example.syllasnap.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.syllasnap.R;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropActivity extends AppCompatActivity {

    private static final String FILENAME = "syllabus.png";
    private CropImageView mCropView;

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
        Button button = (Button)findViewById(R.id.cropImageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap cropped = mCropView.getCroppedImage();
                // Send it up to to the cloud now for OCR.
            }
        });
    }
}
