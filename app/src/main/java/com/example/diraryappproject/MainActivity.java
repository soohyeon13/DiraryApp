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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.diraryappproject.Calendar.CalendarView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private com.google.api.services.calendar.Calendar mService = null;
    private Button mAddCalendarButton;
    private Button mAddEventButton;
    private Button mGetEventButton;
    private TextView mStatusText;
    private TextView mResultText;
    private int mID = 0;
    ProgressDialog mProgress;
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    GoogleAccountCredential mCredential;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String PREF_ACCOUNT_NAME = "accountName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarView.class);
                startActivity(intent);
            }
        });

        mAddCalendarButton = findViewById(R.id.button_main_add_calendar);
        mAddEventButton = findViewById(R.id.button_main_add_event);
        mGetEventButton = findViewById(R.id.button_main_get_event);

        mStatusText = findViewById(R.id.textview_main_status);
        mResultText = findViewById(R.id.textview_main_result);

        mAddCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddCalendarButton.setEnabled(false);
                mStatusText.setText("");
                mID = 1;
                getResultsFromApi();
                mAddCalendarButton.setEnabled(true);
            }
        });

        mAddEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddEventButton.setEnabled(false);
                mStatusText.setText("");
                mID = 2;
//                String result = getResultsFromApi();
                getResultsFromApi();
                mAddEventButton.setEnabled(true);
//                if(result != null) {
//                    Intent intent = new Intent(MainActivity.this, WebActivity.class);
//                    intent.putExtra("uri", result);
//                    startActivity(intent);
//
//                }
            }
        });
        mGetEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetEventButton.setEnabled(false);
                mStatusText.setText("");
                mID = 3;
                getResultsFromApi();
                mGetEventButton.setEnabled(true);
            }
        });
        mResultText.setVerticalScrollBarEnabled(true);
        mResultText.setMovementMethod(new ScrollingMovementMethod());

        mStatusText.setVerticalScrollBarEnabled(true);
        mStatusText.setMovementMethod(new ScrollingMovementMethod());
        mStatusText.setText("버튼을 눌러 테스트");

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Google Calendar API 호출중");

        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(),
                Arrays.asList(SCOPES)
        ).setBackOff(new ExponentialBackOff());
    }

    private String getResultsFromApi() {
        String output = null;
        if (!isGooglePlayServicesAvailable()) {
            acquirGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!isDeviceOnline()) {
            mStatusText.setText("No network connection availale.");
        } else {
            new MakeRequestTask(this, mCredential).execute();
        }
        return null;
    }

    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    private String getCalendarID(String calendarTitle) {

        String id = null;
        String pageToken = null;
        do {
            CalendarList calendarList = null;
            try {

                calendarList = mService.calendarList().list().setPageToken(pageToken).execute();

            } catch (UserRecoverableAuthIOException e) {

                startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);

            } catch (IOException e) {

                e.printStackTrace();

            }
            List<CalendarListEntry> items = calendarList.getItems();


            for (CalendarListEntry calendarListEntry : items) {

                if (calendarListEntry.getSummary().equals(calendarTitle)) {

                    id = calendarListEntry.getId();
                }
            }
            pageToken = calendarList.getNextPageToken();
        } while (pageToken != null);

        return id;
    }


    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {

        // GET_ACCOUNTS 권한을 가지고 있다면
        if (EasyPermissions.hasPermissions(this, Manifest.permission.GET_ACCOUNTS)) {


            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {

                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {


                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }



        } else {


            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }


    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    mStatusText.setText(" 앱을 실행시키려면 구글 플레이 서비스가 필요합니다."
                            + "구글 플레이 서비스를 설치 후 다시 실행하세요.");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
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

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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

    private void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
        GoogleApiAvailability googleApiAvailability =
                GoogleApiAvailability.getInstance();
        Dialog dialog = googleApiAvailability.getErrorDialog(
                MainActivity.this,
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

    public class MakeRequestTask extends AsyncTask<Void, Void, String> {

        private Exception mLastError = null;
        private MainActivity mActivity;
        List<String> eventStrings = new ArrayList<String>();


        public MakeRequestTask(MainActivity activity, GoogleAccountCredential credential) {

            mActivity = activity;

            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            mService = new com.google.api.services.calendar.Calendar
                    .Builder(transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }


        @Override
        protected void onPreExecute() {

            mProgress.show();
            mStatusText.setText("데이터 가져오는 중...");
            mResultText.setText("");
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                if (mID == 1) {
                    return createCalendar();
                } else if (mID == 2) {
                    return addEvent();
                } else if (mID == 3) {
                    return getEvent();
                }
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
            return null;
        }
        private String getEvent() throws IOException {
            DateTime now = new DateTime(System.currentTimeMillis());
            String calendarID = getCalendarID("CalendarTitle");
            if (calendarID == null) {
                return "캘린더를 먼저 생성하세요.";
            }
            Events events = mService.events().list(calendarID)//"primary")
                    .setMaxResults(10)
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
            return eventStrings.size() + "개의 데이터를 가져왔습니다.";
        }
        private String createCalendar() throws IOException {
            String ids = getCalendarID("CalendarTitle");
            if (ids != null) {
                return "이미 캘린더가 생성되어 있습니다. ";
            }
            com.google.api.services.calendar.model.Calendar calendar = new Calendar();
            calendar.setSummary("CalendarTitle");
            calendar.setTimeZone("Asia/Seoul");
            Calendar createdCalendar = mService.calendars().insert(calendar).execute();
            String calendarId = createdCalendar.getId();
            CalendarListEntry calendarListEntry = mService.calendarList().get(calendarId).execute();
            calendarListEntry.setBackgroundColor("#0000ff");
            CalendarListEntry updatedCalendarListEntry =
                    mService.calendarList()
                            .update(calendarListEntry.getId(), calendarListEntry)
                            .setColorRgbFormat(true)
                            .execute();
            return "캘린더가 생성되었습니다.";
        }
        @Override
        protected void onPostExecute(String output) {
            mProgress.hide();
            mStatusText.setText(output);
            if (mID == 3) mResultText.setText(TextUtils.join("\n\n", eventStrings));
        }
        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            MainActivity.REQUEST_AUTHORIZATION);
                } else {
                    mStatusText.setText("MakeRequestTask The following error occurred:\n" + mLastError.getMessage());
                }
            } else {
                mStatusText.setText("요청 취소됨.");
            }
        }
        private String addEvent() {
            String calendarID = getCalendarID("CalendarTitle");
            if (calendarID == null) {
                return "캘린더를 먼저 생성하세요.";
            }
            Event event = new Event()
                    .setSummary("구글 캘린더 테스트")
                    .setLocation("서울시")
                    .setDescription("캘린더에 이벤트 추가하는 것을 테스트합니다.");
            java.util.Calendar calander;
            calander = java.util.Calendar.getInstance();
            SimpleDateFormat simpledateformat;
            simpledateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+09:00", Locale.KOREA);
            String datetime = simpledateformat.format(calander.getTime());
            DateTime startDateTime = new DateTime(datetime);
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("Asia/Seoul");
            event.setStart(start);
            Log.d("@@@", datetime);

            DateTime endDateTime = new DateTime(datetime);
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("Asia/Seoul");
            event.setEnd(end);

            try {
                event = mService.events().insert(calendarID, event).execute();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "Exception : " + e.toString());
            }
            System.out.printf("Event created: %s\n", event.getHtmlLink());
            Log.e("Event", "created : " + event.getHtmlLink());
            String eventStrings = "created : " + event.getHtmlLink();

            return eventStrings;
        }
    }


}
