package com.example.black.hitomi.JSONPackage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.black.hitomi.ComicsBaseAdapter;
import com.example.black.hitomi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser  extends AsyncTask<Void,Void, Boolean>{
    Context ctx;
    String jsonData;
    @SuppressLint("StaticFieldLeak")
    GridView gridView;
    ProgressDialog progressDialog;
    /*ArrayList<JSONObject> items = new ArrayList<>();*/
    JSONArray finalJSONArray;

    JSONParser(Context ctx, String jsonData, GridView gridView) {
        this.ctx = ctx;
        this.jsonData = jsonData;
        this.gridView = gridView;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return parse();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Parsing JSON");
        progressDialog.setMessage("Parsing... JSON... Please wait");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);
        progressDialog.dismiss();
        if(isParsed){
            //BIND
            ComicsBaseAdapter comicsBaseAdapter = new ComicsBaseAdapter(ctx, R.layout.comics,finalJSONArray);
            gridView.setAdapter(comicsBaseAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView tv = view.findViewById(R.id.comicid);
                    Toast.makeText(ctx,tv.getText(),Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(ctx,"Unable to parse json, check log",Toast.LENGTH_SHORT).show();

        }
    }

    private Boolean parse(){

        try {
            JSONObject jsonArray = new JSONObject(jsonData);
            finalJSONArray = jsonArray.getJSONArray("result");
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
