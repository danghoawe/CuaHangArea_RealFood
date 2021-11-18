package com.example.cuahangarea_realfood.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.adapter.DanhGiaSanPhamAdapter;
import com.example.cuahangarea_realfood.databinding.ActivityDanhGiaBinding;
import com.example.cuahangarea_realfood.model.DanhGia;

import java.util.ArrayList;

public class DanhGiaActivity extends AppCompatActivity {
    ActivityDanhGiaBinding binding ;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    ArrayList<DanhGia>danhGias = new ArrayList<>();
    DanhGiaSanPhamAdapter danhGiaSanPhamAdapter ;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDanhGiaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        danhGiaSanPhamAdapter = new DanhGiaSanPhamAdapter(this,R.layout.danhgiasanpham,danhGias);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rcDanhGia.setLayoutManager(linearLayoutManager);
        binding.rcDanhGia.setAdapter(danhGiaSanPhamAdapter);
        firebase_manager.GetPhanHoi(danhGias,danhGiaSanPhamAdapter);
    }

}