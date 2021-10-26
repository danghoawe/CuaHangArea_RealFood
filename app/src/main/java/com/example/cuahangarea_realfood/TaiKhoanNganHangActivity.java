package com.example.cuahangarea_realfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cuahangarea_realfood.adapter.NganHangAdapter;
import com.example.cuahangarea_realfood.model.NganHang;
import com.example.cuahangarea_realfood.model.NganHangAPI;
import com.example.cuahangarea_realfood.model.NganHangWrapper;

import java.util.ArrayList;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaiKhoanNganHangActivity extends AppCompatActivity {

    SearchableSpinner spTenNganHang;
    ArrayList<NganHang>arrayList = new ArrayList<>();
    NganHangAPI nganHangAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan_ngan_hang);
        getData();
        setControl();

    }

    private void setControl() {
        spTenNganHang = findViewById(R.id.spTenNganHang);
    }

    private void getData() {
        arrayList.clear();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(NganHangAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        nganHangAPI = retrofit.create(NganHangAPI.class);
        Call<NganHangWrapper<NganHang>> call = nganHangAPI.getNganHang();
        call.enqueue(new Callback<NganHangWrapper<NganHang>>() {
            @Override
            public void onResponse(Call<NganHangWrapper<NganHang>> call, Response<NganHangWrapper<NganHang>> response) {
                if(response.isSuccessful()){
                    NganHangWrapper<NganHang> wrapper = response.body();
                    assert wrapper !=null;
                    arrayList.addAll(wrapper.items);
                    arrayList.forEach(nganHang -> Log.d("NganHang",nganHang.getName()));
                    NganHangAdapter nganHangAdapter = new NganHangAdapter(TaiKhoanNganHangActivity.this,R.layout.sp_nganhang_item,R.id.textView,arrayList);
                    spTenNganHang.setAdapter(nganHangAdapter);
                }
            }
            @Override
            public void onFailure(Call<NganHangWrapper<NganHang>> call, Throwable t) {
            }
        });

    }
}
