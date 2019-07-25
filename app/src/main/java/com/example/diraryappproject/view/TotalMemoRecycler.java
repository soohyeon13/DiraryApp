package com.example.diraryappproject.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.diraryappproject.R;
import com.example.diraryappproject.adapter.TotalMemoAdapter;
import com.example.diraryappproject.data.MemoCollection;

import java.util.List;

public class TotalMemoRecycler extends AppCompatActivity {
    private RecyclerView memoRecycler;
    private TotalMemoAdapter totalMemoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_memo);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        memoRecycler = findViewById(R.id.recyclerList);

        totalMemoAdapter = new TotalMemoAdapter(this, getMemoCollectionList());
        memoRecycler.setLayoutManager(new LinearLayoutManager(this));
        memoRecycler.setAdapter(totalMemoAdapter);
    }

    private List<MemoCollection> getMemoCollectionList() {
        return MemoCollection.getInstance();
    }
}
