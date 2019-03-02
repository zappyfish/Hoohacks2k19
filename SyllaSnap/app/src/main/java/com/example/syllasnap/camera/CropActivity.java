package com.example.syllasnap.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;

import com.example.syllasnap.R;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropActivity extends AppCompatActivity {

    private static final String FILENAME = "syllabus.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        CropImageView cropView = (CropImageView)findViewById(R.id.cropImageView);
        cropView.setImageBitmap(BitmapHolder.mBitmap);
        cropView.invalidate();

    }
}
