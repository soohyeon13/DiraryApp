package com.example.diraryappproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tyczj.extendedcalendarview.ExtendedCalendarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExtendedCalendarView calendar = (ExtendedCalendarView)findViewById(R.id.calendar);

    }
}
