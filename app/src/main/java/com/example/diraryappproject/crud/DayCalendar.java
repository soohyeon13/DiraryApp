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

import com.example.diraryappproject.Calendar.UserCollection;
import com.example.diraryappproject.R;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

import java.util.Calendar;

public class DayCalendar extends AppCompatActivity implements ColorPickerDialogListener {
    EditText editTitle, editLocation, editSubject, editDescription;
    Button datePickerBtn, timePickerBtn, submitBtn, cancelBtn;
    TextView textColor;
    ImageButton imgPalette;
    private int mYear;
    private int mMonth;
    private int mDay;
    private static final int DIALOG_DEFAULT_ID = 0;
    private static final int DIALOG_PRESET_ID = 1;

    DatePickerDialog.OnDateSetListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daycalendar);

        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_WEEK);

        textColor = findViewById(R.id.textColor);

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        editLocation = findViewById(R.id.editLocation);
        editSubject = findViewById(R.id.editSubject);

        datePickerBtn = findViewById(R.id.datePicker);
        timePickerBtn = findViewById(R.id.timePicker);
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

        timePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                UserCollection.add(new UserCollection() {{
                    setName(title);
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

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}
