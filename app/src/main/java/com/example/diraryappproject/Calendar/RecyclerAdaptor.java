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

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.ViewHolder>{
    private ArrayList<Recyclers> custom;
    private Activity context;

    public RecyclerAdaptor(Activity context, ArrayList<Recyclers> custom) {
        this.context = context;
        this.custom = custom;
    }

    @NonNull
    @Override
    public RecyclerAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item,viewGroup,false);
        RecyclerAdaptor.ViewHolder vh = new RecyclerAdaptor.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdaptor.ViewHolder viewHolder, int i) {

        viewHolder.textTitle.setText("Title :" +custom.get(i).getTitles());
        viewHolder.textSubject.setText("Subject :" +custom.get(i).getSubjects());
        viewHolder.textDueDate.setText("Due Date :"+custom.get(i).getDuedates());
        viewHolder.textDescription.setText("Description :" + custom.get(i).getDescripts());
    }

    @Override
    public int getItemCount() {
        return custom.size();
    }

    public void setList(ArrayList<Recyclers> matchList) {
        this.custom = matchList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle,textSubject,textDueDate,textDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.tv_name);
            textSubject = itemView.findViewById(R.id.tv_type);
            textDueDate = itemView.findViewById(R.id.tv_desc);
            textDescription = itemView.findViewById(R.id.tv_class);

        }
    }
}
