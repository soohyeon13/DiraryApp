package com.example.diraryappproject.Calendar;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diraryappproject.R;

import java.util.ArrayList;

public class MemoRecyclerAdapter extends RecyclerView.Adapter<MemoRecyclerAdapter.ViewHolder> {

    private ArrayList<MemoCollection> customList;
    private Activity context;

    public MemoRecyclerAdapter(Activity context, ArrayList<MemoCollection> customList) {
        this.context = context;
        this.customList = customList;
    }

    @NonNull
    @Override
    public MemoRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item,viewGroup,false);
        MemoRecyclerAdapter.ViewHolder vh = new MemoRecyclerAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MemoRecyclerAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textTitle.setText("Title :" +customList.get(i).getName());
        viewHolder.textLocation.setText("Location :"+customList.get(i).getLocation());
        viewHolder.textDueDate.setText("Due Date :"+customList.get(i).getDate());
        viewHolder.textDescription.setText("Description :" + customList.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return customList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle,textDueDate,textDescription,textLocation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.tv_name);
            textDueDate = itemView.findViewById(R.id.tv_desc);
            textDescription = itemView.findViewById(R.id.tv_class);
            textLocation = itemView.findViewById(R.id.tv_location);
        }
    }
}
