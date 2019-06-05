package com.example.diraryappproject.Calendar;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable dayDrawable;
    private String dayTitle;
    private String dayTitleDate;

    public void setDayDrawable(Drawable dayimage) {
        dayDrawable = dayimage;
    }

    public void setDayTitle(String title) {
        dayTitle = title;
    }

    public void setDayTitleDate(String titleDate) {
        dayTitleDate = titleDate;
    }

    public Drawable getDay() {
        return this.dayDrawable;
    }
    public String getTitle() {
        return this.dayTitle;
    }

    public String getTitleDate() {
        return this.dayTitleDate;
    }


}
