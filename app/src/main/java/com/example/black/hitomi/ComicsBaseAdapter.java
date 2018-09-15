package com.example.black.hitomi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.black.hitomi.JSONPackage.ImageLoadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ComicsBaseAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private JSONArray jsonArray;

    public ComicsBaseAdapter(Context context, int layout, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            view = inflater.inflate(layout,null);
            TextView title = view.findViewById(R.id.comictitle);
            TextView time = view.findViewById(R.id.time);
            TextView comicsid = view.findViewById(R.id.comicid);
            ImageView thumbnail = view.findViewById(R.id.comicthumbnail);
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(position);
                try {
                    title.setText(jsonObject.getString("name"));
                    time.setText(formatTime(jsonObject.getString("time")));
                    comicsid.setText(jsonObject.getString("id"));
                    new ImageLoadTask(jsonObject.getString("image"),thumbnail).execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return view;
    }

    private String formatTime(String time){
        SimpleDateFormat  input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        input.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = input.parse(time);
            return output.format(date)+"";
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
