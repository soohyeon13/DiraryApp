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

import com.example.diraryappproject.ColorData;
import com.example.diraryappproject.R;
import com.example.diraryappproject.data.MemoCollection;
import com.example.diraryappproject.data.User;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class MemoCalendar extends AppCompatActivity implements ColorPickerDialogListener {
    EditText editTitle, editLocation, editDescription;
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

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_memocalendar);

        editTitle = findViewById(R.id.editTitle);
        editTitle.setPrivateImeOptions("defaultInputmode=korean;");
        editLocation = findViewById(R.id.editLocation);
        editDescription = findViewById(R.id.editDescription);

        url_home = getString(R.string.base_uri) + "/memocalendar/add";

        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_WEEK);

        textColor = findViewById(R.id.textColor);

        datePickerBtn = findViewById(R.id.datePicker);
        submitBtn = findViewById(R.id.submitbtn);
        cancelBtn = findViewById(R.id.cancelbtn);

        imgPalette = findViewById(R.id.imgPalttBtn);

        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MemoCalendar.this, listener, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                datePickerBtn.setText(year + "-" + "0" + (month + 1) + "-" + dayOfMonth);
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
                        .show(MemoCalendar.this);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = editTitle.getText().toString();
                final String location = editLocation.getText().toString();
                final String description = editDescription.getText().toString();
                final String day = datePickerBtn.getText().toString();

                JSONObject jsonObject = new JSONObject();
                try{
                    jsonObject.put("memo_title",title);
                    jsonObject.put("memo_location",location);
                    jsonObject.put("memo_description",description);
                    jsonObject.put("memo_day",day);
                    jsonObject.put("user_id", User.getUser().getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(jsonObject);
                new CalendarPost(url_home,jsonObject).execute();

                MemoCollection.getInstance().add(new MemoCollection(){{
                    setTitle(title);
                    setLocation(location);
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
                colorData.setColor(color);
            }
        });
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}

