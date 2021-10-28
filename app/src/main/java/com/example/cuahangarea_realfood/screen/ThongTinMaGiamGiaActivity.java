package com.example.cuahangarea_realfood.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.cuahangarea_realfood.R;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;

public class ThongTinMaGiamGiaActivity extends AppCompatActivity {
    EditText edtMaGiamGia,edtSoTienGiam,edtGiamTheoPhanTram;
    SearchableSpinner spSanPham;
    SingleDateAndTimePicker dateAndTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_ma_giam_gia);
        setControl();
        setEvent();
    }

    private void setEvent() {
        Date date = dateAndTimePicker.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);
    }

    private void setControl() {
        edtMaGiamGia = findViewById(R.id.edtMaGiamGia);
        edtGiamTheoPhanTram = findViewById(R.id.edtGiamPhanTram);
        edtSoTienGiam = findViewById(R.id.edtSoTienGiam);
        spSanPham = findViewById(R.id.spDSsanPham);
        dateAndTimePicker = findViewById(R.id.single_day_picker);
    }
}