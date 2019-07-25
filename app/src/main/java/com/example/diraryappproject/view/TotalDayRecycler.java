package com.example.diraryappproject.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.diraryappproject.R;
import com.example.diraryappproject.adapter.TotalDayAdapter;
import com.example.diraryappproject.data.UserCollection;

import java.util.List;

public class TotalDayRecycler extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TotalDayAdapter totalDayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_day);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerList);

        totalDayAdapter = new TotalDayAdapter(this, getUserCollectionList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(totalDayAdapter);
    }

    private List<UserCollection> getUserCollectionList() {
        return UserCollection.getInstance();
    }
}

