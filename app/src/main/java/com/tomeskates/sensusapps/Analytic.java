package com.tomeskates.sensusapps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Analytic extends AppCompatActivity {

    private FirebaseFirestore db;
    private static final String TAG = "ViewData";

    public SharedPreferences prefs;
    public SharedPreferences.Editor edit;
    public Intent it;

    private Integer totalData, totalKK, totalPenduduk;

    TextView tTotalData, tTotalKK, tTotalPenduduk;
    Button btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analytic);

        tTotalData = findViewById(R.id.tTotalData);
        tTotalKK = findViewById(R.id.tTotalKK);
        tTotalPenduduk = findViewById(R.id.tTotalPenduduk);


        btnKembali = findViewById(R.id.btnKembali);

        db = FirebaseFirestore.getInstance();

        countData();

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onKembali();
            }
        });
    }

    protected void countData(){

        totalData = 0;
        totalKK = 0;
        totalPenduduk = 0;

        db.collection("sensus")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            for (DocumentSnapshot doc: task.getResult().getDocuments()) {

                                totalData +=1;
                                totalKK += Integer.parseInt(doc.get("jumlahkk").toString());
                                totalPenduduk += Integer.parseInt(doc.get("jumlahpenduduk").toString());
                                Log.w(TAG, "=>" + totalData + ", " + totalKK, task.getException());

                                tTotalData.setText(totalData.toString());
                                tTotalKK.setText(totalKK.toString());
                                tTotalPenduduk.setText(totalPenduduk.toString());

                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        Log.w(TAG, "=>" + totalData + ", " + totalKK);

    }


    protected void onKembali(){
        it = new Intent(this, Dashboard.class);
        startActivity(it);
    }
}
