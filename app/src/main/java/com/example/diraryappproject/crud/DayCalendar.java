package com.example.diraryappproject.crud;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.diraryappproject.data.UserCollection;
import com.example.diraryappproject.ColorData;
import com.example.diraryappproject.R;
import com.example.diraryappproject.data.User;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class DayCalendar extends AppCompatActivity implements ColorPickerDialogListener {
    EditText editTitle, editLocation, editSubject, editDescription;
    Button datePickerBtn, submitBtn, cancelBtn;
    TextView textColor;
    ImageButton imgPalette;
    private int mYear;
    private int mMonth;
    private int mDay;
    private static final int DIALOG_DEFAULT_ID = 0;
    private static final int DIALOG_PRESET_ID = 1;
    ColorData colorData;

    DatePickerDialog.OnDateSetListener listener;
    private String url_home;

    private int requestCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daycalendar);
//
//        Intent intent =getIntent();
//        requestCode = intent.

        url_home = getString(R.string.base_uri) +"/daycalendar/add";
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_WEEK);

        textColor = findViewById(R.id.textColor);

        editTitle = findViewById(R.id.editTitle);
        editTitle.setPrivateImeOptions("defaultInputmode=korean;");
        editDescription = findViewById(R.id.editDescription);
        editLocation = findViewById(R.id.editLocation);
        editSubject = findViewById(R.id.editSubject);

        datePickerBtn = findViewById(R.id.datePicker);
        submitBtn = findViewById(R.id.submitbtn);
        cancelBtn = findViewById(R.id.cancelbtn);

        imgPalette = findViewById(R.id.imgPalttBtn);

        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(DayCalendar.this,listener,mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });

        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                datePickerBtn.setText(year + "-" +"0"+ (month+1) + "-" + dayOfMonth);
            }
        };

        imgPalette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog.newBuilder()
                        .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                        .setAllowPresets(false)
                        .setDialogId(DIALOG_PRESET_ID)
                        .setColor(Color.parseColor("#ab250e"))
                        .setShowAlphaSlider(false)
                        .show(DayCalendar.this);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = editTitle.getText().toString();
                final String location = editLocation.getText().toString();
                final String subject = editSubject.getText().toString();
                final String description = editDescription.getText().toString();
                final String day = datePickerBtn.getText().toString();



                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("title",title);
                    jsonObject.put("eventLocation",location);
                    jsonObject.put("event_Subject",subject);
                    jsonObject.put("eventDescription",description);
                    jsonObject.put("eventStart",day);
                    jsonObject.put("user_id", User.getUser().getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println(jsonObject);
                new CalendarPost(url_home,jsonObject).execute();

                UserCollection.getInstance().add(new UserCollection() {{
                    setTitle(title);
                    setLocation(location);
                    setSubject(subject);
                    setDescription(description);
                    setDate(day);
                }});

                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    public void onColorSelected(int dialogId, final int color) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textColor.setBackgroundColor(color);
//                colorData.setColor(color);
            }
        });
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}


