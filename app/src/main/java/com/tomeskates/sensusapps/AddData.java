package com.tomeskates.sensusapps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddData extends AppCompatActivity {

    private FirebaseFirestore db;
    private static final String TAG = "AddData";

    public SharedPreferences prefs;
    public SharedPreferences.Editor edit;
    public Intent it;

    EditText tProvinsi, tKota, tKecamatan, tKelurahan, tRW, tRT, tJumlahKK, tJumlahPenduduk;
    Button btnTambah, btnKembali;

    Date currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);

        tProvinsi = findViewById(R.id.tAlamat);
        tKota = findViewById(R.id.tKota);
        tKecamatan = findViewById(R.id.tKecamatan);
        tKelurahan = findViewById(R.id.tKelurahan);
        tRW = findViewById(R.id.tRW);
        tRT = findViewById(R.id.tRT);
        tJumlahKK = findViewById(R.id.LabelKK);
        tJumlahPenduduk = findViewById(R.id.tJumlahPenduduk);

        btnTambah = findViewById(R.id.btnTambah);
        btnKembali = findViewById(R.id.btnKembali);

        db = FirebaseFirestore.getInstance();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSimpan();
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onKembali();
            }
        });

    }

    protected void onSimpan(){
        String provinsi, kota, kecamatan, kelurahan;
        Integer rw, rt, jmlkk, jmlpenduduk;

        prefs = getSharedPreferences("login", 0);
        String username = prefs.getString("username", null);

        provinsi = tProvinsi.getText().toString();
        kota = tKota.getText().toString();
        kecamatan = tKecamatan.getText().toString();
        kelurahan = tKelurahan.getText().toString();

        rw = Integer.parseInt(tRW.getText().toString());
        rt = Integer.parseInt(tRT.getText().toString());
        jmlkk = Integer.parseInt(tJumlahKK.getText().toString());
        jmlpenduduk = Integer.parseInt(tJumlahPenduduk.getText().toString());

        // GET DATETIME NOW BY GMT+
        currentTime = Calendar.getInstance().getTime();

        final Map<String, Object> sensus = new HashMap<>();
        sensus.put("provinsi", provinsi);
        sensus.put("kota", kota);
        sensus.put("kecamatan", kecamatan);
        sensus.put("kelurahan", kelurahan);
        sensus.put("rw", rw);
        sensus.put("rt", rt);
        sensus.put("jumlahkk", jmlkk);
        sensus.put("jumlahpenduduk", jmlpenduduk);
        sensus.put("user", username);
        sensus.put("datetime", currentTime.toString());

        db.collection("sensus")
            .whereEqualTo("provinsi", provinsi)
            .whereEqualTo("kota", kota)
            .whereEqualTo("kecamatan", kecamatan)
            .whereEqualTo("kelurahan", kelurahan)
            .whereEqualTo("rw", rw)
            .whereEqualTo("rt", rt)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    QuerySnapshot snapshot = task.getResult();
                    System.out.println("Masuk");
                    System.out.println("Hasil: " + snapshot.getDocuments().size());
                    if (snapshot.getDocuments().size() > 0) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Duplikasi data ditemukan", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                    } else {

                        db.collection("sensus")
                            .add(sensus)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Data berhasil disimpan!", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.TOP, 0, 0);
                                    if (task.isSuccessful()) {
                                        toast.show();
                                        onKembali();
                                    } else {
                                        toast.setText("Error, Data gagal disimpan!");
                                        toast.show();
                                    }
                                }
                            });
                    }
                }
            });
    }

    protected void onKembali(){
        it = new Intent(this, Dashboard.class);
        startActivity(it);
    }
}
