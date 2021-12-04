package com.example.cuahangarea_realfood.screen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;

import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.TrangThai.LoaiThongBao;
import com.example.cuahangarea_realfood.Validate;
import com.example.cuahangarea_realfood.databinding.ActivityGuiThongBaoBinding;
import com.example.cuahangarea_realfood.model.CuaHang;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

public class GuiThongBaoActivity extends AppCompatActivity {
    ActivityGuiThongBaoBinding binding;
    String noidung;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    Validate validate = new Validate();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuiThongBaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        LoadData();
    }

    private void LoadData() {
        binding.btnLuuThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validate.isBlank(binding.edtNoiDung))
                {
                    firebase_manager.Ghi_ThongBao_random("admin","Thông báo",binding.edtNoiDung.getText().toString(), LoaiThongBao.NORMAL).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            new KAlertDialog(GuiThongBaoActivity.this,KAlertDialog.SUCCESS_TYPE).setContentText("Đã gửi thông báo đến Admin ").setConfirmText("Ok").setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog kAlertDialog) {
                                    finish();
                                }
                            }).show();
                        }
                    });
                }

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}