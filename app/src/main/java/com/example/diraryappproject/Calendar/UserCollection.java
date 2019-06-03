package com.example.diraryappproject.Calendar;

import java.util.ArrayList;

public class UserCollection {
    public static ArrayList<UserCollection> userCollectionList;
    private String description="";
    private String name="";
    private String date ="";

    public UserCollection(String date,String name,String description) {
        this.date = date;
        this.name =name;
        this.description = description;
    }
}
