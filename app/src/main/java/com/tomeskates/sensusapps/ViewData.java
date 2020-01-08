package com.tomeskates.sensusapps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {

    private FirebaseFirestore db;
    private static final String TAG = "ViewData";

    public SharedPreferences prefs;
    public SharedPreferences.Editor edit;
    public Intent it;

    private RecyclerView recyclerView;
    private RecyclerviewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Sensus> list;

    TextView tUserView;
    EditText tCari;
    Button btnCari, btnEdit, btnHapus;

    String cari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_data);

        prefs = getSharedPreferences("login", 0);
        String username = prefs.getString("username", null);

        cari = "";

        tUserView = findViewById(R.id.tUserView);
        tUserView.setText(username);
        tUserView.setVisibility(View.INVISIBLE);

        tCari = findViewById(R.id.tCari);
        btnCari = findViewById(R.id.btnCari);

        //btnEdit = findViewById(R.id.btnEdit);
        //btnHapus = findViewById(R.id.btnHapus);

        db = FirebaseFirestore.getInstance();

        list = new ArrayList<>();

        retrieveData();

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cari = tCari.getText().toString();

                if(cari.equals("")){
                    retrieveData();
                }else{
                    searchData();
                }
            }
        });

/*
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(TAG, "editaksi ");
            }
        });*/
    }

    protected void searchData(){

        db.collection("sensus")
                .whereEqualTo("kelurahan", cari)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.w(TAG, "pencarian " + cari);

                            list.clear();

                            for (DocumentSnapshot doc: task.getResult().getDocuments()) {
                                Sensus sensus = new Sensus();
                                sensus.setSensusid(doc.getId());
                                sensus.setProvinsi(doc.get("provinsi").toString());
                                sensus.setKota(doc.get("kota").toString());
                                sensus.setKecamatan(doc.get("kecamatan").toString());
                                sensus.setKelurahan(doc.get("kelurahan").toString());
                                sensus.setRt(Integer.parseInt(doc.get("rt").toString()));
                                sensus.setRw(Integer.parseInt(doc.get("rw").toString()));
                                sensus.setJumlahkk(Integer.parseInt(doc.get("jumlahkk").toString()));
                                sensus.setJumlahpenduduk(Integer.parseInt(doc.get("jumlahpenduduk").toString()));
                                sensus.setUser(doc.get("user").toString());
                                sensus.setDatetime(doc.get("datetime").toString());

                                Log.w(TAG, "datasensus2 => " + sensus, task.getException());

                                list.add(sensus);
                            }

                            recyclerView = findViewById(R.id.MyRecyclerview);
                            layoutManager = new LinearLayoutManager(getApplicationContext());
                            adapter = new RecyclerviewAdapter(list);

                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    protected void retrieveData(){

        db.collection("sensus")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        list.clear();

                        for (DocumentSnapshot doc: task.getResult().getDocuments()) {
                            Sensus sensus = new Sensus();
                            sensus.setSensusid(doc.getId());
                            sensus.setProvinsi(doc.get("provinsi").toString());
                            sensus.setKota(doc.get("kota").toString());
                            sensus.setKecamatan(doc.get("kecamatan").toString());
                            sensus.setKelurahan(doc.get("kelurahan").toString());
                            sensus.setRt(Integer.parseInt(doc.get("rt").toString()));
                            sensus.setRw(Integer.parseInt(doc.get("rw").toString()));
                            sensus.setJumlahkk(Integer.parseInt(doc.get("jumlahkk").toString()));
                            sensus.setJumlahpenduduk(Integer.parseInt(doc.get("jumlahpenduduk").toString()));
                            sensus.setUser(doc.get("user").toString());
                            sensus.setDatetime(doc.get("datetime").toString());

                            Log.w(TAG, "datasensus1 => " + sensus, task.getException());

                            list.add(sensus);
                        }

                        recyclerView = findViewById(R.id.MyRecyclerview);
                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        adapter = new RecyclerviewAdapter(list);

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
    }

}
