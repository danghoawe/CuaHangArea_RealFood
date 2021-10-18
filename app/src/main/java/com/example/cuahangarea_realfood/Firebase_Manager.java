package com.example.cuahangarea_realfood;

import android.net.Uri;
import android.util.Log;

import com.example.cuahangarea_realfood.model.CuaHang;
import com.example.cuahangarea_realfood.model.DanhMuc;
import com.example.cuahangarea_realfood.model.SanPham;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Objects;

public class Firebase_Manager {
    public  DatabaseReference mDatabase ;
    public StorageReference storageRef ;
    public FirebaseAuth auth;
    public Firebase_Manager() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();
        auth= FirebaseAuth.getInstance();
    }
    public void Ghi_CuaHang(CuaHang cuaHang)
    {
        mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang);
    }
    public void Ghi_DanhMuc(DanhMuc danhMuc)
    {
        mDatabase.child("DanhMuc").child(auth.getUid()).child(danhMuc.getIDDanhMuc()).setValue(danhMuc);
    }
    public Task<Void> Ghi_SanPham(SanPham sanPham)
    {
       return mDatabase.child("SanPham").child(auth.getUid()).child((sanPham.getIDSanPham())).setValue(sanPham);
    }
    public UploadTask UpImageDanhMuc(Uri truoc, String danhMuc)
    {
       return storageRef.child("DanhMuc").child(danhMuc).child("image").putFile(truoc);
    }
    public void UpImageSanPham(ArrayList<Uri> image, String idSanPham,ArrayList<String>nameImage)
    {
        for (int i = 0; i<image.size();i++
        ) {
            storageRef.child("SanPham").child(auth.getUid()).child(idSanPham).child(nameImage.get(i)).putFile(image.get(i));
        }
    }
    public ArrayList<DanhMuc> GetDanhMuc()
    {
        ArrayList<DanhMuc> danhMucs = new ArrayList<>();
        mDatabase.child("DanhMuc").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                danhMucs.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DanhMuc danhMuc = postSnapshot.getValue(DanhMuc.class);
                    danhMucs.add(danhMuc);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return  danhMucs;
    }

    public void Up2MatCMND(Uri truoc, Uri sau, String IDCuaHang)
    {
        storageRef.child("CuaHang").child(IDCuaHang).child("CMND_MatTruoc").putFile(truoc);
        storageRef.child("CuaHang").child(IDCuaHang).child("CMND_MatSau").putFile(sau);
    }
    public ArrayList<DanhMuc> GetDanhSachDanhMuc()
    {
        ArrayList<DanhMuc> danhMucs = new ArrayList<>();
        mDatabase.child("DanhMuc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                danhMucs.clear();
                Log.d("a",dataSnapshot.toString());
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    DanhMuc danhMuc = postSnapshot.getValue(DanhMuc.class);
                    danhMucs.add(danhMuc);
                    Log.d("a",danhMuc.getIDDanhMuc()+danhMucs.size());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return danhMucs;
    }

}
