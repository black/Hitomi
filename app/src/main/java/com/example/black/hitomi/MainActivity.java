package com.example.black.hitomi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.example.black.hitomi.JSONPackage.Downloader;

public class MainActivity extends AppCompatActivity {

    String jsonURL = "https://api.initiate.host/v1/hitomi.la/?type=&language=&sort=&page=&tag=&character=&group=&artist=";
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.myGridView);
        new Downloader(MainActivity.this,jsonURL,gridView).execute();
    }
}
