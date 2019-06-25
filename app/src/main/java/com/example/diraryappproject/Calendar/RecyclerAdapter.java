package com.example.diraryappproject.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.diraryappproject.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ScheduleViewHolder>{
    private ArrayList<UserCollection> custom;
    private Activity context;

    public RecyclerAdapter(Activity context, ArrayList<UserCollection> custom) {
        this.context = context;
        this.custom = custom;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item,viewGroup,false);
        ScheduleViewHolder vh = new ScheduleViewHolder(view, context);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder scheduleViewHolder, int i) {
        scheduleViewHolder.textTitle.setText("Title :" +custom.get(i).getTitle());
        scheduleViewHolder.textLocation.setText("Location :"+custom.get(i).getLocation());
        scheduleViewHolder.textSubject.setText("Subject :" +custom.get(i).getSubject());
        scheduleViewHolder.textDueDate.setText("Due Date :"+custom.get(i).getDate());
        scheduleViewHolder.textDescription.setText("Description :" + custom.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return custom.size();
    }


    public void setList(ArrayList<UserCollection> matchList) {
        this.custom = matchList;
        notifyDataSetChanged();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textTitle,textSubject,textDueDate,textDescription,textLocation;
        ImageButton imageButton,imageBtnUpdate;
        Activity activity;
        public ScheduleViewHolder(@NonNull View itemView, @NonNull Activity activity) {
            super(itemView);
            this.activity = activity;
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
                if(activity instanceof CalendarView){
                    ((CalendarView)activity).requestScheduleEdit(this);
                }
            }
        }

        public void removedAt(int itemId) {
            custom.remove(itemId);
            notifyItemRemoved(itemId);
            notifyItemRangeChanged(itemId,custom.size());
        }

        public void setSchedule(Bundle bundle) {
            String title = bundle.getString("title");
            textTitle.setText(title);
            String subject = bundle.getString("subject");
            textSubject.setText(subject);
            String duedate = bundle.getString("duedate");
            textDueDate.setText(duedate);
            String description = bundle.getString("description");
            textDescription.setText(description);
            String location = bundle.getString("location");
            textLocation.setText(location);

        }

        private Bundle packSchedule() {
            Bundle schedule = new Bundle();
            schedule.putString("title", textTitle.getText().toString());
            schedule.putString("subject",textSubject.getText().toString());
            schedule.putString("duedate",textDueDate.getText().toString());
            schedule.putString("description",textDescription.getText().toString());
            schedule.putString("location",textLocation.getText().toString());
            return schedule;
        }
    }
}
