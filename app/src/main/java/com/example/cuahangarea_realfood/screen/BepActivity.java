package com.example.cuahangarea_realfood.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;

import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.adapter.DonHangAdapter;
import com.example.cuahangarea_realfood.adapter.DonHang_BepAdapter;
import com.example.cuahangarea_realfood.databinding.ActivityBepBinding;
import com.example.cuahangarea_realfood.model.DonHang;

import java.util.ArrayList;

public class BepActivity extends AppCompatActivity {
    DonHang_BepAdapter donHang_bepAdapter;
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
        donHang_bepAdapter.notifyDataSetChanged();
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
        donHang_bepAdapter = new DonHang_BepAdapter(this, R.layout.donhang_bep_item, donHangs);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        gridLayoutManager = new GridLayoutManager(this,2);
        rcDonHang.setLayoutManager(linearLayoutManager);
        rcDonHang.setAdapter(donHang_bepAdapter);
        firebase_manager.GetDonHang_Bep(donHangs,donHang_bepAdapter);
        setEvent();
    }

    private void setEvent() {

    }



    private void setControl() {
        rcDonHang = findViewById(R.id.rcDonHang);
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