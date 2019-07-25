package com.example.diraryappproject.data;

import java.util.List;
import java.util.Vector;

public class UserCollection {
    private String title;
    private String subject;
    private String description;
    private String name;
    private String location;
    private String date;

    public void setTitle(String value) {
        this.title = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setSubject(String value) {
        this.subject = value;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

    public void setLocation(String value) {
        this.location = value;
    }

    public String getLocation() {
        return this.location;
    }

    public void setDate(String value) {
        this.date = value;
    }

    public String getDate() {
        return this.date;
    }

    // TODO 좀 위험한 문법으로 보인다.
    private static List<UserCollection> instance;

    static {
        instance = new Vector<>();
    }

    public static List<UserCollection> getInstance() {
        return instance;
    }
}
