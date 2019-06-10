package com.example.diraryappproject.Calendar;

import java.util.ArrayList;
import java.util.List;

public class UserCollection {
    private String subject;
    private String description;
    private String name;
    private String date;

    public void setSubject(String value) {this.subject = value;}
    public String getSubject() {return this.subject;}
    public void setDescription(String value) {this.description = value;}
    public String getDescription() {return this.description;}
    public void setName(String value) {this.name = value;}
    public String getName() {return this.name;}
    public void setDate(String value) {this.date = value;}
    public String getDate() {return this.date;}

    private static List<UserCollection> instance = new ArrayList<>();

    public static synchronized List<UserCollection> getInstance() {
        return instance;
    }

    public static void add(UserCollection userCollection) { instance.add(userCollection); }
}
