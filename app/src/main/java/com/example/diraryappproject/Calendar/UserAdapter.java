package com.example.diraryappproject.Calendar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.diraryappproject.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class UserAdapter extends BaseAdapter {
    String currentDateString;
    DateFormat dateFormat;
    private ArrayList<String> items;
    public static List<String> dayString;
    private Activity context;
    private GregorianCalendar selectDate;
    private java.util.Calendar month;
    public ArrayList<UserCollection> userCollectionList;

    public UserAdapter(Activity context, GregorianCalendar monthCalendar, ArrayList<UserCollection> userCollectionList) {
        this.userCollectionList = userCollectionList;
        UserAdapter.dayString = new ArrayList<String>();
        Locale.setDefault(Locale.KOREAN);
        month = monthCalendar;
        selectDate = (GregorianCalendar) monthCalendar.clone();
        this.context = context;
        month.set(GregorianCalendar.DAY_OF_MONTH,1);

        this.items = new ArrayList<String>();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.KOREAN);
        currentDateString = dateFormat.format(selectDate.getTime());
        refreshDays();

    }

    private void refreshDays() {

    }

    @Override
    public int getCount() {
        return dayString.size();
    }

    @Override
    public Object getItem(int position) {
        return dayString.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        TextView dayView;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.calendaraddeventitem,null);
        }

        return null;
    }
}
