package com.tomeskates.sensusapps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.DataSensusViewHolder> {
    private ArrayList<Sensus> list;
    private FirebaseFirestore db;

    public RecyclerviewAdapter(ArrayList<Sensus> list) {
        this.list = list;


    }

    @NonNull
    @Override
    public DataSensusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_data_row, parent, false);

        return new DataSensusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataSensusViewHolder holder, int position) {
        final Sensus sensus = list.get(position);
        String alamatlengkap = sensus.getKelurahan() + " RT" + sensus.getRt() + " RW " + sensus.getRw() + ", " + sensus.getKecamatan() + ", " + sensus.getKota() + ", " + sensus.getProvinsi() ;

        db = FirebaseFirestore.getInstance();

        //holder.sensusid.setText(sensus.getSensusid());
        holder.alamat.setText(alamatlengkap);
        holder.jumlahkk.setText(sensus.getJumlahkk().toString());
        holder.jumlahpenduduk.setText(sensus.getJumlahpenduduk().toString());
        holder.information.setText("By "+sensus.getUser().toString()+" | "+sensus.getDatetime().toString());
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public static class DataSensusViewHolder extends RecyclerView.ViewHolder {
        public TextView sensusid, alamat, jumlahkk, jumlahpenduduk, information, tUserView;

        public DataSensusViewHolder(View view) {
            super(view);

            //sensusid = (TextView) view.findViewById(R.id.tSensusID);
            alamat = (TextView) view.findViewById(R.id.tAlamat);
            jumlahkk = (TextView) view.findViewById(R.id.tJmlKK);
            jumlahpenduduk = (TextView) view.findViewById(R.id.tJmlPenduduk);
            information = (TextView) view.findViewById(R.id.tInformation);

            tUserView = (TextView) view.findViewById(R.id.tUserView);

        }
    }

    /*
    protected void changeSensus(View view, Sensus sensus) {
        Intent intent = new Intent(view.getContext(), EditSensus.class);
        Bundle bundle = new Bundle();
        bundle.putString("provinsi", sensus.getProvinsi());
        bundle.putString("kota[", sensus.getKota());
        bundle.putString("kecamatan", sensus.getKecamatan());
        bundle.putString("kelurahan", sensus.getKelurahan());
        bundle.putInt("rt", sensus.getRt());
        bundle.putInt("rw", sensus.getRw());
        bundle.putInt("kepala_keluarga", sensus.getKepala_keluarga());
        bundle.putInt("penduduk", sensus.getPenduduk());
        intent.putExtras(bundle);
    }
     */
}
