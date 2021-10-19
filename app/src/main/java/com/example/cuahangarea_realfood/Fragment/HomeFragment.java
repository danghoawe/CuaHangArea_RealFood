package com.example.cuahangarea_realfood.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cuahangarea_realfood.Screen.DS_SanPhamActivity;
import com.example.cuahangarea_realfood.Screen.ThongTinCuaHangActivity;
import com.example.cuahangarea_realfood.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    CardView danhSachSanPham,maGiamGia,danhSachDonHang,phanHoiKhachHang,khuVucBep,DoanhThu;
    FragmentHomeBinding binding;

    public HomeFragment() {
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setControl();
    }

    private void setControl() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());



        binding.cardViewDanhSachSamPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DS_SanPhamActivity.class);
                startActivity(intent);
            }
        });

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThongTinCuaHangActivity.class);
                startActivity(intent);
            }
        });

        return  binding.getRoot();
    }
}