package com.example.cuahangarea_realfood.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.developer.kalert.KAlertDialog;
import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.model.SanPham;
import com.example.cuahangarea_realfood.model.Voucher;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;

public class ThongTinMaGiamGiaActivity extends AppCompatActivity {
    EditText edtMaGiamGia,edtSoTienGiam,edtGiamTheoPhanTram;
    TextInputLayout tiplGiamTheoPhanTram,tiplGiamTheoGia;
    SearchableSpinner spSanPham;
    SingleDateAndTimePicker dateAndTimePicker;
    ArrayAdapter adapter;
    List<String> namesSanPham = new ArrayList<>();
    ArrayList<SanPham> dSSanPham = new ArrayList<>();
    Firebase_Manager firebase_manager;
    Button btnLuuMaGiamGia;
    RadioButton radGiamTheoGia,radGiamTheoPhanTram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_ma_giam_gia);
        firebase_manager = new Firebase_Manager();
        setControl();
        loadLoaiGiamGia();
        setEvent();

    }

    private void loadLoaiGiamGia() {
        if (radGiamTheoGia.isChecked())
        {
            edtSoTienGiam.setVisibility(View.VISIBLE);
            tiplGiamTheoGia.setVisibility(View.VISIBLE);
            edtGiamTheoPhanTram.setVisibility(View.GONE);
            tiplGiamTheoPhanTram.setVisibility(View.GONE);
        }
        else {
            edtSoTienGiam.setVisibility(View.GONE);
            tiplGiamTheoGia.setVisibility(View.GONE);
            edtGiamTheoPhanTram.setVisibility(View.VISIBLE);
            tiplGiamTheoPhanTram.setVisibility(View.VISIBLE);

        }
    }

    private void setEvent() {

        adapter = new ArrayAdapter(ThongTinMaGiamGiaActivity.this, android.R.layout.simple_expandable_list_item_1, namesSanPham);
        Date date = dateAndTimePicker.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);
        firebase_manager.GetSanPham_V2((ArrayList) namesSanPham,adapter);
        firebase_manager.GetSanPham(dSSanPham,null);
        spSanPham .setAdapter(adapter);
        btnLuuMaGiamGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KAlertDialog kAlertDialog = new KAlertDialog(ThongTinMaGiamGiaActivity.this,KAlertDialog.PROGRESS_TYPE);
                kAlertDialog.setContentText("Loading");
                kAlertDialog.show();
                String uuid = UUID.randomUUID().toString().replace("-", "");

                Voucher voucher = new Voucher(uuid,dSSanPham.get(spSanPham.getSelectedPosition()).getIDSanPham()
                        ,edtMaGiamGia.getText().toString(),Double.parseDouble(edtSoTienGiam.getText().toString())
                        ,Double.parseDouble(edtGiamTheoPhanTram.getText().toString()),new Date(),dateAndTimePicker.getDate());
                firebase_manager.Ghi_Voucher(voucher).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                        kAlertDialog.setContentText("Lưu mã giảm giá thành công!");
                        kAlertDialog.showConfirmButton(false);
                    }
                });
            }
        });
        radGiamTheoPhanTram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLoaiGiamGia();
            }
        });
        radGiamTheoGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLoaiGiamGia();
            }
        });
    }

    private void setControl() {
        edtMaGiamGia = findViewById(R.id.edtMaGiamGia);
        edtGiamTheoPhanTram = findViewById(R.id.edtGiamPhanTram);
        edtSoTienGiam = findViewById(R.id.edtSoTienGiam);
        spSanPham = findViewById(R.id.spDSsanPham);
        dateAndTimePicker = findViewById(R.id.single_day_picker);
        btnLuuMaGiamGia= findViewById(R.id.btnLuuMaGiamGia);
        radGiamTheoGia = findViewById(R.id.radGiamTheoGia);
        radGiamTheoPhanTram = findViewById(R.id.radGiamTheoPhanTram);
        tiplGiamTheoGia = findViewById(R.id.tiplGiamTheoGia);
        tiplGiamTheoPhanTram = findViewById(R.id.tiplGiamTheoPhanTram);
    }
}