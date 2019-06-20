package com.example.diraryappproject.Calendar;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diraryappproject.GoogleOAuth;
import com.example.diraryappproject.R;
import com.example.diraryappproject.crud.DayCalendar;
import com.example.diraryappproject.crud.MemoCalendar;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class CalendarView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener, View.OnClickListener {
    public GregorianCalendar calMonth, calMonthClone;
    private UserAdapter userAdapter;
    private TextView textMonth;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;

    private RecyclerView dialogRecyclerView;
    private RecyclerAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.drawer_calendar);



        setDialogRecyclerView();

//        for (int i = 0; i < 4; i++)
//            UserCollection.add(new UserCollection() {{
//                setDate("2019-06-05");
//                setName("김수현");
//                setLocation("406");
//                setDescription("test");
//                setSubject("컨피던스");
//            }});

        calMonth = (GregorianCalendar) GregorianCalendar.getInstance();
        calMonthClone = (GregorianCalendar) calMonth.clone();
        userAdapter = new UserAdapter(CalendarView.this, calMonth, recyclerAdapter);

        textMonth = findViewById(R.id.textMonth);
        textMonth.setText(DateFormat.format("yyyy년MM월", calMonth));

        initLayout();

        ImageButton previousMonth = findViewById(R.id.imgPrevBtn);
        previousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calMonth.get(GregorianCalendar.MONTH) == 4 && calMonth.get(GregorianCalendar.YEAR) == 2017) {
                    Toast.makeText(CalendarView.this, "날짜확인 test", Toast.LENGTH_SHORT).show();
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

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        GridView gridView = findViewById(R.id.gridCalendar);
        gridView.setAdapter(userAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectGridDate = UserAdapter.dayString.get(position);
                ((UserAdapter) parent.getAdapter()).getPositionList(selectGridDate, CalendarView.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                anim();
                break;
            case R.id.fab1:
                anim();
                Intent intent = new Intent(CalendarView.this, DayCalendar.class);
                startActivityForResult(intent, 200);
                break;
            case R.id.fab2:
                Intent intent1 = new Intent(CalendarView.this, MemoCalendar.class);
                startActivityForResult(intent1,300);
                anim();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        refreshCalendar();
    }

    public void anim() {
        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }

    private void setDialogRecyclerView() {
        dialogRecyclerView = findViewById(R.id.recyclerList);
        recyclerAdapter = new RecyclerAdapter(this, new ArrayList<UserCollection>());
        dialogRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dialogRecyclerView.setAdapter(recyclerAdapter);
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
            case R.id.google:
                Intent intent = new Intent(this, GoogleOAuth.class);
                startActivity(intent);
                break;
            case R.id.logout:
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

