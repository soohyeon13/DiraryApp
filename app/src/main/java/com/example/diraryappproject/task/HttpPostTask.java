package com.example.diraryappproject.task;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpPostTask extends AsyncTask<Void, Void, String>  {
    private final String url_signin;
    private final String url_authorization;

    public HttpPostTask(String url_signin, String url_authorization) {
        this.url_signin = url_signin;
        this.url_authorization = url_authorization;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url_signin).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", url_authorization);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while((line = br.readLine()) != null)
                sb.append(line).append('\n');
            result = sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
