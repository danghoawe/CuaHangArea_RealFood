package com.example.cuahangarea_realfood.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.cuahangarea_realfood.Fragment.DanhMuc_DialogFragment;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.databinding.ActivityMaGiamGiaBinding;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;

public class MaGiamGiaActivity extends AppCompatActivity {
    ActivityMaGiamGiaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMaGiamGiaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setEvent();
    }

    private void setEvent() {
        binding.btnThemMoiVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaGiamGiaActivity.this,ThongTinMaGiamGiaActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_magiamgia, menu);
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