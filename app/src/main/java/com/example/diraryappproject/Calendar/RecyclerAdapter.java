package com.example.diraryappproject.Calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diraryappproject.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<String> mData;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textTitle;
        TextView textDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgItem);
            textTitle = itemView.findViewById(R.id.dayList);
            textDate = itemView.findViewById(R.id.dayDate);
        }
    }
    RecyclerAdapter(ArrayList<String> list) {
        mData = list;
    }
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.recycler_item,viewGroup,false);
        RecyclerAdapter.ViewHolder vh = new RecyclerAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, int i) {
        String textTitle = mData.get(i);
        String textDate = mData.get(i);
        viewHolder.textTitle.setText(textTitle);
        viewHolder.textDate.setText(textDate);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
