package com.example.diraryappproject;

import org.json.JSONObject;

public class User {
    private static JSONObject user = null;

    public static void setUser(JSONObject user) {
        User.user = user;
    }
    public static JSONObject getUser() {
        return user;
    }
}
