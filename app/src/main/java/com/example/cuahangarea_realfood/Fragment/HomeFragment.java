package com.example.cuahangarea_realfood.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.Screen.DS_SanPhamActivity;
import com.example.cuahangarea_realfood.Screen.ThongTinCuaHangActivity;
import com.example.cuahangarea_realfood.databinding.FragmentHomeBinding;
import com.example.cuahangarea_realfood.model.CuaHang;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    CardView danhSachSanPham,maGiamGia,danhSachDonHang,phanHoiKhachHang,khuVucBep,DoanhThu;
    FragmentHomeBinding binding;
    Firebase_Manager firebase_manager = new Firebase_Manager();
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

        Loaddata();


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



    private void Loaddata() {
        firebase_manager. mDatabase.child("CuaHang").child(firebase_manager.auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CuaHang temp = dataSnapshot.getValue(CuaHang.class);
                binding.txtTenCuaHang.setText(temp.getTenCuaHang());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        firebase_manager.storageRef.child("CuaHang").child(firebase_manager.auth.getUid()).child("Avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(binding.profileImage);
                binding.progessbarAvatar.setVisibility(View.GONE);
            }
        });
        firebase_manager.storageRef.child("CuaHang").child(firebase_manager.auth.getUid()).child("WallPaper").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(binding.imagebackground);
                binding.progessbarWallPaper.setVisibility(View.GONE);

            }
        });
    }
}