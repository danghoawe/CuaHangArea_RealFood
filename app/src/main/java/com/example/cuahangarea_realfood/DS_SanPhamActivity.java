package com.example.cuahangarea_realfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DS_SanPhamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_san_pham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Context context = this;
        FragmentManager fragmentManager = getSupportFragmentManager();
        DanhMuc_DialogFragment danhMuc_dialogFragment = new DanhMuc_DialogFragment();
        switch (item.getItemId())
        {
            case R.id.mnThemDanhMuc:

                if (getFragmentManager() != null) {
                    danhMuc_dialogFragment.onActivityResult(1,1,null);
                    danhMuc_dialogFragment.show(fragmentManager,"DS_SanPhamActivity");
                }
            case R.id.mnThemSanPham:
                Toast.makeText(this, "Thêm", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}