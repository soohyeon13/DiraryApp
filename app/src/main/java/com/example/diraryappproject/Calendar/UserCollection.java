package com.example.diraryappproject.Calendar;

import java.util.ArrayList;

public class UserCollection {
    public static ArrayList<UserCollection> userCollectionList;
    public String subject = "";
    public String description = "";
    public String name = "";
    public String date = "";

    public UserCollection(String date, String name, String subject, String description) {
        this.date = date;
        this.name = name;
        this.subject = subject;
        this.description = description;
    }
}
