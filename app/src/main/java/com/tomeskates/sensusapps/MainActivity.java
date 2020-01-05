package com.tomeskates.sensusapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private static final String TAG = "MainActivity";

    public SharedPreferences prefs;
    public SharedPreferences.Editor edit;
    public Intent it;

    EditText tUsername, tPassword;
    Button btnMasuk;
    TextView tTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        tUsername = findViewById(R.id.tUsername);
        tPassword = findViewById(R.id.tPassword);
        btnMasuk = findViewById(R.id.btnMasuk);

        prefs = getSharedPreferences("login", 0);
        if(prefs.getBoolean("isLogin", false)){
            it = new Intent(this, Dashboard.class);
            startActivity(it);
        }

        edit = prefs.edit();

        it = new Intent();
    }

    public void onMasuk(View v){
        tTitle = findViewById(R.id.tTitle);
        tTitle.setText("Masok");
        EditText txtUsername = findViewById(R.id.tUsername);
        String username = txtUsername.getText().toString();

        edit.putBoolean("isLogin", true);
        edit.putString("username", username);
        edit.commit();

        it = new Intent(this, Dashboard.class);
        startActivity(it);
    }
}
