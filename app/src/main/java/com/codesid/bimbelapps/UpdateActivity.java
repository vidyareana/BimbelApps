package com.codesid.bimbelapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codesid.bimbelapps.Model.Siswa;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateActivity extends AppCompatActivity {
    @BindView(R.id.txtNama)
    TextInputLayout txtNama;
    @BindView(R.id.txtTanggal)
    TextInputLayout txtTanggal;
    @BindView(R.id.btnSubmit)
    MaterialButton btnSubmit;
    @BindView(R.id.et_tgl)
    TextInputEditText et_tgl;
    @BindView(R.id.radioGroup)
    RadioGroup rg_tingkat;
    @BindView(R.id.radioGroup2)
    RadioGroup rg_paket;
    @BindView(R.id.rb_1)
    RadioButton rb_1;
    @BindView(R.id.rb_2)
    RadioButton rb_2;
    @BindView(R.id.rb_3)
    RadioButton rb_3;
    @BindView(R.id.rb_paket_1)
    RadioButton rb_paket_1;
    @BindView(R.id.rb_paket_2)
    RadioButton rb_paket_2;
    @BindView(R.id.rb_paket_3)
    RadioButton rb_paket_3;

    @BindView(R.id.btnTakeImage)
    MaterialButton btnTakeImage;
    String str_nama,str_ttl,str_tingkat,str_paket =null;
    DatabaseReference myref;
    String url1;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btnTakeImage.setVisibility(View.GONE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myref = database.getReference("Siswa");
        Intent intent = getIntent();
        String nama = intent.getStringExtra("nama1");
        String ttl = intent.getStringExtra("ttl1");
        String paket = intent.getStringExtra("paket1");
        String tingkat = intent.getStringExtra("tingkat1");
        key = intent.getStringExtra("key1");
        url1 = intent.getStringExtra("url1");

       txtNama.getEditText().setText(nama);
       txtTanggal.getEditText().setText(ttl);

       if (tingkat.equals("SD")){
           rb_1.setChecked(true);
       }else if(tingkat.equals("SMP")){
           rb_2.setChecked(true);
       }else {
           rb_3.setChecked(true);
       }

       if (paket.equals("3 Mapel")){
           rb_paket_1.setChecked(true);
       }else if (paket.equals("4 Mapel")){
           rb_paket_2.setChecked(true);
       }else {
           rb_paket_3.setChecked(true);
       }
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Pilih Tanggal Lahir");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        et_tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });


        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        // if the user clicks on the positive
                        // button that is ok button update the
                        // selected date
                        et_tgl.setText( materialDatePicker.getHeaderText());
                        // in the above statement, getHeaderText
                        // is the selected date preview from the
                        // dialog
                    }
                });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRg();
                checkRg2();
                checkEt();
            }
        });

    }

    void checkRg(){

        if (rg_tingkat.getCheckedRadioButtonId() == -1)
        {

        }
        else
        {
            int selectedId = rg_tingkat.getCheckedRadioButtonId();
            MaterialRadioButton radioButton = (MaterialRadioButton) findViewById(selectedId);
            str_tingkat = radioButton.getText().toString();
        }

    }
    void checkRg2(){
        if (rg_paket.getCheckedRadioButtonId() == -1)
        {

        }
        else
        {
            int selectedId = rg_paket.getCheckedRadioButtonId();
            MaterialRadioButton radioButton = (MaterialRadioButton) findViewById(selectedId);
            str_paket = radioButton.getText().toString();
        }
    }
    void checkEt(){

        if (txtNama.getEditText().getText().toString().equals("")||txtTanggal.getEditText().getText().toString().equals("")||str_paket.equals("")||str_tingkat.equals("")){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Silahkan Coba Lagi")
                    .show();
        }else{
            str_nama = txtNama.getEditText().getText().toString();
            str_ttl = txtTanggal.getEditText().getText().toString();
            Siswa siswa = new Siswa(key,str_nama,str_ttl,str_tingkat,str_paket,url1);

            myref.child(key).setValue(siswa);
            new SweetAlertDialog(UpdateActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Sukses!")
                    .setContentText("Data terupdate!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                            Intent intent = new Intent(UpdateActivity.this,SiswaActivity.class);
                            startActivity(intent);
                        }
                    }).show();
        }


    }
}