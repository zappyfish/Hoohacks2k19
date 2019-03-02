package com.example.syllasnap.auth;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.syllasnap.R;
import com.example.syllasnap.calendar.CalendarManager;
import com.example.syllasnap.test_activities.MaraTestActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.CalendarScopes;

import java.util.Collections;

public class AuthActivity extends AppCompatActivity {

    protected static final int REQUEST_CODE_RESOLUTION = 1;
    private static final int REQUEST_AUTHORIZATION = 1;
    private static final int REQUEST_ACCOUNT_PICKER = 2;

    private final HttpTransport m_transport = AndroidHttp.newCompatibleTransport();
    private final JsonFactory m_jsonFactory = GsonFactory.getDefaultInstance();
    GoogleAccountCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        credential = GoogleAccountCredential.usingOAuth2(this, Collections.singleton(CalendarScopes.CALENDAR));

        startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (credential.getSelectedAccountName() == null) {
            // ask user to choose account

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == REQUEST_ACCOUNT_PICKER || requestCode == REQUEST_CODE_RESOLUTION)) {
            if(resultCode == RESULT_OK) {
                if(data != null && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        credential.setSelectedAccountName(accountName);
                        CalendarManager.getInstance().setCredentials(credential);
                        startActivity(new Intent(AuthActivity.this, MaraTestActivity.class));
                    }
                }
                // call method to start accessing Google Drive
            } else { // CANCELLED
            }
        }
    }
}
