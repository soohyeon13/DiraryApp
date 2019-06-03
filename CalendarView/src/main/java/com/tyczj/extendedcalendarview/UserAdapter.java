package com.tyczj.extendedcalendarview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
    private CalendarAdapter mAdapter;
    private String gridvalue;
    int firstDay;

    public UserAdapter(Activity context, GregorianCalendar monthCalendar, ArrayList<UserCollection> userCollectionList) {
        this.userCollectionList = userCollectionList;
        UserAdapter.dayString = new ArrayList<String>();
        Locale.setDefault(Locale.KOREAN);
        month = monthCalendar;
        selectDate = (GregorianCalendar) monthCalendar.clone();
        this.context = context;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);

        this.items = new ArrayList<String>();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);
        currentDateString = dateFormat.format(selectDate.getTime());
        mAdapter.refreshDays();

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
            view = layoutInflater.inflate(R.layout.calendaraddeventitem, null);
        }

        dayView = view.findViewById(R.id.date);
        String[] separatedTime = dayString.get(position).split("-");

        gridvalue = separatedTime[2].replaceFirst("^0*", "");
        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
            dayView.setTextColor(Color.parseColor("#A9A9A9"));
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            dayView.setTextColor(Color.parseColor("#A9A9A9"));
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else {
            dayView.setTextColor(Color.parseColor("#696969"));
        }

        if (dayString.get(position).equals(currentDateString)) {
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            view.setBackgroundColor(Color.parseColor("$ffffff"));
        }

        dayView.setText(gridvalue);

        String date = dayString.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (month.get(GregorianCalendar.MONTH)+1);
        if (monthStr.length() ==1) {
            monthStr = "0" +monthStr;
        }

        setEventView(view,position,dayView);


        return view;
    }

    private void setEventView(View view, int position, TextView dayView) {
        int len = UserCollection.userCollectionList.size();
        for (int i = 0; i < len; i++) {
            UserCollection calendarobj = UserCollection.userCollectionList.get(i);
            String date = calendarobj.date;
            int len1 = dayString.size();
            if (len1 > position) {
                if (dayString.get(position).equals(date)) {
                    if ((Integer.parseInt(gridvalue)>1) && (position<firstDay)) {

                    }else if ((Integer.parseInt(gridvalue)<7) && (position >28)) {

                    }else {
                        view.setBackgroundColor(Color.parseColor("#343434"));
                        view.setBackgroundResource(R.drawable.rounded_calendar);
                        dayView.setTextColor(Color.parseColor("#696969"));
                    }
                }
            }
        }
    }
}























