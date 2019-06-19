package com.example.diraryappproject;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpConnection {
    private Activity activity;

    public HttpConnection(Activity activity) {
        this.activity = activity;
    }

    private String url_Home, url_Login, url_Day, body;

    public void setUrl_Home(String url_home) {
        this.url_Home = url_home;
    }

    public void setUrl_Login(String url_login) {
        this.url_Login = url_login;
    }

    public void setUrl_Day(String url_Day) {
        this.url_Day = url_Day;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void execute(String username, String userpassword) {
        HttpAsyncTask httpAsyncTask = new HttpAsyncTask();
        httpAsyncTask.execute(username, userpassword);

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
        HttpURLConnection httpURLConnection;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL url = new URL(url_Home + body);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("GET");

                InputStream is = httpURLConnection.getInputStream();
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append('\n');
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
            }

//            try {
//                URL url = new URL(url_Login);
//                httpURLConnection = (HttpURLConnection) url.openConnection();
//
//                httpURLConnection.setRequestMethod("GET");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//
//                Request();
//
//                OutputStream os = httpURLConnection.getOutputStream();
//                os.write(body.getBytes("UTF-8"));
//                os.flush();
//                os.close();
//
//                if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
//                    Log.d("LOG", "HTTP_OK를 받지 못했습니다.");
//                    return null;
//                }
//
//                InputStream is = httpURLConnection.getInputStream();
//                StringBuilder stringBuilder = new StringBuilder();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(line);
//                    stringBuilder.append('\n');
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                httpURLConnection.disconnect();
//            }
            return null;
        }

        private void Request() {
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }
}
