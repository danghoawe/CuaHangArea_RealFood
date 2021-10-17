package com.example.cuahangarea_realfood.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.Screen.DS_SanPhamActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    CardView danhSachSanPham,maGiamGia,danhSachDonHang,phanHoiKhachHang,khuVucBep,DoanhThu;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        danhSachDonHang = view.findViewById(R.id.cardView_DanhSachDonHang);
        danhSachSanPham = view.findViewById(R.id.cardView_DanhSachSamPham);
        maGiamGia = view.findViewById(R.id.cardView_MaGiamGia);
        phanHoiKhachHang = view.findViewById(R.id.cardView_PhanHoiKhachHang);
        khuVucBep = view.findViewById(R.id.cardView_KhuVucBep);
        phanHoiKhachHang = view.findViewById(R.id.cardView_PhanHoiKhachHang);


        danhSachSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DS_SanPhamActivity.class);
                startActivity(intent);
            }
        });

        return  view;
    }
}