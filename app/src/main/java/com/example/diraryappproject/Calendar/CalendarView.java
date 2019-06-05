package com.example.diraryappproject.Calendar;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diraryappproject.R;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class CalendarView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {
    public GregorianCalendar calMonth, calMonthClone;
    private UserAdapter userAdapter;
    private TextView textMonth;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    ListView listItem;
    ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_calendar);

        UserCollection.userCollectionList = new ArrayList<UserCollection>();
        UserCollection.userCollectionList.add(new UserCollection("2019-06-05", "rlatngus", "test", "test"));

        calMonth = (GregorianCalendar) GregorianCalendar.getInstance();
        calMonthClone = (GregorianCalendar) calMonth.clone();
        userAdapter = new UserAdapter(CalendarView.this, calMonth, UserCollection.userCollectionList);

        listItem = findViewById(R.id.listItem);

        textMonth = findViewById(R.id.textMonth);
        textMonth.setText(android.text.format.DateFormat.format("yyyy년MM월", calMonth));

        initLayout();

        ImageButton previousMonth = findViewById(R.id.imgPrevBtn);
        previousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calMonth.get(GregorianCalendar.MONTH) == 4 && calMonth.get(GregorianCalendar.YEAR) == 2017) {
                    Toast.makeText(CalendarView.this, "날짜확인좀", Toast.LENGTH_SHORT).show();
                } else {
                    setPreviousMonth();
                    refreshCalendar();
                }
            }
        });
        ImageButton nextMonth = findViewById(R.id.imgNextBtn);
        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calMonth.get(GregorianCalendar.MONTH) == 5 && calMonth.get(GregorianCalendar.YEAR) == 2018) {
                    Toast.makeText(CalendarView.this, "날짜확인좀", Toast.LENGTH_SHORT).show();
                } else {
                    setNextMonth();
                    refreshCalendar();
                }
            }
        });
        GridView gridView = findViewById(R.id.gridCalendar);
        gridView.setAdapter(userAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectGridDate = UserAdapter.dayString.get(position);
                ((UserAdapter) parent.getAdapter()).getPositionList(selectGridDate, CalendarView.this);
            }
        });
        listViewAdapter = new ListViewAdapter();
        listItem.setAdapter(listViewAdapter);

    }

    private void setNextMonth() {
        if (calMonth.get(GregorianCalendar.MONTH) == calMonth.getActualMaximum(GregorianCalendar.MONTH)) {
            calMonth.set((calMonth.get(GregorianCalendar.YEAR) + 1), calMonth.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            calMonth.set(GregorianCalendar.MONTH, calMonth.get(GregorianCalendar.MONTH) + 1);
        }

    }

    private void refreshCalendar() {
        userAdapter.refreshDays();
        userAdapter.notifyDataSetChanged();
        textMonth.setText(android.text.format.DateFormat.format("yyyy년MM월", calMonth));


    }

    private void setPreviousMonth() {
        if (calMonth.get(GregorianCalendar.MONTH) == calMonth.getActualMinimum(GregorianCalendar.MONTH)) {
            calMonth.set((calMonth.get(GregorianCalendar.YEAR) - 1), calMonth.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            calMonth.set(GregorianCalendar.MONTH, calMonth.get(GregorianCalendar.MONTH) - 1);
        }
    }

    private void initLayout() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.dl_main_drawer_root);
        navigationView = findViewById(R.id.navView);
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close
        );
        drawerLayout.addDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "item1 select", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item2:
                Toast.makeText(this, "item2 select", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item3:
                Toast.makeText(this, "item3 select", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}

