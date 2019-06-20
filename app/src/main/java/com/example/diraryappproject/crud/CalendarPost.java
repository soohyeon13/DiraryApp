package com.example.diraryappproject.crud;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CalendarPost extends AsyncTask<Void,Void,String> {

    private final String url;
    private final JSONObject jsonObject;

    public CalendarPost(String url, JSONObject jsonObject) {
        this.url = url;
        this.jsonObject = jsonObject;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            URL urlconn = new URL(url);
            HttpURLConnection httpPost = (HttpURLConnection) urlconn.openConnection();
            String json = "";
            json = jsonObject.toString();
            httpPost.setRequestMethod("POST");
            httpPost.setRequestProperty("Accept", "application/json");
            httpPost.setRequestProperty("Content-type", "application/json");
            httpPost.setRequestProperty("Charset", "UTF-8");
            httpPost.setDoOutput(true);
            httpPost.setDoInput(true);

            OutputStream os = httpPost.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(httpPost.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while((line = br.readLine()) != null)
                sb.append(line).append('\n');

            System.out.println(sb.toString());
            httpPost.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
