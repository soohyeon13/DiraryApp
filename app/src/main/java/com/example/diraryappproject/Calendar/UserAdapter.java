package com.example.diraryappproject.Calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.diraryappproject.ColorData;
import com.example.diraryappproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class UserAdapter extends BaseAdapter {
    private Activity context;
    private GregorianCalendar selectDate;
    private java.util.Calendar month;
    private String gridvalue;
    private GregorianCalendar pmonth;
    private GregorianCalendar pmonthMaxset;
    int firstDay;
    int maxWeekNumber;
    int maxPrev;
    int monthLength;
    int calMaxPrev;
    public static List<String> dayString;
    private ArrayList<String> items;
    String itemValue;
    String currentDateString;
    DateFormat dateFormat;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<UserCollection> custom = new ArrayList<UserCollection>();
    private ColorData colorData;


    public UserAdapter(Activity context, Calendar monthCalendar, RecyclerAdapter recyclerAdapter) {

        UserAdapter.dayString = new ArrayList<String>();
        Locale.setDefault(Locale.KOREAN);
        month = monthCalendar;
        selectDate = (GregorianCalendar) monthCalendar.clone();
        this.context = context;
        this.recyclerAdapter = recyclerAdapter;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);

        this.items = new ArrayList<String>();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);
        currentDateString = dateFormat.format(selectDate.getTime());
        refreshDays();

    }

    public void refreshDays() {
        items.clear();
        dayString.clear();
        Locale.setDefault(Locale.KOREAN);
        pmonth = (GregorianCalendar) month.clone();
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        maxWeekNumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        monthLength = maxWeekNumber * 7;
        maxPrev = getMaxPrev();
        calMaxPrev = maxPrev - (firstDay - 1);
        pmonthMaxset = (GregorianCalendar) pmonth.clone();
        pmonthMaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxPrev + 1);

        for (int i = 0; i < monthLength; i++) {
            itemValue = dateFormat.format(pmonthMaxset.getTime());
            pmonthMaxset.add(GregorianCalendar.DATE, 1);
            dayString.add(itemValue);
        }

    }

    private int getMaxPrev() {
        int maxPrev;
        if (month.get(GregorianCalendar.MONTH) == month.getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1), month.
                    getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH, month.get(GregorianCalendar.MONTH) - 1);
        }
        maxPrev = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        return maxPrev;
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
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        dayView.setText(gridvalue);

        String date = dayString.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }

        setEventView(view, position, dayView);

        return view;
    }

    private void setEventView(View view, int position, TextView dayView) {
        int len = UserCollection.getInstance().size();
        for (int i = 0; i < len; i++) {
            UserCollection calendarobj = UserCollection.getInstance().get(i);
            String date = calendarobj.getDate();
            int len1 = dayString.size();
            if (len1 > position) {
                if (dayString.get(position).equals(date)) {
                    if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {

                    } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {

                    } else {
                        view.setBackgroundColor(Color.parseColor("#343434"));
                        view.setBackgroundResource(R.drawable.rounded_calendar);
                        dayView.setTextColor(Color.parseColor("#696969"));
//                    else {
//                        RingDrawable ringDrawable = new RingDrawable(0,18,3,2);
//                        if (colorData == null) {
//                            ringDrawable.setColor(Color.parseColor("#ab250e"));
//                        }else {
//                        ringDrawable.setColor(colorData.getColor());
//                        }
//                        ringDrawable.setSize(1,0);
//                        view.setBackgroundColor(Color.parseColor("#343434"));
//                        view.setBackground(ringDrawable);
//                        dayView.setTextColor(Color.parseColor("#696969"));
                    }
                }
            }
        }
    }

    public void getPositionList(String date, final Activity activity) {
        int len = UserCollection.getInstance().size();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < len; i++) {
            if (UserCollection.getInstance().get(i).getDate().equals(date)) {
                HashMap<String, String> mapList = new HashMap<String, String>();
                mapList.put("hnames", UserCollection.getInstance().get(i).getTitle());
                mapList.put("hsubject", UserCollection.getInstance().get(i).getLocation());
                mapList.put("location", UserCollection.getInstance().get(i).getSubject());
                mapList.put("descript", UserCollection.getInstance().get(i).getDescription());
                JSONObject jsonObject = new JSONObject(mapList);
                jsonArray.put(jsonObject);
            }
        }
        if (jsonArray.length() != 0) {
            recyclerAdapter.setList(getMatchList(jsonArray + ""));
        }
    }

    private ArrayList<UserCollection> getMatchList(String s) {
        try {
            JSONArray jsonArray = new JSONArray(s);
            custom = new ArrayList<UserCollection>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);

                UserCollection userCollection = new UserCollection();

                userCollection.setTitle(jsonObject.optString("hnames"));
                userCollection.setLocation(jsonObject.optString("hsubject"));
                userCollection.setSubject((jsonObject.optString("location")));
                userCollection.setDescription(jsonObject.optString("descript"));
                custom.add(userCollection);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return custom;
    }
}























