package com.example.diraryappproject.Calendar;

import java.util.ArrayList;
import java.util.List;

public class MemoCollection {
    private String name;
    private String location;
    private String description;
    private String date;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getLocation() {return location; }
    public void setLocation(String location) {this.location = location;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    private static List<MemoCollection> instance = new ArrayList<>();

    public static synchronized List<MemoCollection> getInstance() {return instance;}

    public static void add(MemoCollection memoCollection) { instance.add(memoCollection);}
    public static void remove(MemoCollection memoCollection) {instance.remove(memoCollection);}
}
