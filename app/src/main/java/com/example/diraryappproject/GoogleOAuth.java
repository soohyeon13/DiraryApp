package com.example.diraryappproject;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class GoogleOAuth extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private com.google.api.services.calendar.Calendar mService = null;
    GoogleAccountCredential mCredential;
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};


    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION=1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    ProgressDialog mProgress;
    private TextView mResultText;

    //    public GoogleOAuth() {
//        getResultsFromApi();
//        mCredential = GoogleAccountCredential.usingOAuth2(
//                getApplicationContext(),
//                Arrays.asList(SCOPES)
//        ).setBackOff(new ExponentialBackOff());
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_oauth);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Google Calendar API 호출중...");
        mResultText = (TextView) findViewById(R.id.textview_main_result);
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(),
                Arrays.asList(SCOPES)
        ).setBackOff(new ExponentialBackOff());
        getResultsFromApi();

        mResultText.setVerticalScrollBarEnabled(true);
        mResultText.setMovementMethod(new ScrollingMovementMethod());

    }

    public String getResultsFromApi() {
        String output = null;
        if (!isGooglePlayServicesAvailable()) {
            acquirGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!isDeviceOnline()) {
            Toast.makeText(this, "네트워크 오류", Toast.LENGTH_SHORT).show();
        } else {
            new MakeRequestTask(this, mCredential).execute();
        }
        return null;
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data
    ){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(this,"앱을 실행하려면 구글 플레이가 필요",Toast.LENGTH_LONG);
                }else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if(resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences sharedPreferences =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(PREF_ACCOUNT_NAME,accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    private boolean isDeviceOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER
                );
            }
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "구글계정이 필요",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS
            );
        }
    }

    private void acquirGooglePlayServices() {
        GoogleApiAvailability googleApiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (googleApiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    private void showGooglePlayServicesAvailabilityErrorDialog(int connectionStatusCode) {
        GoogleApiAvailability googleApiAvailability =
                GoogleApiAvailability.getInstance();
        Dialog dialog = googleApiAvailability.getErrorDialog(
                GoogleOAuth.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES
        );
        dialog.show();
    }

    private boolean isGooglePlayServicesAvailable() {

        GoogleApiAvailability googleApiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                googleApiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints) {
    }

    public class MakeRequestTask extends AsyncTask<Void, Void, String> {
        private  GoogleOAuth mActivity;
        List<String> eventStrings = new ArrayList<String>();
        public MakeRequestTask(GoogleOAuth googleOAuth, GoogleAccountCredential mCredential) {
            mActivity = googleOAuth;

            HttpTransport httpTrnasport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            mService = new com.google.api.services.calendar.Calendar
                    .Builder(httpTrnasport,jsonFactory,mCredential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }

        @Override
        protected void onPreExecute() {
            mProgress.show();
            mResultText.setText("");
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                getEvent();
            }catch (Exception e) {
                return null;
            }
            return null;
        }

        private String getEvent() throws IOException {
            DateTime dateTime = new DateTime(System.currentTimeMillis());
            Events events = mService.events().list("primary")
                    .setMaxResults(100)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                eventStrings.add(String.format("%s \n (%s)", event.getSummary(), start));
            }
            return eventStrings.size() + "개의 데이터를 가져옴";
        }
        @Override
        protected void onPostExecute(String output) {
            mProgress.hide();
            mResultText.setText(TextUtils.join("\n\n", eventStrings));
        }
    }
}
