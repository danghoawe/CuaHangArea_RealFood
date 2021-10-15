package com.example.cuahangarea_realfood;

import android.net.Uri;

import com.example.cuahangarea_realfood.model.CuaHang;
import com.example.cuahangarea_realfood.model.DanhMuc;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Firebase_Manager {
    DatabaseReference mDatabase ;
    StorageReference storageRef ;
    public Firebase_Manager() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();
    }
    public void Ghi_CuaHang(CuaHang cuaHang)
    {
        mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang);
    }
    public void Ghi_DanhMuc(DanhMuc danhMuc)
    {
        mDatabase.child("DanhMuc").child(danhMuc.getIDDanhMuc()).setValue(danhMuc);
    }
    public void UpImageDanhMuc(Uri truoc, String danhMuc)
    {
        storageRef.child("DanhMuc").child(danhMuc).child("image").putFile(truoc);
    }
    public void Up2MatCMND(Uri truoc, Uri sau, String IDCuaHang)
    {
        storageRef.child("CuaHang").child(IDCuaHang).child("CMND_MatTruoc").putFile(truoc);
        storageRef.child("CuaHang").child(IDCuaHang).child("CMND_MatSau").putFile(sau);
    }

}
