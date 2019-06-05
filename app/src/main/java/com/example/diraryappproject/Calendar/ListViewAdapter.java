package com.example.diraryappproject.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diraryappproject.R;

import java.util.ArrayList;

class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItems = new ArrayList<ListViewItem>();
    private LayoutInflater inflater;

    public ListViewAdapter() {
    }
    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.daylistitem,parent,false);
        }

        ImageView imageView = convertView.findViewById(R.id.imgItem);
        TextView textViewTitle = convertView.findViewById(R.id.dayList);
        TextView textViewTitleDate = convertView.findViewById(R.id.dayDate);

        ListViewItem listViewItem = listViewItems.get(position);

        imageView.setImageDrawable(listViewItem.getDay());
        textViewTitle.setText(listViewItem.getTitle());
        textViewTitleDate.setText(listViewItem.getTitleDate());

        return convertView;
    }
}
