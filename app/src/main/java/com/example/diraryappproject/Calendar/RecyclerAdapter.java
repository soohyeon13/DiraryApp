package com.example.diraryappproject.Calendar;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.diraryappproject.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private ArrayList<UserCollection> custom;
    private Activity context;

    public RecyclerAdapter(Activity context, ArrayList<UserCollection> custom) {
        this.context = context;
        this.custom = custom;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item,viewGroup,false);
        RecyclerAdapter.ViewHolder vh = new RecyclerAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textTitle.setText("Title :" +custom.get(i).getName());
        viewHolder.textLocation.setText("Location :"+custom.get(i).getLocation());
        viewHolder.textSubject.setText("Subject :" +custom.get(i).getSubject());
        viewHolder.textDueDate.setText("Due Date :"+custom.get(i).getDate());
        viewHolder.textDescription.setText("Description :" + custom.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return custom.size();
    }


    public void setList(ArrayList<UserCollection> matchList) {
        this.custom = matchList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textTitle,textSubject,textDueDate,textDescription,textLocation;
        ImageButton imageButton,imageBtnUpdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.tv_name);
            textSubject = itemView.findViewById(R.id.tv_type);
            textDueDate = itemView.findViewById(R.id.tv_desc);
            textDescription = itemView.findViewById(R.id.tv_class);
            textLocation = itemView.findViewById(R.id.tv_location);
            imageButton = itemView.findViewById(R.id.imgItemRemove);
            imageButton.setOnClickListener(this);
            imageBtnUpdate = itemView.findViewById(R.id.imgItemUpdate);
            imageBtnUpdate.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.equals(imageButton)) {
                removedAt(getPosition());
            }
            if (v.equals(imageBtnUpdate)) {
            }
        }

        public void removedAt(int itemId) {
            custom.remove(itemId);
            notifyItemRemoved(itemId);
            notifyItemRangeChanged(itemId,custom.size());
        }
    }
}
