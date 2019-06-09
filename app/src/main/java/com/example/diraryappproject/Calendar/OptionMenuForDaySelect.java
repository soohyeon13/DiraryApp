package com.example.diraryappproject.Calendar;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.diraryappproject.R;

public class OptionMenuForDaySelect extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.crud_menu,menu);
        return true;
    }
}
