package com.example.diraryappproject;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpConnection {
    private Activity activity;

    public HttpConnection(Activity activity) {
        this.activity = activity;
    }

    private String url_Home,url_Login,url_Day;

    public void setUrl_Home(String url_home){this.url_Home =url_home;}
    public void setUrl_Login(String url_login){this.url_Login=url_login;}
    public void setUrl_Day(String url_Day){this.url_Day=url_Day;}

    public void execute(String username,String userpassword) {
        HttpAsyncTask httpAsyncTask = new HttpAsyncTask();
        httpAsyncTask.execute(username,userpassword);

    }

    private class HttpAsyncTask extends AsyncTask<String,Void,Void> {
        @Override
        protected void onPreExecute() {super.onPreExecute();}
        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL url = new URL(url_Home);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("Content-Type","application/json;charset=UTF-8");

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
            } catch (MalformedURLException e) {e.printStackTrace();}
            catch (IOException e) {e.printStackTrace();}

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }
}
