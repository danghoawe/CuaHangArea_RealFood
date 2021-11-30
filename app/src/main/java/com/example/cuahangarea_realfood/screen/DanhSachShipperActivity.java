package com.example.cuahangarea_realfood.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;

import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.Fragment.DanhMuc_DialogFragment;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.TrangThai.TrangThaiShipper;
import com.example.cuahangarea_realfood.adapter.ShipperAdapter;
import com.example.cuahangarea_realfood.databinding.ActivityDanhSachShipperBinding;
import com.example.cuahangarea_realfood.model.Shipper;

import java.util.ArrayList;

public class DanhSachShipperActivity extends AppCompatActivity {
    ActivityDanhSachShipperBinding binding;
    ShipperAdapter shipperAdapter;
    ArrayList<Shipper>shippers = new ArrayList<>();
    Firebase_Manager firebase_manager = new Firebase_Manager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shipperAdapter = new ShipperAdapter(this, R.layout.shipper_listview,shippers);
        binding = ActivityDanhSachShipperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefesh);

        setEvent();
        Loaddata();

    }

    private void Loaddata() {
        binding.rcDonHang.setAdapter(shipperAdapter);
        firebase_manager.GetDanhSachShipper(shippers,shipperAdapter);
    }

    private void setEvent() {
        binding.spTrangThaiShipper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        shipperAdapter.getFilter().filter("");
                        break;
                    case 1:
                        shipperAdapter.getFilter().filter(TrangThaiShipper.DangHoatDong.toString());
                        break;
                    case 2:
                        shipperAdapter.getFilter().filter(TrangThaiShipper.KhongHoatDong.toString());
                        break;
                    case 3:
                        shipperAdapter.getFilter().filter(TrangThaiShipper.DangGiaoHang.toString());
                        break;
                    case 4:
                        shipperAdapter.getFilter().filter(TrangThaiShipper.BiKhoa.toString());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_shipper, menu);
        MenuItem searchItem = menu.findItem(R.id.menuItem_search);
        SearchView searchView =
                (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                shipperAdapter.getFilter().filter(query);
                return true;

            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Context context = this;
        FragmentManager fragmentManager = getSupportFragmentManager();
        DanhMuc_DialogFragment danhMuc_dialogFragment = new DanhMuc_DialogFragment(null);
        switch (item.getItemId()) {
            case R.id.mnThemShipper:
                Intent intent = new Intent(DanhSachShipperActivity.this, ThongTinShipperActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}