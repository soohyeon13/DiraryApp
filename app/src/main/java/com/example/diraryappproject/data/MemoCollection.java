package com.example.diraryappproject.data;

import java.util.List;
import java.util.Vector;

public class MemoCollection {
    private String title;
    private String location;
    private String description;
    private String date;

    public String getTitle() {return title;}
    public void setTitle(String name) {this.title= name;}
    public String getLocation() {return location; }
    public void setLocation(String location) {this.location = location;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    private static List<MemoCollection> instance;

    static {
        instance = new Vector<>();
    }

    public static synchronized List<MemoCollection> getInstance() {return instance;}
}
