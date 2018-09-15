package com.example.black.hitomi.JSONPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Downloader extends AsyncTask<Void,Void,String> {
    Context ctx;
    String jsonURL;
    GridView gridView;
    ProgressDialog progressDialog;

    public Downloader(Context ctx, String jsonURL, GridView gridView) {
        this.ctx = ctx;
        this.jsonURL = jsonURL;
        this.gridView = gridView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Download JSON");
        progressDialog.setMessage("Downloading... JSON... Please wait");
        progressDialog.show();

    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);
        progressDialog.dismiss();
        if(jsonData.startsWith("Error")){
            String error = jsonData;
            Toast.makeText(ctx,error,Toast.LENGTH_LONG).show();
        }else {
            // PARSE JSON DATA
            new JSONParser(ctx,jsonData,gridView).execute();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        return download();
    }

    private String download(){
        Object connection = Connector.connect(jsonURL); // from Connector Object Class
        if(connection.toString().startsWith("Error")){
            return connection.toString();
        }

        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        try {
            if(httpURLConnection.getResponseCode()== HttpURLConnection.HTTP_OK){
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                StringBuffer jsonData = new StringBuffer();
                while((line=bufferedReader.readLine())!=null){
                    jsonData.append(line+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                return jsonData.toString();
            }else {
                return "Error "+httpURLConnection.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error" + e.getMessage();
        }
    }
}
