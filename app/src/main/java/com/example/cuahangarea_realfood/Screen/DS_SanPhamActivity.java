package com.example.cuahangarea_realfood.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
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

import com.example.cuahangarea_realfood.Fragment.DanhMuc_DialogFragment;
import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.SetOnLongClick;
import com.example.cuahangarea_realfood.adapter.DanhMucAdapter;
import com.example.cuahangarea_realfood.adapter.SanPhamAdapter;
import com.example.cuahangarea_realfood.model.DanhMuc;
import com.example.cuahangarea_realfood.model.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
;
import android.widget.Toast;


import java.util.ArrayList;

public class DS_SanPhamActivity extends AppCompatActivity {
    DanhMucAdapter danhMucAdapter;
    SanPhamAdapter sanPhamAdapter;
    ArrayList<DanhMuc> danhMucs;
    ArrayList<SanPham> sanPhams;
    LinearLayoutManager linearLayoutManager,linearLayoutManager2;
    GridLayoutManager gridLayoutManager ;
    Button btnThemSanPham;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    RecyclerView rcDanhMuc,rcSanPham;
    SetOnLongClick setOnLongClick;

    @Override
    protected void onResume() {
        GetSanPham();
        GetDanhSachDanhMuc();
        sanPhamAdapter.notifyDataSetChanged();
        danhMucAdapter.notifyDataSetChanged();
        super.onResume();
        Log.d("a","oke");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        danhMucs = new ArrayList<DanhMuc>();
        sanPhams = new ArrayList<SanPham>();
        setContentView(R.layout.activity_ds_san_pham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setControl();
        ;
        danhMucAdapter = new DanhMucAdapter(this, R.layout.danhmuc_item, danhMucs);

        sanPhamAdapter = new SanPhamAdapter(this, R.layout.sanpham_item, sanPhams);

        setOnLongClick = new SetOnLongClick() {
            @Override
            public void onLongClick(int positon) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DanhMuc_DialogFragment danhMuc_dialogFragment = new DanhMuc_DialogFragment(danhMucs.get(positon));
                danhMuc_dialogFragment.onActivityResult(1, 1, null);
                danhMuc_dialogFragment.show(fragmentManager, "DS_SanPhamActivity");
            }
        };

        danhMucAdapter.setSetOnLongClick(setOnLongClick);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rcDanhMuc.setLayoutManager(linearLayoutManager);
        rcDanhMuc.setAdapter(danhMucAdapter);

        gridLayoutManager = new GridLayoutManager(this,2);
        rcSanPham.setLayoutManager(gridLayoutManager);
        rcSanPham.setAdapter(sanPhamAdapter);
        GetDanhSachDanhMuc();
        GetSanPham();
        setEvent();
    }

    private void setEvent() {
        btnThemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DS_SanPhamActivity.this, ThongTinSanPhamActivity.class);
                startActivity(intent);
            }
        });
    }

    public void GetDanhSachDanhMuc() {
        firebase_manager.mDatabase.child("DanhMuc").child(firebase_manager.auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                danhMucs.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DanhMuc danhMuc = postSnapshot.getValue(DanhMuc.class);
                    danhMucs.add(danhMuc);
                    danhMucAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void GetSanPham() {
        firebase_manager.mDatabase.child("SanPham").child(firebase_manager.auth.getUid()).orderByChild("tenSanPham").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sanPhams.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SanPham sanPham = postSnapshot.getValue(SanPham.class);
                    sanPhams.add(sanPham);
                    sanPhamAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void setControl() {
        rcDanhMuc = findViewById(R.id.rcDanhMuc);
        rcSanPham = findViewById(R.id.rcSanPham);
        btnThemSanPham = findViewById(R.id.btnThemSanPham);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Context context = this;
        FragmentManager fragmentManager = getSupportFragmentManager();
        DanhMuc_DialogFragment danhMuc_dialogFragment = new DanhMuc_DialogFragment(null);
        switch (item.getItemId()) {
            case R.id.mnThemDanhMuc:

                if (getFragmentManager() != null) {
                    danhMuc_dialogFragment.onActivityResult(1, 1, null);
                    danhMuc_dialogFragment.show(fragmentManager, "DS_SanPhamActivity");
                }
                break;
            case R.id.mnThemSanPham:
                Toast.makeText(this, "Thêm", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}