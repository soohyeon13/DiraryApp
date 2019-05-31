package com.example.diraryappproject.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.diraryappproject.R;
import com.tyczj.extendedcalendarview.ExtendedCalendarView;

public class CalendarView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendarview);

        Intent intent = new Intent(this.getIntent());


        ExtendedCalendarView extendedCalendarView = findViewById(R.id.calendar);


    }

}
