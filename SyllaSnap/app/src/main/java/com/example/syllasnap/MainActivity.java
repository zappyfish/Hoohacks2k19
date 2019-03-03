package com.example.syllasnap;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.syllasnap.camera.CameraActivity;
import com.example.syllasnap.test_activities.AngelTestActivity;
import com.example.syllasnap.test_activities.AnnaTestActivity;
import com.example.syllasnap.test_activities.MaraTestActivity;

public class MainActivity extends Activity {

    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpTestActivityButton(R.id.start_camera, new Intent(MainActivity.this, CameraActivity.class));

        //
        mWebView = (WebView) findViewById(R.id.activity_main_webview);
    }


    private void setUpTestActivityButton(int btnId, final Intent startIntent) {
        Button button = (Button) findViewById(btnId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(startIntent);
            }
        });
    }

}
