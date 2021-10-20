package com.example.cuahangarea_realfood;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cuahangarea_realfood.model.CuaHang;
import com.example.cuahangarea_realfood.model.DanhMuc;
import com.example.cuahangarea_realfood.model.SanPham;
import com.example.cuahangarea_realfood.model.ThongBao;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class Firebase_Manager {
    public  DatabaseReference mDatabase ;
    public StorageReference storageRef ;
    public FirebaseAuth auth;
    public Firebase_Manager() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();
        auth= FirebaseAuth.getInstance();
    }

    public Task<Void> Ghi_ThongBao(ThongBao thongBao)
    {
        return  mDatabase.child("ThongBao").child(auth.getUid()).child(thongBao.getIDThongBao()).setValue(thongBao);
    }

    public Task<Void> Ghi_CuaHang(CuaHang cuaHang)
    {
      return  mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang);
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
            try {
                storageRef.child("SanPham").child(auth.getUid()).child(idSanPham).child(nameImage.get(i)).putFile(image.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Log.d("Upload Image: ","Success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull  Exception e) {
                        Log.d("Upload Image: ","Faild");

                    }
                });
            }catch (Exception e)
            {
                Log.d("Firebase: UpLoad",e.getMessage());
            }
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
    public UploadTask UpAvatar(Uri Avatar)
    {
        return   storageRef.child("CuaHang").child(auth.getUid()).child("Avatar").putFile(Avatar);

    }
    public UploadTask UpWallPaper(Uri WallPaper)
    {
        return   storageRef.child("CuaHang").child(auth.getUid()).child("WallPaper").putFile(WallPaper);
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
    public CuaHang getCuaHang(){
        final CuaHang[] cuaHang = new CuaHang[1];
        mDatabase.child("CuaHang").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               CuaHang temp = dataSnapshot.getValue(CuaHang.class);
               cuaHang[0] = temp;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return cuaHang[0];
    }

}
