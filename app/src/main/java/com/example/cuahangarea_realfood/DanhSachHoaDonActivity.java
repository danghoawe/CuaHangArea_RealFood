package com.example.cuahangarea_realfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;

import com.example.cuahangarea_realfood.adapter.ThanhToanAdapter;
import com.example.cuahangarea_realfood.databinding.ActivityDanhSachDonHangBinding;
import com.example.cuahangarea_realfood.databinding.ActivityDanhSachHoaDonBinding;
import com.example.cuahangarea_realfood.model.ThanhToan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DanhSachHoaDonActivity extends AppCompatActivity {
    ArrayList<ThanhToan> thanhToans = new ArrayList<>();
    ThanhToanAdapter thanhToanAdapter;
    LinearLayoutManager linearLayoutManager ;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    ActivityDanhSachHoaDonBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityDanhSachHoaDonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        thanhToanAdapter = new ThanhToanAdapter(this,R.layout.hoadon_item,thanhToans);
        binding.rcDanhSachDonHang.setLayoutManager(linearLayoutManager);
        binding.rcDanhSachDonHang.setAdapter(thanhToanAdapter);
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swipeLayout);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
                thanhToanAdapter.getFilter().filter("");
                pullToRefresh.setRefreshing(false);
            }
        });
        LoadData();
    }
    private void LoadData() {
        binding.progessbar.setVisibility(View.VISIBLE);
        firebase_manager.mDatabase.child("ThanhToan").orderByChild("idCuaHang").equalTo(firebase_manager.auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                thanhToans.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ThanhToan thanhToan = dataSnapshot.getValue(ThanhToan.class);
                    thanhToans.add(thanhToan);
                }
                thanhToanAdapter.notifyDataSetChanged();
                binding.progessbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}