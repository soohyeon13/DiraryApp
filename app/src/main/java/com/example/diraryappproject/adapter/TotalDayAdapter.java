package com.example.diraryappproject.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diraryappproject.R;
import com.example.diraryappproject.data.UserCollection;

import java.util.List;

public class TotalDayAdapter extends RecyclerView.Adapter<TotalDayAdapter.ViewHolder> {
    private Activity context;
    private List<UserCollection> dayList;

    public TotalDayAdapter(Activity context, List<UserCollection> dayList) {
        this.context = context;
        this.dayList = dayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_total_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(view, context);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.totalTitle.setText("제목:" + " " + dayList.get(i).getTitle());
        viewHolder.totalDate.setText("날짜:" + " " + dayList.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView totalTitle, totalDate;

        public ViewHolder(@NonNull View itemView, Activity context) {
            super(itemView);
            totalTitle = itemView.findViewById(R.id.total_title);
            totalDate = itemView.findViewById(R.id.total_date);
        }
    }
}
