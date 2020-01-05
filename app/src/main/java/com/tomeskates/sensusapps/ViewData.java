package com.tomeskates.sensusapps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ViewData extends AppCompatActivity {

    public SharedPreferences prefs;
    public SharedPreferences.Editor edit;
    public Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_data);
    }
}
