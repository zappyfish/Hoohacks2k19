package com.example.syllasnap.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.syllasnap.MyAppWebViewClient;
import com.example.syllasnap.R;

public class ViewActivity extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.activity_main_webview);

        //Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // startActivity(new Intent(MainActivity.this, AuthActivity.class));
        mWebView.loadUrl("webviewJavascript");
    }
}

