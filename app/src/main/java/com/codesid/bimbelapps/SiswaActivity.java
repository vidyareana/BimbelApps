package com.codesid.bimbelapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codesid.bimbelapps.Adapter.SiswaAdapter;
import com.codesid.bimbelapps.Model.Siswa;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SiswaActivity extends AppCompatActivity {
    @BindView(R.id.rv_siswa)
    RecyclerView rv_siswa;
    @BindView(R.id.btnTambahSiswa)
    Button btnTambahSiswa;
    SiswaAdapter siswaAdapter;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa);

        ButterKnife.bind(this);
        reference = FirebaseDatabase.getInstance().getReference().child("Siswa");
        rv_siswa.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Siswa> options
                = new FirebaseRecyclerOptions.Builder<Siswa>()
                .setQuery(reference, Siswa.class)
                .build();

        btnTambahSiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SiswaActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        siswaAdapter = new SiswaAdapter(SiswaActivity.this,options);
        rv_siswa.setAdapter(siswaAdapter);

    }
    @Override protected void onStart()
    {
        super.onStart();
        siswaAdapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        siswaAdapter.stopListening();
    }
}