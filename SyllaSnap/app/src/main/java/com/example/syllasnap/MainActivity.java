package com.example.syllasnap;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.syllasnap.calendar.CalendarManager;
import com.example.syllasnap.camera.CameraActivity;
import com.example.syllasnap.test_activities.AngelTestActivity;
import com.example.syllasnap.test_activities.AnnaTestActivity;
import com.example.syllasnap.test_activities.MaraTestActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpTestActivityButton(R.id.start_camera, new Intent(MainActivity.this, CameraActivity.class));
        setUpTestActivityButton(R.id.button2, new Intent(MainActivity.this, GraphActivity.class));
        // startActivity(new Intent(MainActivity.this, AuthActivity.class));
//        WebView webView = (WebView) findViewById(R.id.webview);
//            webView.loadUrl( "https://env-132697.customer.cloud.microstrategy.com:443/MicroStrategy/servlet/mstrWeb?evt=3186&src=mstrWeb.3186&subscriptionID=9027B4B011E93D74DEE30080EF0579A1&Server=ENV-132697LAIOUSE1&Project=MicroStrategy%20Tutorial&Port=0&share=1&hiddensections=header,path,dockTop,dockLeft,footer");


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
