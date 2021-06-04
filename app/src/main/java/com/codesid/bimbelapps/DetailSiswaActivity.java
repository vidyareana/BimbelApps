package com.codesid.bimbelapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailSiswaActivity extends AppCompatActivity {
    @BindView(R.id.circleImageView)
    CircleImageView imgFoto;
    @BindView(R.id.textView5)
    TextView txtNama;
    @BindView(R.id.textView6)
    TextView txtTtl;
    @BindView(R.id.tv_paket)
    TextView tv_paket;
    @BindView(R.id.tv_tingkat)
    TextView tv_tingkat;
    @BindView(R.id.btn_to_update)
    Button btn_to_update;
    @BindView(R.id.btnDelete)
    Button btnDelete;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_siswa);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String nama = intent.getStringExtra("nama");
        String ttl = intent.getStringExtra("ttl");
        String paket = intent.getStringExtra("paket");
        String tingkat = intent.getStringExtra("tingkat");
        String url = intent.getStringExtra("url");
        String key = intent.getStringExtra("key");

        txtNama.setText(nama);
        txtTtl.setText(ttl);
        tv_paket.setText(paket);
        tv_tingkat.setText(tingkat);
        Glide.with(this)
                .load(url)
                .centerCrop()
                .into(imgFoto);

        btn_to_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DetailSiswaActivity.this,UpdateActivity.class);
                intent1.putExtra("nama1",nama);
                intent1.putExtra("ttl1",ttl);
                intent1.putExtra("paket1",paket);
                intent1.putExtra("tingkat1",tingkat);
                intent1.putExtra("url1",url);
                intent1.putExtra("key1",key);
                startActivity(intent1);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(DetailSiswaActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Apakah Anda Yakin?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                myref = database.getReference("Siswa");
                                myref.child(key).removeValue();
                                sweetAlertDialog.setTitleText("Deleted!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                finish();
                                            }
                                        })
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .showCancelButton(true)
                        .setConfirmText("Yes,delete it!")
                        .show();
            }
        });



    }
}