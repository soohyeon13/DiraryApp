package com.example.diraryappproject.Calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.diraryappproject.R;

import java.util.ArrayList;

class DialogAdaptor extends BaseAdapter {
    private ArrayList<Dialogs> custom;
    private Activity context;

    public DialogAdaptor(Activity context, ArrayList<Dialogs> custom) {
        this.context = context;
        this.custom = custom;
    }

    @Override
    public int getCount() {
        return custom.size();
    }

    @Override
    public Object getItem(int position) {
        return custom.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View listViewItem = layoutInflater.inflate(R.layout.row_addapt,null,true);

        TextView textTitle = listViewItem.findViewById(R.id.tv_name);
        TextView textSubject = listViewItem.findViewById(R.id.tv_type);
        TextView textDueDate = listViewItem.findViewById(R.id.tv_desc);
        TextView textDescription = listViewItem.findViewById(R.id.tv_class);

        textTitle.setText("Title :" +custom.get(position).getTitles());
        textSubject.setText("Subject :" +custom.get(position).getSubjects());
        textDueDate.setText("Due Date :"+custom.get(position).getDuedates());
        textDescription.setText("Description :" + custom.get(position).getDescripts());

        return listViewItem;
    }
}
