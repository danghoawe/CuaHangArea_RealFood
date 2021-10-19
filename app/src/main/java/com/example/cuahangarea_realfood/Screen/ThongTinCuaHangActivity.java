package com.example.cuahangarea_realfood.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.developer.kalert.KAlertDialog;
import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.TrangThai.TrangThaiCuaHang;
import com.example.cuahangarea_realfood.databinding.ActivityThongTinCuaHangBinding;
import com.example.cuahangarea_realfood.model.CuaHang;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ThongTinCuaHangActivity extends AppCompatActivity {
    ActivityThongTinCuaHangBinding binding;
    Firebase_Manager firebase_manager= new Firebase_Manager();
    CuaHang cuaHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongTinCuaHangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.lnLayout.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LoadData();
        setEvent();

    }

    private void setEvent() {

        binding.btnLuuThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CuaHang temp = new CuaHang(firebase_manager.auth.getUid(), binding.edtTenCuaHang.getText().toString()
                        , binding.edtHotenChu.getText().toString(), binding.edtThongTinChiTiet.getText().toString()
                        , binding.edtSoCMND.getText().toString(), binding.edtSoDT.getText().toString()
                        , "", "", cuaHang.getRating(), binding.edtEmail.getText().toString()
                        , cuaHang.getTrangThaiCuaHang(),binding.edtDiaChi.getText().toString());
                cuaHang = temp;
                CapNhatCuaHang(cuaHang);
            }
        });

        binding.btnAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuaHang.setTrangThaiCuaHang(TrangThaiCuaHang.AN);
                CapNhatCuaHang(cuaHang);
            }
        });
        binding.btnHienthi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuaHang.setTrangThaiCuaHang(TrangThaiCuaHang.DaKichHoat);
                CapNhatCuaHang(cuaHang);

            }
        });
    }
    public void CapNhatCuaHang (CuaHang cuaHang){
        KAlertDialog kAlertDialog = new KAlertDialog(ThongTinCuaHangActivity.this,KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
        kAlertDialog.show();
        kAlertDialog.showConfirmButton(false);
        firebase_manager.Ghi_CuaHang(cuaHang).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                kAlertDialog.setContentText("Trạng thái đã cửa hàng đã được cập nhật!");
                kAlertDialog.showConfirmButton(false);
                LoadData();
            }
        });
    }

    private void LoadData() {

       firebase_manager. mDatabase.child("CuaHang").child(firebase_manager.auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CuaHang temp = dataSnapshot.getValue(CuaHang.class);
                cuaHang = temp;
                binding.edtDiaChi.setText( temp.getDiaChi());
                binding.edtTenCuaHang.setText(temp.getTenCuaHang());
                binding.edtEmail.setText(temp.getEmail());
                binding.edtSoDT.setText(temp.getSoDienThoai());
                binding.edtSoCMND.setText(temp.getSoCMND());
                binding.edtHotenChu.setText(temp.getChuSoHuu());
                binding.edtThongTinChiTiet.setText(temp.getThongTinChiTiet());
                binding.progessbar.setVisibility(View.GONE);
                binding.lnLayout.setVisibility(View.VISIBLE);
                if (temp.getTrangThaiCuaHang()==TrangThaiCuaHang.AN)
                {
                    binding.btnHienthi.setVisibility(View.VISIBLE);
                }
                else {
                    binding.btnHienthi.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}