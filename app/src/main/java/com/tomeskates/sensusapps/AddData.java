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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddData extends AppCompatActivity {

    private FirebaseFirestore db;
    private static final String TAG = "MainActivity";

    public SharedPreferences prefs;
    public SharedPreferences.Editor edit;
    public Intent it;

    EditText tProvinsi, tKota, tKecamatan, tKelurahan, tRW, tRT, tJumlahKK, tJumlahPenduduk;
    Button btnMasuk;
    TextView tStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);

        db = FirebaseFirestore.getInstance();

    }

    public void onSimpan(View v){

        tStatus = findViewById(R.id.tStatus);

        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    tStatus.setText("Berhasil");
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error adding document", e);
                    tStatus.setText("Gagal");
                }
            });


    }
    public void onKembali(View v){
        it = new Intent(this, Dashboard.class);
        startActivity(it);
    }
}
