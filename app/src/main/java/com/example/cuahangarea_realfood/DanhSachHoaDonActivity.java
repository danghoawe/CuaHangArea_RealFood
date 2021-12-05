package com.example.cuahangarea_realfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuInflater;
import android.widget.PopupMenu;
import android.widget.SearchView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.developer.kalert.KAlertDialog;
import com.example.cuahangarea_realfood.Fragment.DanhMuc_DialogFragment;
import com.example.cuahangarea_realfood.TrangThai.TrangThaiShipper;
import com.example.cuahangarea_realfood.adapter.ThanhToanAdapter;
import com.example.cuahangarea_realfood.databinding.ActivityDanhSachDonHangBinding;
import com.example.cuahangarea_realfood.databinding.ActivityDanhSachHoaDonBinding;
import com.example.cuahangarea_realfood.model.ThanhToan;
import com.example.cuahangarea_realfood.screen.DanhSachShipperActivity;
import com.example.cuahangarea_realfood.screen.ThongTinShipperActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.Collections;

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hoadon,menu);
        MenuItem menuItem = menu.findItem(R.id.menuItem_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tìm kiếm");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                thanhToanAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                thanhToanAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    int flag =0;
    int flag2 =0;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Context context = this;
        switch (item.getItemId()) {
            case R.id.mn_filter:
                View menuItemView = findViewById(R.id.mn_filter); // SAME ID AS MENU ID
                PopupMenu popupMenu = new PopupMenu(this, menuItemView);
                popupMenu.inflate(R.menu.popupmenu_hoadon);
                // Register Menu Item Click event.
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.mn_theoNgayTao:
                            {
                                if(flag==0)
                                {
                                    Collections.sort(thanhToans,(o1, o2) -> o1.getNgayThanhToan().compareTo(o2.getNgayThanhToan()));
                                    thanhToanAdapter.notifyDataSetChanged();
                                    flag=1;
                                }
                                else {
                                    Collections.sort(thanhToans,(o1, o2) -> o2.getNgayThanhToan().compareTo(o1.getNgayThanhToan()));
                                    thanhToanAdapter.notifyDataSetChanged();
                                    flag=0;
                                }
                                break;
                            }
                            case R.id.mn_TheoSoTien:
                            {
                                if(flag2==0)
                                {
                                    Collections.sort(thanhToans,(o1, o2) -> o1.getSoTien().equals(o2.getSoTien())?-1:1);
                                    thanhToanAdapter.notifyDataSetChanged();
                                    flag2=1;
                                }
                                else {
                                    Collections.sort(thanhToans,(o1, o2) -> o1.getSoTien().equals(o2.getSoTien())?1:-1);
                                    thanhToanAdapter.notifyDataSetChanged();
                                    flag2=0;
                                }
                                break;
                            }
                        }
                        return true;
                    }
                });

                // Show the PopupMenu.
                popupMenu.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}