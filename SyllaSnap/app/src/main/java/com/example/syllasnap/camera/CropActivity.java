package com.example.syllasnap.camera;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.syllasnap.R;
import com.example.syllasnap.calendar.CalendarManager;
import com.example.syllasnap.data.OCRResponse;
import com.example.syllasnap.data.SyllabusEvent;
import com.example.syllasnap.ocr.OCRManager;
import com.example.syllasnap.parsing.SyllabusParser;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;

public class CropActivity extends AppCompatActivity {

    private CropImageView mCropView;
    private SyllabusParser mParser;
    private OCRManager.OCRCallback callback = new OCRManager.OCRCallback() {
        @Override
        public void onOCRComplete(OCRResponse response) {
            List<SyllabusEvent> events = mParser.getSyllabusEvents(response, "Test Class");
            Toast.makeText(CropActivity.this, "Found " + events.size() + " events", Toast.LENGTH_SHORT).show();
            for (final SyllabusEvent event : events) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(CropActivity.this);
                builder1.setMessage("Should I upload the following event?\n" +
                        event.getLine());
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CalendarManager.getInstance().uploadEventToCalendar(event, CropActivity.this);
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
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
        mParser = new SyllabusParser();
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
