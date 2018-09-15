package com.example.black.hitomi.JSONPackage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connector {
    public static Object connect(String jsonURL){
        try {
            URL url = new URL(jsonURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(1500);
            connection.setReadTimeout(1500);
            connection.setDoInput(true);
            return connection;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Error"+e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error"+e.getMessage();
        }

    }
}
