package com.tyczj.extendedcalendarview;

import java.util.ArrayList;

public class UserCollection {
    public static ArrayList<UserCollection> userCollectionList;
    public String description="";
    public String name="";
    public String date ="";

    public UserCollection(String date,String name,String description) {
        this.date = date;
        this.name =name;
        this.description = description;
    }
}
