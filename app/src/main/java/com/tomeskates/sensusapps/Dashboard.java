package com.tomeskates.sensusapps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class Dashboard extends AppCompatActivity {

    public SharedPreferences prefs;
    public SharedPreferences.Editor edit;
    public Intent it;
    TextView tUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        TextView tUsername = findViewById(R.id.tUsername);
        prefs = getSharedPreferences("login", 0);
        String username = prefs.getString("username", null);

        String json = prefs.getString("json", "{}");

        JSONObject objJSON;

        /*
        try{
            objJSON = new JSONObject((json));
            String name = objJSON.getString("name");
            int level = objJSON.getInt("level");
            boolean action = objJSON.getBoolean("active");

            username = "Halo, "+name+"!";
        }catch(JSONException e){
            e.printStackTrace();
        }

        tUsername.setText("Halo, "+username+"!");
         */
    }

    public void onKeluar(View v){
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();

        Intent it = new Intent(this, MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(it);
        finish();
    }

    public void onTambah(View v){
        it = new Intent(this, AddData.class);
        startActivity(it);
    }

    public void onLihat(View v){
        it = new Intent(this, ViewData.class);
        startActivity(it);
    }

    public void onAnalisa(View v){
        it = new Intent(this, Analytic.class);
        startActivity(it);
    }

}
