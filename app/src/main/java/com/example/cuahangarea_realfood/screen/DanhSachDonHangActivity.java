package com.example.cuahangarea_realfood.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.Fragment.DanhMuc_DialogFragment;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.adapter.DanhMucAdapter;
import com.example.cuahangarea_realfood.adapter.DonHangAdapter;
import com.example.cuahangarea_realfood.adapter.SanPhamAdapter;
import com.example.cuahangarea_realfood.model.DanhMuc;
import com.example.cuahangarea_realfood.model.DonHang;
import com.example.cuahangarea_realfood.model.SanPham;
import com.example.cuahangarea_realfood.screen.DS_SanPhamActivity;
import com.example.cuahangarea_realfood.screen.ThongTinSanPhamActivity;

import java.util.ArrayList;

public class DanhSachDonHangActivity extends AppCompatActivity {
    DonHangAdapter donHangAdapter;
    ArrayList<DonHang> donHangs;
    LinearLayoutManager linearLayoutManager,linearLayoutManager2;
    GridLayoutManager gridLayoutManager ;
    Button btnThemSanPham;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    RecyclerView rcDonHang;

    @Override
    protected void onResume() {
        //GetSanPham();
        //GetDanhSachDanhMuc();
        donHangAdapter.notifyDataSetChanged();
        super.onResume();
        Log.d("a","oke");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        donHangs = new ArrayList<DonHang>();
        setContentView(R.layout.activity_danh_sach_don_hang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setControl();


        donHangAdapter = new DonHangAdapter(this, R.layout.donhang_item, donHangs);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        gridLayoutManager = new GridLayoutManager(this,2);
        rcDonHang.setLayoutManager(linearLayoutManager);
        rcDonHang.setAdapter(donHangAdapter);
        firebase_manager.GetDonHang(donHangs,donHangAdapter);
//          GetDanhSachDanhMuc();
//        GetSanPham();
        setEvent();
    }

    private void setEvent() {

    }



    private void setControl() {
        rcDonHang = findViewById(R.id.rcDonHang);
        btnThemSanPham = findViewById(R.id.btnThemSanPham);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menuItem_search);
        SearchView searchView =
                (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
              //  donHangAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
              //  donHangAdapter.getFilter().filter(query);
                return true;

            }

        });
        return true;
    }

}