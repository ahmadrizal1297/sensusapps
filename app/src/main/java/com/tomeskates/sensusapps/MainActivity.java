package com.tomeskates.sensusapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private static final String TAG = "MainActivity";

    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    public Intent it;

    EditText tUsername, tPassword;
    Button btnMasuk;

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

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = tUsername.getText().toString();
                final String password = tPassword.getText().toString();

                db.collection("users")
                    .whereEqualTo("username", username)
                    .whereEqualTo("password", password)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            QuerySnapshot snapshot = task.getResult();

                            Context context = getApplicationContext();
                            CharSequence msg = "Selamat datang! "+username;

                            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP, 0, 0);

                            if (snapshot.getDocuments().size() > 0) {
                                toast.show();
                                onMasuk();
                            } else {
                                msg = "Kombinasi email & password tidak ditemukan!";
                                toast.setText(msg);
                                toast.show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Context context = getApplicationContext();
                            CharSequence msg = "Error!, silahkan coba lagi.";
                            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP, 0, 0);
                            toast.show();
                        }
                });
            }
        });

    }

    protected void onMasuk(){
        EditText txtUsername = findViewById(R.id.tUsername);
        String username = txtUsername.getText().toString();

        edit.putBoolean("isLogin", true);
        edit.putString("username", username);
        edit.commit();

        it = new Intent(this, Dashboard.class);
        startActivity(it);

    }
}
