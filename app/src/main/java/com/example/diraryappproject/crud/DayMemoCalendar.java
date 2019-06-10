package com.example.diraryappproject.crud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.diraryappproject.Calendar.RecyclerAdaptor;
import com.example.diraryappproject.Calendar.UserCollection;
import com.example.diraryappproject.R;

public class DayMemoCalendar extends AppCompatActivity {
    EditText editDay,editLocation,editSubject,editDescription;
    Button datePickerBtn,timePickerBtn,submitBtn,cancelBtn;
    ImageButton imgPalette;

    RecyclerAdaptor recyclerAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daycalendar);


        editDay = findViewById(R.id.editDay);
        editDescription = findViewById(R.id.editDescription);
        editLocation = findViewById(R.id.editLocation);
        editSubject = findViewById(R.id.editSubject);

        datePickerBtn = findViewById(R.id.datePicker);
        timePickerBtn = findViewById(R.id.timePicker);
        submitBtn = findViewById(R.id.submitbtn);
        cancelBtn = findViewById(R.id.cancelbtn);

        imgPalette = findViewById(R.id.imgPalttBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String day = "2019-06-10";
                final String description = editDescription.getText().toString();
                final String location = editLocation.getText().toString();
                final String subject = editSubject.getText().toString();

                UserCollection.add(new UserCollection() {{
                    setSubject(location);
                    setDescription(description);
                    setName(subject);
                    setDate(day);
                }});
                Intent resultIntent = new Intent();
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
